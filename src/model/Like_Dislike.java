package model;

import java.time.LocalDate;

import enums.Video_Comment;

public class Like_Dislike {
	private int ID;
	private boolean isLike;
	private String ownerUsername;
	private LocalDate created;
	private Video_Comment videoOrComment;
	private int videoOrCommentID;
	
	public Like_Dislike(int ID, boolean isLike,String ownerUsername, LocalDate created, Video_Comment videoOrComment, int videoOrCommentID) {
		this.ID = ID;
		this.isLike = isLike;
		this.ownerUsername = ownerUsername;
		this.created = created;
		this.videoOrComment = videoOrComment;
		this.videoOrCommentID = videoOrCommentID;
	}
	
	public Like_Dislike(boolean isLike,String ownerUsername, LocalDate created, Video_Comment videoOrComment, int videoOrCommentID) {
		this.isLike = isLike;
		this.ownerUsername = ownerUsername;
		this.created = created;
		this.videoOrComment = videoOrComment;
		this.videoOrCommentID = videoOrCommentID;
	}

	public Like_Dislike() {
		this.ID = 0;
		this.isLike = true;
		this.created =  LocalDate.now();
		this.videoOrComment = Video_Comment.VIDEO;
		this.videoOrCommentID = 0;
	}
	
	public Like_Dislike(Like_Dislike original) {
		this.ID = original.ID;
		this.isLike = original.isLike;
		this.ownerUsername = original.ownerUsername;
		this.created = original.created;
		this.videoOrComment = original.videoOrComment;
		this.videoOrCommentID = original.videoOrCommentID;
	}

	public String getOwnerUsername() {
		return ownerUsername;
	}

	public void setOwnerUsername(String ownerUsername) {
		this.ownerUsername = ownerUsername;
	}

	public int getVideoOrCommentID() {
		return videoOrCommentID;
	}

	public void setVideoOrCommentID(int videoOrCommentID) {
		this.videoOrCommentID = videoOrCommentID;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public boolean isLike() {
		return isLike;
	}

	public void setLike(boolean isLike) {
		this.isLike = isLike;
	}

	public LocalDate getCreated() {
		return created;
	}

	public void setCreated(LocalDate created) {
		this.created = created;
	}


	public Video_Comment getvideoOrComment() {
		return videoOrComment;
	}

	public void setvideoOrComment(Video_Comment videoOrComment) {
		this.videoOrComment = videoOrComment;
	}
}
