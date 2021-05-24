package controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Schedule;
import model.ScheduleLogic;
import model.User;

public class DayCalendar extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public DayCalendar() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String s_year = request.getParameter("year");
		String s_month = request.getParameter("month");
		String s_day = request.getParameter("day");

		if (s_day.startsWith("*")) {
			s_day = s_day.substring(1);
		}

		//	Sessionスコープからユーザ情報を取得
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		//	データベースからそのユーザに紐づいた予定を取得して、
		//	選択された日時の予定であれば、それをセットして表示させる
		ScheduleLogic logic = new ScheduleLogic();
		List<Schedule> scheduleList = logic.findSchedule(user, s_year, s_month, s_day);

		//	48個の配列を準備して、予定を格納する。
		String[] scheduleArray = new String[49];
		int[] widthArray = new int[49];

		for (int i = 0; i < 49; i++) {
			scheduleArray[i] = "";
			widthArray[i] = 0;
		}

		for(Schedule s : scheduleList) {
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			String startTimeStr = sdf.format(s.getStartTime());
			String starthourStr = startTimeStr.substring(0, 2);
			String startminuteStr = startTimeStr.substring(3, 5);

			int startTimeNum = Integer.parseInt(starthourStr);
			int index = startTimeNum * 2 + 1;
			if (startminuteStr.equals("30")) {
				index++;
			}

			String endTimeStr = sdf.format(s.getEndTime());
			String endhourStr = endTimeStr.substring(0, 2);
			String endminuteStr = endTimeStr.substring(3, 5);

			int endTimeNum = Integer.parseInt(endhourStr);
			int width = (endTimeNum - startTimeNum) * 2;
			if (startminuteStr.equals("30")) {
				width--;
			}
			if (endminuteStr.equals("30")) {
				width++;
			}

			scheduleArray[index] = s.getScheduleTitle();
			widthArray[index] = width;
			/* 同じスケジュールの先頭以外は-1を設定 */
			for (int i = 1; i < width; i++) {
				widthArray[index+i] = -1;
			}

		}

		request.setAttribute("year", s_year);
		request.setAttribute("month", s_month);
		request.setAttribute("day", s_day);
		request.setAttribute("scheduleArray", scheduleArray);
		request.setAttribute("widthArray", widthArray);
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/dayCalendar.jsp");
		rd.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//	show.jspから呼ばれる。
		//	入力されたスケジュールをデータベースに登録し、元のshow.jspにリダイレクト
		//	その際に、登録できたか否かのメッセージを表示させる。
		request.setCharacterEncoding("UTF-8");
		String year = request.getParameter("year");
		String month = request.getParameter("month");
		String day = request.getParameter("day");
		String s_hour = request.getParameter("shour");
		String s_minute = request.getParameter("sminute");
		String e_hour = request.getParameter("ehour");
		String e_minute = request.getParameter("eminute");
		String yotei = request.getParameter("yotei");
		String memo = request.getParameter("memo");

		//	Sessionスコープからユーザ情報を取得
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		//	スケジュールの日付を作成
		String string_schedule = year + "-" + month + "-" + day;
		//	二桁が来ることもあるので、[]を使う
		DateTimeFormatter date_formatter = DateTimeFormatter.ofPattern("yyyy-[]M-[]d");
		LocalDate date = LocalDate.parse(string_schedule, date_formatter);

		//	スケジュールの開始と終了の時間を作成
		String string_stime = s_hour + ":" + s_minute;
		String string_etime = e_hour + ":" + e_minute;
		DateTimeFormatter time_formatter = DateTimeFormatter.ofPattern("[]H:[]m");
		LocalTime stime = LocalTime.parse(string_stime, time_formatter);
		LocalTime etime = LocalTime.parse(string_etime, time_formatter);

		//	スケジュールの作成
		Schedule addSchedule = new Schedule();
		addSchedule.setUser_id(user.getId());
		addSchedule.setScheduleDate(java.sql.Date.valueOf(date));
		addSchedule.setStartTime(java.sql.Time.valueOf(stime));
		addSchedule.setEndTime(java.sql.Time.valueOf(etime));
		addSchedule.setScheduleTitle(yotei);
		addSchedule.setScheduleMemo(memo);

		//	ScheduleLogicをインスタンス化して、addscheduleを登録
		ScheduleLogic scheduleLogic = new ScheduleLogic();
		int result = scheduleLogic.registerSchedule(addSchedule);

		if (result == 1) {
			//	正常にスケジュール登録できた
			String infomsg = yotei + "を登録しました！";
			request.setAttribute("msg", infomsg);
		} else {
			//	スケジュール登録に失敗している
			String errmsg = yotei + "の登録に失敗しました。";
			request.setAttribute("errmsg", errmsg);
		}

		//	データベースからそのユーザに紐づいた予定を取得して、
		//	選択された日時の予定であれば、それをセットして表示させる
		ScheduleLogic logic = new ScheduleLogic();
		List<Schedule> scheduleList = logic.findSchedule(user, year, month, day);

		//	48個の配列を準備して、予定を格納する。
		String[] scheduleArray = new String[49];
		int[] widthArray = new int[49];

		for (int i = 0; i < 49; i++) {
			scheduleArray[i] = "";
			widthArray[i] = 0;
		}

		for(Schedule s : scheduleList) {
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			String startTimeStr = sdf.format(s.getStartTime());
			String starthourStr = startTimeStr.substring(0, 2);
			String startminuteStr = startTimeStr.substring(3, 5);

			int startTimeNum = Integer.parseInt(starthourStr);
			int index = startTimeNum * 2 + 1;
			if (startminuteStr.equals("30")) {
				index++;
			}

			String endTimeStr = sdf.format(s.getEndTime());
			String endhourStr = endTimeStr.substring(0, 2);
			String endminuteStr = endTimeStr.substring(3, 5);

			int endTimeNum = Integer.parseInt(endhourStr);
			int width = (endTimeNum - startTimeNum) * 2;
			if (startminuteStr.equals("30")) {
				width--;
			}
			if (endminuteStr.equals("30")) {
				width++;
			}

			scheduleArray[index] = s.getScheduleTitle();
			widthArray[index] = width;
			/* 同じスケジュールの先頭以外は-1を設定 */
			for (int i = 1; i < width; i++) {
				widthArray[index+i] = -1;
			}

		}

		request.setAttribute("year", year);
		request.setAttribute("month", month);
		request.setAttribute("day", day);
		request.setAttribute("scheduleArray", scheduleArray);
		request.setAttribute("widthArray", widthArray);

		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/dayCalendar.jsp");
		rd.forward(request, response);
	}
}
