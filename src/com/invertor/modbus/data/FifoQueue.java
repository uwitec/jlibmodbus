package com.invertor.modbus.data;

import com.invertor.modbus.exception.IllegalDataValueException;

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
abstract public class FifoQueue {

    final private int capacity;

    public FifoQueue(int capacity) {
        this.capacity = capacity;
    }

    public abstract int size();

    abstract protected int[] peekImpl();

    abstract protected void addImpl(int register);

    abstract protected void pollImpl();

    final public void poll() {
        if (size() != 0)
            pollImpl();
    }

    final public void add(int register) {
        if (size() < capacity)
            addImpl(register);
    }

    final public int[] get() throws IllegalDataValueException {
        if (size() > 31) {
            throw new IllegalDataValueException();
        }
        return peekImpl();
    }
}
