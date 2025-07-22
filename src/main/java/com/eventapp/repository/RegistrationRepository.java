package com.eventapp.repository;

import com.eventapp.model.Registration;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface RegistrationRepository extends MongoRepository<Registration, String> {
    List<Registration> findByEventId(String eventId);
    List<Registration> findByUserId(String userId);
}
