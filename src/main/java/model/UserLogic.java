package model;

import javax.naming.NamingException;

public class UserLogic {
	public User findUser(User user) {
		//	戻り値
		User returnUser = new User();

		UserDAO dao = new UserDAO();
		try {
			returnUser = dao.findUser(user);
		} catch (NamingException e) {
			e.printStackTrace();
		}

		return returnUser;
	}

	public int registerUser(User user) {
		//	戻り値
		int result = -1;
		UserDAO dao = new UserDAO();
		try {
			result = dao.registerUser(user);
		} catch (NamingException e) {
			e.printStackTrace();
		}

		return result;
	}
}
