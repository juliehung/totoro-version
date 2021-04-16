package io.dentall.totoro.business.service.nhi.util;

public enum NhiRuleCheckFormat {
    /**
     * DANGER LEVEL
     */
    // 89009C: 曾申報 90007C(來源-110/01/01)，不得再報 89009C
    D1_1("%s: 曾申報 %s(%s-%s)，不得再報 %s"),

    // 91004C:  91015C(來源-110/01/01) 90天內不得申報 91004C。
    D1_2("%s:  %s(%s-%s) %s 天內不得申報 %s"),

    // 90012C:  37 已申報 92013C(來源-110/01/01)
    D1_3("%s:  %s 已申報 %s(%s-%s)"),

    // 91022C: 需在 91021C (來源-110/01/01) 申報 152 天內
    D1_4("%s: 需在 %s(%s-%s) 申報 %s 天內"),

    // 34004C: 每人限給付一次(含跨院所) (來源-110/01/01)
    D2_1("%s: 每人限給付一次(含跨院所) (%s-%s)"),

    // 92050C: 每人限給付一次 (來源-110/01/01)
    D2_2("%s: 每人限給付一次 (%s-%s) "),

    // 92059C: 每位醫師限給付一次 (來源-110/01/01)
    D2_3("%s: 每位醫師限給付一次 (%s-%s) "),

    // 81: 須未滿六歲
    D3_1("%s: 須%s"),

    // 81: 已於 來源-110/01/01 申報。
    D4_1("%s: 已於 %s-%s 申報"),

    // 91001C: 已於 來源-110/01/05 申報第一象限。
    D4_2("%s: 已於 %s-%s 申報%s"),

    // 91001C: 申報次數已達本月上限。(110/01/01, 110/01/03)
    D5_1("%s: 申報次數已達本月上限。(%s)"),

    // 91003C、91004C、91005C、91017C、91103C、91104C 不得同日申報
    D6_1("%s 不得同日申報"),

    // 91003C: 180 天內曾申報(來源-110/01/01) ，就醫類別請選 AB
    D7_1("%s: %s 內已申報 %s (%s-%s) 的 %s , 就醫類別請選 AB"),

    // 90001C: 60 內已申報 90015C (來源-110/01/01) 的 37 , 就醫類別請選 AB
    D7_2("%s: %s 內已申報 %s (%s-%s) 的 %s , 就醫類別請選 AB"),

    // 90001C: 前 60 天內須曾申報 90015C
    D8_1("%s: 前 %s 天內須曾申報 %s"),

    //8I: 需曾申報 8A
    D8_2("需曾申報 %s"),

    /**
     * WARNING LEVEL
     */
    // 91003C: 91004C(來源-110/01/01) 180 天內申報 91003C(110/01/03、 110/01/04) 核刪機率高
    W1_1("%s: %s(%s-%s) %s 天內申報 %s(%s) 核刪機率高"),

    // 91003C: 限填牙位 UR,UL,LL,LR
    W2_1("%s: 限填牙位 %s"),

    // 需併同 91003C/91004C/91020C 申報
    W3_1("需併同 %s 申報"),

    // 96001C: 項目費用已涵蓋於 90001C(來源-110/01/01) 內
    W4_1("%s: 項目費用已涵蓋於 %s(%s-%s) 內"),

    // 96001C: 不得單獨申報
    W5_1("%s: 不得單獨申報"),

    // 90001C: 90 天內已申報 90002C(來源-110/01/01) 的 37, 支付點數以最高者
    W6_1("%s: %s 天內已申報 %s(%s-%s) 的 %s, 支付點數以最高者"),

    /**
     * INFO LEVEL
     */
    // 90012C: 須檢附影像
    XRAY("%s: 須檢附影像"),

    // 91021C: 執行本方案前，須至健保VPN登錄
    VPN("%s: 執行本方案前，須至健保VPN登錄"),

    // 89101C: 適用於健保特殊醫療服務對象、化療、放射線治療患者
    PT1("%s: 適用於健保特殊醫療服務對象、化療、放射線治療患者"),

    // 90017C: 適用於健保特殊醫療服務對象
    PT1_2("%s: 適用於健保特殊醫療服務對象"),

    // 90021C: 適用於健保特殊醫療服務對象(重度)
    PT1_3("%s: 適用於健保特殊醫療服務對象(重度)"),

    // 90021C: 檢附同意書及治療患者使用束縛帶的診療照
    PT2("%s: 檢附同意書及治療患者使用束縛帶的診療照"),

    // 92072C: 限口乾症患者施行申報
    PT3("%s: 限口乾症患者施行申報"),

    // 92073C: 限確診特殊口腔黏膜難症疾病患
    PT4("%s: 限確診特殊口腔黏膜難症疾病患"),

    // 00302C: 限精神病及精神分裂患者
    PT5("%s: 限精神病及精神分裂患者"),
    ;

    private String format;

    NhiRuleCheckFormat(String format) {
        this.format = format;
    }

    public String getFormat() {
        return format;
    }
}
