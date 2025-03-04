package Keerthu.project.ARS_Backend.DTO;

import Keerthu.project.ARS_Backend.Entity.Booking;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FlightDto {
    private String origin;
    private String destination;
    private int availableSeats;
    private Date arrivalTime;
    private Date departureTime;
    private String flightNumber;

    @OneToMany(mappedBy = "flight")
    private Set<Booking> bookings = new HashSet<>();
}




