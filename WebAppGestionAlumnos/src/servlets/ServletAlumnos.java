

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
