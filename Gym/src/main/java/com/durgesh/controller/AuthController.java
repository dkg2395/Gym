package com.durgesh.controller;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.durgesh.entity.Candidate;
import com.durgesh.entity.Role;
import com.durgesh.payload.JWTAuthResponse;
import com.durgesh.payload.LoginDto;
import com.durgesh.payload.SignUpDto;
import com.durgesh.repository.CandidateRepository;
import com.durgesh.repository.RoleRepository;
import com.durgesh.security.JwtTokenProvider;
import com.durgesh.service.CandidateServiceImpl;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CandidateRepository candidateRepository;
    
    @Autowired
    private CandidateServiceImpl  candidateServiceImpl ;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @PostMapping("/login")
    public ResponseEntity<JWTAuthResponse> authenticateCandidate(@RequestBody LoginDto loginDto){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // get token form tokenProvider
        String token = tokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new JWTAuthResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerCandidate(@RequestBody SignUpDto signUpDto){

        // add check for username exists in a DB
        if(candidateRepository.existsByUsername(signUpDto.getUsername())){
            return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
        }

        // add check for email exists in DB
        if(candidateRepository.existsByEmail(signUpDto.getEmail())){
            return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
        }

        // create Candidate object
        Candidate candidate = new Candidate();
        candidate.setFirstName(signUpDto.getFirstName());
        candidate.setLastName(signUpDto.getLastName());
        candidate.setMobileNumber(signUpDto.getMobileNumber());
        candidate.setUsername(signUpDto.getUsername());
        candidate.setEmail(signUpDto.getEmail());
        candidate.setImage(signUpDto.getImage());
        candidate.setState(signUpDto.getState());
        candidate.setCountry(signUpDto.getCountry());
        candidate.setCity(signUpDto.getCity());
        candidate.setZipcode(signUpDto.getZipcode());
        candidate.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

        Role roles = roleRepository.findByName("ROLE_ADMIN").get();
        candidate.setRoles(Collections.singleton(roles));

        candidateRepository.save(candidate);

        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);

    }
    
    @PostMapping("/{email}/forgot/{newPassword}")
	public String  forgotPassword(@PathVariable("email") String email, @PathVariable("newPassword") String newPassword) {
		log.info("forgot password url called");
		candidateServiceImpl.findCandidateByEmailOrUsername(email, newPassword);
		log.info(" password updated");
		
		return "Your password changed successfully";
	}
}
