package io.dentall.totoro.business.service;

public class GcpConstants {

    static final String BUCKET_NAME = "totoro-admin";

    static final String GCS_BASE_URL = "https://storage.googleapis.com/" + BUCKET_NAME + "/";

    static final String SERVICE_ACCOUNT_PREFIX = "dentall-saas-";

    static final String SEND_SMS_FUNCTION = "https://asia-east2-dentall-saas.cloudfunctions.net/sendSms";

    static final String CHARGE_SMS_FUNCTION = "https://asia-east2-dentall-saas.cloudfunctions.net/chargeSms";
}
