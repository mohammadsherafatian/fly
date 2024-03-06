package com.fly.rightway.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "BUS_DETAILS")
@Setter
@Getter
public class BusDetails {

    @Id
    @GeneratedValue
    private Long id;

    private LocalDateTime departDateTime;

    private Integer capacity;

    private String number;

    @Version
    private Long version;

    @OneToMany(mappedBy = "busDetails")
    private Set<Ticket> tickets;

    public void addTicket(Ticket ticket) {
        ticket.setBusDetails(this);
        getTickets().add(ticket);
    }
}
