package com.rodrigopeleias.barbershop.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString(exclude = {"phone"})
public class Customer {

    private final String name;
    private final String email;
    private final String phone;

    public static Customer of(String name, String email, String phone) {
        return new Customer(name, email, phone);
    }
}
