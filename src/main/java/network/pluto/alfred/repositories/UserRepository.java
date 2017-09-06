package network.pluto.alfred.repositories;

import network.pluto.alfred.models.User;
import network.pluto.alfred.models.Wallet;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByWallet(Wallet wallet);
}
