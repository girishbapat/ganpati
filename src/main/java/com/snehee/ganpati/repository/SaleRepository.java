package com.snehee.ganpati.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.snehee.ganpati.entity.Sale;

/**
 * The interface Sale repository.
 *
 * @author Girish
 */
@Repository
public interface SaleRepository extends JpaRepository<Sale, Integer> {
}
