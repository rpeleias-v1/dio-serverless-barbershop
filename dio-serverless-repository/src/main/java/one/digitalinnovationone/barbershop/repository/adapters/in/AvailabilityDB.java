package one.digitalinnovationone.barbershop.repository.adapters.in;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConvertedEnum;
import com.rodrigopeleias.barbershop.domain.Availability;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import one.digitalinnovationone.barbershop.repository.adapters.converter.LocalTimeConverter;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamoDBDocument
public class AvailabilityDB {

    @DynamoDBAttribute(attributeName = "daysOfWeek")
    private List<String> daysOfWeek;

    @DynamoDBAttribute(attributeName = "OpenTime")
    @DynamoDBTypeConverted(converter = LocalTimeConverter.class)
    private LocalTime openTime;

    @DynamoDBAttribute(attributeName = "CloseTime")
    @DynamoDBTypeConverted(converter = LocalTimeConverter.class)
    private LocalTime closeTime;

    @DynamoDBAttribute(attributeName = "Duration")
    @DynamoDBTypeConverted(converter = LocalTimeConverter.class)
    private LocalTime duration;

    public static AvailabilityDB from(Availability availability) {
        List<String> daysOfWeek = availability.getDaysOfWeek().stream()
                .map(DayOfWeek::name)
                .collect(Collectors.toList());

        return AvailabilityDB.builder()
                .daysOfWeek(daysOfWeek)
                .openTime(availability.getOpenTime())
                .closeTime(availability.getCloseTime())
                .duration(availability.getDuration()).build();
    }

    public static Availability to(AvailabilityDB availability) {
        List<DayOfWeek> daysOfWeek = availability.getDaysOfWeek().stream()
                .map(DayOfWeek::valueOf)
                .collect(Collectors.toList());

        return Availability.builder()
                .daysOfWeek(daysOfWeek)
                .openTime(availability.getOpenTime())
                .closeTime(availability.getCloseTime())
                .duration(availability.getDuration()).build();
    }
}
