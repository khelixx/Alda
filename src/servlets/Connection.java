package servlets;

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

import dao.AnnouncementDAO;
import dao.MessageDAO;
import dao.UserDAO;
import entities.Annonces;
import entities.User;

@WebServlet("/connexion")
public class Connection extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String VUESucess = "/WEB-INF/Home_user.jsp";
	public static final String VUE = "/WEB-INF/Connection.jsp";

	@EJB
	UserDAO user;

	@EJB
	AnnouncementDAO dao;

	@EJB
	MessageDAO message_dao;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		User user_connexion = user.findUser(email, password);

		if (user_connexion != null) {

			java.util.Date dt = new java.util.Date();

			user.updateDate(user_connexion, dt);

			List<Annonces> annoucements = new ArrayList<>();
			annoucements = dao.getAnnoucement_user(user_connexion);

			if (!annoucements.isEmpty()) {
				request.setAttribute("annoucement_user", annoucements);
			}

			long new_notification = message_dao.findAnnouncementSold(email);
			request.setAttribute("notifications", new_notification);
			session.setAttribute("user", user_connexion);
			this.getServletContext().getRequestDispatcher(VUESucess).forward(request, response);
		} else {
			String error = "Bad email or bad password";
			request.setAttribute("error", error);
			this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
		}

	}

}
