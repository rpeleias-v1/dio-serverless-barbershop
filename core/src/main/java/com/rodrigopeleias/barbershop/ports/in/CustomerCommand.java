package com.rodrigopeleias.barbershop.ports.in;

import com.rodrigopeleias.barbershop.shared.SelfValidating;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class CustomerCommand extends SelfValidating<CustomerCommand> {

    @NotEmpty
    @NotNull
    private final String name;

    @NotEmpty
    @NotNull
    private final String email;

    @NotEmpty
    @NotNull
    private final String phone;

    public CustomerCommand(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.validateSelf();
    }
}
