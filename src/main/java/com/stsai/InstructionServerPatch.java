package com.stsai;

import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@SpireInitializer
public class InstructionServerPatch {
    public static void initialize() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 8001), 0);
        server.createContext("/test", new  MyHttpHandler());
        server.createContext("/reset", new ResetHandler());

        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
        server.setExecutor(threadPoolExecutor);
        server.start();

        final Logger logger = LogManager.getLogger(InstructionServerPatch.class.getName());
        logger.info("Server started on port 8001");

    }
}

class MyHttpHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String requestParamValue=null;
        if("GET".equals(httpExchange.getRequestMethod())) {
            requestParamValue = handleGetRequest(httpExchange);
        }

        handleResponse(httpExchange,requestParamValue);
    }

    private String handleGetRequest(HttpExchange httpExchange) {
        String parsedQueryString = httpExchange.getRequestURI()
                .toString()
                .split("\\?")[1]
                .split("=")[1];

        return "filler";
    }

    private void handleResponse(HttpExchange httpExchange, String requestParamValue)  throws  IOException {
        OutputStream outputStream = httpExchange.getResponseBody();
        StringBuilder htmlBuilder = new StringBuilder();

        htmlBuilder.append("<html>")
                .append("<body>")
                .append("<h1>")
                .append("Hello ")
                .append(requestParamValue)
                .append("</h1>")
                .append("</body>")
                .append("</html>");

        // encode HTML content
        String htmlResponse = htmlBuilder.toString();

        // this line is a must
        httpExchange.sendResponseHeaders(200, htmlResponse.length());
        outputStream.write(htmlResponse.getBytes());
        outputStream.flush();
        outputStream.close();
    }
}

class ResetHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        handleResponse(httpExchange);
    }

    private void handleResponse(HttpExchange httpExchange)  throws  IOException {
        OutputStream outputStream = httpExchange.getResponseBody();
        AbstractDungeon.actionManager.callEndTurnEarlySequence();

        StringBuilder htmlBuilder = new StringBuilder();
        htmlBuilder.append("<html>")
                .append("<body>")
                .append("<h1>")
                .append("Reset")
                .append("</h1>")
                .append("</body>")
                .append("</html>");

        // encode HTML content
        String htmlResponse = htmlBuilder.toString();

        // this line is a must
        httpExchange.sendResponseHeaders(200, htmlResponse.length());
        outputStream.write(htmlResponse.getBytes());
        outputStream.flush();
        outputStream.close();
    }
}