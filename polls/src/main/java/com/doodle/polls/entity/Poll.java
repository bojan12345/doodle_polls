package com.doodle.polls.entity;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "polls")
@Data
@NoArgsConstructor
public class Poll implements Serializable {

    @Id
    @Column(name = "id")
    private String id;

    @NotBlank
    @Column(name = "admin_key")
    private String adminKey;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    @Column(name = "latest_change")
    private Date latestChange;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    @Column(name = "initiated")
    private Date initiated;

    @Column(name = "participants_count")
    private int participantsCount;

    @Column(name = "invitees_count")
    private int inviteesCount;

    @Column(name = "data_type")
    private String type;

    @Column(name = "hidden")
    private Boolean hidden;

    @Column(name = "preferences_type")
    private String preferencesType;

    @Column(name = "state")
    private String state;

    @Column(name = "locale")
    private String locale;

    @Column(name = "title")
    private String title;

    @Lob
    @Column(name = "description")
    private String description;

    @OneToOne(optional = false)
    @JoinColumn(name = "initiator_id", nullable = false)
    @ToString.Exclude
    private Initiator initiator;

    @Column(name = "options_hash")
    private String optionsHash;

    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL)
    private List<Participant> participants = new ArrayList<>();

    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL)
    private List<Invitee> invitees = new ArrayList<>();

    @OneToMany(mappedBy = "id", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Option> options = new ArrayList<>();

    @Column(name = "device")
    private String device;

    @Column(name = "levels")
    private String levels;
}
