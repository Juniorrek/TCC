<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <title>Index</title>
        
        <link rel="stylesheet" href="${pageContext.request.contextPath}/node_modules/material-design-icons-iconfont/dist/fonts/material-icons.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/node_modules/materialize-css/dist/css/materialize.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/style.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/index.css">
        <link href="https://fonts.googleapis.com/css?family=Asap" rel="stylesheet">
        <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/resources/icons/favicon.ico">
    </head>
    <body class="background-image">
        <header>
            <nav class="blue blue-darken-1">
                <div class="container nav-wrapper">
                    <a href="${pageContext.request.contextPath}/" class="brand-logo">
                        <img class="responsive-img" style="height: 60px;" src="${pageContext.request.contextPath}/resources/images/artemisLogo.png" />
                    </a>
                    <a href="#" data-activates="mobile-menu" class="button-collapse"><i class="material-icons">menu</i></a>
                    <ul class="right hide-on-med-and-down">
                      <li><a href="${pageContext.request.contextPath}/login">Login</a></li>
                      <li><a href="${pageContext.request.contextPath}/cadastro" class="btn white" style="color: #000;">Cadastro</a></li>
                    </ul>
                    <ul class="side-nav" id="mobile-menu">
                      <li><a href="${pageContext.request.contextPath}/login">Login</a></li>
                      <li><a href="${pageContext.request.contextPath}/cadastro">Cadastro</a></li>
                    </ul>
                </div>
            </nav>
        </header>

            <div class="carousel carousel-slider center" data-indicators="true">
              <div class="carousel-fixed-item">
                <a class="btn waves-effect mais-button" onclick="nextCarousel()"><i class="material-icons right">arrow_forward</i>MAIS</a>
              </div>
              <div class="carousel-item transparent" href="#one!">
                    <div class="flex-container">
                        <div class="container-item">
                                <h4 class="functionality-title">Artemis é uma ferramenta WEB de análise de artigos acadêmicos que tem como objetivo facilitar o processo de revisão de literatura</h4>
                                <h5 class="functionality-desc">Clique no botão abaixo e descubra como nossas funcionalidades podem te ajudar</h5>
<!--                            <div class="row col s12" style="display: block;">
                                <a href="${pageContext.request.contextPath}/cadastro" class="btn blue blue-darken-1">Cadastro</a>
                                <p style="margin: 10px; display: inline-block;">ou</p>
                                <a href="#" class="btn blue blue-darken-1">Saiba mais</a>
                            </div>-->
                        </div>
                        <div class="flex-container">
                            <img class="responsive-img" src="${pageContext.request.contextPath}/resources/images/reverseArtemisLogo.png" />
                        </div>
                    </div>
              </div>
                        
              <div class="carousel-item transparent white-text" href="#two!">
                <h2 class="functionality-title">Extração de fragmentos dos artigos</h2>
                <p class="functionality-desc">
                    Cada artigo é divido em texto completo, abstract, introdução, metodologia e conclusão. Dessa forma
                    é mais prático verificar o conteúdo e as abordagens presentes em cada artigo
                </p>
                
                <div class="functionality-container">
                    
                    <div class="img-container">
                        <img src="${pageContext.request.contextPath}/resources/images/abstract-pdf.png" />
                    </div>
                    
                    <div class="arrow-container">
                        <i class="large material-icons">arrow_forward</i>
                    </div>
                    
                    <div class="img-container">
                        <img src="${pageContext.request.contextPath}/resources/images/abstract-app.png" />
                    </div>
                                 
                </div>
                
              </div>
                        
              <div class="carousel-item transparent white-text" href="#three!">
                <h2 class="functionality-title">Termos relevantes</h2>
                <p class="functionality-desc">
                    Os termos mais relaventes de cada artigo são mostrados de acordo com seu conteúdo e o conteúdo dos outros artigos envolvidos na análise
                </p>
                
                <div class="functionality-container">
                    
                    <div class="img-container">
                        <img src="${pageContext.request.contextPath}/resources/images/articles-list.png" />
                    </div>
                    
                    <div class="arrow-container">
                        <i class="large material-icons">arrow_forward</i>
                    </div>
                    
                    <div class="img-container">
                        <img src="${pageContext.request.contextPath}/resources/images/mainly-words.png" />
                    </div>
                                 
                </div>
                
              </div>
                        
              <div class="carousel-item transparent white-text" href="#four!">
                <h2 class="functionality-title">Ordenação</h2>
                <p class="functionality-desc">
                    Artigos podem ser ordenados por relevância de acordo com palavras inseridas pelo usuário
                </p>

                <div class="functionality-container">
                    
                    <div class="img-container">
                        <img src="${pageContext.request.contextPath}/resources/images/sort-menu.png" />
                    </div>
                    
                    <div class="arrow-container">
                        <i class="large material-icons">arrow_forward</i>
                    </div>
                    
                    <div class="img-container">
                        <img src="${pageContext.request.contextPath}/resources/images/sort-result.png" />
                    </div>
                                 
                </div>

              </div>
                        
              <div class="carousel-item transparent white-text" href="#five!">
                <h2 class="functionality-title">Agrupamento</h2>
                <p class="functionality-desc">
                    Artigos podem ser dividos em grupos de acordo com seu conteúdo
                </p>
                
                <div class="functionality-container">
                    
                    <div class="img-container">
                        <img src="${pageContext.request.contextPath}/resources/images/groups-menu.png" />
                    </div>
                    
                    <div class="arrow-container">
                        <i class="large material-icons">arrow_forward</i>
                    </div>
                    
                    <div class="img-container">
                        <img src="${pageContext.request.contextPath}/resources/images/groups-result.png" />
                    </div>
                                 
                </div>
                    
              </div>
                        
              <div class="carousel-item transparent white-text" href="#six!">
                <h2 class="functionality-title">Projetos compartilhados</h2>
                <p class="functionality-desc">
                    Múltiplas pessoas podem trabalhar em um mesmo projeto, podendo fazer diferentes análises sobre um mesmo artigo
                </p>
                
                <div class="functionality-container">
                    
                    <div class="img-container">
                        <img src="${pageContext.request.contextPath}/resources/images/articles.png" />
                    </div>
                    
                    <div class="arrow-container">
                        <i class="large material-icons">arrow_forward</i>
                    </div>
                    
                    <div class="img-container">
                        <img src="${pageContext.request.contextPath}/resources/images/comment.png" />
                    </div>
                                 
                </div>

              </div>
                    
            </div>
   
                    
        <script type="text/javascript" src="${pageContext.request.contextPath}/node_modules/jquery/dist/jquery.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/node_modules/materialize-css/dist/js/materialize.js"></script>
        <script type="text/javascript">
            $(document).ready(function(){
                $(".button-collapse").sideNav();
//                carouselFillScreen();
            });
            
            $('.carousel.carousel-slider').carousel({fullWidth: true, dist: 0});
            
            $( window ).resize(function() {
//                carouselFillScreen();
            });
            
            function carouselFillScreen() {
                var headerHeight = $('header').height();
                $('.carousel.carousel-slider').css("height", window.innerHeight - headerHeight);
            }
            
            function nextCarousel() {
                $('.carousel.carousel-slider').carousel('next');
            }
        </script>
    </body>
</html>
