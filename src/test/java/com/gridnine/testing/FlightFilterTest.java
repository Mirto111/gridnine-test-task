package com.gridnine.testing;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static com.gridnine.testing.FlightBuilder.createFlight;
import static org.junit.jupiter.api.Assertions.assertEquals;

class FlightFilterTest {

    LocalDateTime threeDaysFromNow = LocalDateTime.now().plusDays(3);
    Flight flightNormal = createFlight("A normal flight with two hour duration", threeDaysFromNow,
            threeDaysFromNow.plusHours(2));
    Flight flightNormalMulti = createFlight("A normal multi segment flight", threeDaysFromNow,
            threeDaysFromNow.plusHours(2), threeDaysFromNow.plusHours(3), threeDaysFromNow.plusHours(5));
    Flight flightDeparted = createFlight("A flight departing in the past",
            threeDaysFromNow.minusDays(6), threeDaysFromNow);
    Flight arriveLess = createFlight("A flight that departs before it arrives", threeDaysFromNow,
            threeDaysFromNow.minusHours(6));
    Flight groundTime = createFlight("A flight with more than two hours ground time",
            threeDaysFromNow, threeDaysFromNow.plusHours(2),
            threeDaysFromNow.plusHours(5), threeDaysFromNow.plusHours(6));
    Flight otherGroundTime = createFlight("Another flight with more than two hours ground time",
            threeDaysFromNow, threeDaysFromNow.plusHours(2), threeDaysFromNow.plusHours(3),
            threeDaysFromNow.plusHours(4), threeDaysFromNow.plusHours(6), threeDaysFromNow.plusHours(7));
    List<Flight> full = List.of(flightNormal, flightNormalMulti, flightDeparted, arriveLess, groundTime, otherGroundTime);

    @Test
    void excludeDepartedFlight() {
        List<Flight> expect = List.of(flightNormal, flightNormalMulti, arriveLess, groundTime, otherGroundTime);
        assertEquals(expect, FlightFilter.excludeDepartedFlight(full));
    }

    @Test
    void excludeArriveLessDepart() {
        List<Flight> expect = List.of(flightNormal, flightNormalMulti, flightDeparted, groundTime, otherGroundTime);
        assertEquals(expect, FlightFilter.excludeArriveLessDepart(full));
    }

    @Test
    void excludeGroundTimeMoreThan() {
        List<Flight> expect = List.of(flightNormal, flightNormalMulti, flightDeparted, arriveLess);
        assertEquals(expect, FlightFilter.excludeGroundTimeMoreThan(full, 2));
    }
}