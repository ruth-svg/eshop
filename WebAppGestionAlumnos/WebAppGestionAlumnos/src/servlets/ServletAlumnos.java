package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import pojos.Alumno;
import dao.DAOAlumnos;
import util.getConnection;

/**
 * Servlet implementation class ServletAlumnos
 */
@WebServlet("/ServletAlumnos")
@Resource(name = "jdbc/miDatasource")
public class ServletAlumnos extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */


	
}
