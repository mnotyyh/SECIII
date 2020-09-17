var n=0;
var orgs=[];
var method=[];
var methodId;
$(document).ready(function() {
    /*
    getRequest(
        '/org/get',
        function (res) {
            orgs = res.content;
            display(orgs);
        },
        function (error) {
            alert(JSON.stringify(error));
        }
    );
*/
    GetInit();
    initSelect();

    //2个子页面切换
    $("#org-ranking").click(function () {
        document.getElementById("org-rankingTable").style.display="block";
        document.getElementById("org-searchingTable").style.display="none";
        //document.getElementById("author-diyTable").style.display="none";
        document.getElementById("org-ranking").className="button2";
        document.getElementById("org-searching").className="button1";
        //document.getElementById("author-diy").className="button1";
    })
    $("#org-searching").click(function () {
        document.getElementById("org-rankingTable").style.display="none";
        document.getElementById("org-searchingTable").style.display="block";
        //document.getElementById("author-diyTable").style.display="none";
        document.getElementById("org-ranking").className="button1";
        document.getElementById("org-searching").className="button2";
        //document.getElementById("author-diy").className="button1";
    })

    $("#org-search").click(function () {
        var name=$('#org_1').val();
        var num=$('#org_2').val();
        if(name.length==0&&num.length==0)
            alert("You can not search for nothing! Please input at least one identity");
        else {
            getRequest(
                '/org/search?name=' + name + '&num=' + num,
                function (res) {
                    orgs = res.content;
                    display(orgs);
                },
                function (error) {
                    alert(JSON.stringify(error));
                }
            );
        }
    });

    function display(orgList) {
        var orgInfo = "";
        n=0;
        for (let org of orgList) {
            orgInfo += "<tr></tr><td class='aaa'>"+"<a href='/view/org-detail?org-id="+org.id+"' target='_blank'>" + org.org_name+"</a>" + "</td>" +
                "<td class='bbb'>" + org.paper_num+ "</td></tr>" ;
            //
            // orgInfo +=
            //     "<td class='ccc'><button type='button' style='background-color: #4CAF50; /* Green */\n" +
            //     "border: 2px solid #4CAF50;" +
            //     "color: white;\n" +
            //
            //     "    text-align: center;\n" +
            //     " border-radius: 6px;\n" +
            //     "    text-decoration: none;\n" +
            //     "    display: inline-block;\n" +
            //     "    font-size:16px;' onclick='orgClick("+ org.id +")'>详情</button>" + "</td></tr>";
            n+=1;
            if(n>1000){break}
        }
        $('#org-list').html(orgInfo);
    }

    function initSelect(){

        // 过滤条件变化后重新查询
        $('#method-select').change (function () {
            methodId=$(this).children('option:selected').val();
            getRating();
        });
    }

    function GetInit() {
        getRequest(
            '/org/rating?methodId='+1,
            function (res) {
                method = res.content||[];

                var tableData = method.map(function (item) {
                    return item.paper_num;
                });
                var nameList = method.map(function (item) {
                    return item.org_name;
                });
                var ids = method.map(function (item) {
                    return item.id;
                }).reverse();
                var option = {
                    title: {
                        text: '机构活跃度排行',
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
                                        var colorList = ['#c23531','#2f4554', '#d48265',
                                            '#91c7ae','#749f83', '#ca8622',
                                            '#B5C334','#FCCE10','#E87C25','#27727B'];
                                        return colorList[params.dataIndex]
                                    }
                                }
                            }
                        }
                    ]
                };
                var RateChart = echarts.init($("#author-rating")[0]);
                RateChart.setOption(option);
                RateChart.off('click');
                RateChart.on('click', function (params) {
                    var id=ids[params.dataIndex];
                    window.open("/view/org-detail?org-id="+id);

                });
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );

    }
    function getRating(){
        getRequest(
            '/org/rating?methodId='+methodId,
            function (res) {
                method = res.content||[];

                var tableData = method.map(function (item) {
                    if(methodId==1)return item.paper_num;
                    if(methodId==2)return item.citation_sum;
                    if(methodId==3)return item.point;
                });
                var nameList = method.map(function (item) {
                    return item.org_name;
                });
                var ids = method.map(function (item) {
                    return item.id;
                }).reverse();
                var option = {
                    title: {
                        text: '机构活跃度排行',
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
                        right: '10%',
                        bottom: '3%',
                        top:'10%',
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
                        name:'机构名称',
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
                                        var colorList = ['#c23531','#2f4554', '#d48265',
                                            '#91c7ae','#749f83', '#ca8622',
                                            '#B5C334','#FCCE10','#E87C25','#27727B'];
                                        return colorList[params.dataIndex]
                                    }
                                }
                            }
                        }
                    ]
                };
                var RateChart = echarts.init($("#author-rating")[0]);
                RateChart.setOption(option);
                RateChart.off('click');
                RateChart.on('click', function (params) {
                    var id=ids[params.dataIndex];
                    window.open("/view/org-detail?org-id="+id);

                });
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    }
});
function orgClick(id){
    window.open("/view/org-detail?org-id="+id);
}