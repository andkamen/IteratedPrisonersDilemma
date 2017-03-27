package com.ipdweb.servicesImpl;

import com.ipdweb.repositories.TournamentMatchUpResultRepository;
import com.ipdweb.services.TournamentMatchUpResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TournamentMatchUpResultServiceImpl implements TournamentMatchUpResultService {

    @Autowired
    private TournamentMatchUpResultRepository tournamentMatchUpResultRepository;


    @Override
    public void deleteById(Long id) {
        this.tournamentMatchUpResultRepository.delete(id);
    }
}
