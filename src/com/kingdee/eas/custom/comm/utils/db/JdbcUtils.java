package com.kingdee.eas.custom.comm.utils.db;
import com.kingdee.bos.BOSException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import net.sourceforge.jtds.jdbc.Driver;


public class JdbcUtils{
	/**
	 * 连接Oracle数据库
	 * @param str
	 * @return
	 */
	public static Connection getConnOracle(String[] str) {
		Connection conn = null;
		if (str.length == 5) {
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
				String url = "jdbc:oracle:thin:@" + str[0] + ":" + str[1] + ":" + str[2];
				String user = str[3];
				String password = str[4];
				conn = DriverManager.getConnection(url, user, password);
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException illegalAccessException) {

			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} 
			return conn;
		} 
		return null;
	}

	/**
	 * 连接SqlServer数据库
	 * @param str
	 * @return
	 */
	public static Connection getConnSqlServer(String[] str) throws BOSException {
		Connection conn = null;
		try {
			Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
			DriverManager.registerDriver((Driver)new Driver());
			String url = "jdbc:jtds:sqlserver://" + str[0] + ":" + str[1] + "/" + str[2];
			String user = str[3];
			String password = str[4];
			conn = DriverManager.getConnection(url, user, password);
		} catch (InstantiationException e) {

		} catch (IllegalAccessException e) {

		} catch (ClassNotFoundException e) {

		} catch (SQLException e) {
			throw new BOSException("数据库连接失败");
		} 
		return conn;
	}
}


