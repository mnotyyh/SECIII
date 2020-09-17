
$(document).ready(function() {
     var id=getUrlParameter('paper-id')
    getRequest(
        '/paper/getById?id='+id,
        function (res) {
            mypaper = res.content;
            display(mypaper);
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
function display(p) {
    $('#document_title').html(p.document_title);

    var info2 = "";
    var authorss = p.authors.split("; ");
    var orgss = p.author_Affiliations.split(";");
    for (var i = 0; i < authorss.length; i++ ) {
        //window.alert(authorss[i]);
        info2 += "<a href='#' onclick='openAuthorDetail(\" "+authorss[i]+  "\",\"" +orgss[i]+ "\")'>" + authorss[i]+"&nbsp "+ "</a><br>";
    }
    $('#authors').html(info2);

    var info3 = "";
    for (var j = 0; j < orgss.length; j++ ) {
        info3 += "<a href='#' onclick='openOrgDetail(\" "+orgss[j]+ "\")'>" + orgss[j] +"&nbsp"+ "</a><br>";
    }
    $('#author_Affiliations').html(info3);
    //$('#author_Affiliations').html(p.author_Affiliations);

    $('#publication_Year').html(p.publication_Year);
    $('#publication_Title').html(p.publication_Title);
    // $('#date_Added_To_Xplore').html(p.date_Added_To_Xplore);
    // $('#volume').html(p.volume);
    // $('#issue').html(p.issue);
    $('#start_Page').html(p.start_Page);
    $('#end_Page').html(p.end_Page);
    $('#abstract').html(p.abstract);
    // $('#issn').html(p.issn);
    // $('#isbns').html(p.isbns);
    $('#doi').html(p.doi);
    // $('#funding_Information').html(p.funding_Information);
    $('#pdf_Link').html(p.pdf_Link);
    var info = "<a id='pdf_L' href='"+p.pdf_Link+"' target=\"_blank\">" + p.pdf_Link + "</a>";
    $('#pdf_L').html(info);

    var keywords=p.author_Keywords.split(";");
    var info4 = "";
    for (var k = 0; k < keywords.length; k++ ) {
        info4 += "<a href='#' onclick='openFieldDetail(\" "+keywords[k]+ "\")'>" + keywords[k] +"&nbsp"+ "</a><br>";
    }
    $('#author_Keywords').html(info4);
    // $('#ieee_Terms').html(p.ieee_Terms);
    // $('#inspec_Controlled_Terms').html(p.inspec_Controlled_Terms);
    // $('#inspec_Non_Controlled_Terms').html(p.inspec_Non_Controlled_Terms);
    // $('#mesh_Terms').html(p.mesh_Terms);
    // $('#article_Citation_Count').html(p.article_Citation_Count);
    $('#reference_Count').html(p.reference_Count);
    // $('#license').html(p.license);

    // $('#online_Date').html(p.online_Date);
    // $('#issue_Date').html(p.issue_Date);
    // $('#meeting_Date').html(p.meeting_Date);
    $('#publisher').html(p.publisher);
    $('#document_Identifier').html(p.document_Identifier);
}
function openAuthorDetail(name1, name2){//alert(name1);alert(name2)
    getRequest(
        '/author/getIdByAuthorAndOrg?Author=' + name1 +"&Org="+ name2,
        function (res) {
            var id=res.content;
            window.open("/view/author-detail-normal?author-id="+id);
        },
        function (error) {
            alert(JSON.stringify(error));
        }
    );
}
function openOrgDetail(name){//alert(name);
    getRequest(
        '/org/getIdByOrgName?name='+name,
        function (res) {
            var id=res.content;
            window.open("/view/org-detail?org-id="+id);
        },
        function (error) {
            alert(JSON.stringify(error));
        }
    );
}
function openFieldDetail(name){//alert(name);
    getRequest(
        '/field/getIdByName?name='+name,
        function (res) {
            var id=res.content;
            window.open("/view/field-detail?field-id="+id);
        },
        function (error) {
            alert(JSON.stringify(error));
        }
    );
}