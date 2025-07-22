package com.eventapp.controller;

import com.eventapp.model.Event;
import com.eventapp.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class HomepageController {

    @Autowired
    private EventRepository eventRepository;

    @GetMapping("/homepage")
    public String homepagePage(Model model) {
        List<Event> events = eventRepository.findAll(); // or eventService.getAllEvents()
        model.addAttribute("events", events);
        return "views/homepage";
    }

    @GetMapping("/event-page")
    public String eventsPage(Model model) {
        List<Event> events = eventRepository.findAll(); // or eventService.getAllEvents()
        model.addAttribute("events", events);
        return "views/eventPage";
    }

    @GetMapping("/dashboard")
    public String dashboardPage() {
        return "views/dashboard";
    }
}
