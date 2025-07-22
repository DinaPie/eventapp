package com.eventapp.controller;

import com.eventapp.model.Event;
import com.eventapp.model.Question;
import com.eventapp.repository.EventRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;

@Controller
@RequestMapping("/event")
public class EventController {

    @Autowired
    private EventRepository eventRepository;

    //Admin
    @GetMapping
    public String listEvents(Model model) {
        model.addAttribute("events", eventRepository.findAll());
        return "views/events/event";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("event", new Event());
        return "views/events/addEvent";
    }

    @PostMapping("/upload")
    public String handleUpload(@ModelAttribute Event event,
                               @RequestParam("image") MultipartFile file,
                               @RequestParam("questionsJson") String questionsJson) throws IOException {

        // Validate and save uploaded image
        if (!file.isEmpty()) {
            String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
            if (!"jpg".equalsIgnoreCase(extension) && !"jpeg".equalsIgnoreCase(extension)) {
                throw new IllegalArgumentException("Only JPG files allowed");
            }

            String filename = UUID.randomUUID() + "." + extension;
            Path uploadPath = Paths.get("uploads", filename);
            Files.createDirectories(uploadPath.getParent());
            Files.copy(file.getInputStream(), uploadPath, StandardCopyOption.REPLACE_EXISTING);

            event.setImageUrl("/uploads/" + filename);
        }

        // Parse the JSON into a list of Question objects
        ObjectMapper mapper = new ObjectMapper();
        List<Question> questions = mapper.readValue(questionsJson, new TypeReference<List<Question>>() {});
        event.setQuestions(questions);

        eventRepository.save(event);
        return "redirect:/event";
    }


    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable String id, Model model) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        model.addAttribute("event", event);
        return "views/events/editEvent";
    }

    @PostMapping("/edit/{id}")
    public String handleEdit(@PathVariable String id,
                             @ModelAttribute Event updatedEvent,
                             @RequestParam("image") MultipartFile file,
                             @RequestParam("questionsJson") String questionsJson) throws IOException {

        Event existing = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        existing.setTitle(updatedEvent.getTitle());
        existing.setDate(updatedEvent.getDate());
        existing.setLocation(updatedEvent.getLocation());

        // Handle image upload if new image provided
        if (!file.isEmpty()) {
            String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
            if (!"jpg".equalsIgnoreCase(extension) && !"jpeg".equalsIgnoreCase(extension)) {
                throw new IllegalArgumentException("Only JPG files allowed");
            }

            String filename = UUID.randomUUID() + "." + extension;
            Path uploadPath = Paths.get("uploads", filename);
            Files.createDirectories(uploadPath.getParent());
            Files.copy(file.getInputStream(), uploadPath, StandardCopyOption.REPLACE_EXISTING);

            existing.setImageUrl("/uploads/" + filename);
        }

        // Parse questions from JSON
        ObjectMapper objectMapper = new ObjectMapper();
        List<Question> parsedQuestions = objectMapper.readValue(questionsJson, new TypeReference<>() {});
        existing.setQuestions(parsedQuestions);

        eventRepository.save(existing);
        return "redirect:/event";
    }

    @PostMapping("/delete/{id}")
    public String deleteEvent(@PathVariable String id) {
        eventRepository.deleteById(id);
        return "redirect:/event";
    }
}