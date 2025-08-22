package com.examly.springapp.controller;

import com.examly.springapp.model.Alumni;
import com.examly.springapp.service.AlumniService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AlumniController.class)
public class AlumniControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AlumniService alumniService;

    private Alumni alumni;

    @BeforeEach
    void setUp() {
        alumni = Alumni.builder().id(1L).name("John Doe").graduationYear(2018).currentCompany("Tech Innovations Inc.")
                .jobTitle("Software Engineer").skills("Java,React,Spring Boot").availableForMentorship(true)
                .email("john@example.com").build();
    }

    @Test
    void controller_testGetAllAlumni() throws Exception {
        when(alumniService.getAllAlumni()).thenReturn(Arrays.asList(alumni));

        mockMvc.perform(get("/api/alumni"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is("John Doe")));
    }

    @Test
    void controller_testGetAlumniByIdFound() throws Exception {
        when(alumniService.getAlumniById(1L)).thenReturn(alumni);

        mockMvc.perform(get("/api/alumni/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("John Doe")));
    }

    @Test
    void controller_testGetAlumniByIdNotFound() throws Exception {
        when(alumniService.getAlumniById(1234L)).thenReturn(null);

        mockMvc.perform(get("/api/alumni/1234"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("Alumni with ID 1234 not found")));
    }

    @Test
    void controller_testCreateAlumniSuccess() throws Exception {
        Alumni toCreate = Alumni.builder().name("New Test").graduationYear(2022).currentCompany("TestCo")
                .jobTitle("Dev").skills("Java").email("new@test.com").availableForMentorship(true).build();
        Alumni saved = Alumni.builder().id(17L).name("New Test").graduationYear(2022).currentCompany("TestCo")
                .jobTitle("Dev").skills("Java").email("new@test.com").availableForMentorship(true).build();
        when(alumniService.createAlumni(any(Alumni.class))).thenReturn(saved);
        mockMvc.perform(post("/api/alumni")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(toCreate)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(17)))
                .andExpect(jsonPath("$.name", is("New Test")));
    }

    @Test
    void controller_testCreateAlumniValidationFailure() throws Exception {
        Alumni invalid = Alumni.builder().graduationYear(2021).currentCompany("TestCo").jobTitle("Dev")
                .skills("Java").email("test@test.com").availableForMentorship(false).build();
        mockMvc.perform(post("/api/alumni")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(invalid)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", containsString("Field name is required")));
    }

    @Test
    void controller_testSearchAlumni() throws Exception {
        when(alumniService.searchAlumni(eq("Java"), eq(2015), eq(2019), isNull())).thenReturn(Collections.singletonList(alumni));
        mockMvc.perform(get("/api/alumni/search?query=Java&startYear=2015&endYear=2019"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is("John Doe")));
    }
}
