package com.doodle.polls.service;

import com.doodle.polls.entity.*;
import com.doodle.polls.repository.OptionRepository;
import com.doodle.polls.repository.ParticipantRepository;
import com.doodle.polls.repository.PollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PollService {

    @Autowired
    private PollRepository pollRepository;
    @Autowired
    private OptionRepository optionRepository;
    @Autowired
    private ParticipantRepository participantRepository;

    public List<Poll> findPolls(SearchRecord searchRecord) {
        List<Poll> polls = pollRepository.findBy(searchRecord);
        for (Poll p : polls) {
            List<Option> optionList = optionRepository.findAllByPollId(p.getId());
            p.setOptions(optionList);
            List<Participant> participantList = participantRepository.findAllByPollId(p.getId());
            p.setParticipants(participantList);
        }
        return polls;
    }

}
