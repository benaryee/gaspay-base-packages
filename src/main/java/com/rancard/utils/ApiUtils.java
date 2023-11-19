package com.rancard.basepackages.utils;

import com.rancard.basepackages.enums.ResponseMessage;
import com.rancard.basepackages.response.ApiResponse;
import com.rancard.basepackages.response.PagedContent;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@SuppressWarnings({"rawtypes", "unused"})

public class ApiUtils {

    @SuppressWarnings("unchecked")
    public static <T, W> ApiResponse<PagedContent<W>> wrapInPagedApiResponse(Page page, List<W> data, String sessionId) {
        ApiResponse<PagedContent<W>> response = new ApiResponse<>();
        response.setCode(ResponseMessage.SUCCESS.getCode());
        response.setMessage(ResponseMessage.SUCCESS.toString());
        response.setRequestId(sessionId);
        response.setData(new PagedContent(page, data));
        return response;
    }

    public static <T> ApiResponse<T> wrapInApiResponse(T data, String requestId) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setCode(ResponseMessage.SUCCESS.getCode());
        response.setMessage(ResponseMessage.SUCCESS.toString());
        response.setRequestId(requestId);
        response.setData(data);
        return response;
    }
}
