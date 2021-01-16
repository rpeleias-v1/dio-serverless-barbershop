package com.rodrigopeleias.barbershop.ports.in;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CreateBarbershopCommandTest {

    @Test
    void whenCreateCommandIsGivenThenItShouldBeCreated() {
        var name = "Barbearia do Rodrigo";
        var address = "Avenida Paulista, 201";
        var city = "São Paulo";
        var availability = createAvailability();

        CreateBarbershopCommand command = new CreateBarbershopCommand(name, address, city, availability);

        assertThat(command.getName(), equalTo(name));
        assertThat(command.getAddress(), equalTo(address));
        assertThat(command.getCity(), equalTo(city));
        assertThat(command.getAvailabilityCommand(), equalTo(availability));
    }

    @Test
    void whenCreateCommandWithoutRequiredFieldIsGivenThenAnExceptionShouldBeThrown() {
        var name = "Barbearia do Rodrigo";
        var address = "Avenida Paulista, 201";
        var city = "São Paulo";

        assertThrows(ConstraintViolationException.class, () -> new CreateBarbershopCommand(name, address, city, null));
    }

    private AvailabilityCommand createAvailability() {
        return new AvailabilityCommand(List.of(DayOfWeek.FRIDAY),
                LocalTime.of(10, 0),
                LocalTime.of(19, 0),
                LocalTime.of(1, 0));
    }
}
