package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import enums.Like_Dislike;
import enums.Video_Comment;
import utils.ConnectionManager;

public class Like_DislikeDAO {
	public static ArrayList<Like_Dislike> getAll() {
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		ArrayList<Like_Dislike> likes_dislikes = new ArrayList<Like_Dislike>();
		
		try {
			/*is_like BIT NOT NULL,
	user_username VARCHAR(30) NOT NULL,
	created DATE NOT NULL,
	video_comment ENUM('VIDEO', 'COMMENT'),
	video_comment_id BIGINT NOT NULL,*/
			ResultSet rset = null;
			String query = "SELECT * FROM LIKE_DISLIKE";
			pstmt = conn.prepareStatement(query);
			rset = pstmt.executeQuery();
			while(rset.next()) {
				int ID = rset.getInt("id");
				boolean isLike = rset.getInt("is_like") == 1? true : false;
				String ownerUsername = rset.getString("user_username");
				LocalDate created = LocalDate.parse(rset.getString("created"));
				Video_Comment videoOrComment = Video_Comment.valueOf(rset.getString("video_comment"));
				int videoOrCommentID = rset.getInt("video_comment_id");
				
				likes_dislikes.add(new Like_Dislike(ID, isLike,ownerUsername, created, videoOrComment, videoOrCommentID));
			}
		}catch (SQLException e) { e.printStackTrace();}
		
		return likes_dislikes;
		
	}
	
	
	public static void uploadLikeDislike(Like_Dislike likeDislike) {
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		try {
			String query = "INSERT INTO LIKE_DISLIKE(is_like,user_username,created,video_comment,video_comment_id) VALUES(?,?,?,?,?);";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, likeDislike.isLike() == true? 1: 0);
			pstmt.setString(2, likeDislike.getOwnerUsername());
			pstmt.setString(3, likeDislike.getCreated().toString());
			pstmt.setString(4, likeDislike.getvideoOrComment().toString());
			pstmt.setInt(5, likeDislike.getVideoOrCommentID());
			
			pstmt.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void removeLikeDislike(Video_Comment videoOrComment, int videoOrCommentID, String ownerUsername) {
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		try {
			String query = "DELETE FROM LIKE_DISLIKE WHERE video_comment = ? AND video_comment_id = ? AND user_username = ?;";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, videoOrComment.toString());
			pstmt.setInt(2, videoOrCommentID);
			pstmt.setString(3, ownerUsername);
			
			pstmt.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public static ArrayList<Like_Dislike> getLikeDislikeForComment(int commentID) {
		ArrayList<Like_Dislike> likesDislikesForComments = new ArrayList<Like_Dislike>();
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		//System.out.println("Pristigli komentar u bazu: " + cmt.getContent() + cmt.getID());
		try {
			ResultSet rset = null;
			String query = "SELECT * FROM LIKE_DISLIKE WHERE video_comment = 'COMMENT' AND video_comment_id = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, commentID);
			rset = pstmt.executeQuery();
			while(rset.next()) {
				int ID = (rset.getInt("LIKE_DISLIKE_id"));
				boolean isLike = (rset.getInt("is_like")) == 1? true : false;
				String ownerUsername = rset.getString("user_username");
				LocalDate created = LocalDate.parse(rset.getString("created"));
				Video_Comment videoOrComment = Video_Comment.valueOf(rset.getString("video_comment"));
				int videoOrCommentID = rset.getInt("video_comment_id");
				
				Like_Dislike ld = new Like_Dislike(ID,isLike,ownerUsername,created,videoOrComment, videoOrCommentID);
				//System.out.println("Lajk/dislajk koji se vraca: " + ld.getOwnerUsername() + ld.getID());
				likesDislikesForComments.add(ld);

			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return likesDislikesForComments;
	}
	
	public static ArrayList<Like_Dislike> getAllForComment() {
		ArrayList<Like_Dislike> likesDislikesForComments = new ArrayList<Like_Dislike>();
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		try {
			ResultSet rset = null;
			String query = "SELECT * FROM LIKE_DISLIKE WHERE video_comment = 'COMMENT';";
			pstmt = conn.prepareStatement(query);
			rset = pstmt.executeQuery();
			while(rset.next()) {
				int ID = (rset.getInt("LIKE_DISLIKE_id"));
				boolean isLike = (rset.getInt("is_like")) == 1? true : false;
				String ownerUsername = rset.getString("user_username");
				LocalDate created = LocalDate.parse(rset.getString("created"));
				Video_Comment videoOrComment = Video_Comment.valueOf(rset.getString("video_comment"));
				int videoOrCommentID = rset.getInt("video_comment_id");
				
				Like_Dislike ld = new Like_Dislike(ID,isLike,ownerUsername,created,videoOrComment, videoOrCommentID);
				//System.out.println("Lajk/dislajk koji se vraca: " + ld.getOwnerUsername() + ld.getID());
				likesDislikesForComments.add(ld);
			}
			
			}catch (Exception e) {
				e.printStackTrace();
			}
			return likesDislikesForComments;
		}
}
