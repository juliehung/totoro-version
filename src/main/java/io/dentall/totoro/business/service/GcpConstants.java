package io.dentall.totoro.business.service;

class GcpConstants {

    private static final String CLOUD_FUNCTION_BASE_URL = "https://asia-east2-dentall-saas.cloudfunctions.net/";

    static final String BUCKET_NAME = "totoro-admin";

    static final String GCS_BASE_URL = "https://storage.googleapis.com/" + BUCKET_NAME + "/";

    static final String SERVICE_ACCOUNT_PREFIX = "dentall-saas-";

    static final String EXECUTE_SMS_EVENT_FUNCTION = CLOUD_FUNCTION_BASE_URL + "executeSmsEvent";

    static final String CHARGE_SMS_FUNCTION = CLOUD_FUNCTION_BASE_URL + "chargeSms";

    static final String UPSERT_SMS_EVENT_FUNCTION = CLOUD_FUNCTION_BASE_URL + "upsertSmsEvent";

    static final String GET_SMS_EVENTS_FUNCTION = CLOUD_FUNCTION_BASE_URL + "getSmsEvents";

    static final String GET_SMS_INFO_FUNCTION = CLOUD_FUNCTION_BASE_URL + "getSmsInfo";

    static final String DELETE_SMS_EVENT_FUNCTION = CLOUD_FUNCTION_BASE_URL + "deleteSmsEvent";
}
