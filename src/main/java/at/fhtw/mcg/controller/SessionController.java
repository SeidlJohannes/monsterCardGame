package at.fhtw.mcg.controller;

import at.fhtw.httpserver.http.ContentType;
import at.fhtw.httpserver.http.HttpStatus;
import at.fhtw.httpserver.http.Method;
import at.fhtw.httpserver.server.Request;
import at.fhtw.httpserver.server.Response;
import at.fhtw.httpserver.server.RestController;
import at.fhtw.mcg.service.SessionService;

public class SessionController implements RestController {
    private final SessionService sessionService;

    public SessionController() {this.sessionService = new SessionService();}

    @Override
    public Response handleRequest(Request request) {
        if (request.getMethod() == Method.GET &&
                request.getPathParts().size() > 1) {
            return this.sessionService.getSession(request.getPathParts().get(1));
        } else if (request.getMethod() == Method.GET) {
            return this.sessionService.getSessionPerRepository();
        } else if (request.getMethod() == Method.POST) {
            return this.sessionService.login(request);
        }

        return new Response(
                HttpStatus.BAD_REQUEST,
                ContentType.JSON,
                "[]"
        );
    }
}
