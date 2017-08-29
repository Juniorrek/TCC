<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Cadastro</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">


        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/node_modules/bootstrap/dist/css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/node_modules/toastr/build/toastr.min.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/style.css">
        <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/resources/icons/favicon.ico">
        <style type="text/css">
            .form-login {
                padding: 15% 8% 25% 8%;
            }
            .form-login input[id="inputSenha"] {
                margin-bottom: -1px;
                border-bottom-right-radius: 0;
                border-bottom-left-radius: 0;
            }
            .form-login input[id="inputConfirmacaoSenha"] {
                margin-bottom: 10px;
                border-top-left-radius: 0;
                border-top-right-radius: 0;
            }
            footer {
                margin-top: 5%;
            }
            .card {
                margin-top: 25%;
            }
            body {
                background-color: #41859C;
            }
            .form-login-heading {
                margin-bottom: 10%;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <div class="row">
                <div class="col-md-4"></div>
                <div class="col-md-4">
                    <div class="card text-center">
                        <div class="card-block">
                            <form:form cssClass="form-login" modelAttribute="cadastro" action="${pageContext.request.contextPath}/cadastrar" method="post">
                                <h2 class="form-login-heading">Cadastrar</h2>
                                <form:label path="email" cssClass="sr-only">Email</form:label>
                                <form:input path="email" cssClass="form-control" placeholder="Email" required="true" autofocus="true"/>
                                <br/>
                                <form:label path="nome" cssClass="sr-only">Nome</form:label>
                                <form:input path="nome" cssClass="form-control" placeholder="Nome" required="true"/>
                                <br/>
                                <form:label path="senha" cssClass="sr-only">Senha</form:label>
                                <form:password id="inputSenha" path="senha" cssClass="form-control" placeholder="Senha" required="true"/>
                                <form:label path="confirmacaoSenha" cssClass="sr-only">Confirmação de senha</form:label>
                                <form:password id="inputConfirmacaoSenha" path="confirmacaoSenha" cssClass="form-control" placeholder="Confirmação de senha" required="true"/>
                                <form:button class="btn btn-lg btn-primary btn-block">Cadastrar</form:button>
                                <footer>
                                   <div class="row">
                                        <div class="col-md-12">
                                            <a href="${pageContext.request.contextPath}/login">Já tenho uma conta</a>
                                        </div>
                                    </div> 
                                </footer>
                            </form:form>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <script type="text/javascript" src="${pageContext.request.contextPath}/node_modules/jquery/dist/jquery.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/node_modules/popper.js/dist/umd/popper.min.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/node_modules/bootstrap/dist/js/bootstrap.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/node_modules/toastr/build/toastr.min.js"></script>
        <script type="text/javascript">
            toastr.options = {
                "closeButton": false,
                "debug": false,
                "newestOnTop": false,
                "progressBar": false,
                "positionClass": "toast-top-full-width",
                "preventDuplicates": false,
                "onclick": null,
                "showDuration": "300",
                "hideDuration": "1000",
                "timeOut": "5000",
                "extendedTimeOut": "1000",
                "showEasing": "swing",
                "hideEasing": "linear",
                "showMethod": "fadeIn",
                "hideMethod": "fadeOut"
            };
            ${retorno}
        </script>
    </body>
</html>
