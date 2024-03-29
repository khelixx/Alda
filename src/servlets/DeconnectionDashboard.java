package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/dashboarddeconnection")
public class DeconnectionDashboard extends HttpServlet {

	public static final String VUE = "/WEB-INF/ConnectionDashboard.jsp";


	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.invalidate();

		this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
	}

	
}
