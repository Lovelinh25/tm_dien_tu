package com.example.markethome.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AcceptDTO {
    private String address;
    private String name;
    private String phone;
    private Long totalPrice;
    private int status;
    private int payment;
    private String date;
}
