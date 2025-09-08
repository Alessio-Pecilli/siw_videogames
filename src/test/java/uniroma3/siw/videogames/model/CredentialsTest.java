package uniroma3.siw.videogames.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;


class CredentialsTest {

	@Test
	void testCheckPasswordSuccess() {
		Credentials c1 = new Credentials();
		c1.setPassword("ciao");
		assertTrue(c1.checkPassword("ciao"));
	}

}
