package com.devops.SpringIdentityCore.dtos.responses;


import com.devops.SpringIdentityCore.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "פרטי המשתמש המוחזרים מהמערכת")
public record UserResponse(

        Long id,
        @Schema(description = "שם מלא של המשתמש", example = "ישראל ישראלי")
        String fullName,
        @Schema(description = "כתובת האימייל של המשתמש", example = "israel@example.com")
        String email,
        @Schema(description = "סוג המשתמש", example = "USER/ADMIN")
        Role role,
        @Schema(description = "זמן יצירת החשבון במערכת", example = "2026-02-22T10:00:00")
        LocalDateTime createdAt
){}