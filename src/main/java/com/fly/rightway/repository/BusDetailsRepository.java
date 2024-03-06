package com.fly.rightway.repository;

import com.fly.rightway.model.BusDetails;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BusDetailsRepository extends CrudRepository<BusDetails, Long> {

    //    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<BusDetails> findWithLockingById(Long id);
}
