package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.User;
import model.UserLogic;

public class UserRegister extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserRegister() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//	ユーザ登録画面へフォワード
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/userRegister.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//	ユーザ登録画面で入力された値を取得
		String loginId = request.getParameter("loginId");
		String name = request.getParameter("name");
		String pass = request.getParameter("password");
		String s_role = request.getParameter("role");

		int role = Integer.parseInt(s_role);

		//	userインスタンスを生成
		User user = new User(loginId, name, pass, role);

		//	ユーザ登録
		UserLogic userLogic = new UserLogic();
		int result = userLogic.registerUser(user);

		//	処理結果に応じて、セットするメッセージを変える
		if (result > 0) {
			String msg = "";
			msg = loginId + "さんの登録に成功しました。";
			request.setAttribute("msg", msg);
		} else {
			String errmsg = "";
			errmsg = loginId + "さんの登録に失敗しました。";
			request.setAttribute("errmsg", errmsg);
		}

		//	ユーザ登録画面へフォワード
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/userRegister.jsp");
		rd.forward(request, response);
	}

}
