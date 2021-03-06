package com.invertor.modbus.master;

import com.invertor.modbus.net.ModbusConnection;
import com.invertor.modbus.net.ModbusConnectionFactory;
import com.invertor.modbus.serial.SerialParameters;
import com.invertor.modbus.serial.SerialPort;
import com.invertor.modbus.serial.SerialUtils;

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

final public class ModbusMasterASCII extends ModbusMasterSerial {

    final private ModbusConnection conn;

    public ModbusMasterASCII(SerialParameters parameters) {
        conn = ModbusConnectionFactory.getASCII(SerialUtils.createSerial(parameters));
    }

    public ModbusMasterASCII(String device, SerialPort.BaudRate baudRate, SerialPort.Parity parity) {
        this(new SerialParameters(device, baudRate, 7, parity == SerialPort.Parity.NONE ? 2 : 1, parity));
    }

    public ModbusMasterASCII(String device, SerialPort.BaudRate baudRate) {
        this(device, baudRate, SerialPort.Parity.EVEN);
    }

    @Override
    protected ModbusConnection getConnection() {
        return conn;
    }
}
