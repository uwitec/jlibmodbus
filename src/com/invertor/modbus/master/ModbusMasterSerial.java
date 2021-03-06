package com.invertor.modbus.master;

import com.invertor.modbus.ModbusMaster;
import com.invertor.modbus.data.CommStatus;
import com.invertor.modbus.exception.ModbusIOException;
import com.invertor.modbus.exception.ModbusNumberException;
import com.invertor.modbus.exception.ModbusProtocolException;
import com.invertor.modbus.msg.base.ModbusRequest;
import com.invertor.modbus.msg.response.*;

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
abstract public class ModbusMasterSerial extends ModbusMaster {

    final private CommStatus commStatus = new CommStatus();

    @Override
    public int readExceptionStatus(int serverAddress) throws
            ModbusProtocolException, ModbusNumberException, ModbusIOException {
        ModbusRequest request = requestFactory.createReadExceptionStatus(serverAddress);
        ReadExceptionStatusResponse response = (ReadExceptionStatusResponse) processRequest(request);
        return response.getExceptionStatus();
    }

    @Override
    public byte[] reportSlaveId(int serverAddress) throws ModbusProtocolException, ModbusNumberException, ModbusIOException {
        ModbusRequest request = requestFactory.createReportSlaveId(serverAddress);
        ReportSlaveIdResponse response = (ReportSlaveIdResponse) processRequest(request);
        return response.getSlaveId();
    }

    @Override
    public CommStatus getCommEventCounter(int serverAddress) throws ModbusProtocolException, ModbusNumberException, ModbusIOException {
        ModbusRequest request = requestFactory.createGetCommEventCounter(serverAddress);
        GetCommEventCounterResponse response = (GetCommEventCounterResponse) processRequest(request);
        commStatus.setCommStatus(response.getStatus());
        commStatus.setEventCount(response.getEventCount());
        return commStatus;
    }

    @Override
    public CommStatus getCommEventLog(int serverAddress) throws ModbusProtocolException, ModbusNumberException, ModbusIOException {
        ModbusRequest request = requestFactory.createGetCommEventLog(serverAddress);
        GetCommEventLogResponse response = (GetCommEventLogResponse) processRequest(request);
        commStatus.setCommStatus(response.getStatus());
        commStatus.setEventCount(response.getEventCount());
        commStatus.setBusMessageCount(response.getMessageCount());
        commStatus.setEventQueue(response.getEventQueue());
        return commStatus;
    }

    @Override
    public void diagnosticsReturnQueryData(int serverAddress, int queryData) throws ModbusNumberException, ModbusProtocolException, ModbusIOException {
        processRequest(requestFactory.createReturnQueryData(serverAddress, queryData));
    }

    @Override
    public void diagnosticsRestartCommunicationsOption(int serverAddress, boolean clearLog) throws ModbusNumberException, ModbusProtocolException, ModbusIOException {
        processRequest(requestFactory.createRestartCommunicationsOption(serverAddress, clearLog));
    }

    @Override
    public int diagnosticsReturnDiagnosticRegister(int serverAddress) throws ModbusNumberException, ModbusProtocolException, ModbusIOException {
        ModbusRequest request = requestFactory.createReturnDiagnosticRegister(serverAddress);
        DiagnosticsResponse response = (DiagnosticsResponse) processRequest(request);
        return response.getSubFunctionData();
    }

    @Override
    public void diagnosticsChangeAsciiInputDelimiter(int serverAddress, int delimiter) throws ModbusNumberException, ModbusProtocolException, ModbusIOException {
        processRequest(requestFactory.createChangeAsciiInputDelimiter(serverAddress, delimiter));
    }

    @Override
    public void diagnosticsForceListenOnlyMode(int serverAddress) throws ModbusNumberException, ModbusProtocolException, ModbusIOException {
        processRequest(requestFactory.createForceListenOnlyMode(serverAddress));
    }

    @Override
    public void diagnosticsClearCountersAndDiagnosticRegister(int serverAddress) throws ModbusNumberException, ModbusProtocolException, ModbusIOException {
        processRequest(requestFactory.createClearCountersAndDiagnosticRegister(serverAddress));
    }

    @Override
    public int diagnosticsReturnBusMessageCount(int serverAddress) throws ModbusNumberException, ModbusProtocolException, ModbusIOException {
        ModbusRequest request = requestFactory.createReturnBusMessageCount(serverAddress);
        DiagnosticsResponse response = (DiagnosticsResponse) processRequest(request);
        return response.getSubFunctionData();
    }

    @Override
    public int diagnosticsReturnBusCommunicationErrorCount(int serverAddress) throws ModbusNumberException, ModbusProtocolException, ModbusIOException {
        ModbusRequest request = requestFactory.createReturnBusCommunicationErrorCount(serverAddress);
        DiagnosticsResponse response = (DiagnosticsResponse) processRequest(request);
        return response.getSubFunctionData();
    }

    @Override
    public int diagnosticsReturnBusExceptionErrorCount(int serverAddress) throws ModbusNumberException, ModbusProtocolException, ModbusIOException {
        ModbusRequest request = requestFactory.createReturnBusExceptionErrorCount(serverAddress);
        DiagnosticsResponse response = (DiagnosticsResponse) processRequest(request);
        return response.getSubFunctionData();
    }

    @Override
    public int diagnosticsReturnSlaveMessageCount(int serverAddress) throws ModbusNumberException, ModbusProtocolException, ModbusIOException {
        ModbusRequest request = requestFactory.createReturnSlaveMessageCount(serverAddress);
        DiagnosticsResponse response = (DiagnosticsResponse) processRequest(request);
        return response.getSubFunctionData();
    }

    @Override
    public int diagnosticsReturnSlaveNoResponseCount(int serverAddress) throws ModbusNumberException, ModbusProtocolException, ModbusIOException {
        ModbusRequest request = requestFactory.createReturnSlaveNoResponseCount(serverAddress);
        DiagnosticsResponse response = (DiagnosticsResponse) processRequest(request);
        return response.getSubFunctionData();
    }

    @Override
    public int diagnosticsReturnSlaveNAKCount(int serverAddress) throws ModbusNumberException, ModbusProtocolException, ModbusIOException {
        ModbusRequest request = requestFactory.createReturnSlaveNAKCount(serverAddress);
        DiagnosticsResponse response = (DiagnosticsResponse) processRequest(request);
        return response.getSubFunctionData();
    }

    @Override
    public int diagnosticsReturnSlaveBusyCount(int serverAddress) throws ModbusNumberException, ModbusProtocolException, ModbusIOException {
        ModbusRequest request = requestFactory.createReturnSlaveBusyCount(serverAddress);
        DiagnosticsResponse response = (DiagnosticsResponse) processRequest(request);
        return response.getSubFunctionData();
    }

    @Override
    public int diagnosticsReturnBusCharacterOverrunCount(int serverAddress) throws ModbusNumberException, ModbusProtocolException, ModbusIOException {
        ModbusRequest request = requestFactory.createReturnBusCharacterOverrunCount(serverAddress);
        DiagnosticsResponse response = (DiagnosticsResponse) processRequest(request);
        return response.getSubFunctionData();
    }

    @Override
    public void diagnosticsClearOverrunCounterAndFlag(int serverAddress) throws ModbusNumberException, ModbusProtocolException, ModbusIOException {
        processRequest(requestFactory.createClearOverrunCounterAndFlag(serverAddress));
    }
}
