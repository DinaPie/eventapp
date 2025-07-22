package com.eventapp.repository;

import com.eventapp.model.Event;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface HomepageRepository extends MongoRepository<Event, String> {
}