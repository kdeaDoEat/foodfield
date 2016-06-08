<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%> 
<script type="text/javascript" src="/FoodField/resources/editor/js/HuskyEZCreator.js" charset="utf-8"></script>
<script src="http://code.jquery.com/jquery-2.1.1.min.js" type="text/javascript"></script>
<script type="text/javascript">
	var oEditors = [];
		$(function() {
			
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
					$.ajax({
						url:'wSubmit',
						type:'post',
						data:{title:$('input[name="title"]').val(),
								shop_name:$('input[name="shop_name"]').val(),
								shop_add:$('input[name="shop_add"]').val(),
								contents:oEditors.getById["ir1"].getIR()
						},
						dataType:'json',
						success:function(result){
							alert(result.ok);
						}
					});
				}
			});
			
			nhn.husky.EZCreator.createInIFrame({
				oAppRef: oEditors,
				elPlaceHolder: "ir1",
				//SmartEditor2Skin.html 파일이 존재하는 경로
				sSkinURI: "/FoodField/resources/editor/SmartEditor2Skin.html",	
				htParams : {
					// 툴바 사용 여부 (true:사용/ false:사용하지 않음)
					bUseToolbar : true,				
					// 입력창 크기 조절바 사용 여부 (true:사용/ false:사용하지 않음)
					bUseVerticalResizer : true,		
					// 모드 탭(Editor | HTML | TEXT) 사용 여부 (true:사용/ false:사용하지 않음)
					bUseModeChanger : true,			
					fOnBeforeUnload : function(){
						
					}
				}, 
				fOnAppLoad : function(){
					//기존 저장된 내용의 text 내용을 에디터상에 뿌려주고자 할때 사용
					oEditors.getById["ir1"].exec("PASTE_HTML", [""]);
				},
				fCreator: "createSEditor2"
			});
		})
		
	</script>
    
<div class="container">
        <div class="row">
            <div class="col-lg-12">
            	<!-- 입력 박스  -->
				<form class="form-horizontal" role="form" method="post" name="inputForm" id="inputForm">
					<!--  제목 부분  -->
					<div class="form-group">
						<label for="name" class="col-sm-2 control-label">제목</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" name="title"
								placeholder="input title">
						</div>
					</div>
					<!-- 장소검색부분 -->
					<div class="form-group">
						<label for="human" class="col-sm-2 control-label">장소</label>
						<div class="col-sm-10">
							<div class="input-group">
								<input type="text" class="form-control" id="place"><span class="input-group-btn">
									<button class="btn btn-default" type="button" id="searchmap">검색</button>
								</span>
							</div>
						</div>
					</div>
					<!-- 장소 뜨는 부분 -->
					<div class="form-group">
						<label for="email" class="col-sm-2 control-label">SHOPNAME</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" name="shop_name" placeholder="가게이름" readonly="readonly">
						</div>
						<label for="email" class="col-sm-2 control-label">SHOPADD</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" name="shop_add" placeholder="가게주소" readonly="readonly">
						</div>
					</div>
					<!-- 내용 부분 -->
					<div class="form-group">
						<label for="message" class="col-sm-2 control-label">내용</label>
						<div class="col-sm-10">
							<textarea rows="10" cols="30" id="ir1" name="contents"
								style="width: 95%; height: 412px; min-width:260px; display:none;"></textarea>
						</div>
					</div>
					<!-- 전송버튼  -->
					<div class="form-group">
						<div class="col-sm-10 col-sm-offset-2" style="text-align: center;">
								<button class="btn icon-btn btn-default" type="button" id="submit"><span class="glyphicon btn-glyphicon glyphicon-pencil img-circle text-muted"></span>　저장</button>
								
						</div>
					</div>
				</form>
            </div>
        </div>
  </div>