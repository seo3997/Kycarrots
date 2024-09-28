<%@page contentType="text/html;charset=UTF-8"%>
<%
	String slatitude 	=request.getParameter("latitude");
	String slongitude 	=request.getParameter("longitude");
	String sptext 		=request.getParameter("ptext");

	
	
    if (slatitude==null || "null".equals(slatitude)|| "".equals(slatitude)){
    	slatitude="";
    }
    if (slongitude==null || "null".equals(slongitude)|| "".equals(slongitude)){
    	slongitude="";
    }

    if (sptext==null || "null".equals(sptext)|| "".equals(sptext)){
    	sptext="";
    }

    
    
    
    
%>

<!DOCTYPE html>
<html>
  <head>
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no">
    <meta charset="utf-8">
    <title>Geocoding service</title>
    <style>
      html, body, #map-canvas {
        height: 100%;
        margin: 0px;
        padding: 0px
      }
      #panel {
        position: absolute;
        top: 5px;
        left: 50%;
        margin-left: -180px;
        z-index: 5;
        background-color: #fff;
        padding: 5px;
        border: 1px solid #999;
      }
    </style>
    <script src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false"></script>
    <script>
		var geocoder;
		var map;
		function initialize() {
		  geocoder = new google.maps.Geocoder();
		  //var latlng = new google.maps.LatLng(-34.397, 150.644);
		  var latlng = new google.maps.LatLng("<%=slatitude%>","<%=slongitude%>");
		  var mapOptions = {
		    zoom: 18,
		    center: latlng
		  }
		  map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions);
		  var marker = new google.maps.Marker({
			    position: latlng,
			    title:"Hello World!"
		  });
		  marker.setMap(map);
		}

		function codeAddress() {
		  var address = document.getElementById('address').value;
		  geocoder.geocode( { 'address': address}, function(results, status) {
		    if (status == google.maps.GeocoderStatus.OK) {
		      document.getElementById('edLat').value=results[0].geometry.location.lat();
		      document.getElementById('edLan').value=results[0].geometry.location.lng();
		      map.setCenter(results[0].geometry.location);
		      var marker = new google.maps.Marker({
		          map: map,
		          position: results[0].geometry.location
		      });
		    } else {
		      alert('Geocode was not successful for the following reason: ' + status);
		    }
		  });
		}

		google.maps.event.addDomListener(window, 'load', initialize);

		
		<%
			if(!"".equals(sptext))
			{
		%>
		function applyLocation() {
		  var edLat = document.getElementById('edLat').value;
		  var edLan = document.getElementById('edLan').value;
		  opener.form.<%=sptext%>.value=edLat+" "+edLan;
		  opener.form.<%=sptext%>_Lat.value=edLat;
		  opener.form.<%=sptext%>_Lan.value=edLan;
		  self.close();
		}
		<%
			}
		%>
		
    </script>
  </head>
  <body>
    <div id="panel">
      <input id="address" type="textbox">
      <input type="button" value="위치찾기" onclick="codeAddress()">
      
      <% 
      	if (!sptext.equals("")){ 
      %>
      <input type="button" value="적용하기" onclick="applyLocation();">
      <%	
      	} 
      %>
    </div>
    <div id="map-canvas"></div>
      <input id="edLat" type="hidden" value="<%=slatitude%>">
      <input id="edLan" type="hidden" value="<%=slongitude%>">
    
    
  </body>
</html>