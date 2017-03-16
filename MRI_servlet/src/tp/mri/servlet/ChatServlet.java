package tp.mri.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ChatServlet
 */
@WebServlet("/chat")
public class ChatServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//http://localhost:8080/MRI_servlet/chat

	private StringBuffer chatContent;

	/**
	 * Default constructor.
	 */
	public ChatServlet() {
		// TODO Auto-generated constructor stub
	}

	private int appels;

	public void init() throws ServletException {
		appels = 0;
		log(" création servlet");
		chatContent = new StringBuffer();

		ServletContext context = getServletContext();
		Enumeration<String> e = context.getInitParameterNames();
		while (e.hasMoreElements()) {
			String key = (String) e.nextElement();
			Object value = context.getInitParameter(key);
			chatContent.append(value.toString()).append("\n");
		}
		//chatContent.append("Bienvenue sur le chat").append("\n");
		//chatContent.append("Soyez polis").append("\n");
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		appels++;

		String action = request.getParameter("action");
		PrintWriter out = response.getWriter();

		if (!isConnected(request)) {
			if (action != null && action.equals("submit")) {
				String ligne = request.getParameter("name");
				if (ligne != null) {
					request.getSession().setAttribute("nom", ligne);
				}
			} else {
				RequestDispatcher rd = request.getRequestDispatcher("login.html");
				rd.include(request, response);
			}

		}

		if (isConnected(request)) {

			String pname = request.getSession().getAttribute("nom").toString();

			if (action != null && action.equals("submit")) {
				String ligne = request.getParameter("ligne");
				if (ligne != null && !ligne.equals(""))
					chatContent.append(
							SimpleDateFormat.getDateTimeInstance().format(new Date()) + " : " + pname + ">" + ligne + "\n");
			}

			RequestDispatcher rd = request.getRequestDispatcher("chat.jsp");

			request.setAttribute("content", chatContent.toString());
			request.setAttribute("appels", appels);

			rd.include(request, response);

			// START HTML

			// out.println("<html>");
			// out.println("<head><title>Hello " + pname+ "</title></head>");
			// out.println("<body>");
			// out.println("Hello " + pname + "<br/> appels = " + appels);
			//
			// out.println("<pre
			// style=\"background-color:pink;\">"+chatContent+"</pre>");
			//
			//
			// out.println("<form name=\"chatForm\" action=\"chat\"
			// method=\"post\">"+
			// "<input type=\"text\" name=\"ligne\" value=\"\"/>"+
			// "<input type=\"submit\" name=\"action\" value=\"submit\"/>"+
			// "<input type=\"submit\" name=\"action\"
			// value=\"refresh\"/>"+"</form>");
			//
			//
			// out.println("</body></html>");
			//

			// END HTML

			out.flush();
		}
	}

	private boolean isConnected(HttpServletRequest request) {
		return request.getSession().getAttribute("nom") != null;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
