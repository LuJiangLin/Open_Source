<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>邮箱激活成功</title>
    <style>
        * {
            margin: 0px;
            padding: 0px;
        }

        body {
            margin: 0px;
            font-size: 12px;
            color: #222;
            font-family: "微软雅黑", "Arial, Helvetica, sans-serif";
            background: #f5f5f5;
        }

        a {
            color: #444;
            text-decoration: none;
            font-family: "微软雅黑", "Arial, Helvetica, sans-serif";
        }

        a:hover {
            color: #54bfd2;
            text-decoration: none;
        }

        ul {
            list-style: none;
            margin: 0px;
            padding: 0px;
        }

        img {
            border: 0px;
        }

        .clear {
            clear: both;
        }

        /*首页头部代码*/
        .container {
            width: 970px;
            margin: 0 auto;
            text-align: center;
            background: #FFF;
            padding: 4em;
            margin-top: 5em;
            border: 1px solid #efefef;
            font-size: 16px;
            line-height: 30px;
            -webkit-box-shadow: 0px 3px 10px;
            -moz-box-shadow: 0px 3px 10px;
            box-shadow: 0px 3px 10px #e9e9e9;
        }

        .title {
            font-size: 28px;
            font-weight: normal;
            display: block;
            padding-bottom: 25px;
            line-height: 30px;
            color: #666;
        }

        .title_img {
            padding-right: 10px;
            margin-bottom: -10px;
        }

        .orange {
            color: #ff6b07;
            font-weight: normal;
        }
    </style>
</head>
<body>
<div class="container">
    <h1 class="title">
        <img class="title_img" src="${pageContext.request.contextPath}/public/images/success_icon.png" width="40"
             height="40"/>激活邮箱成功！
    </h1>
    您的邮箱<b class="orange">${email}</b>已经成功激活!<br/>
</div>
</body>
</html>