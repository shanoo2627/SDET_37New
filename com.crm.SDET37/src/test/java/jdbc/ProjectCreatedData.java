package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.Driver;

public class ProjectCreatedData {
public static void main(String[] args) throws Exception {
	//step1:register to db//
		//Driver driverRef =new Driver();
		Driver driverRef = new Driver();
		//step2:connect to mysql//
		DriverManager.registerDriver(driverRef);
		Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/projects","root","root");
		Statement statement= connection.createStatement();
		String query="select * from amazon; ";
		//step3:execute query//
		ResultSet result=statement.executeQuery(query);
		while(result.next()) {
			System.out.println(result.getString(1)+"\t"+result.getString(2)+"\t"+result.getString(3)+"\t");
		}
		//step4:close database//
		connection.close();
}
}

