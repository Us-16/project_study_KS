var tel, answerCode;

function replaceHyphen(target){
    target.value = target.value
                    .replace(/[^0-9]/g, '')
                    .replace(/^(\d{0,3})(\d{0,4})(\d{0,4})$/g, "$1-$2-$3")
                    .replace(/(\-{1,2})$/g, "");
}

/**
    코드번호 전달
    1. 입력한 전화번호 받음
    2. 중복 확인
    3. 중복확인 후 성공 시, 인증번호 전달
*/
function getCode(){
    axios.get(`/sms/code?tel=${tel}`)
        .then(response => {
            answerCode = String(response.data)
            console.log(answerCode)
        })
        .catch(error => {
            alert('오류가 발생했습니다')
        })
}

function dupTelCheck(){
    tel = tel.value.replace(/-/g, '')

    axios.get(`/sms/tel-dup?tel=${tel}`)
        .then(response => {
            console.log(response.data)
            return response.data
        })
        .catch(error => {
            console.error(error)
        })
        .then(response => {
            getCode()
        })
    return false
}

/**
    코드 번호 확인
    - 코드 번호는 answerCode에 저장
*/
function checkCode(){
    var userInput = document.querySelector('#account_telAuth').value
    function success(){
        alert('성공')
        window.location.href="/login"
    }
    function failure(){
        alert('실패')
    }
    (userInput === answerCode) ? success() : failure()
}