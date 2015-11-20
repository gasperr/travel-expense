/**
 * Created by gandrejc on 18.11..
 */
var EXPENSE = {
    googleAutocomplete: "" ,

    init: function () {
        EXPENSE.closePopup();
        EXPENSE.openPopup();
        EXPENSE.handleTableOpenClose();
        EXPENSE.userInteractionHandler();
        EXPENSE.tableFiltering();
        EXPENSE.googleAutoComplete();
        EXPENSE.managmentInteractionHandler();


    },
    openPopup: function () {
        $(".open-connected-orders").click(function () {
            $("#connected-order").load("connected-order.html");
            $("#connected-order").toggleClass("hidden");

        });
        $(".open-request").click(function () {
            $("#open-request-popup").load("../view-request.html");
            $("#open-request-popup").toggleClass("hidden");

        });
        $(".open-order").click(function () {
            $("#connected-order").load("../view-order.html");
            $("#connected-order").toggleClass("hidden");
        });
        $(".open-connected").click(function () {
            $("#connected").toggleClass("hidden");
        });

        $("#new-order").click(function () {
            $("#add-new-popup").toggleClass("hidden");
        });
    },
    closePopup: function () {
        $(".close-popup").click(function () {
            $(this).closest(".background-popup").toggleClass("hidden");
        });
        //for those that are dynamically added from another file
        $("#connected-order").on("click", ".glyphicon-remove-sign", function () {
            $(this).closest(".background-popup").toggleClass("hidden");
            $("#connected-order").empty();
        });
        $("#open-request-popup").on("click", ".glyphicon-remove-sign", function () {
            $(this).closest(".background-popup").toggleClass("hidden");
            $("#open-request-popup").empty();
        });
    },
    handleTableOpenClose: function () {
        $("tr[class*='collapsable']").next().slideToggle();

        $("tr").click(function (event) {
            var $target = $(event.target);
            var closesTr = $target.closest("tr[class*='collapsable']");
            closesTr.toggleClass("info");
            closesTr.find("i[class*='glyphicon']").toggleClass("glyphicon-chevron-down glyphicon-chevron-up");
            closesTr.next().slideToggle();

        });

        //for those that are dynamicall added from file

        $("#connected-order").on("click", "tr", function () {
            var $target = $(event.target);
            var closesTr = $target.closest("tr[class*='collapsable']");
            closesTr.toggleClass("info");
            closesTr.find("i[class*='glyphicon']").toggleClass("glyphicon-chevron-down glyphicon-chevron-up");
            closesTr.next().slideToggle();
        });
    },
    userInteractionHandler: function () {
        $("#addNewService").click(function () {
            var table = $("#orderServices");
            var inputService = "<input type='text' class='tableInput serviceInput' placeholder='Tip storitve'</input>";
            var inputPrice = "<input type='number' class='tableInput priceInput' placeholder='Cena'</input>";
            var inputNotes = "<input type='text' class='noteInput' placeholder='Opombe'</input>";
            var addBtn = '<button type="button" class="btn-xs btn-success glyphicon glyphicon-plus float-right insertService"></button>'
            var inputFieldsHtml = "<tr id='addingEntry'> <td>" + inputService + "</td> <td>" + inputPrice + "</td> <td>" + inputNotes + addBtn + "</td> </tr>";
            table.append(inputFieldsHtml);
        });
        $("#orderServices").on("click", ".insertService", function () {
            var table = $("#orderServices");
            var service = $(".serviceInput").val();
            var price = $(".priceInput").val();
            var note = $(".noteInput").val();

            var valid = true;
            if(!service){
                valid = false;
                $(".serviceInput").addClass("non-valid-input").attr("placeholder", "Obvezno polje");
            }
            if(!price){
                valid = false;
                $(".priceInput").addClass("non-valid-input").attr("placeholder", "Obvezno polje");
            }
            if(valid){
                $("#addingEntry").remove();
                var htmlEntry = '<tr> <td>' + service + '</td> <td>' + price + '</td> <td>' + note + '</td> </tr>'
                table.append(htmlEntry);
            }
        });
        $("#editOrder").click(function () {
            $(this).toggleClass("disabled");
            var addButtonTo = $("#orderServices").parent(".content-table.section");

            $("#orderServices").find("td").each(function () {
                var value = $(this).html();
                if (isNaN(value)) {
                    $(this).html('<input type="text" class="tableInput serviceInput adding" value="' + value + '"></input>');
                } else {
                    $(this).html('<input type="number" class="tableInput priceInput adding" value="' + value + '"></input>');
                }
            });

            addButtonTo.append('<button type="button" class="btn-xs btn-info glyphicon glyphicon-ok completeEdit float-right"></button>');
        });
        $("#orderServices").parent(".content-table.section").on("click", ".completeEdit", function(){
            $("#orderServices").find(".adding").each(function () {
                var value = $(this).val();
                $(this).parent().html(value);
            });
            $(".completeEdit").remove();
            $("#editOrder").toggleClass("disabled");
        });
        $(".trash-request").click(function(){
           $(this).closest("tr").empty();
        });

        $("#add-new-popup").on("click", "#complete-new-request", function(){
            $(".add-new").each(function(){

                if(!$(this).val()){
                    $(this).addClass("non-valid-input").attr("placeholder", "Obvezno polje");
                }
            })

        })
    },
    managmentInteractionHandler: function(){
        $(".approve-mngmt").click(function(){
           $(this).fadeOut("slow", function(){
               var replc = $('<button type="button" class="approve-mngmt btn btn-sm btn-success disabled"><i class="glyphicon glyphicon-ok"></i> </button>');
               $(this).replaceWith(replc);

           });

            setTimeout(function(){
                console.log($(this));
                $($("button[class*='disabled']").parent().parent()).hide();
            }, 1500);

        });
    },
    tableFiltering: function(){
        //user dashboard status table filtering
        $(".request-filter-user").click(function(){
            //get values
            var allFilters = $(this).parent().parent().find("input[class*='request-filter-user']");
            var zero = $(allFilters[0]).is(":checked");
            var one = $(allFilters[1]).is(":checked");
            var two = $(allFilters[2]).is(":checked");

            $(".section.status table tbody tr").each(function(){
               if($(this).attr("status-data") == 0){
                    zero ? $(this).removeClass("hidden"):$(this).addClass("hidden");
               }
               else if($(this).attr("status-data") == 1){
                    one ? $(this).removeClass("hidden"):$(this).addClass("hidden");
               }else{
                   two ? $(this).removeClass("hidden"):$(this).addClass("hidden");
               }
            });

        });
        //my offers user
        $(".order-filter-user").click(function(){
            //get values
            var allFilters = $(this).parent().parent().find("input[class*='order-filter-user']");
            var zero = $(allFilters[0]).is(":checked");
            var one = $(allFilters[1]).is(":checked");
            var two = $(allFilters[2]).is(":checked");
            var three = $(allFilters[3]).is(":checked");

            $(this).next().toggleClass("unselected");

            $(".section.status table tbody tr").each(function(){
                if($(this).attr("status-data") == 0){
                    zero ? $(this).removeClass("hidden"):$(this).addClass("hidden");
                }
                else if($(this).attr("status-data") == 1){
                    one ? $(this).removeClass("hidden"):$(this).addClass("hidden");
                }else if($(this).attr("status-data") == 2){
                    two ? $(this).removeClass("hidden"):$(this).addClass("hidden");
                }else if($(this).attr("status-data") == 3){
                    three ? $(this).removeClass("hidden"):$(this).addClass("hidden");
                }
            });

        });

        //managment filter by keyword
        $(".filterTableString").keypress(function(){
            var val = $(this).val().toLowerCase();
            var table = $(this).attr("id");

            $("table[class*='"+table+"'] tbody tr").each(function(){
                if(!($(this).html().toLowerCase().indexOf(val) > -1)){
                    $(this).addClass("hidden");
                }else{
                    $(this).removeClass("hidden");
                }
            });
        });
    },
    googleAutoComplete: function(){
        if(document.getElementById('location') != null){
            EXPENSE.googleAutocomplete = new google.maps.places.Autocomplete((document.getElementById('location')), { types: ['geocode'] });
        }
    }
};


/* RUN ON READY */
$(document).ready(function () {

    EXPENSE.init();

    jQuery('.toggle-nav').click(function(e) {
        jQuery(this).toggleClass('active');
        jQuery('.menu ul').toggleClass('active');

        e.preventDefault();
    });

});

/* RUN WINDOW LOAD */
$(document).load(function () {

});