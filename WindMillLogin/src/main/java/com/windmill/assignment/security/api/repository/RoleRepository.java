package com.windmill.assignment.security.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.windmill.assignment.security.api.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer>{

}
