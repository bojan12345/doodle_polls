package com.doodle.polls.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Entity
@Table(name = "participants")
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class Participant implements Serializable {

    @Id
    @Column(name = "id")
    private Long id;

    @NotEmpty
    @Column(name = "name")
    private String name;

    @Column(name = "preferences")
    private String preferences;

    @ManyToOne
    @JoinColumn(name = "poll_id")
    @JsonIgnore
    @ToString.Exclude
    private Poll poll;

}
