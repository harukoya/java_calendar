package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ScheduleDAO {

	public int registerSchedule(Schedule schedule) throws NamingException {
		//	戻り値
		int result = -1;

		//	データソース情報の取得
		InitialContext initContext = new InitialContext();
		DataSource ds = (DataSource)initContext.lookup("java:/comp/env/jdbc/calendar_db");

		//	データベースへ接続
		try(Connection con = ds.getConnection();) {
			//	SQL文
			String sql = "INSERT INTO schedule (user_id, schedule_date, start_time, end_time, schedule_title, schedule_memo) VALUES (?, ?, ?, ?, ?, ?)";
			PreparedStatement ps = con.prepareStatement(sql);

			ps.setInt(1, schedule.getUser_id());
			ps.setDate(2, schedule.getScheduleDate());
			ps.setTime(3, schedule.getStartTime());
			ps.setTime(4, schedule.getEndTime());
			ps.setString(5, schedule.getScheduleTitle());
			ps.setString(6, schedule.getScheduleMemo());

			result = ps.executeUpdate();

			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			return result;
		}
	}

	public List<Schedule> findSchedule(User user, String year, String month, String day) throws NamingException {
		//	戻り値
		List<Schedule> scheduleList = new ArrayList<>();

		//	データソース情報の取得
		InitialContext initContext = new InitialContext();
		DataSource ds = (DataSource)initContext.lookup("java:/comp/env/jdbc/calendar_db");

		//	データベースへ接続
		try(Connection con = ds.getConnection()) {
			//	SQL文
			String sql = "SELECT user_id, schedule_date, start_time, end_time, schedule_title, schedule_memo FROM schedule where user_id = ? AND schedule_date = ?";
			PreparedStatement ps = con.prepareStatement(sql);

			//	java.sql.Date型への変換
			String schedule_date = year + "-" + month + "-" + day;
			DateTimeFormatter date_formatter = DateTimeFormatter.ofPattern("yyyy-[]M-[]d");
			LocalDate date = LocalDate.parse(schedule_date, date_formatter);

			ps.setInt(1, user.getId());
			ps.setDate(2, java.sql.Date.valueOf(date));

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Schedule schedule = new Schedule();
				schedule.setUser_id(rs.getInt("user_id"));
				schedule.setScheduleDate(rs.getDate("schedule_date"));
				schedule.setStartTime(rs.getTime("start_time"));
				schedule.setEndTime(rs.getTime("end_time"));
				schedule.setScheduleTitle(rs.getString("schedule_title"));
				schedule.setScheduleMemo(rs.getString("schedule_memo"));
				scheduleList.add(schedule);
			}

			return scheduleList;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
