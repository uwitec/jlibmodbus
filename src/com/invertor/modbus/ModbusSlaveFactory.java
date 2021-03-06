package com.invertor.modbus;

import com.invertor.modbus.serial.SerialParameters;
import com.invertor.modbus.serial.SerialPort;
import com.invertor.modbus.slave.ModbusSlaveASCII;
import com.invertor.modbus.slave.ModbusSlaveRTU;
import com.invertor.modbus.slave.ModbusSlaveTCP;
import com.invertor.modbus.tcp.TcpParameters;

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

final public class ModbusSlaveFactory {

    private ModbusSlaveFactory() {

    }

    /**
     * Creates ModbusSlaveRTU instance.
     *
     * @param device   - serial port device name
     * @param baudRate - baud rate
     * @param dataBits - data bit count
     * @param stopBits - stop bit count(1,2)
     * @param parity   - parity bit(NONE, EVEN, ODD, MARK, SPACE)
     * @return ModbusSlave instance if there is no errors, else null
     * @see com.invertor.modbus.serial.SerialPort.Parity
     * @see com.invertor.modbus.serial.SerialPort.BaudRate
     * @see com.invertor.modbus.slave.ModbusSlaveRTU
     * @see com.invertor.modbus.ModbusSlave
     */
    static public ModbusSlave createModbusSlaveRTU(String device, SerialPort.BaudRate baudRate, int dataBits, int stopBits, SerialPort.Parity parity) {
        return new ModbusSlaveRTU(device, baudRate, dataBits, stopBits, parity);
    }

    /**
     * Creates ModbusSlaveRTU instance.
     *
     * @param sp - a SerialParameters instance.
     * @return ModbusSlave instance if there is no errors, else null
     * @see com.invertor.modbus.serial.SerialParameters
     * @see com.invertor.modbus.slave.ModbusSlaveRTU
     * @see com.invertor.modbus.ModbusSlave
     */
    static public ModbusSlave createModbusSlaveRTU(SerialParameters sp) {
        return new ModbusSlaveRTU(sp);
    }

    /**
     * Creates ModbusSlaveASCII instance.
     *
     * @param device   - serial port device name
     * @param baudRate - baud rate
     * @param parity   - parity bit(NONE, EVEN, ODD, MARK, SPACE)
     * @return ModbusSlave instance if there is no errors, else null
     * @see com.invertor.modbus.serial.SerialParameters
     * @see com.invertor.modbus.slave.ModbusSlaveASCII
     * @see com.invertor.modbus.ModbusSlave
     */
    static public ModbusSlave createModbusSlaveASCII(String device, SerialPort.BaudRate baudRate, SerialPort.Parity parity) {
        return new ModbusSlaveASCII(device, baudRate, parity);
    }

    /**
     * Creates ModbusSlaveASCII instance with even parity completion.
     *
     * @param device   - serial port device name
     * @param baudRate - baud rate
     * @return ModbusSlave instance if there is no errors, else null
     * @see com.invertor.modbus.serial.SerialPort.BaudRate
     * @see com.invertor.modbus.slave.ModbusSlaveASCII
     * @see com.invertor.modbus.ModbusSlave
     */
    static public ModbusSlave createModbusSlaveASCII(String device, SerialPort.BaudRate baudRate) {
        return new ModbusSlaveASCII(device, baudRate);
    }

    /**
     * Creates ModbusSlaveTCP instance.
     *
     * @param host - ip address of remote slave
     * @return ModbusSlave instance if there is no errors, else null
     * @see com.invertor.modbus.slave.ModbusSlaveTCP
     * @see com.invertor.modbus.ModbusSlave
     */
    static public ModbusSlave createModbusSlaveTCP(String host) {
        return createModbusSlaveTCP(host, Modbus.TCP_PORT);
    }

    /**
     * Creates ModbusSlaveTCP instance.
     *
     * @param host - ip address of remote slave
     * @param port - tcp port
     * @return ModbusSlave instance if there is no errors, else null
     * @see com.invertor.modbus.slave.ModbusSlaveTCP
     * @see com.invertor.modbus.ModbusSlave
     */
    static public ModbusSlave createModbusSlaveTCP(String host, int port) {
        return new ModbusSlaveTCP(new TcpParameters(host, port, false));
    }

    /**
     * Creates ModbusSlaveTCP instance.
     *
     * @param tcpParameters - a TcpParameters instance
     * @return ModbusSlave instance if there is no errors, else null
     * @see com.invertor.modbus.slave.ModbusSlaveTCP
     * @see com.invertor.modbus.ModbusSlave
     * @see com.invertor.modbus.tcp.TcpParameters
     */
    static public ModbusSlave createModbusSlaveTCP(TcpParameters tcpParameters) {
        return new ModbusSlaveTCP(tcpParameters);
    }
}
