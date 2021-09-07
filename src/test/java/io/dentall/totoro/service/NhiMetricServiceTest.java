package io.dentall.totoro.service;

import com.univocity.parsers.csv.CsvParserSettings;
import com.univocity.parsers.csv.CsvRoutines;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;
import io.dentall.totoro.dto.NhiMetricRawVMDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class NhiMetricServiceTest {

    @Value("classpath:nhi_metrics_data_set_1.csv")
    private Resource resourceFile;

    public List<? extends NhiMetricRawVM> loadDataSet() {
        ArrayList<NhiMetricRawVM> result = new ArrayList<>();

        try {
            CsvParserSettings parserSettings = new CsvParserSettings();
            parserSettings.getFormat().setDelimiter(',');
            parserSettings.getFormat().setLineSeparator("\n");
            parserSettings.setHeaderExtractionEnabled(true);

            CsvRoutines routines = new CsvRoutines(parserSettings);
            for (NhiMetricRawVMDTO data : routines.iterate(NhiMetricRawVMDTO.class, resourceFile.getInputStream(), "UTF-8")) {
                result.add(data);
            }
        } catch(Exception e) {
           // ignore exception
        }

        return result;
    }
}
