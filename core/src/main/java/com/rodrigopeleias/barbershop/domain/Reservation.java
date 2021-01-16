package com.rodrigopeleias.barbershop.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(exclude = {"customer"})
public class Reservation {

    private final Customer customer;
    private final LocalDate date;
    private final DayOfWeek dayOfWeek;
    private final LocalTime time;

    public static Reservation of(Customer customer, LocalDate date, DayOfWeek dayOfWeek, LocalTime time) {
        return new Reservation(customer, date, dayOfWeek, time);
    }
}
