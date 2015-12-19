package Servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.AnnouncementDAO;
import DAO.UserDAO;
import Entities.Annonces;
import Entities.User;

@WebServlet("/home_user")
public class Home_user extends HttpServlet {

	public static final String VUE = "/WEB-INF/Home_user.jsp";
	public static final String VUESucess = "/WEB-INF/Connection.jsp";

	@EJB
	private AnnouncementDAO dao;
	
	@EJB
	private UserDAO user_dao;
	
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/* R�cup�ration de la session depuis la requ�te */
		HttpSession session = request.getSession();
		
		if (session.getAttribute("user") == null) {
			/* Redirection vers la page publique */
			this.getServletContext().getRequestDispatcher(VUESucess).forward(request, response);
		} else {
			
		
			this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
		}
	}

	
}