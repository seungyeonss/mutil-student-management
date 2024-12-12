package com.lsy.biz.student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.lsy.biz.common.JDBCUtil;

//DAO(Data Access OBject) 클래스
public class StudentDAO {
	
	//JDBC 관련변수
	private Connection conn; // 데이터베이스 연결을 위한 Connection 객체
	private PreparedStatement pstmt; // SQL 문을 실행하기 위한 PreparedStatement 객체
	private ResultSet rs; // SQL 쿼리 실행 결과를 저장하는 ResultSet 객체
	
		
	//SQL 명령어
	private static String STUDENT_INSERT = "insert into student(id, name, subject, phone) values(?,?,?,?)"; //학생 정보를 데이터베이스에 삽입
	private static String STUDENT_LIST = "select * from student order by id desc";// 모든 학생 정보를 학번 내림차순으로 조회
	private static String STUDENT_SEARCH = "select * from student where id = ?";//특정 학번의 학생 정보를 조회
	private static String STUDENT_UPDATE = "update student set subject = ? , phone = ? where id=?";	//특정 학번의 학생 정보를 수정
	
	
	//학생 목록 보기
	public List<StudentVO> getStudentList(StudentVO vo) { 
		// TODO Auto-generated method stub
		//System.out.println("----> 글 목록 검색 수행중...");
		
		List<StudentVO> studentList = new ArrayList<StudentVO>(); //학생 정보를 저장할 리스트 생성
		
			try {
				conn = JDBCUtil.getConnection(); //데이터베이스 연결 생성
				pstmt = conn.prepareStatement(STUDENT_LIST);//SQL 문 준비
				
				rs = pstmt.executeQuery(); //SQL 문 실행 및 결과 
				
				while(rs.next()) { //결과를 돌면서 학생 정보 생성
					
					StudentVO student = new StudentVO(); //새로운 StudentVO 객체 생성
					
					//ResultSet에서 각 열의 값을 가져와 StudentVO 객체에 설정
					student.setId(rs.getString("ID"));
					student.setName(rs.getString("NAME"));
					student.setSubject(rs.getString("SUBJECT"));
					student.setPhone(rs.getString("PHONE"));
					
					studentList.add(student); //리스트에 학생 객체 추가
					
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				JDBCUtil.close(rs, pstmt, conn);
			}
			
			return studentList;
	}


	//학생 등록
	public void addStudent(StudentVO vo) { 
		// TODO Auto-generated method stub
		//System.out.println("등록 수행중");
		try {
			conn = JDBCUtil.getConnection();//연결 생성
			pstmt = conn.prepareStatement(STUDENT_INSERT); //sql문 준비 
			//PrepareStatement에 매개변수 설정
			pstmt.setString(1, vo.getId());// 첫 번째 물음표에 학번 설정
			pstmt.setString(2, vo.getName());// 두 번째 물음표에 이름 설정
			pstmt.setString(3, vo.getSubject()); // 세 번째 물음표에 학과 설정
			pstmt.setString(4, vo.getPhone());// 네 번째 물음표에 전화번호 설정
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCUtil.close(pstmt, conn);
		}
	}

	// 입력한 학번에 해당하는 학생 정보를 조회
	public StudentVO searchStudent(StudentVO vo) {
		// TODO Auto-generated method stub
		StudentVO student = null; // 조회 결과를 저장할 StudentVO 객체 초기화
		
		try {
			conn = JDBCUtil.getConnection();//연결 생성
			pstmt = conn.prepareStatement(STUDENT_SEARCH);//SQL문 준비
			pstmt.setString(1, vo.getId()); //조회할 학번 설정
			rs = pstmt.executeQuery();
			
			if(rs.next()) { //데이터가 존재하면
				student = new StudentVO();
				student.setId(rs.getString("ID"));
				student.setName(rs.getString("NAME"));
				student.setSubject(rs.getString("SUBJECT"));
				student.setPhone(rs.getString("PHONE"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCUtil.close(rs, pstmt, conn);//해제
			
		}
		return student; //조회된 학생 객체 반환
	}

	// 입력한 학번에 해당하는 학생 정보를 수정
	public void updateStudent(StudentVO vo) {
		// TODO Auto-generated method stub
		try {
			conn = JDBCUtil.getConnection();//연결 생성 
			pstmt = conn.prepareStatement(STUDENT_UPDATE);//SQL문 준비
			//prepareStatement 매개변수 설정
			pstmt.setString(1, vo.getSubject());// 첫 번째 물음표에 새로운 학과 설정
			pstmt.setString(2, vo.getPhone());// 두 번째 물음표에 새로운 전화번호 설정
			pstmt.setString(3, vo.getId());// 세 번째 물음표에 학번 설정
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCUtil.close(pstmt, conn); //해제
		}
	}
	
}

