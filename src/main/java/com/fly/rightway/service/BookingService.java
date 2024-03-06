package com.fly.rightway.service;

import com.fly.rightway.config.error.seatNotAvailable;
import com.fly.rightway.model.BusDetails;
import com.fly.rightway.model.Ticket;
import com.fly.rightway.repository.BusDetailsRepository;
import com.fly.rightway.repository.TicketRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookingService {

    private final BusDetailsRepository busDetailsRepository;
    private final TicketRepository ticketRepository;

    public BookingService(BusDetailsRepository busDetailsRepository, TicketRepository ticketRepository) {
        this.busDetailsRepository = busDetailsRepository;
        this.ticketRepository = ticketRepository;
    }


    public void saveTicket(String firstName, String lastName, Ticket.Gender gender, BusDetails busDetails) throws seatNotAvailable {
        if (busDetails.getCapacity() <= busDetails.getTickets().size()) {
            throw new seatNotAvailable();
        }
        Ticket ticket = new Ticket();
        ticket.setFirstName(firstName);
        ticket.setLastname(lastName);
        ticket.setGender(gender);

        busDetails.addTicket(ticket);

        ticketRepository.save(ticket);
    }

    @Transactional
    public void bookTicket() throws seatNotAvailable, InterruptedException {
        Optional<BusDetails> busOptional = busDetailsRepository.findWithLockingById(1L);
        if (busOptional.isPresent()) {
            saveTicket("mohammad", "sherafatian", Ticket.Gender.MALE, busOptional.get());
        }
        Thread.sleep(1000);
    }

    //test for optimistic  and pessimistic lock
    @Transactional
    public void bookTicket2() throws seatNotAvailable, InterruptedException {
        Optional<BusDetails> busOptional = busDetailsRepository.findWithLockingById(1L);
        if (busOptional.isPresent()) {
            saveTicket("roshanak", "rahmani", Ticket.Gender.FEMALE, busOptional.get());
        }

        Thread.sleep(1000);
    }

}
