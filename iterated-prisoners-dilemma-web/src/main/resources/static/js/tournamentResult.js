window.onload = function () {

    google.charts.load('current', {'packages': ['bar']});
    google.charts.setOnLoadCallback(drawChart);

    function drawChart() {
        var dataGson = $('#data').attr('data-str');
        let strats = JSON.parse(dataGson);
        var matrix = [];
        matrix.unshift(["Strategy", "Score"]);

        strats.forEach(function (element) {
            var result = [];
            result[0] = (element.name);
            result[1] = (element.score);
            matrix.push(result);
        });

        var data = new google.visualization.arrayToDataTable(matrix);

        var options = {
            title: 'Strategy Scores',
            legend: {position: 'none'},
            axes: {
                x: {
                    0: {side: 'bottom', label: 'Strategies'}
                },
                y: {
                    0: {label: 'Score'}
                },
            },
            bar: {groupWidth: "80%"},
            vAxis: {
                format: 'decimal',
                label: 'Score'
            },
            hAxis: {}
        };

        var chart = new google.charts.Bar(document.getElementById('top_x_div'));
        // Convert the Classic options to Material options.
        chart.draw(data, google.charts.Bar.convertOptions(options));
    }
};