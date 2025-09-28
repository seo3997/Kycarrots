<?xml version="1.0" encoding="UTF-8" ?> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//KO" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang="ko">
<head>
<title>Welcome Kentucky Carrot!</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- ===== Terms 전용 공통 설정 ===== -->
<meta name="viewport" content="width=device-width, initial-scale=1, viewport-fit=cover" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />

<style>
	/* ===== Terms (모바일 WebView 최적화) ===== */
	:root{
		--bg:#FFFFFF; --text:#111111; --muted:#555555; --primary:#0A84FF;
		--divider:#E5E5EA; --chip-bg:#F2F2F7; --card:#FFFFFF;
		--radius:14px; --shadow:0 1px 3px rgba(0,0,0,.06), 0 6px 18px rgba(0,0,0,.06);
		--base:16px; --line:1.65;
	}
	@media (prefers-color-scheme: dark){
		:root{ --bg:#0B0B0F; --text:#F2F2F2; --muted:#B9BBC1; --primary:#6CA8FF;
			--divider:#24242A; --chip-bg:#17171C; --card:#0F0F14; --shadow:none; }
	}
	*{box-sizing:border-box; -webkit-tap-highlight-color:transparent;}
	html,body{
		margin:0; padding:0; background:var(--bg); color:var(--text);
		font-family:-apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, "Noto Sans KR",
		"Apple SD Gothic Neo", "맑은 고딕", "Malgun Gothic", Arial, sans-serif;
		font-size:var(--base); line-height:var(--line);
		-webkit-text-size-adjust:100%; text-size-adjust:100%;
	}
	#wrapper{padding:12px;}
	.join_terms{max-width:880px; margin:0 auto;}
	.join_terms dl{margin:0; padding:0;}
	.join_terms dt, .join_terms dd{margin:0;}
	.join_terms dt{
		display:inline-block; margin:20px 8px 10px 0; padding:8px 12px;
		background:var(--chip-bg); border-radius:999px; font-weight:700;
		font-size:clamp(15px, 2.8vw, 17px); color:var(--text); border:1px solid var(--divider);
	}
	.join_terms dd{
		margin:0 0 18px 0; padding:14px 16px; background:var(--card);
		border:1px solid var(--divider); border-radius:var(--radius); box-shadow:var(--shadow);
		font-size:clamp(14px, 2.6vw, 16px); color:var(--text);
	}
	.join_terms dd p{margin:0 0 10px;}
	.join_terms dd br{line-height:var(--line);}
	.join_terms dd .dash{position:relative; padding-left:14px;}
	.join_terms dd .dash:before{content:"–"; position:absolute; left:0; top:0; color:var(--muted);}
	a{color:var(--primary); text-decoration:none;} a:hover,a:focus{text-decoration:underline;}
	b,strong{font-weight:700;} small{color:var(--muted);}
	.hr{height:1px; background:var(--divider); border:0; margin:16px 0;}
	table{width:100%; border-collapse:collapse; margin:10px 0; font-size:0.95em;}
	th,td{padding:10px 12px; border:1px solid var(--divider);} th{text-align:left; background:var(--chip-bg);}
	:focus-visible{outline:2px solid var(--primary); outline-offset:2px;}
	@media (max-width:480px){ #wrapper{padding:10px;} .join_terms dd{padding:12px 13px;} }
	@media print{
		:root{ --bg:#FFFFFF; --text:#000; --divider:#CCC; }
		#wrapper{padding:0;} .join_terms dd{box-shadow:none; border-color:#CCC; page-break-inside:avoid;}
		a{text-decoration:underline;}
	}
</style>
</head>