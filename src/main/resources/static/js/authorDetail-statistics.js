var author;
var topPapers=[];
var content="";
var histories=[];
var years=[];
var nums=[];
var allYears=['1988', '1989', '1990', '1991', '1992', '1993', '1993','1995','1996','1997','1998','1999','2000',
    '2001','2002','2003','2004','2005','2006','2007','2008','2009','2010','2011','2012','2013','2014',
    '2015','2016','2017','2018','2019','2020'];
var allNums=[];
$(document).ready(function() {
    var id = getUrlParameter('author-id');
    getRequest(
        '/author/getById?id=' + id,
        function (res) {
            author=res.content;
            $('#author-citation').text(decodeURI(author.citation_sum));
            $('#author-paperNum').text(decodeURI(author.paper_num));
        },
        function (error) {
            alert(JSON.stringify(error));
        }
    );

    getRequest(
        '/author/getTopPaper?id=' + id,
        function (res) {
            topPapers = res.content;//alert(topPapers.length)
            for(let p of topPapers){
                content+="<tr><td>"+"<span style='color: #1313a6'>"+p.article_Citation_Count+"</span>" + "&nbsp;&nbsp;&nbsp;" +"<a style='color: #358d30' href='/view/paper-detail?paper-id="+p.paper_id+"' target=\"_blank\">" + p.document_title + "</a></td>";
                content+="</tr>";
                content+="<tr><td>"+"~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"+"</td></tr>";
            }
            $('#author-topPapers').html(content);

        },
        function (error) {
            alert(JSON.stringify(error));
        }
    );

    getRequest(
        '/author/history?id='+id,
        function (res) {
           histories=res.content;

            for(let h of histories){
                years.push(h.year);
                nums.push(h.num);
            }
            allNums=getAllHistories(histories);
            drawBar(years,nums);
        },
        function (error) {
            alert(JSON.stringify(error));
        }
    );

    $('#history-mode').change (function () {
        methodId=$(this).children('option:selected').val();
        if(methodId=="1"){
            drawBar(years,nums);
        }
        if(methodId=="2"){
            allNums=getAllHistories(histories);
            //alert(allNums.length)
            drawBar(allYears,allNums);
        }
    });

    getRequest(
        '/author/meeting?id='+id,
        function (res) {
            var meetings=res.content;

            $('#author-meetingNum').text(decodeURI(meetings.length));
            drawBar2(meetings);
        },
        function (error) {
            alert(JSON.stringify(error));
        }
    );

});

function getAllHistories(histories){//alert(histories.length)
    var ans=[];
    for(var i=1988;i<=2020;i++){
        var find=0;
        for(let h of histories){
            if(parseInt(h.year)==i){
                find=1;
                ans.push(h.num);
                break;
            }
        }
        if(find==0)ans.push(0);
    }

    return ans;
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

function drawBar(years,nums) {
    var sum=0;
    for(let n of nums){
        sum+=n;
    }
    let n=0;
    let nums2 = nums.map( item => {
        n = n + item;
        return n;
    });

    var height=Math.ceil(sum/5)*5;//alert(height)
    option = {
        color: ['#3398DB'],
        tooltip: {
            trigger: 'axis',
            axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        legend: {
            data:['本年发文数','截至本年发文总数']
        },
        grid: {
            left: '2%',
            right: '5%',
            bottom: '5%',
            top:'22%',
            containLabel: true
        },
        xAxis: [
            {
                name:'年份',
                nameTextStyle:{
                    color:'red'
                },
                axisLabel:{
                    rotate:45
                },
                type: 'category',
                data: years,
                axisTick: {
                    alignWithLabel: true
                }
            }
        ],
        yAxis: [
            {
                name:'论文数',
                nameTextStyle:{
                    color:'red'
                },
                type: 'value',
                min: 0,
                max: height,
                interval: 5,
            }
        ],
        series: [
            {
                name:'本年发文数',
                type: 'bar',
                barWidth: '30%',
                data: nums,
                color:'#9bd2db',
            },
            {
                name:'截至本年发文总数',
                type: 'bar',
                barWidth: '30%',
                data: nums2,
                color:'#161cdb',
            }
        ]
    };
    var myChart = echarts.init(document.getElementById("author-history"));
    myChart.setOption(option);
}

function drawBar2(data) {
    var num=[];
    var name=[];
    for(let d of data){
        var n=d.publication_title+"("+d.publication_year+")";
        name.push(n);
        num.push(d.paper_num);
    }
    var option2 = {

        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'shadow'
            }
        },

        grid: {
            left: '0.5%',
            right: '10%',
            bottom: '0.5%',
            top:'18%',
            containLabel: true
        },
        xAxis: {
            name:'会议发文数',
            nameTextStyle:{
                color:'red'
            },
            type: 'value',
            boundaryGap: [0, 0.01]
        },
        yAxis: {
            name:'会议名称',
            nameTextStyle:{
                color:'red'
            },
            type: 'category',
            data: name
        },
        series: [
            {
                type: 'bar',
                data: num,
                itemStyle: {
                    normal: {
                        //这里是重点
                        color: function (params) {
                            //注意，如果颜色太少的话，后面颜色不会自动循环，最好多定义几个颜色
                            var colorList = ['#c23531', '#2f4554', '#d48265',
                                '#91c7ae', '#749f83', '#ca8622',
                                '#B5C334', '#FCCE10', '#E87C25', '#27727B'];
                            return colorList[params.dataIndex]
                        }
                    }
                }
            },

        ]
    };
    var RateChart = echarts.init($("#author-meeting")[0]);
    RateChart.setOption(option2);

}