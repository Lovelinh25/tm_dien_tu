package com.example.markethome.service;

import com.example.markethome.Entities.User;
import com.example.markethome.DTO.registerDTO;
import java.util.List;

public interface UserService {
    public List<User> getAll();

    public User save(User user);

    public void sendmail();

    public String OTP(String mail);

    public void register(registerDTO registerDTO);


    public User findByPhone(String phone);

    public User update(User user);

}
