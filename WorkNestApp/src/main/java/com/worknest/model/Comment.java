package com.worknest.model;

import java.util.Date;

import javax.persistence.*;


@Entity
@Table(name="comments")
public class Comment {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long id;

  @Column(nullable=false, length=2000)
  private String content;

  @Temporal(TemporalType.TIMESTAMP)
  private Date createdAt = new Date();

  @ManyToOne @JoinColumn(name="user_id")
  private User user;

  @ManyToOne @JoinColumn(name="task_id")
  private Task task;

  public Long getId() {
	return id;
  }

  public void setId(Long id) {
	this.id = id;
  }

  public String getContent() {
	return content;
  }

  public void setContent(String content) {
	this.content = content;
  }

  public Date getCreatedAt() {
	return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
	this.createdAt = createdAt;
  }

  public User getUser() {
	return user;
  }

  public void setUser(User user) {
	this.user = user;
  }

  public Task getTask() {
	return task;
  }

  public void setTask(Task task2) {
	this.task = task2;
  }
  
}