package com.doodle.polls.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Entity
@Table(name = "initiators")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Initiator implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column(name = "initiator_name")
    private String name;

    @Email(message = "Please provide a valid email address")
    @NotEmpty
    @Column(name = "email")
    private String email;

    @Column(name = "notify")
    private Boolean notify;

    @OneToOne(
            cascade = CascadeType.ALL,
            mappedBy = "initiator")
    @JsonIgnore
    private Poll poll;

}
