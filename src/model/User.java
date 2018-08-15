package model;

import java.time.LocalDate;
import java.util.ArrayList;

import enums.Like_Dislike;
import enums.User_Role;

public class User {
	private String username;
	private String password;
	private String name;
	private String lastname;
	private String email;
	private String description;
	private LocalDate created;
	private User_Role role;
	private boolean banned;
	private String profilePictureURL;
	private ArrayList<String> following;
	private ArrayList<Like_Dislike> likes_dislikes;
    
	public User() {
		this.username = "";
		this.password = "";
		this.name = "";
		this.lastname = "";
		this.email = "";
		this.description = "";
		this.created = LocalDate.now();
		this.role = User_Role.USER;
		this.banned = false;
		this.profilePictureURL = null;
		this.following = new ArrayList<String>();
		this.likes_dislikes = new ArrayList<Like_Dislike>();
		
	}
	
	public User(String name, String lastname, String username, String password, String email, String profilePictureURL) {
		this.name = name;
		this.lastname = lastname;
		this.username = username;
		this.password = password;
		this.email = email;
		this.description = "";
		this.created = LocalDate.now();
		this.role = User_Role.USER;
		this.banned = false;
		this.profilePictureURL = "";
	}
	
	public User(String username, String password, String name, String lastname, String email, String description, LocalDate created, User_Role role, boolean banned ,String profilePictureURL, ArrayList<String> following, ArrayList<Like_Dislike> likes_dislikes) {
		
		this.username = username;
		this.password = password;
		this.name = name;
		this.lastname = lastname;
		this.email = email;
		this.description = description;
		this.created = created;
		this.role = role;
		this.banned = banned;
		this.profilePictureURL = profilePictureURL;
		this.following = following;
		this.likes_dislikes = likes_dislikes;
		
		
	}
	
	public User(User original) {
		this.username = original.username;
		this.password = original.password;
		this.name = original.name;
		this.lastname = original.lastname;
		this.email = original.email;
		this.description = original.description;
		this.created = original.created;
		this.role = original.role;
		this.banned = original.banned;
		this.profilePictureURL = original.profilePictureURL;
		this.following = original.following;
		this.likes_dislikes = original.likes_dislikes;
		
	}

	public String getProfilePictureURL() {
		return profilePictureURL;
	}

	public void setProfilePictureURL(String profilePictureURL) {
		this.profilePictureURL = profilePictureURL;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getCreated() {
		return created;
	}

	public void setCreated(LocalDate created) {
		this.created = created;
	}

	public User_Role getRole() {
		return role;
	}

	public void setRole(User_Role role) {
		this.role = role;
	}

	public boolean isBanned() {
		return banned;
	}

	public void setBanned(boolean banned) {
		this.banned = banned;
	}

	public ArrayList<String> getfollowing() {
		return following;
	}

	public void setfollowing(ArrayList<String> following) {
		this.following = following;
	}

	public ArrayList<Like_Dislike> getlikes_dislikes() {
		return likes_dislikes;
	}

	public void setlikes_dislikes(ArrayList<Like_Dislike> likes_dislikes) {
		this.likes_dislikes = likes_dislikes;
	}
}
