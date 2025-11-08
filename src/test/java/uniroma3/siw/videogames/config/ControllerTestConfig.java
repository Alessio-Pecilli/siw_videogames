package uniroma3.siw.videogames.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Configurazione addizionale per i test dei controller.
 * - Abilita la scansione dei componenti dell'applicazione
 */
@Configuration
@ComponentScan(basePackages = "uniroma3.siw.videogames")
public class ControllerTestConfig {
}