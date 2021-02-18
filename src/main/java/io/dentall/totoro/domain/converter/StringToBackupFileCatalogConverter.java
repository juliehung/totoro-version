package io.dentall.totoro.domain.converter;

import io.dentall.totoro.domain.enumeration.BackupFileCatalog;
import org.springframework.core.convert.converter.Converter;

public class StringToBackupFileCatalogConverter implements Converter<String, BackupFileCatalog> {
    @Override
    public BackupFileCatalog convert(String source) {
        source = source.replaceAll("-", "_");
        return BackupFileCatalog.valueOf(source.toUpperCase());
    }
}
