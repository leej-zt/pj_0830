$(document).ready(function(){

	// 초기설정
	$("#nameVal").prop("disabled", true);

	// 분류 드롭박스의 값에 변화가 생겼을 경우
	$(document).on('change', '.type', function(){

		var type = $('#type').val();
		$("#token").val();
		
		if(type=='') {
			alert("분류를 선택해주세요.")
			return false;
		}
		
		var data = $("#home_form").serialize();
		
		// 목록 검색
		$.ajax({
			url: "/list",
			type: "POST",
			data: $("#home_form").serialize()
		// 정상종료
		}).done(function (data, textStatus, jqXHR) {
			var result = $(data).find(".nameVal");
			$(".nameVal").html(result);
			$("#nameVal").prop("disabled", false);
			
			var token = $(data).find("#token");
			$(".token").html(token);
			
			$('#nameList').val("");
			
		// 이상종료
		}).fail(function (jqXHR, textStatus, errorThrown) {
			alert(textStatus + jqXHR);
		});
	});


	// 조회 버튼 클릭 시
	$(document).on('click', '#searchBtn', function(){

		var name = $('#nameList').val();
		$('#nameVal').val(name);
		
		if(name=='') {
			alert("이름을 선택해주세요.")
			return false;
		}
		
		var data = $("#home_form").serialize();
		
		// 가격 검색
		$.ajax({
			url: "/search",
			type: "POST",
			data: $("#home_form").serialize()
		// 정상종료
		}).done(function (data, textStatus, jqXHR) {
			var result = $(data).find(".price");
			$(".price").html(result);
			
		// 이상종료
		}).fail(function (jqXHR, textStatus, errorThrown) {
			alert(textStatus + jqXHR);
		});
	});

});
