package com.durgesh.service;

import java.util.Optional;

import com.durgesh.entity.Candidate;

public interface ICandidateService {
	
	
	public Candidate findCandidateByEmailOrUsername(String usernameOremail,String newPassword);
   
  

}
