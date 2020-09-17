$(document).ready(function () {
    getRequest(
        '/file/getFiles',
        function (res) {
            var files = res.content;
            display(files);
        },
        function (error) {
            alert(JSON.stringify(error));
        }
    );

    function display(fileList) {
        var fileInfo = "";
        for (let f of fileList) {
            fileInfo += "<tr><td>" +
                "<label>" +
                "<span style='vertical-align:middle'>" + f + "</span>" +
                "<input style='vertical-align: middle' name=\"" + f + "\" type='checkbox' />" +
                "</label>" +
                "</td></tr>";
        }
        $('#file-list').html(fileInfo);
    }


    $("#uploadFile").click(function () {
        var file = document.getElementById('newFile');
        upload(file);
    });

    var isCheckAll = false;
    $('#fileAll').click(function () {
        if (isCheckAll) {
            $("input[type='checkbox']").each(function () {
                this.checked = false;
            });
            isCheckAll = false;
        } else {
            $("input[type='checkbox']").each(function () {
                this.checked = true;
            });
            isCheckAll = true;
        }
    });

    function upload(file) {
        alert("upload");
        let formData = new FormData();
        let temp = file.files[0];
        if (temp) {
            formData.append('file', temp);
            $.ajax({
                url: "/file/addFile",
                type: "POST",
                data: formData,
                processData: false, // 告诉jQuery不要去处理发送的数据
                contentType: false, // 告诉jQuery不要去设置Content-Type请求头
                success: function () {
                    window.location.reload();
                    alert("文件上传成功");
                }
            })
        }
    }

    $("#updateDB").click(function () {
        var fileTobeUpdate = [];
        $("input[type='checkbox']").each(function () {
            if (this.id != "fileAll") {
                if (this.checked) {
                    fileTobeUpdate.push(this.name);
                }
            }
        });

        alert("数据库更新中");
        postRequest(
            '/file/updateDB?fileTobeUpdate=' + fileTobeUpdate,
            null,
            function (res) {
                alert("数据库更新成功");
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );

    });
});

