package com.doodle.polls.repository;

import com.doodle.polls.entity.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OptionRepository extends JpaRepository<Option, Long> {

    List<Option> findAllByPollId(String pollId);

}
