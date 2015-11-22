/**
 * Created by gandrejc on 18.11..
 */
var EXPENSE = {
    googleAutocomplete: "",

    freqData: [
        {Month:'JAN',freq:{seminarji:4786, pot:1319, zivljenje:249}}
        ,{Month:'FEB',freq:{seminarji:1101, pot:412, zivljenje:674}}
        ,{Month:'MAR',freq:{seminarji:932, pot:2149, zivljenje:418}}
        ,{Month:'APR',freq:{seminarji:832, pot:1152, zivljenje:1862}}
        ,{Month:'MAY',freq:{seminarji:4481, pot:3304, zivljenje:948}}
        ,{Month:'JUN',freq:{seminarji:1619, pot:167, zivljenje:1063}}
        ,{Month:'JUL',freq:{seminarji:1819, pot:247, zivljenje:1203}}
        ,{Month:'AVG',freq:{seminarji:4498, pot:3852, zivljenje:942}}
        ,{Month:'SEPT',freq:{seminarji:797, pot:1849, zivljenje:1534}}
        ,{Month:'OCT',freq:{seminarji:162, pot:379, zivljenje:471}}
        ,{Month:'NOV',freq:{seminarji:162, pot:379, zivljenje:471}}
        ,{Month:'DEC',freq:{seminarji:162, pot:379, zivljenje:471}}
    ],

    init: function () {
        EXPENSE.closePopup();
        EXPENSE.openPopup();
        EXPENSE.handleTableOpenClose();
        EXPENSE.userInteractionHandler();
        EXPENSE.tableFiltering();
        EXPENSE.googleAutoComplete();
        EXPENSE.managmentInteractionHandler();
        EXPENSE.homeRedirect();
        EXPENSE.financeInteractionHandler();



        $('#dodajDemoLogin').change(function () {
            $("#kreirajSporocilo").html("");
            var podatki = $(this).val().split(",");
            $("#upime").val(podatki[0]);
            $("#inputPassword3").val(podatki[1]);
        });
    },
    homeRedirect: function () {
        $("#home").click(function () {
            var user_type = localStorage.getItem("user_type");
            console.log(user_type);
            window.location.href = user_type + "/dashboard.html";
        })
    },

    login: function () {
        var username = $("#upime").val();
        var password = $("#inputPassword3").val();

        var upimena = ["Peter Demolis", "Rok Nerovac", "Ana Konda"];
        var gesla = ["managment", "user", "finance"]


        for (var i in upimena) {
            if (upimena[i] === username && gesla[i] === password) {
                window.location.href = "views/"+gesla[i] + "/dashboard.html";

                localStorage.setItem('user_type', password);
                return true;
            }
        }
        $("#inputPassword3").val("");
        $("#passGroup").removeClass("form-group").addClass("form-group has-error");
        $("#usernameGroup").removeClass("form-group").addClass("form-group has-error");
        $("#kreirajSporocilo").html("<span class='obvestilo label label-danger fade-in'>Uporabnisko ime in geslo se bodisi ne ujemaata ali pa uporabnik ne obstaja.</span>");

        return false;

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

        $(".send-to-review li a").click(function(e){
            console.log(e);
            $("#to-review").load("send-to-review.html");
            $("#to-review").toggleClass("hidden");
            e.preventDefault();
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
        $("#to-review").on("click", ".glyphicon-remove-sign", function () {
            $(this).closest(".background-popup").toggleClass("hidden");
            $("#to-review").empty();
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
            if (!service) {
                valid = false;
                $(".serviceInput").addClass("non-valid-input").attr("placeholder", "Obvezno polje");
            }
            if (!price) {
                valid = false;
                $(".priceInput").addClass("non-valid-input").attr("placeholder", "Obvezno polje");
            }
            if (valid) {
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
        $("#orderServices").parent(".content-table.section").on("click", ".completeEdit", function () {
            $("#orderServices").find(".adding").each(function () {
                var value = $(this).val();
                $(this).parent().html(value);
            });
            $(".completeEdit").remove();
            $("#editOrder").toggleClass("disabled");
        });
        $(".trash-request").click(function (e) {
            $(this).closest("tr").empty();
            e.preventDefault();
        });

        $("#add-new-popup").on("click", "#complete-new-request", function () {
            $(".add-new").each(function () {

                if (!$(this).val()) {
                    $(this).addClass("non-valid-input").attr("placeholder", "Obvezno polje");
                }
            })

        });

        $("#readqr").click(function(){
            //try with: https://www4.uwm.edu/sois/qrcode/images/qrcode_bsisit_large_1.gif
            $("#qr-camera-div").toggleClass("hidden").promise().done(function(){
                $(".qr-button > button").addClass("disabled");
                $('#qr-reader').html5_qrcode(function(data){
                        $(".qr-result").html(data);
                        $(".qr-button > button").removeClass("disabled");


                    },
                    function(error){
                        $(".qr-result").html(error);
                        if(!$(".qr-button > button").hasClass("disabled")){
                            $(".qr-button > button").addClass("disabled");
                        }
                    }, function(videoError){
                        $(".qr-result").html(videoError);
                        if(!$(".qr-button > button").hasClass("disabled")){
                            $(".qr-button > button").addClass("disabled");
                        }
                    }
                );
            });

            $(".qr-button > button").click(function(){
                if(!$(this).hasClass("disabled")){
                    var table = $("#orderServices");
                    var qr_read = $(".qr-result").html();
                    var htmlEntry = '<tr> <td>' + qr_read + '</td> <td>N/A</td> <td>QR Auto Read</td> </tr>';
                    table.append(htmlEntry)
                    $('#qr-reader').html5_qrcode_stop();
                    $(this).closest(".background-popup").toggleClass("hidden");
                }
            });

            $(".close-qr").click(function(){
                $('#qr-reader').html5_qrcode_stop();
                $(this).closest(".background-popup").toggleClass("hidden");
            });

        });
    },
    managmentInteractionHandler: function () {
        $(".approve-mngmt").click(function () {
            $(this).fadeOut("slow", function () {
                var replc = $('<button type="button" class="approve-mngmt btn btn-sm btn-success disabled"><i class="glyphicon glyphicon-ok"></i> </button>');
                $(this).replaceWith(replc);

            });

            setTimeout(function () {
                console.log($(this));
                $($("button[class*='disabled']").parent().parent()).hide();
            }, 1500);

        });
    },
    financeInteractionHandler: function () {

        $(".left.finance > .content-inner").fadeOut("slow");
        $(".left.finance > .content-inner").fadeIn("slow");

        $(".glyphicon-refresh").click(function () {
            $(this).addClass("box_rotate box_transition");
            setTimeout(function () {
                $(".glyphicon-refresh").removeClass("box_rotate box_transition");
            }, 1000);
        });

    },

    tableFiltering: function () {
        //user dashboard status table filtering
        $(".request-filter-user").click(function () {
            //get values
            var allFilters = $(this).parent().parent().find("input[class*='request-filter-user']");
            var zero = $(allFilters[0]).is(":checked");
            var one = $(allFilters[1]).is(":checked");
            var two = $(allFilters[2]).is(":checked");

            $(".section.status table tbody tr").each(function () {
                if ($(this).attr("data-status") == 0) {
                    zero ? $(this).removeClass("hidden") : $(this).addClass("hidden");
                }
                else if ($(this).attr("data-status") == 1) {
                    one ? $(this).removeClass("hidden") : $(this).addClass("hidden");
                } else {
                    two ? $(this).removeClass("hidden") : $(this).addClass("hidden");
                }
            });

        });
        //my offers user
        $(".order-filter-user").click(function () {
            //get values
            var allFilters = $(this).parent().parent().find("input[class*='order-filter-user']");
            var zero = $(allFilters[0]).is(":checked");
            var one = $(allFilters[1]).is(":checked");
            var two = $(allFilters[2]).is(":checked");
            var three = $(allFilters[3]).is(":checked");

            $(this).next().toggleClass("unselected");

            $(".section.status table tbody tr").each(function () {
                if ($(this).attr("data-status") == 0) {
                    zero ? $(this).removeClass("hidden") : $(this).addClass("hidden");
                }
                else if ($(this).attr("data-status") == 1) {
                    one ? $(this).removeClass("hidden") : $(this).addClass("hidden");
                } else if ($(this).attr("data-status") == 2) {
                    two ? $(this).removeClass("hidden") : $(this).addClass("hidden");
                } else if ($(this).attr("data-status") == 3) {
                    three ? $(this).removeClass("hidden") : $(this).addClass("hidden");
                }
            });

        });

        //managment filter by keyword
        $(".filterTableString").keypress(function () {
            var val = $(this).val().toLowerCase();
            var table = $(this).attr("id");

            $("table[class*='" + table + "'] tbody tr").each(function () {
                if (!($(this).html().toLowerCase().indexOf(val) > -1)) {
                    $(this).addClass("hidden");
                } else {
                    $(this).removeClass("hidden");
                }
            });
        });
    },
    googleAutoComplete: function () {
        if (document.getElementById('location') != null) {
            EXPENSE.googleAutocomplete = new google.maps.places.Autocomplete((document.getElementById('location')), {types: ['geocode']});
        }
    },
    statisticsExpense: function(id, fData){

            var barColor = 'steelblue';
            function segColor(c){ return {seminarji:"#807dba", pot:"#e08214",zivljenje:"#41ab5d"}[c]; }

            // compute total for each state.
            fData.forEach(function(d){d.total=d.freq.seminarji+d.freq.pot+d.freq.zivljenje;});

            // function to handle histogram.
            function histoGram(fD){
                var hG={},    hGDim = {t: 60, r: 0, b: 30, l: 0};
                hGDim.w = 500 - hGDim.l - hGDim.r,
                    hGDim.h = 300 - hGDim.t - hGDim.b;

                //create svg for histogram.
                var hGsvg = d3.select(id).append("svg")
                    .attr("width", hGDim.w + hGDim.l + hGDim.r)
                    .attr("height", hGDim.h + hGDim.t + hGDim.b).append("g")
                    .attr("transform", "translate(" + hGDim.l + "," + hGDim.t + ")");

                // create function for x-axis mapping.
                var x = d3.scale.ordinal().rangeRoundBands([0, hGDim.w], 0.1)
                    .domain(fD.map(function(d) { return d[0]; }));

                // Add x-axis to the histogram svg.
                hGsvg.append("g").attr("class", "x axis")
                    .attr("transform", "translate(0," + hGDim.h + ")")
                    .call(d3.svg.axis().scale(x).orient("bottom"));

                // Create function for y-axis map.
                var y = d3.scale.linear().range([hGDim.h, 0])
                    .domain([0, d3.max(fD, function(d) { return d[1]; })]);

                // Create bars for histogram to contain rectangles and freq labels.
                var bars = hGsvg.selectAll(".bar").data(fD).enter()
                    .append("g").attr("class", "bar");

                //create the rectangles.
                bars.append("rect")
                    .attr("x", function(d) { return x(d[0]); })
                    .attr("y", function(d) { return y(d[1]); })
                    .attr("width", x.rangeBand())
                    .attr("height", function(d) { return hGDim.h - y(d[1]); })
                    .attr('fill',barColor)
                    .on("mouseover",mouseover)// mouseover is defined below.
                    .on("mouseout",mouseout);// mouseout is defined below.

                //Create the frequency labels above the rectangles.
                bars.append("text").text(function(d){ return d3.format(",")(d[1])})
                    .attr("x", function(d) { return x(d[0])+x.rangeBand()/2; })
                    .attr("y", function(d) { return y(d[1])-5; })
                    .attr("text-anchor", "middle");

                function mouseover(d){  // utility function to be called on mouseover.
                    // filter for selected state.
                    var st = fData.filter(function(s){ return s.Month == d[0];})[0],
                        nD = d3.keys(st.freq).map(function(s){ return {type:s, freq:st.freq[s]};});

                    // call update functions of pie-chart and legend.
                    pC.update(nD);
                    leg.update(nD);
                }

                function mouseout(d){    // utility function to be called on mouseout.
                    // reset the pie-chart and legend.
                    pC.update(tF);
                    leg.update(tF);
                }

                // create function to update the bars. This will be used by pie-chart.
                hG.update = function(nD, color){
                    // update the domain of the y-axis map to reflect change in frequencies.
                    y.domain([0, d3.max(nD, function(d) { return d[1]; })]);

                    // Attach the new data to the bars.
                    var bars = hGsvg.selectAll(".bar").data(nD);

                    // transition the height and color of rectangles.
                    bars.select("rect").transition().duration(500)
                        .attr("y", function(d) {return y(d[1]); })
                        .attr("height", function(d) { return hGDim.h - y(d[1]); })
                        .attr("fill", color);

                    // transition the frequency labels location and change value.
                    bars.select("text").transition().duration(500)
                        .text(function(d){ return d3.format(",")(d[1])})
                        .attr("y", function(d) {return y(d[1])-5; });
                }
                return hG;
            }

            // function to handle pieChart.
            function pieChart(pD){
                var pC ={},    pieDim ={w:250, h: 250};
                pieDim.r = Math.min(pieDim.w, pieDim.h) / 2;

                // create svg for pie chart.
                var piesvg = d3.select(id).append("svg")
                    .attr("width", pieDim.w).attr("height", pieDim.h).append("g")
                    .attr("transform", "translate("+pieDim.w/2+","+pieDim.h/2+")");

                // create function to draw the arcs of the pie slices.
                var arc = d3.svg.arc().outerRadius(pieDim.r - 10).innerRadius(0);

                // create a function to compute the pie slice angles.
                var pie = d3.layout.pie().sort(null).value(function(d) { return d.freq; });

                // Draw the pie slices.
                piesvg.selectAll("path").data(pie(pD)).enter().append("path").attr("d", arc)
                    .each(function(d) { this._current = d; })
                    .style("fill", function(d) { return segColor(d.data.type); })
                    .on("mouseover",mouseover).on("mouseout",mouseout);

                // create function to update pie-chart. This will be used by histogram.
                pC.update = function(nD){
                    piesvg.selectAll("path").data(pie(nD)).transition().duration(500)
                        .attrTween("d", arcTween);
                }
                // Utility function to be called on mouseover a pie slice.
                function mouseover(d){
                    // call the update function of histogram with new data.
                    hG.update(fData.map(function(v){
                        return [v.Month,v.freq[d.data.type]];}),segColor(d.data.type));
                }
                //Utility function to be called on mouseout a pie slice.
                function mouseout(d){
                    // call the update function of histogram with all data.
                    hG.update(fData.map(function(v){
                        return [v.Month,v.total];}), barColor);
                }
                // Animating the pie-slice requiring a custom function which specifies
                // how the intermediate paths should be drawn.
                function arcTween(a) {
                    var i = d3.interpolate(this._current, a);
                    this._current = i(0);
                    return function(t) { return arc(i(t));    };
                }
                return pC;
            }

            // function to handle legend.
            function legend(lD){
                var leg = {};

                // create table for legend.
                var legend = d3.select(id).append("table").attr('class','legend');

                // create one row per segment.
                var tr = legend.append("tbody").selectAll("tr").data(lD).enter().append("tr");

                // create the first column for each segment.
                tr.append("td").append("svg").attr("width", '16').attr("height", '16').append("rect")
                    .attr("width", '16').attr("height", '16')
                    .attr("fill",function(d){ return segColor(d.type); });

                // create the second column for each segment.
                tr.append("td").text(function(d){ return d.type;});

                // create the third column for each segment.
                tr.append("td").attr("class",'legendFreq')
                    .text(function(d){ return d3.format(",")(d.freq);});

                // create the fourth column for each segment.
                tr.append("td").attr("class",'legendPerc')
                    .text(function(d){ return getLegend(d,lD);});

                // Utility function to be used to update the legend.
                leg.update = function(nD){
                    // update the data attached to the row elements.
                    var l = legend.select("tbody").selectAll("tr").data(nD);

                    // update the frequencies.
                    l.select(".legendFreq").text(function(d){ return d3.format(",")(d.freq);});

                    // update the percentage column.
                    l.select(".legendPerc").text(function(d){ return getLegend(d,nD);});
                }

                function getLegend(d,aD){ // Utility function to compute percentage.
                    return d3.format("%")(d.freq/d3.sum(aD.map(function(v){ return v.freq; })));
                }

                return leg;
            }

            // calculate total frequency by segment for all state.
            var tF = ['seminarji','pot','zivljenje'].map(function(d){
                return {type:d, freq: d3.sum(fData.map(function(t){ return t.freq[d];}))};
            });

            // calculate total frequency by state for all segment.
            var sF = fData.map(function(d){return [d.Month,d.total];});

            var hG = histoGram(sF), // create the histogram.
                pC = pieChart(tF), // create the pie-chart.
                leg= legend(tF);  // create the legend.

    },
    statisticsBasic: function(){
        var margin = {top: 20, right: 20, bottom: 30, left: 50},
            width = 960 - margin.left - margin.right,
            height = 500 - margin.top - margin.bottom;

        var parseDate = d3.time.format("%d-%b-%y").parse;

        var x = d3.time.scale()
            .range([0, width]);

        var y = d3.scale.linear()
            .range([height, 0]);

        var xAxis = d3.svg.axis()
            .scale(x)
            .orient("bottom");

        var yAxis = d3.svg.axis()
            .scale(y)
            .orient("left");

        var line = d3.svg.line()
            .x(function(d) { return x(d.date); })
            .y(function(d) { return y(d.close); });

        var svg = d3.select("#basic-chart").append("svg")
            .attr("width", width + margin.left + margin.right)
            .attr("height", height + margin.top + margin.bottom)
            .append("g")
            .attr("transform", "translate(" + margin.left + "," + margin.top + ")");

        d3.tsv("statistics-data/data.tsv", function(error, data) {
            if (error) throw error;

            data.forEach(function(d) {
                d.date = parseDate(d.date);
                d.close = +d.close;
            });

            x.domain(d3.extent(data, function(d) { return d.date; }));
            y.domain(d3.extent(data, function(d) { return d.close; }));

            svg.append("g")
                .attr("class", "x axis")
                .attr("transform", "translate(0," + height + ")")
                .call(xAxis);

            svg.append("g")
                .attr("class", "y axis")
                .call(yAxis)
                .append("text")
                .attr("transform", "rotate(-90)")
                .attr("y", 6)
                .attr("dy", ".71em")
                .style("text-anchor", "end")
                .text("Price ($)");

            svg.append("path")
                .datum(data)
                .attr("class", "line")
                .attr("d", line);
        });
    }
};


/* RUN ON READY */
$(document).ready(function () {

    EXPENSE.init();

    jQuery('.toggle-nav').click(function (e) {
        jQuery(this).toggleClass('active');
        jQuery('.menu ul').toggleClass('active');

        e.preventDefault();
    });
    jQuery('.menu.navbar ul li').click(function (e) {
        jQuery('.menu ul').toggleClass('active');
    });

});

/* RUN WINDOW LOAD */
$(document).load(function () {

});