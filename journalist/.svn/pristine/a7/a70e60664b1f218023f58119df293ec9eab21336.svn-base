package edu.miami.ccs.life;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Driver;
import java.sql.*;

public class DBConnection
{
	private String jdbcUri = "jdbc:postgresql://cheddar.ccs.miami.edu:5432/life_db";
	private String dbPword = "postgres";
	private String dbUname = "postgres";

	public DBConnection()
	{
	
	}
	

	public DBConnection(String jdbcUri, String dbUname, String dbPword)
    {
	    super();
	    this.jdbcUri = jdbcUri;
	    this.dbPword = dbPword;
	    this.dbUname = dbUname;
    }
	
	public Connection getConnection()
	{
		String jdbcDriver = System
		        .getProperty("org.postgresql.Driver");
		Connection con = null;
		Connection con1 = null;
		try
		{
			DriverManager.registerDriver(new Driver());
		}
		catch (SQLException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try
		{
			con = DriverManager.getConnection(jdbcUri, dbUname, dbPword);
		}
		catch (SQLException e)
		{ 
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return con;

	}
}
