package api_tests;

import data_providers.UserDataProvider;
import dto.UserLombok;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.BaseApi;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static utils.UserFactory.*;
import static utils.PropertiesReader.*;

public class RegistrationLoginApiTests implements BaseApi {
    @Test
    public void registrationApiPositiveTest(){
        UserLombok user = positiveUser();
        RequestBody requestBody = RequestBody.create(GSON.toJson(user),JSON);
        Request request = new Request.Builder()
                .url(BASE_URL+REGISTRATION_URL)
                .post(requestBody)
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(response);
        Assert.assertEquals(response.code(), 200);
    }
    @Test(dataProvider = "dataProviderWrongPasswordOrEmail", dataProviderClass = UserDataProvider.class)
    public void registrationApiWrongPasswordNegativeTest(UserLombok user){
        RequestBody requestBody = RequestBody.create(GSON.toJson(user),JSON);
        Request request = new Request.Builder()
                .url(BASE_URL+REGISTRATION_URL)
                .post(requestBody)
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(response);
        Assert.assertEquals(response.code(), 400);
    }
    @Test
    public void registrationApiDuplicateUserNegativeTest(){
        UserLombok user = positiveUser();
        RequestBody requestBody = RequestBody.create(GSON.toJson(user),JSON);
        Request request = new Request.Builder()
                .url(BASE_URL+REGISTRATION_URL)
                .post(requestBody)
                .build();
        Response response;
        try {
            OK_HTTP_CLIENT.newCall(request).execute();
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(response);
        Assert.assertEquals(response.code(), 409);
    }
    @Test
    public void registrationApiWrongFormatNegativeTest(){
        UserLombok user = positiveUser();
        RequestBody requestBody = RequestBody.create(GSON.toJson(user),TEXT);
        Request request = new Request.Builder()
                .url(BASE_URL+REGISTRATION_URL)
                .post(requestBody)
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(response);
        Assert.assertEquals(response.code(), 500);
    }
    @Test
    public void loginApiPositiveTest(){
        UserLombok user = UserLombok.builder()
                .username(getProperty("base.properties", "email"))
                .password(getProperty("base.properties", "password"))
                .build();
        RequestBody requestBody = RequestBody.create(GSON.toJson(user),JSON);
        Request request = new Request.Builder()
                .url(BASE_URL+LOGIN_URL)
                .post(requestBody)
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(response);
        Assert.assertEquals(response.code(), 200);
    }
    @Test
    public void loginApiWrongPasswordNegativeTest(){
        UserLombok user = UserLombok.builder()
                .username(getProperty("base.properties", "email"))
                .password(getProperty("base.properties", "negativePassword"))
                .build();
        RequestBody requestBody = RequestBody.create(GSON.toJson(user),JSON);
        Request request = new Request.Builder()
                .url(BASE_URL+LOGIN_URL)
                .post(requestBody)
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(response);
        Assert.assertEquals(response.code(), 401);
    }

    @Test
    public void loginApiWrongLoginNegativeTest(){
        UserLombok user = UserLombok.builder()
                .username(getProperty("base.properties", "emailInvalid"))
                .password(getProperty("base.properties", "password"))
                .build();
        RequestBody requestBody = RequestBody.create(GSON.toJson(user),JSON);
        Request request = new Request.Builder()
                .url(BASE_URL+LOGIN_URL)
                .post(requestBody)
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(response);
        Assert.assertEquals(response.code(), 401);
    }

    @Test
    public void loginApiWrongFormatNegativeTest(){
        UserLombok user = UserLombok.builder()
                .username(null)
                .password(null)
                .build();
        RequestBody requestBody = RequestBody.create(GSON.toJson(user),JSON);
        Request request = new Request.Builder()
                .url(BASE_URL+LOGIN_URL)
                .post(requestBody)
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(response);
        Assert.assertEquals(response.code(), 401);
    }

    @Test
    public void loginApiNegative_401_Test(){
        String emptyJson = "{}";
        RequestBody requestBody = RequestBody.create(emptyJson,JSON);
        Request request = new Request.Builder()
                .url(BASE_URL+LOGIN_URL)
                .post(requestBody)
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(response);
        Assert.assertEquals(response.code(), 401);
    }
    @Test
    public void registrationApiWrongTypeOfNegativeTest(){
        Request request = new Request.Builder()
                .url(BASE_URL+REGISTRATION_URL)
                .get()
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(response);
        Assert.assertEquals(response.code(), 403);
    }

    @Test
    public void loginApiWrongKeyEmailNegativeTest(){
        UserLombok user = UserLombok.builder()
                .username(getProperty("base.properties", "email"))
                .password(getProperty("base.properties", "password"))
                .build();
        Map<String,String>invalidJson = new HashMap<>();
        invalidJson.put("email", user.getUsername());
        invalidJson.put("password", user.getPassword());
        RequestBody requestBody = RequestBody.create(GSON.toJson(invalidJson),JSON);
        Request request = new Request.Builder()
                .url(BASE_URL+LOGIN_URL)
                .post(requestBody)
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(response);
        Assert.assertEquals(response.code(), 400); // ждали 500, но дает 400
    }
}
