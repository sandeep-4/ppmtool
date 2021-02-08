package com.java.spring.ppmtool.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.Immutable;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity

public class ProjectTask {

	
	
	public ProjectTask() {
		super();
	}
	

	public ProjectTask(long id, String projectSequence,
			@NotBlank(message = "Please include the summary") String summary, String acceptanceCriteria, String status,
			int priority, Date dueDate, String projectIdentifier, Date create_At, Date update_At) {
		super();
		this.id = id;
		this.projectSequence = projectSequence;
		this.summary = summary;
		this.acceptanceCriteria = acceptanceCriteria;
		this.status = status;
		this.priority = priority;
		this.dueDate = dueDate;
		this.projectIdentifier = projectIdentifier;
		this.create_At = create_At;
		this.update_At = update_At;
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	long id;
	@Column(updatable=false,unique=true)
	String projectSequence;
	@NotBlank(message="Please include the summary")
	String summary;
	String acceptanceCriteria;
	String status;
	int priority;
	@JsonFormat(pattern="yyyy-mm-dd")
	Date dueDate;
	
	@Column(updatable=false)
	String projectIdentifier;
	@JsonFormat(pattern="yyyy-mm-dd")
	Date create_At;
	@JsonFormat(pattern="yyyy-mm-dd")
	Date update_At;
	
	@ManyToOne(fetch=FetchType.EAGER)   //cascade=CascadeType.REFRESH
	@JoinColumn(name="backlog_id",updatable=false,nullable=false)
	@JsonIgnore
	Backlog backlog;
	
	@PrePersist
	public void onCreate() {
		this.create_At=new Date();
	}
	
	@PreUpdate
	public void onUpdate() {
		this.update_At=new Date();
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getProjectSequence() {
		return projectSequence;
	}


	public void setProjectSequence(String projectSequence) {
		this.projectSequence = projectSequence;
	}


	public String getSummary() {
		return summary;
	}


	public void setSummary(String summary) {
		this.summary = summary;
	}


	public String getAcceptanceCriteria() {
		return acceptanceCriteria;
	}


	public void setAcceptanceCriteria(String acceptanceCriteria) {
		this.acceptanceCriteria = acceptanceCriteria;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public int getPriority() {
		return priority;
	}


	public void setPriority(int priority) {
		this.priority = priority;
	}


	public Date getDueDate() {
		return dueDate;
	}


	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}


	public String getProjectIdentifier() {
		return projectIdentifier;
	}


	public void setProjectIdentifier(String projectIdentifier) {
		this.projectIdentifier = projectIdentifier;
	}


	public Date getCreate_At() {
		return create_At;
	}


	public void setCreate_At(Date create_At) {
		this.create_At = create_At;
	}


	public Date getUpdate_At() {
		return update_At;
	}


	public void setUpdate_At(Date update_At) {
		this.update_At = update_At;
	}


	public Backlog getBacklog() {
		return backlog;
	}


	public void setBacklog(Backlog backlog) {
		this.backlog = backlog;
	}


	@Override
	public String toString() {
		return "ProjectTask [id=" + id + ", projectSequence=" + projectSequence + ", summary=" + summary
				+ ", acceptanceCriteria=" + acceptanceCriteria + ", status=" + status + ", priority=" + priority
				+ ", dueDate=" + dueDate + ", projectIdentifier=" + projectIdentifier + ", create_At=" + create_At
				+ ", update_At=" + update_At + "]";
	}
	
}
