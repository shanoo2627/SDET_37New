package jdbc;


	import java.sql.Connection;
	import java.sql.DriverManager;
	import java.sql.SQLException;
	import java.sql.Statement;

	import com.mysql.jdbc.Driver;


	public class ToInsertData {
	
	public static void main(String[] args) throws SQLException {
		
		//Driver driverRef =new Driver();
		Driver driverRef = new Driver();
		DriverManager.registerDriver(driverRef);
		Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/students","root","root");
		Statement statement= connection.createStatement();
		String query="insert into student values('virat','kohli','5346','1987/01/07','male')";
		String query2="insert into student values('Dhoni','sigh','5346','1987/01/07','male')";
		int result=statement.executeUpdate(query);
		int result1=statement.executeUpdate(query2);
		
	if(result==1) {
		System.out.println("data is created");
	}
	else {
		System.out.println("data is not created");
	}
		
		connection.close();
	}
	}

