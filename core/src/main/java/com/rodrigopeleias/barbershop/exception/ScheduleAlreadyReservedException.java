package com.rodrigopeleias.barbershop.exception;

import com.rodrigopeleias.barbershop.domain.Customer;

public class ScheduleAlreadyReservedException extends BusinessException {

    public ScheduleAlreadyReservedException(Customer customer) {
        super(String.format("Schedule informed by customer %s is already registered", customer), 400);
    }
}
