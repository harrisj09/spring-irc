module irc.client {
    requires javafx.controls;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;
    requires static lombok;
    requires java.logging;
    opens com.github.harrisj09.irc.ircclient;
    //opens com.github.harrisj09.irc.ircclient.data;
    exports com.github.harrisj09.irc.ircclient.data.dtos;
}