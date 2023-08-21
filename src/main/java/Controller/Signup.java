package Controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import DAO.Mydao;
import DTO.Customer;

@WebServlet(urlPatterns =  "/Signup")
@MultipartConfig
public class Signup extends HttpServlet {
	private static final Mydao DAO = null;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//name
		String name = req.getParameter("username");
		
		//email
		String email = req.getParameter("email");
		
		//password
		String password = req.getParameter("password");
		
		//confirm mobile
		long mobile = Long.parseLong(req.getParameter("mobile"));
		
		//DOB
		LocalDate date = LocalDate.parse(req.getParameter("date"));
		
		//calculating the age
		int age = Period.between(date, LocalDate.now()).getYears();
		
		//Gender
		String gender = req.getParameter("gender");
		
		//logic for storing the photo
		Part pic = req.getPart("picture");
		byte picture[] = null;
		picture = new byte[pic.getInputStream().available()];
		pic.getInputStream().read(picture);
		
		Customer c =new Customer();
		c.setAge(age);
		c.setDob(date);
		c.setEmail(email);
		
		c.setName(name);
		c.setMobile(mobile);
		c.setPassword(password);
		c.setPicture(picture);
		
		
		
		
		
		Mydao dao=new Mydao();
	
		if(dao.fetchByEmail(email)==null&& dao.fetchByMobile(mobile)==null) {
			dao.save(c);		
		resp.getWriter().print("<h1 style=' color :green'> Account created Succesfully</h1>");
		req.getRequestDispatcher("Login.html").include(req, resp);
		}else {
			resp.getWriter().print("<h1 style='color:red'>Email and number Should be Unique  </h1>");
			req.getRequestDispatcher("Signup.html").include(req, resp);
		}
	}
}
