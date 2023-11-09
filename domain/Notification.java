package com.obs.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "tbl_notification")
public class Notification {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "DN_ID", nullable = false, unique = true)
	private String id;

	private String senderName;

	private String senderId;

	private String recieverName;

	private String receiverId;

	private String description;

	private String hintDescription;

	private int notificationType;

	private String postId;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
	private Date createdOn = new Date();

	@Column(name = "notificationSeen", columnDefinition = "tinyint(1) default 0")
	private boolean notificationSeen;

	@Column(name = "notificationReadStatus", columnDefinition = "tinyint(1) default 0")
	private boolean notificationReadStatus;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getSenderId() {
		return senderId;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	public String getRecieverName() {
		return recieverName;
	}

	public void setRecieverName(String recieverName) {
		this.recieverName = recieverName;
	}

	public String getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getHintDescription() {
		return hintDescription;
	}

	public void setHintDescription(String hintDescription) {
		this.hintDescription = hintDescription;
	}

	public int getNotificationType() {
		return notificationType;
	}

	public void setNotificationType(int notificationType) {
		this.notificationType = notificationType;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public boolean isNotificationSeen() {
		return notificationSeen;
	}

	public void setNotificationSeen(boolean notificationSeen) {
		this.notificationSeen = notificationSeen;
	}

	public boolean isNotificationReadStatus() {
		return notificationReadStatus;
	}

	public void setNotificationReadStatus(boolean notificationReadStatus) {
		this.notificationReadStatus = notificationReadStatus;
	}

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

}
