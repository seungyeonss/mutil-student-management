package com.lsy.web.student;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lsy.biz.student.StudentDAO;
import com.lsy.biz.student.StudentVO;

/**
 * Servlet implementation class AddStudentServlet
 */
@WebServlet("/addStudent.do")
public class AddStudentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String encoding;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//System.out.println("---->add 수행중");
		
			//1. 사용자 입력 정보 추출
			ServletContext context = getServletContext();
			this.encoding = context.getInitParameter("studentEncoding");
			
			request.setCharacterEncoding(encoding); //요청된 데이터 처리방식	
			
			 // 응답에 대한 인코딩 설정
	        response.setContentType("text/html; charset=UTF-8"); // 응답 데이터 인코딩 설정
	        response.setCharacterEncoding("UTF-8"); // UTF-8로 인코딩
			
	        //클라이언트로부터 전달된 파라미터를 추출합니다.
			//쿼리 문자열 : id=&name=&subject=&phone
			String id = request.getParameter("id");
			String name = request.getParameter("name");
			String subject = request.getParameter("subject");
			String phone = request.getParameter("phone");
			
			try {
			// 유효성 검사 추가
	        validateStudentData(id, name, subject, phone);
		
			//2. DB 연동 처리 StudentVO 객체 생성 및 값 설정
			StudentVO vo = new StudentVO();
			vo.setId(id);
			vo.setName(name);
			vo.setSubject(subject);
			vo.setPhone(phone);
			
			
			// DAO를 생성하고 DB에 학생 정보를 추가합니다
			StudentDAO studentDAO = new StudentDAO();
			studentDAO.addStudent(vo);
			
			//3. 화면 이동 (성공시 이동)
			response.sendRedirect("getStudentList.do");
			
			 } catch (IllegalArgumentException e) {  // 유효성 검사 실패 처리
		      
			// 에러 메시지를 URL 인코딩하여 학생 추가 페이지로 리다이렉트합니다.
			response.sendRedirect("addStudent.html?errorMessage=" + URLEncoder.encode(e.getMessage(), "UTF-8"));
		   }
	}
	//입력된 학생 데이터의 유효성을 검사하는 메소드
	private void validateStudentData(String id, String name, String subject, String phone) {
		// TODO Auto-generated method stub
		 
		//학번이 null이거나 길이가 9자가 아닌경우 예외발생
		if (id == null || !id.trim().matches("\\d{9}")) {  // 숫자 9자리인지 체크
	        throw new IllegalArgumentException("학번에는 숫자만 입력해야 하며 9자리여야 합니다.");
	    }

        // 이름에 영어와 한글만 허용, 그 외 문자는 예외 처리
        if (!name.trim().matches("[a-zA-Z가-힣]+")) {  // 영어와 한글만 허용
            throw new IllegalArgumentException("이름에는 영어와 한글만 입력할 수 있습니다.");
        }

        // 학과에 한글만 허용, 그 외 문자는 예외 처리
        if (!subject.trim().matches("[가-힣]+")) {  // 한글만 허용
            throw new IllegalArgumentException("학과는 한글만 입력할 수 있습니다.");
        }

        // 전화번호 길이가 13자리가 아닌지 확인 (하이픈 포함)
        if (phone == null || phone.trim().length() != 13) {
            throw new IllegalArgumentException("전화번호는 13자리 숫자여야 합니다.(예시 010-XXXX-XXXX)");
        }

       
    }
	

}
