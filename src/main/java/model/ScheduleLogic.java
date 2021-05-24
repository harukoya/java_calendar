package model;

import java.util.List;

import javax.naming.NamingException;

public class ScheduleLogic {
	public int registerSchedule(Schedule schedule) {
		//	戻り値
		int result = 0;

		ScheduleDAO dao = new ScheduleDAO();
		try {
			result = dao.registerSchedule(schedule);
		} catch (NamingException e) {
			e.printStackTrace();
		}

		return result;
	}

	public List<Schedule> findSchedule(User user, String year, String month, String day) {
		//	戻り値
		List<Schedule> scheduleList = null;

		ScheduleDAO dao = new ScheduleDAO();
		try {
			scheduleList = dao.findSchedule(user, year, month, day);
		} catch (NamingException e) {
			e.printStackTrace();
		}

		return scheduleList;
	}

}
