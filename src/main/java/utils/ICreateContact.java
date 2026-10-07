package utils;

import dto.ContactDto;
import dto.ResponseMessageDto;
import dto.TokenDto;
import dto.UserLombok;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;

import static utils.ContactFactory.positiveContact;
import static utils.PropertiesReader.getProperty;

public interface ICreateContact extends BaseApi{
    default String createContact() {
        UserLombok user = UserLombok.builder()
                .username(getProperty("base.properties", "email"))
                .password(getProperty("base.properties", "password"))
                .build();
        RequestBody requestBody = RequestBody.create(GSON.toJson(user), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + LOGIN_URL)
                .post(requestBody)
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        TokenDto tokenDto;
        try {
            tokenDto = GSON.fromJson(response.body().string(), TokenDto.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ContactDto contact = positiveContact();
        System.out.println(contact);
        System.out.println(tokenDto.getToken());
        RequestBody requestBody2 = RequestBody.create(GSON.toJson(contact), JSON);
        Request request2 = new Request.Builder()
                .url(BASE_URL + PUT_CONTACT)
                .addHeader(AUTH, tokenDto.getToken())
                .post(requestBody2)
                .build();
        Response response2;
        try {
            response2 = OK_HTTP_CLIENT.newCall(request2).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
            ResponseMessageDto responseMessageDto;
            try {
                responseMessageDto = GSON.fromJson(response2.body().string(),
                        ResponseMessageDto.class);
                String idContact = responseMessageDto
                        .getMessage().split("ID: ")[1];
                return idContact;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }}


