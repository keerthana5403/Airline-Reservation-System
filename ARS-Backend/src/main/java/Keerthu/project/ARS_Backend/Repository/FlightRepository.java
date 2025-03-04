package Keerthu.project.ARS_Backend.Repository;


import Keerthu.project.ARS_Backend.Entity.Airport;
import Keerthu.project.ARS_Backend.Entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {
    List<Flight> findByDepartureAirport(Airport addedAirport);

    Flight findByFlightNumber(String flightNumber);
}




