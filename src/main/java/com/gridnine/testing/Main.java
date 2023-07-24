package com.gridnine.testing;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        List<Flight> flights = FlightBuilder.createFlights();
        FlightFilterImpl flightFilter = new FlightFilterImpl();

        System.out.println("Все рейсы:");
        flightFilter.allFlightSegments(flights);
        System.out.println("-----------------------------------");

        System.out.println("Рейсы с вылетом раньше текущего времени:");
        flightFilter.departureToTheCurrentPointInTime(flights);

        System.out.println("Сегменты рейсов, дата прибытия которых предшествует дате вылета:");
        flightFilter.thereAreSegmentsWithTheArrivalDateEarlierThanTheDepartureDate(flights);

        System.out.println("Полеты, в которых общее время, проведённое на земле превышает два часа:");
        flightFilter.theTotalTimeSpentOnTheGroundExceedsTwoHours(flights);
    }
}