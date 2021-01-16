package one.digitalinnovationone.barbershop.repository.adapters.in;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.rodrigopeleias.barbershop.domain.Barbershop;
import com.rodrigopeleias.barbershop.domain.Reservation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamoDBTable(tableName = "Barbershop")
@ToString
public class BarbershopDB {

    @DynamoDBHashKey(attributeName = "Name")
    private String name;

    @DynamoDBAttribute(attributeName = "Address")
    private String address;

    @DynamoDBAttribute(attributeName = "City")
    private String city;

    @DynamoDBAttribute(attributeName = "Availability")
    private AvailabilityDB availability;

    @DynamoDBAttribute(attributeName = "Reservations")
    private List<ReservationDB> reservations;

    public static BarbershopDB from(Barbershop barbershop) {
        List<ReservationDB> reservations = barbershop.getReservations()
                .stream()
                .map(ReservationDB::from)
                .collect(Collectors.toList());

        return BarbershopDB.builder()
                .name(barbershop.getName())
                .address(barbershop.getAddress())
                .city(barbershop.getCity())
                .availability(AvailabilityDB.from(barbershop.getAvailability()))
                .reservations(reservations)
                .build();
    }

    public static Barbershop to(BarbershopDB barbershopDB) {
        List<Reservation> reservations = barbershopDB.getReservations()
                .stream()
                .map(ReservationDB::to)
                .collect(Collectors.toList());

        return Barbershop.builder()
                .name(barbershopDB.getName())
                .address(barbershopDB.getAddress())
                .city(barbershopDB.getCity())
                .availability(AvailabilityDB.to(barbershopDB.getAvailability()))
                .reservations(reservations)
                .build();
    }
}
