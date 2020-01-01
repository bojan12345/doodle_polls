package com.doodle.polls.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "options")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Option implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotEmpty
    @Column(name = "text")
    private String text;

    @Column(name = "available")
    private Boolean available;

    @Column(name = "all_day")
    private Boolean allDay;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start")
    private Date start;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "options_date")
    private Date optionsDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "poll_id", nullable = false)
    @JsonIgnore
    private Poll poll;
}
