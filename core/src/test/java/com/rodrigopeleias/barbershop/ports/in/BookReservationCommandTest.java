package com.rodrigopeleias.barbershop.ports.in;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BookReservationCommandTest {

    @Test
    void whenRequiredReservationFieldsAreInformedThenItShouldBeCreated() {
        var barbershopName = "Barbearia do Rodrigo";
        var today = LocalDate.now();
        var dayOfWeek = DayOfWeek.FRIDAY;
        var customerCommand = createCommand();

        BookReservationCommand command = new BookReservationCommand(barbershopName,
                today, dayOfWeek,
                LocalTime.of(10, 0), customerCommand);

        assertThat(command.getBarbershopName(), equalTo(barbershopName));
        assertThat(command.getDate(), equalTo(today));
        assertThat(command.getDayOfWeek(), equalTo(dayOfWeek));
        assertThat(command.getCustomerCommand(), equalTo(customerCommand));
    }

    @Test
    void whenAnyNonRequiredReservationFieldsIsInformedThenAnExceptionShouldBeThrown() {
        var barbershopName = "Barbearia do Rodrigo";
        var today = LocalDate.now();
        var customerCommand = createCommand();

        assertThrows(ConstraintViolationException.class, () ->
                new BookReservationCommand(barbershopName, today, null,
                        LocalTime.of(10, 0), customerCommand));
    }

    private CustomerCommand createCommand() {
        var name = "Rodrigo Peleias";
        var email = "teste@email.com";
        var phone = "(11)99999-99999";

        return new CustomerCommand(name, email, phone);
    }
}
