package com.rodrigopeleias.barbershop.ports.in;

import com.rodrigopeleias.barbershop.shared.SelfValidating;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

@Getter
public class AvailabilityCommand extends SelfValidating<AvailabilityCommand> {

    @NotNull
    private final List<DayOfWeek> daysOfWeek;

    @NotNull
    private final LocalTime openTime;

    @NotNull
    private final LocalTime closeTime;

    @NotNull
    private final LocalTime duration;

    public AvailabilityCommand(List<DayOfWeek> daysOfWeek, LocalTime openTime, LocalTime closeTime, LocalTime duration) {
        this.daysOfWeek = daysOfWeek;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.duration = duration;
        this.validateSelf();
    }
}
