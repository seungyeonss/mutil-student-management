/**
 * 
 */

// URL에서 파라미터를 추출하는 함수 <자바스크립트 에러 코드 chat gpt 활용>
function getParameterByName(name) {
    const url = window.location.href;  // 현재 페이지 URL을 가져옴
    name = name.replace(/[\[\]]/g, '\\$&');  // 파라미터 이름에 [, ]와 같은 특수 문자가 있을 경우, 정규식에서 문제를 일으킬 수 있으므로 이스케이프 처리합니다.
    const regex = new RegExp('[?&]' + name + '(=([^&#]*)|&|#|$)');  // 파라미터 추출을 위한 정규식
    const results = regex.exec(url); //정규식을 사용하여 URL에서 파라미터를 추출
    if (!results || !results[2]) return null;  // 파라미터가 없으면 null 반환
    return decodeURIComponent(results[2].replace(/\+/g, ' '));  // 파라미터 값을 반환
}

// 페이지가 로드될 때 오류 메시지를 확인하고, 경고창을 띄움
window.onload = function() {
    const errorMessage = getParameterByName('errorMessage');  // URL에서 errorMessage 파라미터 추출
    if (errorMessage) {
        alert(errorMessage);  // 오류 메시지를 경고창으로 표시
    }
}
