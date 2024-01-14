package com.example.markethome.service;

import com.example.markethome.Entities.Service;

import java.util.List;

public interface ServiceService {
    public String saveImg(String img);

    public String saveImgDetail(String img, String id, int i);

    public List<String> updateImgDetail(String[] img,  Service service);

    public void removeImg(String img);
}
