package Servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Deconnection")
public class Deconnexion extends HttpServlet {
	  public static final String VUE = "/WEB-INF/Connection.jsp";

	    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
	        /* R�cup�ration et destruction de la session en cours */
	        HttpSession session = request.getSession();
	        session.invalidate();

	        /* Affichage de la page de connexion */
	        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
	    }
	    
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/* R�cup�ration et destruction de la session en cours */		
		HttpSession session = request.getSession();
		session.invalidate();
		/* Affichage de la page de connexion */
		this.getServletContext().getRequestDispatcher( VUE ).forward(request, response);
	}
}
