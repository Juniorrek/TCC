<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Projetos</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">


        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/node_modules/bootstrap/dist/css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/node_modules/font-awesome/css/font-awesome.min.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/node_modules/datatables.net-bs4/css/dataTables.bootstrap4.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/node_modules/toastr/build/toastr.min.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/style.css">
        <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/resources/icons/favicon.ico">
    </head>
    <body>
        <header style="margin-bottom: 20px;">
            <nav class="navbar navbar-expand-lg navbar-dark bg-ocean">
                <div class="container">
                    <a class="navbar-brand" href="${pageContext.request.contextPath}/principal">
                        <img src="${pageContext.request.contextPath}/resources/images/tritomus.png" width="40" height="32" class="d-inline-block align-top" alt="">
                        Tritomus
                    </a>
                    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarsExample07" aria-controls="navbarsHeader" aria-expanded="false" aria-label="Toggle navigation">
                        <span class="navbar-toggler-icon"></span>
                    </button>

                    <div class="collapse navbar-collapse" id="navbarsHeader">
                        <ul class="navbar-nav mr-auto">
                            <li class="nav-item">
                                <a class="nav-link" href="${pageContext.request.contextPath}/principal">&nbsp; Home <span class="sr-only">(current)</span></a>
                            </li>
                            <li class="nav-item active">
                                <a class="nav-link" href="${pageContext.request.contextPath}/projetos">&nbsp; Projetos</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="${pageContext.request.contextPath}/artigos">&nbsp; Artigos</a>
                            </li>
                        </ul>
                        <ul class="navbar-nav">
                            <li class="nav-item dropdown">
                                <a class="nav-link dropdown-toggle" href="#" id="dropdown" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><i class="fa fa-user-circle fa-fw" aria-hidden="true"></i>&nbsp; ${logado.nome}</a>
                                <div class="dropdown-menu" aria-labelledby="dropdown">
                                    <a class="dropdown-item" href="#"><i class="fa fa-address-card fa-fw" aria-hidden="true"></i>&nbsp; Meu perfil</a>
                                    <a class="dropdown-item" href="#"><i class="fa fa-cog fa-fw" aria-hidden="true"></i>&nbsp; Outra parada</a>
                                    <div class="dropdown-divider"></div>
                                    <a class="dropdown-item" href="${pageContext.request.contextPath}/logout"><i class="fa fa-sign-out fa-fw" aria-hidden="true"></i>&nbsp; Logout</a>
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>
        </header>

        <div class="container">
            <div class="row">
                <div class="col-md-12">
                    <div class="card">
                        <div class="card-header">
                            <h4 class="card-title">Projetos</h4>
                        </div>
                        <div class="card-block" style="padding: 25px;">
                            <table id="tableProjetos" class="table table-striped table-bordered" cellspacing="0" width="100%">
                                <thead>
                                    <tr>
                                        <th hidden>Id</th>
                                        <th>Nome</th>
                                        <th hidden>Descricao</th>
                                        <th>Ação</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${projetos}" var="projeto">
                                        <tr>
                                            <td hidden>${projeto.id}</td>
                                            <td>${projeto.nome}</td>
                                            <td hidden>${projeto.descricao}</td>
                                            <td>
                                                <div class="btn-group mr-2" role="group">
                                                    <button id="btnVizualizarProjeto" type="button" class="btn btn-primary"><i class="fa fa-eye" aria-hidden="true"></i></button>
                                                    <button id="btnEditarProjeto" type="button" class="btn btn-info"><i class="fa fa-edit" aria-hidden="true"></i></button>
                                                    <button id="btnDeletarProjeto" type="button" class="btn btn-danger"><i class="fa fa-trash" aria-hidden="true"></i></button>
                                                </div>
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
            <div class="modal fade" id="modalAdicionarProjeto" tabindex="-1" role="dialog" aria-labelledby="modalAdicionarProjetoLabel" aria-hidden="true">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="modalAdicionarProjetoLabel">Adicionar projeto</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <div class="form-group">
                                <form:label path="nome">Nome</form:label>
                                <form:input path="nome" cssClass="form-control" required="true" autofocus="true"/>
                            </div>
                            <div class="form-group">
                                <form:label path="descricao">Descrição</form:label>
                                <form:textarea path="descricao" cssClass="form-control" required="true" autofocus="true"/>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancelar</button>
                            <button type="submit" class="btn btn-success">Adicionar</button>
                        </div>
                    </div>
                </div>
            </div>
        </form:form>
                         
        <form id="formEditar" action="${pageContext.request.contextPath}/projetos/editar" method="post">
            <div class="modal fade" id="modalEditarProjeto" tabindex="-1" role="dialog" aria-labelledby="modalEditarProjetoLabel" aria-hidden="true">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="modalEditarProjetoLabel">Editar projeto</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <input type="hidden" class="form-control" id="inputId" name="id" />
                            <div class="form-group">
                                <label for="inputNome">Nome</label>
                                <input type="text" class="form-control" id="inputNome" name="nome" />
                            </div>
                            <div class="form-group">
                                <label for="textareaDescricao">Descricao</label>
                                <textarea class="form-control" id="textareaDescricao" name="descricao" rows="3"></textarea>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancelar</button>
                            <button type="submit" class="btn btn-info">Editar</button>
                        </div>
                    </div>
                </div>
            </div>
        </form>
            
        <form id="formDeletar" action="${pageContext.request.contextPath}/projetos/deletar" method="post">
            <div class="modal fade" id="modalDeletarProjeto" tabindex="-1" role="dialog" aria-labelledby="modalDeletarProjetoLabel" aria-hidden="true">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="modalDeletarProjetoLabel">Deletar projeto</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <input type="hidden" class="form-control" id="inputId" name="id" />
                            Tem certeza que deseja deletar este projeto?
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancelar</button>
                            <button type="submit" class="btn btn-danger">Deletar</button>
                        </div>
                    </div>
                </div>
            </div>
        </form>
            
        <form id="formVizualizar" action="${pageContext.request.contextPath}/projetos/vizualizar" method="post">
            <input type="hidden" class="form-control" id="inputId" name="id" />
        </form>

        <script type="text/javascript" src="${pageContext.request.contextPath}/node_modules/jquery/dist/jquery.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/node_modules/popper.js/dist/umd/popper.min.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/node_modules/bootstrap/dist/js/bootstrap.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/node_modules/datatables.net/js/jquery.dataTables.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/node_modules/datatables.net-bs4/js/dataTables.bootstrap4.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/node_modules/datatables.net-buttons/js/dataTables.buttons.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/node_modules/toastr/build/toastr.min.js"></script>
        <script type="text/javascript">
            $(document).ready(function() {
                var tableProjetos = $('#tableProjetos').DataTable({
                    "language": lang,
                    "dom": 'Bfrtip',
                    "buttons": [
                        {
                            "text": 'Adicionar',
                            "className": 'btn btn-success',
                            "action": function (e, dt, node, config) {
                                $('#modalAdicionarProjeto').modal('show');
                            }
                        }
                    ],
                    "columnDefs": [
                        { 
                            "width": "10%",
                            "targets": 3,
                            "orderable": false,
                            "searchable": false
                        }
                    ]
                });
                
                $('#tableProjetos tbody').on( 'click', 'button[id="btnVizualizarProjeto"]', function () {
                    var data = tableProjetos.row( $(this).parents('tr') ).data();
                    
                    $('#formVizualizar #inputId').val(data[0]);
                    
                    $('#formVizualizar').submit();
                });
                
                $('#tableProjetos tbody').on( 'click', 'button[id="btnEditarProjeto"]', function () {
                    var data = tableProjetos.row( $(this).parents('tr') ).data();
                    
                    $('#formEditar #inputId').val(data[0]);
                    $('#formEditar #inputNome').val(data[1]);
                    $('#formEditar #textareaDescricao').val(data[2]);
                    
                    $('#modalEditarProjeto').modal('show');
                });
                
                $('#tableProjetos tbody').on( 'click', 'button[id="btnDeletarProjeto"]', function () {
                    var data = tableProjetos.row( $(this).parents('tr') ).data();
                    
                    $('#formDeletar #inputId').val(data[0]);
                    
                    $('#modalDeletarProjeto').modal('show');
                });
                
                toastr.options = {
                    "closeButton": false,
                    "debug": false,
                    "newestOnTop": false,
                    "progressBar": false,
                    "positionClass": "toast-bottom-right",
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
            });
            
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
