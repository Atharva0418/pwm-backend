package com.atharvadholakia.password_manager.repository;

import com.atharvadholakia.password_manager.data.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {}
