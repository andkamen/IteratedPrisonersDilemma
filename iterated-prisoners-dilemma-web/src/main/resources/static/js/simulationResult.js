window.onload = function () {

    google.charts.load('current', {'packages': ['corechart']});
    google.charts.setOnLoadCallback(drawChart);

    function drawChart() {
        var matrixGson = $('#matrix').attr('matrix-str');
        var stratsGson = $('#strats').attr('strats-str');
        var genCountGson = $('#genCount').attr('genCount-str');

        let strats = JSON.parse(stratsGson);
        let scores = JSON.parse(matrixGson);
        let genCount = JSON.parse(genCountGson);

        var data = new google.visualization.DataTable();

        strats.forEach(function (element) {
            data.addColumn('number', element)
        });
        data.addRows(scores);

        var options = {
            title: 'Population Distribution Over Generations',
            curveType: 'function',
            vAxis: {
                title: "Strategy Count",
                viewWindowMode: "explicit",
                viewWindow: {min: 0}
            },
            hAxis: {
                title: "Generation #",
                viewWindowMode: "explicit",
                viewWindow: {min: 0},
                format: '0',
                gridlines: {
                    count: genCount
                }
            }
        };

        var chart = new google.visualization.LineChart(document.getElementById('curve_chart'));

        chart.draw(data, options);
    }
};