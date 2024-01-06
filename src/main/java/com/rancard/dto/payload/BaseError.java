/*(C) Gaspay App 2023 */
package com.rancard.dto.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.Objects;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseError implements Serializable {

    private int errorCode;
    private String errorMessage;
    private String url;

    public BaseError() {}

    public BaseError(int errorCode, String errorMessage, String url) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.url = url;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseError baseError = (BaseError) o;
        return errorCode == baseError.errorCode
                && Objects.equals(errorMessage, baseError.errorMessage)
                && Objects.equals(url, baseError.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(errorCode, errorMessage, url);
    }

    @Override
    public String toString() {
        return "BaseError{"
                + "errorCode="
                + errorCode
                + ", errorMessage='"
                + errorMessage
                + '\''
                + ", url='"
                + url
                + '\''
                + '}';
    }
}
