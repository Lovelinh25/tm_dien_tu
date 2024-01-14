package com.example.markethome.service;

import com.example.markethome.DTO.*;
import com.example.markethome.Entities.Booking;
import com.example.markethome.Entities.User;
import com.example.markethome.reponsitory.BookingRepository;
import com.example.markethome.reponsitory.userRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;

    @Autowired
    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Autowired
    private userRepository userRepository;

    public List<BookingHistoryDTO> getUserBookingHistoryByPhone(String phone) {
        User u = userRepository.getByPhone(phone);
        List<Booking> bookings = bookingRepository.findByUserPhone(u.getId());
        return bookings.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<BookingHistoryDTO> getAllBookings() {
        List<Booking> bookings = bookingRepository.findAll();
        return bookings.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<BookingHistoryDTO> getAllBookings2(int branch) {
        List<Booking> bookings = bookingRepository.findAllWithNotRemoveWithBranch(branch);
        return bookings.stream().map(this::convertToDTO2).collect(Collectors.toList());
    }

    private BookingHistoryDTO convertToDTO(Booking booking) {
        BookingHistoryDTO bookingHistoryDTO = new BookingHistoryDTO();
        bookingHistoryDTO.setId(booking.getID());
        bookingHistoryDTO.setDate(booking.getDate());
        bookingHistoryDTO.setStatus(booking.getStatus());
        bookingHistoryDTO.setPayment(booking.getPayment());
        bookingHistoryDTO.setTotalPrice(booking.getTotalPrice());

        // Convert User entity to UserDTO

        if (booking.getUser() != null) {
            userDTO userDTO = new userDTO();
            userDTO.setId(booking.getUser().getId());
            userDTO.setPhone(booking.getUser().getPhone());

            bookingHistoryDTO.setUser(userDTO);
        }
        // Convert Branch entity to BranchDTO
        BranchDTO branchDTO = new BranchDTO();



        bookingHistoryDTO.setBranch(branchDTO);

        SetTime setTime = new SetTime();
        return bookingHistoryDTO;
    }

    private BookingHistoryDTO convertToDTO2(Booking booking) {
        BookingHistoryDTO bookingHistoryDTO = new BookingHistoryDTO();
        bookingHistoryDTO.setId(booking.getID());
        bookingHistoryDTO.setDate(booking.getDate());

        bookingHistoryDTO.setStatus(booking.getStatus());
        bookingHistoryDTO.setPayment(booking.getPayment());
        bookingHistoryDTO.setTotalPrice(booking.getTotalPrice());

        // Convert User entity to UserDTO

        if (booking.getUser() != null) {
            userDTO userDTO = new userDTO();
            userDTO.setId(booking.getUser().getId());
            userDTO.setPhone(booking.getUser().getName());

            bookingHistoryDTO.setUser(userDTO);
        }
        // Convert Branch entity to BranchDTO
        BranchDTO branchDTO = new BranchDTO();


        bookingHistoryDTO.setBranch(branchDTO);

        SetTime setTime = new SetTime();

        return bookingHistoryDTO;
    }
}
