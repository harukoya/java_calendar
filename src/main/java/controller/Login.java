package controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.MyCalendar;
import model.MyCalendarLogic;
import model.User;
import model.UserDAO;
import model.UserLogic;

public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String loginId = request.getParameter("loginId");
		String pass = request.getParameter("password");


		//	Userインスタンスを生成
		User user = new User();
		user.setLoginId(loginId);
		user.setPass(pass);

		//	Loggerクラスのインスタンスを生成
		Logger logger = Logger.getLogger(UserDAO.class.getName());

		try {
			LogManager manager = LogManager.getLogManager();
			manager.readConfiguration(UserDAO.class.getResourceAsStream("../logging.properties"));
		} catch (IOException | SecurityException e) {
			e.printStackTrace();
		}
		//	ユーザ検索
		UserLogic userLogic = new UserLogic();
		User loginUser = userLogic.findUser(user);

		if (loginUser != null) {
			//	ログイン成功
			logger.log(Level.INFO, "ログイン成功", loginUser.getName());
			//	セッションスコープにユーザ情報を格納
			HttpSession session = request.getSession();
			session.setAttribute("user", loginUser);

			//	実行日時でカレンダーを生成
			MyCalendarLogic logic = new MyCalendarLogic();
			MyCalendar mc = null;
			mc = logic.createMyCalendar();

			//	リクエストスコープに格納
			request.setAttribute("mc", mc);

			//	main画面へフォワード
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/monthCalendar.jsp");
			rd.forward(request, response);
		} else {
			//	ログイン失敗
			logger.log(Level.INFO, "ログイン失敗");
			String errmsg = "ログインに失敗しました。ログインIDまたはパスワードが正しくありません。";
			request.setAttribute("errmsg", errmsg);
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/index.jsp");
			rd.forward(request, response);
		}
	}

}
