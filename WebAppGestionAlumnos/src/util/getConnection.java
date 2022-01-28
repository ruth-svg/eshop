package util;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class getConnection {
	
	public static Connection conexionBD () throws Exception{
		
		Connection conn=null;
		DataSource ds=null;
		
		try {
			Context initContext = new InitialContext();
			ds= (DataSource) initContext.lookup("java:comp/env/jdbc/miDataSource");
			conn= ds.getConnection();
			return conn;
			
		} catch (NamingException  e) {
			// TODO Auto-generated catch block
			System.err.println("getConnection:No se puede obtener el DataSource");
			e.printStackTrace();
			throw new Exception ("Getconnection: No se puede obtener el DataSource");
		} catch ( SQLException e){
			System.err.println("getConnection:Error al conectar a la BBDD");
			e.printStackTrace();
			throw new Exception("GetConnection: Error al conectar a la BBDD");
		} 
		
		
	}

}
