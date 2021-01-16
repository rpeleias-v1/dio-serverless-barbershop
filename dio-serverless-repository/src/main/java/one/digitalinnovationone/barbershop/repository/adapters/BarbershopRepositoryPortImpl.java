package one.digitalinnovationone.barbershop.repository.adapters;

import com.rodrigopeleias.barbershop.domain.Barbershop;
import com.rodrigopeleias.barbershop.ports.out.BarbershopRepositoryPort;

import java.util.Optional;

public class BarbershopRepositoryPortImpl implements BarbershopRepositoryPort {

    @Override
    public Optional<Barbershop> load(String barbershopName) {
        return null;
    }

    @Override
    public Barbershop save(Barbershop barbershop) {
        return null;
    }
}
