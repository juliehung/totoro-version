package io.dentall.totoro.business.service;

class GcpConstants {

    static final String CLOUD_FUNCTION_BASE_URL = "https://asia-east2-dentall-saas.cloudfunctions.net/";

    static final String BUCKET_NAME = "totoro-admin";

    static final String GCS_BASE_URL = "https://storage.googleapis.com/" + BUCKET_NAME + "/";

    static final String SERVICE_ACCOUNT = "dentall-saas-cloud-functions.json";

    static final String EXECUTE_SMS_EVENT_API = "/executeSmsEvent";

    static final String CHARGE_SMS_API = "/chargeSms";

    static final String UPSERT_SMS_EVENT_API = "/upsertSmsEvent";

    static final String GET_SMS_EVENTS_API = "/getSmsEvents";

    static final String GET_SMS_INFO_API = "/getSmsInfo";

    static final String DELETE_SMS_EVENT_API = "/deleteSmsEvent";
}
