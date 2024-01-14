package com.example.markethome.service;

import com.example.markethome.DTO.CalendarDTO;

import java.util.List;

public interface CalendarService {

    List<CalendarDTO> getData(String start, String end, int branch) throws Exception;
}
