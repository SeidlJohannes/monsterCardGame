package at.fhtw;

import at.fhtw.httpserver.server.Server;
import at.fhtw.httpserver.utils.Router;
import at.fhtw.mcg.controller.SessionController;
import at.fhtw.mcg.controller.UserController;
import at.fhtw.mcg.persistence.UnitOfWork;
import at.fhtw.mcg.persistence.repository.UserRepository;
import at.fhtw.mcg.persistence.repository.UserRepositoryImpl;
import at.fhtw.sampleapp.controller.EchoController;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Server server = new Server(10001, configureRouter());
        try {
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Router configureRouter()
    {
        Router router = new Router();
        UserRepository userRepository = new UserRepositoryImpl(new UnitOfWork());
        router.addService("/user", new UserController(userRepository));
        router.addService("/sessions", new SessionController(userRepository));
        router.addService("/echo", new EchoController());

        return router;
    }
}
