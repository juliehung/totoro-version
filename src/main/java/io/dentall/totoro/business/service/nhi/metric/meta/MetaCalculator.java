package io.dentall.totoro.business.service.nhi.metric.meta;

import java.util.List;

public interface MetaCalculator<R> extends Calculator<Meta<R>> {

    String getSourceNames();

    String getCalculatorName();

    MetaConfig getConfig();

    // 為了減少因為細微計算的條件不同就產生不同的MetaCalculator，所以實作此方法，提供出有那些條件
    // 如果有繼承AbstractMetaCalculator，預設是回傳null，如有需要可以自行覆寫
    List<?> getExtraKeyAttribute();

}
