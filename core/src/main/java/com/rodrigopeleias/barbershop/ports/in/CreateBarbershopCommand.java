package com.rodrigopeleias.barbershop.ports.in;

import com.rodrigopeleias.barbershop.shared.SelfValidating;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CreateBarbershopCommand extends SelfValidating<CreateBarbershopCommand> {

    @NotNull
    private final String name;

    @NotNull
    private final String address;

    @NotNull
    private final String city;

    @NotNull
    private final AvailabilityCommand availabilityCommand;

    public CreateBarbershopCommand(String name, String address, String city, AvailabilityCommand availabilityCommand) {
        this.name = name;
        this.address = address;
        this.city = city;
        this.availabilityCommand = availabilityCommand;
        this.validateSelf();
    }
}
