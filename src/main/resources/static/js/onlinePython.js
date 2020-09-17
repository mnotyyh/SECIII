$(document).ready(function() {


    $('#online-download').click(function () {
        var content=$("#online-input").val();
        var mode=$("#online-select").val();
        if(content==null || content=="")alert("搜索字符串不能为空！");
        else{
            postRequest(
                '/file/onlinePython?str='+content+'&mode='+mode,
                null,
                function (res) {
                    alert(res.content);
                },
                function (error) {
                    alert(JSON.stringify(error));
                }
            );
        }
    });

});