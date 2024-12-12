package com.lsy.web.student;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lsy.biz.student.StudentDAO;
import com.lsy.biz.student.StudentVO;


@WebServlet("/getStudentList.do")
public class GetStudentListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * 클라이언트로부터의 요청을 처리하는 메소드
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//System.out.println("--->list 수행중");
		
		//1.DB 연동 처리
		StudentVO vo = new StudentVO(); //StudentVO 객체 생성
				
		StudentDAO studentDAO = new StudentDAO();//데이터베이스 접근 DAO객체 생성
		List<StudentVO> studentList = studentDAO.getStudentList(vo);// 모든 학생 정보를 가져옴
				
		//2. 응답 화면 구성
		response.setContentType("text/html;charset=UTF-8"); // 응답 데이터 처리
		PrintWriter out = response.getWriter();//응답을 보내기위한 PrintWriter 객체 생성
		
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<title> 학생 전체 출력 </title>");
		out.println("<link rel='stylesheet' href='css/studentList.css'>"); // CSS 파일 연결
		out.println("</head>");
		out.println("<body>");
		out.println("<h1> 학생 목록 </h1>");
		
		out.println("<div class = 'container' >");
		out.println("<table>");
		out.println("<tr>");
		out.println("<th> 학번 </th>"); // 표 제목 학번
		out.println("<th> 이름 </th>"); // 표 제목 이름
		out.println("<th> 학과 </th>"); // 표 제목 학과 
		out.println("<th> 전화번호 </th>"); // 표 제목 전화번호 
		out.println("</tr>");
		
		for (StudentVO student : studentList) { //학생 리스트 테이블에 데이터를 추가
			out.println("<tr>");
			out.println("<td>" + student.getId() + "</td>");  // 학번 불러오기
			out.println("<td>" + student.getName() + "</td>"); // 이름 불러오기
			out.println("<td>" + student.getSubject() + "</td>"); // 학과 불러오기
			out.println("<td>" + student.getPhone() + "</td>");   // 전화번호 불러오기 
			out.println("</tr>");
		}
		
		out.println("</table>");
		out.println("<br>");
		out.println("<a href = 'programList.html'> 홈으로 돌아가기 </a> ");
		out.println("</div");
		out.println("</body>");
		out.println("</html>");
		
		out.close(); //닫기
	}

}
