<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    
<script type="text/javascript" src="/FoodField/resources/ckeditor/ckeditor.js" charset="utf-8"></script>
<script src="http://code.jquery.com/jquery-2.1.1.min.js" type="text/javascript"></script>
<script type="text/javascript">
		$(function() {
			CKEDITOR.replace('editor',{
				filebrowserImageUploadUrl: 'uploadPhoto?${_csrf.parameterName}=${_csrf.token}',
			});
			
			$('#searchmap').on('click',function(){
				if($('#place').val() == ''){
					alert('검색단어를 입력해주세요!');
				}else{
					var popUrl = "searchMap?search="+$('#place').val();	//팝업창에 출력될 페이지 URL
					var popOption = "width=870, height=660, resizable=no, scrollbars=no, status=no;";    //팝업창 옵션(optoin)
					window.open(popUrl,"",popOption);
				} 
			});
			$('#submit').on('click',function(){
				var result = confirm('내용을 확인하셨습니까?');
				if(result){
					var con = CKEDITOR.instances.editor.getData().replace(/\r\n/g, '');
					if(charByteSize(con)>4000) {
						alert('4000byte 이하로 입력해주세요! 현재 : '+charByteSize(con));
						return;
					}
					$.ajax({
						url:'wSubmit?${_csrf.parameterName}=${_csrf.token}',
						type:'post',
						data:{nickname:'${sessionScope.userInfo.nickname}',
								title:$('input[name="title"]').val(),
								shop_name:$('input[name="shop_name"]').val(),
								shop_add:$('input[name="shop_add"]').val(),
								contents:con
						},
						dataType:'json',
						success:function(result){
							alert(result.ok);
						},error:function(request,status,error){
							alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
						}
					});
				}
			});
			
			window.parent.CKEDITOR.tools.callFunction('${CKEditorFuncNum}', '${file_path}', '파일 전송 완료.');
		})
		
		
	function charByteSize(msg) {
		if (msg == null || msg.length == 0)
			return 0;
		var i, size = 0;
		var charCode, chr = null;
		for (i = 0; i < msg.length; i++)
		{
			chr = msg.charAt(i);
			charCode = chr.charCodeAt(0);
			if (charCode <= 0x00007F)	size += 1;
			else if (charCode <= 0x0007FF)	size += 2;
			else if (charCode <= 0x00FFFF)  size += 3;
			else  size += 4;
		}
		return size;
	}
</script>
    
<div class="container">

      	<!-- 입력 박스  -->
	<form class="form-horizontal" role="form" method="post" name="inputForm" id="inputForm">
		<!--  제목 부분  -->
		<div class="form-group">
		<label class="col-xs-12 col-md-1 col-md-offset-1 control-label">제목　</label>
			<div class="col-xs-10 col-md-9">
				<input type="text" class="form-control" name="title"
					placeholder="input title">
			</div>
		</div>
		
		<!-- 장소검색부분 -->
		<div class="form-group">
			<label class="col-xs-12 col-md-1 col-md-offset-1 control-label">장소　</label>
			<div class="col-xs-10 col-md-9">
				<div class="input-group">
					<input type="text" class="form-control" id="place"><span class="input-group-btn">
						<button class="btn btn-default" type="button" id="searchmap">검색</button>
					</span>
				</div>
			</div>
		</div>
		
		<!-- 장소 뜨는 부분 -->
		<div class="form-group">
			<label class="col-xs-12 col-md-1 col-md-offset-1 control-label">SHOPNAME　</label>
			<div class="col-xs-10 col-md-9">
				<input type="text" class="form-control" name="shop_name" placeholder="가게이름" readonly="readonly">
			</div>
			<label class="col-xs-12 col-md-1 col-md-offset-1 control-label">SHOPADD　</label>
			<div class="col-xs-10 col-md-9">
				<input type="text" class="form-control" name="shop_add" placeholder="가게주소" readonly="readonly">
			</div>
		</div>
		
		<!-- 내용 부분 -->
		<div class="form-group">
			<label class="col-xs-12 col-md-1 col-md-offset-1 control-label">내용　</label>
			<div class="col-xs-10 col-md-9">
				<textarea id="editor" style="width:100%; height:400px;"></textarea>
			</div>
		</div>
		<!-- 전송버튼  -->
		<div class="form-group">
			<div class="col-xs-10 col-sm-offset-2" style="text-align: center;">
					<button class="btn icon-btn btn-default" type="button" id="submit"><span class="glyphicon btn-glyphicon glyphicon-pencil img-circle text-muted"></span>　저장</button>
			</div>
		</div>
	</form>
</div>