package controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.RelationshipDAO;
import utils.JSONUtils;
import utils.Servlet;
import utils.ValidationUtils;

@WebServlet("/changeRelation")

public class ChangeRelationController extends Servlet {
	
	private RelationshipDAO relationshipDAO;
	
	public ChangeRelationController() {
		this.relationshipDAO = new RelationshipDAO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
							throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
							throws ServletException, IOException {
		
		String list = new String("");
		
		String mode = request.getParameter("mode");
		
		HttpSession session = this.getSession(request);
		
		if (session.getAttribute("Session_ID") != null) {
			
			switch(mode) {
			
				case "insert":
					boolean insert = insertRelationShip(request);
					break;
				case "delete":
					boolean delete = deleteRelationShip(request);
					break;			
			}
			
		}

		sendResponse(request, response, list);
	}

	
	private boolean insertRelationShip(HttpServletRequest request) {
		boolean answer=false;
		String userA = request.getParameter("userA");
		String userB = request.getParameter("userB");
		if (ValidationUtils.isEmpty(userA) == false && ValidationUtils.isEmpty(userB) == false) {
			
			answer = this.relationshipDAO.insertRelationship(userA,userB);
		}
		return answer;
	}
		
	private boolean deleteRelationShip(HttpServletRequest request) {
		boolean answer=false;
		String userA = request.getParameter("userA");
		String userB = request.getParameter("userB");
		if (ValidationUtils.isEmpty(userA) == false && ValidationUtils.isEmpty(userB) == false) {
			answer = this.relationshipDAO.deleteRelationship(userA,userB);
		}
		return answer;
	}		
	
	
	private void sendResponse(HttpServletRequest request, HttpServletResponse response, String list) throws ServletException, IOException {
		this.setResponseJSONHeader(response);
		request.setAttribute("errors", JSONUtils.getJSON(list));
		response.getWriter().print(JSONUtils.getJSON(list));
	}

}




