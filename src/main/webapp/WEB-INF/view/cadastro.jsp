<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <title>Cadastro</title>

        <link rel="stylesheet" href="${pageContext.request.contextPath}/node_modules/material-design-icons-iconfont/dist/fonts/material-icons.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/node_modules/materialize-css/dist/css/materialize.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/node_modules/toastr/build/toastr.min.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/style.css">
        <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/resources/icons/favicon.ico">
    </head>
    <body style="background-color: #2196f3;">
        <div class="row">
            <div class="col s12 m4 offset-m4">
                <div class="card white">
                    <div class="card-content">
                        <span class="card-title">Cadastro</span>
                        <form:form modelAttribute="cadastro" action="${pageContext.request.contextPath}/cadastrar" method="post">
                            <div class="row">
                                <div class="input-field col s12">
                                    <form:input type="email" path="email" cssClass="validate" required="true" autofocus="true"/>
                                    <form:label path="email">Email</form:label>
                                </div>
                            </div>
                            <div class="row">
                                <div class="input-field col s12">
                                    <form:input path="nome" required="true"/>
                                    <form:label path="nome">Nome</form:label>
                                </div>
                            </div>
                            <div class="row">
                                <div class="input-field col s12">
                                    <form:password path="senha" cssClass="validate" required="true"/>
                                    <form:label path="senha">Senha</form:label>
                                </div>
                            </div>
                            <div class="row">
                                <div class="input-field col s12">
                                    <form:password path="confirmacaoSenha" cssClass="validate" required="true"/>
                                    <form:label path="confirmacaoSenha">Confirmação de senha</form:label>
                                </div>
                            </div>
                            <div class="row">
                                <div class="input-field col s12">
                                    <button class="btn-large waves-effect waves-light blue" type="submit" name="action" style="color: #FFF;">Cadastrar</button>
                                </div>
                            </div>
                            <div class="row">
                                <div class="input-field col s12 center">
                                    <a href="${pageContext.request.contextPath}/login">Já tenho uma conta</a>
                                </div>
                            </div>
                        </form:form>
                    </div>
                </div>
            </div>
        </div>

        <script type="text/javascript" src="${pageContext.request.contextPath}/node_modules/jquery/dist/jquery.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/node_modules/materialize-css/dist/js/materialize.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/node_modules/toastr/build/toastr.min.js"></script>
        <script type="text/javascript">
            toastr.options.timeOut = 0;
            toastr.options.extendedTimeOut = 0;
            ${retorno}
        </script>
    </body>
</html>
