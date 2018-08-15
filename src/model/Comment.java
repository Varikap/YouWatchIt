package model;

import java.time.LocalDate;

public class Comment {
	private int ID;
	private String content;
	private LocalDate created;
	private String ownerUsername;
	private int videoID;
	
	public Comment () {
		//prepraviti ID
		this.ID = 0;
		this.content = "";
		this.created = LocalDate.now();
		this.ownerUsername = null;
		this.videoID = 0;
	}
	
	public Comment(int ID, String content, LocalDate created, String ownerUsername, int videoID) {
		this.ID = ID;
		this.content = content;
		this.created = created;
		this.ownerUsername = ownerUsername;
		this.videoID = videoID;
		
	}
	
	public Comment(String content, LocalDate created, String ownerUsername, int videoID) {
		this.content = content;
		this.created = created;
		this.ownerUsername = ownerUsername;
		this.videoID = videoID;
		
	}
	
	public String getOwnerUsername() {
		return ownerUsername;
	}

	public void setOwnerUsername(String ownerUsername) {
		this.ownerUsername = ownerUsername;
	}

	public int getVideoID() {
		return videoID;
	}

	public void setVideoID(int videoID) {
		this.videoID = videoID;
	}

	public Comment(Comment original) {
		this.ID = original.ID;
		this.content = original.content;
		this.created = original.created;
		this.ownerUsername = original.ownerUsername;
		this.videoID = original.videoID;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public LocalDate getCreated() {
		return created;
	}

	public void setCreated(LocalDate created) {
		this.created = created;
	}
}
