import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.i18n.geo.GeoService;
import ru.netology.i18n.LocalizationService;
import ru.netology.i18n.geo.GeoServiceImpl;
import ru.netology.sender.MessageSenderImpl;

import java.util.*;
import java.util.stream.Stream;

public class MessageSenderTests {

    static MessageSenderImpl sut;

    @BeforeAll
    public static void start() {
        System.out.println("Start tests");
    }

    @BeforeEach
    public void init() {
        GeoService geoService = Mockito.mock(GeoService.class);
        Mockito.when(geoService.byIp(Mockito.startsWith("172.")))
                .thenReturn(new Location("Moscow", Country.RUSSIA, null, 0));
        Mockito.when(geoService.byIp(Mockito.startsWith("96.")))
                .thenReturn(new Location("New York", Country.USA, null,  0));

        LocalizationService localizationService = Mockito.mock(LocalizationService.class);
        Mockito.when(localizationService.locale(Country.RUSSIA))
                .thenReturn("Добро пожаловать");
        Mockito.when(localizationService.locale(Country.USA))
                .thenReturn("Wellcome");

        sut = new MessageSenderImpl(geoService, localizationService);
    }

    @AfterAll
    public static void finish() {
        System.out.printf("\nTests finished");
    }

    @ParameterizedTest
    @MethodSource("source")
    public void sendTest (String ip, String expected) {

        Map<String, String> headers = new HashMap<String, String>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, ip);
        String result = sut.send(headers);

        Assertions.assertEquals(expected, result);

    }

    private static Stream<Arguments> source() {
        return Stream.of(Arguments.of("172.0.32.11", "Добро пожаловать"), Arguments.of("96.0.32.11", "Wellcome"));
    }

    @org.junit.jupiter.api.Test
    public void byCoordinatesTest () {
        //given:
        double latitude=0, longitude=0;
        GeoService geo = new GeoServiceImpl();

        //when
        var expected = RuntimeException.class;

        //then
        Assertions.assertThrows( expected, () -> geo.byCoordinates( latitude,  longitude));
    }

}
