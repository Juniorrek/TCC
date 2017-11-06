<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <title>Index</title>
        
        <link rel="stylesheet" href="${pageContext.request.contextPath}/node_modules/material-design-icons-iconfont/dist/fonts/material-icons.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/node_modules/materialize-css/dist/css/materialize.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/style.css">
        <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/resources/icons/favicon.ico">
    </head>
    <body>
        <header>
            <nav class="blue blue-darken-1">
                <div class="container nav-wrapper">
                    <a href="${pageContext.request.contextPath}/" class="brand-logo">
                        <img class="responsive-img" style="height: 60px;" src="${pageContext.request.contextPath}/resources/images/artemisLogo.png" />
                    </a>
                    <a href="#" data-activates="mobile-menu" class="button-collapse"><i class="material-icons">menu</i></a>
                    <ul class="right hide-on-med-and-down">
                      <li><a href="${pageContext.request.contextPath}/login">Login</a></li>
                      <li><a href="${pageContext.request.contextPath}/cadastro" class="btn white" style="color: #000;">Cadastro</a></li>
                    </ul>
                    <ul class="side-nav" id="mobile-menu">
                      <li><a href="${pageContext.request.contextPath}/login">Login</a></li>
                      <li><a href="${pageContext.request.contextPath}/cadastro">Cadastro</a></li>
                    </ul>
                </div>
            </nav>
        </header>
                    
        <div class="container" style="margin-top: 5%;">
            <div class="row">
                <div class="col s6">
                    <div class="row col s12">
                        <h3>Análise de artigos</h3>
                    </div>
                    <div class="row col s12">
                        <h5 style="text-align: justify;">Texto texto bla bla Organize seus artigos sistema legal muito foda usem texto texto que vai inventar esse texto?</h5>
                    </div>
                    <div class="row col s12" style="display: block;">
                        <a href="${pageContext.request.contextPath}/cadastro" class="btn blue blue-darken-1">Cadastro</a>
                        <p style="margin: 10px; display: inline-block;">ou</p>
                        <a href="#" class="btn blue blue-darken-1">Saiba mais</a>
                    </div>
                </div>
                <div class="col s1">
                </div>
                <div class="col s5">
                    <img class="responsive-img" src="${pageContext.request.contextPath}/resources/images/reverseArtemisLogo.png" />
                </div>
            </div>
        </div>

        <script type="text/javascript" src="${pageContext.request.contextPath}/node_modules/jquery/dist/jquery.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/node_modules/materialize-css/dist/js/materialize.js"></script>
        <script type="text/javascript">
            $(document).ready(function(){
                $(".button-collapse").sideNav();
            });
        </script>
    </body>
</html>
