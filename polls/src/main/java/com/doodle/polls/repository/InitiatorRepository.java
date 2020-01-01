package com.doodle.polls.repository;

import com.doodle.polls.entity.Initiator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InitiatorRepository extends JpaRepository<Initiator, Long> {
}
