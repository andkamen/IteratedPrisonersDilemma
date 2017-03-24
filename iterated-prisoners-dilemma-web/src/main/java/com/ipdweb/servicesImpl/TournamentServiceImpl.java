package com.ipdweb.servicesImpl;


import com.ipdweb.entities.Tournament;
import com.ipdweb.repositories.TournamentRepository;
import com.ipdweb.services.TournamentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TournamentServiceImpl implements TournamentService {

    @Autowired
    private TournamentRepository tournamentRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public void save(Tournament tournament) {
        this.tournamentRepository.save(tournament);
    }
}
