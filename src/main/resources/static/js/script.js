/**
 * Created by Ruslan on 05.10.2016.

 function alertWork() {
    //alert("Work.");
    console.log("Work!");
}

 function changeMobile(){
    $("#mobile").mask("");
    var mobile = $('#mobile').val();
    mobile = mobile.replace(/\D+/g,"");
    $("#mobileServer").val(mobile);
    //console.log(mobile);
}*/

function changeDate(ddMMyyyy) {
    switch(ddMMyyyy){
        case 'd':
            birthdayDD = $("#date").val();
            break;
        case 'm':
            birthdayMM = $("#month").val();
            break;
        case 'y':
            birthdayYYYY = $("#year").val();
            break;
    }

    birthdayServer = birthdayYYYY + "-" + birthdayMM + "-" + birthdayDD;
    $("#birthdayServer").val(birthdayServer);
}

function changeSex() {
    sex = $('.radioSex:checked').val();

    $("#sex").val(sex);
}

var files;

$('input[type=file]').change(function(){
    files = this.files.toLocaleString();
    console.log(files[0]);
});

function changeMobile(){
    mobile = $('#mobile').val();
    mobile = mobile.replace(/\D+/g,"");
    mobile = String(mobile);
    $("#mobileServer").val(mobile);
    console.log($("#mobileServer").val());
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
