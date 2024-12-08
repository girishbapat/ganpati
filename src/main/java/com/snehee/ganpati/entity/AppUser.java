package com.snehee.ganpati.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.hibernate.envers.Audited;

import lombok.Data;

/**
 * 
 * @author Girish
 *
 */
@Audited
@Data
@Entity
@Table(name = "app_user")
public class AppUser {
	protected AppUser() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String username;
	private String password;
	private String email;
	/*
	 * @CreationTimestamp
	 * 
	 * @Temporal(TemporalType.TIMESTAMP) private Date create_time;
	 */

}
