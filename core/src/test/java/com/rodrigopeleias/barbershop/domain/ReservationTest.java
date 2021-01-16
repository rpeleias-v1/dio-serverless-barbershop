package com.rodrigopeleias.barbershop.domain;

import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ReservationTest {

    @Test
    void whenReservationIsGivenThenItShouldBeCreated() {
        var expectedDate = LocalDate.of(2020, 1, 12);
        var expectedDayOfWeek = DayOfWeek.FRIDAY;
        var expectedTime = LocalTime.of(10, 0);
        var reservation = Reservation.of(this.createCustomer(), expectedDate, expectedDayOfWeek, expectedTime);

        assertThat(reservation.getCustomer(), equalTo(this.createCustomer()));
        assertThat(reservation.getDayOfWeek(), equalTo(expectedDayOfWeek));
        assertThat(reservation.getTime(), equalTo(expectedTime));
        assertThat(reservation.getDate(), equalTo(expectedDate));

    }

    private Customer createCustomer() {
        var expectedName = "Rodrigo Peleias";
        var expectedEmail = "rodrigo@email.com";
        var expectedPhone = "(11)99991-1234";

        return Customer.of(expectedName, expectedEmail, expectedPhone);
    }
}
