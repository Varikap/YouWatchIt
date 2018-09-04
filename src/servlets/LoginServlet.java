package servlets;

import java.io.IOException;
import java.util.HashMap;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.fasterxml.jackson.databind.ObjectMapper;

import dao.UserDAO;
import model.User;


public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public LoginServlet() {
        super();
       
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String msg = "Success";
		
		if(!UserDAO.usernameExists(username))
			msg = "Username does not exist";
		else {
			User user = UserDAO.getUser(username);
			if (!user.getPassword().equals(password))
				msg = "password is incorrect";
			else {
				HttpSession session = request.getSession();
				session.setAttribute("loggedInUser", user);
				System.out.println(user.getName());
			}
		}
		HashMap<String, Object> data = new HashMap<String,Object>();
		data.put("msg", msg);
		System.out.println(msg);
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(data);
		
		response.setContentType("application/json");
		response.getWriter().write(jsonData);
		return;
	}
}
