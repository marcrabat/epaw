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

		if (session_ID == null) {
			errors.addError("requestError", "Your session is not valid");
			dispatcher = request.getRequestDispatcher("/main.jsp");
		} else if (session_ID == "anonymous") {
			System.out.println("TODO: Gestionar acces com a anonymous");
		} else {

			switch (mode) {
			case "retrieveFeedbackForTweet":
				System.out.println("Retrieving feedback");
				int tweetToRetrieveFeedback = Integer.parseInt((String) request.getParameter("data"));
				
				// TEST
				//feedbackDAO.associateTweet(1, 2); //associo tweetID 2 com a resposta d'1
				List<Integer> feedbackTweetsID = feedbackDAO.getAssociated(tweetToRetrieveFeedback);
				if (ValidationUtils.isEmpty(feedbackTweetsID) == false) {
					
					//Recorro llista d'ids a retornar i els afegeixo al feedback
					for(Integer tweetID : feedbackTweetsID) {
						BeanTweet tweetToReturn = tweetDAO.returnTweet(tweetID);
						System.out.print(tweetToReturn.toString());
						feedback.add(tweetToReturn);
					}
				} else {
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
				if (errors.haveErrors() == false) {
					sendResponseWithNoErrors(request, response, tweets);
				} else {
					sendResponseWithErrors(request, response, errors);
				}
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

}
