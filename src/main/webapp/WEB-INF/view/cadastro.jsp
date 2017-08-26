<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Cadastro</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">


        <link rel="stylesheet" type="text/css" href="node_modules/bootstrap/dist/css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="resources/css/style.css">
        <style type="text/css">
            .form-login {
                padding: 15% 8% 25% 8%;
            }
            .form-login input[id="inputSenha"] {
                margin-bottom: -1px;
                border-bottom-right-radius: 0;
                border-bottom-left-radius: 0;
            }
            .form-login input[id="inputSenhaConfirmacao"] {
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
                            <form class="form-login" action="login.jsp" method="post">
                                <h2 class="form-login-heading">Cadastrar</h2>
                                <label for="inputEmail" class="sr-only">Email</label>
                                <input type="email" id="inputEmail" class="form-control" placeholder="Email" required="" autofocus="">
                                <br>
                                <label for="inputNome" class="sr-only">Nome</label>
                                <input type="text" id="inputNome" class="form-control" placeholder="Nome" required="">
                                <br>
                                <label for="inputSenha" class="sr-only">Senha</label>
                                <input type="password" id="inputSenha" class="form-control" placeholder="Senha" required="">
                                <label for="inputSenhaConfirmacao" class="sr-only">Confirmação de senha</label>
                                <input type="password" id="inputSenhaConfirmacao" class="form-control" placeholder="Confirmação de senha" required="">
                                <button class="btn btn-lg btn-primary btn-block" type="submit">Cadastrar</button>
                                <footer>
                                   <div class="row">
                                        <div class="col-md-12">
                                            <a href="login.jsp">Já tenho uma conta</a>
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
