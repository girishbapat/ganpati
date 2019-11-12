package com.snehee.ganpati.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * 
 * @author Girish
 *
 */
@Data
@Entity
@Table(name = "user")
public class User {
	protected User() {
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
