package com.example.markethome.controller;

import com.example.markethome.DTO.ReceptionistDTO;
import com.example.markethome.Entities.*;
import com.example.markethome.reponsitory.*;
import com.example.markethome.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/receptionist")
@CrossOrigin(origins = "*")
public class ReceptionistAPI {

    @Autowired
    private ServiceRepository serviceRepository;
    @Autowired
    private userRepository userReponsitory;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private BookingDetailRepository bookingDetailRepository;
    @Autowired
    private BookingService bookingService;
    private final SimpMessagingTemplate messagingTemplate;

    public ReceptionistAPI(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }



    @RolesAllowed("ROLE_RECEPTIONIST")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid ReceptionistDTO receptionistDTO) {
        List<BookingDetail> bookingDetails = new ArrayList<>();

        User receptionist = userReponsitory.findById(receptionistDTO.getIdReceptionist()).get();
        User user = new User();
        boolean checkUser = false;
        if (userReponsitory.existsByPhone(receptionistDTO.getPhone())) {
            checkUser = true;
            user = userReponsitory.findByPhone(receptionistDTO.getPhone()).get();
        }
        for (int i = 0; i < receptionistDTO.getServiceId().size(); i++) {
            if (checkUser == true && serviceRepository.existsById(receptionistDTO.getServiceId().get(i)) && serviceRepository.getById(receptionistDTO.getServiceId().get(i)).getStatus() == 1) {
                BookingDetail bookingDetail = new BookingDetail();
                bookingDetail.setStatus(1);
                bookingDetail.setUser(user);
                bookingDetail.setService(serviceRepository.findServiceById(receptionistDTO.getServiceId().get(i)));
                bookingDetails.add(bookingDetail);
            }
        }
        Booking booking = new Booking();
        booking.setTotalPrice(receptionistDTO.getTotal());
        booking.setStatus(0);
        if (checkUser) {
            booking.setUser(user);
        }
        booking.setPayment(0);
        booking.setDate(receptionistDTO.getDate());
        Booking newBooking1 = bookingRepository.save(booking);
        if (checkUser == true) {
            for (BookingDetail b : bookingDetails
            ) {
                b.setBooking(newBooking1);
                bookingDetailRepository.save(b);
            }
        }
        return ResponseEntity.ok("done");
    }
    @GetMapping("/booking/{phone}")
    public ResponseEntity<?> getBookingForReceptionist(@PathVariable String phone) {
      List<Booking> list = bookingRepository.findAcept();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/ser")
    public ResponseEntity<?> getSerForReceptionist() {
        List<Service> list = serviceRepository.findAllL();
        return ResponseEntity.ok().body(list);
    }
    @RolesAllowed("ROLE_ADMIN")
    @GetMapping("/acceptSer/{id}")
    public ResponseEntity<?> acceptSer(@PathVariable int id) {
        Service s = serviceRepository.findServiceById(id);
        s.setStatus(0);
        serviceRepository.save(s);
        return ResponseEntity.ok().body(s.getId());
    }
    @RolesAllowed("ROLE_ADMIN")
    @GetMapping("/remove/{id}")
    public ResponseEntity<?> removeSer(@PathVariable int id) {
        Service s = serviceRepository.findServiceById(id);
        s.setStatus(3);
        serviceRepository.save(s);
        return ResponseEntity.ok().body(s.getId());
    }
    @GetMapping("/restore/{id}")
    public ResponseEntity<?> restoreSer(@PathVariable int id) {
        Service s = serviceRepository.findServiceById(id);
        s.setStatus(1);
        serviceRepository.save(s);
        return ResponseEntity.ok().body(s.getId());
    }
}
