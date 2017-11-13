/* global toastr */

function groupsValidation () {
    var articlesCount = $('.article-header').length - 1;
    var groups = $('#grupos').val();
    
    if (articlesCount < 2) {
        toastr.error('É necessário 2 ou mais artigos para executar um agrupamento.');
        return false;
    }
    
    if (groups < 2) {
        toastr.error('Ao menos dois grupos devem ser formados.');
        return false;
    }
    
    return true;
}

function sortValidation () {
    var category = $('#selectPorOrdenacao').val();
    var sortWords = $('#sort-words').children().length - 1;
    
    if (category === null) {
        toastr.error('Uma categoria deve ser selecionada para executar a ordenação.');
        return false;
    }
    
    if (sortWords === 0) {
        toastr.error('Ao menos uma palavra deve ser inserida para executar a ordenação.');
        return false;
    }
    
    return true;
}