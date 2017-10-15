<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <title>Projeto</title>


        <link rel="stylesheet" href="${pageContext.request.contextPath}/node_modules/material-design-icons-iconfont/dist/fonts/material-icons.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/node_modules/materialize-css/dist/css/materialize.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/node_modules/fine-uploader/fine-uploader/fine-uploader-new.min.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/node_modules/datatables.net-dt/css/jquery.dataTables.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/style.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/projeto.css">
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
                    
        <!--<div id="containao" class="container">-->
            <div class="row">
                <div id="containao" class="col s12 l8 offset-l2">
                    <div class="card grey lighten-4">
                        <div class="card-content">
                            <span class="card-title">Projeto ${projeto.nome}</span>
                        </div>
                        <div class="card-tabs">
                            <ul class="tabs tabs-fixed-width" id="tabMaster">
                                <li class="tab"><a class="active" href="#dados">Dados</a></li>
                                <li class="tab"><a href="#artigos">Artigos</a></li>
                                <li class="tab"><a href="#analise">Análises</a></li>
                            </ul>
                        </div>
                        <div class="card-content white">
                            <div id="dados">
                                <form id="formEditarProjeto" action="${pageContext.request.contextPath}/projetos/editar" method="post">
                                    <input name="id" type="hidden" value="${projeto.id}" />
                                    <div class="input-field">
                                        <input id="nomeProjeto" name="nome" type="text" value="${projeto.nome}" />
                                        <label for="nomeProjeto">Nome</label>
                                    </div>
                                    <div class="input-field">
                                        <textarea id="descricaoProjeto" name="descricao" class="materialize-textarea"  data-length="200">${projeto.descricao}</textarea>
                                        <label for="descricaoProjeto">Descrição</label>
                                    </div>
                                    <div style="margin-bottom: 50px;">
                                        <p style="color: #9e9e9e;">Sinônimos</p>
                                        <a class="btn waves-effect waves-light modal-trigger segment-button" href="#modalSinonimosObjetivo">Objetivo</a>
                                        <a class="btn waves-effect waves-light modal-trigger segment-button" href="#modalSinonimosMetodologia">Metodologia</a>
                                        <a class="btn waves-effect waves-light modal-trigger segment-button" href="#modalSinonimosResultado">Resultado</a>
                                    </div>
                                    <button type="submit" class="btn-floating halfway-fab waves-effect waves-light blue left"><i class="material-icons">mode_edit</i></button>
                                </form>
                            </div>
                            <div id="artigos">
<!--                                <div class="row">
                                    <%--<c:forEach items="${artigos}" var="artigo">--%>
                                        <div class="col s12 m4">
                                            <div class="card grey lighten-4">
                                                <div class="card-content">
                                                    <p>${artigo.getName().replace("_", " ").replace(".pdf", "")}</p>
                                                    <%--<c:set var="absolutePath" value='${artigo.path.replace("\\", "/")}' />--%>
                                                    <button class="btn-floating waves-effect waves-light blue left" onclick="vizualizarArtigo('${artigo.getName()}')"><i class="material-icons">visibility</i></button>
                                                    <button class="btn-floating waves-effect waves-light red right" onclick="deletarArtigo('${absolutePath}')"><i class="material-icons">delete</i></button>
                                                </div>
                                            </div>
                                        </div>
                                    <%--</c:forEach>--%>
                                </div>-->
                                <button data-target="modalAdicionarArtigo" class="btn-floating halfway-fab waves-effect waves-light green center-btn modal-trigger orange-button-small"><i class="material-icons">add</i></button>
                                 <div class="row">
                                    <div class="col s12">
                                        <div class="card white">
                                            <div class="card-content">
                                                <span class="card-title">Artigos</span>
                                                <table id="tableArtigos" class="striped centered">
                                                    <a data-target="modalAdicionarArtigo" class="waves-effect waves-light btn modal-trigger orange-button"><i class="material-icons left">add</i>adicionar artigos</a>
                                                    <thead>
                                                        <tr>
                                                            <th hidden>Id</th>
                                                            <th>Nome</th>
                                                            <th>Ação</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody id="tableBody">
                                                        <c:forEach items="${projeto.artigos}" var="artigo">
                                                            <tr>
                                                                <td hidden></td>
                                                                <td>${artigo.nome.replace(".pdf", "")}</td>
                                                                <td>
                                                                    <button class="btn-floating wavesartigo-effect waves-light blue" onclick="visualizarArtigo(${artigo.id})"><i class="material-icons">visibility</i></button>
                                                                    <button class="btn-floating waves-effect waves-light red" onclick="deletarArtigo('${artigo.id}')"><i class="material-icons">delete</i></button>
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
                            <div id="analise">
                                <div class='row'>
                                    <div class='col s12'>
                                        <ul class="collapsible" data-collapsible="accordion">
                                            <c:forEach items="${segmentos_artigos}" var="artigo" varStatus="status">
                                                <li>
                                                    <div class="collapsible-header article-header" 
                                                         onclick="nuvem('${artigo.mainWords}','${status.count}')">
                                                         <i class="material-icons right more">expand_more</i>
                                                         ${artigo.nome.replace("_", " ").replace(".pdf", "")}
                                                    </div>
                                                    <div class="collapsible-body">
                                                        <span>
                                                            <div class="row">
                                                                <div class="col s12">
                                                                    <ul class="tabs tabs-fixed-width">
                                                                        <li class="tab col s3"><a class="active" href="#beginSegmentos${status.count}">Segmentos</a></li>
                                                                        <li class="tab col s3" onclick="mainWordsChart('${artigo.mainWords}','wordChart${status.count}')"><a href="#beginTFs${status.count}">TF</a></li>
                                                                        <li class="tab col s3"><a href="#beginWORDCLOUD${status.count}">WORDCLOUD</a></li>
                                                                    </ul>
                                                                </div>
                                                                <div id="beginSegmentos${status.count}" class="col s12">
                                                                    <h6>Resumo</h6>
                                                                    <ul class='collapsible' data-collapsible='accordion'>
                                                                        <li>
                                                                            <div class='collapsible-header abstract'>Abstract</div>
                                                                            <div class='collapsible-body'><span>${artigo.resumo}</span></div>
                                                                        </li>
                                                                    </ul>
                                                                    <h6>Segmentos</h6>
                                                                    <ul class='collapsible' data-collapsible='accordion'>
                                                                        <li>
                                                                            <div class='collapsible-header objective'>Objetivo</div>
                                                                            <div class='collapsible-body'><span>${artigo.objetivo}</span></div>
                                                                        </li>
                                                                        <li>
                                                                            <div class='collapsible-header methodology'>Metodologia</div>
                                                                            <div class='collapsible-body'><span>${artigo.metodologia}</span></div>
                                                                        </li>
                                                                        <li>
                                                                            <div class='collapsible-header conclusion'>Resultado</div>
                                                                            <div class='collapsible-body'><span>${artigo.resultado}</span></div>
                                                                        </li>
                                                                    </ul>
                                                                </div>
                                                                <div id="beginTFs${status.count}" class="col s12">
                                                                    <ul class="tabs tabs-fixed-width">
                                                                        <li class="tab col s3">
                                                                            <a href="#word${status.count}">Palavra</a>
                                                                        </li>
                                                                        <li class="tab col s3">
                                                                            <a href="#bigram${status.count}" onclick="mainWordsChart('${artigo.mainBigrams}','bigramChart${status.count}')">Bigrama</a>
                                                                        </li>
                                                                        <li class="tab col s3"><a href="#trigram${status.count}" onclick="mainWordsChart('${artigo.mainTrigrams}','trigramChart${status.count}')">Trigrama</a></li>
                                                                    </ul>
                                                                    <div id="word${status.count}">
                                                                         <canvas id="wordChart${status.count}" width="400" height="400"></canvas>                                                                
                                                                    </div>
                                                                    <div id="bigram${status.count}">
                                                                        <canvas id="bigramChart${status.count}" width="400" height="400"></canvas>
                                                                    </div>
                                                                    <div id="trigram${status.count}">
                                                                        <canvas id="trigramChart${status.count}" width="400" height="400"></canvas>
                                                                    </div>
                                                                </div>
                                                                <div id="beginWORDCLOUD${status.count}" class="col s12">
                                                                    <canvas id="beginCanvas${status.count}" width='640' height='480' style='border:1px solid #000000;'></canvas>   
                                                                </div>
                                                            </div>
                                                        </span>
                                                    </div>
                                                </li>
                                            </c:forEach>
                                            <li onclick="tfIdfCharts('${tfidfWord}', 'tfWord', 'Palavras')">
                                                <div class="collapsible-header article-header relevantTerms"><i class="material-icons right more">expand_more</i>Termos Relevantes</div>  
                                                <div class="collapsible-body">
                                                    <ul class="tabs tabs-fixed-width">
                                                        <li class="tab col s3"><a href="#word${status.count}">Palavra</a></li>
                                                        <li class="tab col s3" onclick="tfIdfCharts('${tfidfBigram}', 'tfBigram', 'Bigramas')"><a href="#bigram${status.count}">Bigrama</a></li>
                                                        <li class="tab col s3" onclick="tfIdfCharts('${tfidfTrigram}', 'tfTrigram', 'Trigramas')"><a href="#trigram${status.count}">Trigrama</a></li>
                                                    </ul>
                                                    <div id="word${status.count}">
                                                        <div class="row">
                                                            <div class="col s12">
                                                                <canvas id="tfWord" width="400" height="400"></canvas>
                                                             </div>
                                                        </div>
                                                        <div class="row">
                                                            <div class="col s12">
                                                                <div class="chartjs-legend" id="chartjs-legend-tfWord" class="noselect"></div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div id="bigram${status.count}">
                                                        <div class="row">
                                                            <div class="col s12">
                                                                <canvas id="tfBigram" width="400" height="400"></canvas>
                                                             </div>
                                                        </div>
                                                        <div class="row">
                                                            <div class="col s12">
                                                                <div class="chartjs-legend" id="chartjs-legend-tfBigram" class="noselect"></div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div id="trigram${status.count}">
                                                        <div class="row">
                                                            <div class="col s12">
                                                                <canvas id="tfTrigram" width="400" height="400"></canvas>
                                                             </div>
                                                        </div>
                                                        <div class="row">
                                                            <div class="col s12">
                                                                <div class="chartjs-legend" id="chartjs-legend-tfTrigram" class="noselect"></div>
                                                            </div>
                                                        </div> 
                                                    </div>                       
                                                </div>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                                        
                            <div id="resultadoAnalise"></div>           
                                        
                        </div>
                </div>
                </div>
                <div id="configs" class="col s4" style="display: none;">
                    <div class="card grey lighten-4">
                        <div class="card-content">
                            <span class="card-title">Filtros</span>
                        </div>
                        <div class="card-content white">
                            <ul class="collapsible" data-collapsible="accordion">
                                <li>
                                    <div class="collapsible-header">Agrupamento</div>
                                    <div class="collapsible-body">
                                            <div class="input-field">
                                                <select id="forma" name="forma">
                                                    <option value="text" selected>Texto inteiro</option>
                                                    <option value="abstract">Resumo</option>
                                                    <option value="objective">Objetivo</option>
                                                    <option value="methodology">Metodologia</option>
                                                    <option value="conclusion">Resultados</option>
                                                </select>
                                                <label>Forma de agrupamento</label>
                                            </div>
                                            <div class="row">
                                                <div class="input-field">
                                                  <input type="number" id="grupos" name="grupos" min="2" max="${fn:length(segmentos_artigos)-1}" value="2" class="validate"  />
                                                  <label for="grupos">Quantidade de grupos</label>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <button class="btn waves-effect waves-light col s12 orange-button-small" onclick="agrupar()">AGRUPAR
                                                    <i class="material-icons right">send</i>
                                                </button>
                                            </div>
                                            <input type="hidden" name="id" value="${projeto.id}"/>
                                    </div>
                                </li>
                                <li>
                                    <div class="collapsible-header menu">Ordenação</div>
                                    <div class="collapsible-body">
                                        <span>
                                            <div class="input-field">
                                                <select id="selectPorOrdenacao">
                                                    <option value="" disabled selected>Escolha uma opção</option>
                                                    <option value="text">Artigo</option>
                                                    <option value="abstract">Resumo</option>
                                                    <option value="objective">Objetivo</option>
                                                    <option value="methodology">Metodologia</option>
                                                    <option value="conclusion">Resultado</option>
                                                </select>
                                                <label>Por</label>
                                            </div>
                                            <br/>
                                            <div class="input-field">
                                                <div class="chips keywords"></div>
                                                <label>Keywords</label>
                                            </div>
                                             <div class="row">
                                                <button class="btn waves-effect waves-light col s12 orange-button-small" id="btnOrdenar" name="action">Ordenar
                                                    <i class="material-icons right">send</i>
                                                </button>
                                            </div>
                                        </span>
                                    </div>
                                </li>
                            </ul>
                            <div class="center">
                                <a href="#" onclick="limparFiltros()">Limpar filtros</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        <!--</div>-->
            
        <div id="loadando" class="loadando" style="display: none; z-index: 9999;">
            <div class="preloader-wrapper big active" style="position: absolute; left: 50%; top: 50%;">
                <div class="spinner-layer spinner-blue-only">
                    <div class="circle-clipper left">
                        <div class="circle"></div>
                    </div><div class="gap-patch">
                        <div class="circle"></div>
                    </div><div class="circle-clipper right">
                        <div class="circle"></div>
                    </div>
                </div>
            </div>
        </div>
                                        
        <div id="modalAdicionarArtigo" class="modal">
            <div class="modal-content">
                <h4>Adicionar artigo</h4>
                <div id="uploader"></div>
            </div>
            <div class="modal-footer">
                <a href="#!" class="modal-action modal-close waves-effect waves-light btn-flat">Voltar</a>
                <a class="modal-action modal-close waves-effect waves-light btn-flat green accent-4">Pronto</a>
            </div>
        </div>
                                        
        <form:form action="${pageContext.request.contextPath}/projetos/artigos/deletar" method="post">
            <div id="modalDeletarArtigo" class="modal">
                <div class="modal-content">
                    <h4>Deletar artigo</h4>
                    <input type="hidden" name="id" />
                    <input type="hidden" name="projeto_id" value="${projeto.id}" />
                    <p>Tem certeza que deseja deletar este artigo?</p>
                </div>
                <div class="modal-footer">
                    <a href="#!" class="modal-action modal-close waves-effect waves-light btn-flat">Voltar</a>
                    <button type="submit" class="waves-effect waves-red btn red">Deletar</button>
                </div>
            </div>
        </form:form>
                                        
        <div id="modalVizualizarArtigo" class="modal">
            <div class="modal-content">
                <h4>Vizualizar artigo</h4>
                <iframe width="100%" height="480"></iframe>
            </div>
            <div class="modal-footer">
                <a href="#!" class="modal-action modal-close waves-effect waves-light btn-flat">Voltar</a>
            </div>
        </div>
        
        <div id="modalSinonimosObjetivo" class="modal">
            <div class="modal-content">
                <h4>Sinônimos objetivo</h4>
                <div class="input-field">
                    <div class="chips sinonimosObjetivo"></div>
                </div>
            </div>
            <div class="modal-footer">
                <a href="#!" class="waves-effect waves-light btn-flat" onclick="sinonimosPadroes(1)">Padrões</a>
                <a href="#!" class="modal-action modal-close waves-effect waves-light btn-flat">Voltar</a>
            </div>
        </div>
        
        <div id="modalSinonimosMetodologia" class="modal">
            <div class="modal-content">
                <h4>Sinônimos metodologia</h4>
                <div class="input-field">
                    <div class="chips sinonimosMetodologia"></div>
                </div>
            </div>
            <div class="modal-footer">
                <a href="#!" class="waves-effect waves-light btn-flat" onclick="sinonimosPadroes(2)">Padrões</a>
                <a href="#!" class="modal-action modal-close waves-effect waves-light btn-flat">Voltar</a>
            </div>
        </div>
        
        <div id="modalSinonimosResultado" class="modal">
            <div class="modal-content">
                <h4>Sinônimos resultado</h4>
                <div class="input-field">
                    <div class="chips sinonimosResultado"></div>
                </div>
            </div>
            <div class="modal-footer">
                <a href="#!" class="waves-effect waves-light btn-flat" onclick="sinonimosPadroes(3)">Padrões</a>
                <a href="#!" class="modal-action modal-close waves-effect waves-light btn-flat">Voltar</a>
            </div>
        </div>
        
        <script type="text/javascript" src="${pageContext.request.contextPath}/node_modules/jquery/dist/jquery.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/node_modules/datatables.net/js/jquery.dataTables.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/node_modules/materialize-css/dist/js/materialize.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/loading.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/node_modules/wordcloud/src/wordcloud2.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/node_modules/chart.js/dist/Chart.bundle.min.js"></script>
        <script type="text/javascript">
            var nofilter = "";
            $(document).ready(function(){
                
                $(".button-collapse").sideNav();
                
                $(".dropdown-button").dropdown({
                    belowOrigin: true
                });
                
                $('.modal').modal();
                
                $('select').material_select();
                
                $('.collapsible').collapsible();
                
                $('.chips').material_chip();
                
                //SINONIMOS
                var sinonimosObjetivo = JSON.parse('${sinonimosObjetivoJson}');
                var sinonimosMetodologia = JSON.parse('${sinonimosMetodologiaJson}');
                var sinonimosResultado = JSON.parse('${sinonimosResultadoJson}');
                var data = new Object({data:[]});
                sinonimosObjetivo.forEach(function (v, k) {
                    data.data.push({
                        tag: v
                    });
                });
                $('.chips.sinonimosObjetivo').material_chip(data);
                
                data = new Object({data:[]});
                sinonimosMetodologia.forEach(function (v, k) {
                    data.data.push({
                        tag: v
                    });
                });
                $('.chips.sinonimosMetodologia').material_chip(data);
                
                data = new Object({data:[]});
                sinonimosResultado.forEach(function (v, k) {
                    data.data.push({
                        tag: v
                    });
                });
                $('.chips.sinonimosResultado').material_chip(data);
                
                $('#tabMaster').tabs({
                    onShow: function(e) {
                        var tab = e[0].id;
                        
                        if (tab === 'analise') {
                            //$('#containao').addClass('vaiprolado');
                            $('#containao').removeClass("offset-l2");
                            $('#configs').show();
                        } else {
                            //$('#containao').removeClass('vaiprolado');
                            $('#containao').addClass("offset-l2");
                            $('#configs').hide();
                        }
                    } 
                });
                
                $('#tableArtigos').DataTable({
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
                
                $('#tableUsuariosArtigo').DataTable({
                    "language": lang,
                    "dom": 'Brtip',
                    "columnDefs": [
                        { 
                            "width": "1%",
                            "targets": 2,
                            "orderable": false,
                            "searchable": false
                        }
                    ]
                });
                
                $('textarea#observacoes').characterCounter();
                
                nofilter = $('#analise').html();
                
                $('#formEditarProjeto').submit(function (e) {
                    var form = this;
                    
                    var sinObj = $('.chips.sinonimosObjetivo').material_chip('data');
                    var sinMet = $('.chips.sinonimosMetodologia').material_chip('data');
                    var sinRes = $('.chips.sinonimosResultado').material_chip('data');
                    
                    sinObj.forEach(function (v, k) {
                        $(form).append(
                            $('<input>')
                                .attr('type', 'hidden')
                                .attr('name', 'sinonimosObjetivo')
                                .val(v.tag)
                        );
                    });
                    
                    sinMet.forEach(function (v, k) {
                        $(form).append(
                            $('<input>')
                                .attr('type', 'hidden')
                                .attr('name', 'sinonimosMetodologia')
                                .val(v.tag)
                        );
                    });
                    
                    sinRes.forEach(function (v, k) {
                        $(form).append(
                            $('<input>')
                                .attr('type', 'hidden')
                                .attr('name', 'sinonimosResultado')
                                .val(v.tag)
                        );
                    });
                });
            });
            
            function resetTabs() {
                $('#tabMaster').tabs({
                    onShow: function(e) {
                        var tab = e[0].id;
                        
                        if (tab === 'analise') {
                            //$('#containao').addClass('vaiprolado');
                            $('#containao').removeClass("offset-s2");
                            $('#configs').show();
                        } else {
                            //$('#containao').removeClass('vaiprolado');
                            $('#containao').addClass("offset-s2");
                            $('#configs').hide();
                        }
                    } 
                });
            }
            
            function reload() {
                location = window.location.href;
            }
            
            function visualizarArtigo(id) {
                var contextPath = '${pageContext.request.contextPath}';
                $('#modalVizualizarArtigo iframe').attr('src', contextPath + "/projetos/artigos/visualizar?id=" + id); 
                $('#modalVizualizarArtigo').modal('open');
            }
            
            function deletarArtigo(id) {
                $('#modalDeletarArtigo input[name="id"]').val(id);
                $('#modalDeletarArtigo').modal('open');
                $('#tableArtigos').load(document.URL +  ' #tableArtigos');
            }
            
            function nuvem(words, num) {
                var lista = words.split(";");
                var list = [["" , ""]];
                for(i=0;i<lista.length;i+=2){       
                    list.push([lista[i], lista[i+1]]);
                }
                list.splice(0,1);
                WordCloud(document.getElementById("beginCanvas" + num), { list: list } );
            }
            
            function tfIdfCharts(tfs, id, type) {
                var list = tfs.split(";");
                var tfObj = new Array();
                for(i = 0; i < list.length - 1; i+=5) {
                    var newObj = {
                        "id": list[i],
                        "word": list[i+1],
                        "count": parseInt(list[i+2]),
                        "total": parseInt(list[i+3]),
                        "tfidf": parseFloat(list[i+4])
                    };
                    tfObj.push(newObj);
                }
                
                var words = new Array();
                tfObj.forEach((obj) => {
                   words.push(obj.word); 
                });
                
                var counts = new Array();
                tfObj.forEach((obj) => {
                   counts.push(obj.count); 
                });

                var ids = new Array();
                tfObj.forEach((obj) => {
                   ids.push(obj.id); 
                });
                
                var idsUnique = [...new Set(ids)];
                
                var bgColors = [
                                'rgba(255, 99, 132, 0.8)',
                                'rgba(54, 162, 235, 0.8)',
                                'rgba(255, 206, 86, 0.8)',
                                'rgba(75, 192, 192, 0.8)',
                                'rgba(153, 150, 255, 0.8)',
                                'rgba(255, 159, 64, 0.8)',
                                'rgba(150, 200, 50, 0.8)',
                                'rgba(150, 50, 250, 0.8)',
                                'rgba(100, 150, 200, 0.8)',
                                'rgba(50, 150, 250, 0.8)'
                            ];
                
                var legendColorsBase = {};
                for (let i = 0; i < idsUnique.length; i++) {
                    legendColorsBase[idsUnique[i]] = bgColors[i];
                }

                var legendColors = new Array();
                for (let i = 0; i < ids.length; i++) {
                    legendColors.push(legendColorsBase[ids[i]]);
                }
                       
                var ctx = document.getElementById(id).getContext('2d');
                window.myChart = new Chart(ctx, {
                    type: 'bar',
                    data: {
                        labels: words,
                        datasets: [{
                            label: 'Contador',
                            data: counts,
                            backgroundColor: legendColors,
                            borderColor: legendColors,
                            borderWidth: 1
                        }]
                    },
                    options: {
                        title: {
                            display: true,
                            fontSize: 24,
                            text: 'Principais ' + type,
                        },
                        scales: {
                            yAxes: [{
                                ticks: {
                                    beginAtZero:true
                                }
                            }]
                        },
                        legend: false,
                        legendCallback: function(chart) {
                            var text = [];
                            text.push('<ul class="' + chart.id + '-legend">');
                            for (var i = 0; i < idsUnique.length; i++) {
                              text.push('<li><span style="background-color:' + Object.values(legendColorsBase)[i] + '">');
                              if (idsUnique[i]) {
                                text.push(idsUnique[i]);
                              }
                              text.push('</span></li>');
                            }
                            text.push('</ul>');
                            return text.join("");
                        }
                    }
                });
                $('#chartjs-legend-'+id).html(myChart.generateLegend());
            }
            
            function mainWordsChart(words, id) {
                var list = words.split(";");
                var word = new Array();
                var count = new Array();
                for(i = 0; i < list.length - 1; i+=2) {
                    word.push(list[i]);
                    count.push(parseInt(list[i+1]));
                }
                
                var ctx = document.getElementById(id).getContext('2d');
                var myChart = new Chart(ctx, {
                    type: 'bar',
                    data: {
                        labels: word,
                        datasets: [{
                            label: 'Contador',
                            data: count,
                            backgroundColor: [
                                'rgba(255, 99, 132, 0.2)',
                                'rgba(54, 162, 235, 0.2)',
                                'rgba(255, 206, 86, 0.2)',
                                'rgba(75, 192, 192, 0.2)',
                                'rgba(153, 102, 255, 0.2)',
                                'rgba(255, 159, 64, 0.2)',
                                'rgba(150, 200, 50, 0.2)',
                                'rgba(150, 50, 250, 0.2)',
                                'rgba(100, 150, 200, 0.2)',
                                'rgba(50, 150, 250, 0.2)'
                            ],
                            borderColor: [
                                'rgba(255,99,132,1)',
                                'rgba(54, 162, 235, 1)',
                                'rgba(255, 206, 86, 1)',
                                'rgba(75, 192, 192, 1)',
                                'rgba(153, 102, 255, 1)',
                                'rgba(255, 159, 64, 1)',
                                'rgba(150, 200, 50, 1)',
                                'rgba(150, 50, 250, 1)',
                                'rgba(100, 150, 200, 1)',
                                'rgba(50, 150, 250, 1)'
                            ],
                            borderWidth: 1
                        }]
                    },
                    options: {
                        scales: {
                            yAxes: [{
                                ticks: {
                                    beginAtZero:true
                                }
                            }]
                        }
                    }
                });
            }
            
            function limparFiltros() {
                $('#analise').html(nofilter);
                $('ul.tabs').tabs();
                $('.collapsible').collapsible();
            }
            
            function sinonimosPadroes(segmento) {
                var defaultSinonimosObjetivo = ["ambition", "aspiration", "intent", "purpose", "propose", "mission", "target", "desing", "object", "end in view", "ground zero", "wish", "goal", " aim ", " mind ", "meaning", " mark ", " gaol ", "final ", "reach"];
                var defaultSinonimosMetodologia = [" mode ", "procedure", "technique", "approach", "channels", "design", "manner", " plan ", "practice", "process", "program", " way ", "method", "conduct", "measure", "operation", "proceeding", "scheme", "strategy", "step", " form ", "arrangement"];
                var defaultSinonimosResultado = ["closure", "complet", "consequen", "denouement", "development", "ending", "result", "culmination", "finaliz", "fulfillment", "windup", "outcome", "conclu", "reaction", "achievement", "attainment", "realization", "succes"];

                if (segmento === 1) {
                    var data = new Object({data:[]});
                    defaultSinonimosObjetivo.forEach(function (v, k) {
                        data.data.push({
                            tag: v
                        });
                    });
                    $('.chips.sinonimosObjetivo').material_chip(data);
                } else if (segmento === 2) {
                    var data = new Object({data:[]});
                    defaultSinonimosMetodologia.forEach(function (v, k) {
                        data.data.push({
                            tag: v
                        });
                    });
                    $('.chips.sinonimosMetodologia').material_chip(data);
                } else if (segmento === 3 ) {
                    var data = new Object({data:[]});
                    defaultSinonimosResultado.forEach(function (v, k) {
                        data.data.push({
                            tag: v
                        });
                    });
                    $('.chips.sinonimosResultado').material_chip(data);
                }
            }
            
            $('#btnOrdenar').click(function () {
                var segment = $('#selectPorOrdenacao').val();
                var keywords = $('.chips.keywords').material_chip('data');
                
                $.ajax({
                    url: "${pageContext.request.contextPath}/projetos/ordenar",
                    type:'get',
                    dataType: 'json',
                    data: {
                        "projeto": ${projeto.id},
                        "segment": segment,
                        "keywords": JSON.stringify(keywords)
                    },
                    success: function(artigos) {
                        $('#analise').html("");
                        var htmlao = "<div class='row'>" +
                                                "<div class='col s12'>" +
                                                    "<ul class='collapsible' data-collapsible='accordion'>";
                        artigos.forEach(function(v, k) {
                            var idx = k + 1;
                            htmlao += '<li>' +
                                        '<div class="collapsible-header"><h5>' + idx + ' - ' + v.nome + '</h5></div>' +
                                        '<div class="collapsible-body">' +
                                            '<span>' +
                                                '<div class="row">' +
                                                    '<div class="col s12">' +
                                                        '<ul class="tabs tabs-fixed-width">' +
                                                            '<li class="tab col s3"><a class="active" href="#Segmentos' + idx + '">Segmentos</a></li>' +
                                                            '<li class="tab col s3"><a href="#TF' + idx + '">TF</a></li>' +
                                                            '<li class="tab col s3"><a href="#ordWORDCLOUD' + idx + '">WORDCLOUD</a></li>' +
                                                        '</ul>' +
                                                    '</div>' +
                                                    '<div id="Segmentos' + idx + '" class="col s12">' +
                                                        '<h6>Resumo</h6>' +
                                                        '<ul class="collapsible" data-collapsible="accordion">' +
                                                            '<li>' +
                                                                '<div class="collapsible-header abstract"> Abstract </div>' +
                                                                '<div class="collapsible-body"><span>' + v.resumo + '</span></div>' +
                                                            '</li>' +
                                                        '</ul>' +
                                                        '<h6>Segmentos</h6>' +
                                                        '<ul class="collapsible" data-collapsible="accordion">' +
                                                            '<li>' +
                                                                '<div class="collapsible-header objective">Objetivo</div>' +
                                                                '<div class="collapsible-body"><span>' + v.objetivo + '</span></div>' +
                                                            '</li>' +
                                                            '<li>' +
                                                                '<div class="collapsible-header methodology">Metodologia</div>' +
                                                                '<div class="collapsible-body"><span>' + v.metodologia + '</span></div>' +
                                                            '</li>' +
                                                            '<li>' +
                                                                '<div class="collapsible-header conclusion">Resultado</div>' +
                                                                '<div class="collapsible-body"><span>' + v.resultado + '</span></div>' +
                                                            '</li>' +
                                                        '</ul>' +
                                                    '</div>' +
                                                    "<div id='TF" + idx + "' class='col s12'><img src='data:image/jpg;base64," + v.imgWord + "'/></div>" +
                                                    '<div id="ordWORDCLOUD' + idx + '" class="col s12">';
                                                    htmlao += "<canvas id='ordCanvas" + idx + "' width='640' height='480' style='border:1px solid #000000;'></canvas></div>" +
                                                '</div>' +
                                            '</span>' +
                                        '</div>' +
                                    '</li>';
                        });
                        htmlao += "</ul></div></div>";
                        $('#analise').html(htmlao);
                        artigos.forEach(function(v, k) {
                            var num = k+1;
                            var lista = (v.mainWords).split(" ");
                            var list = [["" , ""]];
                            for(i=0; i<lista.length; i+=2){    
                                list.push([lista[i], lista[i+1]]);
                            }
                            list.splice(0,1);
                            WordCloud(document.getElementById("ordCanvas" + num), { list: list } );
                        });
                        $('ul.tabs').tabs();
                        $('.collapsible').collapsible();
                        resetTabs();//Pensar numa funçãozinha melhor pra reloadar tudo ja q essa merda buga
                    },
                    error: function(erro) {
                        console.log(erro);
                    }
                });
            });
            
            $("#grupos").keypress(function (evt) {
                    evt.preventDefault();
            });
            
            //SCROLL/FOLLOW
            var $sidebar   = $("#configs"), 
                $window    = $(window),
                offset     = $sidebar.offset(),
                topPadding = 15;

            $window.scroll(function() {
                if ($window.scrollTop() > offset.top + 75) {
                    $sidebar.stop().animate({
                        marginTop: $window.scrollTop() - 75// - offset.top + topPadding
                    });
                } else {
                    $sidebar.stop().animate({
                        marginTop: 0
                    });
                }
            });
                        
            function agrupar() {
                var grupos = $('#grupos').val();
                var forma = $('#forma').val();
                $.ajax({
                    url: "${pageContext.request.contextPath}/projetos/artigos/agrupar",
                    type:'get',
                    data: {
                        "grupos": grupos,
                        "forma": forma,
                        "id": ${projeto.id}
                    },
                    success: function(grupos) {
                        $('#analise').html("");
                        var htmlao = "<div class='row'>" +
                                                "<div class='col s12'>" +
                                                    "<ul class='collapsible' data-collapsible='accordion'>";                                    
                        grupos = JSON.parse(grupos);
                        console.log(grupos);
                        cont = 1;
                        grupos.forEach(function(v, k) {
                            htmlao += "<li>" +
                                            "<div class='collapsible-header'><h5>GRUPO " + v.numero + "</h5></div>" +
                                                "<div class='collapsible-body'>" + 
                                                    "<span>" + 
                                                       "<div class='row'>" +
                                                            "<div class='col s12'>" +
                                                                "<ul class='tabs tabs-fixed-width'>" +
                                                                    "<li class='tab col s3'><a class='active' href='#Artigos" + v.numero + "'>ARTIGOS</a></li>" +
                                                                    "<li class='tab col s3'><a href='#Wordcloud" + v.numero + "'>WORDCLOUD</a></li>" +
                                                                "</ul>" +
                                                            "</div>" +
                                                            "<div id='Artigos" + v.numero + "' class='col s12'>";
                                                            htmlao += "<ul class='collapsible' data-collapsible='accordion'>";  
                                                            (v.artigos).forEach(function(t,l) {
                                                                htmlao += "<li>";
                                                                    htmlao += "<div class='collapsible-header'>" + t.nome + "</div>";
                                                                    htmlao += "<div class='collapsible-body'>" + 
                                                                                    "<span>" + 
                                                                                    "<div class='row'>" + 
                                                                                        "<div class='col s12'>";
                                                                                            htmlao += "<ul class='tabs tabs-fixed-width'>" +
                                                                                                "<li class='tab col s3'><a class='active' href='#Segmentos" + cont + "'>Segmentos</a></li>" +
                                                                                                "<li class='tab col s3'><a href='#TF" + cont + "'>TF</a></li>" +
                                                                                                "<li class='tab col s3'><a href='#groupWORDCLOUD" + cont + "'>WORDCLOUD</a></li>" +
                                                                                            "</ul></div>";
                                                                                            htmlao += "<div id='Segmentos" + cont + "' class='col s12'>" +
                                                                                                '<h6>Resumo</h6>' +
                                                                                                '<ul class="collapsible" data-collapsible="accordion">' +
                                                                                                    '<li>' +
                                                                                                        '<div class="collapsible-header abstract"> Abstract </div>' +
                                                                                                        '<div class="collapsible-body"><span>' + t.resumo + '</span></div>' +
                                                                                                    '</li>' +
                                                                                                '</ul>' +
                                                                                                '<h6>Segmentos</h6>' +
                                                                                                '<ul class="collapsible" data-collapsible="accordion">' +
                                                                                                    '<li>' +
                                                                                                        '<div class="collapsible-header objective">Objetivo</div>' +
                                                                                                        '<div class="collapsible-body"><span>' + t.objetivo + '</span></div>' +
                                                                                                    '</li>' +
                                                                                                    '<li>' +
                                                                                                        '<div class="collapsible-header methodology">Metodologia</div>' +
                                                                                                        '<div class="collapsible-body"><span>' + t.metodologia + '</span></div>' +
                                                                                                    '</li>' +
                                                                                                    '<li>' +
                                                                                                        '<div class="collapsible-header conclusion">Resultado</div>' +
                                                                                                        '<div class="collapsible-body"><span>' + t.resultado + '</span></div>' +
                                                                                                    '</li>' +
                                                                                                '</ul>' +
                                                                                               "</div>";
                                                                                       htmlao += "<div id='TF" + cont + "' class='col s12'><img src='data:image/jpg;base64," + t.imgWord + "'/></div>" +
                                                                                               "<div id='groupWORDCLOUD" + cont + "' class='col s12'><canvas id='groupCanvas" + cont + "' width='640' height='480' style='border:1px solid #000000;'></canvas></div>";
                                                                htmlao += "</div></span></div></li>";
                                                                cont++;
                                                            });
                                                            htmlao += "</ul>";
                                                            htmlao += "</div>" +
                                                            "<div id='Wordcloud" + v.numero + "' class='col s12'>";
                                                            htmlao += "<canvas id='myCanvas" + v.numero + "' width='640' height='480' style='border:1px solid #000000;'></canvas>";
                                                     htmlao += "</div></div></span>" +
                                                    "</div>" +
                                                "</li>";
                            });
                            htmlao += "</ul></div></div>";
                            $('#analise').html(htmlao);
                            var num = 1;
                            grupos.forEach(function(v, k) { <!--CONSTRUÇÃO WORDCLOUD-->
                                var list = [["" , ""]];
                                (v.keywords).forEach(function(t, l) {
                                    list.push([t, 100]);
                                });
                                list.splice(0,1);
                                WordCloud(document.getElementById("myCanvas" + v.numero), { list: list } );
                                (v.artigos).forEach(function(g, h) {
                                    var lista = (g.mainWords).split(" ");
                                    var list2 = [["" , ""]];
                                    for(i=0; i<lista.length; i+=2){    
                                        list2.push([lista[i], lista[i+1]]);
                                    }
                                    list2.splice(0,1);
                                    WordCloud(document.getElementById("groupCanvas" + num), { list: list2 } );
                                    num++;
                                });
                            });
                            $('ul.tabs').tabs();
                            $('.collapsible').collapsible();
                    },
                    error: function(erro) {
                        console.log(erro);
                    }
                });
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
        <script type="text/javascript" src="${pageContext.request.contextPath}/node_modules/fine-uploader/fine-uploader/fine-uploader.min.js"></script>
        <script type="text/template" id="qq-template">
            <div class="qq-uploader-selector qq-uploader" qq-drop-area-text="Arraste seus artigos aqui">
                <div class="qq-total-progress-bar-container-selector qq-total-progress-bar-container">
                    <div role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" class="qq-total-progress-bar-selector qq-progress-bar qq-total-progress-bar"></div>
                </div>
                <div class="qq-upload-drop-area-selector qq-upload-drop-area" qq-hide-dropzone>
                    <span class="qq-upload-drop-area-text-selector"></span>
                </div>
                <div class="qq-upload-button-selector qq-upload-button">
                    <div>Inserir um artigo</div>
                </div>
                <span class="qq-drop-processing-selector qq-drop-processing">
                    <span>Processing dropped files...</span>
                    <span class="qq-drop-processing-spinner-selector qq-drop-processing-spinner"></span>
                </span>
                <ul class="qq-upload-list-selector qq-upload-list" aria-live="polite" aria-relevant="additions removals">
                    <li>
                        <div class="qq-progress-bar-container-selector">
                            <div role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" class="qq-progress-bar-selector qq-progress-bar"></div>
                        </div>
                        <span class="qq-upload-spinner-selector qq-upload-spinner"></span>
                        <img class="qq-thumbnail-selector" qq-max-size="100" qq-server-scale>
                        <span class="qq-upload-file-selector qq-upload-file"></span>
                        <span class="qq-edit-filename-icon-selector qq-edit-filename-icon" aria-label="Edit filename"></span>
                        <input class="qq-edit-filename-selector qq-edit-filename" tabindex="0" type="text">
                        <span class="qq-upload-size-selector qq-upload-size"></span>
                        <button type="button" class="qq-btn qq-upload-cancel-selector qq-upload-cancel">Cancel</button>
                        <button type="button" class="qq-btn qq-upload-retry-selector qq-upload-retry">Retry</button>
                        <button type="button" class="qq-btn qq-upload-delete-selector qq-upload-delete">Delete</button>
                        <span role="status" class="qq-upload-status-text-selector qq-upload-status-text"></span>
                    </li>
                </ul>

                <dialog class="qq-alert-dialog-selector">
                    <div class="qq-dialog-message-selector"></div>
                    <div class="qq-dialog-buttons">
                        <button type="button" class="qq-cancel-button-selector">Close</button>
                    </div>
                </dialog>

                <dialog class="qq-confirm-dialog-selector">
                    <div class="qq-dialog-message-selector"></div>
                    <div class="qq-dialog-buttons">
                        <button type="button" class="qq-cancel-button-selector">No</button>
                        <button type="button" class="qq-ok-button-selector">Yes</button>
                    </div>
                </dialog>

                <dialog class="qq-prompt-dialog-selector">
                    <div class="qq-dialog-message-selector"></div>
                    <input type="text">
                    <div class="qq-dialog-buttons">
                        <button type="button" class="qq-cancel-button-selector">Cancel</button>
                        <button type="button" class="qq-ok-button-selector">Ok</button>
                    </div>
                </dialog>
            </div>
        </script>
        <script>
            var uploader = new qq.FineUploader({
                element: document.getElementById("uploader"),
                request: {
                    endpoint: "${pageContext.request.contextPath}/projetos/artigos/adicionar",
                    params: {
                        projeto: '${projeto.id}'
                    }
                },
                success: true,
                callbacks: {
                    onComplete: function(id, name, response) {
                        if (response.success) {
                            $('#tableArtigos').load(document.URL +  ' #tableArtigos');
                        }
                    }
                }
                //autoUpload: false,
                //text: {
                //    uploadButton: '<i class="icon-plus icon-white"></i> Select Files'
                //}
            });
        </script>
    </body>
</html>
