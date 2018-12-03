/**
 * @Name index.js
 * @Author niushuai
 * @Date 2018/12/3 8:49
 * @Desc
 */

var host = "http://www.niushuai.xyz/"
function sendRequest(_type,_url,_data) {
    $.ajax({
        type: _type,
        url: host+_url,
        data: _data,
        dataType: "JSON",
        success: function(data){
            alert(2);
            alert(JSON.stringify(data));
        }
    });
}

$("#add").click(function(){
    sendRequest("GET","menu/addMenu",null);
});

$("#delete").click(function(){
    sendRequest("GET","menu/delete",null);
});

$("#addSub").click(function(){
    sendRequest("GET","menu/addSubMenu",null);
});
$("#addDefault").click(function(){
    sendRequest("GET","menu/addDefault",null);
});




