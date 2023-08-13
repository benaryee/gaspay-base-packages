package com.rancard.auth.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.rancard.auth.exception.ServiceException;
import com.rancard.auth.model.UserRepository;
import com.rancard.auth.model.mongo.User;
import com.rancard.auth.model.payload.Address;
import com.rancard.auth.model.payload.LocationInfo;
import com.rancard.auth.model.payload.LocationResponse;
import com.rancard.auth.model.response.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.modelmapper.TypeToken;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Objects;

import static com.rancard.auth.model.enums.ServiceError.ADDRESS_NOT_FOUND_EXCEPTION;
import static com.rancard.auth.model.enums.ServiceError.USER_NOT_FOUND;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final Gson gson;
    public User getUserByMsisdn(String msisdn){
        return userRepository.findByMsisdn(msisdn).orElseThrow(()->
                new ServiceException(USER_NOT_FOUND));
    }

    public Address getUserAddressByGps(String ghanaPostGps){

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "address=" + ghanaPostGps);
        Request request = new Request.Builder()
                .url("https://ghanapostgps.sperixlabs.org/get-location")
                .method("POST", body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        try (Response response = client.newCall(request).execute()) {

            if (response.body() != null) {
                String jsonResponse = Objects.requireNonNull(response.body()).string();
                LocationResponse locationResponse = parseJson(jsonResponse);

                if (locationResponse.isFound()) {
                    LocationInfo locationInfo = locationResponse.getData().getTable().get(0);
                    return mapToFormattedAddress(locationInfo);
                }
                throw new ServiceException(ADDRESS_NOT_FOUND_EXCEPTION);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    private LocationResponse parseJson(String json) {
        Type responseType = new TypeToken<LocationResponse>(){}.getType();
        return gson.fromJson(json, responseType);
    }

    private Address mapToFormattedAddress(LocationInfo locationInfo) {
        Address formattedAddress = new Address();
        formattedAddress.setStreet(locationInfo.getStreet());
        formattedAddress.setCity(locationInfo.getDistrict());
        formattedAddress.setState(locationInfo.getRegion());
        formattedAddress.setPostalCode(locationInfo.getPostCode());
        formattedAddress.setLocation(locationInfo.getGPSName());
        formattedAddress.setLongitude(locationInfo.getCenterLongitude());
        formattedAddress.setLatitude(locationInfo.getCenterLatitude());
        formattedAddress.setGhanaPostGps(locationInfo.getGPSName());
        return formattedAddress;
    }
}