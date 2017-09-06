package network.pluto.alfred.repositories;

import network.pluto.alfred.models.User;
import network.pluto.alfred.models.Wallet;
import org.springframework.data.repository.CrudRepository;

public interface WalletRepository extends CrudRepository<Wallet, Long> {
    Wallet findByUser(User user);
}
