package com.examly.springapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderClassName = "AlumniBuilder")
public class Alumni {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Field name is required")
    private String name;

    @Min(value = 1900, message = "Graduation year must be after 1900")
    private int graduationYear;

    private String currentCompany;

    private String jobTitle;

    @NotBlank(message = "Field skills is required")
    private String skills; // Changed from List<String> to String

    private boolean availableForMentorship;

    @Email(message = "Invalid email")
    @NotBlank(message = "Email is required")
    private String email;

    private LocalDateTime createdAt = LocalDateTime.now();

    public static class AlumniBuilder {
        public AlumniBuilder skills(String skill) {
            this.skills = skill;
            return this;
        }

        // Support List<String> for compatibility
        public AlumniBuilder skills(List<String> skills) {
            this.skills = String.join(",", skills);
            return this;
        }
    }
}