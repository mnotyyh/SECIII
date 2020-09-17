var author;
$(document).ready(function() {//alert("interest!");
    var id = getUrlParameter('author-id');//alert(name);
    getRequest(
        '/author/getRecentById?id=' + id+'&mode='+1,
        function (res) {
            var recentField = res.content;
            var str="";
            //alert(recentField.length/3);
            for(var i=0;i<recentField.length/3;i++){
                str+="<tr>";
                for(var j=0;j<Math.min(3,999);j++){
                    str+="<td><a style='color: #238719' href='#' onclick='openFieldDetail(\" "+recentField[i*3+j] + "\")'>" +recentField[i*3+j]+"</a></td>";
                }
                str+="</tr>";
            }

            $('#author-recentField').html(str);
        },
        function (error) {
            alert(JSON.stringify(error));
        }
    );
    getRequest(
        '/author/getRecentById?id=' + id+'&mode='+2,
        function (res) {
            var recentPaper = res.content;
            var str="";
            for(let p of recentPaper){
                str+="<tr><td><a style='color: #2a8cff' href='/view/paper-detail?paper-id="+p.id+"' target=\"_blank\">"+p.document_title+"</a></td></tr>";
                str+="<tr><td>"+"~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"+"</td></tr>";
            }

            $('#author-recentPaper').html(str);
        },
        function (error) {
            alert(JSON.stringify(error));
        }
    );
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
        '/author/interest?id=' + id,
        function (res) {
            var fields=res.content;
            var str="";
            for(var i=0;i<fields.length-2;i+=2){
                str+="<tr>";
                for(var j=i;j<i+2;j++) {//alert(j);
                    var name=fields[j][0];
                    str += "<td><span style='color: #1313a6'>" + fields[j][1] + "</span>" + "&nbsp;&nbsp;&nbsp;"
                        + "<a style='color: #358d30' href='#' onclick='openFieldDetail(\" "+name + "\")'>" + fields[j][0] + "</a></td>"
                }
                str+="</tr>";
            }
            $('#author-field').html(str);
        },
        function (error) {
            alert(JSON.stringify(error));
        }
    );
    getRequest(
        '/author/interest_change?id=' + id,
        function (res) {
            var interest=res.content;
           drawBar(interest.names,interest.years,interest.nums.reverse());
        },
        function (error) {
            alert(JSON.stringify(error));
        }
    );
});
function getUrlParameter(name){
    name = name.replace(/[]/,"\[").replace(/[]/,"\[").replace(/[]/,"\\\]");
    var regexS = "[\\?&]"+name+"=([^&#]*)";
    var regex = new RegExp( regexS );
    var results = regex.exec(window.parent.location.href );
    if( results == null ) return ""; else {
        return results[1];
    }
};

function openFieldDetail(name){//alert(name);
    getRequest(
        '/field/getIdByName?name='+name,
        function (res) {
            var id=res.content;
            if(id==null)alert("抱歉！该方向目前暂无详情");
            else window.open("/view/field-detail?field-id="+id);
        },
        function (error) {
            alert(JSON.stringify(error));
        }
    );
}

function drawBar(names,years,nums){
    var colors=['rgba(206,24,16,0.73)','rgba(205,125,18,0.69)','rgba(189,186,43,0.72)','rgba(135,198,29,0.75)',
        'rgba(58,192,141,0.74)','rgba(53,91,196,0.7)','rgba(30,30,189,0.64)','rgba(114,31,181,0.66)',
        'rgba(185,25,151,0.66)','rgba(185,127,154,0.63)','rgba(131,182,189,0.64)','rgba(169,109,181,0.66)',
        'rgba(185,163,113,0.66)','rgba(75,185,77,0.63)','rgba(108,128,189,0.64)','rgba(47,43,49,0.66)'];
    var mySeries=[];
    for(var i=0;i<nums.length;i++){
        var numData=[];
        for(var j=years.length-1;j>=0;j--)
            numData.push(nums[j][i]);
        var part={
            name:names[i],
            type:'bar',
            stack: '总量',
            color:colors[i],
            itemStyle : { normal: {label : {show: true, position: 'insideRight',

                        formatter: function(params) {
                            if (params.value > 0) {
                                return params.value;
                            } else {
                                return '';
                            }
                        },
                    }}},
            data:numData
        };
        mySeries.push(part);
    }
    var option = {
        tooltip : {
            trigger: 'axis',
            axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        grid:{
            top:'14%'
        },
        legend: {
            x:'center',
            data:names,
        },
        toolbox: {
            show : true,
            feature : {
                mark : {show: true},
                dataView : {show: true, readOnly: false},
                magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},
                restore : {show: true},
                saveAsImage : {show: true}
            }
        },
        calculable : true,

        xAxis : [
            {
                name:'论文数',
                nameTextStyle:{
                    color:'green'
                },
                type : 'value'
            }
        ],
        yAxis : [
            {
                name:'年份',
                nameTextStyle:{
                    color:'green'
                },
                type : 'category',
                data : years
            }
        ],
        series : mySeries
    };

    var myChart = echarts.init($("#author-fieldChange")[0]);
    myChart.setOption(option);
}