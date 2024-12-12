package com.lsy.web.student;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lsy.biz.student.StudentDAO;
import com.lsy.biz.student.StudentVO;


 // Servlet implementation class UpdateStudentServlet
 
@WebServlet("/updateStudent.do")
public class UpdateStudentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String encoding;
	
	 //GET 요청을 처리해 학생 정보를 수정할 수 있는 폼을 제공
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        response.setContentType("text/html; charset=UTF-8");// 응답 인코딩
        request.setCharacterEncoding("UTF-8"); // 요청 인코딩
        
        String id = request.getParameter("id"); // 수정할 학생의 ID 가져오기
        

        // DB에서 학생 정보 조회
        StudentDAO studentDAO = new StudentDAO();
        StudentVO vo = new StudentVO();
        vo.setId(id); // 학번을 VO 객체에 설정
        StudentVO student = studentDAO.searchStudent(vo); // 학생 조회

       
        if (student != null ) {  // 입력한 학번의 학생이 존재하는 경우 수정 폼을 출력
            PrintWriter out = response.getWriter();
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title> 학생 정보 수정 </title>");
            out.println("<link rel='stylesheet' href='css/updateStudent.css'>"); // CSS 파일 연결
            out.println("</head>");
            out.println("<body>");
            out.println("<h2>학생 정보 수정</h2>");
            out.println("<form action='updateStudent.do' method='POST'>");
            out.println("<input name='id' type='hidden' value='" + student.getId() + "'>");
            out.println("<label for='id'>학번:</label>");
            out.println("<input type='text' id='id' name='id' value='" + student.getId() + "' readonly><br>");
            out.println("<label for='name'>이름:</label>");
            out.println("<input type='text' id='name' name='name' value='" + student.getName() + "' readonly><br>");
            out.println("<label for='subject'>학과:</label>");
            out.println("<input type='text' id='subject' name='subject' value='" + student.getSubject() + "'required><br>");
            out.println("<label for='phone'>전화번호:</label>");
            out.println("<input type='text' id='phone' name='phone' value='" + student.getPhone() + "' required><br>");
            out.println("<button type='submit'>수정 완료</button>");
            out.println("</form>");
            out.println("</body>");
            out.println("</html>");
        } else {
            
            PrintWriter out = response.getWriter(); // 학생 정보가 없는 경우 에러 메시지를 출력하고 검색 페이지로 이동
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<body>");
            out.println("<script type=\"text/javascript\">");
            out.println("alert('해당 학번의 학생이 존재하지 않습니다.');"); //알림창 출력
            out.println("location='updateStudent.html';"); // 검색 페이지로 이동
            out.println("</script>");
            out.println("</body>");
            out.println("</html>");
        }
	}

    

    
    // @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     
	//Post 요청을 처리하여 학생 정보를 수정함
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");  // 요청 인코딩
        response.setContentType("text/html; charset=UTF-8");  // 응답 인코딩

        // 수정된 정보 가져오기
        String id = request.getParameter("id");
        String subject = request.getParameter("subject").trim();
        String phone = request.getParameter("phone").trim();
        

        // 유효성 검사
        if (!subject.trim().matches("^[가-힣]+$")) { //학과는 한글만 가능 
            PrintWriter out = response.getWriter();
            out.println("<script type=\"text/javascript\">");
            out.println("alert('학과는 한글만 입력할 수 있습니다.');"); //한글이 아닌경우 에러메시지 출력
            out.println("window.history.back();"); // 수정 페이지로 다시 돌아감
            out.println("</script>");
            out.close();
            return;
        }
            
        if (!phone.matches("\\d{3}-\\d{4}-\\d{4}")) { //전화번호는 숫자만 가능 
            PrintWriter out = response.getWriter();
            out.println("<script type=\"text/javascript\">");
            out.println("alert('전화번호는 13자리 숫자여야 합니다.(예시 010-XXXX-XXXX)');"); //13자리 숫자가아닌 경우 에러메시지 출력
            out.println("window.history.back();"); // 수정 페이지로 다시 돌아감
            out.println("</script>");
            out.close();
            return;
        }
       

        // DB에 수정된 정보 업데이트
        StudentVO student = new StudentVO();
        student.setId(id);
        student.setSubject(subject);
        student.setPhone(phone);

        StudentDAO studentDAO = new StudentDAO();
        try {
        										// 업데이트 메서드 호출 (DB에 수정된 학생 정보 반영)
            studentDAO.updateStudent(student);  // updateStudent() 호출
        } catch (Exception e) {
            e.printStackTrace();
            // 오류가 발생하면 오류 메시지 표시
            PrintWriter out = response.getWriter();
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<body>");
            out.println("<h2>학생 정보 수정에 실패했습니다.</h2><br>");
            out.println("<a href='searchStudent.html'>다시 입력하기</a>");
            out.println("</body>");
            out.println("</html>");
            return;
        }

        // 수정 성공 시 성공 메시지 출력
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<body>");
        out.println("<h2>학생 정보가 성공적으로 수정되었습니다.</h2><br>");
        out.println("<a href='programList.html'>홈으로 돌아가기</a>");
        out.println("</body>");
        out.println("</html>");
    }
}
    
	