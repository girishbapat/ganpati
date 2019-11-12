package com.snehee.ganpati.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.snehee.ganpati.entity.Idol;

/**
 * The interface Idol repository.
 *
 * @author Girish
 */
@Repository
public interface IdolRepository extends JpaRepository<Idol, Integer> {
}
