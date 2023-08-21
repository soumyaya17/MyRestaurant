package DAO;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DTO.Customer;

@WebServlet("/login")
public class MyLogin extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String email = req.getParameter("username");
		String password = req.getParameter("password");

		// Verify if email exists
		Mydao dao = new Mydao();
		if (email.equals("atulshenoy17@gamil.com") && password.equals("as17")) {
			resp.getWriter().print("<h1 style='color:red'>login succesfull!!!!!!!!</h1>");
			req.getRequestDispatcher("AdminHome.html").include(req, resp);
		}

		else {
			Customer customer = dao.fetchByEmail(email);

			if (customer == null) {
				resp.getWriter().print("<h1 style='color:red'>invalid email</h1>");
				req.getRequestDispatcher("Login.html").include(req, resp);
			} else {
				if (password.equals(customer.getPassword())) {
					resp.getWriter().print("<h1 style='color:green'>login success</h1>");
					req.getRequestDispatcher("Home.html").include(req, resp);
				} else {
					resp.getWriter().print("<h1 style='color:red'>invalid password</h1>");
					req.getRequestDispatcher("Login.html").include(req, resp);
				}
			}
		}
	}
}
