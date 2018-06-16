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

import database.FeedbackDAO;
import database.LikeDAO;
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
	private FeedbackDAO feedbackDAO;

	public FeedController() {
		this.tweetDAO = new TweetDAO();
		this.feedbackDAO = new FeedbackDAO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = null;
		List<BeanTweet> tweets = new ArrayList<BeanTweet>();
		List<BeanTweet> feedback = new ArrayList<BeanTweet>();

		ErrorMessages errors = new ErrorMessages();
		HttpSession session = this.getSession(request);
		String session_ID = (String) session.getAttribute("Session_ID");
		String userToLook = (String) session.getAttribute("userToLook");
		String mode = request.getParameter("mode");
		LikeDAO likeDao = new LikeDAO();

		if (session_ID == null) {
			errors.addError("requestError", "Your session is not valid");
			dispatcher = request.getRequestDispatcher("/main.jsp");
		} else if (session_ID == "anonymous") {
			int numberOfTweetsForAnonymous = 2; 
			System.out.println("TODO: Gestionar acces com a anonymous");
			
			tweets = tweetDAO.returnGlobalTimeline(numberOfTweetsForAnonymous);
			for(int i=0; i<tweets.size();i++) {
				BeanTweet tweet = tweets.get(i);
				tweet.setLikes(likeDao.countTweetLikes(tweet.getTweetID()));
				tweets.set(i, tweet);			
			}
			if (errors.haveErrors() == false) {
				sendResponseWithNoErrors(request, response, tweets);
			} else {
				sendResponseWithErrors(request, response, errors);
			}
		} else {
			switch (mode) {
				case "retrieveFeedbackForTweet":
					System.out.println("Retrieving feedback");
					int tweetToRetrieveFeedback = Integer.parseInt((String) request.getParameter("data"));
					
					// TEST
					//feedbackDAO.associateTweet(1, 2);
					//feedbackDAO.associateTweet(1, 3);
					List<Integer> feedbackTweetsID = feedbackDAO.getAssociated(tweetToRetrieveFeedback);
					if (ValidationUtils.isEmpty(feedbackTweetsID) == false) {
	
						for(Integer tweetID : feedbackTweetsID) {
							BeanTweet tweetToReturn = tweetDAO.returnTweet(tweetID);
							feedback.add(tweetToReturn);
						}
						session.setAttribute("tweetFeedback", Integer.toString(tweetToRetrieveFeedback));
					} else {
						//TODO: Decidir com volem mostrar que un tweet no té missatges.
						System.out.println("No messages available");
					}
					if (errors.haveErrors() == false) {
						sendResponseWithNoErrors(request, response, feedback);
					} else {
						sendResponseWithErrors(request, response, errors);
					}
					break;
				
				case "retrieveListOfTweetsForUser":
					if (ValidationUtils.isEmpty(userToLook) == false) {
						tweets = tweetDAO.returnGlobalTimeline(20);
					}
					tweets = tweetDAO.returnGlobalTimeline(20);
					for(int i=0; i<tweets.size();i++) {
						BeanTweet tweet = tweets.get(i);
						tweet.setLikes(likeDao.countTweetLikes(tweet.getTweetID()));
						tweets.set(i, tweet);			
					}
					if (errors.haveErrors() == false) {
						sendResponseWithNoErrors(request, response, tweets);
					} else {
						sendResponseWithErrors(request, response, errors);
					}
					break;
				
				case "insertLikeForTweet":
	
					int numLikes = 0;
					int tweetID = Integer.parseInt((String) request.getParameter("tweetID"));
					String username = (String) request.getParameter("username");
					
					if(!likeDao.checkUserLike(tweetID, username)) likeDao.insertUserLike(tweetID, username);
					else likeDao.deleteUserLike(tweetID, username);
					
					if (errors.haveErrors() == false) {
						sendLikesResponseWithNoErrors(request, response, numLikes);
					} else {
						sendResponseWithErrors(request, response, errors);
					}
					break;
					
				case "deleteTweet":
					errors.addError(this.deleteTweet(request));
					sendResponseWithErrors(request, response, errors);
					break;
			}
			
		}

	}

	private void sendResponseWithErrors(HttpServletRequest request, HttpServletResponse response, ErrorMessages errors)
			throws ServletException, IOException {
		this.setResponseJSONHeader(response);
		request.setAttribute("errors", errors.getJSON());
		response.getWriter().print(errors.getJSON());
	}

	private void sendResponseWithNoErrors(HttpServletRequest request, HttpServletResponse response,
			List<BeanTweet> tweets) throws ServletException, IOException {
		this.setResponseJSONHeader(response);
		request.setAttribute("tweets", JSONUtils.getJSON(tweets));
		response.getWriter().print(JSONUtils.getJSON(tweets));
	}
	
	private void sendLikesResponseWithNoErrors(HttpServletRequest request, HttpServletResponse response,
			int numLikes) throws ServletException, IOException {
		this.setResponseJSONHeader(response);
		request.setAttribute("tweets", JSONUtils.getJSON(numLikes));
		response.getWriter().print(JSONUtils.getJSON(numLikes));
	}
	
	private ErrorMessages deleteTweet(HttpServletRequest request) {
		ErrorMessages errors = new ErrorMessages();
		
		int tweetToDelete = Integer.valueOf(request.getParameter("data"));
		
		if (ValidationUtils.isNotNull(tweetToDelete) == true
				&& ValidationUtils.isNotNaN(tweetToDelete) == true) {
			
			boolean deleteRetweetsFeedback = this.feedbackDAO.deleteRetweetsAllAssociations(tweetToDelete);
			boolean deleteFeedback = this.feedbackDAO.deleteTweetAllAssociations(tweetToDelete);			
			
			boolean deleteRetweet = this.tweetDAO.deleteRetweets(tweetToDelete);
			boolean deleteTweet = this.tweetDAO.deleteTweet(tweetToDelete);
			
			
			
			if (deleteTweet == false) {
				errors.addError("deleteTweet", "The tweet can not be deleted!");
			}
			
		}
		
		return errors;
	}

}
