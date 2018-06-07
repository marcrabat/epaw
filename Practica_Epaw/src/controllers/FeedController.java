package controllers;

import java.io.IOException;
import java.util.ArrayList;
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
		List<BeanTweet> tweets = new ArrayList<BeanTweet>();
		ErrorMessages errors = new ErrorMessages();
		RequestDispatcher dispatcher = null;
		HttpSession session = this.getSession(request);
		String session_ID = (String) session.getAttribute("Session_ID");
		String userToLookFeed = (String) session.getAttribute("userToLookFeed");

		if (session_ID == null) {
			errors.addError("requestError", "Your session is not valid");
			dispatcher = request.getRequestDispatcher("/main.jsp");
		} else if(session_ID == "anonymous") {
			System.out.println("TODO: Gestionar acces com a anonymous");
		} else {
			if(ValidationUtils.isEmpty(userToLookFeed) == false){
				//vaig a globalTimeLine
			}
			System.out.println("Generar response per usuari loggejat");
			BeanTweet anonymous = tweetDAO.returnTweet(123);
			tweets.add(anonymous);
		}
		
		if(errors.haveErrors() == false){
			sendResponseWithNoErrors(request, response, tweets);
		} else{
			sendResponseWithErrors(request, response, errors);
		}
		
	}

	private void sendResponseWithErrors(HttpServletRequest request, HttpServletResponse response, ErrorMessages errors)
			throws ServletException, IOException {
		this.setResponseJSONHeader(response);
		request.setAttribute("errors", errors.getJSON());
		response.getWriter().print(errors.getJSON());
	}
	
	private void sendResponseWithNoErrors(HttpServletRequest request, HttpServletResponse response, List<BeanTweet> tweets)
			throws ServletException, IOException {
		this.setResponseJSONHeader(response);
		request.setAttribute("tweets", JSONUtils.getJSON(tweets));
		response.getWriter().print(JSONUtils.getJSON(tweets));
	}


}
