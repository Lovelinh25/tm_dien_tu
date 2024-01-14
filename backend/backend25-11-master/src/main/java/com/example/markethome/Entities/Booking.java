package com.example.markethome.Entities;



import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer ID;
    private String date;
    @Column(nullable = false)
    private int status;
    @Column(nullable = false)
    private int payment;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private long totalPrice;
    @ManyToOne()
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(mappedBy = "booking")
    private Collection<BookingDetail> bookingDetails;


    public Booking() {
    }
    public Booking(Integer ID, String date, int status, int payment, String address, String name, String phone, long totalPrice, User user, Collection<BookingDetail> bookingDetails) {
        this.ID = ID;
        this.date = date;
        this.status = status;
        this.payment = payment;
        this.address = address;
        this.name = name;
        this.phone = phone;
        this.totalPrice = totalPrice;
        this.user = user;
        this.bookingDetails = bookingDetails;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getDate() {
        return date;
    }



    public void setDate(String date) {
        this.date = date;
    }
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPayment() {
        return payment;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }

    public long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Collection<BookingDetail> getBookingDetails() {
        return bookingDetails;
    }

    public void setBookingDetails(Collection<BookingDetail> bookingDetails) {
        this.bookingDetails = bookingDetails;
    }
}
