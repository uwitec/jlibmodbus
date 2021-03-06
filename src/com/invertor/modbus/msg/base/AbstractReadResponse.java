package com.invertor.modbus.msg.base;

import com.invertor.modbus.Modbus;
import com.invertor.modbus.exception.ModbusNumberException;
import com.invertor.modbus.net.stream.base.ModbusInputStream;
import com.invertor.modbus.net.stream.base.ModbusOutputStream;

import java.io.IOException;

/**
 * Copyright (c) 2015-2016 JSC Invertor
 * [http://www.sbp-invertor.ru]
 * <p/>
 * This file is part of JLibModbus.
 * <p/>
 * JLibModbus is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p/>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * <p/>
 * Authors: Vladislav Y. Kochedykov, software engineer.
 * email: vladislav.kochedykov@gmail.com
 */
abstract public class AbstractReadResponse extends ModbusResponse {

    private int byteCount = 0;

    protected AbstractReadResponse(int serverAddress) throws ModbusNumberException {
        super(serverAddress);
    }

    protected AbstractReadResponse(int serverAddress, int byteCount) throws ModbusNumberException {
        super(serverAddress);

        this.byteCount = byteCount;
    }

    public int getByteCount() {
        return byteCount;
    }

    protected void setByteCount(int byteCount) throws ModbusNumberException {
        if (byteCount > (Modbus.MAX_PDU_LENGTH - 2))
            throw new ModbusNumberException("Byte count greater then max allowable");
        this.byteCount = byteCount;
    }

    @Override
    final public void readResponse(ModbusInputStream fifo) throws IOException, ModbusNumberException {
        setByteCount(fifo.read());
        readData(fifo);
    }

    @Override
    final public void writeResponse(ModbusOutputStream fifo) throws IOException {
        fifo.write(getByteCount());
        writeData(fifo);
    }

    abstract protected void readData(ModbusInputStream fifo) throws IOException, ModbusNumberException;

    abstract protected void writeData(ModbusOutputStream fifo) throws IOException;

    @Override
    final protected int responseSize() {
        return 1 + getByteCount();
    }
}
