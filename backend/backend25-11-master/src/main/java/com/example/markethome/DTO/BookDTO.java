package com.example.markethome.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {
    private String address;
    private String name;
    private String phone;
    private Long totalPrice;
    private String user;

    private String bankCode;

    @Override
    public String toString() {
        return "BookDTO{" +
                "address='" + address + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", totalPrice=" + totalPrice +
                ", user='" + user + '\'' +
                ", bankCode='" + bankCode + '\'' +
                '}';
    }
}
