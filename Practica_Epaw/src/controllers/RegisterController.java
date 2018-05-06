package controllers;

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

@WebServlet("/register")
public class RegisterController extends Servlet {
	
	public RegisterController() {}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
							throws ServletException, IOException {
		this.doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
							throws ServletException, IOException {
		
		UserDAO userDAO = new UserDAO();
		// JSONUtils Falla, crec que hi ha un problema amb la llibreria.
		
		boolean result = false;
		//BeanUser vistaUser = JSONUtils.returnJSONObject(request.getParameter("user"), BeanUser.class);
		
		/* Codigo provisional, cunado sepa que recivo des de la vista, y que tengo que devolver lo adapto */
		
		
		//TEST INSERT USER
		BeanUser vistaUser = new BeanUser();
		//TODO Do the same with view data
		vistaUser.setUser("UserMarc");
		vistaUser.setName("Marc");
		vistaUser.setPassword("emdicmarc");
		vistaUser.setDescription("aixo es una prova");
		List<String> genres = new LinkedList<String>();
		genres.add("genre1");
		vistaUser.setGameGenres(genres);
		vistaUser.setGender("Male");
		vistaUser.setMail("marc@mail.com");
		vistaUser.setSurname("Test");
		vistaUser.setTwitchChannelID("https://www.twitch.tv/twitchchan");
		vistaUser.setYoutubeChannelID("https://www.youtube.com/channel/youtubechan");
		List<String> consoles = new LinkedList<String>();
		consoles.add("console1");
		vistaUser.setUserConsoles(consoles);
		vistaUser.setBirthDate(1996, 5, 4); //Ull que esta deprecated
		//userDAO.insertUser(vistaUser);
		//Many ways of delete, for consistence, preferabily user and mail
		//userDAO.deleteUser("user", "UserMarc");
		//userDAO.deleteUser("mail", "marc@mail.com");
		
		System.out.println(JSONUtils.getJSON(vistaUser));
		
		// Fill the bean with the request parmeters
		/*
		try {
			BeanUtils.populate(vistaUser, request.getParameterMap());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		*/
		
		ErrorMessages errors = this.validateUserInformation(vistaUser);
		if (errors.haveErrors() == false) {
			
			System.out.println("No Errors");
			
			result = true;

			if (userDAO.existUser(vistaUser.getUser(), vistaUser.getMail()) == false) { //TODO falla, diu que l'statement no es tanca, pero es tanca
				
				System.out.println("No Exist");
				result = userDAO.insertUser(vistaUser);
				if (result == false) { System.out.println("Errors"); errors.addError("userInsert", "The user can not be inserted in BD"); }
			} else {
				
				System.out.println("Exist user");
				errors.addError("userExist", "The user already exist!!!");
			}
			
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
		String youtubeChannel = "https://www.youtube.com/channel/";
		String twitchChannel = "https://www.twitch.tv/";
		ErrorMessages error = new ErrorMessages();
		
		if ((ValidationUtils.isEmpty(user.getName())) == true
				|| (ValidationUtils.isBetweenLength(user.getName(), 3, 30)) == false) {
			error.addError("name", "The field is wrong!");
		}
		
		if ((ValidationUtils.isEmpty(user.getSurname())) == true
				|| (ValidationUtils.isBetweenLength(user.getSurname(), 3, 30)) == false) {
			error.addError("surname", "The field is wrong!");
		}
		
		if ((ValidationUtils.isEmpty(user.getMail()) == true)
			|| (ValidationUtils.isPatternMatches(ValidationUtils.REGEX_EMAIL, user.getMail()) == false)) {

			error.addError("email", "The email need to have the format xx@xx.xx");
			
			/*
			String[] aux = GeneralUtils.split(user.getMail(), "@");
			if (aux.length != 2) {
				error.addError("email", "The email need to have the format xx@xx");
			}
			*/
		}
		
		if (ValidationUtils.isEmpty(user.getBirthDate()) == false) {
			//int age = Calendar.getInstance().YEAR - user.getBirthDate().getYear();
			int age = 18;
			if (ValidationUtils.isBetweenLength(age, 18, 90) == false) {
				error.addError("birthDate", "the birthDate is not correct, your age is not between 18 and 90");
			}
		} else {
			error.addError("birthDate", "the field is wrong");
		}
		
		if ((ValidationUtils.isEmpty(user.getUser())) == true
				|| (ValidationUtils.isBetweenLength(user.getUser(), 4, 30)) == false) {
			error.addError("user", "The field is wrong!");
		}
		
		if ((ValidationUtils.isEmpty(user.getPassword())) == true
				|| (ValidationUtils.isBetweenLength(user.getPassword(), 8, 20)) == false) {
			error.addError("password", "The field is wrong!");
		}
		
		if (ValidationUtils.haveMaxLength(user.getDescription(), 255) == true) {
			error.addError("description", "the field not accept more of 255 characters!");
		}

		if ((ValidationUtils.isEmpty(user.getYoutubeChannelID()) == false)
			&& (ValidationUtils.equals(user.getYoutubeChannelID().substring(0, youtubeChannel.length()), youtubeChannel) == false)) {
			error.addError("youtube", "the field is wrong");
		}
		
		if ((ValidationUtils.isEmpty(user.getTwitchChannelID()) == false)
			&& (ValidationUtils.equals(user.getTwitchChannelID().substring(0, twitchChannel.length()), twitchChannel) == false)) {
			error.addError("twitch", "the field is wrong");
		}

		return error;
	}

}
