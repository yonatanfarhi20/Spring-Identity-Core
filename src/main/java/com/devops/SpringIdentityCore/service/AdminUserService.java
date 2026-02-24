package com.devops.SpringIdentityCore.service;

import com.devops.SpringIdentityCore.dtos.requests.UserRegistrationRequest;
import com.devops.SpringIdentityCore.dtos.responses.UserResponse;
import com.devops.SpringIdentityCore.enums.Role;
import com.devops.SpringIdentityCore.mapper.UserMapper;
import com.devops.SpringIdentityCore.model.User;
import com.devops.SpringIdentityCore.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminUserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public List<UserResponse> getAllActiveUsers() {
        return userRepository.findAllByEnabledTrue().stream()
                .map(userMapper::toResponse).toList();
    }
    @Transactional(readOnly = true)
    public List<UserResponse> getDeletedUsers() {
        return userRepository.findAllByEnabledFalse().stream()
                .map(userMapper::toResponse).toList();
    }
    @Transactional
    public UserResponse restoreUser(Long id) {
        var user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("משתמש לא נמצא"));
        user.setEnabled(true);
        return userMapper.toResponse(userRepository.save(user));
    }
    @Transactional
    public void hardDeleteUser(Long id) {
        userRepository.deleteById(id);
    }
    @Transactional
    public void softDeleteAll() {
        userRepository.softDeleteAllNonAdminUsers();
    }
    @Transactional
    public void hardDeleteAll() {
        userRepository.hardDeleteAllNonAdminUsers();
    }
    @Transactional
    public void restoreAll() {
        userRepository.restoreAllDeletedUsers();
    }
    @Transactional
    public UserResponse createAdmin(UserRegistrationRequest request) {
        log.info("מנהל מערכת יוצר חשבון מנהל חדש: {}", request.email());
        if (userRepository.existsByEmail(request.email().toLowerCase().trim()))
            throw new RuntimeException("כתובת האימייל כבר קיימת במערכת");
        // 1. מיפוי ל-Entity מה-DTO (גם אם נשלח שם Role אחר, אנחנו נדרוס אותו)
        User adminUser = userMapper.toEntity(request);//הייתה לי בעיה למרות שב default הגדרתי true לכן אני עשיתי פה ביצוע שנית
        // 2. הצפנת סיסמה
        adminUser.setPassword(passwordEncoder.encode(request.password()));
        // 3. קביע  של התפקיד ל-ADMIN כדי לקבל אבטחה מקסימלית
        adminUser.setRole(Role.ADMIN);
        adminUser.setEnabled(true);
        User savedAdmin = userRepository.save(adminUser);
        log.info("חשבון מנהל נוצר בהצלחה עם מזהה: {}", savedAdmin.getId());
        return userMapper.toResponse(savedAdmin);
    }
}