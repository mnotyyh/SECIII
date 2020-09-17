var field;
var papers=[];
var authors=[];
var orgs=[];
var histories=[];
var years=[];
var nums=[];
var allYears=['1988', '1989', '1990', '1991', '1992', '1993', '1993','1995','1996','1997','1998','1999','2000',
    '2001','2002','2003','2004','2005','2006','2007','2008','2009','2010','2011','2012','2013','2014',
    '2015','2016','2017','2018','2019','2020'];
var allNums=[];
$(document).ready(function() {

    var id = getUrlParameter('field-id');

    getRequest(
        '/field/getById?id=' + id,
        function (res) {
            field = res.content;
            $('#field-name').text(field.field_name);
            $('#field-paperNum').text(field.paper_num);
            $('#field-CitationSum').text(field.citation_sum);
        },
        function (error) {
            alert(JSON.stringify(error));
        }
    );

    getRequest(
        '/field/history?id=' + id,
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
        '/field/rank?id=' + id,
        function (res) {
            var ranks = res.content;
            $('#fieldDetail-rank-paper').text(decodeURI(ranks[0]));
            $('#fieldDetail-rank-citation').text(decodeURI(ranks[1]));
        },
        function (error) {
            alert(JSON.stringify(error));
        }
    );
    getRequest(
        '/field/rankData?id=' + id,
        function (res) {
            var rankData = res.content;
            var markPoint=[];
            var index=rankData[0][1];
            markPoint.push(rankData[index][0]);markPoint.push(rankData[index][1]);
            drawTable(rankData.slice(1),"fieldDetail-rank",markPoint,rankData.field_name);
        },
        function (error) {
            alert(JSON.stringify(error));
        }
    );

    getRequest(
        '/field/getTopPapers?id=' + id,
        function (res) {
            papers = res.content.slice(0,20);
            var content="";
            var cnt=0;
            for(let p of papers){
                if(cnt===10)break;
                content+="<tr><td>"+"<span style='color: #1313a6'>"+p.article_Citation_Count+"</span>" + "&nbsp;&nbsp;&nbsp;" +"<a style='color: #358d30' href='/view/paper-detail?paper-id="+p.id+"' target=\"_blank\">" + p.document_title + "</a></td>";
                content+="</tr>";
                //content+="<tr><td>"+"~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"+"</td></tr>";
                cnt+=1;
            }
            $('#fieldDetail-topPaper').html(content);
        },
        function (error) {
            alert(JSON.stringify(error));
        }
    )


    getRequest(
        '/field/getTopAuthors?id=' + id,
        function (res) {
            authors = res.content.slice(0,20);
            var str="";
            for(var i=0;i<Math.min(10,authors.length);i+=2){
                str+="<tr>";
                for(var j=i;j<i+2;j++) {//alert(j);
                    var id=authors[j].id;
                    str += "<td><span style='color: #1313a6'>" + authors[j].paper_num + "</span>" + "&nbsp;&nbsp;&nbsp;"
                        + "<a style='color: #358d30' href='#' onclick='openAuthorDetail("+id + ")'>" + authors[j].author_name + "</a>"
                        +"<span>"+" ( "+authors[j].org+" )"+"</span></td>"
                        +"<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>";
                }
                str+="</tr>";
            }
            $('#fieldDetail-topAuthor').html(str);
        },
        function (error) {
            alert(JSON.stringify(error));
        }
    );

    getRequest(
        '/field/getTopOrgs?id=' + id,
        function (res) {
            orgs = res.content.slice(0,20);
            var str="";//str+=orgs[0].org_name;
            for(var i=0;i<Math.min(10,orgs.length);i+=2){
                str+="<tr>";
                for(var j=i;j<i+2;j++) {//alert(j);
                    var id=orgs[j].id;
                    str += "<td><span style='color: #1313a6'>" + orgs[j].paper_num + "</span>" + "&nbsp;&nbsp;&nbsp;"
                        + "<a style='color: #358d30' href='#' onclick='openOrgDetail("+id + ")'>" + orgs[j].org_name + "</a></td>"
                        +"<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>";
                }
                str+="</tr>";
            }
            $('#fieldDetail-topOrg').html(str);
        },
        function (error) {
            alert(JSON.stringify(error));
        }
    );


});
function openAuthorDetail(id){//alert(name);
    window.open("/view/author-detail-normal?author-id="+id);
}
function openOrgDetail(id){//alert(name);
    window.open("/view/org-detail?org-id="+id);
}

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
    var t=Math.ceil(sum/10);
    var height=t*10;//alert(height)
    var gap=Math.pow(10,t);
    var option = {
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
                    rotate:60
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
                //interval: gap,
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
    var myChart = echarts.init(document.getElementById("fieldDetail-history"));
    myChart.setOption(option);
}

function drawTable(authors,position,markPoint,name) {
    var x=markPoint[0],y=markPoint[1];//alert(x);alert(y);
    var option = {
        grid:{
            top:'18%'
        },
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
                    symbolSize:70,
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