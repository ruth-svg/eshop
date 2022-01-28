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
	public ServletAlumnos() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String boton = request.getParameter("boton");
		String envio = "";
		if ("Alta".equals(boton))
			envio = "AltaAlumno.jsp";
		else if ("Baja".equals(boton))
			envio = "BajaAlumno.jsp";
		else if ("Actualiza".equals(boton))
			envio = "ActualizaAlumno.jsp";
		else if ("Consulta".equals(boton))
			envio = "ConsultaAlumno.jsp";

		request.getRequestDispatcher(envio).forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Acción:alta,baja,consulta,actualiza
		String boton = request.getParameter("boton");
		boolean error = false;
		Connection conn = null;
		String mensaje = "";
		boolean alt = false;
		int baj = 0, act = 0;

		// Recogida de párametros del formulario
		String nombre = request.getParameter("nombre");
		String apellidos = request.getParameter("apellidos");
		String ciclo = request.getParameter("ciclo");
		String curso = request.getParameter("curso");

		// Creación objeto alumno
		Alumno a = new Alumno();
		a.setNombre(nombre);
		a.setApellidos(apellidos);
		a.setCiclo(ciclo);
		a.setCurso(curso);

		
		try {
			
			// Conexión a BD
			conn = getConnection.conexionBD();
			
			// Llamada a DAOs
			if ("Alta".equals(boton)) {
				//Comprobar que existe siempre (error duplicidad de clave)
				alt = DAOAlumnos.selectId(a, conn);
				if (!alt) {
					DAOAlumnos.insert(a, conn);
					mensaje = "Alta realizada correctamente";
				} else {
					mensaje = "El alumno ya existe.";
				}
			} else if ("Baja".equals(boton)) {
				baj = DAOAlumnos.delete(a, conn);
				System.out.println(baj);
				if (baj == 1) {
					mensaje = "Baja realizada correctamente";
				} else {
					mensaje = "El alumno no" + " existe";
				}
			} else if ("Actualizar".equals(boton)) {
				act = DAOAlumnos.update(a, conn);
				if (act == 1) {
					mensaje = "Actualización realizada correctamente";
				} else {
					mensaje = "El alumno no" + " existe";
				}
			} else if ("Consultar".equals(boton)) {
				a = DAOAlumnos.select(a, conn);
				if (a != null) {
					mensaje = "El alumno consultado es:" + a.toString();
				} else {
					mensaje = "El alumno no" + " existe";
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			error = true;
			System.err.println("ServletAlumnos:Se ha producido el siguiente error");
			e.printStackTrace();
		} finally {
		
			if ("Volver".equals(request.getParameter("volver"))) {
				request.getRequestDispatcher("MenuBotones.jsp").forward(request, response);
			} else {
				if (error) {
					mensaje = "Error al realizar " + boton;
				}
				request.setAttribute("mensaje", mensaje);
				request.getRequestDispatcher("mensaje.jsp").forward(request, response);

			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

}
