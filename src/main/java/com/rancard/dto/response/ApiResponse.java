/*(C) Gaspay App 2023 */
package com.rancard.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rancard.dto.payload.BaseError;
import java.io.Serializable;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> implements Serializable {

    private int code;
    private String message;
    private T data;
    private long duration;
    private String requestId;
    private BaseError error;

    public ApiResponse() {}

    public ApiResponse(
            int code, String message, T data, long duration, String requestId, BaseError error) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.duration = duration;
        this.requestId = requestId;
        this.error = error;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public BaseError getError() {
        return error;
    }

    public void setError(BaseError error) {
        this.error = error;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApiResponse<?> that = (ApiResponse<?>) o;
        return code == that.code
                && duration == that.duration
                && Objects.equals(message, that.message)
                && Objects.equals(data, that.data)
                && Objects.equals(requestId, that.requestId)
                && Objects.equals(error, that.error);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, message, data, duration, requestId, error);
    }

    @Override
    public String toString() {
        return "ApiResponse{"
                + "code="
                + code
                + ", message='"
                + message
                + '\''
                + ", data="
                + data
                + ", duration="
                + duration
                + ", requestId='"
                + requestId
                + '\''
                + ", error="
                + error
                + '}';
    }
}
