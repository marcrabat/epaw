package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.FeedbackDAO;
import database.TweetDAO;
import database.UserDAO;
import models.BeanTweet;
import models.BeanUser;
import utils.ErrorMessages;
import utils.SessionUtils;
import utils.ValidationUtils;
import utils.JSONUtils;
import utils.Servlet;

@WebServlet("/publishTweet")
public class PublishTweetController extends Servlet {
	
	private TweetDAO tweetDAO;
	private FeedbackDAO feedbackDAO;
	private UserDAO userDAO;
	
	
	public PublishTweetController() {
		this.tweetDAO = new TweetDAO();
		this.feedbackDAO = new FeedbackDAO();
		this.userDAO = new UserDAO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
							throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
							throws ServletException, IOException {
		
		ErrorMessages errors = new ErrorMessages();
		HttpSession session = this.getSession(request);
		
		if (session.getAttribute("Session_ID") != null) {
			
			errors.addError(this.publishTweet(request));
			
		}

		sendResponse(request, response, errors);
	}

	private ErrorMessages publishTweet(HttpServletRequest request) {
		BeanTweet tweet = this.getTweet(request);
		BeanUser author = SessionUtils.getSessionUser(request);
		ErrorMessages errors = new ErrorMessages();

		if (tweet != null) {
			
			if (ValidationUtils.isEmpty(tweet.getAuthor()) == true) {
				tweet.setAuthor(author.getUser());
			}

			errors = this.validateTweetInformation(tweet);

			if (errors.haveErrors() == false) {
				
				int commentTweetId = -1;
				String commentId = request.getParameter("commentTweetId");
				if (ValidationUtils.isNotNull(commentId) == true) {
					commentTweetId = Integer.valueOf(commentId);
				}
				
				
				if (ValidationUtils.isNull(commentTweetId) == true || commentTweetId == -1) {
					errors.addError(this.insertOrUpdateTweet(tweet));
				} else {
					errors.addError(this.commentTweet(tweet, commentTweetId));
				}
				
			}
		}
		
		return errors;
	}
	
	private BeanTweet getTweet(HttpServletRequest request) {
		BeanTweet tweet = null;
 		String jsonData = request.getParameter("data");
		if (ValidationUtils.isEmpty(jsonData) == false) {
			tweet = JSONUtils.returnJSONObject(jsonData, BeanTweet.class);
		}
		return tweet;
	}
	
	private ErrorMessages validateTweetInformation(BeanTweet tweet) {
		ErrorMessages errors = new ErrorMessages();
		
		if(ValidationUtils.isEmpty(tweet.getMessage()) == true) {
			errors.addError("tweet", "Tweet can not be empty");
		} else if (ValidationUtils.haveMaxLength(tweet.getMessage(), 226) == true){
			errors.addError("tweet", "Your tweet is to long, only accepts 225 characters");
		}
		
		return errors;
	}
	
	private ErrorMessages insertOrUpdateTweet(BeanTweet tweet) {
		ErrorMessages errors = new ErrorMessages();
		boolean existUser = this.userDAO.existUserSelectingField(UserDAO.COLUMN_USER, tweet.getAuthor());
		
		if (existUser == true) {
			
			boolean existTweet = this.tweetDAO.existTweet(tweet.getTweetID());
			
			if (existTweet == false) {
				errors.addError(this.insertTweet(tweet));
			} else {
				errors.addError(this.updateTweet(tweet));
			}
			
		} else {
			errors.addError("user", "User not exist");
		}
		
		return errors;
	}
	
	private ErrorMessages insertTweet(BeanTweet tweet) {
		ErrorMessages errors = new ErrorMessages();
		
		boolean insertTweet = this.tweetDAO.insertTweetAndPutHisId(tweet);
		
		if (insertTweet == false) {
			errors.addError("tweet", "Tweet can not be inserted");
		}
		
		return errors;
	}
	
	private ErrorMessages updateTweet(BeanTweet tweet) {
		ErrorMessages errors = new ErrorMessages();
		
		boolean updateTweet = this.tweetDAO.updateTweetAllInfo(tweet);
		
		if (updateTweet == false) {
			errors.addError("tweet", "Tweet can not be edited");
		}
		
		return errors;
	}
	
	private ErrorMessages commentTweet(BeanTweet commentTweet, int tweetId) {
		ErrorMessages errors = new ErrorMessages();
		
		
		errors = this.insertTweet(commentTweet);
		
		if (errors.haveErrors() == false) {
			
			boolean insertFeedback = this.feedbackDAO.associateTweet(tweetId, commentTweet.getTweetID());
			
			if (insertFeedback == false) {
				errors.addError("tweet", "Tweet can not be commented");
				this.tweetDAO.deleteTweet(commentTweet.getTweetID());
			}
			
		}
		
		return errors;
	}
	
	private void sendResponse(HttpServletRequest request, HttpServletResponse response, ErrorMessages errors)
																				throws ServletException, IOException {
		this.setResponseJSONHeader(response);
		request.setAttribute("errors", errors.getJSON());
		response.getWriter().print(errors.getJSON());
	}

}
