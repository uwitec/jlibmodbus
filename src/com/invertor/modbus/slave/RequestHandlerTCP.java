package com.invertor.modbus.slave;

import com.invertor.modbus.Modbus;
import com.invertor.modbus.ModbusSlave;
import com.invertor.modbus.data.DataHolder;
import com.invertor.modbus.exception.ModbusIOException;
import com.invertor.modbus.msg.base.ModbusMessage;
import com.invertor.modbus.msg.base.ModbusRequest;
import com.invertor.modbus.net.ModbusConnection;
import com.invertor.modbus.net.transport.ModbusTransport;

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
 * GNU General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * <p/>
 * Authors: Vladislav Y. Kochedykov, software engineer.
 * email: vladislav.kochedykov@gmail.com
 */
class RequestHandlerTCP extends RequestHandler {

    RequestHandlerTCP(ModbusSlave slave, ModbusConnection conn) {
        super(slave, conn);
    }

    @Override
    public void run() {
        setListening(true);
        try {
            do {
                DataHolder dataHolder = getSlave().getDataHolder();
                ModbusTransport transport = getConn().getTransport();
                ModbusRequest request = (ModbusRequest) transport.readRequest();
                if (request.getServerAddress() == getSlave().getServerAddress()) {
                    ModbusMessage response = request.process(dataHolder);
                    response.setTransactionId(request.getTransactionId());
                    transport.send(response);
                }
            } while (isListening());
        } catch (Exception e) {
            //
        } finally {
            setListening(false);
            try {
                getConn().close();
            } catch (ModbusIOException ioe) {
                Modbus.log().warning(ioe.getMessage());
            }
        }
    }
}