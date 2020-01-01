package com.doodle.polls.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "invitees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Invitee implements Serializable {

    @Id
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "poll_id")
    @JsonIgnore
    private Poll poll;

}
