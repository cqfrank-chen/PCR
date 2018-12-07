package public_chat_room;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.mysql.fabric.xmlrpc.base.Member;

import java.sql.Connection;;

public class pcrDbUtil {
	private DataSource dataSource;

	public pcrDbUtil(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public int addUser(UserInfo theUser) throws Exception {
		Connection myConnection = null;
		PreparedStatement myStatement = null;
		ResultSet myResultSet = null;
		try {
			myConnection = dataSource.getConnection();
			String sql = "insert into userInfo" + "(uName, uIcon, uLang)" + "value(?,?,?)";
			myStatement = myConnection.prepareStatement(sql);

			myStatement.setString(1, theUser.getName());
			myStatement.setString(2, theUser.getIcon());
			myStatement.setString(3, theUser.getLanguage());

			myStatement.execute();
			close(null, myStatement, null);
			sql = "select max(uId) from userInfo where uName = ?";
			myStatement = myConnection.prepareStatement(sql);
			myStatement.setString(1, theUser.getName());

			myResultSet = myStatement.executeQuery();
			int id = -1;
			while (myResultSet.next()) {
				id = myResultSet.getInt(1);
			}
			return id;
		} finally {
			close(myConnection, myStatement, null);
		}
	}

	public void addMessage(String tableName, String uId, String message) throws Exception {
		Connection myConnection = null;
		PreparedStatement myStatement = null;
		try {
			myConnection = dataSource.getConnection();
			String sql = "insert into " + tableName + "(uId, message)" + "value(?,?)";
			myStatement = myConnection.prepareStatement(sql);

			myStatement.setString(1, uId);
			myStatement.setString(2, message);
			myStatement.execute();
		} finally {
			close(myConnection, myStatement, null);
		}
	}

	public List<messInfo> getMessage(String tableName) throws Exception {
		List<messInfo> list = new ArrayList<>();
		Connection myConnection = null;
		Statement myStatement = null;
		ResultSet myResultSet = null;
		try {
			myConnection = dataSource.getConnection();
			String sql = "select " + tableName + ".*, userInfo.* from " + tableName + " left join userInfo on "
					+ tableName + ".uId=userInfo.uId order by messId desc";
			myStatement = myConnection.createStatement();
			myResultSet = myStatement.executeQuery(sql);
			int cnt = 0;
			while (myResultSet.next() && cnt < 20) {
				String icon = myResultSet.getString(6);
				String userName = myResultSet.getString(5);
				String message = myResultSet.getString(3);
				messInfo tmp = new messInfo(message, userName, icon);
				list.add(tmp);
				cnt++;
			}
			return list;
		} finally {
			close(myConnection, myStatement, myResultSet);
		}
	}

	public List<UserInfo> getMember(String tableName) throws Exception {
		List<UserInfo> list = new ArrayList<>();
		Connection myConnection = null;
		Statement myStatement = null;
		ResultSet myResultSet = null;
		try {
			myConnection = dataSource.getConnection();
			String sql = "select " + tableName + ".*, userInfo.* from " + tableName + " left join userInfo on "
					+ tableName + ".uId=userInfo.uId";
			myStatement = myConnection.createStatement();
			myResultSet = myStatement.executeQuery(sql);
			int cnt = 0;
			while (myResultSet.next() && cnt < 5) {
				String userName = myResultSet.getString(3);
				UserInfo tmp = new UserInfo(userName, null, null);
				list.add(tmp);
				cnt++;
			}
			return list;
		} finally {
			close(myConnection, myStatement, myResultSet);
		}
		
	}
	
	public void dropMember(String tableName, String uId, String tableName2) throws Exception {
		Connection myConnection = null;
		Statement myStatement = null;
		ResultSet myResultSet = null;
		try {
			myConnection = dataSource.getConnection();
			String sql = "delete from " + tableName + " where uId=" + uId + "";
			myStatement = myConnection.createStatement();
			myStatement.execute(sql);
			close(myConnection, myStatement, myResultSet);
			
			myConnection = dataSource.getConnection();
			sql = "select count(*) from " + tableName + "";
			myStatement = myConnection.createStatement();
			myResultSet = myStatement.executeQuery(sql);
			
			int cnt = 0; 
			while (myResultSet.next()) {
				cnt = myResultSet.getInt(1);
			}
			System.out.println("cnt = " + cnt);
			close(myConnection, myStatement, myResultSet);
			
			if (cnt == 0) {
				myConnection = dataSource.getConnection();
				sql = "drop table " + tableName + "; drop table " + tableName2 + ";";
				myStatement = myConnection.createStatement();
				myStatement.execute(sql);
			}
		} finally {
			close(myConnection, myStatement, myResultSet);
		}
	}
	private void close(Connection myConnection, Statement myStatement, ResultSet myResultSet) {
		try {
			if (myConnection != null) {
				myConnection.close();
			}
			if (myStatement != null) {
				myStatement.close();
			}
			if (myResultSet != null) {
				myResultSet.close();
			}
		} catch (Exception o) {
			o.printStackTrace();
		}
	}
}
