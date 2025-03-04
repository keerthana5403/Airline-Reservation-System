package Keerthu.project.ARS_Backend;

import Keerthu.project.ARS_Backend.Entity.Airport;
import Keerthu.project.ARS_Backend.Entity.Flight;
import Keerthu.project.ARS_Backend.Repository.AirportRepository;
import Keerthu.project.ARS_Backend.Repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.*;

@SpringBootApplication
@CrossOrigin
public class AirlineReservationSystemApplication implements CommandLineRunner {

    @Autowired
    private AirportRepository airportRepository;

    @Autowired
    private FlightRepository flightRepository;

    @Transactional
    public void sendAirportsWithFlights() {
        Airport jfkAirport = new Airport("JFK", "John F. Kennedy International Airport", "New York");
        Airport laxAirport = new Airport("LAX", "Los Angeles International Airport", "Los Angeles");
        Airport ordAirport = new Airport("ORD", "O'Hare International Airport", "Chicago");

        Flight jfkToLaxFlight = createFlight("New York", "Los Angeles", 200, jfkAirport);
        Flight jfkToChicagoFlight = createFlight("New York", "Chicago", 180, jfkAirport);
        Flight laxToChicagoFlight = createFlight( "Los Angeles", "Chicago", 150, laxAirport);

        airportRepository.deleteAll();
        airportRepository.saveAll(Arrays.asList(jfkAirport, laxAirport, ordAirport));
        flightRepository.saveAll(Arrays.asList(jfkToLaxFlight, jfkToChicagoFlight, laxToChicagoFlight));
    }

    private Flight createFlight(String origin, String destination, int availableSeats, Airport airport) {
        Calendar calendar = new GregorianCalendar();
        Date now = new Date(System.currentTimeMillis());  // Fix: Use System.currentTimeMillis()
        calendar.setTime(now);

        Date arrivalTime = new Date(calendar.getTimeInMillis());  // Fix: Convert properly
        calendar.add(Calendar.HOUR_OF_DAY, 2);
        Date departureTime = new Date(calendar.getTimeInMillis());  // Fix: Convert properly

        String flightNumber = UUID.randomUUID().toString();

        return new Flight(flightNumber, origin, destination, availableSeats, arrivalTime, departureTime, airport);
    }



    public static void main(String[] args) {
        SpringApplication.run(AirlineReservationSystemApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        sendAirportsWithFlights();
    }
}

