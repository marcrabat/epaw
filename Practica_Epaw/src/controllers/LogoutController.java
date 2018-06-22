 package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import utils.Servlet;

@WebServlet("/logout")
public class LogoutController extends Servlet {

	public LogoutController() {}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
							throws ServletException, IOException {
		this.doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
							throws ServletException, IOException {
		
		
		HttpSession session = this.getSession(request);
		
		session.invalidate();
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/main");
		dispatcher.forward(request, response);
		
	}

}
