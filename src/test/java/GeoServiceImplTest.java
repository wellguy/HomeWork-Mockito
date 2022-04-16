import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.i18n.geo.GeoServiceImpl;

import java.util.stream.Stream;

public class GeoServiceImplTest {
    GeoServiceImpl sut;

    @BeforeEach
    public void init() {
        sut = new GeoServiceImpl();
    }

    @BeforeAll
    public static void start() {
        System.out.println("Start tests");
    }

    @AfterAll
    public static void finish() {
        System.out.printf("Tests finished");
    }

    @ParameterizedTest
    @MethodSource("source")
    public void byIPtest(String ip, Location expected) {
        //given

        //when
        Location result = sut.byIp(ip);

        //then
        Assertions.assertEquals(expected.getCity(), result.getCity());
        Assertions.assertEquals(expected.getCountry(), result.getCountry());
        Assertions.assertEquals(expected.getStreet(), result.getStreet());
        Assertions.assertEquals(expected.getBuiling(), result.getBuiling());

    }

    private static Stream<Arguments> source() {
        return Stream.of(
                Arguments.of("127.0.0.1", new Location(null, null, null, 0)),
                Arguments.of("172.0.32.11", new Location("Moscow", Country.RUSSIA, "Lenina", 15)),
                Arguments.of("96.44.183.149", new Location("New York", Country.USA, " 10th Avenue", 32)),
                Arguments.of("172.44.183.149", new Location("Moscow", Country.RUSSIA, null, 0)),
                Arguments.of("96.44.78.149", new Location("New York", Country.USA, null,  0)));
    }

    @org.junit.jupiter.api.Test
    public void byCoordinatesTest () {
        //given:
        double latitude=0, longitude=0;

        //when
        var expected = RuntimeException.class;

        //then
        Assertions.assertThrows( expected, () -> sut.byCoordinates( latitude,  longitude));
    }



}
