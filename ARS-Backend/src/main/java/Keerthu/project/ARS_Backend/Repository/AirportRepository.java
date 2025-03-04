package Keerthu.project.ARS_Backend.Repository;


import Keerthu.project.ARS_Backend.Entity.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AirportRepository extends JpaRepository<Airport, Long> {

    Optional<Airport> findByCode(String code);

    void deleteByCode(String airportCode);

}
