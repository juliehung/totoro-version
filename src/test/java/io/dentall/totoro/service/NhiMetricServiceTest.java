package io.dentall.totoro.service;

import com.univocity.parsers.csv.CsvParserSettings;
import com.univocity.parsers.csv.CsvRoutines;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;
import io.dentall.totoro.domain.Holiday;
import io.dentall.totoro.dto.HolidayTestDTO;
import io.dentall.totoro.dto.NhiMetricRawVMDTO;
import io.dentall.totoro.test.mapper.MetricTestMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.util.ResourceUtils.getFile;

@Service
public class NhiMetricServiceTest {

    public List<? extends NhiMetricRawVM> loadDataSet(String filePath) {
        ArrayList<NhiMetricRawVM> result = new ArrayList<>();

        try (BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(getFile("classpath:" + filePath)))) {
            CsvParserSettings parserSettings = new CsvParserSettings();
            parserSettings.getFormat().setDelimiter(',');
            parserSettings.getFormat().setLineSeparator("\n");
            parserSettings.setHeaderExtractionEnabled(true);

            CsvRoutines routines = new CsvRoutines(parserSettings);
            for (NhiMetricRawVMDTO data : routines.iterate(NhiMetricRawVMDTO.class, inputStream, "UTF-8")) {
                result.add(data);
            }
        } catch (Exception e) {
            // ignore exception
        }

        return result;
    }

    public List<? extends Holiday> loadHolidayDataSet() {
        ArrayList<Holiday> result = new ArrayList<>();

        try (BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(getFile("classpath:holiday.csv")))) {
            CsvParserSettings parserSettings = new CsvParserSettings();
            parserSettings.getFormat().setDelimiter(',');
            parserSettings.getFormat().setLineSeparator("\n");
            parserSettings.setHeaderExtractionEnabled(true);

            CsvRoutines routines = new CsvRoutines(parserSettings);
            for (HolidayTestDTO data : routines.iterate(HolidayTestDTO.class, inputStream, "UTF-8")) {
                result.add(MetricTestMapper.INSTANCE.mapToHoliday(data));
            }
        } catch (Exception e) {
            e.printStackTrace();
            // ignore exception
        }

        return result;
    }
}
