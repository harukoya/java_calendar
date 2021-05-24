package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.MyCalendar;
import model.MyCalendarLogic;

public class MonthCalendar extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public MonthCalendar() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String s_year = request.getParameter("year");
		String s_month = request.getParameter("month");
		MyCalendarLogic logic = new MyCalendarLogic();
		MyCalendar mc = null;

		if (s_year != null && s_month != null) {
			int year = Integer.parseInt(s_year);
			int month = Integer.parseInt(s_month);
			if (month == 0) {
				month = 12;
				year--;
			}
			if (month == 13) {
				month = 1;
				year++;
			}
			//	クエリパラメータでカレンダーを生成
			mc = logic.createMyCalendar(year, month);
		} else {
			//	クエリパラメータがない場合、実行日時でカレンダーを生成
			mc = logic.createMyCalendar();
		}

		//	リクエストスコープに格納
		request.setAttribute("mc", mc);
		//	viewにフォワード
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/monthCalendar.jsp");
		rd.forward(request, response);
	}
}
