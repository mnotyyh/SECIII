var method=[];
var methodId;
$(document).ready(function() {
    GetInit();
    initSelect();

    var x=$('#method-select-2').val();
    var y=$('#method-select-3').val();
    var z=$('#method-select-4').val();
    playLine(x,y,z);

    /*
    getRequest(
        '/field/year_change',
        function (res) {
            var data = res.content;
            drawLine(data);
        },
        function (error) {
            alert(JSON.stringify(error));
        }
    );
*/
    getRequest(
        '/field/cloud',
        function (res) {
            var clouds = res.content;
            drawCloud(clouds);
        },
        function (error) {
            alert(JSON.stringify(error));
        }
    );

    function playLine(mode1,mode2,mode3) {
        getRequest(
            '/field/year_change?mode1='+mode1+'&mode2='+mode2,
            function (res) {
                var data = res.content;
                drawLine(data.names, data.years, data.nums,mode3);
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    }

    $('#method-select-2').change (function () {
        var x=$('#method-select-2').val();
        var y=$('#method-select-3').val();
        var z=$('#method-select-4').val();
        playLine(x,y,z);
    });
    $('#method-select-3').change (function () {
        var x=$('#method-select-2').val();
        var y=$('#method-select-3').val();
        var z=$('#method-select-4').val();
        playLine(x,y,z);
    });
    $('#method-select-4').change (function () {
        var x=$('#method-select-2').val();
        var y=$('#method-select-3').val();
        var z=$('#method-select-4').val();//alert(z);
        playLine(x,y,z);
    });

    function initSelect(){

        // 过滤条件变化后重新查询
        $('#method-select').change (function () {
            methodId=$(this).children('option:selected').val();
            getRating();
        });
    }
    function GetInit() {
        getRequest(
            '/field/getTop20?mode=1',
            function (res) {
                method = res.content||[];

                var tableData = method.map(function (item) {
                    return item.paper_num;
                });
                var nameList = method.map(function (item) {
                    return item.field_name;
                });
                var ids = method.map(function (item) {
                    return item.id;
                }).reverse();
                var option = {
                    title: {
                        text: '研究方向活跃度排行',
                        subtext: '数据来自学术网站'
                    },
                    tooltip: {
                        trigger: 'axis',
                        axisPointer: {
                            type: 'shadow'
                        }
                    },

                    grid: {
                        left: '3%',
                        right: '9%',
                        bottom: '3%',
                        containLabel: true
                    },
                    xAxis: {
                        name:'论文数',
                        nameTextStyle:{
                            color:'red'
                        },
                        type: 'value',
                        boundaryGap: [0, 0.01]
                    },
                    yAxis: {
                        name:'研究方向',
                        nameTextStyle:{
                            color:'red'
                        },
                        type: 'category',
                        data: nameList.reverse()
                    },
                    series: [
                        {
                            type: 'bar',
                            data: tableData.reverse(),
                            itemStyle: {
                                normal: {
                                    //这里是重点
                                    color: function(params) {
                                        //注意，如果颜色太少的话，后面颜色不会自动循环，最好多定义几个颜色
                                        var colors=['rgba(206,24,16,0.73)','rgba(205,125,18,0.69)','rgba(189,186,43,0.72)','rgba(135,198,29,0.75)',
                                            'rgba(58,192,141,0.74)','rgba(53,91,196,0.7)','rgba(30,30,189,0.64)','rgba(114,31,181,0.66)',
                                            'rgba(185,25,151,0.66)','rgba(185,127,154,0.63)','rgba(131,182,189,0.64)','rgba(169,109,181,0.66)',
                                            'rgba(185,163,113,0.66)','rgba(75,185,77,0.63)','rgba(108,128,189,0.64)','rgba(90,180,174,0.66)',
                                            'rgba(51,209,71,0.66)','rgba(110,81,185,0.63)','rgba(189,63,61,0.64)','rgba(11,29,49,0.66)'];
                                        return colors.reverse()[params.dataIndex]
                                    }
                                }
                            }
                        }
                    ]
                };
                var RateChart = echarts.init($("#field-rating")[0]);
                RateChart.setOption(option);
                RateChart.off('click');
                RateChart.on('click', function (params) {
                    var id=ids[params.dataIndex];
                    window.open("/view/field-detail?field-id="+id);

                });
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );

    }
    function getRating(){
        getRequest(
            '/field/getTop20?mode='+methodId,
            function (res) {
                method = res.content||[];

                var tableData = method.map(function (item) {
                    if(methodId==1)return item.paper_num;
                    if(methodId==2)return item.citation_sum;
                    if(methodId==3)return item.point;
                });
                var nameList = method.map(function (item) {
                    return item.field_name;
                });
                var ids = method.map(function (item) {
                    return item.id;
                }).reverse();
                var option = {
                    title: {
                        text: '研究方向活跃度排行',
                        subtext: '数据来自学术网站'
                    },
                    tooltip: {
                        trigger: 'axis',
                        axisPointer: {
                            type: 'shadow'
                        }
                    },

                    grid: {
                        left: '3%',
                        right: '4%',
                        bottom: '3%',
                        containLabel: true
                    },
                    xAxis: {
                        type: 'value',
                        boundaryGap: [0, 0.01]
                    },
                    yAxis: {
                        type: 'category',
                        data: nameList.reverse()

                    },
                    series: [
                        {
                            type: 'bar',
                            data: tableData.reverse(),
                            itemStyle: {
                                normal: {
                                    //这里是重点
                                    color: function(params) {
                                        //注意，如果颜色太少的话，后面颜色不会自动循环，最好多定义几个颜色
                                        var colors=['rgba(206,24,16,0.73)','rgba(205,125,18,0.69)','rgba(189,186,43,0.72)','rgba(135,198,29,0.75)',
                                            'rgba(58,192,141,0.74)','rgba(53,91,196,0.7)','rgba(30,30,189,0.64)','rgba(114,31,181,0.66)',
                                            'rgba(185,25,151,0.66)','rgba(185,127,154,0.63)','rgba(131,182,189,0.64)','rgba(169,109,181,0.66)',
                                            'rgba(185,163,113,0.66)','rgba(75,185,77,0.63)','rgba(108,128,189,0.64)','rgba(90,180,174,0.66)',
                                            'rgba(51,209,71,0.66)','rgba(110,81,185,0.63)','rgba(189,63,61,0.64)','rgba(11,29,49,0.66)'];
                                        return colors.reverse()[params.dataIndex]
                                    }
                                }
                            }
                        }
                    ]
                };
                var RateChart = echarts.init($("#field-rating")[0]);
                RateChart.setOption(option);
                RateChart.off('click');
                RateChart.on('click', function (params) {
                    var id=ids[params.dataIndex];
                    window.open("/view/field-detail?field-id="+id);

                });
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    }

    //3个子页面切换
    $("#field-ranking").click(function () {
        document.getElementById("field-part1").style.display="block";
        document.getElementById("field-part2").style.display="none";
        document.getElementById("field-part3").style.display="none";
        document.getElementById("field-ranking").className="button2";
        document.getElementById("field-change").className="button1";
        document.getElementById("field-more").className="button1";
    })
    $("#field-change").click(function () {
        $('#field-part1').hide();
        $('#field-part2').show();
        $('#field-part3').hide();
        $('#field-line').resize();
        document.getElementById("field-ranking").className="button1";
        document.getElementById("field-change").className="button2";
        document.getElementById("field-more").className="button1";
    })
    $("#field-more").click(function () {
        $('#field-part1').hide();
        $('#field-part2').hide();
        $('#field-part3').show();
        $('#field-cloud').resize();
        /*document.getElementById("field-part1").style.display="none";
        document.getElementById("field-part2").style.display="none";
        document.getElementById("field-part3").style.display="block";*/

        document.getElementById("field-ranking").className="button1";
        document.getElementById("field-change").className="button1";
        document.getElementById("field-more").className="button2";
    })

    $("#field-cloud-reload").click(function () {
        getRequest(
            '/field/cloud',
            function (res) {
                var clouds = res.content;
                drawCloud(clouds);
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    });
});
function fieldClick(id){
    window.open("/view/field-detail?field-id="+id);
}

function drawLine(names,years,nums,toolTipKind){
    var myseries=[];
    var toolTip=['axis','item'];
    for(var i=0;i<20;i++){
        var serie={
            name:names[i],
            type:'line',
            data:nums[i]
        };
        myseries.push(serie);
    }
    var option = {
        tooltip : {
            trigger: toolTip[toolTipKind]
        },
        legend: {
            data:names
        },
        grid:{
            top:'20%'
        },
        toolbox: {
            show : true,
            feature : {
                mark : {show: true},
                dataView : {show: true, readOnly: false},
                magicType : {show: true, type: ['line', 'bar']},
                restore : {show: true},
                saveAsImage : {show: true}
            }
        },
        calculable : true,
        xAxis : [
            {
                type : 'category',
                boundaryGap : false,
                data : years,
                axisLabel:{
                    rotate:45
                },
            }
        ],
        yAxis : [
            {
                type : 'value',
            }
        ],
        series :myseries
    };
    var RateChart = echarts.init($("#field-line")[0]);
    RateChart.setOption(option);
}

function drawCloud(data) {
    var ids =data.map(function (item) {
        return item.id;
    });
    var myData=[];
    var i=0;
    for(let d of data){
        var dd={
            name:d.field_name,
            value:d.paper_num,
        };
        i++;
        myData.push(dd);
    }

    var option = {
            backgroundColor: '#F7F7F7',
        tooltip: {
            show: true
        },
        series: [{
            name: 'field cloud',
            type: 'wordCloud',
            size: ['100%', '100%'],
            rotationRange: [0, 0],
            textPadding: 0,
            //color:'#3bbccb',
            textStyle: {
                normal: {
                    color: function() {
                        return 'rgb(' + [
                            Math.round(Math.random() * 160),
                            Math.round(Math.random() * 160),
                            Math.round(Math.random() * 160)
                        ].join(',') + ')';
                    }
                },
                emphasis: {
                    shadowBlur: 10,
                    shadowColor: '#333333'
                }
            },

            autoSize: {
                enable: true,
                minSize: 14
            },
            data: myData
        }]
    };
    var RateChart = echarts.init($("#field-cloud")[0]);
    RateChart.setOption(option);
    RateChart.off('click');
    RateChart.on('click', function (params) {
        var id=ids[params.dataIndex];
        window.open("/view/field-detail?field-id="+id);

    });
}
