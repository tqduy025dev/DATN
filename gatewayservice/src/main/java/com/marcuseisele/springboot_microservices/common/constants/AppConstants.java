package com.server.datn.server.common.constants;

import java.util.Arrays;
import java.util.List;

public class AppConstants {
    public static final String COMPANY_KEY                                      = "base.company.code";
    public static final String urlDowloadImage                                  = "https://storage.googleapis.com/demojavaspring.appspot.com/";

    public final static String SUCC_CODE                                        = "00";
    public final static String FAIL_CODE                                        = "99";
    public final static String BAD_REQUEST_CODE                                 = "90";
    public final static String UNAUTHORIZED_CODE                                = "91";
    public final static String CONFLICT_CODE                                    = "92";
    public final static String CREATED_CODE                                     = "01";
    public final static String ACCEPTED_CODE                                    = "02";

    public final static String STATUS_ACTIVE                                    = "ACTV";
    public final static String STATUS_DELETE                                    = "DLTD";
    public final static String STATUS_PENDING                                   = "PEND";
    public final static String STATUS_APPROVE                                   = "APRV";
    public final static String STATUS_REJECT                                    = "RJCT";
    public final static List<String> ACTIONS                                    = Arrays.asList("RJCT", "DLTD", "PEND", "APRV");

    public final static String STATUS_D0                                        = "D0";//Xoa thanh cong
    public final static String STATUS_D1                                        = "D1";//ban ghi da xoa
    public final static String STATUS_D2                                        = "D2";//Khong ton tai ban ghi
    public final static String STATUS_C0                                        = "C0";
    public final static String STATUS_C1                                        = "C1";
    public final static String STATUS_C2                                        = "C2";
    public final static String STATUS_U0                                        = "U0";//Cap nhat thanh cong
    public final static String STATUS_U1                                        = "U1";
    public final static String STATUS_U2                                        = "U2";//Khong ton tai ban ghi
    public final static String PASSWORD_NOT_MATCH                               = "P2";
    public final static String PASSWORD_UNVALID                                 = "P1";

    public final static String DEFAULT_FORMAT_DATE                              = "MM/dd/yyyy";
    public final static String DEFAULT_FORMAT_DATE_KEY                          = "MMddyyyy";
    public final static String DEFAULT_FORMAT_DATE_TIME                         = "MM/dd/yyyy HH:mm:ss";
    public final static String DEFAULT_FORMAT_TIME                              = "HH:mm";


    public final static String FIELD_EMPLOYEE_ID                                = "employeeId";
    public final static String FIELD_EMPLOYEE_EMAIL                             = "email";
    public final static String FIELD_EMPLOYEE_FIRSTNAME                         = "firstName";
    public final static String FIELD_EMPLOYEE_MIDDLENAME                        = "middleName";
    public final static String FIELD_EMPLOYEE_LASTNAME                          = "lastName";
    public final static String FIELD_EMPLOYEE_ADDRESS                           = "address";
    public final static String FIELD_EMPLOYEE_PHONE                             = "phone";
    public final static String FIELD_EMPLOYEE_STATUS                            = "status";
    public final static String FIELD_EMPLOYEE_GENDER                            = "gender";
    public final static String FIELD_EMPLOYEE_DAYOFBIRTH                        = "dayOfBirth";


    public final static String FIELD_OFFICE_ID                                  = "officeId";
    public final static String FIELD_OFFICE_NAME                                = "officeName";
    public final static String FIELD_OFFICE_STATUS                              = "status";

    public final static String CHECK_IN                                         = "CHECKIN";
    public final static String CHECK_OUT                                        = "CHECKOUT";

}
