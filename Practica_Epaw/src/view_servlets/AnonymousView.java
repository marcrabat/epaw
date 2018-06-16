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

@WebServlet("/anonymous")
public class AnonymousView extends Servlet {
	
	private static final long serialVersionUID = 1L;

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