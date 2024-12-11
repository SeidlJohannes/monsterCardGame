package at.fhtw.mcg.service;

import at.fhtw.httpserver.http.ContentType;
import at.fhtw.httpserver.http.HttpStatus;
import at.fhtw.httpserver.server.HeaderMap;
import at.fhtw.httpserver.server.Request;
import at.fhtw.httpserver.server.Response;
import at.fhtw.mcg.model.Card;
import at.fhtw.mcg.model.User;
import at.fhtw.mcg.persistence.UnitOfWork;
import at.fhtw.mcg.persistence.repository.UserRepository;
import at.fhtw.mcg.persistence.repository.UserRepositoryImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class PackageService extends AbstractService {

    private ObjectMapper objectMapper = new ObjectMapper();


    // POST /packages(:id
    public Response acquirePackage(Request request)
    {
        System.out.println("Acquire Package");
        HeaderMap headerMap = request.getHeaderMap();
        String tokenBearer = headerMap.getHeader("Authorization"); //Bearer username-mtcgToken
        //System.out.println("tokenBearer: " + tokenBearer); //Debug

        return new Response(HttpStatus.CREATED);
    }

    public Response createPackage(Request request)
    {
        System.out.println("Create Package");
        return new Response(HttpStatus.CREATED);
    }

    // GET /packages
    public Response getUserPerRepository() {
        return new Response(HttpStatus.NOT_IMPLEMENTED);
    }
}
