package view_servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.TweetDAO;
import models.BeanTweet;
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

		int tweetID = Integer.valueOf((String) request.getParameter("tweetID"));
		
		BeanTweet tweetBD = this.tweetDAO.returnTweet(tweetID);
		
		sendResponse(request, response, tweetBD);
		
	}
	
	private void sendResponse(HttpServletRequest request, HttpServletResponse response, BeanTweet tweet)
																		throws ServletException, IOException {
		this.setResponseJSONHeader(response);
		HttpSession session = this.getSession(request);
		
		if (ValidationUtils.isNull(tweet) == false) {
			session.setAttribute("tweetInfo", JSONUtils.getJSON(tweet));
		} else {
			tweet = new BeanTweet();
		}
		
		response.getWriter().print(JSONUtils.getJSON(tweet, "dd/MM/yyyy hh:mm:ss"));
	}

}
