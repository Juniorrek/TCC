$(document).bind("ajaxSend", function(){
   $("#loadando").show();
 }).bind("ajaxComplete", function(){
   $("#loadando").hide();
 });