var author;

$(document).ready(function() {
    var id = getUrlParameter('author-id');//alert(name);
    getRequest(
        '/author/getById?id=' + id,
        function (res) {
            author = res.content;
        },
        function (error) {
            alert(JSON.stringify(error));
        }
    );
    getRequest(
        '/author/rank?id=' + id+"&mode="+0,
        function (res) {
            var temp = res.content;
            $('#author-rank-sameOrg-paper').text(decodeURI(temp[0]));
            $('#author-rank-sameOrg-citation').text(decodeURI(temp[1]));
        },
        function (error) {
            alert(JSON.stringify(error));
        }
    );
    getRequest(
        '/author/rankData?id=' + id+"&mode="+0,
        function (res) {
            var authors = res.content;
            var markPoint=[];
            var index=authors[0][1];
            markPoint.push(authors[index][0]);markPoint.push(authors[index][1]);
            drawTable(authors.slice(1),"author-rank-sameOrg",markPoint,author.author_name);
        },
        function (error) {
            alert(JSON.stringify(error));
        }
    );

    getRequest(
        '/author/rank?id=' + id+"&mode="+1,
        function (res) {
            var temp = res.content;
            $('#author-rank-difOrg-paper').text(decodeURI(temp[0]));
            $('#author-rank-difOrg-citation').text(decodeURI(temp[1]));
        },
        function (error) {
            alert(JSON.stringify(error));
        }
    );
    getRequest(
        '/author/rankData?id=' + id+"&mode="+1,
        function (res) {
            var authors = res.content;
            var markPoint=[];
            var index=authors[0][1];
            markPoint.push(authors[index][0]);markPoint.push(authors[index][1]);
            drawTable(authors.slice(1),"author-rank-difOrg",markPoint,author.author_name);
        },
        function (error) {
            alert(JSON.stringify(error));
        }
    );
});
function drawTable(authors,position,markPoint,name) {
    var x=markPoint[0],y=markPoint[1];//alert(x);alert(y);
    var option = {

        xAxis: [
            {
                name:'论文数',
                type: 'value',
                scale: true,
                axisLabel: {
                    formatter: '{value}'
                }

            }
        ],
        yAxis: [
            {
                name:'被引用数',
                type: 'value',
                scale: true,
                axisLabel: {
                    formatter: '{value}'
                }
            }
        ],
        series: [
            {
                name: '学术排名',
                type: 'scatter',
                data: authors,
                markPoint: {
                    symbolSize:100,
                    itemStyle:{
                        normal:{
                            label:{
                                show: true,
                                color: '#ff1837',//气泡中字体颜色
                            }
                        }
                    },
                    data: [
                        {name:name},
                        {coord:[x,y]}
                    ]
                },
                symbolSize: 5,
                itemStyle: {
                    normal: {
                        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                            {offset: 0, color: '#90c81d'},
                            {offset: 0.5, color: '#2d8d26'},
                            {offset: 1, color: '#286e1b'}
                        ])
                    }

                }

            },

        ]
    };
    var myChart = echarts.init(document.getElementById(position));
    myChart.setOption(option);
}
function getUrlParameter(name){
    name = name.replace(/[]/,"\[").replace(/[]/,"\[").replace(/[]/,"\\\]");
    var regexS = "[\\?&]"+name+"=([^&#]*)";
    var regex = new RegExp( regexS );
    var results = regex.exec(window.parent.location.href );
    if( results == null ) return ""; else {
        return results[1];
    }
};