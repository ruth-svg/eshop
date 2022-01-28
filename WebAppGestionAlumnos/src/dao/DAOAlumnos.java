package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import pojos.Alumno;

public class DAOAlumnos {

	public static int insert(Alumno a, Connection conn) throws ClassNotFoundException, SQLException {
		String sql = "INSERT INTO matriculas.alumnos VALUES (?,?,?,?,?)";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, a.getIdmatricula());
			ps.setString(2, a.getNombre());
			ps.setString(3, a.getApellidos());
			ps.setString(4, a.getCiclo());
			ps.setString(5, a.getCurso());
			return ps.executeUpdate();
		}
	}

	public static int delete(Alumno a, Connection conn) throws ClassNotFoundException, SQLException {
		String sql = "DELETE  FROM  matriculas.alumnos WHERE idmatricula= ?";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, a.getIdmatricula());
			return ps.executeUpdate();
		}
	}

	public static int update(Alumno a, Connection conn) throws ClassNotFoundException, SQLException {
		String sql = "UPDATE  matriculas.alumnos a SET CICLO=?,CURSO=? WHERE a.idmatricula= ?";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, a.getCiclo());
			ps.setString(2, a.getCurso());
			ps.setString(3, a.getIdmatricula());
			return ps.executeUpdate();
		}
	}

	public static boolean selectId(Alumno a, Connection conn) throws ClassNotFoundException, SQLException {
		String sql = "SELECT * FROM matriculas.alumnos a WHERE a.idmatricula = ?";
		boolean exist = false;
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, a.getIdmatricula());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				exist = true;
			}
		}
		return exist;
	}

	public static Alumno select(Alumno a, Connection conn) throws ClassNotFoundException, SQLException {
		String sql = "SELECT * FROM matriculas.alumnos a WHERE a.idmatricula = ?";
		Alumno alumno = null;
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, a.getIdmatricula());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				alumno = new Alumno();
				alumno.setNombre(rs.getString(2));
				alumno.setApellidos(rs.getString(3));
				alumno.setCiclo(rs.getString(4));
				alumno.setCurso(rs.getString(5));
			}
		}
		return alumno;
	}

}
