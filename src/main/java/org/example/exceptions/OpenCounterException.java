package org.example.exceptions;

import java.io.IOException;

public class OpenCounterException extends IOException {
    private String msg;

    public OpenCounterException( String msg) {;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
