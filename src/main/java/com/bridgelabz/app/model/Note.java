package com.bridgelabz.app.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="note")
public class Note {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "noteid")
	private int noteId;
	private String title;
	private String description;
	private LocalDateTime creadtedtime;
	private  LocalDateTime updatetime;
	private boolean isarchive;
	private boolean ispinned;
	private boolean intrash;
	
	@Column(name = "id")
	private int userId;
	public int getNoteId() {
		return noteId;
	}
	public void setNoteId(int noteId) {
		this.noteId = noteId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	public LocalDateTime getCreadtedtime() {
		return creadtedtime;
	}
	public void setCreadtedtime(LocalDateTime creadtedtime) {
		this.creadtedtime = creadtedtime;
	}
	public LocalDateTime getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(LocalDateTime updatetime) {
		this.updatetime = updatetime;
	}
	public boolean isIsarchive() {
		return isarchive;
	}
	public void setIsarchive(boolean isarchive) {
		this.isarchive = isarchive;
	}
	public boolean isIspinned() {
		return ispinned;
	}
	public void setIspinned(boolean ispinned) {
		this.ispinned = ispinned;
	}
	public boolean isIntrash() {
		return intrash;
	}
	public void setIntrash(boolean intrash) {
		this.intrash = intrash;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
}
