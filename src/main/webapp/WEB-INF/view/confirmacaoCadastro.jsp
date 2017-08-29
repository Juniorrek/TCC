<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Confirmação de cadastro</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">


        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/node_modules/bootstrap/dist/css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/style.css">
        <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/resources/icons/favicon.ico">
        <style type="text/css">
            .card-block {
                padding: 15% 8% 25% 8%;
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
                            <h2 class="form-login-heading">Cadastro realizado com sucesso !!!</h2>
                            <br/>
                            <h3 class="form-login-heading">Verifique seu email para confirmar seu cadastro.</h3>
                            <footer>
                                <div class="row">
                                     <div class="col-md-12">
                                         <a href="${pageContext.request.contextPath}/login">Logar</a>
                                     </div>
                                 </div> 
                             </footer>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <script type="text/javascript" src="${pageContext.request.contextPath}/node_modules/jquery/dist/jquery.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/node_modules/popper.js/dist/umd/popper.min.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/node_modules/bootstrap/dist/js/bootstrap.js"></script>
    </body>
</html>
