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
import com.rodrigopeleias.barbershop.utils.BarbershopUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static java.time.DayOfWeek.FRIDAY;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookReservationServiceTest {

    @Mock
    private BarbershopRepositoryPort barbershopRepositoryPort;

    private BookReservationUseCase bookReservationUseCase;

    @BeforeEach
    void setUp() {
        bookReservationUseCase = new BookReservationService(barbershopRepositoryPort);
    }

    @Test
    void whenNewReservationIsInformedThenItShouldBeBooked() {
        CustomerCommand customerCommand = createCustomerCommand();
        BookReservationCommand reservationCommand = createReservationCommand(customerCommand);
        Barbershop expectedBarbershop = BarbershopUtils.createBarbershop();
        Barbershop expectedBarbershopWithReservation = BarbershopUtils.createBarbershop();
        expectedBarbershopWithReservation.add(createReservation(reservationCommand));

        when(barbershopRepositoryPort.load(reservationCommand.getBarbershopName())).thenReturn(Optional.of(expectedBarbershop));
        when(barbershopRepositoryPort.save(expectedBarbershop)).thenReturn(expectedBarbershopWithReservation);

        BookedReservation bookedReservation = bookReservationUseCase.book(reservationCommand);

        assertThat(bookedReservation.getBarbershopName(), equalTo(reservationCommand.getBarbershopName()));
        assertThat(bookedReservation.getDate(), equalTo(reservationCommand.getDate()));
        assertThat(bookedReservation.getDayOfWeek(), equalTo(reservationCommand.getDayOfWeek()));
        assertThat(bookedReservation.getTime(), equalTo(reservationCommand.getTime()));
        assertThat(bookedReservation.getCustomerName(), equalTo(customerCommand.getName()));
    }

    @Test
    void whenNonExistingBarbershopIsInformedThenAnExceptionShouldBeThrown() {
        CustomerCommand customerCommand = createCustomerCommand();
        BookReservationCommand reservationCommand = createReservationCommand(customerCommand);

        when(barbershopRepositoryPort.load(reservationCommand.getBarbershopName())).thenReturn(Optional.empty());

        assertThrows(BarbershopNotFoundException.class, () -> bookReservationUseCase.book(reservationCommand));
    }


    private BookReservationCommand createReservationCommand(CustomerCommand customerCommand) {
        return new BookReservationCommand("Barbearia do Rodrigo",
                LocalDate.now(), FRIDAY, LocalTime.of(11, 0), customerCommand);
    }

    private CustomerCommand createCustomerCommand() {
        CustomerCommand customerCommand = new CustomerCommand("Rodrigo Peleias", "teste@email.com", "(11)99999-9999");
        return customerCommand;
    }

    private Reservation createReservation(BookReservationCommand command) {
        CustomerCommand customerCommand = command.getCustomerCommand();

        Customer customer = Customer.of(customerCommand.getName(),
                customerCommand.getEmail(), customerCommand.getPhone());
        return Reservation.of(customer, command.getDate(), command.getDayOfWeek(), command.getTime());
    }
}
