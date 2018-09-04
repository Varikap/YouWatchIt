package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import dao.VideoDAO;
import model.Video;

public class GetVideos extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public GetVideos() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<Video> videos = VideoDAO.getAll(null, null, 0, 0, null, null, null);
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(videos);
		response.setContentType("application/json");
		response.getWriter().write(jsonData);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
