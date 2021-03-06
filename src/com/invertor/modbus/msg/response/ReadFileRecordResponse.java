package com.invertor.modbus.msg.response;

import com.invertor.modbus.exception.ModbusNumberException;
import com.invertor.modbus.msg.base.AbstractReadResponse;
import com.invertor.modbus.msg.base.ModbusFileRecord;
import com.invertor.modbus.net.stream.base.ModbusInputStream;
import com.invertor.modbus.net.stream.base.ModbusOutputStream;
import com.invertor.modbus.utils.DataUtils;
import com.invertor.modbus.utils.ModbusFunctionCode;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

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
final public class ReadFileRecordResponse extends AbstractReadResponse {

    public static final int READ_RESP_SUB_REQ_LENGTH = 2;
    private ModbusFileRecord[] fileRecords = null;

    public ReadFileRecordResponse(int serverAddress) throws ModbusNumberException {
        super(serverAddress);
    }

    @Override
    protected void readData(ModbusInputStream fifo) throws IOException, ModbusNumberException {
        List<ModbusFileRecord> records = new LinkedList<ModbusFileRecord>();
        int response_byte_count = getByteCount();
        int read = 0;
        int i = 0;
        while (read < response_byte_count) {
            int record_byte_count = fifo.read() * 2;
            if (fifo.read() != ModbusFileRecord.REF_TYPE)
                throw new ModbusNumberException("Reference type mismatch.");
            read += READ_RESP_SUB_REQ_LENGTH;
            if (record_byte_count > response_byte_count - read) {
                record_byte_count = response_byte_count - read;
            }
            byte[] buffer = new byte[record_byte_count];
            if (fifo.read(buffer) != record_byte_count)
                throw new ModbusNumberException(record_byte_count + " bytes expected, but not received.");
            read += record_byte_count;
            ModbusFileRecord mfr = new ModbusFileRecord(0, i++, DataUtils.toIntArray(buffer));
            records.add(mfr);
        }
        setFileRecords(records.toArray(new ModbusFileRecord[records.size()]));
    }

    @Override
    protected void writeData(ModbusOutputStream fifo) throws IOException {
        for (ModbusFileRecord r : getFileRecords()) {
            fifo.write(r.getRecordLength());
            fifo.write(ModbusFileRecord.REF_TYPE);
            fifo.write(DataUtils.toByteArray(r.getRegisters()));
        }
    }

    @Override
    public int getFunction() {
        return ModbusFunctionCode.READ_FILE_RECORD.toInt();
    }

    public ModbusFileRecord[] getFileRecords() {
        return fileRecords;
    }

    public void setFileRecords(ModbusFileRecord[] fileRecords) throws ModbusNumberException {
        this.fileRecords = fileRecords;
        int byteCount = 0;
        for (ModbusFileRecord r : getFileRecords()) {
            byteCount += 2 + r.getRecordLength() * 2;
        }
        setByteCount(byteCount);
    }
}
