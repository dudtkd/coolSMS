<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
</head>
<body>
	<table>
		<tr class="mobileNo">
			<th><label for="phone">휴대폰 번호</label></th>
			<td>
				<p>
					<input id="phone" type="text" name="phone" title="전화번호 입력" required />
					<button id="phoneChk" class="doubleChk">인증번호 보내기</button><br /> 
					
					<input id="phone2" type="text" name="phone2" title="인증번호 입력" disabled required />
					<button id="phoneChk2" class="doubleChk">본인인증</button> 
					<span class="point successPhoneChk">휴대폰 번호 입력후 인증번호 보내기를 해주십시오.</span> 
					<input type="hidden" id="phoneDoubleChk" />
				</p>
				<p class="tip">최초 가입 시에만 사용하고 있습니다. 따로 저장되지 않습니다.(번호만 입력해주세요.)</p>
			</td>
		</tr>
	</table>

</body>
<script type="text/javascript">
//휴대폰 번호 인증
var code2 = ""; // 인증번호를 전역으로 사용하기 위해 변수 설정
$("#phoneChk").click(function(){
	var phone = $("#phone").val();
	if(phone == null || phone == ""){
		alert("정보를 제대로 입력하세요")	// 약식으로 입력값 검증 나중에 수정
		return false;
	}
	alert("인증번호 발송이 완료되었습니다.\n휴대폰에서 인증번호 확인을 해주십시오.");
	var info = {
			phone : phone
	}
	$.ajax({
        type:"post",
        url:"/phoneCheck.do",
        contentType : "application/json; charset=utf-8",
        data: JSON.stringify(info),
        cache : false,	// 솔직히 이건 잘 모르겟음
        success:function(data){
        	if(data == "null" && data == ""){
        		alert("휴대폰 번호가 올바르지 않습니다.")
				$(".successPhoneChk").text("유효한 번호를 입력해주세요.");
				$(".successPhoneChk").css("color","red");
				$("#phone").attr("autofocus",true);
        	}else{	        		
        		$("#phone2").attr("disabled",false);
        		$("#phoneChk2").css("display","inline-block");
        		$(".successPhoneChk").text("인증번호를 입력한 뒤 본인인증을 눌러주십시오.");
        		$(".successPhoneChk").css("color","green");
        		$("#phone").attr("readonly",true);
        		code2 = data;
        	}
        }
    });
});

//휴대폰 인증번호 대조
$("#phoneChk2").click(function(){
	if($("#phone2").val() == code2){
		$(".successPhoneChk").text("인증번호가 일치합니다.");
		$(".successPhoneChk").css("color","green");
		$("#phoneDoubleChk").val("true");
		$("#phone2").attr("disabled",true);
	}else{
		$(".successPhoneChk").text("인증번호가 일치하지 않습니다. 확인해주시기 바랍니다.");
		$(".successPhoneChk").css("color","red");
		$("#phoneDoubleChk").val("false");
		$(this).attr("autofocus",true);
	}
});

</script>
</html>