package com.rodrigopeleias.barbershop.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

@Getter
@Builder
@EqualsAndHashCode
public class Availability {

    private final List<DayOfWeek> daysOfWeek;
    private LocalTime openTime;

    private LocalTime closeTime;
    private final LocalTime duration;

    private Availability(List<DayOfWeek> daysOfWeek, LocalTime openTime, LocalTime closeTime, LocalTime duration) {
        this.daysOfWeek = daysOfWeek;
        validateAndInitialize(openTime, closeTime);
        this.duration = duration;
    }

    public static Availability of(List<DayOfWeek> daysOfWeek, LocalTime openTime, LocalTime closeTime, LocalTime duration) {
        return new Availability(daysOfWeek, openTime, closeTime, duration);
    }

    public void validateAndInitialize(LocalTime openTime, LocalTime closeTime) {
        this.openTime = openTime;
        if (closeTime.isBefore(this.openTime)) {
            throw new IllegalArgumentException("Close time must be greter than open time");
        }
        this.closeTime = closeTime;
    }

    public int dailyAvailableTime() {
        return (closeTime.getHour() - openTime.getHour()) / duration.getHour();
    }
}
