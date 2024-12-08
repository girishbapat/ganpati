/**
 * 
 */
package com.snehee.ganpati.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.hibernate.envers.Audited;

import com.snehee.ganpati.enums.Size;

import lombok.Data;

/**
 * 
 * @author Girish
 *
 */
@Audited
@Data
@Entity
@Table(name = "idol")
public class Idol {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private String type;
	private String specs;
	@Enumerated(EnumType.STRING)
	private Size size;
	private BigDecimal cost;
	private BigDecimal price;
	private int quantity;
	@Column(name = "reparable_qty")
	private int reparableQty;
	@Column(name = "damaged_qty")
	private int damagedQty;
	private String comments;

}
