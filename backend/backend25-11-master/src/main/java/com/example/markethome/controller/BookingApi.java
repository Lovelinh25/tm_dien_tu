package com.example.markethome.controller;

import com.example.markethome.DTO.BookDTO;
import com.example.markethome.DTO.CartDTO;
import com.example.markethome.Entities.*;
import com.example.markethome.reponsitory.*;
import com.example.markethome.service.BookingService;
import com.example.markethome.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/booking")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class BookingApi {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private ServiceRepository serviceRepository;
    @Autowired
    private BookingDetailRepository bookingDetailRepository;
    @Autowired
    private BookingService bookingService;
    @Autowired
    private userRepository userRepository;

    @PostMapping("/addToCart")
    public ResponseEntity<?> addToCart(@RequestBody CartDTO cartDTO) {
        User user = userService.findByPhone(cartDTO.getPhone());
        BookingDetail bookingDetail = new BookingDetail();
//        bookingDetail.setBooking();
        List<BookingDetail> listd = bookingDetailRepository.existService(user.getId(), cartDTO.getServiceID());
        Service service = serviceRepository.findServiceById(cartDTO.getServiceID());
        Booking booking = bookingRepository.findBookingByID(2);
        bookingDetail.setBooking(booking);
        bookingDetail.setService(service);
        bookingDetail.setUser(user);
        bookingDetail.setStatus(0);
        if (listd.size() == 0) {
            BookingDetail B = bookingDetailRepository.save(bookingDetail);
            System.out.println("ok ");
            return ResponseEntity.ok("ok");
        } else {
            return ResponseEntity.ok("notok");
        }

    }

    @PostMapping("/listCart")
    public ResponseEntity<?> listCart(@RequestBody CartDTO cartDTO) {
        User user = userService.findByPhone(cartDTO.getPhone());
        List<BookingDetail> list = bookingDetailRepository.findByBookingId(user.getId());
        System.out.println(list);
        List<Service> list1 = new ArrayList<>();
        for (BookingDetail a : list
        ) {
            System.out.println(a);
            list1.add(a.getService());
        }
        for (Service b : list1
        ) {
            System.out.println(b);
        }
        return ResponseEntity.ok().body(list1);
    }
    @GetMapping("/accept/{id}")
    public ResponseEntity<?> accept(@PathVariable int id )
    {
        Booking b = bookingRepository.findBookingByID(id);
        b.setStatus(2);
        bookingRepository.save(b);
        return ResponseEntity.ok().body(b.getID());
    }

    @PostMapping("/deleteDetail")
    public void deleteDetail(@RequestBody CartDTO cartDTO) {
        User user = userService.findByPhone(cartDTO.getPhone());
        BookingDetail b = bookingDetailRepository.exist(user.getId(), cartDTO.getServiceID());
        System.out.println(cartDTO.getServiceID());
        System.out.println(user.getId());
        System.out.println(b);
        b.setStatus(2);
        bookingDetailRepository.save(b);
        System.out.println(b);
//        System.out.println(b1);

    }

        @PostMapping("/book")
        public ResponseEntity<?> book(@RequestBody BookDTO bookDTO){
            System.out.println(bookDTO);
            User user = userService.findByPhone(bookDTO.getUser());
            List<Booking> listb = bookingRepository.existbooking(user.getId());
            if(listb.size()==0) {
                Booking booking = new Booking();
                LocalDate currentDate = LocalDate.now();
                DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                // Chuyển LocalDate thành String
                String formattedDate = currentDate.format(formatter1);
                booking.setUser(user);
                booking.setName(bookDTO.getName());
                booking.setPhone(bookDTO.getPhone());
                booking.setDate(formattedDate);
                booking.setTotalPrice(bookDTO.getTotalPrice());
                booking.setStatus(0);
                booking.setAddress(bookDTO.getAddress());
                booking.setPayment(0);
                System.out.println(booking);
                Booking booking1 = bookingRepository.save(booking);
                List<BookingDetail> list = bookingDetailRepository.findByBookingId(user.getId());
                for (BookingDetail b : list
                ) {
                    b.setBooking(booking1);
                    b.setStatus(1);
                    System.out.println(b);
                    bookingDetailRepository.save(b);
                }
                return ResponseEntity.ok("ss");
            }
            else return ResponseEntity.ok("fail");

        }
}

