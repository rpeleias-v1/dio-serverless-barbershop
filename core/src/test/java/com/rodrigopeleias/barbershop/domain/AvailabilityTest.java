package com.rodrigopeleias.barbershop.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.List;

import static java.time.DayOfWeek.FRIDAY;
import static java.time.DayOfWeek.MONDAY;
import static java.time.DayOfWeek.SATURDAY;
import static java.time.DayOfWeek.THURSDAY;
import static java.time.DayOfWeek.TUESDAY;
import static java.time.DayOfWeek.WEDNESDAY;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AvailabilityTest {

    @Test
    void whenAvailabilityIsGivenThenItShouldBeCreated() {
        var expectedDaysOfWeek = List.of(MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY);
        var expectedOpenTime = LocalTime.of(10, 0);
        var expectedCloseTime = LocalTime.of(19, 0);
        var expectedDuration = LocalTime.of(1, 0);
        var availability = Availability.of(expectedDaysOfWeek, expectedOpenTime, expectedCloseTime, expectedDuration);

        assertThat(availability.getDaysOfWeek(), equalTo(expectedDaysOfWeek));
        assertThat(availability.getOpenTime(), equalTo(expectedOpenTime));
        assertThat(availability.getCloseTime(), equalTo(expectedCloseTime));
        assertThat(availability.getDuration(), equalTo(expectedDuration));
    }

    @Test
    void whenAvailabilityWithInvalidCloseTimeIsGivenThenAnExceptionShouldBeThrown() {
        var expectedDaysOfWeek = List.of(MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY);
        var expectedOpenTime = LocalTime.of(10, 0);
        var expectedCloseTime = LocalTime.of(8, 0);
        var expectedDuration = LocalTime.of(1, 0);

        assertThrows(IllegalArgumentException.class, () -> Availability.of(expectedDaysOfWeek, expectedOpenTime, expectedCloseTime, expectedDuration));
    }

    @Test
    void whenAvailabilityIsGivenThenTotalAvailableTimesShouldBeCalculated() {
        var expectedDaysOfWeek = List.of(MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY);
        var expectedOpenTime = LocalTime.of(10, 0);
        var expectedCloseTime = LocalTime.of(19, 0);
        var expectedDuration = LocalTime.of(1, 0);
        var availability = Availability.of(expectedDaysOfWeek, expectedOpenTime, expectedCloseTime, expectedDuration);

        assertThat(availability.dailyAvailableTime(), is(9));
    }

}
