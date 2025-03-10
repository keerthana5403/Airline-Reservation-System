package Keerthu.project.ARS_Backend.Controller;


import Keerthu.project.ARS_Backend.DTO.AirportDto;
import Keerthu.project.ARS_Backend.DTO.AirportWithFlightsDto;
import Keerthu.project.ARS_Backend.DTO.FlightDto;
import Keerthu.project.ARS_Backend.Entity.Airport;
import Keerthu.project.ARS_Backend.Service.AirportService;
import Keerthu.project.ARS_Backend.Service.FlightService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/airports")
@Slf4j
@CrossOrigin("*")
public class AirportController {

    @Autowired
    private AirportService airportService;

    @Autowired
    private FlightService flightService;


    @PostMapping("/add")
    public ResponseEntity<AirportWithFlightsDto> addAirport(@RequestBody AirportDto airportDto) {
        Airport addedAirport = airportService.addAirport(airportDto);

        List<FlightDto> flights = flightService.getFlightByAirport(addedAirport);

        log.info("{}", flights);
        AirportWithFlightsDto airport = new AirportWithFlightsDto();
        airport.setName(addedAirport.getName());
        airport.setCode(addedAirport.getCode());
        airport.setLocation(addedAirport.getLocation());
        airport.setDepartingFlights(flights);


        return new ResponseEntity<>(airport, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Airport> getAirportById(@PathVariable Long id) {
        Airport airport = airportService.getAirportById(id);
        if (airport == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(airport);
    }

    @GetMapping("/all")
    public ResponseEntity<List<AirportWithFlightsDto>> getAllAirportsWithFlights() {
        List<Airport> airports = airportService.getAllAirports();
        List<AirportWithFlightsDto> airportDtos = new ArrayList<>();

        for (Airport airport : airports) {
            List<FlightDto> flights = flightService.getFlightByAirport(airport);

            AirportWithFlightsDto airportDto = new AirportWithFlightsDto();
            airportDto.setName(airport.getName());
            airportDto.setCode(airport.getCode());
            airportDto.setLocation(airport.getLocation());
            airportDto.setDepartingFlights(flights);

            airportDtos.add(airportDto);
        }

        return ResponseEntity.ok(airportDtos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Airport> updateAirport(@PathVariable Long id, @RequestBody AirportDto updatedAirport) {
        Airport airport = airportService.updateAirport(id, updatedAirport);
        if (airport == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(airport);
    }
    @PutMapping("/update")
    public ResponseEntity<Airport> updateAirport(@RequestBody AirportDto updatedAirport, @RequestParam String code) {
        Airport airport = airportService.updateAirportByCode(code, updatedAirport);
        log.info("Airport value{}",airport);
        if (airport == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(airport);
    }

    @GetMapping("/get/{code}")
    public ResponseEntity<Airport> getAirportByCode(@PathVariable String code){
        Airport airport = airportService.getAirportByCode(code);
        return ResponseEntity.ok(airport);
    }

    @DeleteMapping("/{airportCode}")
    @Transactional
    public ResponseEntity<String> deleteAirport(@PathVariable String airportCode) {
        airportService.deleteAirportByAirportCode(airportCode);
        return ResponseEntity.ok("Airport deleted successfully.");
    }

    @DeleteMapping("/delete-all")
    public ResponseEntity<String> deleteAllAirport() {
        airportService.deleteAllAirport();
        return ResponseEntity.ok("Airports deleted successfully.");
    }

}

