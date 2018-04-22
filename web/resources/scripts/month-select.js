/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
var month_select_overlay_target_Id ;
            var month_items_id = ["month-item-1","month-item-2", "month-item-3","month-item-4","month-item-5","month-item-6","month-item-7","month-item-8","month-item-9","month-item-10","month-item-11","month-item-12"] ;
            $(document).ready(function(){
                
                var months =  ["January","Febuary","March","April","May","June","July","August","September","October","November","December"] ;
                var selected_month  ;
                $.each(month_items_id,function(){
                    
                    $("#" + this).click(function(){
                        remove_ui_selected_class();
                        selected_month = months[parseInt($(this).val()) - 1];
                        $(this).addClass("ui-selected-mpit");
                        
                        var year = yearSelectMenuWidget.getSelectedValue();
                       
                        var month_year_str = selected_month + "-" + year ;
                        $(PrimeFaces.escapeClientId(month_select_overlay_target_Id )).val(month_year_str);
                    });
                });    
                
                
            });
            function set_month_overlay_targetId(id){
                    
                month_select_overlay_target_Id = id ;
            }
                
            function remove_ui_selected_class(){
                $.each(month_items_id,function(){
                    $("#" + this).removeClass("ui-selected-mpit");
                });
            }

