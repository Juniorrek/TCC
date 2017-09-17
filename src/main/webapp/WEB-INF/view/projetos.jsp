<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <title>Projetos</title>


        <link rel="stylesheet" href="${pageContext.request.contextPath}/node_modules/material-design-icons-iconfont/dist/fonts/material-icons.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/node_modules/materialize-css/dist/css/materialize.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/node_modules/datatables.net-dt/css/jquery.dataTables.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/style.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/projetos.css">
        <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/resources/icons/favicon.ico">
    </head>
    <body>
        <header>
            <ul id="dropdown-logado" class="dropdown-content">
                <li><a href="#!">Meu perfil</a></li>
                <li class="divider"></li>
                <li><a href="${pageContext.request.contextPath}/logout">Logout</a></li>
            </ul>
            <nav class="blue blue-darken-1">
                <div class="container nav-wrapper">
                    <a href="${pageContext.request.contextPath}/principal" class="brand-logo">Tritomus</a>
                    <a href="#" data-activates="mobile-menu" class="button-collapse"><i class="material-icons">menu</i></a>
                    <ul class="right hide-on-med-and-down">
                      <li><a href="${pageContext.request.contextPath}/principal"><i class="material-icons left">home</i>Home</a></li>
                      <li class="active"><a href="${pageContext.request.contextPath}/projetos"><i class="material-icons left">work</i>Projetos</a></li>
                      <li><a class="dropdown-button" href="#!" data-activates="dropdown-logado"><i class="material-icons left">account_circle</i>${logado.nome}<i class="material-icons right">arrow_drop_down</i></a></li>
                    </ul>
                    <ul class="side-nav" id="mobile-menu">
                      <li><a href="${pageContext.request.contextPath}/principal"><i class="material-icons left">home</i>Home</a></li>
                      <li class="active"><a href="${pageContext.request.contextPath}/projetos"><i class="material-icons left">work</i>Projetos</a></li>
                      <li class="divider"></li>
                      <li><a href="#!">Meu perfil</a></li>
                      <li><a href="${pageContext.request.contextPath}/logout">Logout</a></li>
                    </ul>
                </div>
            </nav>
        </header>
                    
        <div class="container">
            <div class="row">
                <div class="col s12">
                    <div class="card white">
                        <div class="card-content">
                            <span class="card-title">Projetos</span>
                            <table id="tableProjetos" class="striped centered">
                                <a id="newProject" data-target="modalAdicionarProjeto" class="waves-effect waves-light btn modal-trigger"><i class="material-icons left">add</i>criar projeto</a>
                                <thead>
                                    <tr>
                                        <th hidden>Id</th>
                                        <th>Nome</th>
                                        <th>Ação</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${projetos}" var="projeto">
                                        <tr>
                                            <td hidden>${projeto.id}</td>
                                            <td>${projeto.nome}</td>
                                            <td>
                                                <button class="btn-floating waves-effect waves-light blue" onclick="vizualizarProjeto(${projeto.id})"><i class="material-icons">visibility</i></button>
                                                <button class="btn-floating waves-effect waves-light red" onclick="deletarProjeto(${projeto.id})"><i class="material-icons">delete</i></button>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
                    
        <form:form modelAttribute="projeto" action="${pageContext.request.contextPath}/projetos/adicionar" method="post">
            <div id="modalAdicionarProjeto" class="modal">
                <div class="modal-content">
                    <h4>Adicionar projeto</h4>
                    <div class="row">
                        <div class="input-field col s12">
                            <form:input path="nome" cssClass="validate" required="true" autofocus="true"/>
                            <form:label path="nome">Nome</form:label>
                        </div>
                    </div>
                    <div class="row">
                        <div class="input-field col s12">
                            <form:textarea path="descricao" cssClass="materialize-textarea" data-length="200" />
                            <form:label path="descricao">Descrição</form:label>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <a href="#!" class="modal-action modal-close waves-effect waves-light btn-flat">Voltar</a>
                    <button type="submit" class="waves-effect waves-green btn green">Adicionar</button>
                </div>
            </div>
        </form:form>
                    
        <form:form modelAttribute="projeto" action="${pageContext.request.contextPath}/projetos/deletar" method="post">
            <div id="modalDeletarProjeto" class="modal">
                <div class="modal-content">
                    <h4>Deletar projeto</h4>
                    <input type="hidden" name="id" />
                    <p>Tem certeza que deseja deletar este projeto?</p>
                </div>
                <div class="modal-footer">
                    <a href="#!" class="modal-action modal-close waves-effect waves-light btn-flat">Voltar</a>
                    <button type="submit" class="waves-effect waves-red btn red">Deletar</button>
                </div>
            </div>
        </form:form>
                    
        <form id="formVizualizarProjeto" action="${pageContext.request.contextPath}/projetos/vizualizar" method="post">
            <input type="hidden" name="id" />
        </form>
                    
        <div class="fixed-action-btn">
            <button id="btnAdicionarProjeto" data-target="modalAdicionarProjeto" class="btn-floating waves-effect waves-light btn-large green modal-trigger">
                <i class="large material-icons">add</i>
            </button>
        </div>

        <script type="text/javascript" src="${pageContext.request.contextPath}/node_modules/jquery/dist/jquery.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/node_modules/datatables.net/js/jquery.dataTables.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/node_modules/materialize-css/dist/js/materialize.js"></script>
        <script type="text/javascript">
            $(document).ready(function() {
                $(".button-collapse").sideNav();
                
                $(".dropdown-button").dropdown({
                    belowOrigin: true
                });
                
                $('.modal').modal();
                
                $('#tableProjetos').DataTable({
                    "language": lang,
                    "dom": 'Bfrtip',
                    "columnDefs": [
                        { 
                            "width": "20%",
                            "targets": 2,
                            "orderable": false,
                            "searchable": false
                        }
                    ]
                });
            });
            
            function deletarProjeto(id) {
                $('#modalDeletarProjeto input[name="id"]').val(id);
                $('#modalDeletarProjeto').modal('open');
            }
            
            function vizualizarProjeto(id) {
                $('#formVizualizarProjeto input[name="id"]').val(id);
                $('#formVizualizarProjeto').submit();
            }
            
            var lang = {
                "sEmptyTable": "Nenhum registro encontrado",
                "sInfo": "Mostrando de _START_ até _END_ de _TOTAL_ registros",
                "sInfoEmpty": "Mostrando 0 até 0 de 0 registros",
                "sInfoFiltered": "(Filtrados de _MAX_ registros)",
                "sInfoPostFix": "",
                "sInfoThousands": ".",
                "sLengthMenu": "_MENU_ resultados por página",
                "sLoadingRecords": "Carregando...",
                "sProcessing": "Processando...",
                "sZeroRecords": "Nenhum registro encontrado",
                "sSearch": "Pesquisar",
                "oPaginate": {
                    "sNext": "Próximo",
                    "sPrevious": "Anterior",
                    "sFirst": "Primeiro",
                    "sLast": "Último"
                },
                "oAria": {
                    "sSortAscending": ": Ordenar colunas de forma ascendente",
                    "sSortDescending": ": Ordenar colunas de forma descendente"
                }
            };
        </script>
    </body>
</html>
