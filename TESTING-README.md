# Suite di Test per Videogames Application

## Panoramica

Questa suite di test comprende test unitari e di integrazione per l'applicazione Videogames, utilizzando **JUnit 5** e **Mockito** per garantire la qualità e la correttezza del codice.

## Struttura dei Test

### 📁 Test Unitari per Modelli
- **`VideogiocoTest.java`**: Test completi per la classe `Videogioco`
  - Test costruttori (vuoto e con parametri)
  - Test getter e setter
  - Test metodi equals, hashCode
  - Test calcolo media recensioni
  - Test gestione categorie

- **`UtenteTest.java`**: Test completi per la classe `Utente`
  - Test costruttori
  - Test getter e setter
  - Test metodi equals, hashCode
  - Test gestione liste (recensioni, messaggi)

### 📁 Test Unitari per Servizi
- **`VideogiocoServiceTest.java`**: Test per `VideogiocoService` con Mockito
  - Test getAllVideogames()
  - Test getByStato()
  - Test getByCategoria() e getByCategorie()
  - Test getVideogameById() (esistente e non esistente)
  - Test salvaNuovoVideogame()
  - Test eliminaVideogioco()
  - Test existsVideogioco()

### 📁 Test Unitari per Controller
- **`VideogiocoControllerTest.java`**: Test per `VideogiocoController` con MockMvc
  - Test endpoint `/videogiochi`
  - Test endpoint `/videogiochi/filter` con vari parametri
  - Test endpoint `/videogiochi/{id}`
  - Test filtri combinati

### 📁 Test di Integrazione
- **`VideogiocoRepositoryIntegrationTest.java`**: Test di integrazione con database H2
  - Test operazioni CRUD
  - Test query personalizzate del repository
  - Test relazioni tra entità
  - Test operazioni in cascata

## Come Eseguire i Test

### Prerequisiti
- Java 17+
- Maven 3.6+

### Eseguire Tutti i Test
```bash
mvn test
```

### Eseguire Test Specifici

#### Test per modelli
```bash
mvn test -Dtest=VideogiocoTest
mvn test -Dtest=UtenteTest
```

#### Test per servizi
```bash
mvn test -Dtest=VideogiocoServiceTest
```

#### Test per controller
```bash
mvn test -Dtest=VideogiocoControllerTest
```

#### Test di integrazione
```bash
mvn test -Dtest=VideogiocoRepositoryIntegrationTest
```

### Eseguire Test per Categoria
```bash
# Solo test unitari
mvn test -Dgroups=unit

# Solo test di integrazione
mvn test -Dgroups=integration
```

### Report di Copertura
Per generare un report di copertura del codice:
```bash
mvn jacoco:prepare-agent test jacoco:report
```

Il report sarà disponibile in `target/site/jacoco/index.html`.

## Tecnologie Utilizzate

### 🧪 Framework di Test
- **JUnit 5**: Framework di test principale
- **Mockito**: Per il mocking delle dipendenze
- **MockMvc**: Per test dei controller web
- **TestEntityManager**: Per test di integrazione JPA

### 🗄️ Database di Test
- **H2 Database**: Database in-memory per test di integrazione
- **Spring Boot Test**: Configurazione automatica per test

### 📊 Assertions e Verifiche
- **AssertJ**: Assertions fluide e leggibili
- **Mockito Verify**: Verifica interazioni con mock

## Configurazione

### application-test.properties
```properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true
```

### Dipendenze Maven (già aggiunte)
```xml
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter</artifactId>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>org.mockito</groupId>
    <artifactId>mockito-core</artifactId>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>test</scope>
</dependency>
```

## Esempi di Test

### Test Unitario con Mockito
```java
@ExtendWith(MockitoExtension.class)
class VideogiocoServiceTest {
    
    @Mock
    private VideogiocoRepository repository;
    
    @InjectMocks
    private VideogiocoService service;
    
    @Test
    void testGetAllVideogames() {
        // Given
        when(repository.findAll()).thenReturn(Arrays.asList(videogioco1, videogioco2));
        
        // When
        List<Videogioco> result = service.getAllVideogames();
        
        // Then
        assertEquals(2, result.size());
        verify(repository, times(1)).findAll();
    }
}
```

### Test di Integrazione
```java
@DataJpaTest
@ActiveProfiles("test")
class VideogiocoRepositoryIntegrationTest {
    
    @Autowired
    private TestEntityManager entityManager;
    
    @Autowired
    private VideogiocoRepository repository;
    
    @Test
    void testFindByStato() {
        // Given
        Stato stato = entityManager.persistAndFlush(new Stato());
        Videogioco videogioco = new Videogioco("Test", "Desc", "url", stato, null);
        entityManager.persistAndFlush(videogioco);
        
        // When
        List<Videogioco> result = repository.findByStato(stato);
        
        // Then
        assertEquals(1, result.size());
    }
}
```

## Best Practices Implementate

1. **Naming Convention**: Nomi descrittivi per test e metodi
2. **Given-When-Then**: Struttura chiara dei test
3. **Isolation**: Ogni test è indipendente
4. **Mocking**: Isolamento delle dipendenze nei test unitari
5. **Test Data**: Setup pulito con `@BeforeEach`
6. **Assertions**: Verifiche multiple per completezza
7. **Documentation**: Annotazioni `@DisplayName` descrittive

## Continuous Integration

I test possono essere integrati in pipeline CI/CD:

```yaml
# GitHub Actions example
- name: Run Tests
  run: mvn test
  
- name: Generate Test Report
  run: mvn surefire-report:report
```

## Troubleshooting

### Problemi Comuni

1. **Test falliscono per dipendenze**: Verificare che tutte le dipendenze siano correttamente mocckate
2. **Database issues**: Controllare la configurazione H2 in `application-test.properties`
3. **Security issues**: I test disabilitano automaticamente Spring Security

### Debug dei Test
```bash
# Eseguire test in modalità debug
mvn test -Dmaven.surefire.debug

# Verbose output
mvn test -X
```

## Metriche e Reporting

Dopo l'esecuzione dei test, i report sono disponibili in:
- `target/surefire-reports/`: Report Surefire
- `target/site/jacoco/`: Report di copertura JaCoCo
- Console output con statistiche di esecuzione

## Futuro

Possibili estensioni della suite di test:
- Test per altri controller (AuthController, UtenteController, etc.)
- Test per altri servizi (UtenteService, CategoriaService, etc.)
- Test per validazioni Bean Validation
- Test di performance con JMeter
- Test di sicurezza con Spring Security Test
