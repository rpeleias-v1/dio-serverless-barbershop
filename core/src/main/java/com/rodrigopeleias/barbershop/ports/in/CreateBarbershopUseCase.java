package com.rodrigopeleias.barbershop.ports.in;

import com.rodrigopeleias.barbershop.ports.out.BarbershopRepositoryPort;
import com.rodrigopeleias.barbershop.ports.out.CreatedBarbershop;
import com.rodrigopeleias.barbershop.service.CreateBarbershopService;

public interface CreateBarbershopUseCase {

    CreatedBarbershop create(CreateBarbershopCommand createBarbershopCommand);

    static CreateBarbershopUseCase getInstance(BarbershopRepositoryPort barbershopRepositoryPort) {
        return new CreateBarbershopService(barbershopRepositoryPort);
    }
}
