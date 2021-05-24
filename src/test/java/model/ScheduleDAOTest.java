package model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.naming.NamingException;

import org.junit.jupiter.api.Test;

class ScheduleDAOTest {

	@Test
	void testRegisterSchedule() {
		Schedule schedule = new Schedule();
		ScheduleDAO dao = new ScheduleDAO();

		//	入力値の準備
		String string_year = "2021";
		String string_month = "5";
		String string_day = "5";
		String string_schedule = string_year + "-" + string_month + "-" + string_day;
		//	二桁が来ることもあるので、[]を使う
		DateTimeFormatter date_formatter = DateTimeFormatter.ofPattern("yyyy-[]M-[]d");
		LocalDate date = LocalDate.parse(string_schedule, date_formatter);

		String string_shour = "9";
		String string_sminute = "00";
		String string_ehour = "10";
		String string_eminute = "30";
		String string_stime = string_shour + ":" + string_sminute;
		String string_etime = string_ehour + ":" + string_eminute;
		//	二桁が来ることもあるので、[]を使う
		DateTimeFormatter time_formatter = DateTimeFormatter.ofPattern("[]H:mm");
		LocalTime stime = LocalTime.parse(string_stime, time_formatter);
		LocalTime etime = LocalTime.parse(string_etime, time_formatter);

		String test_title = "TEST_TITLE";

		schedule.setUser_id(1);
		schedule.setScheduleDate(java.sql.Date.valueOf(date));
		schedule.setStartTime(java.sql.Time.valueOf(stime));
		schedule.setEndTime(java.sql.Time.valueOf(etime));
		schedule.setScheduleTitle(test_title);

		int result = -1;
		try {
			result = dao.registerSchedule(schedule);
		} catch (NamingException e) {
			e.printStackTrace();
		}
		assertEquals(result, 1);
	}

	@Test
	void testFindSchedule() {
		//	入力値の準備(User)
		User testUser = new User();
		testUser.setId(1);
		testUser.setLoginId("admin");
		//	入力値の準備(Schedule)
		String string_year = "2021";
		String string_month = "5";
		String string_day = "5";

		ScheduleDAO dao = new ScheduleDAO();

		List<Schedule> resultScheduleList = null;
		try {
			resultScheduleList = dao.findSchedule(testUser, string_year, string_month, string_day);
		} catch (NamingException e) {
			e.printStackTrace();
		}
		//	adminのscheduleはテストの一つのみの想定
		assertEquals(resultScheduleList.size(), 1);
	}

}
