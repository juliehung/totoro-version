package io.dentall.totoro.business.service.report.context;

@FunctionalInterface
public interface ReportDataProvider<S extends ReportSetting, D> {

    D get(S setting);
}
