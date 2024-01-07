/*(C) Gaspay App 2023-2024 */
package com.rancard.mongo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Enquiry extends BaseMongoModel {

    private String msisdn;
    private String request;
    private boolean callback;
    private String alternativeNumber;
    private boolean respondedTo;
}
