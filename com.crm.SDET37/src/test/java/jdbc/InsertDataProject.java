package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.Driver;

public class InsertDataProject {
public static void main(String[] args) throws SQLException {
		
		//Driver driverRef =new Driver();
		Driver driverRef = new Driver();
		DriverManager.registerDriver(driverRef);
		Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/projects","root","root");
		Statement statement= connection.createStatement();
		String query="insert into amazon values('shirts','ID45','2000')";
		
		int result=statement.executeUpdate(query);

		
	if(result==1) {
		System.out.println("data is created");
	}
	else {
		System.out.println("data is not created");
	}
		
		connection.close();
	}
	}



