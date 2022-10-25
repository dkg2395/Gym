package com.durgesh.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.durgesh.entity.Candidate;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {
    Optional<Candidate> findByEmail(String email);
    Optional<Candidate> findByUsernameOrEmail(String username, String email);
    
//    @Query("SELECT c FROM Candidate c WHERE c.username = :usernameOremail and c.email = :usernameOremail")
//    Optional<Candidate> findByUsernameOrEmail(@Param("usernameOremail") String usernameOremail);
    Optional<Candidate> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
   
    
}
