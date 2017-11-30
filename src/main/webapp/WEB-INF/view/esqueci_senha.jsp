<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <title>Esqueci minha senha</title>

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
                        <span class="card-title">Digite o email cadastrado</span>
                        <form:form modelAttribute="login" action="${pageContext.request.contextPath}/esqueci_senha/procurar" method="post">
                            <div class="row">
                                <div class="input-field col s12">
                                    <form:input path="email" type="email" required="true" autofocus="true"/>
                                    <form:label path="email">Email</form:label>
                                </div>
                            </div>
                            <div class="row">
                                <div class="input-field col s12">
                                    <button class="btn-large waves-effect waves-light blue white-text" type="submit" name="action">Procurar</button>
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
