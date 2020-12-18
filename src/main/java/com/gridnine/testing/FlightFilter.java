package com.gridnine.testing;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Class for filter flights.
 */
public class FlightFilter {

    public static List<Flight> excludeDepartedFlight(List<Flight> flights) {
        return checkFlight(flights, s -> s.getDepartureDate().isBefore(LocalDateTime.now()));
    }

    public static List<Flight> excludeArriveLessDepart(List<Flight> flights) {
        return checkFlight(flights, s -> s.getArrivalDate().isBefore(s.getDepartureDate()));
    }

    public static List<Flight> excludeGroundTimeMoreThan(List<Flight> flights, int hours) {
        List<Flight> list = new ArrayList<>();
        for (Flight flight : flights) {
            List<Segment> segments = flight.getSegments();
            long groundHours = 0;
            for (int j = 0; j < segments.size() - 1; j++) {
                groundHours += segments.get(j).getArrivalDate().until(segments.get(j + 1).getDepartureDate(), ChronoUnit.HOURS);
            }
            if (groundHours < hours) {
                list.add(flight);
            }
        }
        return list;
    }

    private static List<Flight> checkFlight(List<Flight> flights, Predicate<Segment> check) {
        List<Flight> list = new ArrayList<>();
        for (Flight flight : flights) {
            List<Segment> segments = flight.getSegments();
            for (Segment segment : segments) {
                if (!check.test(segment)) {
                    list.add(flight);
                    break;
                }
            }
        }
        return list;
    }
}
