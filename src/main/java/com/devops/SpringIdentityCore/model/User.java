package com.devops.SpringIdentityCore.model;

import com.devops.SpringIdentityCore.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity @Table(name = "users")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Email(message = "כתובת האימייל אינה תקינה")
    @NotBlank(message = "חובה להזין אימייל")
    private String email;
    @NotBlank(message = "חובה להזין סיסמה")
    @Size(min = 8, message = "הסיסמה חייבת להכיל לפחות 8 תווים")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&]).{8,}$",
            message = "הסיסמה חייבת להכיל לפחות אות גדולה אחת, אות קטנה אחת, מספר ותו מיוחד"
    )
    private String password;
    @NotBlank(message = "חובה להזין שם מלא")
    @Size(min = 2, max = 50, message = "השם חייב להיות בין 2 ל-50 תווים")
    private String fullName;
    @NotNull(message = "חובה להגדיר תפקיד למשתמש")
    @Enumerated(EnumType.STRING)
    private Role role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @Builder.Default
    private boolean enabled = true;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}