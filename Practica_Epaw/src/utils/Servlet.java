/**
 * Classe Base para los Servlets con utilidades genericas.
 * @author Marc Pérez - 173287
 *
 */

package utils;
import java.io.File;
import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Servlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	public Servlet() {}
	
	public void init(ServletConfig config) throws ServletException {
    	super.init(config);
    }
	
	// Necesita que es sobreescrigui.
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {}
	
	// Necesita que es sobreescrigui.
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {}
	
	protected void setResponseHTMLHeader(HttpServletResponse response) {
		response.setContentType("text/html");
	}

	protected void setResponseJSONHeader(HttpServletResponse response) {
		response.setContentType("application/json");
	}
	
	/** Funcion que modifica el header del response para añadir un archivo pdf, attachment o inline.
	 * @param HttpServletResponse response
	 * @param File pdf
	 * @param String type (attachment or inline);
	 */
	protected void setResponsePDF(HttpServletResponse response, File pdf, String type) {
		if (pdf != null) {
			if ((type.equals("attachment") == true) || (type.equals("inline") == true)) {
				response.setContentType("application/pdf");
			    response.addHeader("Content-Disposition", type + "; filename=" + pdf);
			    response.setContentLength((int) pdf.length());
			}
		}
	}
	
	// Metodo inutil, pero se escribe menos para llamarlo.
	protected HttpSession getSession(HttpServletRequest request) {
		return request.getSession();
	}
	
}
