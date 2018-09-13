package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import dao.VideoDAO;
import dao.UserDAO;
import model.User;
import model.Video;

public class Profile extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Profile() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// single page user
		if (request.getParameter("username") != null && request.getParameter("videos") == null) {
			String username = request.getParameter("username");
			User user = UserDAO.getUser(username);
			ObjectMapper mapper = new ObjectMapper();
			String jsonData = mapper.writeValueAsString(user);
			response.setContentType("application/json");
			response.getWriter().write(jsonData);
		}

		if (request.getParameter("videos") != null && request.getParameter("username") != null) {
			String username = request.getParameter("username");
			ArrayList<Video> listav = VideoDAO.getVideoForUser(username);
			ObjectMapper mapper = new ObjectMapper();
			String jsonData = mapper.writeValueAsString(listav);
			response.setContentType("application/json");
			response.getWriter().write(jsonData);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}
