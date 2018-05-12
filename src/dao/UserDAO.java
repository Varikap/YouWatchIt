package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import enums.Role;
import model.User;

public class UserDAO {
	
	public static User getUser (String username) {
		Connection conn = utils.ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		
		try {
			String query = "select * from User where username=?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, username);
			ResultSet rset = pstmt.executeQuery();
			if (rset.next()) {
				Role role = null;
				role = Role.getRole(rset.getInt("role"));
				return new User(rset.getInt("id"),rset.getString("username"),rset.getString("password"),rset.getString("name"),rset.getString("lastName"), rset.getString("email"), rset.getBoolean("banned"), role);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static boolean usernameExists (String username) {
		Connection conn = utils.ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		
		try {
			String query = "select * from User where username=? and banned=False";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, username);
			ResultSet rset = pstmt.executeQuery();
			if (rset.next())
				return true;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
}
