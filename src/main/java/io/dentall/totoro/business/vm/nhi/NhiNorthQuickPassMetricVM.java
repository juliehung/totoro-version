package io.dentall.totoro.business.vm.nhi;

import java.math.BigDecimal;

public class NhiNorthQuickPassMetricVM {

    /**
     * 每件平均醫療費用值小於 (同儕平均數+1個標準差)*1.2
     */
    private BigDecimal a7 = BigDecimal.ZERO;

    /**
     * 根管治療未完成率一年內平均未完成率小於 28.74%
     */
    private BigDecimal a8 = BigDecimal.ZERO;

    /**
     * 牙體復形(OD)合計申報點數佔牙科處置申報點數<64.38%
     */
    private BigDecimal a9 = BigDecimal.ZERO;

    /**
     * 醫師產值(申請金額)≦去年同期高額排名 3%之最低金額(55 萬)
     */
    private BigDecimal a10 = BigDecimal.ZERO;

    /**
     * 恆牙 2 年內自家再補率≦4.5%
     */
    private BigDecimal a14 = BigDecimal.ZERO;

    /**
     * 乳牙 1 年半自家重補率≦10%或乳牙填補顆數＜15 顆
     */
    private BigDecimal a15_1 = BigDecimal.ZERO;

    /**
     * 乳牙 1 年半自家重補率≦10%或乳牙填補顆數＜15 顆
     */
    private BigDecimal a15_2 = BigDecimal.ZERO;

    /**
     * "89013C（複合體充填）3 個月申報醫令件數達 50 件以上且申報病患
     * 年齡小於 50 歲醫令占率為 40%以上"
     */
    private BigDecimal a17_1 = BigDecimal.ZERO;

    /**
     * "89013C（複合體充填）3 個月申報醫令件數達 50 件以上且申報病患
     * 年齡小於 50 歲醫令占率為 40%以上"
     */
    private BigDecimal a17_2 = BigDecimal.ZERO;

    /**
     * 院所申報 P4003C 大於 5 件需申報 91018C 大(等)於 1 件
     */
    private BigDecimal a18_1 = BigDecimal.ZERO;

    /**
     * 院所申報 P4003C 大於 5 件需申報 91018C 大(等)於 1 件
     */
    private BigDecimal a18_2 = BigDecimal.ZERO;

    public BigDecimal getA7() {
        return a7;
    }

    public void setA7(BigDecimal a7) {
        this.a7 = a7;
    }

    public BigDecimal getA8() {
        return a8;
    }

    public void setA8(BigDecimal a8) {
        this.a8 = a8;
    }

    public BigDecimal getA9() {
        return a9;
    }

    public void setA9(BigDecimal a9) {
        this.a9 = a9;
    }

    public BigDecimal getA10() {
        return a10;
    }

    public void setA10(BigDecimal a10) {
        this.a10 = a10;
    }

    public BigDecimal getA14() {
        return a14;
    }

    public void setA14(BigDecimal a14) {
        this.a14 = a14;
    }

    public BigDecimal getA15_1() {
        return a15_1;
    }

    public void setA15_1(BigDecimal a15_1) {
        this.a15_1 = a15_1;
    }

    public BigDecimal getA15_2() {
        return a15_2;
    }

    public void setA15_2(BigDecimal a15_2) {
        this.a15_2 = a15_2;
    }

    public BigDecimal getA17_1() {
        return a17_1;
    }

    public void setA17_1(BigDecimal a17_1) {
        this.a17_1 = a17_1;
    }

    public BigDecimal getA17_2() {
        return a17_2;
    }

    public void setA17_2(BigDecimal a17_2) {
        this.a17_2 = a17_2;
    }

    public BigDecimal getA18_1() {
        return a18_1;
    }

    public void setA18_1(BigDecimal a18_1) {
        this.a18_1 = a18_1;
    }

    public BigDecimal getA18_2() {
        return a18_2;
    }

    public void setA18_2(BigDecimal a18_2) {
        this.a18_2 = a18_2;
    }
}
