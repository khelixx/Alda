package servlets;

import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import dao.AnnouncementDAO;
import dao.MessageDAO;
import dao.UserDAO;
import entities.Annonces;
import entities.User;

@WebServlet("/home_user")
public class Home_user extends HttpServlet {

	public static final String VUE = "/WEB-INF/Home_user.jsp";
	public static final String VUESucess = "/WEB-INF/Connection.jsp";

	@EJB
	private MessageDAO dao;

	@EJB
	private UserDAO user_dao;

	@EJB
	private AnnouncementDAO announceDAO;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/* R�cup�ration de la session depuis la requ�te */
		HttpSession session = request.getSession();

		User user_temp = user_dao.findByUser((User) session.getAttribute("user"));
		long new_notification = dao.findAnnouncementSold(user_temp.getEmail());
		request.setAttribute("notifications", new_notification);

		List<Annonces> annonce = announceDAO.findAll();
		String news = newAnnouncewithFactorUser(annonce, user_temp);
		if (news.equals("news")) {
			request.setAttribute("news", news);
		}

		this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
	}

	public String newAnnouncewithFactorUser(List<Annonces> announce, User user) {

		String answear = "";
		String tmp = null;
		for (Annonces a : announce) {
			System.out.println(a.getUser().getEmail());
			if (!(a.getUser().getEmail().equals(user.getEmail()))) {
				boolean check = true;
				if (a.getDate().compareTo(user.getDate_connexion()) > 0) {
					String factor[] = user.getFactor().split(";");
					if (!StringUtils.isBlank(factor[0])) {
						tmp = factor[0].replaceAll(" ", "");
						if (Integer.parseInt(tmp) > a.getPrice()) {
							check = false;
						}
					}

					if (!StringUtils.isBlank(factor[1])) {
						tmp = factor[1].replaceAll(" ", "");
						if (Integer.parseInt(tmp) < a.getPrice()) {
							check = false;
						}
					}

					if (!StringUtils.isBlank(factor[2])) {
						tmp = factor[1].replaceAll(" ", "");
						if (Integer.parseInt(tmp) != a.getPostal_code()) {
							check = false;
						}
					}

					if (check) {
						answear = "news";
					}
				}
			}
		}
		return answear;
	}

}