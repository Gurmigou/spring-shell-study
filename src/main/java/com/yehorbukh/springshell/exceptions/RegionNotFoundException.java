package com.yehorbukh.springshell.exceptions;

public class RegionNotFoundException extends Throwable {
    public RegionNotFoundException() {
    }

    public RegionNotFoundException(String message) {
        super(message);
    }
}
