package ru.site.datasource.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.site.datasource.enums.RoleName;
import ru.site.datasource.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(RoleName roleName);
}