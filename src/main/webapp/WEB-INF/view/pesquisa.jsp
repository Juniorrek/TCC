<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Iniciar pesquisa</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">


        <link rel="stylesheet" type="text/css" href="node_modules/bootstrap/dist/css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="node_modules/font-awesome/css/font-awesome.min.css">
        <link rel="stylesheet" type="text/css" href="resources/css/style.css">
    </head>
    <body>
        <header>
            <nav class="navbar navbar-expand-lg navbar-dark bg-ocean">
                <div class="container">
                    <a class="navbar-brand" href="#">
                        <img src="resources/images/tritomus.png" width="40" height="32" class="d-inline-block align-top" alt="">
                        Tritomus
                    </a>
                    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarsExample07" aria-controls="navbarsHeader" aria-expanded="false" aria-label="Toggle navigation">
                        <span class="navbar-toggler-icon"></span>
                    </button>

                    <div class="collapse navbar-collapse" id="navbarsHeader">
                        <ul class="navbar-nav mr-auto">
                            <li class="nav-item active">
                                <a class="nav-link" href="principal.jsp">Home <span class="sr-only">(current)</span></a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="projetos.jsp">Projetos</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="artigos.jsp">Artigos</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="PesquisarServlet">Analisar</a>
                            </li>
                        </ul>
                        <ul class="navbar-nav">
                            <li class="nav-item dropdown">
                                <a class="nav-link dropdown-toggle" href="#" id="dropdown" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><i class="fa fa-user-circle fa-fw" aria-hidden="true"></i> Nome</a>
                                <div class="dropdown-menu" aria-labelledby="dropdown">
                                    <a class="dropdown-item" href="#"><i class="fa fa-address-card fa-fw" aria-hidden="true"></i> Meu perfil</a>
                                    <a class="dropdown-item" href="#"><i class="fa fa-cog fa-fw" aria-hidden="true"></i> Outra parada</a>
                                    <div class="dropdown-divider"></div>
                                    <a class="dropdown-item" href="index.jsp"><i class="fa fa-sign-out fa-fw" aria-hidden="true"></i> Logout</a>
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>
        </header>

        <div class="container">
            <br/>
            <form method="post" action="PesquisarServlet?action=analise">
                <table class="table table-responsive table-striped table-bordered">
                    <thead class="thead-default">
                        <tr><th>Artigo:</th></tr>
                    </thead>
                    <tbody>
                        <c:forEach var="arq" items="${Lista}">
                            <tr><th scope="row"><input type="checkbox" name="arqs" value="${arq}">${arq}</th></tr>
                        </c:forEach>
                    </tbody>
                </table>
                <button type="submit" class="btn btn-labeled btn-success">Iniciar pesquisa</button>
            </form>
        </div>

        <script type="text/javascript" src="node_modules/jquery/dist/jquery.js"></script>
        <script type="text/javascript" src="node_modules/popper.js/dist/umd/popper.min.js"></script>
        <script type="text/javascript" src="node_modules/bootstrap/dist/js/bootstrap.js"></script>
    </body>
</html>
