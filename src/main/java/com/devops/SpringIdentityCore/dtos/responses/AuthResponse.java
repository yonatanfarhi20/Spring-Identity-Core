package com.devops.SpringIdentityCore.dtos.responses;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "תגובת התחברות מוצלחת הכוללת טוקן")
public record AuthResponse(
        String token,
        String email,
        String fullName
) {}