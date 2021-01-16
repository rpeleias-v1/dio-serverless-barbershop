package com.rodrigopeleias.barbershop.domain;

import com.rodrigopeleias.barbershop.builder.ReservationBuilder;
import com.rodrigopeleias.barbershop.exception.ScheduleAlreadyReservedException;
import com.rodrigopeleias.barbershop.exception.TimeNotAvailableException;
import com.rodrigopeleias.barbershop.utils.AvailabilityUtils;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static com.rodrigopeleias.barbershop.utils.AvailabilityUtils.*;
import static com.rodrigopeleias.barbershop.utils.AvailabilityUtils.createAvailability;
import static com.rodrigopeleias.barbershop.utils.BarbershopUtils.createBarbershop;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BarbershopTest {

    @Test
    void whenBarbershopIsGivenThenItShouldBeCreated() {
        var expectedName = "Barbearia do Rodrigo";
        var expectedAddress = "Avenida Paulista 201";
        var expectedCity = "São Paulo";

        var barbershop = Barbershop.builder()
                .name(expectedName)
                .address(expectedAddress)
                .city(expectedCity)
                .availability(createAvailability())
                .build();

        assertThat(barbershop.getName(), equalTo(expectedName));
        assertThat(barbershop.getAddress(), equalTo(expectedAddress));
        assertThat(barbershop.getCity(), equalTo(expectedCity));
        assertThat(barbershop.getAvailability(), equalTo(createAvailability()));
    }

    @Test
    void whenReservationIsGivenThenItShouldBeAdded() {
        Barbershop barbershop = createBarbershop();

        Reservation reservation = ReservationBuilder.builder().build();
        barbershop.add(reservation);

        assertTrue(barbershop.getReservations().contains(reservation));
    }

    @Test
    void whenAlreadyReservedTimeIsGivenThenAnExceptionShouldBeThrown() {
        Barbershop barbershop = createBarbershop();

        Reservation reservation = ReservationBuilder.builder().build();
        barbershop.add(reservation);
        Reservation alreadyReservedTime = ReservationBuilder.builder().build();

        assertThrows(ScheduleAlreadyReservedException.class, () -> barbershop.add(alreadyReservedTime));
    }

    @Test
    void whenOvertimeForReservationIsGivenThenAnExceptionShouldBeThrown() {
        Barbershop barbershop = createBarbershop();

        Reservation reservation = ReservationBuilder.builder()
                .time(LocalTime.of(22, 0))
                .build();
        assertThrows(TimeNotAvailableException.class, () -> barbershop.add(reservation));
    }

    @Test
    void whenEarlierTimeForReservationIsGivenThenAnExceptionShouldBeThrown() {
        Barbershop barbershop = createBarbershop();

        Reservation reservation = ReservationBuilder.builder()
                .time(LocalTime.of(8, 0))
                .build();
        assertThrows(TimeNotAvailableException.class, () -> barbershop.add(reservation));
    }
}
