package api_tests;

import dto.ContactDto;
import dto.ResponseMessageDto;
import dto.TokenDto;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utils.BaseApi;
import utils.ILogin;

import java.io.IOException;

import static utils.ContactFactory.*;

public class AddContactApiTests implements BaseApi, ILogin {
    TokenDto tokenDto;
    SoftAssert softAssert = new SoftAssert();

    @BeforeClass
    public void login(){
        tokenDto = loginGetToken();
    }

    @Test
    public  void addNewContactPositiveTest(){
        ContactDto contact = positiveContact();
        System.out.println(contact);
        System.out.println(tokenDto.getToken());
        RequestBody requestBody = RequestBody.create(GSON.toJson(contact), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL+ADD_CONTACT)
                .addHeader(AUTH, tokenDto.getToken())
                .post(requestBody)
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assert.assertEquals(response.code(),200);
    }

    @Test
    public  void addNewContactWithSoftAssertPositiveTest(){
        ContactDto contact = positiveContact();
        System.out.println(contact);
        System.out.println(tokenDto.getToken());
        RequestBody requestBody = RequestBody.create(GSON.toJson(contact), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL+ADD_CONTACT)
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
        softAssert.assertEquals(response.code(),200, "validate status code");
        softAssert.assertTrue(responseMessageDto.getMessage().contains("Contact was added!"), "validate message");
        softAssert.assertAll();
    }
}
