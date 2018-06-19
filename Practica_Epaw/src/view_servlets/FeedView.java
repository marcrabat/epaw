package view_servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.UserDAO;
import models.BeanUser;
import utils.JSONUtils;
import utils.Servlet;
import utils.ValidationUtils;

@WebServlet("/feed")
public class FeedView extends Servlet {

	public FeedView() {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = this.getSession(request);
		RequestDispatcher dispatcher = null;
		String session_ID = (String) session.getAttribute("Session_ID");
		if (session_ID == null) {
			dispatcher = request.getRequestDispatcher("/main.jsp");
		} else {
			
			String userToLook = request.getParameter("userToLook");
			if(ValidationUtils.isNotNull(userToLook) == true) {
				session.setAttribute("userToLook", userToLook);
				UserDAO dao = new UserDAO();
				BeanUser user = dao.returnUser(UserDAO.COLUMN_USER, userToLook);
				session.setAttribute("userToLookInfo", JSONUtils.getJSON(user));
			}
			
			String mode = request.getParameter("mode");
			if(ValidationUtils.isNotNull(mode) == true) {
				session.setAttribute("modeOfFeed", mode);
			}
			
			dispatcher = request.getRequestDispatcher("/feed.jsp");
		}
			
		dispatcher.forward(request, response);
	}

}
