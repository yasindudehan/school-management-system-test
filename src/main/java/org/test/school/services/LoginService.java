package org.test.school.services;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpPrincipal;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.java.Log;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.test.school.dto.LoginResponse;
import org.test.school.dto.ResponseWrapper;
import org.test.school.dto.UserLoginRequest;
import org.test.school.utils.exceptions.APIException;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

@ApplicationScoped
public class LoginService {

    @ConfigProperty(name = "keycloak.token.api")
    String tokenApi;

    @ConfigProperty(name="quarkus.oidc.client-id")
    String clientId;
    @ConfigProperty(name = "quarkus.oidc.credentials.secret")
    String clientSecret;

    public ResponseWrapper login(UserLoginRequest request) throws APIException, JsonProcessingException {
        try {
            String formData = "client_id=" + URLEncoder.encode(clientId, StandardCharsets.UTF_8) +
                    "&client_secret=" + URLEncoder.encode(clientSecret, StandardCharsets.UTF_8) +
                    "&grant_type=password"+
                    "&username=" +URLEncoder.encode(request.getName(), StandardCharsets.UTF_8) +
                    "&password=" +URLEncoder.encode(request.getPassword(), StandardCharsets.UTF_8);

            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(tokenApi))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(formData))
                    .build();

            HttpClient httpClient = HttpClient.newHttpClient();
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                ResponseWrapper<LoginResponse> responseWrapper = new ResponseWrapper();
                ObjectMapper om = new ObjectMapper();
                responseWrapper.setData(om.readValue(response.body(), LoginResponse.class));
                var data=response.body();
                responseWrapper.setMessage("Login Successfully");
                return responseWrapper;
            } else {
                throw new APIException("Unauthorized ", 401);
            }
        }catch(APIException ex){
                throw ex;
            }
        catch(Exception ex){
            throw new APIException("Internal Server Error",500);
        }
    }
}
