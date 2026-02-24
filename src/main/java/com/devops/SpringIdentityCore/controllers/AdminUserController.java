package com.devops.SpringIdentityCore.controllers;

import com.devops.SpringIdentityCore.dtos.requests.UserRegistrationRequest;
import com.devops.SpringIdentityCore.dtos.responses.UserResponse;
import com.devops.SpringIdentityCore.service.AdminUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/users")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Admin Management", description = "ממשק ניהול למנהלים בלבד - ניהול משתמשים והרשאות")
@SecurityRequirement(name = "bearerAuth")
public class AdminUserController {

    private final AdminUserService adminService;

    @GetMapping
    @Operation(summary = "צפייה בכל המשתמשים הפעילים", description = "מחזיר רשימה של כל המשתמשים שחשבונם פעיל במערכת")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(adminService.getAllActiveUsers());
    }
    @GetMapping("/deleted")
    @Operation(summary = "צפייה במשתמשים מחוקים", description = "שליפת רשימת המשתמשים שבוצעה להם מחיקה לוגית (Disabled)")
    public ResponseEntity<List<UserResponse>> getDeletedUsers() {
        return ResponseEntity.ok(adminService.getDeletedUsers());
    }
    @PatchMapping("/{id}/restore")
    @Operation(summary = "שחזור משתמש בודד", description = "החזרת חשבון שנמחק לוגית למצב פעיל (Enabled)")
    public ResponseEntity<UserResponse> restoreUser(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.restoreUser(id));
    }
    @DeleteMapping("/{id}/hard")
    @Operation(summary = "מחיקת משתמש לצמיתות", description = "מחיקה פיזית של המשתמש ממסד הנתונים - פעולה בלתי הפיכה")
    public ResponseEntity<Void> hardDeleteUser(@PathVariable Long id) {
        adminService.hardDeleteUser(id);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/bulk-soft-delete")
    @Operation(summary = "מחיקה לוגית גורפת", description = "השבתת כל המשתמשים במערכת פרט למנהלים")
    public ResponseEntity<Void> softDeleteAll() {
        adminService.softDeleteAll();
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/bulk-hard-delete")
    @Operation(summary = "מחיקה לצמיתות גורפת", description = "מחיקה פיזית של כל המשתמשים מהמערכת פרט למנהלים")
    public ResponseEntity<Void> hardDeleteAll() {
        adminService.hardDeleteAll();
        return ResponseEntity.noContent().build();
    }
    @PatchMapping("/bulk-restore")
    @Operation(summary = "שחזור גורף של משתמשים", description = "החזרת כל המשתמשים שנמחקו לוגית למצב פעיל במכה אחת")
    public ResponseEntity<Void> restoreAll() {
        adminService.restoreAll();
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/create-admin")
    @Operation(summary = "יצירת מנהל מערכת חדש", description = "מאפשר למנהל קיים לרשום מנהל נוסף למערכת")
    public ResponseEntity<UserResponse> createAdmin(@Valid @RequestBody UserRegistrationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.createAdmin(request));
    }
}
