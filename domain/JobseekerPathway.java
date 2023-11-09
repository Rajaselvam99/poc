
package com.obs.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "tbl_pathway")
public class JobseekerPathway {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "DN_ID", nullable = false, unique = true)
	private String pathwayId;

	private String currentPath;

	private String currentStep;

	private String currentBranch;

	private String currentBranchStep;

	private String targetBranch;

	private String targetBranchStep;

	private String targetStep;

	private String targetPath;

	private String country;

	private int indexValue;

	@Column(name = "currentPathNonTraining", columnDefinition = "tinyint(1) default 0")
	private boolean currentPathNonTraining;

	@Column(name = "targetPathNonTraining", columnDefinition = "tinyint(1) default 0")
	private boolean targetPathNonTraining;

	public String getPathwayId() {
		return pathwayId;
	}

	public void setPathwayId(String pathwayId) {
		this.pathwayId = pathwayId;
	}

	public String getCurrentPath() {
		return currentPath;
	}

	public void setCurrentPath(String currentPath) {
		this.currentPath = currentPath;
	}

	public String getTargetPath() {
		return targetPath;
	}

	public void setTargetPath(String targetPath) {
		this.targetPath = targetPath;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCurrentStep() {
		return currentStep;
	}

	public void setCurrentStep(String currentStep) {
		this.currentStep = currentStep;
	}

	public String getTargetStep() {
		return targetStep;
	}

	public void setTargetStep(String targetStep) {
		this.targetStep = targetStep;
	}

	public String getCurrentBranch() {
		return currentBranch;
	}

	public void setCurrentBranch(String currentBranch) {
		this.currentBranch = currentBranch;
	}

	public String getCurrentBranchStep() {
		return currentBranchStep;
	}

	public void setCurrentBranchStep(String currentBranchStep) {
		this.currentBranchStep = currentBranchStep;
	}

	public String getTargetBranch() {
		return targetBranch;
	}

	public void setTargetBranch(String targetBranch) {
		this.targetBranch = targetBranch;
	}

	public String getTargetBranchStep() {
		return targetBranchStep;
	}

	public void setTargetBranchStep(String targetBranchStep) {
		this.targetBranchStep = targetBranchStep;
	}

	public int getIndexValue() {
		return indexValue;
	}

	public void setIndexValue(int indexValue) {
		this.indexValue = indexValue;
	}

	public boolean isCurrentPathNonTraining() {
		return currentPathNonTraining;
	}

	public void setCurrentPathNonTraining(boolean currentPathNonTraining) {
		this.currentPathNonTraining = currentPathNonTraining;
	}

	public boolean isTargetPathNonTraining() {
		return targetPathNonTraining;
	}

	public void setTargetPathNonTraining(boolean targetPathNonTraining) {
		this.targetPathNonTraining = targetPathNonTraining;
	}

}
