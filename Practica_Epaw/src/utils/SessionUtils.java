package utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import models.BeanUser;

public class SessionUtils {

	public static BeanUser getSessionUser(HttpServletRequest request) {
		BeanUser sessionUser = null;
		HttpSession session = request.getSession();
		if (session != null) {
			String userInfo = (String) session.getAttribute("userInfo");
			
			if (userInfo != null) {
				sessionUser = JSONUtils.returnJSONObject(userInfo, BeanUser.class);
			}
			
		}
		
		return sessionUser;
	}
	
	public static String getString(HttpServletRequest request, String param) {
		String value = "";
		HttpSession session = request.getSession();
		
		if (session != null) {
			value = (String) session.getAttribute(param);
		}
		
		return value;
	}
	
	public static String getSessionUserToLook(HttpServletRequest request) {
		return getString(request ,"userToLook");
	}
	
}
