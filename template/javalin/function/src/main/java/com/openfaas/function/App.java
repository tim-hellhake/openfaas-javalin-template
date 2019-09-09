package com.openfaas.function;

import io.javalin.Javalin;

public class App {
    public static void main(String[] args) {
        Javalin app = Javalin.create().start(8082);
        app.get("/", ctx -> ctx.result("Hello World"));
    }
}
