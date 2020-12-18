package com.gridnine.testing;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        List<Flight> flightList = FlightBuilder.createFlights();
        List<Flight> filterBefore = FlightFilter.excludeDepartedFlight(flightList);
        System.out.println();
        System.out.println("Без фильтрации");
        System.out.println();
        flightList.forEach(System.out::println);
        System.out.println();
        System.out.println("Исключены вылеты до текущего момента времени");
        System.out.println();
        filterBefore.forEach(System.out::println);
        List<Flight> filterArriveLessDepart = FlightFilter.excludeArriveLessDepart(flightList);
        System.out.println();
        System.out.println("Исключены полеты с датой прилёта раньше даты вылета");
        System.out.println();
        filterArriveLessDepart.forEach(System.out::println);
        List<Flight> filterGround = FlightFilter.excludeGroundTimeMoreThan(flightList, 2);
        System.out.println();
        System.out.println("Исключены полеты чье общее время, проведённое на земле превышает два часа");
        System.out.println();
        filterGround.forEach(System.out::println);
    }
}
