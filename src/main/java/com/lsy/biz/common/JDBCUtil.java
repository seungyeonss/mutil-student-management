package com.lsy.biz.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCUtil {
	
public static Connection getConnection() {
		
		Connection conn = null; // 데이터베이스 연결을 저장할 Connection 객체 선언
		
		try {
			
			// JDBC 1단계 : 드라이버 객체 로딩
			DriverManager.registerDriver(new org.h2.Driver()); // h2 데이터베이스 연결
			
			// JDBC 2단계 :  커넥션 연결
			String jdbcUrl = "jdbc:h2:tcp://localhost/~/test";
			conn = DriverManager.getConnection(jdbcUrl, "sa", ""); //데이터베이스에 연결,  사용자명은 "sa", 비밀번호는 빈 문자열 코드
			
		} catch (SQLException e) { //예외 발생 시 처리
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			 // 사용자에게 데이터베이스 연결 오류 메시지를 출력합니다.
		    System.out.println("데이터베이스 연결에 문제가 발생했습니다.");
		}
		
		return conn; //Connection 객체를 반환합니다.
		
	}

	public static void close(PreparedStatement pstmt, Connection conn) {
		//JDBC 5단계 : 연결 해제
		
		try {
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static void close(ResultSet rs,PreparedStatement pstmt, Connection conn) {
	
		//JDBC 5단계 : 연결 해제
		try {
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		try {
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
