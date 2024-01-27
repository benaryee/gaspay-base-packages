/*(C) Gaspay App 2024 */
package com.rancard.dto.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rancard.enums.Status;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class DrmResponse<T> implements Serializable {
    private int code;
    private String message;
    private boolean continueSession;
    private T data;
    private boolean proceedToNextStep;
    private boolean returnResponse;

    public DrmResponse<T> toDrmResponse(
            String message, Status status, boolean continueSession, T data) {
        switch (status) {
            case SUCCESS:
                this.setCode(200);
                this.setMessage(message);
                break;
            case FAILED:
                this.setCode(400);
                this.setMessage(message);
                break;
            case ERROR:
                this.setCode(500);
                this.setMessage(message);
                break;
        }

        this.setContinueSession(continueSession);
        this.setData(data);

        return this;
    }

    public DrmResponse<T> toDrmResponseStatus(Status status) {
        switch (status) {
            case SUCCESS:
                this.setCode(200);
                break;
            case FAILED:
                this.setCode(400);
                break;
            case ERROR:
                this.setCode(500);
                break;
        }
        return this;
    }
}
