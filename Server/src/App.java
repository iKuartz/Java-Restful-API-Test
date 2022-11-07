public static void main(String[] args) throws Exception {
    HttpServer server = HttpServer.create(new InetSocketAddress(8500), 0);
    HttpContext context = server.createContext("/test");
    context.setHandler(Sample::handleRequest);
    server.start();
    System.out.println("Server started on port 8500");
}    
 
private static void handleRequest(final HttpExchange exchange) throws IOException {
    final String json = "{\"weight\":\"23400\"}";
    final String origin = exchange.getRequestHeaders().getFirst("Origin");
    if(origin != null) exchange.getResponseHeaders().add("Access-Control-Allow-Origin", origin);
    exchange.getResponseHeaders().add("Access-Control-Allow-Headers","origin, content-type, accept, authorization");
    exchange.getResponseHeaders().add("Access-Control-Allow-Credentials", "true");
    exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
    exchange.getResponseHeaders().set("contentType", "application/json; charset=UTF-8");
    exchange.sendResponseHeaders(200, json.getBytes().length);
    try(OutputStream os = exchange.getResponseBody()) {
        os.write(json.getBytes());
        os.flush();
    }
    exchange.close();
}
