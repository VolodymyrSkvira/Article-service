package com.example.articleservice.repository;

import com.example.articleservice.model.user.Role;
import com.example.articleservice.model.user.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(RoleName name);
}
