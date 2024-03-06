package com.fly.rightway.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "TICKET_DETAILS")
public class Ticket {

    @Id
    @GeneratedValue
    private Long id;

    private String firstName;

    private String lastname;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Version
    private Long version;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bus_id")
    private BusDetails busDetails;

    public enum Gender {
        MALE, FEMALE
    }
}
