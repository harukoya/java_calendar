package filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class Filter
 */
@WebFilter("/*")
public class Filter implements javax.servlet.Filter {

    /**
     * Default constructor.
     */
    public Filter() {
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		//	文字コードをUTF-8に設定
		request.setCharacterEncoding("UTF-8");
		//	sessionが存在しなければ、ログイン画面へ遷移させる
		//	getSessionにfalseとすることで、sessionが存在しなければnullが返る
		HttpSession session = ((HttpServletRequest)request).getSession(false);

		if (session != null) {
			//	ログイン済み
			chain.doFilter(request, response);
		} else {
			//	sessionがnullなのでログイン画面へ飛ばす
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/index.jsp");
			rd.forward(request, response);

		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
