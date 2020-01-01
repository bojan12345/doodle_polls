package com.doodle.polls.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchRecord {

    private String title;
    private String description;
    private String id;
    private String adminKey;
    private String initiatorName;
    private String optionsHash;
    private Date creationDateFrom;
    private Date creationDateTo;

}
