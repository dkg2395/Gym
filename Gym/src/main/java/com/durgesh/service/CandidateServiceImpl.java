package com.durgesh.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.durgesh.entity.Candidate;
import com.durgesh.exception.CandidateDoesntExistsException;
import com.durgesh.repository.CandidateRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CandidateServiceImpl implements ICandidateService {

	@Autowired
	private CandidateRepository candidateRepository;
	
	 @Autowired
	    private PasswordEncoder passwordEncoder;


	@Override
	public Candidate findCandidateByEmailOrUsername(String email, String newPassword) {

		log.info("check user exist or not ");
		Boolean existsByEmail = candidateRepository.existsByEmail(email);
		if (!existsByEmail) {
			throw new CandidateDoesntExistsException("Username doesn't exists");

		} else {

			Candidate candidate = candidateRepository.findByEmail(email).get();

			candidate.setPassword(passwordEncoder.encode(newPassword));
			candidateRepository.save(candidate);

			log.info("new password saved");
			return candidate;
		}

	}

}
