package Keerthu.project.ARS_Backend.Service;
import Keerthu.project.ARS_Backend.DTO.FlightDto;
import Keerthu.project.ARS_Backend.Entity.Airport;
import Keerthu.project.ARS_Backend.Entity.Flight;
import Keerthu.project.ARS_Backend.Handler.ResourceNotFoundException;
import Keerthu.project.ARS_Backend.Repository.AirportRepository;
import Keerthu.project.ARS_Backend.Repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date; // ✅ Import java.sql.Date explicitly
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FlightService {

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private AirportRepository airportRepository;

    public void addFlight(FlightDto flightDto, String code) {
        Optional<Airport> airportOptional = airportRepository.findByCode(code);
        if (airportOptional.isEmpty()) {
            throw new ResourceNotFoundException("Resource not found");
        }

        Flight flight = new Flight();
        flight.setOrigin(flightDto.getOrigin());
        flight.setDestination(flightDto.getDestination());
        flight.setAvailableSeats(flightDto.getAvailableSeats());

        // ✅ Fix type conversion
        flight.setArrivalTime(new Date(flightDto.getArrivalTime().getTime()));
        flight.setDepartureTime(new Date(flightDto.getDepartureTime().getTime()));

        flight.setFlightNumber(UUID.randomUUID().toString());
        flight.setDepartureAirport(airportOptional.get());

        flightRepository.save(flight);
    }

    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }

    public Flight getFlightById(Long id) {
        return flightRepository.findById(id).orElse(null);
    }

    public void updateFlight(Long id, FlightDto updatedFlightDto) {
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource Not Found"));

        flight.setAvailableSeats(updatedFlightDto.getAvailableSeats());
        flight.setOrigin(updatedFlightDto.getOrigin());
        flight.setDestination(updatedFlightDto.getDestination());

        // ✅ Fix type conversion
        flight.setArrivalTime(new Date(updatedFlightDto.getArrivalTime().getTime()));
        flight.setDepartureTime(new Date(updatedFlightDto.getDepartureTime().getTime()));

        flightRepository.save(flight);
    }

    public void deleteFlight(Long id) {
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource Not Found"));

        flightRepository.deleteById(id);
    }

    public List<FlightDto> getFlightByAirport(Airport addedAirport) {
        List<Flight> flights = flightRepository.findByDepartureAirport(addedAirport);
        return flights.stream().map(this::mapToFlightDTO).toList();
    }

    private FlightDto mapToFlightDTO(Flight flight) {
        FlightDto flightDto = new FlightDto();
        flightDto.setOrigin(flight.getOrigin());
        flightDto.setDestination(flight.getDestination());

        // ✅ Fix type conversion
        flightDto.setArrivalTime(new Date(flight.getArrivalTime().getTime()));
        flightDto.setDepartureTime(new Date(flight.getDepartureTime().getTime()));

        flightDto.setBookings(flight.getBookings());
        flightDto.setFlightNumber(flight.getFlightNumber());
        flightDto.setAvailableSeats(flight.getAvailableSeats());
        return flightDto;
    }

    public void updateFlightWithFlightNumber(FlightDto updatedFlightDto, String flightNumber) {
        Flight flight = flightRepository.findByFlightNumber(flightNumber);

        flight.setAvailableSeats(updatedFlightDto.getAvailableSeats());
        flight.setOrigin(updatedFlightDto.getOrigin());
        flight.setDestination(updatedFlightDto.getDestination());

        // ✅ Fix type conversion
        flight.setArrivalTime(new Date(updatedFlightDto.getArrivalTime().getTime()));
        flight.setDepartureTime(new Date(updatedFlightDto.getDepartureTime().getTime()));

        flightRepository.save(flight);
    }

    public Flight getFlightByFlightNumber(String flightNumber) {
        return flightRepository.findByFlightNumber(flightNumber);
    }

    public void removeFlightFromAirport(String flightNumber) {
        Flight flight = flightRepository.findByFlightNumber(flightNumber);

        if (flight != null) {
            Airport airport = flight.getDepartureAirport();
            if (airport != null) {
                airport.getDepartingFlights().remove(flight);
                airportRepository.save(airport);
            }
            flightRepository.delete(flight);
        } else {
            throw new ResourceNotFoundException("Resource Not Found");
        }
    }
} // ✅ Ensure the class has a closing brace
