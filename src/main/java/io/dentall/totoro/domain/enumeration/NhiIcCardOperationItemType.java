package io.dentall.totoro.domain.enumeration;

public enum NhiIcCardOperationItemType {
    TREATMENT_PROCEDURE,
    TREATMENT_DRUG,
    // 由於健保卡會獨立寫入，診察代碼與費用，但是系統本身沒有這個結構，所以使用之後 item id 此時的資料等同於空值 0
    EXAMINATION,
}
