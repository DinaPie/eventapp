package com.eventapp.controller;

import com.eventapp.model.Event;
import com.eventapp.model.Registration;
import com.eventapp.repository.EventRepository;
import com.eventapp.repository.RegistrationRepository;
import com.eventapp.service.RegistrationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    @Autowired
    private final EventRepository eventRepository;

    @Autowired
    private final RegistrationRepository registrationRepository;

    @Autowired
    private RegistrationService registrationService;


    public RegistrationController(
            EventRepository eventRepository,
            RegistrationRepository registrationRepository,
            RegistrationService registrationService) {

        this.eventRepository = eventRepository;
        this.registrationRepository = registrationRepository;
        this.registrationService = registrationService;
    }

    @GetMapping
    public String showEventCards(Model model) {
        List<Event> events = eventRepository.findAll();
        model.addAttribute("events", events);
        return "views/registrations/registration";
    }

    @GetMapping("/{eventId}")
    public String showEventRegistrations(@PathVariable String eventId, Model model) {
        List<Registration> registrations = registrationRepository.findByEventId(eventId);
        Event event = eventRepository.findById(eventId).orElse(null);
        model.addAttribute("event", event);
        model.addAttribute("registrations", registrations);
        return "views/registrations/registrationEvent";
    }

    @GetMapping("/form/{id}")
    public String registerForm(@PathVariable String id, Model model) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        Registration registration = new Registration();
        registration.setEventId(event.getId());
        registration.setEventTitle(event.getTitle());

        // Initialize answers list based on event questions
        List<Registration.Answer> answers = new ArrayList<>();
        for (int i = 0; i < event.getQuestions().size(); i++) {
            answers.add(new Registration.Answer()); // or AnswerDTO if using a DTO
        }
        registration.setAnswers(answers);

        model.addAttribute("event", event);
        model.addAttribute("registration", registration);
        return "views/forms/register";
    }


    @PostMapping("/save")
    public String saveRegistration(@ModelAttribute Registration registration, Principal principal, RedirectAttributes redirectAttributes) {
        if (principal != null) {
            registration.setUserId(principal.getName());
        }

        registrationService.save(registration);
        redirectAttributes.addFlashAttribute("success", "Registration submitted successfully!");
        return "redirect:/homepage";
    }
}
