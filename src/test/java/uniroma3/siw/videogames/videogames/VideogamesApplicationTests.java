package uniroma3.siw.videogames.videogames;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(
    properties = {
        "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration," +
        "org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration," +
        "org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration"
    }
)
@ActiveProfiles("test")
class VideogamesApplicationTests {

	@Test
	void contextLoads() {
	}

}
