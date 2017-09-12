<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <title>Index</title>
        
        <link rel="stylesheet" href="${pageContext.request.contextPath}/node_modules/material-design-icons-iconfont/dist/fonts/material-icons.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/node_modules/materialize-css/dist/css/materialize.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/style.css">
        <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/resources/icons/favicon.ico">
    </head>
    <body>
        <header>
            <nav class="blue blue-darken-1">
                <div class="container nav-wrapper">
                    <a href="${pageContext.request.contextPath}/" class="brand-logo">Tritomus</a>
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
                    
        <div style="position: absolute; top: 20%; left: 30%;">
            <canvas id="myCanvas" width="640" height="480" style="border:1px solid #000000;"></canvas>
        </div>

        <script type="text/javascript" src="${pageContext.request.contextPath}/node_modules/jquery/dist/jquery.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/node_modules/materialize-css/dist/js/materialize.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/node_modules/wordcloud/src/wordcloud2.js"></script>
        <script type="text/javascript">
            $(document).ready(function(){
                $(".button-collapse").sideNav();
                
                var list = [['results',280],['method',160],['objective',160],['optimization',150],['mathematics',140],['algorithm',120],['number',120],['abstract',120],['function',120],['subcategory',100],['pdf',100],['graphs',100],['intersection',100],['constrained',100],['methodology',100],['problems',100],['category',100],['file',100],['quantum',90],['coordinated',80],['engineering',80],['nonlinear',80],['paper',80],['obtain',80],['geodesics',70],['information',70],['powell’s',60],['model',60],['two',60],['proposed',60],['numerical',60],['cliques',60],['bound',60],['induced',60],['closed',60],['free',60],['derivative',60],['show',60],['problem',50],['algorithms',50],['theory',40],['system',40],['space',40],['20],π',40],['programming',40],['group',40],['prove',40],['convergent',40],['approach',40],['decreasing',40],['therefore',40],['set',40],['globally',40],['box',40],['result',40],['norm',40],['self',40],['new',40],['uses',40],['linear',40],['applications',40],['quadratic',40],['formula',40],['estimated',40],['version',40],['article',40],['search',40],['matrix',40],['strategy',40],['introduce',40],['find',40],['rates',40],['based',40],['entropy',40],['classifications',40],['whose',40],['families',40],['present',40],['subgraphs',40],['forbidden',40],['minimal',40],['states',40],['maximum',40],['classic',40],['general',40],['non',40],['solve',40],['exponential',40],['underdetermined',40],['proved',40],['solving',40],['experiments',40],['numbers',40],['graph',40],['different',40],['normalized',40],['constraint',40],['well',40],['extended',40],['classifiers',30],['methods',30],['active',30],['unconstrained',30],['theorem',30],['unsupervised',30],['allow',30],['human',30],['intervention',30],['case',30],['violation',20],['terms',20],['efficiency',20],['robustness',20],['bi',20],['reduced',20],['integer',20],['mixed',20],['discrete',20],['deal',20],['subgraph',20],['scatter',20],['minimum',20],['colors',20],['assigned',20],['tabu',20],['way',20],['penalties',20],['multiobjective',20],['empty',20],['adaptation',20],['receive',20],['same',20],['color',20],['equal',20],['introduces',20],['common',20],['vertex',20],['previous',20],['work',20],['entangled',20],['characterized',20],['multipartite',20],['pure',20],['matrices',20],['density',20],['classes',20],['note',20],['partial',20],['spectra',20],['minimally',20],['admissisble',20],['cardinality',20],['grows',20],['exponentially',20],['inequalities',20],['vertices',20],['edges',20],['furthermore',20],['describe',20],['ideas',20],['generate',20],['similar',20],['concentration',20],['entanglement',20],['seems',20],['difficult',20],['compression',20],['source',20],['characterization',20],['follow',20],['discuss',20],['briefly',20],['connection',20],['without',20],['derivatives',20],['introduction',20],['[380],]',20],['performance',20],['each',20],['iteration',20],['brief',20],['interpolation',20],['give',20],['constructed',20],['around',20],['current',20],['iterate',20],['minimized',20],['will',20],['duality',20],['trial',20],['point',20],['whole',20],['process',20],['embedded',20],['trust',20],['region',20],['framework',20],['weyl',20],['infinity',20],['schur',20],['instead',20],['euclidean',20],['naturally',20],['subproblem',20],['arises',20],['types”',20],['explore',20],['faces',20],['“method',20],['easily',20],['compare',20],['implementation',20],['newuoa',20],['bobyqa',20],['theoretic',20],['respectively',20],['require',20],['classical',20],['functional',20],['evaluations',20],['analogue',20],['sequence',20],['symmetric',20],['representation',20],['elegantly',20],['bounded',20],['variation',20],['fourier',20],['multiplier',20],['besov',20],['quite',20],['connected',20],['important',20],['presented',20],['handling',20],['convergence',20],['global',20],['banach',20],['umdspace',20],['fukushima',20],['illustration',20],['applicability',20],['study',20],['solvability',20],['cauchy',20],['periodic',20],['boundary',20],['conditions',20],['standard',20],['including',20],['mathematical',20],['minimization',20],['structural',20],['eliminates',20],['desirable',20],['items',20],['data',20],['processing',20],['proposes',20],['classifier',20],['soft',20],['coloring',20],['tested',20],['li',20],['instances',20],['literature',20],['obtained',20],['compared',20],['lucidi',20],['made',20],['yielding',20],['good',20],['better',20],['supervised',20],['sometimes',20],['providing',20],['alternative',20],['considers',20],['additional',20],['humans',20],['lampariello',20],['considered',20],['grippo',20],['combines',20],['manipulations',20],['us',20],['line',20],['original',20],['christoffel',20],['darboux',20],['globalized',20],['interest',20],['update',20],['broyden',20],['algebra',20],['developments',20],['jensen’s',20],['inequality',20],['main',20]];
                WordCloud(document.getElementById('myCanvas'), { list: list } );
            });
        </script>
    </body>
</html>
