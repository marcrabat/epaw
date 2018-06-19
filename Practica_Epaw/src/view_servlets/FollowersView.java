package view_servlets;

import java.io.IOException;

import java.lang.reflect.InvocationTargetException;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;

import database.UserDAO;
import models.BeanUser;
import utils.BD;
import utils.ErrorMessages;
import utils.ServletUtilities;
import utils.GeneralUtils;
import utils.ValidationUtils;
import utils.JSONUtils;
import utils.Servlet;

@WebServlet("/followers")
public class FollowersView extends Servlet{
	public FollowersView() {}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
							throws ServletException, IOException {
		this.doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
							throws ServletException, IOException {

		
		String contextPath = request.getContextPath();
		HttpSession session = this.getSession(request);
		
		RequestDispatcher dispatcher;
		
		if (session.getAttribute("Session_ID") == null) {
			dispatcher = request.getRequestDispatcher("/main.jsp");
		} else {
			String mode = request.getParameter("mode");
			if(ValidationUtils.isNotNull(mode) == true) {
				session.setAttribute("followersViewMode", mode);
			}
			dispatcher = request.getRequestDispatcher("/followers.jsp");
		}
		
		dispatcher.forward(request, response);
	}
}
