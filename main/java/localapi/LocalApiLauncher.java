package localapi;

import java.io.IOException;
import java.util.Scanner;

public final class LocalApiLauncher {
    public static void main(String[] args) throws IOException {
        int port = args.length > 0 ? Integer.parseInt(args[0]) : 8080;
        LocalApiServer server = new LocalApiServer(port);
        server.start();

        System.out.println("Local API started");
        System.out.println("Base URL: " + server.baseUrl());
        System.out.println("POST endpoint: " + server.baseUrl() + "/users");
        System.out.println("Health endpoint: " + server.baseUrl() + "/health");
        System.out.println("Press Enter to stop...");

        try (Scanner scanner = new Scanner(System.in)) {
            scanner.nextLine();
        } finally {
            server.stop();
        }
    }
}
