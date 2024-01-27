/*(C) Gaspay App 2024 */
package com.rancard.dto.request;

import java.net.URI;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;

@Data
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class RequestDetails {
    private URI url;
    private HttpMethod requestType;
}
