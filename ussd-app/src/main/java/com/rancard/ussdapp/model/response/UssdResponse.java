package com.rancard.ussdapp.model.response;

import com.google.gson.Gson;
import com.rancard.ussdapp.model.request.UssdRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UssdResponse {

    private String requestId;
    private Long duration;
    private String message;
    private boolean continueSession;

    public String toUnifierResponse(UssdRequest request){
        Map<String, Object> jsonResponseMap = new HashMap<>();

        String userMessage;

        if(this.getMessage() != null){
            if(request.getMobileNetwork().equals("OT")){
                userMessage  = this.message.replaceAll("&","and");
            }else{
                userMessage = this.message;
            }
        }else{
            userMessage = "Sorry, we were unable to process your request at this time. Please try again later. Ts and Cs Apply.";
        }
        jsonResponseMap.put("message", userMessage);
        jsonResponseMap.put("continueSession", continueSession);
        return new Gson().toJson(jsonResponseMap);
    }
}
