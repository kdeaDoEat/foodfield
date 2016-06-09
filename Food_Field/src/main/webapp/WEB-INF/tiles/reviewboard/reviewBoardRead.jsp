<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<style>
    .overlay_info {border-radius: 6px; margin-bottom: 12px; float:left;position: relative; border: 1px solid #ccc; border-bottom: 2px solid #ddd;background-color:#fff;}
    .overlay_info:nth-of-type(n) {border:0; box-shadow: 0px 1px 2px #888;}
    .overlay_info a {display: block; background: #d95050; background: #d95050 url(http://i1.daumcdn.net/localimg/localimages/07/mapapidoc/arrow_white.png) no-repeat right 14px center; text-decoration: none; color: #fff; padding:12px 36px 12px 14px; font-size: 14px; border-radius: 6px 6px 0 0}
    .overlay_info a strong {background:url(http://i1.daumcdn.net/localimg/localimages/07/mapapidoc/place_icon.png) no-repeat; padding-left: 27px;}
    .overlay_info .desc {padding:14px;position: relative; min-width: 190px; height: 56px}
    .overlay_info img {vertical-align: top;}
    .overlay_info .address {font-size: 12px; color: #333; position: absolute; left: 80px; right: 14px; top: 24px; white-space: normal}
    .overlay_info:after {content:'';position: absolute; margin-left: -11px; left: 50%; bottom: -12px; width: 22px; height: 12px; background:url(http://i1.daumcdn.net/localimg/localimages/07/mapapidoc/vertex_white.png) no-repeat 0 bottom;}
</style>

<script src="http://code.jquery.com/jquery-2.1.1.min.js" type="text/javascript"></script>
<script type="text/javascript">
$(function() {
	// html로 내용을 뿌려준다
	var contents='${vo.contents}';
	contents.slice(0,-1);
	
	$('#ir1').html(contents);
	// 댓글 읽어 온다
	$('#cmt').load("/FoodField/review/comment?num=${vo.num}");
	
	$('#commentBtn').on('click',function(){
		if($('textarea[name="contents"]').val() ==''){
			alert('댓글을 입력하세요');
			return;
		}else{
			var result = confirm('내용을 확인하셨습니까?');
			if(result){
				$.ajax({
					url:'cSubmit',
					type:'post',
					data:$('#commentForm').serialize(),
					dataType:'json',
					success:function(result){
						$('textarea[name="contents"]').val('');
						$('#cmt').load("/FoodField/review/comment?num=${vo.num}");
					},error:function(er){
						alert('에러 : '+er);
					}
				});
			}
		}
	});
	var nick = "${vo.nickname}";
	var myNick = "${sessionScope.userInfo.nickname}";
	if(nick == myNick){
		$('#modifyIcon').css("visibility","visible");
		$('#deleteIcon').css("visibility","visible");
	}
})

function edit(cnum) {
	$('#'+cnum+'originalContents').css("visibility","hidden").css("display","none");
	$('#'+cnum+'newContents').css("visibility","visible").css("display","block");
	$('#'+cnum+'editIcon').css("visibility","hidden").css("display","none");
	$('#'+cnum+'okIcon').css("visibility","visible").css("display","block");
	$('#'+cnum+'delIcon').css("visibility","hidden").css("display","none");
}

function editok(cnum) {
	var result = confirm('댓글을 확인 및 수정하시겠습니까?');
	if(result){
		$.ajax({
			url:'commentModify',
			type:'post',
			data:{num:$('.boardNum').val(),cnum:cnum,contents:$('#'+cnum+'newContents').val()},
			dataType:'json',
			success:function(result){
				$('#cmt').load("/FoodField/review/comment?num=${vo.num}");
			},error:function(er){
				alert('에러 : '+er);
			}
		});
	}
}

function del(cnum) {
	var result = confirm('댓글을 삭제하시겠습니까?');
	if(result){
		$.ajax({
			url:'commentDelete',
			type:'post',
			data:{cnum:cnum},
			dataType:'json',
			success:function(result){
				$('#cmt').load("/FoodField/review/comment?num=${vo.num}");
			},error:function(er){
				alert('에러 : '+er);
			}
		});
	}
}

function reviewModify(num) {
	var result = confirm('글을 수정하시겠습니까?');
	if(result){
		location.href="/FoodField/review/modify?num="+num;
	}
}

function reviewDelete(num) {
	var result = confirm('댓글을 삭제하시겠습니까?');
	if(result){
		$.ajax({
			url:'reviewDelete',
			type:'post',
			data:{num:num},
			dataType:'json',
			success:function(result){
				location.href="/FoodField/review";
			},error:function(er){
				alert('에러 : '+er);
			}
		});
	}
}

</script>
<div class="container">
        <div class="row">
            <div class="col-lg-12">
            	<!-- 입력 박스  -->
					<!--  제목 부분  -->
					<div class="form-group">
						<label for="name" class="col-sm-2 control-label"></label>
						<div class="col-sm-10">
							<h3>
								<label for="name" class="col-sm-9 control-label">${vo.title}</label>
							</h3>
						</div>
					</div>
					
					<div style="margin-top: 5%; margin-bottom: 5%;margin-left: 8%">
						<div class="form-group">
							<div class="col-sm-10"style="text-align: right;">
								<span class="glyphicon glyphicon-eye-open">　${vo.hit }　</span>
								<span class="glyphicon glyphicon-comment">　${vo.cmtnum}　</span>
								<span class="glyphicon glyphicon-thumbs-up">　${vo.recommend}　</span>
							</div>
						</div>
					</div>
					
					<div style="margin-top: 10%; margin-bottom: 10%;"></div>
					<!-- 내용 부분 -->
					
					<div class="form-group">
						<div id="ir1" style="width: 80%; margin-left: 10%">
						</div>
					</div>
					
					<div class="form-group">
						<div style="width: 80%; margin-left: 10%">
						<div id="map" style="width:100%;height:350px;"></div>
						</div>
					</div>
					
					<div class="form-group">
						<div class="col-sm-10 col-sm-offset-2" style="text-align: right; width: 80%; margin-left: 10%">
						<span id="modifyIcon" class="glyphicon glyphicon-edit" style="font-size: 25px; cursor: pointer; visibility: hidden;" onclick="reviewModify(${vo.num})"></span>　　　
						<span id="deleteIcon" class="glyphicon glyphicon-remove" style="font-size: 25px; cursor: pointer; visibility: hidden;" onclick="reviewDelete(${vo.num})"></span>
						</div>
					</div>
					
					<div class="form-group">
						<div class="col-sm-10 col-sm-offset-2" style="text-align: center; margin-top: 3%;margin-bottom: 3%">
							<a class="btn icon-btn btn-primary" href="/FoodField/review">
            				<span class="glyphicon btn-glyphicon glyphicon-thumbs-up img-circle text-muted" style="color: white; font: bold;">
            				</span>　추천</a>
            				<a class="btn icon-btn btn-primary" href="/FoodField/review">
            				<span class="glyphicon btn-glyphicon glyphicon-list img-circle text-muted">
            				</span>　리스트보기</a>
						</div>
					</div>
					
					<div class="col-sm-10 col-sm-offset-2">
						<form id="commentForm" >
							<input type="hidden" value="${vo.num }" name="num" style="display: none;">
							<input type="hidden" value="${sessionScope.userInfo.nickname}" name="nickname" style="display: none;">
	                    	<textarea rows="3" class="form-control" placeholder="댓글을 입력해주세요" name="contents"></textarea>  
                		</form>
	                    <div class="required-icon" style="margin: 3%">
	                        <div style="text-align: right;">
		                        <button type="button" class="btn icon-btn btn-primary" id="commentBtn">
		                        <span class="glyphicon btn-glyphicon glyphicon-list img-circle text-muted">
	            				</span>　입력</button>
							</div>
	                        </div>
	                    </div>
                	</div>
					<div class="form-group">
						<div class="col-sm-10 col-sm-offset-2" style="text-align: center; margin-bottom: 3%" id="cmt">
						</div>
					</div>
            </div>
        </div>

        
<!--  지도표시  -->
<script type="text/javascript" src="//apis.daum.net/maps/maps3.js?apikey=9a675a9591fb0de82c5feea1ff08af2c&libraries=services"></script>
<script>
var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
    mapOption = {
        center: new daum.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
        level: 3 // 지도의 확대 레벨
    };  

// 지도를 생성합니다    
var map = new daum.maps.Map(mapContainer, mapOption); 

// 주소-좌표 변환 객체를 생성합니다
var geocoder = new daum.maps.services.Geocoder();

// 주소로 좌표를 검색합니다
geocoder.addr2coord("${vo.shop_add}", function(status, result) {

    // 정상적으로 검색이 완료됐으면 
     if (status === daum.maps.services.Status.OK) {

        var coords = new daum.maps.LatLng(result.addr[0].lat, result.addr[0].lng);

        // 결과값으로 받은 위치를 마커로 표시합니다
        var marker = new daum.maps.Marker({
            map: map,
            position: coords
        });

        // 인포윈도우로 장소에 대한 설명을 표시합니다
        var infowindow = new daum.maps.InfoWindow({
            content: '<div style="width:150px;text-align:center;padding:6px 0;">${vo.shop_name}</div>'
        });
        infowindow.open(map, marker);

        // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
        map.setCenter(coords);
    } 
});    
</script>
<!--  지도 끝  -->
