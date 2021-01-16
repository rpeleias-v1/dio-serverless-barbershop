package com.rodrigopeleias.barbershop.service;

import com.rodrigopeleias.barbershop.domain.Barbershop;
import com.rodrigopeleias.barbershop.domain.Customer;
import com.rodrigopeleias.barbershop.domain.Reservation;
import com.rodrigopeleias.barbershop.exception.BarbershopAlreadyRegisteredException;
import com.rodrigopeleias.barbershop.exception.BarbershopNotFoundException;
import com.rodrigopeleias.barbershop.ports.in.BookReservationCommand;
import com.rodrigopeleias.barbershop.ports.in.BookReservationUseCase;
import com.rodrigopeleias.barbershop.ports.in.CustomerCommand;
import com.rodrigopeleias.barbershop.ports.out.BarbershopRepositoryPort;
import com.rodrigopeleias.barbershop.ports.out.BookedReservation;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BookReservationService implements BookReservationUseCase {

    private final BarbershopRepositoryPort barbershopRepositoryPort;

    @Override
    public BookedReservation book(BookReservationCommand command) {
        Barbershop barbershop = barbershopRepositoryPort.load(command.getBarbershopName())
                .orElseThrow(() -> new BarbershopNotFoundException(command.getBarbershopName()));

        CustomerCommand customerCommand = command.getCustomerCommand();
        Customer customer = create(customerCommand);
        Reservation reservation = create(command, customer);

        barbershop.add(reservation);
        barbershopRepositoryPort.save(barbershop);

        return BookedReservation.of(barbershop.getName(), reservation.getDate(),
                reservation.getDayOfWeek(), reservation.getTime(),
                customer.getName());
    }

    private Customer create(CustomerCommand customerCommand) {
        return Customer.of(customerCommand.getName(),
                customerCommand.getEmail(),
                customerCommand.getPhone());
    }

    private Reservation create(BookReservationCommand command, Customer customer) {
        return Reservation.of(customer,
                command.getDate(),
                command.getDayOfWeek(),
                command.getTime());
    }
}
