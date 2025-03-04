package Keerthu.project.ARS_Backend.Repository;

import Keerthu.project.ARS_Backend.Entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    Booking findByBookingNumber(String bookingNumber);
}
