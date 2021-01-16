package com.rodrigopeleias.barbershop.exception;

public class BarbershopNotFoundException extends BusinessException {

    public BarbershopNotFoundException(String barbershopName) {
        super(String.format("Barbershop with name %s not found!", barbershopName), 409);
    }
}
