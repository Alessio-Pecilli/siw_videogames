package uniroma3.siw.videogames.videogames;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(
	classes = uniroma3.siw.videogames.VideogamesApplication.class
)
@ActiveProfiles("test")
class VideogamesApplicationTests {

	@Test
	void contextLoads() {
		// Verifica che il contesto Spring si avvii correttamente
	}
}
