import com.gridnine.testing.Flight;
import com.gridnine.testing.FlightFilter;
import com.gridnine.testing.FlightFilterImpl;
import com.gridnine.testing.Segment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Класс для тестирования
 */

public class FlightFilterImplTest {

    private final List<Flight> flights = new ArrayList<>();
    private final FlightFilter flightFilter = new FlightFilterImpl();
    LocalDateTime time = LocalDateTime.now();

    @Test
    @DisplayName("Проверка метода отображения отклонения для текущего времени.")
    public void departureToTheCurrentPointInTimeTest() {
        Segment segment = new Segment(LocalDateTime.now().minusHours(5),LocalDateTime.now());
        List<Segment> segments = new ArrayList<>();
        segments.add(segment);
        List<Flight> filteredFlights = flightFilter.departureToTheCurrentPointInTime(flights);
        assertTrue(filteredFlights.isEmpty());
    }

    @Test
    @DisplayName("Тест метода отображения рейсов с датой прибытия раньше даты вылета. ")
    public void thereAreSegmentsWithTheArrivalDateEarlierThanTheDepartureDateTest() {
        Segment segment = new Segment(LocalDateTime.now(),LocalDateTime.now().minusHours(1));
        List<Segment> segments = new ArrayList<>();
        segments.add(segment);
        List<Flight> filteredFlights = flightFilter.thereAreSegmentsWithTheArrivalDateEarlierThanTheDepartureDate(flights);
        assertTrue(filteredFlights.isEmpty());
    }

    @Test
    @DisplayName("Проверка метода фильтрации рейсов с общим временем нахождения на земле более двух часов")
    public void filterFlightsWithTotalTimeOnGroundExceedingTwoHours() {

        Segment segment1 = new Segment(time.plusHours(2), time);
        Segment segment2 = new Segment(time.plusHours(2).plusMinutes(2), time.plusHours(6));
        List<Segment> segments = new ArrayList<>();
        segments.add(segment1);
        segments.add(segment2);
        Flight flight = new Flight(segments);
        List<Flight> flights = new ArrayList<>();
        flights.add(flight);
        List<Flight> filteredFlights = flightFilter.theTotalTimeSpentOnTheGroundExceedsTwoHours(flights);
        assertTrue(filteredFlights.isEmpty());
    }
}