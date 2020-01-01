package com.doodle.polls.repository;

import com.doodle.polls.entity.Poll;
import com.doodle.polls.entity.SearchRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PollRepository extends JpaRepository<Poll, String> {

    @Query("SELECT p FROM Poll p " +
            "WHERE p.title LIKE %:#{#searchRecord.title}% OR :#{#searchRecord.title} IS NULL " +
            "AND (p.description LIKE %:#{#searchRecord.description}% OR :#{#searchRecord.description} IS NULL)" +
            "AND (p.id LIKE %:#{#searchRecord.id}% OR :#{#searchRecord.id} IS NULL)" +
            "AND (p.adminKey LIKE %:#{#searchRecord.adminKey}% OR :#{#searchRecord.adminKey} IS NULL)" +
            "AND (p.optionsHash LIKE %:#{#searchRecord.optionsHash}% OR :#{#searchRecord.optionsHash} IS NULL)" +
            "AND (p.initiator.name LIKE %:#{#searchRecord.initiatorName}% OR :#{#searchRecord.initiatorName} IS NULL)" +
            "AND (p.initiator.name LIKE %:#{#searchRecord.initiatorName}% OR :#{#searchRecord.initiatorName} IS NULL) " +
            "AND (p.initiated BETWEEN :#{#searchRecord.creationDateFrom} AND :#{#searchRecord.creationDateTo} " +
            "OR :#{#searchRecord.creationDateFrom} IS NULL OR :#{#searchRecord.creationDateTo} IS NULL) " +
            "AND (p.initiated >= :#{#searchRecord.creationDateFrom} OR :#{#searchRecord.creationDateFrom} IS NULL) " +
            "AND (p.initiated <= :#{#searchRecord.creationDateTo} OR :#{#searchRecord.creationDateTo} IS NULL)")
    List<Poll> findBy(@Param("searchRecord") SearchRecord searchRecord);

}

