package ua.bish.project.security.services;

public interface SecurityService {
    String findLoggedInUsername();

    void autoLogin(String username, String password);
}
