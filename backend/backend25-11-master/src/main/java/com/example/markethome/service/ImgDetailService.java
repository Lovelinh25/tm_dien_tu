package com.example.markethome.service;

import com.example.markethome.Entities.ImgDetail;
import com.example.markethome.reponsitory.ImgDetailReponsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ImgDetailService {

    @Autowired
    ImgDetailReponsitory imgDetailReponsitory;
    @Autowired
    public ImgDetailService(ImgDetailReponsitory imgDetailRepository) {
        this.imgDetailReponsitory = imgDetailRepository;
    }

    public Collection<ImgDetail> getImgDetails() {
        return imgDetailReponsitory.findAll();
    }
}
