 

$(document).ready(function (){
    $('.inTextControlled').focus(function(){
    
        $('.inTextControlled').val("") ;
    });

    $('.inTextControlled').blur(function (){
        $('.inTextControlled').val("Patient ID")
    });
//$("paddingRemover").removeClass('ui-accordion-content') ;
//$('paddingRemover').addClass('custom-accordion-ui-content') ;
    
/*$('.fieldCleared').focus(function(){
    
        $('.fieldCleared').val("") ;
    });*/
}) ;


 
 