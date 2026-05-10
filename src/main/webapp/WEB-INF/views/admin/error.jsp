<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%
    Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
    if (statusCode == null) statusCode = 0;
    String requestUri = (String) request.getAttribute("javax.servlet.error.request_uri");
    if (requestUri == null || requestUri.isEmpty()) requestUri = request.getRequestURI();
    String rawMsg = (String) request.getAttribute("javax.servlet.error.message");
    if (rawMsg == null) rawMsg = "";

    String title = "오류가 발생했습니다";
    String desc = "알 수 없는 오류가 발생했습니다. 잠시 후 다시 시도해 주세요.";
    String icon = "fa-exclamation-triangle";
    int redirectSec = 3;                 // 자동 이동까지 대기초
    String redirectUrl = "/admin/login.do";  // 로그인으로 유도

    switch (statusCode) {
        case 401:
            title = "로그인이 필요합니다";
            desc  = "세션이 만료되었거나 로그인하지 않았습니다.";
            icon  = "fa-lock";
            redirectSec = 2;
            break;
        case 403:
            title = "접근 권한이 없습니다";
            desc  = "요청하신 작업을 수행할 권한이 없습니다.";
            icon  = "fa-ban";
            redirectSec = 2;
            break;
        case 404:
            title = "페이지를 찾을 수 없습니다";
            desc  = "요청하신 주소가 잘못되었거나 이동되었습니다.";
            icon  = "fa-search";
            redirectSec = 5;
            break;
        case 500:
            title = "서버 오류가 발생했습니다";
            desc  = "잠시 후 다시 시도해 주세요.";
            icon  = "fa-bolt";
            break;
        default:
            // keep defaults
            break;
    }
    if (!rawMsg.isEmpty()) {
        desc += " (" + rawMsg + ")";
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0, width=device-width" />
    <meta http-equiv="Cache-Control" content="no-cache" />
    <meta http-equiv="Expires" content="Mon, 06 Jan 1990 00:00:01 GMT"/>
    <meta http-equiv="Expires" content="-1">
    <meta http-equiv="Pragma" content="no-cache"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="robots" content="NONE" />
    <title>asagong | 에러</title>

    <script type="text/javascript" src="/common/js/jquery/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="/common/js/jquery-ui/jquery-ui-1.10.3.custom.min.js"></script>

    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css" />
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js"></script>

    <link href="/common/css/bootstrap/bootstrap-datetimepicker.min.css" rel="stylesheet" type="text/css" media="screen" />
    <script type="text/javascript" src="/common/js/bootstrap/moment-with-locales.min.js"></script>
    <script type="text/javascript" src="/common/js/bootstrap/bootstrap-datetimepicker.min.js"></script>

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css"  />
    <link rel="stylesheet" href="/common/css/AdminLTE/ionicons.min.css" />
    <link rel="stylesheet" href="/common/css/AdminLTE/AdminLTE.css" />
    <link rel="stylesheet" href="/common/css/AdminLTE/skin-green-light.css" />
    <script type="text/javascript" src="/common/js/AdminLTE/app.js"></script>

    <link rel="stylesheet" href="/common/js/icheck/skins/flat/blue.css" />
    <script type="text/javascript" src="/common/js/icheck/icheck.min.js"></script>

    <script type="text/javascript" src="/common/js/common.js?version=7.01"></script>
    <link rel="stylesheet" href="/common/css/common.css?version=6.4" />
    <link rel="stylesheet" href="/common/css/dashboard.css" />

    <style>
        body, html { height: 100%; }
        .error-wrap {
            min-height: 100%;
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 24px;
        }
        .error-card {
            max-width: 640px;
            width: 100%;
            border-radius: 12px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.08);
        }
        .error-icon {
            opacity: .85;
        }
        .small-muted {
            font-size: 12px; color: #8692A6;
        }
    </style>
</head>
<body class="hold-transition skin-green-light">
<div class="wrapper">
    <div class="content-wrapper error-wrap">
        <div class="card error-card">
            <div class="card-body text-center">
                <div class="mb-3">
                    <i class="fa <%= icon %> fa-3x error-icon"></i>
                </div>
                <h3 class="mb-2"><%= title %></h3>
                <p class="small-muted mb-3">
                    HTTP <%= statusCode %> · <%= requestUri %>
                </p>
                <p class="mb-4"><%= desc %></p>

                <div class="d-flex justify-content-center">
                    <a href="<%= redirectUrl %>" class="btn btn-primary mr-2">
                        <i class="fa fa-sign-in-alt"></i> 로그인으로 이동
                    </a>
                    <button class="btn btn-light" onclick="history.back(); return false;">
                        <i class="fa fa-arrow-left"></i> 이전 페이지
                    </button>
                </div>
            </div>
            <div class="card-footer text-center small-muted">
                <span id="redirectTimer"><%= redirectSec %></span>초 후 자동으로 이동합니다.
            </div>
        </div>
    </div>

    <footer class="main-footer text-center">
        <strong>Copyright 2026. <a href="#">asagong</a>.</strong> All rights reserved.
    </footer>
</div>

<script>
    (function(){
        var sec = <%= redirectSec %>;
        var el = document.getElementById('redirectTimer');
        var timer = setInterval(function(){
            sec--;
            if (el) el.textContent = sec;
            if (sec <= 0) {
                clearInterval(timer);
                location.replace('<%= redirectUrl %>');
            }
        }, 1000);
    })();
</script>
</body>
</html>
