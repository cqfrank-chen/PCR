package public_chat_room;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.json.JSONArray;
import org.json.JSONObject;


/**
 * Servlet implementation class RoomControllerServlet
 */
@WebServlet("/RoomControllerServlet")
public class RoomControllerServlet extends HttpServlet {
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
		} catch (Exception exc) {
			throw new ServletException(exc);
		}

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("UTF-8");
		try {
			PrintWriter out = response.getWriter();
			String theCommand = request.getParameter("command");
			if (theCommand == null) {
			}
			switch (theCommand) {
			case "queryRoom":
				List<Room> list;
				try {
					list = pcrDbUtil.getRooms();
					StringBuilder ss = new StringBuilder();
					ss.append("{");
					ss.append("\"rows\":[");
					for (int i = 0; i < list.size(); i++) {
						System.out.println(i);
						if (i == 0)
							ss.append("{\"roomId\":" + "\"" + list.get(i).getId() 
									+ "\", \"roomName\":" + "\""+ list.get(i).getRoomName()
									+ "\", \"roomCount\":" + list.get(i).getnowNum()
									+ ", \"roomSum\":"  + list.get(i).getmaxNum() + "}");
						else
							ss.append(",{\"roomId\":" + "\"" + list.get(i).getId() 
									+ "\", \"roomName\":" + "\""+ list.get(i).getRoomName()
									+ "\", \"roomCount\":" + list.get(i).getnowNum()
									+ ", \"roomSum\":"  + list.get(i).getmaxNum() + "}");
					}
					ss.append("]");
					ss.append("}");
					String res = ss.toString();
					System.out.println(res);
					JSONObject jsonObject = new JSONObject(res);
					System.out.println(jsonObject);
					out.print(jsonObject);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				break;
			case "createRoom":
				createRoom(request,response);
				break;
			case "loadRoom":
				loadRoom(request,response);
				break;
			case "logout":
				logout(request, response);
				break;
			default:
				;
			}
		} catch (Exception exc) {
			throw new ServletException(exc);
		}
	}

	private void loadRoom(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setCharacterEncoding("UTF=8");
		response.setContentType("text/html;charset=UTF-8");
		HttpSession session = request.getSession();
		if (session.getAttribute("theRoom") == null) {
			String roomId = request.getParameter("roomId");
			Room theRoom = pcrDbUtil.getRoom(roomId);
			pcrDbUtil.addUserToRoom(theRoom.getRoomMeb(), (int)session.getAttribute("userId"));
			session.setAttribute("theRoom", theRoom);
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("/chatRoom.jsp");
		dispatcher.forward(request, response);
	}

	private void createRoom(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		String token = TokenProccessor.getInstance().makeToken();//创建令牌
//		System.out.println("在FormServlet中生成的token："+token);
//		request.getSession().setAttribute("token", token); 
		request.getSession().setAttribute("token", "true");
		RequestDispatcher dispatcher = request.getRequestDispatcher("/create_a_room.jsp");
		dispatcher.forward(request, response);
	}
	
	private void logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("step0");
		HttpSession session = request.getSession();
		String sessionId = session.getId();
		if(session.isNew()) {
			System.out.println("session创建成功，session的id是："+sessionId);
		}else {
			System.out.println("服务器已经存在该session，session的id是："+sessionId);
		}
		int theUserId=(int) session.getAttribute("userId");
		System.out.println(theUserId);
		//pcrDbUtil.deleteUser(theUserId);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
		dispatcher.forward(request, response);
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request,response);
	}

	

}
