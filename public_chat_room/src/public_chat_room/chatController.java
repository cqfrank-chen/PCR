package public_chat_room;

import java.io.IOException;

import java.io.PrintWriter;
import java.util.*;

import javax.accessibility.AccessibleRelation;
import javax.annotation.Resource;
import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.json.JSONArray;

/**
 * Servlet implementation class charController
 */
@WebServlet("/chatController")
public class chatController extends HttpServlet {
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
		} catch (Exception o) {
			new ServletException(o);
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		String userName = (String) session.getAttribute("userName");
		String command = request.getParameter("command");
		String message = request.getParameter("message");
		String messageTable = (String) session.getAttribute("messageTable");
		String uId = (String) session.getAttribute("uId");
		String memberTable = (String) session.getAttribute("memberTable");
		System.out.println(command + " " + userName + " " + message);
		System.out.println(uId);
		System.out.println(messageTable);
		switch (command) {
		case "post":
			try {
				pcrDbUtil.addMessage(messageTable, uId, message);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "queryMember":
			List<UserInfo> list = queryMember();

			StringBuilder ss = new StringBuilder();
			ss.append("[");
			for (int i = 0; i < list.size(); i++) {
				if (i == 0)
					ss.append("{\"name\":" + "\"" + list.get(i).getName() + "\", \"icon\":" + "\""
							+ list.get(i).getIcon() + "\"}");
				else
					ss.append(",{\"name\":" + "\"" + list.get(i).getName() + "\", \"icon\":" + "\""
							+ list.get(i).getIcon() + "\"}");
			}
			ss.append("]");
			String res = ss.toString();
			JSONArray jsonArray = new JSONArray(res);
			System.out.println(jsonArray);
			out.print(jsonArray);
			break;
		case "queryMessage":
			try {
				List<messInfo> list2 = pcrDbUtil.getMessage(messageTable);
				StringBuilder ss2 = new StringBuilder();
				ss2.append("[");
				for (int i = 0; i < list2.size(); i++) {
					if (i == 0)
						ss2.append("{\"name\":" + "\"" + list2.get(i).getUserName() + "\", \"icon\":" + "\"" + list2.get(i).getIcon()
								+ "\", \"message\":\"" + list2.get(i).getMessage() + "\"}");
					else
						ss2.append(",{\"name\":" + "\"" + list2.get(i).getUserName() + "\", \"icon\":" + "\"" + list2.get(i).getIcon()
								+ "\", \"message\":\"" + list2.get(i).getMessage() + "\"}");
				}
				ss2.append("]");
				String res2 = ss2.toString();
				JSONArray jsonArray2 = new JSONArray(res2);
				System.out.println(jsonArray2);
				out.print(jsonArray2);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		default:
			break;
		}
	}


	private List<UserInfo> queryMember() {
		List<UserInfo> list = new ArrayList<>();
		UserInfo user1 = new UserInfo("userName1", "icon-1", null);
		UserInfo user2 = new UserInfo("userName2", "icon-2", null);
		list.add(user1);
		list.add(user2);
		return list;
	}
}
