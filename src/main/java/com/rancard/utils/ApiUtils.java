package com.rancard.utils;

import com.rancard.enums.ResponseMessage;
import com.rancard.response.ApiResponse;
import com.rancard.response.PagedContent;
import java.util.List;

@SuppressWarnings({"rawtypes", "unused"})

public class ApiUtils {

    @SuppressWarnings("unchecked")
//    public static <T, W> ApiResponse<PagedContent<W>> wrapInPagedApiResponse(Page page, List<W> data, String sessionId) {
//        ApiResponse<PagedContent<W>> response = new ApiResponse<>();
//        response.setCode(ResponseMessage.SUCCESS.getCode());
//        response.setMessage(ResponseMessage.SUCCESS.toString());
//        response.setRequestId(sessionId);
//        response.setData(new PagedContent(page, data));
//        return response;
//    }

    public static <T> ApiResponse<T> wrapInApiResponse(T data, String requestId) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setCode(ResponseMessage.SUCCESS.getCode());
        response.setMessage(ResponseMessage.SUCCESS.toString());
        response.setRequestId(requestId);
        response.setData(data);
        return response;
    }
}
