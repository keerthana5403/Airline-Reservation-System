package Keerthu.project.ARS_Backend.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AirportWithFlightsDto {
    private String name;
    private String location;
    private String code;
    private List<FlightDto> departingFlights;
}
