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

import database.FeedbackDAO;
import database.LikeDAO;
import database.TweetDAO;
import database.UserDAO;
import models.BeanTweet;
import models.BeanUser;
import utils.ErrorMessages;
import utils.ValidationUtils;
import utils.JSONUtils;
import utils.Servlet;
import utils.SessionUtils;

@WebServlet("/checkFeedErrors")
public class FeedController extends Servlet {

	private TweetDAO tweetDAO;
	private FeedbackDAO feedbackDAO;
	private LikeDAO likeDAO;
	private UserDAO userDAO;
	
	private static final int numberOfTweetsForAnonymous = 2;

	public FeedController() {
		this.tweetDAO = new TweetDAO();
		this.feedbackDAO = new FeedbackDAO();
		this.likeDAO = new LikeDAO();
		this.userDAO = new UserDAO();
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

		if (ValidationUtils.isNotEmpty(userToLook) == true) {
			boolean existUser = this.userDAO.existUserSelectingField(UserDAO.COLUMN_USER, userToLook);
			if (existUser == false) {
				errors.addError("userNotExist", "The user not exist!");
			}
		}
		
		if (errors.haveErrors() == true) {
			sendResponseWithErrors(request, response, errors);
		} else {
		
			if (session_ID == null) {
	
				errors.addError("requestError", "Your session is not valid");
				dispatcher = request.getRequestDispatcher("/main.jsp");
				dispatcher.forward(request, response);
	
			} else if (session_ID == "anonymous") {
	
				switch (mode) {
	
				case "retrieveListOfTweetsForAnonymous":
					tweets = retrieveListOfTweetsForAnonymous(request);
					sendTweetsResponse(request, response, tweets, errors);
					break;
				
				case "retrieveFeedbackForAnonymousTweets":
					retrieveFeedbackForTweet(request, feedback, session);
					sendTweetsResponse(request, response, feedback, errors);
					break;
				}
	
			} else {
				
				switch (mode) {
				
					case "retrieveFeedbackForTweet":
						retrieveFeedbackForTweet(request, feedback, session);
						sendTweetsResponse(request, response, feedback, errors);
						break;
		
					case "retrieveListOfTweetsForUser":
						
						tweets = retrieveListOfTweetsForUser(userToLook);
						sendTweetsResponse(request, response, tweets, errors);
						break;
		
					case "insertLikeForTweet":
						int numLikes = insertDeinsertLikes(request);
						sendLikesResponse(request, response, errors, numLikes);
						break;
		
					case "deleteTweet":
						errors.addError(this.deleteTweet(request));
						sendResponseWithErrors(request, response, errors);
						break;
						
					case "viewFollowingTweets":
						tweets = this.viewFollowingTweets(request);
						if (tweets.size() == 0) {
							errors.addError("tweets", "You not are following anybody!");
						}
						sendTweetsResponse(request, response, tweets, errors);
						break;
				}
	
			}
		}

	}

	private int insertDeinsertLikes(HttpServletRequest request) {
		int numLikes = 0;
		int tweetID = Integer.parseInt((String) request.getParameter("tweetID"));
		String username = (String) request.getParameter("username");

		if (!this.likeDAO.checkUserLike(tweetID, username))
			this.likeDAO.insertUserLike(tweetID, username);
		else
			this.likeDAO.deleteUserLike(tweetID, username);
		return numLikes;
	}

	private List<BeanTweet> retrieveListOfTweetsForUser(String userToLook) {
		List<BeanTweet> tweets;
		if (ValidationUtils.isEmpty(userToLook) == false) {
			tweets = this.tweetDAO.returnUserFeed(userToLook);
		} else {
			tweets = this.tweetDAO.returnGlobalTimeline(20);
		}
		retrieveLikesForListOfTweets(tweets);
		return tweets;
	}

	private List<BeanTweet> retrieveListOfTweetsForAnonymous(HttpServletRequest request) {
		List<BeanTweet> tweets;
		String wantUserFeed = request.getParameter("data");
		if (ValidationUtils.isEmpty(wantUserFeed) == true) {
			tweets = this.tweetDAO.returnGlobalTimeline(numberOfTweetsForAnonymous);
		} else {
			tweets = this.tweetDAO.returnUserFeed(wantUserFeed);
		}
		retrieveLikesForListOfTweets(tweets);
		return tweets;
	}

	private void retrieveFeedbackForTweet(HttpServletRequest request, List<BeanTweet> feedback, HttpSession session) {
		int tweetToRetrieveFeedback = Integer.parseInt((String) request.getParameter("data"));
		List<Integer> feedbackTweetsID = this.feedbackDAO.getAssociated(tweetToRetrieveFeedback);
		if (ValidationUtils.isEmpty(feedbackTweetsID) == false) {

			for (Integer tweetID : feedbackTweetsID) {
				BeanTweet tweetToReturn = this.tweetDAO.returnTweet(tweetID);
				feedback.add(tweetToReturn);
			}
			session.setAttribute("tweetFeedback", Integer.toString(tweetToRetrieveFeedback));
		} 
	}

	private void sendLikesResponse(HttpServletRequest request, HttpServletResponse response, ErrorMessages errors,
			int numLikes) throws ServletException, IOException {
		if (errors.haveErrors() == false) {
			sendLikesResponseWithNoErrors(request, response, numLikes);
		} else {
			sendResponseWithErrors(request, response, errors);
		}
	}

	private void retrieveLikesForListOfTweets(List<BeanTweet> tweets) {
		for (int i = 0; i < tweets.size(); i++) {
			BeanTweet tweet = tweets.get(i);
			tweet.setLikes(this.likeDAO.countTweetLikes(tweet.getTweetID()));
			tweets.set(i, tweet);
		}
	}

	private void sendTweetsResponse(HttpServletRequest request, HttpServletResponse response, List<BeanTweet> tweets,
			ErrorMessages errors) throws ServletException, IOException {
		if (errors.haveErrors() == false) {
			sendResponseWithNoErrors(request, response, tweets);
		} else {
			sendResponseWithErrors(request, response, errors);
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

	private void sendLikesResponseWithNoErrors(HttpServletRequest request, HttpServletResponse response, int numLikes)
			throws ServletException, IOException {
		this.setResponseJSONHeader(response);
		request.setAttribute("tweets", JSONUtils.getJSON(numLikes));
		response.getWriter().print(JSONUtils.getJSON(numLikes));
	}

	private ErrorMessages deleteTweet(HttpServletRequest request) {
		ErrorMessages errors = new ErrorMessages();

		int tweetToDelete = Integer.valueOf(request.getParameter("data"));

		if (ValidationUtils.isNotNull(tweetToDelete) == true && ValidationUtils.isNotNaN(tweetToDelete) == true) {

			this.feedbackDAO.deleteRetweetsAllAssociations(tweetToDelete);
			this.feedbackDAO.deleteTweetAllAssociations(tweetToDelete);

			this.tweetDAO.deleteRetweets(tweetToDelete);
			boolean deleteTweet = this.tweetDAO.deleteTweet(tweetToDelete);

			if (deleteTweet == false) {
				errors.addError("deleteTweet", "The tweet can not be deleted!");
			}

		}

		return errors;
	}
	
	private List<BeanTweet> viewFollowingTweets(HttpServletRequest request) {
		List<BeanTweet> tweets = new ArrayList<BeanTweet>();
		BeanUser user = SessionUtils.getSessionUser(request);
		
		if (ValidationUtils.isNotNull(user) == true) {
			tweets = this.tweetDAO.returnTweetsOfFollowingUsers(user.getUser());
			retrieveLikesForListOfTweets(tweets);
		}

		return tweets;
	}

}
