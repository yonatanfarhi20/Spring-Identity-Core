package com.devops.SpringIdentityCore.exceptions;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "מבנה הודעת שגיאה סטנדרטי של המערכת")
public record ErrorResponse(

        @Schema(description = "קוד הסטטוס של ה-HTTP", example = "404")
        int status,
        @Schema(description = "תיאור השגיאה בעברית", example = "משתמש לא נמצא")
        String message,
        @Schema(description = "הנתיב שבו קרתה השגיאה", example = "/api/v1/users/1")
        String path,
        @Schema(description = "זמן התרחשות השגיאה במילישניות", example = "1708612345678")
        long timestamp
) {}