<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <script src="/rMate/LicenseKey/rMateChartH5License.js"></script>
  <script src="/rMate/rMateChartH5/JS/rMateChartH5.js"></script>
  <link rel="stylesheet" href="/rMate/rMateChartH5/Assets/Css/rMateChartH5.css"/>
  <script type="text/javascript">
    rMateChartH5.create("chart1", "chartHolder", "", "100%", "100%");
    var layoutStr =
      '<rMateChart backgroundColor="#FFFFFF" borderStyle="none">'
        +'<Options>'
          +'<Caption text="My First Chart" fontSize="20"/>'
          +'<Legend/>'
        +'</Options>'
        +'<Column2DChart showDataTips="true">'
          +'<horizontalAxis>'
            +'<CategoryAxis categoryField="Month"/>'
          +'</horizontalAxis>'
          +'<verticalAxis>'
            +'<LinearAxis maximum="10"/>'
          +'</verticalAxis>'
          +'<series>'
            +'<Column2DSeries labelPosition="outside" yField="2011" displayName="2011"/>'
          +'</series>'
        +'</Column2DChart>'
      +'</rMateChart>';
    var chartData =
      [{"Month":"Jan", "2011":2.4},
      {"Month":"Feb", "2011":3.8},
      {"Month":"Mar", "2011":8.1},
      {"Month":"Apr", "2011":5.1},
      {"Month":"May", "2011":2.1},
      {"Month":"Jun", "2011":5.2},
      {"Month":"Jul", "2011":4.2}];
    rMateChartH5.calls("chart1", {
      "setLayout" : layoutStr,
      "setData" : chartData
    });
  </script>
</head>
<body>
  <div id="chartHolder" style="width:600px; height:400px;"></div>
</body>
</html>