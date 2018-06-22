package view_servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import utils.Servlet;

@WebServlet("/anonymous")
public class AnonymousView extends Servlet {
	
	public AnonymousView() {}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		RequestDispatcher dispatcher = null;
		HttpSession session = this.getSession(request);
		session.setAttribute("Session_ID", "anonymous");
		dispatcher = request.getRequestDispatcher("/feed");
		dispatcher.forward(request, response);
	}

}