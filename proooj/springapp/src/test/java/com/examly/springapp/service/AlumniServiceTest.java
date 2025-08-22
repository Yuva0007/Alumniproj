package com.examly.springapp.service;

import com.examly.springapp.model.Alumni;
import com.examly.springapp.repository.AlumniRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AlumniServiceTest {
    @Mock
    private AlumniRepository alumniRepository;
    @InjectMocks
    private AlumniService alumniService;

    private Alumni alumni1, alumni2, alumni3;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        alumni1 = Alumni.builder().id(1L).name("John Doe").graduationYear(2018).currentCompany("Tech Innovations Inc.")
                .jobTitle("Software Engineer").skills("Java,React,Spring Boot").availableForMentorship(true)
                .email("john@example.com").build();
        alumni2 = Alumni.builder().id(2L).name("Alex Smith").graduationYear(2022).currentCompany("Alpha Group")
                .jobTitle("Analyst").skills("Excel, PowerBI").availableForMentorship(false)
                .email("alex@example.com").build();
        alumni3 = Alumni.builder().id(3L).name("Jane Roe").graduationYear(2009).currentCompany("Legacy Creators")
                .jobTitle("Manager").skills("Management,Leadership").availableForMentorship(true)
                .email("jane@example.com").build();
    }

    @Test
    void testGetAllAlumni() {
        when(alumniRepository.findAll()).thenReturn(Arrays.asList(alumni1, alumni2, alumni3));
        List<Alumni> result = alumniService.getAllAlumni();
        assertEquals(3, result.size());
        verify(alumniRepository, times(1)).findAll();
    }

    @Test
    void testGetAlumniById() {
        when(alumniRepository.findById(2L)).thenReturn(Optional.of(alumni2));
        Alumni found = alumniService.getAlumniById(2L);
        assertNotNull(found);
        assertEquals("Alex Smith", found.getName());
    }

    @Test
    void testCreateAlumni() {
        Alumni toSave = Alumni.builder().name("Create Test").graduationYear(2021).currentCompany("CreateComp")
                .jobTitle("Dev").skills("Java,React").email("create@test.com").build();
        when(alumniRepository.save(any(Alumni.class))).thenAnswer(inv -> {
            Alumni a = inv.getArgument(0);
            a.setId(99L);
            return a;
        });
        Alumni result = alumniService.createAlumni(toSave);
        assertNotNull(result.getId());
        assertEquals("CreateComp", result.getCurrentCompany());
    }

    @Test
    void testSearchAlumniByNameAndYears() {
        when(alumniRepository.findAll()).thenReturn(Arrays.asList(alumni1, alumni2, alumni3));
        List<Alumni> filtered = alumniService.searchAlumni("John", 2015, 2019, null);
        assertEquals(1, filtered.size());
        assertEquals("John Doe", filtered.get(0).getName());
    }

    @Test
    void testSearchAlumniBySkillsAndMentorship() {
        when(alumniRepository.findAll()).thenReturn(Arrays.asList(alumni1, alumni2, alumni3));
        List<Alumni> filtered = alumniService.searchAlumni("java", null, null, true);
        assertEquals(1, filtered.size());
        assertEquals("John Doe", filtered.get(0).getName());
    }

}
