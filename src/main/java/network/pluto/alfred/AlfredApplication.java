package network.pluto.alfred;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({"network.pluto.alfred", "network.pluto.bibliotheca"})
@EnableJpaRepositories("network.pluto.bibliotheca.repositories")
@EntityScan("network.pluto.bibliotheca.models")
public class AlfredApplication {

    public static void main(String[] args) {
        SpringApplication.run(AlfredApplication.class, args);
    }
}
