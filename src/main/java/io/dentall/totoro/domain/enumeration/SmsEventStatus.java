package io.dentall.totoro.domain.enumeration;

/**
 * 由於初期設定的時候並沒有強制要求狀態型別，因此資料面已經混合前後端格式之資料，
 * 有鑒於 firestore 不易 migration，只能配合當前資料型態
 */
public enum SmsEventStatus {
    draft,
    completed,
}
