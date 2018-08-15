package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import enums.Like_Dislike;
import enums.User_Role;
import enums.Video_Comment;
import model.User;
import utils.ConnectionManager;

public class UserDAO {
	
public static Map<String, Integer> getFollowingForUser(ArrayList<String> usernames) {
		
		Map<String,Integer> userData = new HashMap<String, Integer>(); 
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		try {
			for(String username : usernames) {
				
				String query = "SELECT COUNT(*) FROM FOLLOWING WHERE followed_username = ?;";
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, username);
				ResultSet rset = pstmt.executeQuery();
				while(rset.next()) {
					//System.out.println("Prati: " + username + "I on ima: " + rset.getInt(1) + "pratioca" );
					userData.put(username, rset.getInt(1));
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return userData;
	}
	
	
	public static User getUser(String x) {
		
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		ArrayList<String> usernamesOfFollowers = new ArrayList<String>();
		ArrayList<Like_Dislike> likes_dislikes = new ArrayList<Like_Dislike>();
		
		try {
			ResultSet rset1 = null;
			String query = "SELECT * FROM USERS WHERE username IN(SELECT followed_username FROM FOLLOWING WHERE following_username = ?)";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, x);
			rset1 = pstmt.executeQuery();
			while(rset1.next()) {
				String username = rset1.getString("username");
				usernamesOfFollowers.add(username);
			}
		}catch (SQLException e) {e.printStackTrace(); System.out.println("Ne postojeci user");}
		
		try {
			ResultSet rset2 = null;
			String query = "SELECT * FROM LIKE_DISLIKE WHERE user_username = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, x);
			rset2 = pstmt.executeQuery();
			while(rset2.next()) {
				int ID = rset2.getInt("LIKE_DISLIKE_id");
				boolean isLike = rset2.getInt("is_like") == 1? true : false;
				String ownerUsername = rset2.getString("user_username");
				LocalDate created = LocalDate.parse(rset2.getString("created"));
				Video_Comment videoOrComment = Video_Comment.valueOf(rset2.getString("video_comment"));
				int videoOrCommentID = rset2.getInt("video_comment_id");
				
				Like_Dislike LIKE_DISLIKE = new Like_Dislike(ID, isLike,ownerUsername, created, videoOrComment, videoOrCommentID);
				likes_dislikes.add(LIKE_DISLIKE);
			}
			
		}catch (SQLException ex) { ex.printStackTrace();System.out.println("Ne postojeci user");}
		
		try {
			ResultSet rset3 = null;
			String query = "SELECT * FROM USERS WHERE username=? AND deleted = 0";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, x);
			//System.out.println(pstmt);
			
			rset3 = pstmt.executeQuery();
			while(rset3.next()) {
				String username = rset3.getString("username");
				String password = rset3.getString("password");
				String name = rset3.getString("name");
				String lastname = rset3.getString("lastname");
				String email = rset3.getString("email");
				String description = rset3.getString("description");
				
				LocalDate created = LocalDate.parse(rset3.getString("created"));
//				SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
				User_Role role = User_Role.valueOf(rset3.getString("user_role"));
				boolean banned = rset3.getInt("banned") == 1? true : false;
				String profilePictureURL = rset3.getString("profile_picture_URL");
				
				return new User(username, password, name, lastname, email, description, created, role, banned,profilePictureURL, usernamesOfFollowers, likes_dislikes);
				
				
				}
			}catch (SQLException ex) { ex.printStackTrace();System.out.println("Ne postojeci user");}
			
		return null;
	}
	
	public static boolean usernameExists(String x) {
		
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		try {
			ResultSet rset = null;
			String query = "SELECT * FROM USERS WHERE username = ? AND deleted = 0";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, x);
			rset = pstmt.executeQuery();
			if(rset.next()) {
				return true;
			}
			
			
		}catch(SQLException e) {e.printStackTrace();}
		
		return false;
	}
	
	public static boolean emailExists(String x) {
		
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		try {
			ResultSet rset = null;
			String query = "SELECT * FROM USERS WHERE email = ? AND deleted = 0";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, x);
			rset = pstmt.executeQuery();
			if(rset.next()) {
				return true;
			}
			
			
		}catch(SQLException e) {e.printStackTrace();}
		
		return false;
	}
	
	public static void createUser(User user) {
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		try {

			String query = "INSERT INTO USERS(username,password,name,lastname,email,description,created,user_role,banned,profile_picture_URL,deleted) VALUES (?,?,?,?,?,?,?,?,?,?,0)";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, user.getUsername());
			pstmt.setString(2, user.getPassword());
			pstmt.setString(3, user.getName());
			pstmt.setString(4, user.getLastname());
			pstmt.setString(5, user.getEmail());
			pstmt.setString(6, user.getDescription());
			pstmt.setString(7, user.getCreated().toString());
			pstmt.setString(8, user.getRole().toString());
			pstmt.setInt(9, user.isBanned() == true? 1 : 0);
			pstmt.setString(10, user.getProfilePictureURL());
			
			pstmt.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void updateForUser(String username, String action, String newValue) {
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		try {
			String query = "UPDATE users SET ";
			switch(action) {
			case "name":
				query += "name = ? WHERE username = ?";
				break;
			case "lastname":
				query += "lastname = ? WHERE username = ?";
				break;
			case "username":
				query += "username = ? WHERE username = ?";
				break;
			case "password":
				query += "password = ? WHERE username = ?";
				break;
			case "email":
				query += "email = ? WHERE username = ?";
				break;
			case "description":
				query += "description = ? WHERE username = ?";
				break;
			case "role":
				query += "user_role = ? WHERE username = ?";
				break;
			case "banned":
				query += "banned = ? WHERE username = ?";
				break;
			case "deleted":
				query += "deleted = ? WHERE username = ?";
				break;
			case "profilePictureURL":
				query += "profile_picture_URL = ? WHERE username = ?";
				break;
			}
			
			pstmt = conn.prepareStatement(query);
			
			if(action.equals("banned") || action.equals("deleted")) {
				pstmt.setInt(1, Boolean.parseBoolean(newValue) == true ? 1:0);
			} else {
				pstmt.setString(1, newValue);
			}
			pstmt.setString(2, username);
			System.out.println("Izgled queryja za update usera: " + pstmt.toString());
			pstmt.executeUpdate();
			
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public static ArrayList<String> getAllUsernames() {
		ArrayList<String> users = new ArrayList<String>();
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		try {
			ResultSet rset = null;
			String query = "SELECT * FROM USERS WHERE deleted = 0;";
			pstmt = conn.prepareStatement(query);
			rset = pstmt.executeQuery();
			while(rset.next()) {
				users.add(rset.getString("username"));
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
		return users;
	}
	
	public static int getNumberOfFollowersForUser(String x) {
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		try {
			String query = "SELECT COUNT(*) FROM FOLLOWING WHERE followed_username = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, x);
			ResultSet rset = pstmt.executeQuery();
			while(rset.next()) {
				return rset.getInt(1);
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	
	public static int isUserBlocked(String x) {
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		try {
			String query = "SELECT banned FROM users WHERE username = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, x);
			ResultSet rset = pstmt.executeQuery();
			while(rset.next()) {
				System.out.println("");
				return rset.getInt(1);
				
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public static Map<String, Integer> getTop5(){
		Map<String , Integer> top5 = new HashMap<String, Integer>();
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		try {
			String query = "SELECT followed_username,COUNT(*) FROM following GROUP BY followed_username ORDER BY COUNT(*) DESC LIMIT 5;";
			pstmt = conn.prepareStatement(query);
			ResultSet rset = pstmt.executeQuery();
			while(rset.next()) {
				System.out.println(rset.getString(1));
				System.out.println(rset.getInt(2));
				top5.put(rset.getString(1), rset.getInt(2));
				
				
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return top5;
	}
	
}
