package com.devops.SpringIdentityCore.controllers;

import com.devops.SpringIdentityCore.dtos.requests.UserUpdateRequest;
import com.devops.SpringIdentityCore.dtos.responses.UserResponse;
import com.devops.SpringIdentityCore.service.UserProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "User Profile", description = "ניהול פרופיל אישי")
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    private final UserProfileService userProfileService;

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @userProfileService.isOwner(#id)")
    @Operation(summary = "צפייה בפרטי פרופיל", description = "נגיש לבעל החשבון מחובר או למנהל מערכת ")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userProfileService.getUserById(id));
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @userProfileService.isOwner(#id)")
    @Operation(summary = "עדכון פרטים אישיים", description = "נגיש לבעל החשבון מחובר או למנהל מערכת ")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @Valid @RequestBody UserUpdateRequest request) {
        return ResponseEntity.ok(userProfileService.updateUser(id, request));
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @userProfileService.isOwner(#id)")
    @Operation(summary = "מחיקה עצמית (לוגית)", description = "נגיש לבעל החשבון מחובר או למנהל מערכת ")
    public ResponseEntity<Void> softDeleteSelf(@PathVariable Long id) {
        userProfileService.softDeleteSelf(id);
        return ResponseEntity.noContent().build();
    }
}