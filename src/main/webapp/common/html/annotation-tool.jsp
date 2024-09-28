<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>


<head>
	<meta charset="utf-8" />
	<meta name="viewport" content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0, width=device-width" />
	<meta http-equiv="Cache-Control" content="no-cache" />
	<!-- <meta http-equiv="Expires" content="0"/> -->
	<meta http-equiv="Expires" content="Mon, 06 Jan 1990 00:00:01 GMT" /> 
	<meta http-equiv="Expires" content="-1" />

	<meta http-equiv="Pragma" content="no-cache" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<meta name="robots" content="NONE" />
<!--  
<link rel="shortcut icon" href="/common/images/common/favicon_16.png">
<link rel="shortcut icon" href="/common/images/common/favicon_32.png">
-->
	<title>Crowd Oh! - Annotation Tool</title>
	
	<script type="text/javascript" src="/common/js/jquery/jquery-1.11.1.min.js"></script>
	<script type="text/javascript" src="/common/js/jquery-ui/jquery-ui-1.10.3.custom.min.js"></script>

	<!-- icheck -->
	<script type="text/javascript" src="/common/js/common.js?version=6.5"></script>
	<link rel="stylesheet" href="/common/css/common.css?version=6.4" />

<script>
$(function(){
	$('.figure').click(function(){
		//alert('클릭했어!');
		$('#annotation-tool > .content-section > .left-section > .figure').removeClass('on');
		$(this).addClass('on');
		console.log($(this).find('img').attr('src'));
		$('.work-section > img').attr('src',$(this).find('img').attr('src'));
	});
	$('.zoomBtn').click(function(){
		$('.zoomOption').show();
	});
	$('.zoomOption').mouseleave(function(){
		$(this).hide();
	});
});
</script>

<style>
body,html{margin: 0; padding: 0; background-color: #000;}
#annotation-tool{width: 1280px; height: 800px; background-color: #000; color: #fff;}
#annotation-tool .head-title{margin-bottom: 10px; padding: 12px 10px; border: 1px solid #fff; background-color: #3e5c59;}
#annotation-tool > .top-section{margin-bottom: 10px; padding: 10px 0; background-color: #2b579a; color: #fff; text-align:right;}
#annotation-tool > .top-section > .dataset-name{padding-right: 10px;}
#annotation-tool > .content-section{display: flex; flex-direction: row; flex-wrap: wrap; justify-content: spacer-between; justify-around: space-around;}
#annotation-tool > .content-section > div{}

#annotation-tool > .content-section > .left-section{ width: 120px; margin-right: 10px; text-align: center;  overflow: hidden; overflow-y:auto;height: 754px; background-color: #333;}
#annotation-tool > .content-section > .left-section > .figure{display:block; width: 100px; margin: 1rem auto 0; cursor:pointer;}
#annotation-tool > .content-section > .left-section > .figure > .thum-img{display: block; width: 90px; height: 90px; text-align: center; overflow: hidden; background-color: rgba(255,255,255,0.5);}
#annotation-tool > .content-section > .left-section > .figure img{max-width:100%; width: 100%; padding: 1px;}
#annotation-tool > .content-section > .left-section > .figure .figure-caption{padding-top: 2px; color: #fff;word-break: keep-all; word-wrap: break-word; white-space: normal;}
#annotation-tool > .content-section > .left-section > .figure:hover > .thum-img,
#annotation-tool > .content-section > .left-section > .figure.on > .thum-img{background-color: rgba(255,255,255,1)}
#annotation-tool > .content-section > .left-section > .figure > .thum-img > img{margin:0; padding: 0; border: 0 none;}

#annotation-tool > .content-section > .center-section{ width: calc(100% - 445px);background-color: #333;}
#annotation-tool > .content-section > .center-section > .head-title{min-height: 40px; padding: 0px;}
#annotation-tool > .content-section > .center-section > .head-title > .icon-box{position: relative; display: inline-block; margin: 5px 0 0 10px;}
#annotation-tool > .content-section > .center-section > .head-title > .icon-box > .zoomBtn{display: block; width: 24px; height: 24px; padding: 2px; background: #333 url(/common/img/ico_ATool.53a748eb.png) no-repeat 5px 6px; text-indent: -999999999em; border: 0 none; color: transparent; cursor: pointer;}
#annotation-tool > .content-section > .center-section > .head-title > .icon-box > .zoomOption{display:none;position: absolute; top: 28px; left: 0; list-style: none; margin: 0; padding: 0; background-color: #333;}
#annotation-tool > .content-section > .center-section > .head-title > .icon-box > .zoomOption > li > em{display:block; width: 28px;height: 35px; text-indent: -999999999em; color: transparent; background-repeat: no-repeat; background-image: url(/common/img/ico_ATool.53a748eb.png); cursor: pointer;}
#annotation-tool > .content-section > .center-section > .head-title > .icon-box > .zoomOption > li > em:hover{background-color: #999;}
#annotation-tool > .content-section > .center-section > .head-title > .icon-box > .zoomOption > li:nth-of-type(1) > em{background-position: -24px 10px;}
#annotation-tool > .content-section > .center-section > .head-title > .icon-box > .zoomOption > li:nth-of-type(2) > em{background-position: -54px 10px;}
#annotation-tool > .content-section > .center-section > .head-title > .icon-box > .zoomOption > li:nth-of-type(3) > em{background-position: -84px 10px;}
#annotation-tool > .content-section > .center-section > .head-title >.file-name{float:right; padding-right: 10px; line-height: 40px;}
#annotation-tool > .content-section > .center-section > .work-section{min-width: 818px; margin-top: 10px;text-align:center;}
#annotation-tool > .content-section > .center-section > .work-section > img{max-width: 100%;}

#annotation-tool > .content-section > .right-section{ width: 300px; margin-left: 10px; background-color: #333;}
#annotation-tool > .content-section > .right-section > .labelInfo > pre{display: block; color: #fff; width: 96%; height: 450px; padding: 0 5px; word-break: normal; white-space: break-spaces; font-size: 12px; overflow-y:auto;}
#annotation-tool > .content-section > .right-section > .metadata-list{list-style: none; width: 100%; margin: 0; padding: 0 5px;}
#annotation-tool > .content-section > .right-section > .metadata-list:after{display:block; content: ""; clear: both; }
#annotation-tool > .content-section > .right-section > .metadata-list > li{float: left; width: 50%; margin-bottom: 2px; overflow: hidden;}
#annotation-tool > .content-section > .right-section > .metadata-list > li > label{float: left; width: 52px; padding: 4px 0; font-size: 12px;}
#annotation-tool > .content-section > .right-section > .metadata-list > li > select{float: left; width: 90px; font-size: 12px; vertical-align: middle;}
#annotation-tool > .content-section > .right-section > .metadata-list > li > select.disabled{background-color: #999;}
</style>

</head>

<body>

<div id="annotation-tool" class="" style="">
	<div class="top-section">
		<span class="dataset-name"><strong>데이터넷 명 : </strong>시간을 담다!</span>
	</div>
	<div class="content-section">

		<div class="left-section">

			<figure class="figure">
			  <div class="thum-img"><img src="http://img.g2b.go.kr:7070/Resource/CataAttach/XezCatalog/XZMOK/move_image/2012%2F02%2F29%2F20120229163954-63.jpg" class="figure-img img-fluid rounded" alt="..."></div>
			  <figcaption class="figure-caption">동서울떡 간판 이미지</figcaption>
			</figure>
			
			<figure class="figure">
			  <div class="thum-img"><img src="https://mblogthumb-phinf.pstatic.net/MjAyMDAzMTlfMjY4/MDAxNTg0NjA5NjE4MTQ4.nNxQLahx5tFNnZPZ1IvimehNJb75PDjAiI4M6oRPO-Eg.AWIBXyJQHBtb47Y0y0yKK-g78JlA0tUJ7OyMLurpUgwg.JPEG.menfourteen/%EB%8F%84%EA%B3%A1%EB%8F%99%EA%B0%84%ED%8C%90_(1).JPG?type=w800" class="figure-img img-fluid rounded" alt="..."></div>
			  <figcaption class="figure-caption">어썸네일</figcaption>
			</figure>
<!-- 			
			<figure class="figure">
			  <div class="thum-img"><img src="https://mblogthumb-phinf.pstatic.net/MjAyMDAzMTlfMjY4/MDAxNTg0NjA5NjE4MTQ4.nNxQLahx5tFNnZPZ1IvimehNJb75PDjAiI4M6oRPO-Eg.AWIBXyJQHBtb47Y0y0yKK-g78JlA0tUJ7OyMLurpUgwg.JPEG.menfourteen/%EB%8F%84%EA%B3%A1%EB%8F%99%EA%B0%84%ED%8C%90_(1).JPG?type=w800" class="figure-img img-fluid rounded" alt="..."></div>
			  <figcaption class="figure-caption">어썸네일</figcaption>
			</figure>
			
			<figure class="figure">
			  <div class="thum-img"><img src="https://mblogthumb-phinf.pstatic.net/MjAyMDAzMTlfMjY4/MDAxNTg0NjA5NjE4MTQ4.nNxQLahx5tFNnZPZ1IvimehNJb75PDjAiI4M6oRPO-Eg.AWIBXyJQHBtb47Y0y0yKK-g78JlA0tUJ7OyMLurpUgwg.JPEG.menfourteen/%EB%8F%84%EA%B3%A1%EB%8F%99%EA%B0%84%ED%8C%90_(1).JPG?type=w800" class="figure-img img-fluid rounded" alt="..."></div>
			  <figcaption class="figure-caption">어썸네일</figcaption>
			</figure>
			
			<figure class="figure">
			  <div class="thum-img"><img src="https://mblogthumb-phinf.pstatic.net/MjAyMDAzMTlfMjY4/MDAxNTg0NjA5NjE4MTQ4.nNxQLahx5tFNnZPZ1IvimehNJb75PDjAiI4M6oRPO-Eg.AWIBXyJQHBtb47Y0y0yKK-g78JlA0tUJ7OyMLurpUgwg.JPEG.menfourteen/%EB%8F%84%EA%B3%A1%EB%8F%99%EA%B0%84%ED%8C%90_(1).JPG?type=w800" class="figure-img img-fluid rounded" alt="..."></div>
			  <figcaption class="figure-caption">어썸네일</figcaption>
			</figure>
			
			<figure class="figure">
			  <div class="thum-img"><img src="https://mblogthumb-phinf.pstatic.net/MjAyMDAzMTlfMjY4/MDAxNTg0NjA5NjE4MTQ4.nNxQLahx5tFNnZPZ1IvimehNJb75PDjAiI4M6oRPO-Eg.AWIBXyJQHBtb47Y0y0yKK-g78JlA0tUJ7OyMLurpUgwg.JPEG.menfourteen/%EB%8F%84%EA%B3%A1%EB%8F%99%EA%B0%84%ED%8C%90_(1).JPG?type=w800" class="figure-img img-fluid rounded" alt="..."></div>
			  <figcaption class="figure-caption">어썸네일</figcaption>
			</figure>
			
			<figure class="figure">
			  <div class="thum-img"><img src="https://mblogthumb-phinf.pstatic.net/MjAyMDAzMTlfMjY4/MDAxNTg0NjA5NjE4MTQ4.nNxQLahx5tFNnZPZ1IvimehNJb75PDjAiI4M6oRPO-Eg.AWIBXyJQHBtb47Y0y0yKK-g78JlA0tUJ7OyMLurpUgwg.JPEG.menfourteen/%EB%8F%84%EA%B3%A1%EB%8F%99%EA%B0%84%ED%8C%90_(1).JPG?type=w800" class="figure-img img-fluid rounded" alt="..."></div>
			  <figcaption class="figure-caption">어썸네일</figcaption>
			</figure>
			
			<figure class="figure">
			  <div class="thum-img"><img src="https://mblogthumb-phinf.pstatic.net/MjAyMDAzMTlfMjY4/MDAxNTg0NjA5NjE4MTQ4.nNxQLahx5tFNnZPZ1IvimehNJb75PDjAiI4M6oRPO-Eg.AWIBXyJQHBtb47Y0y0yKK-g78JlA0tUJ7OyMLurpUgwg.JPEG.menfourteen/%EB%8F%84%EA%B3%A1%EB%8F%99%EA%B0%84%ED%8C%90_(1).JPG?type=w800" class="figure-img img-fluid rounded" alt="..."></div>
			  <figcaption class="figure-caption">어썸네일</figcaption>
			</figure>
			
			<figure class="figure">
			  <div class="thum-img"><img src="https://mblogthumb-phinf.pstatic.net/MjAyMDAzMTlfMjY4/MDAxNTg0NjA5NjE4MTQ4.nNxQLahx5tFNnZPZ1IvimehNJb75PDjAiI4M6oRPO-Eg.AWIBXyJQHBtb47Y0y0yKK-g78JlA0tUJ7OyMLurpUgwg.JPEG.menfourteen/%EB%8F%84%EA%B3%A1%EB%8F%99%EA%B0%84%ED%8C%90_(1).JPG?type=w800" class="figure-img img-fluid rounded" alt="..."></div>
			  <figcaption class="figure-caption">어썸네일</figcaption>
			</figure>
			
			<figure class="figure">
			  <div class="thum-img"><img src="https://mblogthumb-phinf.pstatic.net/MjAyMDAzMTlfMjY4/MDAxNTg0NjA5NjE4MTQ4.nNxQLahx5tFNnZPZ1IvimehNJb75PDjAiI4M6oRPO-Eg.AWIBXyJQHBtb47Y0y0yKK-g78JlA0tUJ7OyMLurpUgwg.JPEG.menfourteen/%EB%8F%84%EA%B3%A1%EB%8F%99%EA%B0%84%ED%8C%90_(1).JPG?type=w800" class="figure-img img-fluid rounded" alt="..."></div>
			  <figcaption class="figure-caption">어썸네일</figcaption>
			</figure>
 -->


		</div>




		<div class="center-section">
			
			<div class="head-title">
				<div class="icon-box">
					<span class="zoomBtn">확대</span>
					<ul class="zoomOption">
						<li><em onclick="">확대</em></li>
						<li><em onclick="">축소</em></li>
						<li><em onclick="">원래대로</em></li>
					</ul>
				</div>
				<span class="file-name"><strong>파일명 : </strong>동서울떡 간판 이미지 .jpg</span>
			</div>
			<div class="work-section"><!-- 작업용 이미지 공간 // -->
			
				<img src="http://간판집.com/images/gp_2dc527f2772e4328976e5e4c92a9f2d1.jpg" />

			</div><!-- // 작업용 이미지 공간 -->
		</div>




		<div class="right-section">
			<div class="head-title">라벨정보</div>
			<div class="labelInfo">
				<pre>
images: [
    {
        "id": 1,
        "width": 2737,
        "height": 1346,
        "file_name": "간판1.jpg",
        "date_captured": "2020-11-27 10:44:14"
    }
]
annotations: [
    {
        "id": 1,
        "image_id": 1,
        "text": "시간을담다",
        "bbox": [
            1880,
            588,
            588,
            399
        ]
    }
]
images: [
    {
        "id": 1,
        "width": 2737,
        "height": 1346,
        "file_name": "간판1.jpg",
        "date_captured": "2020-11-27 10:44:14"
    }
]
annotations: [
    {
        "id": 1,
        "image_id": 1,
        "text": "시간을담다",
        "bbox": [
            1880,
            588,
            588,
            399
        ]
    }
]
				</pre>
			</div>
			<div class="head-title">메타데이터</div>
				<ul class="metadata-list">
					<li>
						<label for="">분류</label>
						<select disabled="disabled">
							<option>실외간판</option>
						</select>
					</li>
					<li>
						<label for="">세분류</label>
						<select disabled="disabled">
							<option>지주이용간판</option>
						</select>
					</li>
					<li>
						<label for="">지역</label>
						<select disabled="disabled">
							<option>수도권</option>
						</select>
					</li>
					<li>
						<label for="">수집장치</label>
						<select disabled="disabled">
							<option>스마트폰</option>
						</select>
					</li>
					<li>
						<label for="">날씨</label>
						<select disabled="disabled">
							<option>맑음</option>
						</select>
					</li>
					<li>
						<label for="">조도</label>
						<select disabled="disabled">
							<option>어두움</option>
						</select>
					</li>
					<li>
						<label for="">광원</label>
						<select>
							<option>자체발광</option>
						</select>
					</li>
					<li>
						<label for="">외곽선</label>
						<select>
							<option>상</option>
						</select>
					</li>
					<li>
						<label for="">글씨방향</label>
						<select>
							<option>가로</option>
						</select>
					</li>
					<li>
						<label for="">글씨크기</label>
						<select>
							<option>대</option>
						</select>
					</li>
					<li>
						<label for="">글씨폰트</label>
						<select>
							<option>캘리그라피</option>
						</select>
					</li>
					<li>
						<label for="">글자색</label>
						<select>
							<option>여러색</option>
						</select>
					</li>
					<li>
						<label for="">글자연결</label>
						<select>
							<option>빈공간없이 이어짐</option>
						</select>
					</li>
				</ul>
		</div>	
	</div>
</div>

</body>
</html>