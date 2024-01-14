package com.example.markethome.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServiceDTO {
    private int id;
    private String name;
    private long price;
    private int category;
    private String description;
    private String img;;
    private  int status;
    private String[] imgList;
    private int userid;

}

