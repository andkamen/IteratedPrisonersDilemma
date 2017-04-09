package com.ipdweb.areas.tournament.servicesImpl;


import com.ipdweb.areas.tournament.services.MatchUpResultService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MatchUpResultServiceImpl implements MatchUpResultService {

    @Autowired
    private ModelMapper modelMapper;


}
