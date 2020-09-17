var methodId;
var authors=[];
var method=[];
var n=0;
$(document).ready(function() {
    /*
    getRequest(
        '/author/get',
        function (res) {
            authors = res.content;
            display(authors);
        },
        function (error) {
            alert(JSON.stringify(error));
        }
    );
*/
    GetInit();
    initSelect();

    //2个子页面切换
    $("#author-ranking").click(function () {
        document.getElementById("author-rankingTable").style.display="block";
        document.getElementById("author-searchingTable").style.display="none";
        //document.getElementById("author-diyTable").style.display="none";
        document.getElementById("author-ranking").className="button2";
        document.getElementById("author-searching").className="button1";
        //document.getElementById("author-diy").className="button1";
    })
    $("#author-searching").click(function () {
        document.getElementById("author-rankingTable").style.display="none";
        document.getElementById("author-searchingTable").style.display="block";
        //document.getElementById("author-diyTable").style.display="none";
        document.getElementById("author-ranking").className="button1";
        document.getElementById("author-searching").className="button2";
        //document.getElementById("author-diy").className="button1";
    })


    // 4种搜索模式，每个模式可选的排序方式也不一样多
    $("#authorSearch-mode").change(function() {
        var mode=$('#authorSearch-mode').val();
        var obj=document.getElementById('authorSearch-sort');
        if(mode==0){ // 搜名字有全部排序方式
            obj.options.length=0;
            obj.options.add(new Option("相关度排序",0));
            obj.options.add(new Option("姓名字母序",1));
            obj.options.add(new Option("论文数降序",2));
            obj.options.add(new Option("论文数升序",3));
            obj.options.add(new Option("被引用数降序",4));
            obj.options.add(new Option("被引用数升序",5));
        }
        else if(mode==1){  //搜机构也有全部排序方式
            obj.options.length=0;
            obj.options.add(new Option("相关度排序",0));
            obj.options.add(new Option("姓名字母序",1));
            obj.options.add(new Option("论文数降序",2));
            obj.options.add(new Option("论文数升序",3));
            obj.options.add(new Option("被引用数降序",4));
            obj.options.add(new Option("被引用数升序",5));
        }
        else if(mode==2){  //搜论文数只有3种有道理的排序
            obj.options.length=0;
            obj.options.add(new Option("姓名字母序",1));
            obj.options.add(new Option("论文数降序",2));
            obj.options.add(new Option("论文数升序",3));
        }
        else if(mode==3){  //搜论文数只有3种有道理的排序
            obj.options.length=0;
            obj.options.add(new Option("姓名字母序",1));
            obj.options.add(new Option("被引用数降序",4));
            obj.options.add(new Option("被引用数升序",5));
        }
    })


    $("#author-search").click(function () {
        var mode=$('#authorSearch-mode').val();
        var sort=$('#authorSearch-sort').val();
        var str=$('#author_content').val();
        var flag=true;

        if(str.length==0) { //输入不能为空
            alert("You can not search for nothing! Please input at least one identity!");
            flag = false;
        }

        if(mode==2||mode==3){
            var r = /^\+?[1-9][0-9]*$/;
            if(!r.test(str)) {
                alert("You must input and POSTIVE INTEGER!")
                flag=false;
            }
        }


        if(flag) {
            getRequest(
                '/author/search?mode=' + mode + '&sort=' + sort + '&str=' + str,
                function (res) {
                    authors = res.content;
                    display(authors);
                    $("#author-list").attr("style","display:block");
                },
                function (error) {
                    alert(JSON.stringify(error));
                }
            );
        }
    });

    function display(List) {
        var Info = "";
        n=0;
        for (let a of List) {
            Info += "<tr></tr><td style='text-align: left'>"+"<a style='font-size: 125%' href='/view/author-detail-normal?author-id="+a.id+"' target='_blank'>" + a.author_name+"</a>"
                + "( " + a.org + " )" + "</tr>" ;

            // Info +=
            //     "<td class='ddd'><button type='button' style='background-color: #4CAF50; /* Green */\n" +
            //     "border: 2px solid #4CAF50;" +
            //     "color: white;\n" +
            //
            //     "    text-align: center;\n" +
            //     " border-radius: 6px;\n" +
            //     "    text-decoration: none;\n" +
            //     "    display: inline-block;\n" +
            //     "    font-size:16px;' onclick='authorClick("+ a.id + ")'>详情</button>" + "</td></tr>";
            n+=1;
            if(n>1000){break}
        }
        $('#author-list').html(Info);
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
            '/author/rating?methodId='+1,
            function (res) {
                method = res.content||[];
                var authors=res.content;
                var tableData = method.map(function (item) {
                    return item.paper_num;
                });
                var ids = method.map(function (item) {
                    return item.id;
                }).reverse();
                /*
                var nameList = method.map(function (item) {
                    return item.author_name;
                });
                */
                var nameList2=[]
                for(var i=0;i<10;i++){
                    //alert(authors[i].author_name) tainanle
                    var s=authors[i].author_name+"\n("+authors[i].org+")"
                    nameList2.push(s)
                }

                var option = {
                    title: {
                        text: '作者活跃度排行',
                        subtext: '数据来自学术网站'
                    },
                    tooltip: {
                        trigger: 'axis',
                        axisPointer: {
                            type: 'shadow'
                        }
                    },

                    grid: {
                        left: '0.5%',
                        right: '25%',
                        top:'10%',
                        bottom: '0.5%',
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
                        name:'作者名称',
                        nameTextStyle:{
                            color:'red'
                        },
                        type: 'category',
                        data: nameList2.reverse()
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
                    window.open("/view/author-detail-normal?author-id="+id);
                });
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );

    }

    function getRating(){
        getRequest(
            '/author/rating?methodId='+methodId,
            function (res) {
                method = res.content||[];
                var authors=res.content;

                var tableData = method.map(function (item) {
                    if(methodId==1)return item.paper_num;
                    if(methodId==2)return item.citation_sum;
                    if(methodId==3)return item.point;
                });
                var ids = method.map(function (item) {
                    return item.id;
                }).reverse();
                /*
                var nameList = method.map(function (item) {
                    return item.author_name;
                });
                */
                var nameList2=[]
                for(var i=0;i<10;i++){
                    //alert(authors[i].author_name)
                    var s=authors[i].author_name+"\n("+authors[i].org+")"
                    nameList2.push(s)
                }

                var option = {
                    title: {
                        text: '作者活跃度排行',
                        subtext: '数据来自学术网站'
                    },
                    tooltip: {
                        trigger: 'axis',
                        axisPointer: {
                            type: 'shadow'
                        }
                    },

                    grid: {
                        left: '0.5%',
                        right: '0.5%',
                        bottom: '0.5%',
                        containLabel: true
                    },
                    xAxis: {
                        type: 'value',
                        boundaryGap: [0, 0.01]
                    },
                    yAxis: {
                        type: 'category',
                        data: nameList2.reverse(),


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
                    window.open("/view/author-detail-normal?author-id="+id);
                });
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    }
});
function isInteger(obj) {
    return typeof obj === 'number' && obj%1 === 0
}

