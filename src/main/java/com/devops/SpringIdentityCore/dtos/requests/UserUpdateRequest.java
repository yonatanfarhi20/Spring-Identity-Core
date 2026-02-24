package com.devops.SpringIdentityCore.dtos.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(description = "בקשה לעדכון פרטי משתמש קיים (כל השדות אופציונליים)")
public record UserUpdateRequest(

        @Schema(
                description = "שם מלא חדש לעדכון",
                example = "ישראל ישראלי החדש",
                minLength = 2,
                maxLength = 50,
                requiredMode = Schema.RequiredMode.NOT_REQUIRED
        )
        @Size(min = 2, max = 50, message = "השם חייב להיות בין 2 ל-50 תווים")
        String fullName,
        @Schema(
                description = "סיסמה חדשה לעדכון (תתעדכן רק אם יישלח ערך תקין)",
                example = "NewSecureP@ss1!",
                requiredMode = Schema.RequiredMode.NOT_REQUIRED
        )
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
                message = "הסיסמה החדשה חייבת להכיל לפחות אות גדולה, אות קטנה, מספר ותו מיוחד"
        )
        String password
) {}