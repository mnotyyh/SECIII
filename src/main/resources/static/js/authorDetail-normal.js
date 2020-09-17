var author;

$(document).ready(function() {
    var id = getUrlParameter('author-id');//alert(name);

    //iframe的内嵌页面替换，以及4个大目录颜色替换
    $("#jump1").click(function() {
        changeColor("jump1");
        document.getElementById("myFrame").src="/view/author-detail-statistics";
    });
    $("#jump2").click(function() {
        changeColor("jump2");
        document.getElementById("myFrame").src="/view/author-detail-interest";
    });
    $("#jump3").click(function() {
        changeColor("jump3");
        document.getElementById("myFrame").src="/view/author-detail-relation";
    });
    $("#jump4").click(function() {
        changeColor("jump4");
        document.getElementById("myFrame").src="/view/author-detail-rank";
    });

    getRequest(
        '/author/getById?id=' + id,
        function (res) {
            author = res.content;
            $('#authorDetail-name').text(decodeURI(author.author_name));
            $('#authorDetail-org').text(decodeURI(author.org));
            $('#authorPaperNum').text(decodeURI(author.paper_num));
            $('#authorCitationSum').text(decodeURI(author.citation_sum));
        },
        function (error) {
            alert(JSON.stringify(error));
        }
    );

    $("#authorDetail-org").click(function(){
        getRequest(
            '/org/getByName?name='+author.org+'&mode='+0,
            function (res) {
                var orgId=res.content;alert(orgId);
                $("#authorDetail-orgd").attr("href","/view/org-detail?org-id="+orgId);
                window.open("/view/org-detail?org-id="+orgId);
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    });



    // 作者论文3个子目录，点击页面滚动到对应位置  子目录也可以直接跨越跳转了
    $("#authorDetail-statistics-section1").click(function() {
        var src=document.getElementById("myFrame").src;
        if (src !== "http://localhost:2800/view/author-detail-statistics"){
            document.getElementById("myFrame").src = "/view/author-detail-statistics";
            changeColor("jump1");
        }
        else {
            var iwin = document.getElementById('myFrame').contentWindow;
            iwin.scroll(0, 0);
        }
    });
    $("#authorDetail-statistics-section2").click(function() {
        var src = document.getElementById("myFrame").src;
        if (src !== "http://localhost:2800/view/author-detail-statistics"){
            document.getElementById("myFrame").src = "/view/author-detail-statistics";
            changeColor("jump1");
            setTimeout(function() {
                var e = document.createEvent("MouseEvents");
                e.initEvent("click", true, true);
                document.getElementById("authorDetail-statistics-section2").dispatchEvent(e);
            }, 500);
        }
        else {
            var iwin = document.getElementById('myFrame').contentWindow;
            var doc = iwin.document;
            var element1 = doc.getElementById('authorDetail-statistics-part1');
            iwin.scroll(0, element1.scrollHeight);
        }
    });
    $("#authorDetail-statistics-section3").click(function() {
        var src=document.getElementById("myFrame").src;
        if (src !== "http://localhost:2800/view/author-detail-statistics"){
            document.getElementById("myFrame").src = "/view/author-detail-statistics";
            changeColor("jump1");
            setTimeout(function() {
                var e = document.createEvent("MouseEvents");
                e.initEvent("click", true, true);
                document.getElementById("authorDetail-statistics-section3").dispatchEvent(e);
            }, 500);
        }
        else {
            var iwin = document.getElementById('myFrame').contentWindow;
            var doc = iwin.document;
            var element2 = doc.getElementById('authorDetail-statistics-part2');
            var element1 = doc.getElementById('authorDetail-statistics-part1');
            iwin.scroll(0, element2.scrollHeight + element1.scrollHeight);
        }
    });

    $("#authorDetail-interest-section1").click(function() {
        var src=document.getElementById("myFrame").src;
        if (src !== "http://localhost:2800/view/author-detail-interest"){
            document.getElementById("myFrame").src = "/view/author-detail-interest";
            changeColor("jump2");
        }
        else {
            var iwin = document.getElementById('myFrame').contentWindow;
            iwin.scroll(0, 0);
        }
    });
    $("#authorDetail-interest-section2").click(function() {//alert("2")
        var src=document.getElementById("myFrame").src;
        if (src !== "http://localhost:2800/view/author-detail-interest"){
            document.getElementById("myFrame").src = "/view/author-detail-interest";
            changeColor("jump2");
            setTimeout(function() {
                var e = document.createEvent("MouseEvents");
                e.initEvent("click", true, true);
                document.getElementById("authorDetail-interest-section2").dispatchEvent(e);
            }, 500);
        }
        else {
            var iwin = document.getElementById('myFrame').contentWindow;
            var doc = iwin.document;
            var element1 = doc.getElementById('authorDetail-interest-part1');
            //var yy=doc.body.scrollHeight-element1.scrollHeight-20;alert(yy);
            iwin.scroll(0, element1.scrollHeight);
        }
    });
    $("#authorDetail-interest-section3").click(function() {//alert("3")
        var src=document.getElementById("myFrame").src;
        if (src !== "http://localhost:2800/view/author-detail-interest"){
            document.getElementById("myFrame").src = "/view/author-detail-interest";
            changeColor("jump2");
            setTimeout(function() {
                var e = document.createEvent("MouseEvents");
                e.initEvent("click", true, true);
                document.getElementById("authorDetail-interest-section3").dispatchEvent(e);
            }, 500);
        }
        else {
            var iwin = document.getElementById('myFrame').contentWindow;
            var doc = iwin.document;
            var element1 = doc.getElementById('authorDetail-interest-part1');
            var element2 = doc.getElementById('authorDetail-interest-part2');
            iwin.scroll(0, element1.scrollHeight+element2.scrollHeight+20);
        }
    });
    $("#authorDetail-interest-section4").click(function() {//alert("2")
        var src=document.getElementById("myFrame").src;
        if (src !== "http://localhost:2800/view/author-detail-interest"){
            document.getElementById("myFrame").src = "/view/author-detail-interest";
            changeColor("jump2");
            setTimeout(function() {
                var e = document.createEvent("MouseEvents");
                e.initEvent("click", true, true);
                document.getElementById("authorDetail-interest-section4").dispatchEvent(e);
            }, 500);
        }
        else {
            var iwin = document.getElementById('myFrame').contentWindow;
            var doc = iwin.document;
            var element1 = doc.getElementById('authorDetail-interest-part1');
            var element2 = doc.getElementById('authorDetail-interest-part2');
            var element3 = doc.getElementById('authorDetail-interest-part3');
            iwin.scroll(0, element1.scrollHeight+element2.scrollHeight+element3.scrollHeight+20);
        }
    });

    $("#authorDetail-relation-section1").click(function() {
        var src=document.getElementById("myFrame").src;
        if (src !== "http://localhost:2800/view/author-detail-relation"){
            document.getElementById("myFrame").src = "/view/author-detail-relation";
            changeColor("jump3");
        }
        else {
            var iwin = document.getElementById('myFrame').contentWindow;
            iwin.scroll(0, 0);
        }
    });
    $("#authorDetail-relation-section2").click(function() {
        var src=document.getElementById("myFrame").src;
        if (src !== "http://localhost:2800/view/author-detail-relation"){
            document.getElementById("myFrame").src = "/view/author-detail-relation";
            changeColor("jump3");
            setTimeout(function() {
                var e = document.createEvent("MouseEvents");
                e.initEvent("click", true, true);
                document.getElementById("authorDetail-relation-section2").dispatchEvent(e);
            }, 500);
        }
        else {
            var iwin = document.getElementById('myFrame').contentWindow;
            var doc = iwin.document;
            var element1 = doc.getElementById('authorDetail-relation-part1');
            iwin.scroll(0, element1.scrollHeight);
        }
    });

    $("#authorDetail-rank-section1").click(function() {
        var src=document.getElementById("myFrame").src;
        if (src !== "http://localhost:2800/view/author-detail-rank"){
            document.getElementById("myFrame").src = "/view/author-detail-rank";
            changeColor("jump4");
        }
        else {
            var iwin = document.getElementById('myFrame').contentWindow;
            iwin.scroll(0, 0);
        }
    });
    $("#authorDetail-rank-section2").click(function() {
        var src=document.getElementById("myFrame").src;
        if (src !== "http://localhost:2800/view/author-detail-rank"){
            document.getElementById("myFrame").src = "/view/author-detail-rank";
            changeColor("jump4");
            setTimeout(function() {
                var e = document.createEvent("MouseEvents");
                e.initEvent("click", true, true);
                document.getElementById("authorDetail-rank-section2").dispatchEvent(e);
            }, 500);
        }
        else {
            var iwin = document.getElementById('myFrame').contentWindow;
            var doc = iwin.document;
            var element1 = doc.getElementById('authorDetail-rank-part1');
            iwin.scroll(0, element1.scrollHeight);
        }
    });

});

function changeColor(mode){
    var jumps=["jump1","jump2","jump3","jump4"];
    for(let jump of jumps){
        if(jump == mode){
            document.getElementById(jump).style.color="rgba(255,24,55,0.93)";
        }
        else
            document.getElementById(jump).style.color="#000000";
    }
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