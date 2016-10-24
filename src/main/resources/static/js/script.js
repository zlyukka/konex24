/**
 * Created by Ruslan on 05.10.2016.
 */
function alertWork() {
    //alert("Work.");
    console.log("Work!")
}
function postTellId(tell) {
    if($(tell).val().length<10){
        return
    }
    $.post("/admin/getUserByTel",{tel:""+$(tell).val()},
        function(result){
            console.log(result["id"],result["fio"],result["tel"]);
            $("#userId").val(result["id"]);
            $("#userName").text(" "+result["fio"]);
        }, "json");
}