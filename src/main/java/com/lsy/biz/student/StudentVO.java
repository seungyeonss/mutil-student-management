package com.lsy.biz.student;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/*
@NoArgsConstructor  // 기본 생성자 자동 생성
@AllArgsConstructor // 모든 필드를 포함하는 생성자 자동 생성
@Getter // 필드별 Getter 생성
@Setter // 필드별 Setter 생성
@ToString // toString() 메서드 자동 생성
*/
@Data

//VO(value Object)클래스
public class StudentVO {
	
	private String id;    // 학번
	private String name;  // 이름
	private String subject; // 학과
	private String phone; // 전화번호
}
