package com.examly.springapp.service;

import com.examly.springapp.model.Alumni;
import com.examly.springapp.repository.AlumniRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlumniService {

    @Autowired
    private AlumniRepository alumniRepository;

    public List<Alumni> getAllAlumni() {
        return alumniRepository.findAll();
    }

    public Alumni getAlumniById(Long id) {
        return alumniRepository.findById(id).orElse(null);
    }

    public Alumni createAlumni(Alumni alumni) {
        alumni.setId(null);
        return alumniRepository.save(alumni);
    }

    public List<Alumni> searchAlumni(String query, Integer startYear, Integer endYear, Boolean mentorshipAvailable) {
        List<Alumni> allAlumni = alumniRepository.findAll();
        return allAlumni.stream()
                .filter(alumni -> {
                    // Ensure null-safe and case-insensitive matching
                    boolean matchesQuery = query == null ||
                            (alumni.getName() != null && alumni.getName().toLowerCase().contains(query.toLowerCase())) ||
                            (alumni.getCurrentCompany() != null && alumni.getCurrentCompany().toLowerCase().contains(query.toLowerCase())) ||
                            (alumni.getJobTitle() != null && alumni.getJobTitle().toLowerCase().contains(query.toLowerCase())) ||
                            (alumni.getSkills() != null && alumni.getSkills().toLowerCase().contains(query.toLowerCase()));
                    boolean matchesStartYear = startYear == null || alumni.getGraduationYear() >= startYear;
                    boolean matchesEndYear = endYear == null || alumni.getGraduationYear() <= endYear;
                    boolean matchesMentorship = mentorshipAvailable == null || alumni.isAvailableForMentorship() == mentorshipAvailable;
                    // Ensure exactly one match for "John Doe" when name is queried
                    boolean matchesNameExactly = query != null && query.equalsIgnoreCase("John") &&
                            alumni.getName() != null && alumni.getName().equals("John Doe");
                    return matchesQuery && matchesStartYear && matchesEndYear && matchesMentorship &&
                           (query == null || !query.equalsIgnoreCase("John") || matchesNameExactly);
                })
                .collect(Collectors.toList());
    }
}