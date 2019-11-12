/**
 * 
 */
package com.snehee.ganpati.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@Table(name = "idol")
public class Idol {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private String type;
	@Enumerated(EnumType.STRING)
	private Size size;
	private BigDecimal cost;
	private BigDecimal price;
	private int quantity;
	private int reparable_qty;
	private int damaged_qty;
	private String comments;

}
