package io.dentall.totoro.business.service.report.context;

import io.dentall.totoro.domain.enumeration.BackupFileCatalog;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ReportRunner {

    ReportCategory category();

    BackupFileCatalog backupFileCatalog();

}
