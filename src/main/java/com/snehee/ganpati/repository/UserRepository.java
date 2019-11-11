package com.snehee.ganpati.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.snehee.ganpati.entity.User;

/**
 * The interface User repository.
 *
 * @author Girish
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {}
