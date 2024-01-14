package com.example.markethome.controller;

import com.example.markethome.DTO.BookingHistoryDTO;
import com.example.markethome.Entities.Booking;
import com.example.markethome.reponsitory.BookingRepository;
import com.example.markethome.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("/bookings")
@CrossOrigin(origins = "*")
public class HistoryBookingApi {
    @Autowired BookingService bookingService;
    @Autowired
    BookingRepository bookingRepository;

    @GetMapping("/history/{phone}")
    public List<BookingHistoryDTO> getUserBookingHistoryByPhone(@PathVariable String phone) {
        return bookingService.getUserBookingHistoryByPhone(phone);
    }

    @RolesAllowed("ROLE_ADMIN")
    @GetMapping("/history/all")
    public List<BookingHistoryDTO> getAllBookings() {
        return bookingService.getAllBookings();
    }
    @GetMapping("/delete/{id}")
    public ResponseEntity<?> remove(@PathVariable int id) {
        Booking b= bookingRepository.findBookingByID(id);
        b.setStatus(3);
        bookingRepository.save(b);
        return ResponseEntity.ok().body(id);
    }
}