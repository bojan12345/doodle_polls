package com.doodle.polls.controller;


import com.doodle.polls.entity.Poll;
import com.doodle.polls.entity.SearchRecord;
import com.doodle.polls.service.PollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;


@RestController
public class PollsController {

    @Autowired
    PollService pollService;

    @GetMapping(path = "/polls", produces = "application/json")
    public List<Poll> getAllPolls(@RequestParam(required = false) String title,
                                  @RequestParam(required = false) String description,
                                  @RequestParam(required = false) String id,
                                  @RequestParam(required = false) String adminKey,
                                  @RequestParam(required = false) String initiatorName,
                                  @RequestParam(required = false) String optionsHash,
                                  @RequestParam(required = false) Date creationDateFrom,
                                  @RequestParam(required = false) Date creationDateTo) {

        SearchRecord searchRecord = new SearchRecord(title, description, id, adminKey, initiatorName, optionsHash, creationDateFrom, creationDateTo);
        return pollService.findPolls(searchRecord);
    }
}
