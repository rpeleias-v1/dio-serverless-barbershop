package com.rodrigopeleias.barbershop.ports.in;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AvailabilityCommandTest {

    @Test
    void whenAvailabilityIsGivenThenItShouldBeCreated() {
        var availabilityCommand = new AvailabilityCommand(List.of(DayOfWeek.FRIDAY),
                LocalTime.of(10, 0),
                LocalTime.of(19, 0),
                LocalTime.of(1, 0));

        assertThat(availabilityCommand, notNullValue());
    }

    @Test
    void whenAvailabilityWithoutRequiredValueIsGivenThenAnExceptionShouldBeThrown() {
        assertThrows(ConstraintViolationException.class,
                () -> new AvailabilityCommand(List.of(DayOfWeek.FRIDAY),
                        LocalTime.of(10, 0),
                        LocalTime.of(19, 0), null));
    }
}
