package us.pollapp.inturik;

import java.io.IOException;

import javax.servlet.http.*;

@SuppressWarnings("serial")
public class PollApp_BackendServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		resp.setContentType("text/plain");
		resp.getWriter().println("Hello, world 2");

	}
}
