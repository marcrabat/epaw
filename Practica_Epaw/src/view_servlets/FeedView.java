package view_servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import utils.Servlet;

@WebServlet("/feed")
public class FeedView extends Servlet {
	private static final long serialVersionUID = 1L;
       
    public FeedView() {}
    

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = this.getSession(request);			
		RequestDispatcher dispatcher;
		
		
		if (session.getAttribute("Session_ID") == null) {
			dispatcher = request.getRequestDispatcher("/main.jsp");
			
		} else {
			System.out.println("Enter with session: " + session.getAttribute("Session_ID"));
			dispatcher = request.getRequestDispatcher("/feed.jsp");
		}
		
		
		dispatcher.forward(request, response);
	}

}
