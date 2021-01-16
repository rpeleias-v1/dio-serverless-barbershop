package one.digitalinnovationone.barbershop.adapter.out.repository;

import com.rodrigopeleias.barbershop.domain.Barbershop;
import com.rodrigopeleias.barbershop.ports.out.BarbershopRepositoryPort;

import java.util.Optional;

public class MockBarbershopRepositoryPortImpl implements BarbershopRepositoryPort {

    @Override
    public Optional<Barbershop> load(String barbershopName) {
//        return Optional.of(BarbershopUtils.createBarbershop());
        return Optional.empty();
    }

    @Override
    public Barbershop save(Barbershop barbershop) {
        return MockBarbershopUtils.createBarbershop();
    }
}
