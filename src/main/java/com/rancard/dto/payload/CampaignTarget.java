/*(C) Gaspay App 2023 */
package com.rancard.dto.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rancard.enums.CampaignTargetType;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CampaignTarget {
    private List<CampaignTargetType> targetType;
    private List<String> targetIds;
    private String targetName;
    private String targetDescription;
    private String targetLocation;
    private String targetRegion;
    private String targetZone;
    private String targetCountry;
    private String targetCity;
    private String targetState;
    private String targetDormancyPeriod;
    private String targetLastTransactionDate;
    private String targetLastTransactionAmount;
    private String targetAgeOnPlatform;
    private String targetGender;
    private String targetMaritalStatus;
    private String targetOccupation;
    private String targetIncome;
    private String targetEducation;
    private String targetNumberOfChildren;
    private String targetNumberOfDependents;
    private String targetNumberOfAccounts;
    private String targetNumberOfLoans;
    private String targetNumberOfDeposits;
    private String targetNumberOfWithdrawals;
    private String targetNumberOfTransfers;
}
