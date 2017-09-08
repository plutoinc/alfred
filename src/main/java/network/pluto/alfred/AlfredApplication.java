package network.pluto.alfred;

import network.pluto.bibliotheca.configurations.BibliothecaConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(BibliothecaConfiguration.class)
public class AlfredApplication {

    public static void main(String[] args) {
        SpringApplication.run(AlfredApplication.class, args);
    }
}
