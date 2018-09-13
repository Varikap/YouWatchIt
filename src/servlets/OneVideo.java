package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import dao.Like_DislikeDAO;
import dao.UserDAO;
import dao.VideoDAO;
import model.Comment;
import model.Like_Dislike;
import model.User;
import model.Video;

public class OneVideo extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public OneVideo() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//premesti na Session servlet
		String userStatus = "Authenticated";

		HttpSession session = request.getSession();
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		if (loggedInUser == null) {
			userStatus = "Unauthenticated";
		}

		int videoID = Integer.parseInt(request.getParameter("videoID"));
		Video video = VideoDAO.getVideo(videoID);

		ArrayList<Like_Dislike> videoLikesDislikes = VideoDAO.getLikesDislikesForVideo(videoID);
		ArrayList<Comment> videoComments = VideoDAO.getCommentsForVideo(videoID);
		ArrayList<Like_Dislike> commentLikeDislike = Like_DislikeDAO.getAllForComment();

		// Update usera
		if (userStatus.equals("Authenticated")) {
			loggedInUser = UserDAO.getUser(loggedInUser.getUsername());
		}

		boolean videoOwnerBlocked = UserDAO.isUserBlocked(video.getOwnerUsername()) == 1 ? true : false;

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("video", video);
		data.put("videoLikesDislikes", videoLikesDislikes);
		data.put("comments", videoComments);
		data.put("commentsLikeDislike", commentLikeDislike);
		data.put("userStatus", userStatus);
		data.put("videoOwnerBlocked", videoOwnerBlocked);
		if (userStatus == "Authenticated") {
			data.put("loggedInUser", loggedInUser);
		}
		ObjectMapper mapper = new ObjectMapper();

		String jsonData = mapper.writeValueAsString(data);
		response.setContentType("application/json");
		response.getWriter().write(jsonData);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int videoID = Integer.parseInt(request.getParameter("videoID"));
		String parameter = (String) request.getParameter("parameter");
		String value = (String) request.getParameter("value");

		VideoDAO.updateForVideo(videoID, parameter, value);

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("message", "success");
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(data);
		response.setContentType("application/json");
		response.getWriter().write(jsonData);
	}

}
