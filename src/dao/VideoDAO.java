package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import enums.Video_Comment;
import enums.Video_Visibility;
import model.Comment;
import model.Like_Dislike;
import model.Video;
import utils.ConnectionManager;

public class VideoDAO {
	public static ArrayList<Video> getAll(String searchedVideo, String user, int minViews, int maxViews,
			LocalDate startDate, LocalDate endDate, String sortBy) {

		/*
		 * System.out.println("--------------------------------");
		 * System.out.println("Ispis svih primljenih u komunikaciji sa bazom");
		 * System.out.println("Pretrazeni filter: " + searchedVideo);
		 * System.out.println("Pretrazeno po useru: " + user);
		 * System.out.println("Minimalan broj pregleda: " + minViews);
		 * System.out.println("Maksimalan broj pregleda: " + maxViews);
		 * System.out.println("Od datuma: " + startDate);
		 * System.out.println("Do datuma: " + endDate);
		 */

		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		ArrayList<Video> videos = new ArrayList<Video>();

		try {
			ResultSet rset = null;
			String query = null;
			/*
			 * System.out.println(searchedVideo == ""); System.out.println(user == "");
			 * System.out.println(minViews == 0); System.out.println(maxViews == 0);
			 * System.out.println(startDate.equals(LocalDate.now()));
			 * System.out.println(endDate.equals(LocalDate.now())); System.out.println();
			 */
			if (searchedVideo == null && user == null && minViews == 0 && maxViews == 0 && startDate == null
					&& endDate == null && sortBy == null) {
				query = "SELECT * FROM VIDEOS";
				pstmt = conn.prepareStatement(query);
				// System.out.println("Prvi statement dobar");
			} else {
				query = "SELECT * FROM VIDEOS WHERE video_name LIKE ? AND owner_username LIKE ? AND views BETWEEN ? AND ? AND created BETWEEN ? AND ? AND deleted = 0";
				if (sortBy == null) {
					query += " ORDER BY created DESC;";
				} else {
					switch (sortBy) {
					case "nameAsc":
						query += " ORDER BY video_name ASC;";
						break;
					case "nameDesc":
						query += " ORDER BY video_name DESC;";
						break;
					case "viewsAsc":
						query += " ORDER BY views ASC;";
						break;
					case "viewsDesc":
						query += " ORDER BY views DESC;";
						break;
					case "dateAsc":
						query += " ORDER BY created ASC;";
						break;
					case "dateDesc":
						query += " ORDER BY created DESC;";
						break;
					}
				}

				pstmt = conn.prepareStatement(query);
				if (searchedVideo == null) {
					pstmt.setString(1, "%");
				} else {
					pstmt.setString(1, "%" + searchedVideo + "%");
				}

				if (user == null) {
					pstmt.setString(2, "%");
				} else {
					pstmt.setString(2, "%" + user + "%");
				}

				if (minViews < 0) {
					pstmt.setString(3, "%");
				} else {
					pstmt.setInt(3, minViews);
				}

				if (maxViews < 0) {
					pstmt.setString(4, "%");
				} else {
					pstmt.setInt(4, maxViews);
				}

				if (startDate == null) {
					pstmt.setString(5, "%");
				} else {
					pstmt.setString(5, startDate.toString());
				}

				if (endDate == null) {
					pstmt.setString(6, LocalDate.now().toString());
				} else {
					pstmt.setString(6, endDate.toString());
				}

				// System.out.println("Izgled queryja:");
				// System.out.println(pstmt);
				// System.out.println("SELECT * FROM video WHERE video_name LIKE " +
				// searchedVideo.toString() + " AND owner_username = " + user + " AND views
				// BETWEEN " +minViews +" AND "+maxViews+" AND created BETWEEN "+ startDate+"
				// AND "+ endDate);
				// System.out.println("Drugi statement dobar");
			}

			rset = pstmt.executeQuery();
			while (rset.next()) {
				int its_id = rset.getInt("video_id");
				String name = rset.getString("video_name");
				String videoURL = rset.getString("video_embedURL");
				String imageURL = rset.getString("imageURL");
				String description = rset.getString("description");

				Video_Visibility visibility = Video_Visibility.valueOf(rset.getString("visibility"));
				boolean ratingVisibility = rset.getInt("rating_visibility") == 1 ? true : false;
				boolean commentsAllowed = rset.getInt("comments_allowed") == 1 ? true : false;

				boolean blocked = rset.getInt("blocked") == 1 ? true : false;
				int views = rset.getInt("views");
				LocalDate created = LocalDate.parse((rset.getString("created")));
				String ownerUsername = rset.getString("owner_username");

				videos.add(new Video(its_id, name, videoURL, imageURL, description, visibility, commentsAllowed,
						ratingVisibility, blocked, views, created, ownerUsername));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return videos;
	}

	public static ArrayList<Video> getVideoForUser(String x) {
		ArrayList<Video> userVideos = new ArrayList<Video>();
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		try {
			ResultSet rset = null;
			String query = "SELECT * FROM VIDEOS WHERE owner_username = ? AND deleted = 0";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, x);
			rset = pstmt.executeQuery();

			while (rset.next()) {
				int itsID = rset.getInt("video_id");
				String name = rset.getString("video_name");
				String videoURL = rset.getString("video_embedURL");
				String imageURL = rset.getString("imageURL");
				String description = rset.getString("description");

				Video_Visibility visibility = Video_Visibility.valueOf(rset.getString("visibility"));
				boolean ratingVisibility = rset.getInt("rating_visibility") == 1 ? true : false;
				boolean commentsAllowed = rset.getInt("comments_allowed") == 1 ? true : false;

				boolean blocked = rset.getInt("blocked") == 1 ? true : false;
				int views = rset.getInt("views");
				LocalDate created = LocalDate.parse((rset.getString("created")));
				String ownerUsername = rset.getString("owner_username");

				userVideos.add(new Video(itsID, name, videoURL, imageURL, description, visibility, commentsAllowed,
						ratingVisibility, blocked, views, created, ownerUsername));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return userVideos;
	}

	public static boolean videoNameExists(String videoName) {
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		try {
			ResultSet rset = null;
			String query = "SELECT * FROM VIDEOS WHERE video_name LIKE ? AND deleted = 0";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, videoName);
			rset = pstmt.executeQuery();
			if (rset.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean videoImageURLExists(String imageURL) {
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		try {
			ResultSet rset = null;
			String query = "SELECT * FROM VIDEOS WHERE imageURL LIKE ? AND deleted = 0";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, imageURL);
			rset = pstmt.executeQuery();
			if (rset.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean videoURLExists(String videoURL) {
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		try {
			ResultSet rset = null;
			String query = "SELECT * FROM VIDEOS WHERE video_embedURL = ? AND deleted = 0";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, videoURL);
			rset = pstmt.executeQuery();
			// System.out.println(pstmt);
			if (rset.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	// public static void uploadVideo()

	public static Video getVideo(int id) {
		Connection conn = ConnectionManager.getConnection();

		PreparedStatement pstmt = null;

		try {
			ResultSet rset = null;
			String query = "UPDATE VIDEOS SET views = views + 1 WHERE video_id = ? AND deleted = 0";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			ResultSet rset = null;
			String query = "SELECT * FROM VIDEOS WHERE video_id=? AND deleted = 0";
			int index = 1;
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, id);
			// System.out.println(pstmt);

			rset = pstmt.executeQuery();
			while (rset.next()) {
				int its_id = rset.getInt("video_id");
				String name = rset.getString("video_name");
				String videoURL = rset.getString("video_embedURL");
				String imageURL = rset.getString("imageURL");
				String description = rset.getString("description");

				Video_Visibility visibility = Video_Visibility.valueOf(rset.getString("visibility"));
				boolean ratingVisibility = rset.getInt("rating_visibility") == 1 ? true : false;
				boolean commentsAllowed = rset.getInt("comments_allowed") == 1 ? true : false;

				boolean blocked = rset.getInt("blocked") == 1 ? true : false;
				int views = rset.getInt("views");
				LocalDate created = LocalDate.parse((rset.getString("created")));
				String ownerUsername = rset.getString("owner_username");

				return new Video(its_id, name, videoURL, imageURL, description, visibility, commentsAllowed,
						ratingVisibility, blocked, views, created, ownerUsername);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static int uploadVideo(Video video) {
		int generatedID = 0;
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		try {
			String query = "INSERT INTO VIDEOS(video_name,video_embedURL,imageURL,description,visibility,rating_visibility,comments_allowed,blocked,views,created,owner_username,deleted) VALUES (?,?,?,?,?,?,?,?,?,?,?,0)";
			pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, video.getName());
			pstmt.setString(2, video.getVideoURL());
			pstmt.setString(3, video.getimageURL());
			pstmt.setString(4, video.getdescription());
			pstmt.setString(5, video.getvisibility().toString());
			pstmt.setInt(6, video.isratingVisibility() == true ? 1 : 0);
			pstmt.setInt(7, video.iscommentsAllowed() == true ? 1 : 0);
			pstmt.setInt(8, video.isblocked() == true ? 1 : 0);
			pstmt.setInt(9, video.getviews());
			pstmt.setString(10, video.getcreated().toString());
			pstmt.setString(11, video.getOwnerUsername());

			pstmt.executeUpdate();

			ResultSet generatedKey = pstmt.getGeneratedKeys();
			while (generatedKey.next()) {
				generatedID = generatedKey.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return generatedID;
	}

	public static ArrayList<Like_Dislike> getLikesDislikesForVideo(int id) {

		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		ArrayList<Like_Dislike> likesDislikes = new ArrayList<Like_Dislike>();

		try {
			ResultSet rset = null;
			String query = "SELECT * FROM LIKE_DISLIKE WHERE video_comment = 'VIDEO' AND video_comment_id=?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, id);
			rset = pstmt.executeQuery();

			while (rset.next()) {
				int itsId = rset.getInt("LIKE_DISLIKE_id");
				boolean isLike = rset.getInt("is_like") == 1 ? true : false;
				String ownerUsername = rset.getString("user_username");
				LocalDate created = LocalDate.parse(rset.getString("created"));
				Video_Comment videoOrComment = Video_Comment.valueOf(rset.getString("video_comment"));
				int videoOrCommentID = rset.getInt("video_comment_id");

				likesDislikes
						.add(new Like_Dislike(itsId, isLike, ownerUsername, created, videoOrComment, videoOrCommentID));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return likesDislikes;

	}

	public static ArrayList<Comment> getCommentsForVideo(int id) {

		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		ArrayList<Comment> videoComments = new ArrayList<Comment>();

		try {
			ResultSet rset = null;
			String query = "SELECT * FROM comment WHERE video_id = ?";
			pstmt = conn.prepareCall(query);
			pstmt.setInt(1, id);
			rset = pstmt.executeQuery();

			while (rset.next()) {
				int itsId = rset.getInt("comment_id");
				String content = rset.getString("content");
				LocalDate created = LocalDate.parse(rset.getString("created"));
				String ownerUsername = rset.getString("user_username");
				int videoId = rset.getInt("video_id");

				videoComments.add(new Comment(itsId, content, created, ownerUsername, videoId));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return videoComments;

	}

	public static void updateForVideo(int id, String parameter, String value) {
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		try {
			System.out.println("PRISTIGLI PARAMETRI");
			System.out.println(id);
			System.out.println(parameter);
			System.out.println(value);
			String query = "UPDATE VIDEOS SET " + parameter.toString() + " = ? WHERE video_id = ?";
			pstmt = conn.prepareStatement(query);
			switch (parameter) {
			case "description":
				pstmt.setString(1, value);
				break;

			case "visibility":
				Video_Visibility visibility = Video_Visibility.PUBLIC;
				switch (visibility) {
				case PUBLIC:
					visibility = Video_Visibility.PUBLIC;
					break;
				case PRIVATE:
					visibility = Video_Visibility.PRIVATE;
					break;
				case UNLISTED:
					visibility = Video_Visibility.UNLISTED;
					break;
				}
				pstmt.setString(1, value.toString().toUpperCase());
				break;
			case "rating_visibility":
				pstmt.setInt(1, Integer.parseInt(value));
				break;
			case "comments_allowed":
				pstmt.setInt(1, Integer.parseInt(value));
				break;
			case "blocked":
				pstmt.setInt(1, Integer.parseInt(value));
				break;
			case "deleted":
				pstmt.setInt(1, Integer.parseInt(value));
				break;

			}
			pstmt.setInt(2, id);

			System.out.println("Izgled queryja: " + pstmt.toString());
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<Video> getVideosForQueriedUser(String x) {
		ArrayList<Video> videos = new ArrayList<Video>();
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		try {
			String query = "SELECT * FROM VIDEOS WHERE owner_username LIKE '%" + x + "%'";
			pstmt = conn.prepareStatement(query);
			// pstmt.setString(1, x);
			ResultSet rset = pstmt.executeQuery();
			while (rset.next()) {
				int its_id = rset.getInt("video_id");
				String name = rset.getString("video_name");
				String videoURL = rset.getString("video_embedURL");
				String imageURL = rset.getString("imageURL");
				String description = rset.getString("description");

				Video_Visibility visibility = Video_Visibility.valueOf(rset.getString("visibility"));
				boolean ratingVisibility = rset.getInt("rating_visibility") == 1 ? true : false;
				boolean commentsAllowed = rset.getInt("comments_allowed") == 1 ? true : false;

				boolean blocked = rset.getInt("blocked") == 1 ? true : false;
				int views = rset.getInt("views");
				LocalDate created = LocalDate.parse((rset.getString("created")));
				String ownerUsername = rset.getString("owner_username");

				videos.add(new Video(its_id, name, videoURL, imageURL, description, visibility, commentsAllowed,
						ratingVisibility, blocked, views, created, ownerUsername));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return videos;
	}

	public static ArrayList<Video> getVideosForQueriedComment(String x) {
		ArrayList<Video> videos = new ArrayList<Video>();
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		try {
			String query = "SELECT * FROM VIDEOS JOIN comment ON VIDEOS.video_id = comment.video_id WHERE comment.content LIKE'%"
					+ x + "%' AND VIDEOS.deleted = 0 GROUP BY VIDEOS.video_name";
			pstmt = conn.prepareStatement(query);
			// pstmt.setString(1, x);
			ResultSet rset = pstmt.executeQuery();
			while (rset.next()) {
				int its_id = rset.getInt("video_id");
				String name = rset.getString("video_name");
				String videoURL = rset.getString("video_embedURL");
				String imageURL = rset.getString("imageURL");
				String description = rset.getString("description");

				Video_Visibility visibility = Video_Visibility.valueOf(rset.getString("visibility"));
				boolean ratingVisibility = rset.getInt("rating_visibility") == 1 ? true : false;
				boolean commentsAllowed = rset.getInt("comments_allowed") == 1 ? true : false;

				boolean blocked = rset.getInt("blocked") == 1 ? true : false;
				int views = rset.getInt("views");
				LocalDate created = LocalDate.parse((rset.getString("created")));
				String ownerUsername = rset.getString("owner_username");

				videos.add(new Video(its_id, name, videoURL, imageURL, description, visibility, commentsAllowed,
						ratingVisibility, blocked, views, created, ownerUsername));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return videos;
	}
}
