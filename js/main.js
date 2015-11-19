/**
 * Created by gandrejc on 18.11..
 */
var EXPENSE = {
    init: function (){
        EXPENSE.closePopup();
        EXPENSE.openPopup();
        EXPENSE.handleTableOpenClose();



    },
    openPopup: function(){
        $(".open-connected-orders").click(function(){
            $("#connected-order").load("connected-order.html");
            $("#connected-order").toggleClass("hidden");

        });
        $(".open-order").click(function(){
            $("#connected-order").load("view-order.html");
            $("#connected-order").toggleClass("hidden");
        });
        $(".open-connected").click(function(){
            $("#connected").toggleClass("hidden");
        });

        $("#new-order").click(function(){
            $("#add-new-popup").toggleClass("hidden");
        });
    },
    closePopup: function(){
        $(".close-popup").click(function(){
            $(this).closest(".background-popup").toggleClass("hidden");
        });
        //for those that are dynamically added from another file
        $("#connected-order").on("click", ".glyphicon-remove-sign", function(){
            $(this).closest(".background-popup").toggleClass("hidden");
            $("#connected-order").empty();
        });
    },
    handleTableOpenClose: function() {
        $("tr[class*='collapsable']").next().slideToggle();

        $("tr").click(function(event) {
            var $target = $(event.target);
            var closesTr = $target.closest("tr[class*='collapsable']");
            closesTr.toggleClass("info");
            closesTr.find("i[class*='glyphicon']").toggleClass("glyphicon-chevron-down glyphicon-chevron-up");
            closesTr.next().slideToggle();

        });

        //for those that are dynamicall added from file

        $("#connected-order").on("click", "tr", function(){
            var $target = $(event.target);
            var closesTr = $target.closest("tr[class*='collapsable']");
            closesTr.toggleClass("info");
            closesTr.find("i[class*='glyphicon']").toggleClass("glyphicon-chevron-down glyphicon-chevron-up");
            closesTr.next().slideToggle();
        });
    }
};

/* RUN ON READY */
$(document).ready(function() {

    EXPENSE.init();

});

/* RUN WINDOW LOAD */
$(document).load(function() {

});