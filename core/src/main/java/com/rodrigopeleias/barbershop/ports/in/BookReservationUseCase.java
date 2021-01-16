package com.rodrigopeleias.barbershop.ports.in;

import com.rodrigopeleias.barbershop.ports.out.BarbershopRepositoryPort;
import com.rodrigopeleias.barbershop.ports.out.BookedReservation;
import com.rodrigopeleias.barbershop.service.BookReservationService;

public interface BookReservationUseCase {

    BookedReservation book(BookReservationCommand bookReservationCommand);

    static BookReservationUseCase getInstance(BarbershopRepositoryPort barbershopRepositoryPort) {
        return new BookReservationService(barbershopRepositoryPort);
    }
}
