/**
 * 자바스크립트 코드 chat gpt활용>
 */

function validateForm() { //폼이 제출되기 전에 호출되어 입력된 데이터의 유효성을 검사함
    var id = document.getElementById("user-id").value; //학번 입력값 가져오기
    var regex = /^[0-9]{9}$/; // 9자리 숫자만 허용

    if (!regex.test(id)) {
        alert("학번은 9자리 숫자여야 합니다.");
        return false; // 폼 제출을 막음
    }
    return true;
}