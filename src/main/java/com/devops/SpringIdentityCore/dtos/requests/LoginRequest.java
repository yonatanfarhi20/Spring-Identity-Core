package com.devops.SpringIdentityCore.dtos.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

@Schema(description = "בקשה להתחברות למערכת")
public record LoginRequest(

        @Schema(
                description = "כתובת האימייל של המשתמש",
                example = "user@example.com",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @Email(message = "כתובת האימייל אינה תקינה")
        @NotBlank(message = "חובה להזין אימייל")
        String email,

        @Schema(
                description = "סיסמת המשתמש (חייבת לעמוד בתקני האבטחה)",
                example = "P@ssword123",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank(message = "חובה להזין סיסמה")
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&]).{8,}$",
                message = "הסיסמה שגויה"
        )
        String password
) {}