package com.snehee.ganpati.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.snehee.ganpati.entity.AppUser;

/**
 * The interface User repository.
 *
 * @author Girish
 */
@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Integer> {
}
