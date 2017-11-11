<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <title>Alterar senhar</title>

        <link rel="stylesheet" href="${pageContext.request.contextPath}/node_modules/material-design-icons-iconfont/dist/fonts/material-icons.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/node_modules/materialize-css/dist/css/materialize.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/node_modules/toastr/build/toastr.min.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/style.css">
        <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/resources/icons/favicon.ico">
    </head>
    <body>
        <header>
            <ul id="dropdown-logado" class="dropdown-content">
                <li class="active"><a href="${pageContext.request.contextPath}/meuPerfil/alterarSenha">Alterar senha</a></li>
                <li class="divider"></li>
                <li><a href="${pageContext.request.contextPath}/logout">Logout</a></li>
            </ul>
            <nav class="blue blue-darken-1">
                <div class="container nav-wrapper">
                    <a href="${pageContext.request.contextPath}/principal" class="brand-logo">
                        <img class="responsive-img" style="height: 60px;" src="${pageContext.request.contextPath}/resources/images/artemisLogo.png" />
                    </a>
                    <a href="#" data-activates="mobile-menu" class="button-collapse"><i class="material-icons">menu</i></a>
                    <ul class="right hide-on-med-and-down">
                      <li><a href="${pageContext.request.contextPath}/principal"><i class="material-icons left">home</i>Home</a></li>
                      <li><a href="${pageContext.request.contextPath}/projetos"><i class="material-icons left">work</i>Projetos</a></li>
                      <li><a class="dropdown-button" href="#!" data-activates="dropdown-logado"><i class="material-icons left">account_circle</i>${logado.nome}<i class="material-icons right">arrow_drop_down</i></a></li>
                    </ul>
                    <ul class="side-nav" id="mobile-menu">
                      <li><a href="${pageContext.request.contextPath}/principal"><i class="material-icons left">home</i>Home</a></li>
                      <li><a href="${pageContext.request.contextPath}/projetos"><i class="material-icons left">work</i>Projetos</a></li>
                      <li class="divider"></li>
                      <li><a href="${pageContext.request.contextPath}/meuPerfil/alterarSenha">Alterar senha</a></li>
                      <li><a href="${pageContext.request.contextPath}/logout">Logout</a></li>
                    </ul>
                </div>
            </nav>
        </header>
                    
        <div class="container" style="margin-top: 2%;">
            <div class="row">
                <div class="col s6 offset-s3">
                    <div class="card white">
                        <div class="card-content">
                            <span class="card-title">Alterar senha</span>
                            <form action="${pageContext.request.contextPath}/meuPerfil/alterarSenha/confirmar" method="post">
                                <div class="row">
                                    <div class="input-field col s12">
                                        <input type="password" id="inputSenhaAtual" name="senhaAtual" required="true" />
                                        <label for="inputSenhaAtual">Senha atual</label>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="input-field col s12">
                                        <input type="password" id="inputNovaSenha" name="novaSenha" required="true" />
                                        <label for="inputNovaSenha">Nova senha</label>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="input-field col s12">
                                        <input type="password" id="inputNovaSenhaConfirmacao" name="novaSenhaConfirmacao" required="true" />
                                        <label for="inputNovaSenhaConfirmacao">Confirmação nova senha</label>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="input-field col s12">
                                        <button class="btn-large waves-effect waves-light blue white-text" type="submit" name="action">Alterar</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <script type="text/javascript" src="${pageContext.request.contextPath}/node_modules/jquery/dist/jquery.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/node_modules/materialize-css/dist/js/materialize.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/node_modules/toastr/build/toastr.min.js"></script>
        <script type="text/javascript">
            $(document).ready(function(){
                $(".button-collapse").sideNav();
                $(".dropdown-button").dropdown({
                    belowOrigin: true
                });
                
                toastr.options.timeOut = 0;
                toastr.options.extendedTimeOut = 0;
                ${retorno}
            });
        </script>
    </body>
</html>
