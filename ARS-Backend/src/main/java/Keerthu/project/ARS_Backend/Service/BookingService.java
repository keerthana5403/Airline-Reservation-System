package Keerthu.project.ARS_Backend.Service;

import Keerthu.project.ARS_Backend.DTO.BookingDto;
import Keerthu.project.ARS_Backend.DTO.BookingDtoResponse;
import Keerthu.project.ARS_Backend.Entity.Airport;
import Keerthu.project.ARS_Backend.Entity.Booking;
import Keerthu.project.ARS_Backend.Entity.Flight;
import Keerthu.project.ARS_Backend.Handler.InvalidCancellationException;
import Keerthu.project.ARS_Backend.Handler.ResourceNotFoundException;
import Keerthu.project.ARS_Backend.Repository.BookingRepository;
import Keerthu.project.ARS_Backend.Repository.FlightRepository;
import Keerthu.project.ARS_Backend.Util.BookingStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private FlightRepository flightRepository;

    public String createBooking(BookingDto bookingDto) {


        Booking booking = new Booking();
        booking.setBookingNumber(UUID.randomUUID().toString());

        Flight flight = flightRepository.findByFlightNumber(bookingDto.getFlightNumber());
        Airport airport = flight.getDepartureAirport();
        booking.setFlight(flight);
        booking.setBookingDate(new Date());
        booking.setDepartureAirport(airport);
        booking.setBookingStatus(BookingStatus.RESERVED);

        bookingRepository.save(booking);

        return "Booked Successfully";
    }

    public List<BookingDtoResponse> getAllBookings() {
        List<Booking> bookings = bookingRepository.findAll();
        List<BookingDtoResponse> bookingDtoResponses = bookings.stream().map(this::mapToBookingDtoResponse).toList();
        return bookingDtoResponses;
    }

    private BookingDtoResponse mapToBookingDtoResponse(Booking booking) {
        BookingDtoResponse bookingDtoResponse = new BookingDtoResponse();
        bookingDtoResponse.setAirportCode(booking.getDepartureAirport().getCode());
        bookingDtoResponse.setFlightNumber(booking.getFlight().getFlightNumber());
        bookingDtoResponse.setOrigin(booking.getFlight().getOrigin());
        bookingDtoResponse.setDestination(booking.getFlight().getDestination());
        bookingDtoResponse.setArrivalTime(booking.getFlight().getArrivalTime());
        bookingDtoResponse.setDepartureTime(booking.getFlight().getDepartureTime());
        bookingDtoResponse.setStatus(String.valueOf(booking.getBookingStatus()));
        bookingDtoResponse.setBookingDate(booking.getBookingDate());
        bookingDtoResponse.setBookingNumber(booking.getBookingNumber());
        return bookingDtoResponse;
    }

    public void cancelBooking(String bookingNumber) {
        Booking booking = bookingRepository.findByBookingNumber(bookingNumber);
        if(!booking.getBookingStatus().equals(BookingStatus.RESERVED)){
            throw new InvalidCancellationException("Flight Cannot be cancelled");
        }
        booking.setBookingStatus(BookingStatus.CANCELED);
        bookingRepository.save(booking);
    }

    public void deleteBooking(String bookingNumber) {
        Booking booking = bookingRepository.findByBookingNumber(bookingNumber);
        if(booking != null && booking.getBookingStatus().equals(BookingStatus.CANCELED)){
            bookingRepository.delete(booking);
        }else {
            throw new ResourceNotFoundException("Resource Not Found");
        }
    }
}








