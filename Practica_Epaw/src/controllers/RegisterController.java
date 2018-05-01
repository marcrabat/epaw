package controllers;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import models.BeanUser;
import utils.BD;
import utils.ErrorMessages;
import utils.ServletUtilities;
import utils.GeneralUtils;
import utils.ValidationUtils;
import utils.JSONUtils;
import utils.Servlet;

@WebServlet("/FormController")
public class RegisterController extends Servlet {
	
	public RegisterController() {}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
							throws ServletException, IOException {
		this.doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
							throws ServletException, IOException {
		
		// JSONUtils Falla, crec que hi ha un problema amb la llibreria.
		
		boolean result = false;
		//BeanUser vistaUser = JSONUtils.returnJSONObject(request.getParameter("user"), BeanUser.class);
		
		/* Codigo provisional, cunado sepa que recivo des de la vista, y que tengo que devolver lo adapto */
		
		BeanUser vistaUser = new BeanUser();
		// Fill the bean with the request parmeters
		try {
			BeanUtils.populate(vistaUser, request.getParameterMap());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
		ErrorMessages errors = this.validateUserInformation(vistaUser);
		if (errors.haveErrors() == false) {
			
			result = true;
			
			/*
			if (userDAO.existUser(user.getUser(), user.getMail()) == false) {
				userDAO.insertUser(user);
				result = true;
			} else {
				error.addError("userExist", "The user already exist!!!");
			}
			*/
			
		}

		//System.out.println(JSONUtils.getJSON(errors));
		
		// Put the bean into the request as an attribute
		request.setAttribute("user", vistaUser);
		request.setAttribute("result", result);
		//request.setAttribute("errors", JSONUtils.getJSON(errors));
		RequestDispatcher dispatcher = request.getRequestDispatcher("/RegisterForm.jsp");
		dispatcher.forward(request, response);
		
	}
	
	private ErrorMessages validateUserInformation(BeanUser user) {
		ErrorMessages error = new ErrorMessages();
		
		if ((ValidationUtils.isEmpty(user.getUser())) == true
				&& (ValidationUtils.haveMinimumLength(user.getUser(), 5)) == false) {
			error.addError("user", "The field is wrong!");
		}
		
		if (ValidationUtils.isEmpty(user.getMail()) == true) {
			String[] aux = GeneralUtils.split(user.getMail(), "@");
			if (aux.length != 2) {
				error.addError("email", "The email need to have the format xx@xx");
			}
		}
		
		return error;
	}

}
