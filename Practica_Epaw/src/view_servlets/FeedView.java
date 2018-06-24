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
import utils.ErrorMessages;
import utils.JSONUtils;
import utils.Servlet;
import utils.ValidationUtils;

@WebServlet("/feed")
public class FeedView extends Servlet {

	private UserDAO userDAO;
	
	public FeedView() {
		this.userDAO = new UserDAO();
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
		ErrorMessages errors = new ErrorMessages();
		
		if (session_ID == null) {
			dispatcher = request.getRequestDispatcher("/main.jsp");
		} else {
			this.putModeValueToSession(request);
			errors.addError(this.existUserToLook(request));
			
			if (errors.haveErrors() == false) {
				dispatcher = request.getRequestDispatcher("/feed.jsp");
			}
			
		}
		
		if (errors.haveErrors() == true) {
			sendResponse(response, errors);
		} else {
			dispatcher.forward(request, response);
		}
	}
	
	private void putModeValueToSession(HttpServletRequest request) {
		HttpSession session = this.getSession(request);
		String mode = request.getParameter("mode");
		if(ValidationUtils.isNotNull(mode) == true) {
			session.setAttribute("modeOfFeed", mode);
		}
	}
	
	private ErrorMessages existUserToLook(HttpServletRequest request) {
		ErrorMessages error = new ErrorMessages();
		HttpSession session = this.getSession(request);
		
		String userToLook = request.getParameter("userToLook");
		if(ValidationUtils.isNotNull(userToLook) == true) {
			session.setAttribute("userToLook", userToLook);
		}
		
		if(ValidationUtils.isNotEmpty(userToLook) == true) {
			
			boolean existUser = this.userDAO.existUserSelectingField(UserDAO.COLUMN_USER, userToLook);
			
			if (existUser == true) {
				BeanUser user = this.userDAO.returnUser(UserDAO.COLUMN_USER, userToLook);
				session.setAttribute("userToLookInfo", JSONUtils.getJSON(user));
			} else {
				error.addError("user", "user you want to look, not exist!");
			}

		}
		
		return error;
	}
	
	private void sendResponse(HttpServletResponse response, ErrorMessages errors)
												throws ServletException, IOException {
		this.setResponseJSONHeader(response);
		response.getWriter().print(errors.getJSON());
	}

}
