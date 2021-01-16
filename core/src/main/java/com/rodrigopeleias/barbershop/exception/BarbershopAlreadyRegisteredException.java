package com.rodrigopeleias.barbershop.exception;

public class BarbershopAlreadyRegisteredException extends BusinessException {

    public BarbershopAlreadyRegisteredException(String barbershopName) {
        super(String.format("Barbershop with name %s already exists!", barbershopName), 409);
    }
}
