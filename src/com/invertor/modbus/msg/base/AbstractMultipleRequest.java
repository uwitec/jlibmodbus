package com.invertor.modbus.msg.base;

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
abstract public class AbstractMultipleRequest extends AbstractDataRequest {
    private int quantity;

    protected AbstractMultipleRequest(int serverAddress) throws ModbusNumberException {
        super(serverAddress);
    }

    protected AbstractMultipleRequest(int serverAddress, int startAddress, int quantity) throws ModbusNumberException {
        super(serverAddress, startAddress);
        if (!checkAddressRange(startAddress, quantity))
            throw new ModbusNumberException("Error in start address", startAddress);

        setQuantity(quantity);
    }

    @Override
    protected void readData(ModbusInputStream fifo) throws IOException, ModbusNumberException {
        setQuantity(fifo.readShortBE());
    }

    @Override
    protected void writeData(ModbusOutputStream fifo) throws IOException {
        fifo.writeShortBE(quantity);
    }

    @Override
    protected int dataSize() {
        return 2;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    protected abstract boolean checkAddressRange(int startAddress, int quantity);
}
