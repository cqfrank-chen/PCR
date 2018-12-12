package public_chat_room;



import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class CreateRoomServlet
 */
@WebServlet("/CreateRoomServlet")
public class CreateRoomServlet extends HttpServlet {
	private static final long serialVersionUID = 1L; 

    @Resource(name = "jdbc/pcr")
	private DataSource dataSource;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getSession().getAttribute("token") == "true" ) {
			create_room(request);
			create_mestable();
			request.getSession().setAttribute("token", null);
		}
	    RequestDispatcher dispatcher = request.getRequestDispatcher("/roomList.jsp");
		dispatcher.forward(request, response);
	 }
	private void create_mestable() {
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = dataSource.getConnection();
			String sql = "select * from roominfo order by id desc;";
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			int id = rs.getInt("id");
			sql = "create table mes"+id+" (mesId int auto_increment PRIMARY KEY,uId int,message char (250));";
			stmt.execute(sql);
			sql = "create table meb"+id+"(mebId int auto_increment PRIMARY KEY,uId int);";
			stmt.execute(sql);
			rs.close();
			stmt.close();
            conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void create_room(HttpServletRequest request) {
		Connection conn = null;
		Statement stmt = null;
		String room_name = "'"+request.getParameter("room_name")+"'";
	    String max_num = request.getParameter("people_num");
	    String num = 1+"";
	    try {
			conn = dataSource.getConnection();
			String sql = "insert into roominfo(name,maxnum,nownum) values("+room_name+","+max_num+","+num+");";
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			sql = "select * from roominfo order by id desc;";
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			int id = rs.getInt("id");
			sql = "update roominfo set mestable = \"mes"+id+"\" where id="+id;
			System.out.println(sql);
			stmt.execute(sql);
			sql = "update roominfo set mebtable = \"meb"+id+"\" where id="+id;
			System.out.println(sql);
			stmt.execute(sql);
			stmt.close();
            conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
