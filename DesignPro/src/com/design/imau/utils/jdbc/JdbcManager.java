package com.design.imau.utils.jdbc;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PropertyResourceBundle;

public class JdbcManager {

	private PropertyResourceBundle bundle;
	private static JdbcManager manager;
	private String DBhost = ""; // 数据库地址
	private String DBport = ""; // 数据库端口号
	private String DBname = ""; // 数据库名称
	private String DBuser = ""; // 数据库用户名
	private String DBpasswd = ""; // 数据库密码2                                                        
	private String DRIVER = ""; // 数据库驱动
	private String DBpath = ""; // 数据库完整路径
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	private JdbcManager() {
		try {
			bundle = new PropertyResourceBundle(
					JdbcManager.class.getResourceAsStream("config.properties"));
		} catch (IOException e) {
			System.out.println("config.properties配置文件没有找到");
		}
		this.DBhost = getKeyValue("DBhost");
		this.DBport = getKeyValue("DBport");
		this.DBname = getKeyValue("DBname");
		this.DBuser = getKeyValue("DBuser");
		this.DBpasswd = getKeyValue("DBpasswd");
		this.DRIVER = "com.mysql.jdbc.Driver";
		this.DBpath = "jdbc:mysql://" + DBhost + ":" + DBport + "/" + DBname;
	}

	/**
	 * 获得配置文件内容
	 * 
	 * @param key
	 *            配置文件中对应的参数名称
	 * @return 参数对应的值
	 */
	 private String getKeyValue(String key) {
		return this.bundle.getString(key);
	}

	/**
	 * @return jdbc对象
	 */
	public synchronized static JdbcManager getJdbcManager() {
		if (manager == null) {
			manager = new JdbcManager();
			manager.init();
		}
		return manager;
	}

	/**
	 * 初始化
	 */
	private void init() {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			System.out.println("驱动未找到");
		}
	}

	/**
	 * 连接数据库
	 */
	private void connection() {
		try {
			conn = DriverManager.getConnection(DBpath, DBuser, DBpasswd);
		} catch (SQLException e) {
			System.out.println("数据库连接失败");
		}
	}

	/**
	 * 关闭连接
	 **/
	private void closeConn() {
		try {
			if (conn != null) {
				conn.close();
				conn = null;
			}
			if (pstmt != null) {
				pstmt.close();
				pstmt = null;
			}
			if (rs != null) {
				rs.close();
				rs = null;
			}
		} catch (SQLException e) {
			System.out.println("数据库连接关闭异常");
		}
	}

	/**
	 * 更新数据库
	 * 
	 * @param sql
	 *            执行sql语句
	 * @param params
	 *            sql参数
	 * @return true表示更新成功,false表示更新不成功
	 * @throws SQLException
	 *             pstmt
	 */
	public boolean upDBData(String sql, List<Object> params)
			throws SQLException {
		//打开链接
		connection();
		
		boolean flag = false;
		// 表示当用户执行查询、删除和修改语的时候所影响的行数
		int resultLine = -1;
		pstmt = conn.prepareStatement(sql);
		// 表示占位符的第一个
		int index = 1;
		if (params != null && !params.isEmpty()) {
			for (int i = 0; i < params.size(); i++) {
				pstmt.setObject(index++, params.get(i));
			}
		}
		resultLine = pstmt.executeUpdate();
		flag = resultLine > 0 ? true : false;
		//关闭连接
		closeConn();
		return flag;
	}

	/**
	 * 获得单挑查询记录
	 * 
	 * @param sql
	 *            sql语句
	 * @param params
	 *            参数
	 * @return 返回查询Map<String, Object>
	 * @throws SQLException
	 *             prepareStatement
	 */
	public Map<String, Object> getSingleResult(String sql, List<Object> params)
			throws SQLException {
		//打开链接
		connection();
		
		Map<String, Object> map = new HashMap<String, Object>();
		pstmt = conn.prepareStatement(sql);
		int index = 1;
		if (params != null && !params.isEmpty()) {
			for (int i = 0; i < params.size(); i++) {
				pstmt.setObject(index++, params.get(i));
			}
		}
		rs = pstmt.executeQuery();
		ResultSetMetaData metaData = rs.getMetaData();
		int cols_num = metaData.getColumnCount();
		while (rs.next()) {
			for (int i = 0; i < cols_num; i++) {
				String cols_name = metaData.getColumnName(i + 1);
				Object cols_value = rs.getObject(cols_name);

				if (cols_value == null) {
					cols_value = "";
				}
				map.put(cols_name, cols_value);
			}
		}
		//关闭连接
		closeConn();
		return map;
	}

	/**
	 * 获得多条返回记录
	 * 
	 * @param sql
	 *            sql查询语句
	 * @param params
	 *            参数
	 * @return List<Map<String, Object>>
	 * @throws SQLException
	 *             prepareStatement;
	 */
	public List<Map<String, Object>> getMultiResult(String sql,
			List<Object> params) throws SQLException {
		//打开链接
		connection();
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		int index = 1;
		pstmt = conn.prepareStatement(sql);
		if (params != null && !params.isEmpty()) {
			for (int i = 0; i < params.size(); i++) {
				pstmt.setObject(index++, params.get(i));
			}
		}
		rs = pstmt.executeQuery();
		ResultSetMetaData metaData = pstmt.getMetaData();
		int cols_num = metaData.getColumnCount();
		while (rs.next()) {
			Map<String, Object> map = new HashMap<String, Object>();
			for (int i = 0; i < cols_num; i++) {
				String cols_name = metaData.getColumnName(i + 1);
				Object cols_value = rs.getObject(cols_name);
				if (cols_value == null) {
					cols_value = "";
				}
				map.put(cols_name, cols_value);
			}
			list.add(map);
		}
		//关闭连接
		closeConn();
		return list;
	}
}
