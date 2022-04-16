import org.junit.jupiter.api.*;
import ru.netology.entity.Country;
import ru.netology.i18n.LocalizationServiceImpl;

public class LocalizationServiceImplTest {
    LocalizationServiceImpl sut;

    @BeforeEach
    public void init() {
        sut = new LocalizationServiceImpl();
    }

    @BeforeAll
    public static void start() {
        System.out.println("Start tests");
    }

    @AfterAll
    public static void finish() {
        System.out.printf("Tests finished");
    }

    @Test
    public void localeTest() {
        //given
        Country country = Country.RUSSIA;
        String expected = "Добро пожаловать";

        //when
        String result = sut.locale(country);

        //then
        Assertions.assertEquals(expected, result);

    }
}
