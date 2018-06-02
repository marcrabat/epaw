package controllers;

import java.io.IOException;
import java.util.List;

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
import utils.SessionUtils;
import utils.GeneralUtils;
import utils.ValidationUtils;
import utils.JSONUtils;
import utils.Servlet;

@WebServlet("/checkFeedErrors")
public class FeedController extends Servlet {
	private static final long serialVersionUID = 1L;
	
	private TweetDAO tweetDAO;
	
	public FeedController() {
		this.tweetDAO = new TweetDAO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<BeanTweet> tweets = null;
		ErrorMessages errors = new ErrorMessages();
		RequestDispatcher dispatcher = null;
		HttpSession session = this.getSession(request);
		String session_ID = (String) session.getAttribute("Session_ID");

		if (session_ID == null) {
			errors.addError("requestError", "Your session is not valid");
			dispatcher = request.getRequestDispatcher("/main.jsp");
		} else if(session_ID == "anonymous") {
			System.out.println("TODO: Gestionar acces com a anonymous");
		} else {
			System.out.println("Generar response per usuari loggejat");
		}

		sendResponse(request, response, errors);
	}

	private void sendResponse(HttpServletRequest request, HttpServletResponse response, ErrorMessages errors)
			throws ServletException, IOException {
		this.setResponseJSONHeader(response);
		request.setAttribute("errors", errors.getJSON());
		response.getWriter().print(errors.getJSON());
	}

}
