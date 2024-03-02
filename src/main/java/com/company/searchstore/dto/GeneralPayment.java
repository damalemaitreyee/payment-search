package com.company.searchstore.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeneralPayment {
    private String Covered_Recipient_First_Name;
    private String Covered_Recipient_Last_Name;
    private String Recipient_City;
    private String Recipient_Country;
    private String Covered_Recipient_Specialty_1;
    private String Change_Type;
    private String Covered_Recipient_Type;
    private String Teaching_Hospital_CCN;
    private String Teaching_Hospital_ID;
    private String Teaching_Hospital_Name;
    private String Covered_Recipient_Profile_ID;
    private String Covered_Recipient_NPI;
    private String Covered_Recipient_Middle_Name;
    private String Covered_Recipient_Name_Suffix;
    private String Recipient_Primary_Business_Street_Address_Line1;
    private String Recipient_Primary_Business_Street_Address_Line2;
    private String Recipient_State;
    private String Recipient_Zip_Code;
    private String Recipient_Province;
    private String Recipient_Postal_Code;
    private String Covered_Recipient_Primary_Type_1;
    private String Covered_Recipient_Primary_Type_2;
    private String Covered_Recipient_Primary_Type_3;
    private String Covered_Recipient_Primary_Type_4;
    private String Covered_Recipient_Primary_Type_5;
    private String Covered_Recipient_Primary_Type_6;
    private String Covered_Recipient_Specialty_2;
    private String Covered_Recipient_Specialty_3;
    private String Covered_Recipient_Specialty_4;
    private String Covered_Recipient_Specialty_5;
    private String Covered_Recipient_Specialty_6;
    private String Covered_Recipient_License_State_code1;
    private String Covered_Recipient_License_State_code2;
    private String Covered_Recipient_License_State_code3;
    private String Covered_Recipient_License_State_code4;
    private String Covered_Recipient_License_State_code5;
    private String Submitting_Applicable_Manufacturer_or_Applicable_GPO_Name;
    private String Applicable_Manufacturer_or_Applicable_GPO_Making_Payment_ID;
    private String Applicable_Manufacturer_or_Applicable_GPO_Making_Payment_Name;
    private String Applicable_Manufacturer_or_Applicable_GPO_Making_Payment_State;
    private String Applicable_Manufacturer_or_Applicable_GPO_Making_Payment_Country;
    private String Total_Amount_of_Payment_USDollars;
    private String Date_of_Payment;
    private String Number_of_Payments_Included_in_Total_Amount;
    private String Form_of_Payment_or_Transfer_of_Value;
    private String Nature_of_Payment_or_Transfer_of_Value;
    private String City_of_Travel;
    private String State_of_Travel;
    private String Country_of_Travel;
    private String Physician_Ownership_Indicator;
    private String Third_Party_Payment_Recipient_Indicator;
    private String Name_of_Third_Party_Entity_Receiving_Payment_or_Transfer_of_Value;
    private String Charity_Indicator;
    private String Third_Party_Equals_Covered_Recipient_Indicator;
    private String Contextual_Information;
    private String Delay_in_Publication_Indicator;
    private String Record_ID;
    private String Dispute_Status_for_Publication;
    private String Related_Product_Indicator;
    private String Covered_or_Noncovered_Indicator_1;
    private String Indicate_Drug_or_Biological_or_Device_or_Medical_Supply_1;
    private String Product_Category_or_Therapeutic_Area_1;
    private String Name_of_Drug_or_Biological_or_Device_or_Medical_Supply_1;
    private String Associated_Drug_or_Biological_NDC_1;
    private String Associated_Device_or_Medical_Supply_PDI_1;
    private String Covered_or_Noncovered_Indicator_2;
    private String Indicate_Drug_or_Biological_or_Device_or_Medical_Supply_2;
    private String Product_Category_or_Therapeutic_Area_2;
    private String Name_of_Drug_or_Biological_or_Device_or_Medical_Supply_2;
    private String Associated_Drug_or_Biological_NDC_2;
    private String Associated_Device_or_Medical_Supply_PDI_2;
    private String Covered_or_Noncovered_Indicator_3;
    private String Indicate_Drug_or_Biological_or_Device_or_Medical_Supply_3;
    private String Product_Category_or_Therapeutic_Area_3;
    private String Name_of_Drug_or_Biological_or_Device_or_Medical_Supply_3;
    private String Associated_Drug_or_Biological_NDC_3;
    private String Associated_Device_or_Medical_Supply_PDI_3;
    private String Covered_or_Noncovered_Indicator_4;
    private String Indicate_Drug_or_Biological_or_Device_or_Medical_Supply_4;
    private String Product_Category_or_Therapeutic_Area_4;
    private String Name_of_Drug_or_Biological_or_Device_or_Medical_Supply_4;
    private String Associated_Drug_or_Biological_NDC_4;
    private String Associated_Device_or_Medical_Supply_PDI_4;
    private String Covered_or_Noncovered_Indicator_5;
    private String Indicate_Drug_or_Biological_or_Device_or_Medical_Supply_5;
    private String Product_Category_or_Therapeutic_Area_5;
    private String Name_of_Drug_or_Biological_or_Device_or_Medical_Supply_5;
    private String Associated_Drug_or_Biological_NDC_5;
    private String Associated_Device_or_Medical_Supply_PDI_5;
    private String Program_Year;
    private String Payment_Publication_Date;

}
