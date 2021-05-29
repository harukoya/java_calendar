package model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


public class UserDAO {

	public User findUser(User user) throws NamingException {
		//	戻り値
		User returnUser = new User();

		//	Loggerクラスのインスタンスを生成
		Logger logger = Logger.getLogger(UserDAO.class.getName());

		try {
			LogManager manager = LogManager.getLogManager();
			manager.readConfiguration(UserDAO.class.getResourceAsStream("../logging.properties"));
		} catch (IOException | SecurityException e) {
			e.printStackTrace();
		}

		//	データソース情報の取得
		InitialContext initContext = new InitialContext();
		DataSource ds = (DataSource)initContext.lookup("java:/comp/env/jdbc/calendar_db");

		//	データベースへ接続
		try (Connection con = ds.getConnection()) {
			//	ユーザ検索用クエリ
			String sql = "SELECT id, login_id, name, role FROM user WHERE login_id = ? AND pass = ?";
			PreparedStatement ps = con.prepareStatement(sql);

			ps.setString(1, user.getLoginId());
			ps.setString(2, user.getPass());

			ResultSet rs = ps.executeQuery();
			logger.log(Level.INFO, "データベース接続に成功");

			if (rs.next()) {
				//	ユーザが見つかったので、情報をセット
				returnUser.setId(rs.getInt("id"));
				returnUser.setLoginId(rs.getString("login_id"));
				returnUser.setName(rs.getString("name"));
				returnUser.setRole(rs.getInt("role"));
				logger.log(Level.INFO, "見つかった");
			} else {
				logger.log(Level.INFO, "見つからない");
				returnUser = null;
			}
		} catch (SQLException e) {
			logger.log(Level.WARNING, "データベース接続に失敗", e);
			return null;
		}

		return returnUser;

	}

	public int registerUser(User user) throws NamingException {
		//	戻り値
		int result = 0;

		//	データソース情報の取得
		InitialContext initContext = new InitialContext();
		DataSource ds = (DataSource)initContext.lookup("java:/comp/env/jdbc/calendar_db");

		//	データベースへ接続
		try (Connection con = ds.getConnection()) {
			//	ユーザ登録用クエリ
			String sql = "INSERT INTO user (login_id, name, pass, role) VALUES (?, ?, ?, ?)";
			PreparedStatement ps = con.prepareStatement(sql);

			ps.setString(1, user.getLoginId());
			ps.setString(2, user.getName());
			ps.setString(3, user.getPass());
			ps.setInt(4, user.getRole());

			result = ps.executeUpdate();

			if (result != 0) {
				System.out.println(user.getLoginId() + "の登録に成功しました。");
			} else {
				System.out.println(user.getLoginId() + "の登録に失敗しました。");
			}

			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			return result;
		}
	}
}
