package com.example.markethome;

import com.example.markethome.Entities.Role;
import com.example.markethome.reponsitory.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private  RoleRepository roleRepository;


    @Override
    public void run(String... args) throws Exception {
        // Kiểm tra xem dữ liệu đã tồn tại hay chưa
        if (roleRepository.count() == 0) {
            Role admin = new Role("ROLE_ADMIN");
            Role customer = new Role("ROLE_CUSTOMER");
            roleRepository.saveAll(List.of(admin, customer));
        }

    }
}
