/**
 * Created by gandrejc on 18.11..
 */
var EXPENSE = {
    init: function (){
        EXPENSE.closePopup();
        EXPENSE.openPopup();
        EXPENSE.handleTableOpenClose();
        EXPENSE.userInteractionHandler();



    },
    openPopup: function(){
        $(".open-connected-orders").click(function(){
            $("#connected-order").load("connected-order.html");
            $("#connected-order").toggleClass("hidden");

        });
        $(".open-order").click(function(){
            $("#connected-order").load("../view-order.html");
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
    },
    userInteractionHandler: function() {
            $("#addNewService").click(function(){
                var table = $("#orderServices");
                var inputService = "<input type='text' class='tableInput serviceInput' placeholder='Tip storitve'</input>";
                var inputPrice = "<input type='number' class='tableInput priceInput' placeholder='Cena'</input>";
                var inputNotes = "<input type='text' class='noteInput' placeholder='Opombe'</input>";
                var addBtn = '<button type="button" class="btn-xs btn-success glyphicon glyphicon-plus float-right insertService"></button>'
                var inputFieldsHtml = "<tr id='addingEntry'> <td>"+inputService+"</td> <td>"+inputPrice+"</td> <td>"+inputNotes+addBtn+"</td> </tr>";
                table.append(inputFieldsHtml);
            });
            $("#orderServices").on("click", ".insertService", function(){
                var table = $("#orderServices");
                var service = $(".serviceInput").val();
                var price = $(".priceInput").val();
                var note = $(".noteInput").val();
                $("#addingEntry").remove();
                var htmlEntry = '<tr> <td>'+service+'</td> <td>'+price+'</td> <td>'+note+'</td> </tr>'
                table.append(htmlEntry);
            })



    }
};

/* RUN ON READY */
$(document).ready(function() {

    EXPENSE.init();

});

/* RUN WINDOW LOAD */
$(document).load(function() {

});