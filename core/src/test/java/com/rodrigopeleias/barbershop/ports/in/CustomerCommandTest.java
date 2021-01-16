package com.rodrigopeleias.barbershop.ports.in;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CustomerCommandTest {

    @Test
    void whenCustomerIsInformedThenItShouldBeCreated() {
        var name = "Rodrigo Peleias";
        var email = "teste@email.com";
        var phone = "(11)99999-99999";

        CustomerCommand customerCommand = new CustomerCommand(name, email, phone);

        assertThat(customerCommand.getName(), equalTo(name));
        assertThat(customerCommand.getEmail(), equalTo(email));
        assertThat(customerCommand.getPhone(), equalTo(phone));
    }

    @Test
    void whenAnyNonRequiredCustomerFieldIsInformedThenAnExceptionShouldBeThrown() {
        var name = "Rodrigo Peleias";
        var email = "teste@email.com";

        assertThrows(ConstraintViolationException.class, () -> new CustomerCommand(name, email, null));
    }
}
