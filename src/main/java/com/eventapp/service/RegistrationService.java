package com.eventapp.service;

import com.eventapp.model.Registration;
import com.eventapp.repository.RegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {
    @Autowired
    private RegistrationRepository registrationRepository;

    public void save(Registration registration) {
        registrationRepository.save(registration);
    }
}

