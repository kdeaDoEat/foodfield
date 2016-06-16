<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script src="http://code.jquery.com/jquery-2.1.1.min.js" type="text/javascript"></script>
<script type="text/javascript">
$(function() {
	$(".mainImg").children().css("width","100%").css("height","100%").attr('class','img-responsive img-rounded to-animate').css("height","220px");
})
</script>
<div id="fh5co-main">
		<!-- Features -->
		<div class="fh5co-spacer fh5co-spacer-lg"></div>		
		<!-- Products -->
		<div class="container" id="fh5co-products">
			<div class="row text-left">
				<div class="col-md-8">
					<h2 class="fh5co-section-lead">최고 평가</h2>
					<h3 class="fh5co-section-sub-lead"></h3>
				</div>
				<div class="fh5co-spacer fh5co-spacer-md"></div>
			</div>
			<div class="row">
					<c:forEach var="list" items="${highCommend}">
						<c:choose>
							<c:when test="${list.photo eq null }">
							<div class="col-md-3 col-sm-6 col-xs-6 col-xxs-12 fh5co-mb30">
								<div class="fh5co-product">
									<div style="margin: 10px;"><img src="/FoodField/resources/bootstrap/images/no_img.gif" alt="FREEHTML5.co Free HTML5 Template Bootstrap" class="img-responsive img-rounded to-animate" style="width: 100%;height: 100%">
									</div><h4>${list.title }</h4>
									<p>${list.contents }</p>
									<p><a href="/FoodField/review/read?num=${list.num }">Read more</a></p>
								</div>
							</div>
							</c:when>
							<c:otherwise>
							<div class="col-md-3 col-sm-6 col-xs-6 col-xxs-12 fh5co-mb30">
								<div class="fh5co-product">
									<div style="margin: 10px;" class="mainImg">${list.photo }
									</div><h4>${list.title }</h4>
									<p>${list.contents }</p>
									<p><a href="/FoodField/review/read?num=${list.num }">Read more</a></p>
								</div>
							</div>
							</c:otherwise>
						</c:choose>
					</c:forEach>
			</div>
		</div>



		<!-- Features -->

		<div id="fh5co-features">
			<div class="container">
				<div class="row text-center">
					<div class="col-md-8 col-md-offset-2">
						<h2 class="fh5co-section-lead">Point!</h2>
						<h3 class="fh5co-section-sub-lead"></h3>
					</div>
					<div class="fh5co-spacer fh5co-spacer-md"></div>
				</div>
				<div class="row">
					<div class="col-md-6 col-sm-6 fh5co-feature-border">
						<div class="fh5co-feature">
							<div class="fh5co-feature-icon to-animate">
								<i class="glyphicon glyphicon-map-marker"></i>
							</div>
							<div class="fh5co-feature-text">
								<h3>내 주위 맛집!</h3>
								<p>자신의 위치기반으로 주위 맛집을 찾아준다</p>
								<p><a href="/FoodField/doit">Read more</a></p>
							</div>
						</div>
						<div class="fh5co-feature no-border">
							<div class="fh5co-feature-icon to-animate">
								<i class="glyphicon glyphicon-plus"></i>
							</div>
							<div class="fh5co-feature-text">
								<h3>즐겨찾기!</h3>
								<p>즐겨찾기설정으로 좋아하는 음식리뷰를 추가하고 모아본다</p>
								<p><a href="#">Read more</a></p>
							</div>
						</div>
					</div>
					<div class="col-md-6 col-sm-6">
						<div class="fh5co-feature">
							<div class="fh5co-feature-icon to-animate">
								<i class="glyphicon glyphicon-cutlery"></i>
							</div>
							<div class="fh5co-feature-text">
								<h3>나만의 후기!</h3>
								<p>나의 방문기를 여러사람들과 공유 추천을 받으며 포인트를 모은다</p>
								<p><a href="/FoodField/review">Read more</a></p>
							</div>
						</div>
						<div class="fh5co-feature no-border">
							<div class="fh5co-feature-icon to-animate">
								<i class="icon-clock2"></i>
							</div>
							<div class="fh5co-feature-text">
								<h3>24/7 Support</h3>
								<p>Far far away, behind the word mountains, far from the countries Vokalia.</p>
								<p><a href="#">Read more</a></p>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<!-- Products -->
		<div class="fh5co-spacer fh5co-spacer-lg"></div>

	</div>