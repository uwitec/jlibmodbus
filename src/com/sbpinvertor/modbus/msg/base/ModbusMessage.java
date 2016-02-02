package com.sbpinvertor.modbus.msg.base;

import com.sbpinvertor.modbus.Modbus;
import com.sbpinvertor.modbus.exception.ModbusNumberException;
import com.sbpinvertor.modbus.net.stream.base.ModbusInputStream;
import com.sbpinvertor.modbus.net.stream.base.ModbusOutputStream;
import com.sbpinvertor.modbus.utils.ModbusFunctionCode;

import java.io.IOException;

/**
 * Copyright (c) 2015-2016 JSC "Zavod "Invertor"
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

abstract public class ModbusMessage implements Transportable {

    final private int serverAddress;
    private int protocolId = Modbus.PROTOCOL_ID;
    private int transactionId = 0;

    ModbusMessage(int serverAddress) throws ModbusNumberException {
        if (!Modbus.checkServerAddress(serverAddress))
            throw new ModbusNumberException("Error in slave id", serverAddress);
        this.serverAddress = serverAddress;
    }

    final public void write(ModbusOutputStream fifo) throws IOException {
        fifo.write(getServerAddress());
        writePDU(fifo);
    }

    final public void read(ModbusInputStream fifo) throws ModbusNumberException, IOException {
        readPDU(fifo);
    }

    abstract protected void readPDU(ModbusInputStream fifo) throws ModbusNumberException, IOException;

    abstract protected void writePDU(ModbusOutputStream fifo) throws IOException;

    abstract public ModbusFunctionCode getFunction();

    public int size() {
        return 1 + pduSize();
    }

    abstract protected int pduSize();

    public int getServerAddress() {
        return serverAddress;
    }

    public int getProtocolId() {
        return protocolId;
    }

    public void setProtocolId(int protocolId) {
        this.protocolId = protocolId;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }
}
