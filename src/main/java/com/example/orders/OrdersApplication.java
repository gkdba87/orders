package com.example.orders;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;
import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootApplication
@RestController
public class OrdersApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrdersApplication.class, args);
	}
	@RequestMapping("/hello")
	public String hello(){
		return "Namaste";
	}

	@Autowired
	private Environment env;
	@RequestMapping("/dbtest")
	public String dbtest(){

		String sql = "SELECT * FROM customer";
		Connection conn = null;

		try {
			conn =  DriverManager.getConnection(env.getProperty("spring.datasource.url"),env.getProperty("spring.datasource.username"),env.getProperty("spring.datasource.password"));
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			rs.next();
			String res="<h1>"+rs.getInt("CUST_ID") + rs.getString("NAME")+rs.getInt("Age")+"</h1>";
			return res;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {}
			}
		}
	}
}

