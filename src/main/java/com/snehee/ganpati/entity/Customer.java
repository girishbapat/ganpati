package com.snehee.ganpati.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.hibernate.envers.Audited;

import lombok.Data;
@Audited
@Data
@Entity
@Table(name = "customer")
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	@Column(name ="primary_mobile")
	private String primaryMobile;
	@Column(name ="secondary_mobile")
	private String secondaryMobile;
	private String landline;
	private String address;
	private String info;
	private String comments;
}