package com.lsy.web.student;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lsy.biz.student.StudentDAO;
import com.lsy.biz.student.StudentVO;

/**
 * Servlet implementation class SearchStudentServlet
 */
@WebServlet("/searchStudent.do")
public class SearchStudentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 *클라이언트로부터 요청을 처리하는 메소드
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//System.out.println("----> search 수행중");
		
		// 1. 입력받은 학번 가져오기
        String id = request.getParameter("id").trim();  // 입력된 학번 앞뒤 공백 제거
        
        // 2. 학번 유효성 검사 (9자리 숫자인지 확인)
        if (id == null || !id.matches("\\d{9}")) {  // 학번이 9자리 숫자가 아닐 경우
           
        	response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            
            out.println("<script type=\"text/javascript\">");//에러메시지를 자바스크립트로 출력 <자바스크립트 부분 chat gpt 이용>
            out.println("alert('학번은 9자리 숫자여야 합니다. 다시 입력하세요.');");
            out.println("window.location.href = 'searchStudent.html';"); // 다시 검색 페이지로 이동
            out.println("</script>");
            out.close();
            return; // 메서드 종료
        }
        
        // 3. DB 연동 처리
        StudentVO vo = new StudentVO();  // StudentVO 객체 생성 및 값 설정
        vo.setId(id); //입력받은 학번을 VO 객체에 설정
        
        StudentDAO studentDAO = new StudentDAO();
        StudentVO student = studentDAO.searchStudent(vo); 
        
        // 4. 응답 페이지 설정
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        
        if (student != null) { // 학번이 존재하는 경우
        	out.println("<!DOCTYPE html>");
    		out.println("<html>");
    		out.println("<head>");
    		out.println("<title> 학생 조회 </title>");
    		out.println("<link rel='stylesheet' href='css/studentList.css'>"); // CSS 파일 연결
    		out.println("</head>");
    		out.println("<body>");
    	
            out.println("<h2>학번 조회 결과</h2>");
            out.println("<div class = 'container' >");
    		out.println("<table>");
    		out.println("<tr>");
    		out.println("<th> 학번 </th>"); // 표 제목 학번
    		out.println("<th> 이름 </th>"); // 표 제목 이름
    		out.println("<th> 학과 </th>"); // 표 제목 학과 
    		out.println("<th> 전화번호 </th>"); // 표 제목 전화번호 
    		out.println("</tr>");
    		
    		out.println("<tr>");
			out.println("<td>" + student.getId() + "</td>");  // 학번 불러오기
			out.println("<td>" + student.getName() + "</td>"); // 이름 불러오기
			out.println("<td>" + student.getSubject() + "</td>"); // 학과 불러오기
			out.println("<td>" + student.getPhone() + "</td>");   // 전화번호 불러오기 
			out.println("</tr>");
          
            out.println("</table>");
    		out.println("<br><br>");
    		out.println("<a href = 'programList.html'> 홈으로 돌아가기 </a> ");
    		
    		
    		out.println("</div");
    		out.println("</body>");
    		out.println("</html>");
    		
        } else { // 학번이 존재하지 않는 경우
        	out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<body>");
            out.println("<script type=\"text/javascript\">"); //에러메시지 출력 <자바스크립트 chatgpt 사용>
            out.println("alert('일치하는 학번이 없습니다. 다시 입력하세요.');");
            out.println("location='searchStudent.html';"); // 다시 검색 페이지로 이동
            out.println("</script>");
            out.println("</body>");
            out.println("</html>");
            
        }
        out.close();
    }
	

}
