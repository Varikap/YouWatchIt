package servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import model.User;

public class SessionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public SessionServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("session").equals("provera")) {
			
				String userStatus = "Authenticated";
				
				HttpSession session = request.getSession();
				User ulogovanUser = (User)session.getAttribute("loggedInUser");
				if(ulogovanUser == null)
					userStatus = "Unauthenticated";
				
				Map<String, User> data = new HashMap<String, User>();
				data.put(userStatus, ulogovanUser);
				ObjectMapper mapper = new ObjectMapper();
				String jsonData = mapper.writeValueAsString(data);
				
				response.setContentType("application/json");
				response.getWriter().write(jsonData);
				
			}
			
			if(request.getParameter("session").equals("deaktivacija") ) {
				HttpSession session=request.getSession();  
	            session.invalidate();  
				
			}
			
	}
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
