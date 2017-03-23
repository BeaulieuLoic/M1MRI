$(function() {

    var rootURL = "http://localhost:8080/RestExample/rest";

    var lastID = 0;

    function updateMessages() {
        $.ajax({
            type : 'GET',
            url : rootURL + "/messages/after/" + lastID,
            dataType : "json",
            success : function(data) {
                if (null != data) {
                    if ($.isArray(data)) {
                        console.log("append each message");
                        $.each(data, function(key, message) {
                            appendMessage(message);
                        });

                    } else {
                        console.log("append one message ");
                        appendMessage(data);
                    }

                }
                console.log("call update again in 1s");
                setTimeout(updateMessages, 1000);
            },
            error : function(jqXHR, textStatus, errorThrown) {
                console.log('updateMessages error: ' + textStatus);
            }
        });
    }

    function appendMessage(message) {
        console.log(message);

        $('#chatContent').append("<p>").append(message.id).append(":").append(
                message.date).append(">").append(message.content)
                .append("</p>");

        lastID = parseInt(message.id) + 1;
        console.log(lastID + " computed from " + message.id);

    }

    function sendMessage(messageContent) {
        $.ajax({
            type : 'POST',
            url : rootURL + "/messages",
            contentType : 'application/json',
            data : JSON.stringify({
                "content" : messageContent
            }),
            dataType : "json",
            success : function(data) {
                console.log("create message " + data.id);
            },
            error : function(jqXHR, textStatus, errorThrown) {
                console.log('sendMessage error: ' + textStatus);
            }
        });
    }

    function sendContent() {
        var ligne = $("#ligne").val();
        $("#ligne").val("");
        sendMessage(ligne);
    }

    $("#submitButton").click(function() {
        sendContent();
    });

    $(window).keydown(function(event) {
        if (event.keyCode == 13) {
            event.preventDefault();
            sendContent();
            return false;
        }
    });

    updateMessages();

});