package com.devops.SpringIdentityCore.service;


import com.devops.SpringIdentityCore.dtos.requests.UserUpdateRequest;
import com.devops.SpringIdentityCore.dtos.responses.UserResponse;
import com.devops.SpringIdentityCore.mapper.UserMapper;
import com.devops.SpringIdentityCore.model.User;
import com.devops.SpringIdentityCore.repository.UserRepository;
import com.devops.SpringIdentityCore.security.SecurityUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final SecurityUtils securityUtils; // הזרקת כלי האבטחה החדש

    @Transactional(readOnly = true)
    public UserResponse getUserById(Long id) {
        validateOwnershipOrAdmin(id); // בדיקת הרשאה לפני שליפה
        return userRepository.findById(id)
                .map(userMapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("משתמש לא נמצא"));
    }
    @Transactional
    public UserResponse updateUser(Long id, UserUpdateRequest request) {
        validateOwnershipOrAdmin(id); // בדיקת הרשאה לפני עדכון
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("משתמש לא נמצא"));
        userMapper.updateEntityFromDto(request, existingUser);
        return userMapper.toResponse(userRepository.save(existingUser));
    }
    @Transactional
    public void softDeleteSelf(Long id) {
        validateOwnershipOrAdmin(id); // בדיקת הרשאה לפני מחיקה
        userRepository.softDelete(id);
    }

     // מתודת עזר לבדיקה האם המשתמש המחובר הוא בעל המידע או מנהל.
     // במידה ולא, תיזרק שגיאת AccessDenied שתחזור למשתמש בעברית.
    public void validateOwnershipOrAdmin(Long targetUserId) {
        if (securityUtils.isAdmin()) {
            return; // מנהל מורשה לכל הפעולות
        }
        String currentUserEmail = securityUtils.getCurrentUserEmail();
        User targetUser = userRepository.findById(targetUserId)
                .orElseThrow(() -> new EntityNotFoundException("משתמש לא נמצא"));
        if (!targetUser.getEmail().equals(currentUserEmail)) {
            throw new AccessDeniedException("אין לך הרשאה לגשת או לערוך מידע זה");
        }
    }

     // משמש עבור ה-PreAuthorize בקונטרולר
    public boolean isOwner(Long id) {
        String email = securityUtils.getCurrentUserEmail();
        return userRepository.findById(id)
                .map(user -> user.getEmail().equals(email))
                .orElse(false);
    }
}
