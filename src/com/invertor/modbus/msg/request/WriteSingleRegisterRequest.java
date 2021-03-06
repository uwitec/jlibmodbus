package com.invertor.modbus.msg.request;

import com.invertor.modbus.Modbus;
import com.invertor.modbus.data.DataHolder;
import com.invertor.modbus.exception.ModbusNumberException;
import com.invertor.modbus.exception.ModbusProtocolException;
import com.invertor.modbus.msg.base.AbstractDataRequest;
import com.invertor.modbus.msg.base.ModbusResponse;
import com.invertor.modbus.msg.response.WriteSingleRegisterResponse;
import com.invertor.modbus.net.stream.base.ModbusInputStream;
import com.invertor.modbus.net.stream.base.ModbusOutputStream;
import com.invertor.modbus.utils.ModbusFunctionCode;

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
public class WriteSingleRegisterRequest extends AbstractDataRequest {

    private int value;

    public WriteSingleRegisterRequest(int serverAddress) throws ModbusNumberException {
        super(serverAddress);
    }

    public WriteSingleRegisterRequest(int serverAddress, int startAddress, int value) throws ModbusNumberException {
        super(serverAddress, startAddress);

        if (!Modbus.checkRegisterValue(value)) {
            throw new ModbusNumberException("Register value out of range", value);
        }
        setValue(value);
    }

    @Override
    public ModbusResponse process(DataHolder dataHolder) throws ModbusNumberException {
        WriteSingleRegisterResponse response = new WriteSingleRegisterResponse(getServerAddress(), getStartAddress(), getValue());
        try {
            dataHolder.writeHoldingRegister(getStartAddress(), getValue());
        } catch (ModbusProtocolException e) {
            response.setException();
            response.setModbusExceptionCode(e.getException().getValue());
        }
        return response;
    }

    @Override
    public boolean validateResponseImpl(ModbusResponse response) {
        WriteSingleRegisterResponse r = (WriteSingleRegisterResponse) response;
        return r.getStartAddress() == getStartAddress() && r.getValue() == getValue();
    }

    @Override
    final protected void readData(ModbusInputStream fifo) throws IOException {
        setValue(fifo.readShortBE());
    }

    @Override
    public int getFunction() {
        return ModbusFunctionCode.WRITE_SINGLE_REGISTER.toInt();
    }

    @Override
    final public void writeData(ModbusOutputStream fifo) throws IOException {
        fifo.writeShortBE(getValue());
    }

    @Override
    protected int dataSize() {
        return 2;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = ((short) value) & 0xffff;
    }
}
