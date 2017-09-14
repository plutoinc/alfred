package network.pluto.alfred;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({"network.pluto.alfred", "network.pluto.bibliotheca"})
@EnableJpaAuditing
@EnableJpaRepositories("network.pluto.bibliotheca.repositories")
@EntityScan(
        value = "network.pluto.bibliotheca.models",
        basePackageClasses = Jsr310JpaConverters.class)
public class AlfredApplication {

    public static void main(String[] args) {
        SpringApplication.run(AlfredApplication.class, args);
    }
}
