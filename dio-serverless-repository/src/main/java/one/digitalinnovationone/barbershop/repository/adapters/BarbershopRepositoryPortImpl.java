package one.digitalinnovationone.barbershop.repository.adapters;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.rodrigopeleias.barbershop.domain.Barbershop;
import com.rodrigopeleias.barbershop.ports.out.BarbershopRepositoryPort;
import one.digitalinnovationone.barbershop.repository.adapters.in.AvailabilityDB;
import one.digitalinnovationone.barbershop.repository.adapters.in.BarbershopDB;

import java.util.Optional;

public class BarbershopRepositoryPortImpl implements BarbershopRepositoryPort {

    private final DynamoDBMapper mapper;

    public BarbershopRepositoryPortImpl() {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
        this.mapper = new DynamoDBMapper(client);
    }

    @Override
    public Optional<Barbershop> load(String barbershopName) {
        BarbershopDB loadedBarbershop = this.mapper.load(BarbershopDB.class, barbershopName);
        System.out.println(loadedBarbershop);

        return loadedBarbershop == null
                ? Optional.empty()
                : Optional.ofNullable(BarbershopDB.to(loadedBarbershop));
    }

    @Override
    public Barbershop save(Barbershop barbershop) {
        AvailabilityDB availabilityDB = AvailabilityDB.from(barbershop.getAvailability());

        BarbershopDB barbershopDB = BarbershopDB.from(barbershop);
        barbershopDB.setAvailability(availabilityDB);

        this.mapper.save(barbershopDB);

        Optional<Barbershop> createdBarbershop = this.load(barbershop.getName());
        return createdBarbershop.get();
    }
}
