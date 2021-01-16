package com.rodrigopeleias.barbershop.ports.out;

import com.rodrigopeleias.barbershop.domain.Barbershop;

import java.util.Optional;

public interface BarbershopRepositoryPort {

    Optional<Barbershop> load(String barbershopName);

    Barbershop save(Barbershop barbershop);

}
