package servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import dao.UserDAO;
import model.User;

public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Register() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String name = request.getParameter("name");
		String lastname = request.getParameter("lastname");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		String message = "";
		String profilePictureURL = null;

		if (UserDAO.usernameExists(username) == true) {
			message += "failed";
		}
		if (UserDAO.emailExists(email) == true) {
			message += "failed";
		}

		if (name != null && lastname != null && username != null && password != null && email != null) {
			User user = new User(name, lastname, username, password, email, profilePictureURL);
			if (UserDAO.usernameExists(username) == false && UserDAO.emailExists(email) == false) {
				UserDAO.createUser(user);
				message = "success";
			}
		} else {
			message = "failed";
		}

		if (!name.equals(" ") && !lastname.equals(" ") && !username.equals(" ") && !password.equals(" ")
				&& !email.equals(" ")) {
			message = "success";
		} else {
			message = "failed";
		}

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("message", message);

		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(data);

		response.setContentType("application/json");
		response.getWriter().write(jsonData);
	}

}
