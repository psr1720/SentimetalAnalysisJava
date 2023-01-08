package servletpack;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class hateSpeech
 */
@WebServlet("/hateSpeech")
public class hateSpeech extends HttpServlet {
	public hateSpeech() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JFSDProject","root","Bannu@1566");
			nlpPipeline ob = new nlpPipeline();
			String sql1 = "insert into query values (?,?,?,?)";
			PreparedStatement p1 = con.prepareStatement(sql1);
			String fname = request.getParameter("fname");
			String lname = request.getParameter("lname");
			String email = request.getParameter("email");
			String message = request.getParameter("message");
			p1.setString(1, fname);
			p1.setString(2, lname);
			p1.setString(3, email);
			p1.setString(4, message);
	int i=p1.executeUpdate();
	if(i>0) {
		nlpPipeline.init();
	    String ans = nlpPipeline.estimatingSentiment(message);
	    out.println(ans);
	}

	
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		doGet(request, response);
	}

}
