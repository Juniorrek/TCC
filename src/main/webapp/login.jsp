<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Login</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">


        <link rel="stylesheet" type="text/css" href="node_modules/bootstrap/dist/css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="resources/css/style.css">
        <style type="text/css">
            .form-login {
                padding: 15% 8% 25% 8%;
            }
            .form-login input[type="email"] {
                margin-bottom: -1px;
                border-bottom-right-radius: 0;
                border-bottom-left-radius: 0;
            }
            .form-login input[type="password"] {
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
                            <form class="form-login" action="principal.jsp" method="post">
                                <h2 class="form-login-heading">Login</h2>
                                <label for="inputEmail" class="sr-only">Email</label>
                                <input type="email" id="inputEmail" class="form-control" placeholder="Email" required="" autofocus="">
                                <label for="inputSenha" class="sr-only">Senha</label>
                                <input type="password" id="inputSenha" class="form-control" placeholder="Senha" required="">
                                <div class="row">
                                    <div class="col-md-6">
                                        <a class="btn btn-lg btn-primary btn-block" href="cadastro.jsp">Cadastrar</a>
                                    </div>
                                    <div class="col-md-6">
                                        <button class="btn btn-lg btn-success btn-block" type="submit">Entrar</button>
                                    </div>
                                </div>
                                <footer>
                                   <div class="row">
                                        <div class="col-md-12">
                                            <a href="#">Esqueci minha senha</a>
                                        </div>
                                    </div> 
                                </footer>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <script type="text/javascript" src="node_modules/jquery/dist/jquery.js"></script>
        <script type="text/javascript" src="node_modules/popper.js/dist/umd/popper.min.js"></script>
        <script type="text/javascript" src="node_modules/bootstrap/dist/js/bootstrap.js"></script>
    </body>
</html>
