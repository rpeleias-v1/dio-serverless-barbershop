package one.digitalinnovationone.barbershop.repository.adapters.in;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.rodrigopeleias.barbershop.domain.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamoDBDocument
public class CustomerDB {

    @DynamoDBAttribute(attributeName = "Name")
    private String name;

    @DynamoDBAttribute(attributeName = "Email")
    private String email;

    @DynamoDBAttribute(attributeName = "Phone")
    private String phone;

    public static CustomerDB from(Customer customer) {
        return CustomerDB.builder()
                .name(customer.getName())
                .email(customer.getEmail())
                .phone(customer.getPhone())
                .build();
    }

    public static Customer to(CustomerDB customerDB) {
        return Customer.builder()
                .name(customerDB.getName())
                .email(customerDB.getEmail())
                .phone(customerDB.getPhone())
                .build();
    }
}
