var authors_orgSame=[];
var authors_orgDif=[];
var author;
$(document).ready(function() {//alert("relation!");
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
        '/author/getRelatedAuthors?id=' + id+"&mode="+0,
        function (res) {
            authors_orgSame=res.content;
            $('#author-sameOrgNum').text(decodeURI(authors_orgSame.length));
            var childrens=[];
            var i=0;
            for(let it of authors_orgSame){
                if(i==20)break;
                var temp={name:it.author_name,value:it.id};
                childrens.push(temp);
                i++;
            }
            drawTree(childrens,"author-sameOrg");
        },
        function (error) {
            alert(JSON.stringify(error));
        }
    );
    getRequest(
        '/author/getRelatedAuthors?id=' + id+"&mode="+1,
        function (res) {
            authors_orgDif=res.content;
            $('#author-difOrgNum').text(decodeURI(authors_orgDif.length));
            var childrens=[];
            var i=0;
            for(let it of authors_orgDif){
                if(i==20)break;
                var temp={name:it.author_name,value:it.id};
                childrens.push(temp);
                i++;
            }
            drawTree(childrens,"author-difOrg");
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

function drawTree(data,position){
    var ids = data.map(function (item) {
        return item.id;
    }).reverse();

    option2 = {
        title : {
            text:'\n作者： '+author.author_name,
            subtext: "机构： "+author.org,
            textStyle: {
                fontSize:15
            }
        },

        grid:{
            right:'30%',
            top:'5%'
        },
        toolbox: {
            show : true,
            feature : {
                mark : {show: true},
                dataView : {show: true, readOnly: false},
                restore : {show: true},
                saveAsImage : {show: true}
            }
        },
        series : [
            {
                name:'树图',
                type:'tree',
                orient: 'horizontal',  // vertical horizontal
                rootLocation: {x: 100,y: 230}, // 根节点位置  {x: 100, y: 'center'}
                nodePadding: 8,
                rootPadding:8,
                rootStyle:{
                    position: 'left'
                },
                itemStyle: {
                    normal: {
                        color: '#51b43c',
                        label: {
                            show: true,
                            position: 'right',
                            formatter: "{b}",
                        },
                        lineStyle: {
                            color: '#50c3cc',
                            type: 'curve' // 'curve'|'broken'|'solid'|'dotted'|'dashed'

                        }
                    },
                    emphasis: {
                        color: '#4883b4',
                        label: {
                            show: false
                        },
                        borderWidth: 0
                    }
                },

                data: [{
                        name: author.author_name,
                        value: 9,
                        children: data
                    }]
            }
        ]
    };
    option = {

        tooltip : {
            trigger: 'item',
            formatter: '{a} : {b}'
        },
        toolbox: {
            show : true,
            feature : {
                restore : {show: true},
                magicType: {show: true, type: ['force', 'chord']},
                saveAsImage : {show: true}
            }
        },

        series : [
            {
                type:'force',
                name : "学者关系",
                ribbonType: false,

                itemStyle: {
                    normal: {
                        label: {
                            show: true,
                            textStyle: {
                                color: '#333'
                            }
                        },
                        nodeStyle : {
                            brushType : 'both',
                            borderColor : 'rgba(255,215,0,0.4)',
                            borderWidth : 1
                        },
                        linkStyle: {
                            type: 'curve'
                        }
                    },
                    emphasis: {
                        label: {
                            show: false
                            // textStyle: null      // 默认使用全局文本样式，详见TEXTSTYLE
                        },
                        nodeStyle : {
                            //r: 30
                        },
                        linkStyle : {}
                    }
                },
                useWorker: false,
                minRadius : 15,
                maxRadius : 25,
                gravity: 1.1,
                scaling: 1.1,
                roam: 'move',
                nodes:[
                    {name: '乔布斯', value : 20, label: '乔布斯'},
                    {name: '丽萨-乔布斯',value : 2},
                    {name: '保罗-乔布斯',value : 3},
                    {name: '克拉拉-乔布斯',value : 3},
                    {name: '劳伦-鲍威尔',value : 7},
                    {name: '史蒂夫-沃兹尼艾克',value : 5},
                    {name: '奥巴马',value : 8},
                    {name: '比尔-盖茨',value : 9},
                    {name: '乔纳森-艾夫',value : 4},
                    {name: '蒂姆-库克',value : 4},
                    {name: '龙-韦恩',value : 1},
                ],
                links : [
                    {source : '丽萨-乔布斯', target : '乔布斯'},
                    {source : '保罗-乔布斯', target : '乔布斯'},
                    {source : '克拉拉-乔布斯', target : '乔布斯'},
                    {source : '劳伦-鲍威尔', target : '乔布斯'},
                    {source : '史蒂夫-沃兹尼艾克', target : '乔布斯'},
                    {source : '奥巴马', target : '乔布斯'},
                    {source : '比尔-盖茨', target : '乔布斯'},
                    {source : '乔纳森-艾夫', target : '乔布斯'},
                    {source : '蒂姆-库克', target : '乔布斯'},
                    {source : '龙-韦恩', target : '乔布斯'},

                ]
            }
        ]
    };
    var myChart = echarts.init(document.getElementById(position));
    myChart.setOption(option2);
    myChart.off('click');
    myChart.on('click', function (params) {
        var index=params.dataIndex;
        var id=data[index-2].value;
        window.open("/view/author-detail-normal?author-id="+id);
    });
}