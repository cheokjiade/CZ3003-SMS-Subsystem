

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SMS
 */
@WebServlet("/SMS")
public class SMS extends HttpServlet {
	private static final long serialVersionUID = 1L;
	LoadBalancer server;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SMS() {
        super();
        
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		server = new LoadBalancer();
	}

	/**
	 * @see Servlet#getServletInfo()
	 */
	public String getServletInfo() {
		// TODO Auto-generated method stub
		return null; 
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		//if (SMSstart.server == null) SMSstart.server.start();
		//if(server == null) server = SMSstart.server;
		String loc = request.getParameter("loc");
		String desc = request.getParameter("desc");
		server.sendMessageOut(desc + "\n@\n" + loc, "94593932");
		//server.broadcast(desc + "\n@\n" + loc);
		out.append(desc + "\n@\n" + loc);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
