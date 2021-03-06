package com.invertor.modbus.exception;

import com.invertor.modbus.utils.ModbusExceptionCode;

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

/**
 * quote from MODBUS Application Protocol Specification V1.1b
 * <p/>
 * "A value contained in the query data field is not an
 * allowable value for server (or slave). This
 * indicates a fault in the structure of the remainder
 * of a complex request, such as that the implied
 * length is incorrect. It specifically does NOT mean
 * that a data item submitted for storage in a register
 * has a value outside the expectation of the
 * application program, since the MODBUS protocol
 * is unaware of the significance of any particular
 * value of any particular register."
 */
public class IllegalDataValueException extends ModbusProtocolException {

    public IllegalDataValueException() {
        super(ModbusExceptionCode.ILLEGAL_DATA_VALUE);
    }
}
