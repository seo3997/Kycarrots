/******************************************************************************************************************

	naver map	

******************************************************************************************************************/
/* naver map */
var NaverMap = function(options, map_options)
{
	this.map = null;
	
	this.obj = 
	{
		icon : null
		, marker : []						// 마커 리스트
		, info_window : []					// 정보창 리스트
		, circle : null						// 반경
		, my_marker : null					// 내위치 마커
		, my_info_window : null				// 내위치 정보창 리스트
	};
	
	this.options = 
	{
		apikey : null
		, search_apikey : null
		, id : 'map'
		, img : 'http://static.naver.com/maps2/icons/pin_spot2.png'
		, img_width : 28
		, img_height : 37
		, isMapTypeControl : false
		, isZoomControl : false
		, isViewMyPoint : false
		, myLat : null						// 나의 지점(lat)
		, myLng : null						// 나의 지점(lng)
	};
	
	$.extend(this.options, options);
	
	this.map_options = 
	{
		point : new nhn.api.map.LatLng(36.399934, 127.394400)
		, size : new nhn.api.map.Size(500, 500)
		, zoom : 10
		, enableWheelZoom : true
		, enableDragPan : true
		, enableDblClickZoom : false
		, mapMode : 0
		, activateTrafficMap : false
		, activateBicycleMap : false
		, minMaxLevel : [ 1, 14 ]
		
	};
	
	$.extend(this.map_options, map_options);
	
	this.vars = {
		zoomControl: null
		, mapControl : null
	};
	
	this.init();
};

NaverMap.prototype = 
{
	/*
	 * 초기화 함수 
	 */
	init : function()
	{ 
		var oSize = new nhn.api.map.Size(this.options.img_width, this.options.img_height);
		var oOffset = new nhn.api.map.Size(this.options.img_width / 2, this.options.img_height);
		
		this.obj.icon = new nhn.api.map.Icon(this.options.img, oSize, oOffset);
		this.map = new nhn.api.map.Map(this.options.id ,this.map_options);
		
		// control 객체 추가
		if(this.options.isMapTypeControl)
		{
			var control = new nhn.api.map.MapTypeBtn();
			control.setPosition({top:10, right:10});
			this.map.addControl(control);
			this.vars.mapControl = control;
		}
		
		// control 객체 추가
		if(this.options.isZoomControl)
		{
			var control = new nhn.api.map.ZoomControl();
			control.setPosition({top:0, right:10});
			this.map.addControl(control);
			this.vars.zoomControl = control;
		}
		
		// 내위치를 보이고자 할때
		if(this.options.isViewMyPoint){
			this.setMyMarker(this.options.myLat, this.options.myLat, "내위치");
		}
	}
	/*
	 * 이벤트 설정
	 * - param
	 * target : 타겟(map, marker, infoWindow)
	 * method : 이벤트 종류 (click, mouseenter..)
	 * fun : callback function
	 */
	, initEvent : function(target, method, fun) 
	{
		this.map.attach(method, function(oEvent) 
		{
			var oTarget = oEvent.target;
			if(target == 'map')
			{
				if(oTarget instanceof nhn.api.map.Map) 
				{
					fun.call(this, oEvent);
				}
			}
			if(target == 'marker')
			{
				if(oTarget instanceof nhn.api.map.Marker) 
				{
					fun.call(this, oEvent);
				}
			}
			if(target == 'infoWindow')
			{
				if(oTarget instanceof nhn.api.map.InfoWindow) 
				{
					fun.call(this, oEvent);
				}
			}
		});
	}
	/*
	 * 내위치 마크 설정
	 *  - param
	 * lat : String (위도)
	 * lng : String (경도)
	 * title : String (마커제목)
	 */
	, setMyMarker : function(lat, lng, title)
	{	
		// 기존 내위치 마커 삭제
		if(this.obj.my_marker != null){
			this.map.removeOverlay(this.obj.my_marker);
		}
		
		var marker = new nhn.api.map.Marker(this.obj.icon, 
		{
			point : new nhn.api.map.LatLng(lat, lng)
			, title : title
		});
		
		this.map.addOverlay(marker);
		this.obj.my_marker = marker;
	}
	/*
	 * 내위치 정보창 설정
	 *  - param
	 * lat : String (위도)
	 * lng : String (경도)
	 * content : String (html 내용)
	 * options : css (window_info 스타일)
	 */
	, setMyInfoWindow : function(lat, lng, content, option)
	{
		// 기존 내위치 정보창 삭제
		if(this.obj.my_info_window != null){
			this.map.removeOverlay(this.obj.my_info_window);
		}
		
		var info_content = this.makeInfoWindowForm(content, option);
		var info_window = new nhn.api.map.InfoWindow( 
		{
			point : new nhn.api.map.LatLng(lat, lng)
			, content : info_content
		});
		
		this.obj.my_info_window = info_window;
		// 정보창 보이기(default : false)
		this.obj.my_info_window.setVisible(true);
		
		// infowindow 넓이를 구해온다음에 반만큼 왼쪽으로 움직인다.
		var left = $(info_content).outerWidth() / 2 * -1;
		this.obj.my_info_window.setPosition({top : 40, left : left});
//		this.obj.info_window[length].setOpacity(0.7);
		this.map.addOverlay(this.obj.my_info_window);
	}
	/*
	 * 마크 설정
	 *  - param
	 * lat : String (위도)
	 * lng : String (경도)
	 * title : String (마커제목)
	 * index : int (marker 객체 위치)
	 */
	, setMarker : function(lat, lng, title, index)
	{	
		var length = this.obj.marker.length;
		
		// 위치 지정이 있을경우
		if(index != null)
		{
			// 해당 인덱스에 넣을것이기에
			length = index;
			// 먼저 해당 인덱스 마크를 삭제
			this.delMarker(index);
			// 삭제후 해당 위치에 널객체를 생성
			this.obj.marker.splice(index, 0, null);
		}
		
		var marker = new nhn.api.map.Marker(this.obj.icon, 
		{
			point : new nhn.api.map.LatLng(lat, lng)
			, title : title
		});
		
		this.map.addOverlay(marker);
		this.obj.marker[length] = marker;
	}
	/*
	 * 마크 z-index 설정
	 *  - param
	 * index : int (marker 객체 위치)
	 * zindex : int (z-index)
	 */
	, setZIndex : function(zindex, index)
	{	
		// index 값없을경우 전체보기
		if(index != null){ this.obj.marker[index].setZIndex(zindex); }
		else 
		{
			for(var i in this.obj.marker){ this.obj.marker[i].setZIndex(zindex); }
		}
	}
	/*
	 * 마크 삭제
	 *  - param
	 * num : int (마크 인덱스 번호)
	 */
	, delMarker : function(num)
	{
		// 전체 삭제
		if(num == null)
		{
			for(var i = 0; i < this.obj.marker.length; i++)
			{
				this.map.removeOverlay(this.obj.marker[i]);
			}
			this.obj.marker = [];
		}
		else
		{
			// marker가 존재할 경우만 삭제한다
			if(this.obj.marker[num] != null)
			{
				this.map.removeOverlay(this.obj.marker[num]);
				// 해당 index 마커 삭제
				this.obj.marker.splice(num, 1);
			}
		}
	}
	/*
	 * 정보창 div 만들기
	 *  - param
	 * content : String (내용)
	 * options : Object(option 내용)
	 *  - return
	 *  return_con : html(태그)
	 */
	, makeInfoWindowForm : function(content, options)
	{
		var return_con;
		var $p_div = $('<div />');
		// 넓이, 높이, 내용만 셋팅
		var $c_div = $('<div />', 
		{
			html : content
			, 'class' : 'map_info_window'
		});
		
		// infowindow 가 absolute 이기 때문에 width를 설정해 주어야 한다.
		// infowindow 안에 들어갈 text의 넓이를 구하기 위해서 임시로 body태그에 붙이고 넓이 구한후 삭제
		var $span = $('<span />',
		{
			html : content
			, 'class' : 'temp_span'
			, 'style' : 'display:none'
		});
		
		$span.css('display', 'hidden');
		$('body').prepend($span);
		var width = $span.outerWidth();
		$span.remove();
		
		var d_options = 
		{
			'position' : 'relative'
			, 'background-color' : '#fff'
			, 'border' : '1px solid #eee'
			, 'padding' : '5px'
			, 'width' : width
			, 'text-align' : 'center'
		};
		
		$.extend(d_options, options);
		
		// 나머지 css 속성들
		$c_div.css(d_options);
		$p_div.html($c_div);
		return_con = $p_div.html();
		
		return return_con;
	}
	/*
	 * 정보창 셋팅
	 *  - param
	 * lat : String (위도)
	 * lng : String (경도)
	 * content : String (html 내용)
	 * index : int (window_info 객체 위치)
	 * options : css (window_info 스타일)
	 */
	, setInfoWindow : function(lat, lng, content, index, option)
	{
		var length = this.obj.info_window.length;
		
		// 위치 지정이 있을경우
		if(index != null)
		{
			// 해당 인덱스에 넣을것이기에
			length = index;
			// 먼저 해당 인덱스 정보창을 삭제
			this.delInfoWindow(index);
			// 삭제후 해당 위치에 널객체를 생성
			this.obj.info_window.splice(index, 0, null);
		}
		
		var info_content = this.makeInfoWindowForm(content, option);
		var info_window = new nhn.api.map.InfoWindow( 
		{
			point : new nhn.api.map.LatLng(lat, lng)
			, content : info_content
		});
		
		this.obj.info_window[length] = info_window;
		// 정보창 보이기(default : false)
		this.obj.info_window[length].setVisible(false);
		
		// infowindow 넓이를 구해온다음에 반만큼 왼쪽으로 움직인다.
		var left = $(info_content).outerWidth() / 2 * -1;
		this.obj.info_window[length].setPosition({top : 40, left : left});
//		this.obj.info_window[length].setOpacity(0.7);
		this.map.addOverlay(this.obj.info_window[length]);
	}
	/*
	 * 정보창 보이기
	 *  - param
	 * index : int (window_info 객체 위치)
	 */
	, showInfoWindow : function(index)
	{
		// index 값없을경우 전체보기
		if(index != null){ this.obj.info_window[index].setVisible(true); }
		else 
		{
			for(var i in this.obj.info_window){ this.obj.info_window[i].setVisible(true); }
		}
	}
	/*
	 * 정보창 숨기기
	 *  - param
	 * index : int (window_info 객체 위치)
	 */
	, hideInfoWindow : function(index)
	{
		// index 값없을경우 전체숨기기
		if(index != null){ this.obj.info_window[index].setVisible(false); }
		else 
		{
			for(var i in this.obj.info_window){ this.obj.info_window[i].setVisible(false); }
		}
	}
	/*
	 * 정보창 삭제
	 *  - param
	 * num : int (마크 인덱스 번호)
	 */
	, delInfoWindow : function(num)
	{	
		// 전체 삭제
		if(num == null)
		{
			for(var i = 0; i < this.obj.info_window.length; i++)
			{
				this.map.removeOverlay(this.obj.info_window[i]);
			}
			this.obj.info_window = [];
		}
		else
		{
			// info_window 존재할 경우만 삭제한다
			if(this.obj.info_window[num] != null)
			{
				this.map.removeOverlay(this.obj.info_window[num]);
				// 해당 index info_window 삭제
				this.obj.info_window.splice(num, 1);
			}
		}
	}
	// 주소검색
	, addrSearch : function(addr, fun)
	{	
//		var param = {'key' : this.options.apikey, 'query' : addr, 'encoding' : 'utf-8', 'coord' : 'latlng'};
//		// cross domain 가능할시
//		$.ajax(
//		{
//			url : 'http://openapi.map.naver.com/api/geocode.php'
//			, dataType : 'xml'
//			, data : param
//			, type : 'post'
//			, success : function(response)
//			{
//				var s = $.parseXML(response.responseText);
//				var json = $.xml2json(s);
//				var item = null;
//				
//				if(json.item != null)
//				{
//					item = (json.item.length == null) ? Array(json.item) : json.item;
//				}
//				
//				fun.call(this, item);
//			}
//			, error : function(response)
//			{
//				fun.call(this, null);
//			}
//		});
		
		var _this = this;
		// cross domain 불가능할시(egovframework/framework/common/web/FCommonController 참조)
		var param = {'url' : 'http://openapi.map.naver.com/api/geocode?key=' + _this.options.apikey + '&query=' + encodeURI(addr) + '&encoding=utf-8&coord=latlng'};
		$.ajax(
		{
			url : '/fcommon/getDataCrossDomainXmlAjax.do'
			, dataType : 'json'
			, data : param
			, type : 'post'
			, success : function(response)
			{
				var json = response.resultStats.resultList;
				var items = null;
				
				// 검색결과 있을경우
				if(typeof json.error == 'undefined'){
					if(json[0].items.length > 0)
					{
						items = json[0].items
						
						// tm128 좌표를 구한다.
						for(var i = 0; i < items.length; i++)
						{
							var point = new nhn.api.map.LatLng(items[i].point.y, items[i].point.x);
							var tm_point = point.toTM128();
							var p = { 'x' : tm_point.x, 'y' : tm_point.y };
							items[i].tm = p;
							items[i].latlng = items[i].point;
						}
					}
				}
				
				fun.call(this, items);
			}
			, error : function(response)
			{
				fun.call(this, null);
			}
		});
	}
	/*
	 * 맵 사이즈 조절
	 */
	, resizeMap : function(width, height){
		this.map.setSize(new nhn.api.map.Size(width, height));
	}
	/*
	 * 좌표값으로 지도 이동
	 * - param
	 * lat : float 위도
	 * lng : float 경도
	 * animate : boolean 자연스럽게(미구현)
	 */
	, movePointMap : function(lat, lng, animate)
	{
		if(animate == null || !animate) { this.map.setCenter(new nhn.api.map.LatLng(lat, lng)); }
		else { this.map.setCenter(new nhn.api.map.LatLng(lat, lng)); }
	}
	/*
	 * 지도 축척 변경
	 * - param
	 * level : number, string (숫자일 경우 해당 축척으로 -, + 붙을경우 더하거나 빼기)
	 */
	, setLevelMap : function(level)
	{
		if(typeof level == 'string')
		{
			level = eval(this.map.getLevel() + level);
		}
		this.map.setLevel(level);
	}
	/*
	 * 지도 zoom 컨트롤 추가,삭제
	 * - param
	 * flag : boolean 값(true : 추가, false : 삭제)
	 * position : {top:10, right:10} 형식
	 */
	, setZoomControll : function(flag, position){
		var control = new nhn.api.map.ZoomControl();
		// zoom 컨트롤 추가
		if(flag){
			control.setPosition(position);
			this.map.addControl(control);
			this.vars.zoomControl = control;
		} else {
			this.map.removeControl(this.vars.zoomControl);
		}
	}
	/*
	 * 맵 getter
	 */
	, getMap : function()
	{
		return this.map;
	}
	/*
	 * 마커 getter
	 * - return
	 * marker : marker 객체
	 */
	, getMarker : function()
	{
		return this.obj.marker;
	}
	/*
	 * 마커 getter
	 * - param
	 * marker : marker 객체
	 * - return
	 * index : marker index 번호
	 */
	, getMarkerIndex : function(marker)
	{
		if(marker != null)
		{
			for(var i in this.obj.marker)
			{ 
				if(this.obj.marker[i] == marker)
				{
					return i;
				}
			}
		}
		
		return -1;
	}
	/*
	 * 마커 getter
	 * - param
	 * index : marker index 번호
	 * - return
	 * marker : marker 객체
	 */
	, getMarkerObject : function(index)
	{
		if(index != null)
		{
			return this.obj.marker[index];
		}
		
		return null;
	}
	/*
	 * 정보창 getter
	 * - return
	 * infoWindow : infoWindow 객체
	 */
	, getInfoWindow : function()
	{
		return this.obj.info_window;
	}
	/*
	 * 정보창 getter
	 * - param
	 * infoWindow : infoWindow 객체
	 * - return
	 * index : infoWindow index 번호
	 */
	, getInfoWindowIndex : function(infoWindow)
	{
		if(infoWindow != null)
		{
			for(var i in this.obj.info_window)
			{ 
				if(this.obj.info_window[i] == infoWindow)
				{
					return i;
				}
			}
		}
		
		return -1;
	}
	/*
	 * 정보창 getter
	 * - param
	 * index : infoWindow index 번호
	 * - return
	 * infoWindow : infoWindow 객체
	 */
	, getInfoWindowObject : function(index)
	{
		if(index != null)
		{
			return this.obj.info_window[index];
		}
		
		return null;
	}
};