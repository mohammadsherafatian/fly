package com.fly.rightway.controller;

import com.fly.rightway.model.BusDetails;
import com.fly.rightway.repository.BusDetailsRepository;
import com.fly.rightway.service.BookingService;
import org.apache.commons.lang3.function.FailableRunnable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/api")
public class BusDetailsController {

    private final BookingService service;
    private final BusDetailsRepository busDetailsRepository;

    public BusDetailsController(BookingService service, BusDetailsRepository busDetailsRepository) {
        this.service = service;
        this.busDetailsRepository = busDetailsRepository;
    }

    @GetMapping("/addBus")
    public void addBus(@RequestParam String number, @RequestParam Integer capacity){
        BusDetails busDetails = new BusDetails();
        busDetails.setCapacity(capacity);
        busDetails.setNumber(number);
        busDetails.setDepartDateTime(LocalDateTime.now());
        busDetailsRepository.save(busDetails);

    }

    @GetMapping("/bookTicket")
    public void bookTicket() {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.execute(run(service::bookTicket));
        executor.execute(run(service::bookTicket2));
        executor.shutdown();
    }

    private Runnable run(FailableRunnable<Exception> runnable) {

        return () -> {
            try {
                runnable.run();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };

    }
}
