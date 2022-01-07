package io.dentall.totoro.business.service.report.context;

import io.dentall.totoro.business.service.report.ReportBuilderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

import static java.util.Optional.empty;

public interface ReportSetting {

    Logger log = LoggerFactory.getLogger(ReportSetting.class);

    default Optional<Class<ReportBuilderService>> getBuilderService() {
        String className = this.getClass().getSimpleName();
        String reportServiceName =
            ReportBuilderService.class.getPackage().getName()
                + "."
                + className.substring(0, className.lastIndexOf("Setting")) + "BuilderService";

        try {
            return Optional.of(
                (Class<ReportBuilderService>)
                    ReportBuilderService.class.getClassLoader().loadClass(reportServiceName));
        } catch (ClassNotFoundException e) {
            log.error("load report service class error", e);
        }

        return empty();
    }

}
