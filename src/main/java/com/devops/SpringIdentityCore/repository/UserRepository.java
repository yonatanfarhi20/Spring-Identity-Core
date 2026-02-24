package com.devops.SpringIdentityCore.repository;
//יצרתי שאילתות גם בעצמי
import com.devops.SpringIdentityCore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // ==========================================
    // 1. Authentication & Identification
    // ==========================================
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);

    // ==========================================
    // 2. User Management (Active/Deleted)
    // ==========================================
    List<User> findAllByEnabledTrue();
    List<User> findAllByEnabledFalse();
    List<User> findByFullNameContainingIgnoreCase(String fullName);

    // ==========================================
    // 3. Single Resource Operations (Soft Delete)
    // ==========================================
    @Modifying
    @Query("UPDATE User u SET u.enabled = false WHERE u.id = :userId")
    void softDelete(@Param("userId") Long userId);

    // ==========================================
    // 4. Admin Bulk Operations
    // ==========================================
    @Modifying
    @Query("UPDATE User u SET u.enabled = false WHERE u.enabled = true AND u.role != 'ADMIN'")
    void softDeleteAllNonAdminUsers();
    @Modifying
    @Query("DELETE FROM User u WHERE u.role != 'ADMIN'")
    void hardDeleteAllNonAdminUsers();
    @Modifying
    @Query("UPDATE User u SET u.enabled = true WHERE u.enabled = false")
    void restoreAllDeletedUsers();
}