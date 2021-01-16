package one.digitalinnovationone.barbershop.repository.adapters.in;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConvertedEnum;
import com.rodrigopeleias.barbershop.domain.Reservation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import one.digitalinnovationone.barbershop.repository.adapters.converter.LocalDateConverter;
import one.digitalinnovationone.barbershop.repository.adapters.converter.LocalTimeConverter;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamoDBDocument
public class ReservationDB {

    @DynamoDBAttribute(attributeName = "Customer")
    private CustomerDB customer;

    @DynamoDBTypeConverted(converter = LocalDateConverter.class)
    @DynamoDBAttribute(attributeName = "Date")
    private LocalDate date;

    @DynamoDBAttribute(attributeName = "DayOfWeek")
    private String dayOfWeek;

    @DynamoDBTypeConverted(converter = LocalTimeConverter.class)
    @DynamoDBAttribute(attributeName = "Time")
    private LocalTime time;

    public static ReservationDB from(Reservation reservation) {
        return ReservationDB.builder()
                .customer(CustomerDB.from(reservation.getCustomer()))
                .dayOfWeek(reservation.getDayOfWeek().name())
                .time(reservation.getTime())
                .build();
    }

    public static Reservation to(ReservationDB reservationDB) {
        return Reservation.builder()
                .customer(CustomerDB.to(reservationDB.getCustomer()))
                .dayOfWeek(DayOfWeek.valueOf(reservationDB.getDayOfWeek()))
                .time(reservationDB.getTime())
                .build();
    }
}
