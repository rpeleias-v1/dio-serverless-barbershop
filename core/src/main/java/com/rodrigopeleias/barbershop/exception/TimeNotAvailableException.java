package com.rodrigopeleias.barbershop.exception;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
public class TimeNotAvailableException extends BusinessException {

    public TimeNotAvailableException(LocalTime time) {
        super(String.format("The following time is not available for booking: %s", time.format(DateTimeFormatter.ofPattern("hh:mm"))), 400);
    }
}
