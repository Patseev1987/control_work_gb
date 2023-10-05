package org.example.model;

import org.example.dao.DataAccess;
import org.example.exceptions.OpenCounterException;

import java.io.Closeable;
import java.io.IOException;

public class CounterNew implements Closeable {
    private boolean isOpen = true;
    private static int count;
    static {
        DataAccess dataAccess = new DataAccess();
        count = dataAccess.getCountAnimals();
    }
    public void add() throws OpenCounterException {
        if (isOpen) {
            count++;
        }else {
            throw new OpenCounterException("Счетчик закрыт");
        }
    }

    @Override
    public void close() throws OpenCounterException {
        isOpen = false;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public static int getCount() {
        return count;
    }
}
