package io.dentall.totoro.business.service.nhi.util;

import io.dentall.totoro.business.service.NhiRuleCheckInfoType;

public enum NhiRuleCheckFormat {
    /**
     * DANGER LEVEL
     */
    // 89009C: 曾申報 90007C(來源-110/01/01)，不得再報 89009C
    D1_1("%s: 曾申報 %s(%s-%s)，不得再報 %s", NhiRuleCheckInfoType.DANGER),

    // 91004C:  91015C(來源-110/01/01) 90天內不得申報 91004C。
    D1_2("%s:  %s(%s-%s) %s 天內不得申報 %s", NhiRuleCheckInfoType.DANGER),

    // 91004C:  91015C(來源-110/01/01) 90天內不得申報 91004C。
    D1_2_2("%s:  %s(%s-%s) %s 個月內不得申報 %s", NhiRuleCheckInfoType.DANGER),

    // 90012C:  37 已申報 92013C(來源-110/01/01)
    D1_3("%s:  %s 已申報 %s(%s-%s)", NhiRuleCheckInfoType.DANGER),

    // 91022C: 需在 91021C (來源-110/01/01) 申報 152 天內
    D1_4("%s: 需在 %s(%s-%s) 申報 %s 天內", NhiRuleCheckInfoType.DANGER),

    // 00316C:  往前 365 天內不得申報任何牙科處置
    D1_5("%s:  往前 %s 天內不得申報任何牙科處置", NhiRuleCheckInfoType.DANGER),

    // 34004C: 每人限給付一次(含跨院所) (來源-110/01/01)
    D2_1("%s: 每人限給付一次(含跨院所) (%s-%s)", NhiRuleCheckInfoType.DANGER),

    // 92050C: 每人限給付一次 (來源-110/01/01)
    D2_2("%s: 每人限給付一次 (%s-%s) ", NhiRuleCheckInfoType.DANGER),

    // 92059C: 每位醫師限給付一次 (來源-110/01/01)
    D2_3("%s: 每位醫師限給付一次 (%s-%s) ", NhiRuleCheckInfoType.DANGER),

    // 81: 須未滿六歲
    D3_1("%s: 須%s", NhiRuleCheckInfoType.DANGER),

    // 81: 已於 來源-110/01/01 申報。
    D4_1("%s: 已於 %s-%s 申報", NhiRuleCheckInfoType.DANGER),

    // 91001C: 已於 來源-110/01/05 申報第一象限。
    D4_2("%s: 已於 %s-%s 申報%s", NhiRuleCheckInfoType.DANGER),

    // 91001C: 申報次數已達本月上限。(110/01/01, 110/01/03)
    D5_1("%s: 申報次數已達本月上限。(%s)", NhiRuleCheckInfoType.DANGER),

    // 91003C、91004C、91005C、91017C、91103C、91104C 不得同日申報
    D6_1("%s: %s 不得同日申報", NhiRuleCheckInfoType.DANGER),

    // 91003C: 180 天內曾申報(來源-110/01/01) ，就醫類別請選 AB
    D7_1("%s: %s 天內曾申報(%s-%s), 就醫類別請選 AB", NhiRuleCheckInfoType.DANGER),

    // 90001C: 60 內已申報 90015C (來源-110/01/01) 的 37 , 就醫類別請選 AB
    D7_2("%s: %s 天內曾申報 %s (%s-%s) 的 %s, 就醫類別請選 AB", NhiRuleCheckInfoType.DANGER),

    // 90001C: 前 60 天內須曾申報 90015C
    D8_1("%s: 前 %s 天內須曾申報 %s", NhiRuleCheckInfoType.DANGER),

    //8I: 需曾申報 8A
    D8_2("%s: 需曾申報 %s", NhiRuleCheckInfoType.DANGER),

    /**
     * WARNING LEVEL
     */
    // 91003C: 91004C(來源-110/01/01) 180 天內申報 91003C(110/01/03、 110/01/04) 核刪機率高
    W1_1("%s: %s(%s-%s) %s 天內申報 %s(%s) 核刪機率高", NhiRuleCheckInfoType.WARNING),

    // 91003C: 限填牙位 UR,UL,LL,LR
    W2_1("%s: 限填牙位 %s", NhiRuleCheckInfoType.WARNING),

    // 需併同 91003C/91004C/91020C 申報
    W3_1("%s: 需併同 %s 申報", NhiRuleCheckInfoType.WARNING),

    // 96001C: 項目費用已涵蓋於 90001C(來源-110/01/01) 內
    W4_1("%s: 項目費用已涵蓋於 %s(%s-%s) 內", NhiRuleCheckInfoType.WARNING),

    // 96001C: 不得單獨申報
    W5_1("%s: 不得單獨申報", NhiRuleCheckInfoType.WARNING),

    // 90001C: 90 天內已申報 90002C(來源-110/01/01) 的 37, 支付點數以最高者
    W6_1("%s: %s 天內已申報 %s(%s-%s) 的 %s, 支付點數以最高者", NhiRuleCheckInfoType.WARNING),

    // 91018C: 限經「牙周病統合治療方案」核備之醫師，執行院所內已完成第三階段91023C患者之牙醫醫療服務，且需與第二階段91022C間隔九十天。
    PERIO_1("%s: 限經「牙周病統合治療方案」核備之醫師，執行院所內已完成第三階段91023C患者之牙醫醫療服務，且需與第二階段91022C間隔九十天。", NhiRuleCheckInfoType.WARNING),

    /**
     * INFO LEVEL
     */
    // 90012C: 須檢附影像
    XRAY("%s: 須檢附影像", NhiRuleCheckInfoType.INFO),

    // 91021C: 執行本方案前，須至健保VPN登錄
    VPN("%s: 執行本方案前，須至健保VPN登錄", NhiRuleCheckInfoType.INFO),

    // 89101C: 適用於健保特殊醫療服務對象、化療、放射線治療患者
    PT1("%s: 適用於健保特殊醫療服務對象、化療、放射線治療患者", NhiRuleCheckInfoType.INFO),

    // 90017C: 適用於健保特殊醫療服務對象
    PT1_2("%s: 適用於健保特殊醫療服務對象", NhiRuleCheckInfoType.INFO),

    // 90021C: 適用於健保特殊醫療服務對象(重度)
    PT1_3("%s: 適用於健保特殊醫療服務對象(重度)", NhiRuleCheckInfoType.INFO),

    // 90021C: 檢附同意書及治療患者使用束縛帶的診療照
    PT2("%s: 檢附同意書及治療患者使用束縛帶的診療照", NhiRuleCheckInfoType.INFO),

    // 92072C: 限口乾症患者施行申報
    PT3("%s: 限口乾症患者施行申報", NhiRuleCheckInfoType.INFO),

    // 92073C: 限確診特殊口腔黏膜難症疾病患
    PT4("%s: 限確診特殊口腔黏膜難症疾病患", NhiRuleCheckInfoType.INFO),

    // 00302C: 限精神病及精神分裂患者
    PT5("%s: 限精神病及精神分裂患者", NhiRuleCheckInfoType.INFO),

    // 92094C: 限週六、日及國定假日申報, 當月看診需≦二十六日, 前月於健保VPN完成登錄
    HOLIDAY("%s: 限週六、日及國定假日申報, 當月看診需≦二十六日, 前月於健保VPN完成登錄", NhiRuleCheckInfoType.INFO),

    // 91022C: 檢附牙菌斑控制紀錄表、牙周病檢查紀錄表
    PERIO_REC_1("%s: 檢附牙菌斑控制紀錄表、牙周病檢查紀錄表", NhiRuleCheckInfoType.INFO),

    // 91011C: 檢附牙周囊袋記錄表
    PERIO_REC_2("%s: 檢附牙周囊袋記錄表", NhiRuleCheckInfoType.INFO),

    //92043C: 檢附手術紀錄
    OS_REC_1("%s: 檢附手術紀錄", NhiRuleCheckInfoType.INFO),
    ;

    private String format;

    private NhiRuleCheckInfoType level;

    NhiRuleCheckFormat(String format, NhiRuleCheckInfoType level) {
        this.format = format;
        this.level = level;
    }

    public String getFormat() {
        return format;
    }

    public NhiRuleCheckInfoType getLevel() {
        return level;
    }
}
