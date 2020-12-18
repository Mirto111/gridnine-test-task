package com.gridnine.testing;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Factory class to get sample list of flights.
 */
class FlightBuilder {
    static List<Flight> createFlights() {
        LocalDateTime threeDaysFromNow = LocalDateTime.now().plusDays(3);
        return Arrays.asList(
            //A normal flight with two hour duration
            createFlight("A normal flight with two hour duration",threeDaysFromNow, threeDaysFromNow.plusHours(2)),
            //A normal multi segment flight
            createFlight("A normal multi segment flight", threeDaysFromNow, threeDaysFromNow.plusHours(2),
                threeDaysFromNow.plusHours(3), threeDaysFromNow.plusHours(5)),
            //A flight departing in the past
            createFlight("A flight departing in the past", threeDaysFromNow.minusDays(6), threeDaysFromNow),
            //A flight that departs before it arrives
            createFlight("A flight that departs before it arrives", threeDaysFromNow, threeDaysFromNow.minusHours(6)),
            //A flight with more than two hours ground time
            createFlight("A flight with more than two hours ground time", threeDaysFromNow, threeDaysFromNow.plusHours(2),
                threeDaysFromNow.plusHours(5), threeDaysFromNow.plusHours(6)),
            //Another flight with more than two hours ground time
            createFlight("Another flight with more than two hours ground time", threeDaysFromNow, threeDaysFromNow.plusHours(2),
                threeDaysFromNow.plusHours(3), threeDaysFromNow.plusHours(4),
                threeDaysFromNow.plusHours(6), threeDaysFromNow.plusHours(7)));
    }

    private static Flight createFlight(final String name, final LocalDateTime... dates) {
        if ((dates.length % 2) != 0) {
            throw new IllegalArgumentException(
                "you must pass an even number of dates");
        }
        List<Segment> segments = new ArrayList<>(dates.length / 2);
        for (int i = 0; i < (dates.length - 1); i += 2) {
            segments.add(new Segment(dates[i], dates[i + 1]));
        }
        return new Flight(name, segments);
    }
}

/**
 * Bean that represents a flight.
 */
class Flight {
    //для тестов
    private final String name;

    private final List<Segment> segments;

    Flight(String name, List<Segment> segments) {
        this.name = name;
        this.segments = segments;
    }

    List<Segment> getSegments() {
        return segments;
    }

    @Override
    public String toString() {
        return name + " - " + segments.stream().map(Object::toString)
            .collect(Collectors.joining(" "));
    }
}

/**
 * Bean that represents a flight segment.
 */
class Segment {
    private final LocalDateTime departureDate;

    private final LocalDateTime arrivalDate;

    Segment(final LocalDateTime dep, final LocalDateTime arr) {
        departureDate = Objects.requireNonNull(dep);
        arrivalDate = Objects.requireNonNull(arr);
    }

    LocalDateTime getDepartureDate() {
        return departureDate;
    }

    LocalDateTime getArrivalDate() {
        return arrivalDate;
    }

    @Override
    public String toString() {
        DateTimeFormatter fmt =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        return '[' + departureDate.format(fmt) + '|' + arrivalDate.format(fmt)
            + ']';
    }
}