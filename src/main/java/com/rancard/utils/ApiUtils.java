/*(C) Gaspay App 2023-2024 */
package com.rancard.utils;

import com.rancard.dto.response.ApiResponse;
import com.rancard.dto.response.PagedContent;
import com.rancard.enums.ResponseMessage;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@SuppressWarnings({"rawtypes", "unused"})
@Component
public class ApiUtils {

    @SuppressWarnings("unchecked")
    public static <T, W> ApiResponse<PagedContent<W>> wrapInPagedApiResponse(
            Page page, List<W> data, String sessionId, long... start) {
        ApiResponse<PagedContent<W>> response = new ApiResponse<>();
        response.setCode(ResponseMessage.SUCCESS.getCode());
        response.setMessage(ResponseMessage.SUCCESS.toString());
        response.setRequestId(sessionId);
        response.setData(new PagedContent(page, data));
        if (start.length > 0) {
            response.setDuration(System.currentTimeMillis() - start[0]);
        }
        return response;
    }

    public static <T> ApiResponse<T> wrapInApiResponse(T data, String requestId, long... start) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setCode(ResponseMessage.SUCCESS.getCode());
        response.setMessage(ResponseMessage.SUCCESS.toString());
        response.setRequestId(requestId);
        response.setData(data);
        if (start.length > 0) {
            response.setDuration(System.currentTimeMillis() - start[0]);
        }
        return response;
    }


    public static <T> ApiResponse<T> wrapInApiResponse(T data, String requestId, String message, int code, long... start) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setCode(code);
        response.setMessage(message);
        response.setRequestId(requestId);
        response.setData(data);
        return response;
    }

    public static <T> ApiResponse<T> wrapInApiResponse(ApiResponse<T> apiResponse, long... start) {
        if (start.length > 0) {
            apiResponse.setDuration(System.currentTimeMillis() - start[0]);
        }
        return apiResponse;
    }
}
