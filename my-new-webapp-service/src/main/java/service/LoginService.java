package service;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface LoginService {
    String returnExistedUserInJson(String session_id, String login, String password) throws JsonProcessingException;
}
