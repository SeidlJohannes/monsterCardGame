package at.fhtw.mcg.controller;

import at.fhtw.httpserver.http.ContentType;
import at.fhtw.httpserver.http.HttpStatus;
import at.fhtw.httpserver.http.Method;
import at.fhtw.httpserver.server.Request;
import at.fhtw.httpserver.server.Response;
import at.fhtw.httpserver.server.RestController;
import at.fhtw.mcg.persistence.repository.UserRepository;
import at.fhtw.mcg.service.PackageService;
import at.fhtw.mcg.service.UserService;

public class PackageController implements RestController {
    private final PackageService packageService;

    public PackageController() {
        this.packageService = new PackageService();
    }

    @Override
    public Response handleRequest(Request request) {
        String body = request.getBody();
        if (request.getMethod() == Method.GET) {
            return this.packageService.getUserPerRepository();
        }//Body is empty -> acquire package
        else if (request.getMethod() == Method.POST && (body == null || body.trim().isEmpty() || body.equals("\"\""))) {
            return this.packageService.acquirePackage(request);
        }//Body NOT empty -> create package
        else if (request.getMethod() == Method.POST) {
            return this.packageService.createPackage(request);
        }

        return new Response(
                HttpStatus.BAD_REQUEST,
                ContentType.JSON,
                "[]"
        );
    }
}
