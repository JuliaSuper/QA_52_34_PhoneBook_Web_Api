package api_tests;

import data_providers.ContactDataProvider;
import dto.*;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utils.BaseApi;
import utils.ILogin;

import java.io.IOException;

import static utils.ContactFactory.*;
import static utils.PropertiesReader.getProperty;

public class AddContactApiTests implements BaseApi, ILogin {
    TokenDto tokenDto;
    SoftAssert softAssert = new SoftAssert();


    @BeforeClass
    public void login() {
        tokenDto = loginGetToken();
    }

    @Test
    public void addNewContactPositiveTest() {
        ContactDto contact = positiveContact();
        System.out.println(contact);
        System.out.println(tokenDto.getToken());
        RequestBody requestBody = RequestBody.create(GSON.toJson(contact), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + PUT_CONTACT)
                .addHeader(AUTH, tokenDto.getToken())
                .post(requestBody)
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assert.assertEquals(response.code(), 200);
    }

    @Test
    public void addNewContactWithSoftAssertPositiveTest() {
        ContactDto contact = positiveContact();
        System.out.println(contact);
        System.out.println(tokenDto.getToken());
        RequestBody requestBody = RequestBody.create(GSON.toJson(contact), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + PUT_CONTACT)
                .addHeader(AUTH, tokenDto.getToken())
                .post(requestBody)
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ResponseMessageDto responseMessageDto;
        try {
            responseMessageDto = GSON.fromJson(response.body().string(),
                    ResponseMessageDto.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(responseMessageDto);
        softAssert.assertEquals(response.code(), 200, "validate status code");
        softAssert.assertTrue(responseMessageDto.getMessage().contains("Contact was added!"), "validate message");
        softAssert.assertAll();
    }

    @Test
    public void addContactNegative_400_Test() {
        UserLombok user = UserLombok.builder()
                .username(getProperty("base.properties", "email"))
                .password(getProperty("base.properties", "password"))
                .build();

        RequestBody loginBody = RequestBody.create(GSON.toJson(user), JSON);
        Request loginRequest = new Request.Builder()
                .url(BASE_URL + LOGIN_URL)
                .post(loginBody)
                .build();

        String token = "";
        try (Response response = OK_HTTP_CLIENT.newCall(loginRequest).execute()) {
            if (response.body() != null) {
                com.google.gson.JsonObject jsonObject = com.google.gson.JsonParser
                        .parseString(response.body().string())
                        .getAsJsonObject();

                token = jsonObject.get("token").getAsString();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String brokenContactJson = "{\"name\":\"TestName\"}";

        RequestBody requestBody = RequestBody.create(brokenContactJson, JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + PUT_CONTACT)
                .addHeader(AUTH, token)
                .post(requestBody)
                .build();

        try (Response response = OK_HTTP_CLIENT.newCall(request).execute()) {
            System.out.println("Response code: " + response.code());
            Assert.assertEquals(response.code(), 400);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void addContactNegative_401_Test() {
        String contactJson = "{"
                + "\"name\": \"John\","
                + "\"lastName\": \"Doe\","
                + "\"email\": \"john@mail.com\","
                + "\"phone\": \"1234567890\","
                + "\"address\": \"Street 1\","
                + "\"description\": \"Friend\""
                + "}";

        String invalidToken = "invalid_jwt_token_string_12345";

        RequestBody requestBody = RequestBody.create(contactJson, JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + PUT_CONTACT)
                .addHeader(AUTH, invalidToken)
                .post(requestBody)
                .build();

        try (Response response = OK_HTTP_CLIENT.newCall(request).execute()) {
            System.out.println("Response code: " + response.code());
            Assert.assertEquals(response.code(), 401);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void getAllContactsNegative_403_Test() {
        Request request = new Request.Builder()
                .url(BASE_URL + PUT_CONTACT)
                .addHeader(AUTH, "")
                .get()
                .build();

        try (Response response = OK_HTTP_CLIENT.newCall(request).execute()) {
            System.out.println("Response code: " + response.code());
            Assert.assertEquals(response.code(), 403);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void addNewContactWrongTokenNegativeTest() {
        ContactDto contact = positiveContact();
        System.out.println(contact);
        System.out.println(tokenDto.getToken());
        RequestBody requestBody = RequestBody.create(GSON.toJson(contact), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + PUT_CONTACT)
                .addHeader(AUTH, "tokenDto.getToken()")
                .post(requestBody)
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assert.assertEquals(response.code(), 401);
    }

    @Test
    public void addNewContactWOTokenNegativeTest() {
        ContactDto contact = positiveContact();
        System.out.println(contact);
        System.out.println(tokenDto.getToken());
        RequestBody requestBody = RequestBody.create(GSON.toJson(contact), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + PUT_CONTACT)

                .post(requestBody)
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assert.assertEquals(response.code(), 403);
    }
    @Test(dataProvider = "dataProviderWrongContact",
            dataProviderClass = ContactDataProvider.class)
    public void registrationNegativeWrongContact_400_Test(ContactDto contactDto) {
        UserLombok user = UserLombok.builder()
                .username(getProperty("base.properties", "email"))
                .password(getProperty("base.properties", "password"))
                .build();

        RequestBody loginBody = RequestBody.create(GSON.toJson(user), JSON);
        Request loginRequest = new Request.Builder()
                .url(BASE_URL + LOGIN_URL)
                .post(loginBody)
                .build();

        String token = "";
        try (Response response = OK_HTTP_CLIENT.newCall(loginRequest).execute()) {
            if (response.body() != null) {
                com.google.gson.JsonObject jsonObject = com.google.gson.JsonParser
                        .parseString(response.body().string())
                        .getAsJsonObject();

                token = jsonObject.get("token").getAsString();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String brokenContactJson = GSON.toJson(contactDto);

        RequestBody requestBody = RequestBody.create(brokenContactJson, JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + PUT_CONTACT)
                .addHeader(AUTH, token)
                .post(requestBody)
                .build();

        try (Response response = OK_HTTP_CLIENT.newCall(request).execute()) {
            System.out.println("Response code: " + response.code());
            Assert.assertEquals(response.code(), 400);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void registrationDuplicateContact_409_Tests() {
        UserLombok user = UserLombok.builder()
                .username(getProperty("base.properties", "email"))
                .password(getProperty("base.properties", "password"))
                .build();

        RequestBody loginBody = RequestBody.create(GSON.toJson(user), JSON);
        Request loginRequest = new Request.Builder()
                .url(BASE_URL + LOGIN_URL)
                .post(loginBody)
                .build();

        String token = "";
        try (Response response = OK_HTTP_CLIENT.newCall(loginRequest).execute()) {
            if (response.body() != null) {
                com.google.gson.JsonObject jsonObject = com.google.gson.JsonParser
                        .parseString(response.body().string())
                        .getAsJsonObject();

                token = jsonObject.get("token").getAsString();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ContactDto contact = ContactDto.builder()
                .id("")
                .name("John")
                .lastName("Doe")
                .email("john_duplicate@test.com")
                .phone("1234567890")
                .address("Berlin")
                .description("For duplicate test")
                .build();

        RequestBody requestBody = RequestBody.create(GSON.toJson(contact), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + PUT_CONTACT)
                .addHeader(AUTH, token)
                .post(requestBody)
                .build();
        String generatedId = "";
        try (Response response = OK_HTTP_CLIENT.newCall(request).execute()) {
            System.out.println("First creation status: " + response.code());
            if (response.body() != null) {
                String responseBodyStr = response.body().string();
                com.google.gson.JsonObject jsonObject = com.google.gson.JsonParser
                        .parseString(responseBodyStr).getAsJsonObject();
                if (jsonObject.has("id")) {
                    generatedId = jsonObject.get("id").getAsString();
                    System.out.println("Generated ID: " + generatedId);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ContactDto duplicateContact = ContactDto.builder()
                .id(generatedId)
                .name("John")
                .lastName("Doe")
                .email("john_duplicate@test.com")
                .phone("1234567890")
                .address("Berlin")
                .description("For duplicate test")
                .build();

        RequestBody duplicateRequestBody = RequestBody.create(GSON.toJson(duplicateContact), JSON);
        Request duplicateRequest = new Request.Builder()
                .url(BASE_URL + PUT_CONTACT)
                .addHeader(AUTH, token)
                .post(duplicateRequestBody)
                .build();
        try (Response response = OK_HTTP_CLIENT.newCall(request).execute()) {
            System.out.println("Duplicate request status: " + response.code());
            Assert.assertEquals(response.code(), 409);
            if (response.body() != null) {
                String bodyString = response.body().string();
                ErrorMessageDto errorResponse = GSON.fromJson(bodyString, ErrorMessageDto.class);
                Assert.assertEquals(errorResponse.getStatus(), 409);
                System.out.println("Server error message: " + errorResponse.getMessage());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    } // тест вместо 409 дает 200

}
