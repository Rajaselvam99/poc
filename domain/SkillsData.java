package com.obs.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_skill_data")
public class SkillsData {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "DN_ID", nullable = false)
	private Long id;

	private String skill;

	@Column(name = "active", columnDefinition = "tinyint(1) default 1")
	private boolean active;

	@Column(name = "deleted", columnDefinition = "tinyint(1) default 0")
	private boolean deleted;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSkill() {
		return skill;
	}

	public void setSkill(String skill) {
		this.skill = skill;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

}
