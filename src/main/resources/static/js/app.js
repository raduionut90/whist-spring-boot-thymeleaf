var stompClient = null;

$(document).ready(function(){

    if(stompClient!=null)
        stompClient.disconnect();

    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, function (frame) {
        stompClient.subscribe('/topic/message', function (getMessage) {
            //
            // var messageJson = JSON.parse(getMessage.body);
            //
            // $("#mesajNou").html("<p> " + messageJson.playerName + " </p>");
            $( "#here" ).load(window.location.href + " #here" );


        });
    });

    $("input").click(function(){

        sendData2Socket();

    });

});

function sendData2Socket() {

    var playerName = $("#playerName").val();
    var message = $("#message").val();

    stompClient.send("/app/mesaje", {}, JSON.stringify({'playerName': playerName, 'message': message}));

}
