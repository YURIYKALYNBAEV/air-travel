package com.gridnine.testing;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс для фильтрации списка рейсов
 */
public class FlightFilterImpl implements FlightFilter {
    private final List<Segment> segs = new ArrayList<>();
    private final LocalDateTime currentDateTime = LocalDateTime.now();
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private final List<Flight> tempListFlights = new ArrayList<>();

    /**
     * Метод отображения отправлений на текущий момент времени.
     */
    @Override
    public List<Flight> departureToTheCurrentPointInTime(List<Flight> flights) {
        for (Flight flight : flights) {
            segs.addAll(flight.getSegments());
            while (segs.size() > 0) {
                LocalDateTime dep = segs.get(0).getDepartureDate();
                LocalDateTime arr = segs.remove(0).getArrivalDate();
                if (dep.isBefore(currentDateTime)) {
                    showFlight(flight, dep, arr);
                    tempListFlights.add(flight);
                }
            }
        }
        return tempListFlights;
    }

    /**
     * Метод отображения рейсов, у которых дата прибытия предшествует дате вылета.
     */
    @Override
    public List<Flight> thereAreSegmentsWithTheArrivalDateEarlierThanTheDepartureDate(List<Flight> flights) {
        for (Flight flight : flights) {
            segs.addAll(flight.getSegments());
            while (segs.size() > 0) {
                LocalDateTime dep = segs.get(0).getDepartureDate();
                LocalDateTime arr = segs.remove(0).getArrivalDate();
                if (arr.isBefore(dep)) {
                    showFlight(flight, dep, arr);
                    tempListFlights.add(flight);
                }
            }
        }
        return tempListFlights;
    }

    /**
     * Метод отображения полетов, время нахождения на земле более двух часов.
     */
    @Override
    public List<Flight> theTotalTimeSpentOnTheGroundExceedsTwoHours(List<Flight> flights) {
        for (Flight flight : flights) {
            segs.addAll(flight.getSegments());
            if (segs.size() > 2) {
                while (segs.size() > 2) {
                    LocalDateTime arr = segs.remove(1).getArrivalDate();
                    LocalDateTime dep = segs.remove(1).getDepartureDate();
                    if (arr.plusHours(2).isBefore(dep)) {
                        showFlight(flight, dep, arr);
                        tempListFlights.add(flight);
                    }
                }
            }
        }
        return tempListFlights;
    }

    /**
     * Метод отображения всех рейсов
     */
    @Override
    public void allFlightSegments(List<Flight> flights) {
        for (Flight flight : flights) {
            for (int i = 0; i < flight.getSegments().size(); i++) {
                System.out.println(flight.getSegments().get(i));
            }
        }
    }

    /**
     * Способ отображения полетной информации (Внутри класса)
     */
    private void showFlight(Flight flight, LocalDateTime dep, LocalDateTime arr) {
        System.out.println("отправление: " + dateTimeFormatter.format(dep) + "\n"
                + "прибытие: " + dateTimeFormatter.format(arr) + "\n");
    }
}
