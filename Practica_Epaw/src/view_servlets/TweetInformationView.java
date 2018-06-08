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

import database.TweetDAO;
import models.BeanTweet;
import utils.BD;
import utils.ErrorMessages;
import utils.ServletUtilities;
import utils.GeneralUtils;
import utils.ValidationUtils;
import utils.JSONUtils;
import utils.Servlet;

@WebServlet("/tweetInformation")
public class TweetInformationView extends Servlet {
	
	private TweetDAO tweetDAO;
	
	public TweetInformationView() {
		this.tweetDAO = new TweetDAO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
							throws ServletException, IOException {
		this.doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
							throws ServletException, IOException {

		HttpSession session = this.getSession(request);
		int tweetID = Integer.valueOf((String) request.getParameter("tweetID"));
		
		BeanTweet tweetBD = this.tweetDAO.returnTweet(tweetID);
		
		if (ValidationUtils.isNull(tweetBD) == false) {
			session.setAttribute("tweetInfo", JSONUtils.getJSON(tweetBD));
		}
		
	}

}
