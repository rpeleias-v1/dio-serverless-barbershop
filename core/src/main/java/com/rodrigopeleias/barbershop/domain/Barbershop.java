package com.rodrigopeleias.barbershop.domain;

import com.rodrigopeleias.barbershop.exception.ScheduleAlreadyReservedException;
import com.rodrigopeleias.barbershop.exception.TimeNotAvailableException;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@EqualsAndHashCode
public class Barbershop {

    private final String name;
    private final String address;
    private final String city;
    private final Availability availability;

    @Builder.Default
    private final List<Reservation> reservations = new ArrayList<>();

    public void add(Reservation reservation) {
        verifyIfTimeIsAlreadyScheduled(reservation);
        verifyIfTimeInformedIsValid(reservation);
        this.reservations.add(reservation);
    }

    private void verifyIfTimeIsAlreadyScheduled(Reservation reservation) {
        if (this.reservations.contains(reservation)) {
            throw new ScheduleAlreadyReservedException(reservation.getCustomer());
        }
    }

    private void verifyIfTimeInformedIsValid(Reservation reservation) {
        LocalTime openTime = availability.getOpenTime();
        LocalTime closeTime = availability.getCloseTime();

        LocalTime informedReservationTime = reservation.getTime();
        if (informedReservationTime.isBefore(openTime) || informedReservationTime.isAfter(closeTime)) {
            throw new TimeNotAvailableException(informedReservationTime);
        }
    }


}
