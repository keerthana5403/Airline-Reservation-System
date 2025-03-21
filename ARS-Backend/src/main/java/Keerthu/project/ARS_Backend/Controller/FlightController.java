package Keerthu.project.ARS_Backend.Controller;


import Keerthu.project.ARS_Backend.DTO.FlightDto;
import Keerthu.project.ARS_Backend.Entity.Flight;
import Keerthu.project.ARS_Backend.Service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/flights")
@CrossOrigin("*")
public class FlightController {

    @Autowired
    private FlightService flightService;

    @PostMapping("/add")
    public ResponseEntity<String> addFlight(@RequestBody FlightDto flightDto, @RequestParam String code){
        flightService.addFlight(flightDto, code);
        return new ResponseEntity<>("Flight added successfully", HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Flight>> getAllFlights() {
        List<Flight> flights = flightService.getAllFlights();
        return ResponseEntity.ok(flights);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Flight> getFlightById(@PathVariable Long id) {
        Flight flight = flightService.getFlightById(id);
        if (flight == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(flight);
    }
    @GetMapping("/get/{flightNumber}")
    public ResponseEntity<Flight> getFlightById(@PathVariable String flightNumber) {
        Flight flight = flightService.getFlightByFlightNumber(flightNumber);
        if (flight == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(flight);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateFlight(@PathVariable Long id, @RequestBody FlightDto updatedFlightDto) {
        flightService.updateFlight(id, updatedFlightDto);
        return ResponseEntity.ok("Flight updated successfully.");
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateFlight(@RequestBody FlightDto updatedFlightDto, @RequestParam String flightNumber) {
        flightService.updateFlightWithFlightNumber(updatedFlightDto, flightNumber);
        return ResponseEntity.ok("Flight updated successfully.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFlight(@PathVariable Long id) {
        flightService.deleteFlight(id);
        return ResponseEntity.ok("Deleted Successfully");
    }

    @DeleteMapping("/delete/{flightNumber}")
    public ResponseEntity<String> removeFlightFromAirport(@PathVariable String flightNumber) {
        flightService.removeFlightFromAirport(flightNumber);
        return ResponseEntity.ok("Flight removed from airport successfully");
    }
}