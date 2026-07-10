package localapi;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicInteger;

public final class LocalApiServer {
    private static final Gson GSON = new Gson();
    private final AtomicInteger nextId = new AtomicInteger(1);
    private final int port;
    private HttpServer server;

    public LocalApiServer() {
        this(8080);
    }

    public LocalApiServer(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        if (server != null) {
            return;
        }

        server = HttpServer.create(new InetSocketAddress("localhost", port), 0);
        server.createContext("/health", exchange -> writeJson(exchange, 200, "{\"status\":\"ok\"}"));
        server.createContext("/users", new UsersHandler());
        server.setExecutor(null);
        server.start();
    }

    public void stop() {
        if (server != null) {
            server.stop(0);
            server = null;
        }
    }

    public String baseUrl() {
        if (server == null) {
            throw new IllegalStateException("LocalApiServer has not been started");
        }

        return "http://localhost:" + server.getAddress().getPort();
    }

    private final class UsersHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            try {
                if (!"POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                    writeJson(exchange, 405, "{\"error\":\"method not allowed\"}");
                    return;
                }

                String requestBody = readBody(exchange.getRequestBody());
                JsonObject payload = requestBody.isBlank()
                        ? new JsonObject()
                        : GSON.fromJson(requestBody, JsonObject.class);

                if (payload == null) {
                    payload = new JsonObject();
                }

                payload.addProperty("id", nextId.getAndIncrement());
                payload.addProperty("createdAt", System.currentTimeMillis());

                writeJson(exchange, 201, GSON.toJson(payload));
            } finally {
                exchange.close();
            }
        }
    }

    private static String readBody(InputStream inputStream) throws IOException {
        return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
    }

    private static void writeJson(HttpExchange exchange, int statusCode, String body) throws IOException {
        byte[] response = body.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().add("Content-Type", "application/json; charset=utf-8");
        exchange.sendResponseHeaders(statusCode, response.length);
        try (OutputStream outputStream = exchange.getResponseBody()) {
            outputStream.write(response);
        }
    }
}
