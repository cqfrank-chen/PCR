package public_chat_room;


import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import java.io.IOException;

/**
 * Servlet implementation class enterController
 */
@WebServlet("/enterController")
public class enterController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private pcrDbUtil pcrDbUtil;
	
	@Resource(name = "jdbc/pcr")
	private DataSource dataSource;
	
	
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		try {
			pcrDbUtil = new pcrDbUtil(dataSource);
		}catch (Exception o) {
			new ServletException(o);
		}
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String name = request.getParameter("userName");
		String icon = request.getParameter("icon");
		String language = request.getParameter("language");
		UserInfo User = new UserInfo(name, icon, language);
		
		HttpSession session = request.getSession();
		session.setAttribute("userName", name);
		session.setAttribute("icon", icon);
		session.setAttribute("language", language);
		try {
			int uId = pcrDbUtil.addUser(User);
			session.setAttribute("userId", uId);
			System.out.println(uId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		request.setAttribute("USER",User);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/roomList.jsp");
		dispatcher.forward(request, response);
	}
}
