package uniroma3.siw.videogames;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;

/**
 * Suite completa di test per l'applicazione Videogames
 * 
 * Questa suite include:
 * - Test unitari per i modelli (Videogioco, Utente)
 * - Test unitari per i servizi (VideogiocoService)
 * - Test unitari per i controller (VideogiocoController)
 * - Test di integrazione per i repository
 * 
 * Per eseguire tutti i test:
 * mvn test
 * 
 * Per eseguire test specifici:
 * mvn test -Dtest=VideogiocoTest
 * mvn test -Dtest=VideogiocoServiceTest
 * mvn test -Dtest=VideogiocoControllerTest
 * mvn test -Dtest=VideogiocoRepositoryIntegrationTest
 */
@DisplayName("Suite completa di test per l'applicazione Videogames")
@TestMethodOrder(MethodOrderer.DisplayName.class)
public class VideogamesTestSuite {

    @Test
    @DisplayName("Informazioni sulla suite di test")
    void suiteInfo() {
        System.out.println("=== SUITE DI TEST VIDEOGAMES ===");
        System.out.println("Questa suite include i seguenti test:");
        System.out.println("1. Test unitari per modelli:");
        System.out.println("   - VideogiocoTest: Test per la classe Videogioco");
        System.out.println("   - UtenteTest: Test per la classe Utente");
        System.out.println("2. Test unitari per servizi:");
        System.out.println("   - VideogiocoServiceTest: Test per VideogiocoService con Mockito");
        System.out.println("3. Test unitari per controller:");
        System.out.println("   - VideogiocoControllerTest: Test per VideogiocoController con MockMvc");
        System.out.println("4. Test di integrazione:");
        System.out.println("   - VideogiocoRepositoryIntegrationTest: Test di integrazione con database H2");
        System.out.println("=================================");
    }
}
