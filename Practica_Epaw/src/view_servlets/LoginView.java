package view_servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utils.Servlet;

@WebServlet("/login")
public class LoginView extends Servlet {
	
	public LoginView() {}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
							throws ServletException, IOException {
		this.doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
							throws ServletException, IOException {

		RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
		dispatcher.forward(request, response);
		
	}

}
