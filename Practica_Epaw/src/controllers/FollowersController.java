package controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.RelationshipDAO;
import database.TweetDAO;
import database.UserDAO;
import models.BeanUser;
import utils.ErrorMessages;
import utils.JSONUtils;
import utils.Servlet;
import utils.SessionUtils;
import utils.ValidationUtils;

@WebServlet("/checkFollowers")

public class FollowersController extends Servlet {
	
	public FollowersController() {}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
							throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
							throws ServletException, IOException {
		
		//ErrorMessages errors = new ErrorMessages();
		List<String> list = null;
		
		String mode = request.getParameter("mode");
		
		HttpSession session = this.getSession(request);
		
		if (session.getAttribute("Session_ID") != null) {
			
			switch(mode) {
			
				case "followers":
					//errors.addError(this.followersList(request));
					list = this.followersList(request);
					break;
				case "following":
					//errors.addError(this.followingList(request));
					break;			
			}
			
		}

		sendResponse(request, response, list);
	}

	
	private List<String> followersList(HttpServletRequest request) {
		List<String> followers = null;
		ErrorMessages errors = new ErrorMessages();
		RelationshipDAO relationshipDAO = new RelationshipDAO();
		String jsonData = request.getParameter("data");
		if (ValidationUtils.isEmpty(jsonData) == false) {
			followers = relationshipDAO.getFollowers(jsonData);
			errors.addError("followers", followers.toString());
		}
		return followers;
	}
		
	private ErrorMessages followingList(HttpServletRequest request) {
		
		ErrorMessages errors = new ErrorMessages();
		RelationshipDAO relationshipDAO = new RelationshipDAO();
		String jsonData = request.getParameter("data");
		if (ValidationUtils.isEmpty(jsonData) == false) {
			List<String> following = relationshipDAO.getFollowing(jsonData);
			errors.addError("following", following.toString());
		}
		return errors;
	}		
	
	
	private void sendResponse(HttpServletRequest request, HttpServletResponse response, List<String> list) throws ServletException, IOException {
		this.setResponseJSONHeader(response);
		request.setAttribute("errors", JSONUtils.getJSON(list));
		response.getWriter().print(JSONUtils.getJSON(list));
	}

}

