package com.examly.springapp.controller;

import com.examly.springapp.model.*;
import com.examly.springapp.service.AlumniService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alumni")
public class AlumniController {

    @Autowired
    private AlumniService alumniService;

    @GetMapping
    public List<Alumni> getAllAlumni() {
        return alumniService.getAllAlumni();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAlumniById(@PathVariable Long id) {
        Alumni alumni = alumniService.getAlumniById(id);
        if (alumni == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Alumni with ID " + id + " not found"));
        }
        return ResponseEntity.ok(alumni);
    }

    @PostMapping
    public ResponseEntity<?> createAlumni(@Valid @RequestBody Alumni alumni) {
        Alumni created = alumniService.createAlumni(alumni);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldError().getDefaultMessage();
        return ResponseEntity.badRequest().body(new ErrorResponse(message));
    }

    @GetMapping("/search")
    public List<Alumni> searchAlumni(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) Integer startYear,
            @RequestParam(required = false) Integer endYear,
            @RequestParam(required = false) Boolean mentorshipAvailable) {
        return alumniService.searchAlumni(query, startYear, endYear, mentorshipAvailable);
    }
}