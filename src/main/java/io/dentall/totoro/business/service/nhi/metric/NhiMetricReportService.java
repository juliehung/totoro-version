package io.dentall.totoro.business.service.nhi.metric;

import io.dentall.totoro.business.service.nhi.metric.dto.*;
import io.dentall.totoro.business.service.nhi.metric.report.*;
import io.dentall.totoro.business.service.nhi.metric.util.ExcelUtil;
import io.dentall.totoro.business.vm.nhi.NhiMetricReportBodyVM;
import io.dentall.totoro.business.vm.nhi.NhiMetricReportQueryStringVM;
import io.dentall.totoro.domain.NhiMetricReport;
import io.dentall.totoro.domain.enumeration.NhiMetricReportType;
import io.dentall.totoro.repository.NhiMetricReportRepository;
import io.dentall.totoro.service.util.DateTimeUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class NhiMetricReportService {

    private final NhiMetricReportRepository nhiMetricReportRepository;

    private final ApplicationContext applicationContext;

    private final KaoPingDistrictRegularReport kaoPingDistrictRegularReport;

    private final KaoPingDistrictReductionReport kaoPingDistrictReductionReport;

    private final MiddleDistrictReport middleDistrictReport;

    private final NorthDistrictReport northDistrictReport;

    private final SouthDistrictReport southDistrictReport;

    private final TaipeiDistrictReport taipeiDistrictReport;

    private final EastDistrictReport eastDistrictReport;

    public NhiMetricReportService(
        NhiMetricReportRepository nhiMetricReportRepository,
        ApplicationContext applicationContext,
        KaoPingDistrictRegularReport kaoPingDistrictRegularReport,
        KaoPingDistrictReductionReport kaoPingDistrictReductionReport,
        MiddleDistrictReport middleDistrictReport,
        NorthDistrictReport northDistrictReport,
        SouthDistrictReport southDistrictReport,
        TaipeiDistrictReport taipeiDistrictReport,
        EastDistrictReport eastDistrictReport, MetricService metricService
    ) {
        this.nhiMetricReportRepository = nhiMetricReportRepository;
        this.applicationContext = applicationContext;
        this.kaoPingDistrictRegularReport = kaoPingDistrictRegularReport;
        this.kaoPingDistrictReductionReport = kaoPingDistrictReductionReport;
        this.middleDistrictReport = middleDistrictReport;
        this.northDistrictReport = northDistrictReport;
        this.southDistrictReport = southDistrictReport;
        this.taipeiDistrictReport = taipeiDistrictReport;
        this.eastDistrictReport = eastDistrictReport;
    }

    @Transactional
    public void generateNhiMetricReport(
        NhiMetricReportBodyVM nhiMetricReportBodyVM,
        CompositeDistrictDto nhiMetricResultDto,
        OutputStream outputStream
    ) throws Exception {
        Workbook wb = new HSSFWorkbook();
        Map<ExcelUtil.SupportedCellStyle, CellStyle> csm = ExcelUtil.createReportStyle(wb);

        if (nhiMetricReportBodyVM.getNhiMetricReportTypes().contains(NhiMetricReportType.TAIPEI_DISTRICT)) {
            if (nhiMetricResultDto == null ||
                nhiMetricResultDto.getTaipeiDistrictDtoList() == null ||
                nhiMetricResultDto.getTaipeiDistrictDtoList().size() == 0
            ) {
                throw new Exception("There is not data in taipei district dto.");
            }
            List<TaipeiDistrictDto> contents = nhiMetricResultDto.getTaipeiDistrictDtoList();
            taipeiDistrictReport.generateReport(wb, csm, contents);
        }
        if (nhiMetricReportBodyVM.getNhiMetricReportTypes().contains(NhiMetricReportType.NORTH_DISTRICT)) {
            if (nhiMetricResultDto == null ||
                nhiMetricResultDto.getNorthDistrictDtoList() == null ||
                nhiMetricResultDto.getNorthDistrictDtoList().size() == 0
            ) {
                throw new Exception("There is not data in north district dto.");
            }
            List<NorthDistrictDto> contents = nhiMetricResultDto.getNorthDistrictDtoList();
            northDistrictReport.generateReport(wb, csm, contents);
        }
        if (nhiMetricReportBodyVM.getNhiMetricReportTypes().contains(NhiMetricReportType.MIDDLE_DISTRICT)) {
            if (nhiMetricResultDto == null ||
                nhiMetricResultDto.getMiddleDistrictDtoList() == null ||
                nhiMetricResultDto.getMiddleDistrictDtoList().size() == 0
            ) {
                throw new Exception("There is not data in middle district dto.");
            }
            List<MiddleDistrictDto> contents = nhiMetricResultDto.getMiddleDistrictDtoList();
            middleDistrictReport.generateReport(wb, csm, contents);
        }
        if (nhiMetricReportBodyVM.getNhiMetricReportTypes().contains(NhiMetricReportType.SOUTH_DISTRICT)) {
            if (nhiMetricResultDto == null ||
                nhiMetricResultDto.getSouthDistrictDtoList() == null ||
                nhiMetricResultDto.getSouthDistrictDtoList().size() == 0
            ) {
                throw new Exception("There is not data in south district dto.");
            }
            List<SouthDistrictDto> contents = nhiMetricResultDto.getSouthDistrictDtoList();
            southDistrictReport.generateReport(wb, csm, contents);
        }
        if (nhiMetricReportBodyVM.getNhiMetricReportTypes().contains(NhiMetricReportType.KAO_PING_REDUCTION_DISTRICT)) {
            if (nhiMetricResultDto == null ||
                nhiMetricResultDto.getKaoPingDistrictReductionDtoList() == null ||
                nhiMetricResultDto.getKaoPingDistrictReductionDtoList().size() == 0
            ) {
                throw new Exception("There is not data in kao-ping-reduction district dto.");
            }
            List<KaoPingDistrictReductionDto> contents = nhiMetricResultDto.getKaoPingDistrictReductionDtoList();
            kaoPingDistrictReductionReport.generateReport(wb, csm, contents);
        }
        if (nhiMetricReportBodyVM.getNhiMetricReportTypes().contains(NhiMetricReportType.KAO_PING_REGULAR_DISTRICT)) {
            if (nhiMetricResultDto == null ||
                nhiMetricResultDto.getKaoPingDistrictRegularDtoList() == null ||
                nhiMetricResultDto.getKaoPingDistrictRegularDtoList().size() == 0
            ) {
                throw new Exception("There is not data in kao-ping-regular district dto.");
            }
            List<KaoPingDistrictRegularDto> contents = nhiMetricResultDto.getKaoPingDistrictRegularDtoList();
            kaoPingDistrictRegularReport.generateReport(wb, csm, contents);
        }
        if (nhiMetricReportBodyVM.getNhiMetricReportTypes().contains(NhiMetricReportType.EAST_DISTRICT)) {
            if (nhiMetricResultDto == null ||
                nhiMetricResultDto.getEastDistrictDtoList() == null ||
                nhiMetricResultDto.getEastDistrictDtoList().size() == 0
            ) {
                throw new Exception("There is not data in east district dto.");
            }
            List<EastDistrictDto> contents = nhiMetricResultDto.getEastDistrictDtoList();
            eastDistrictReport.generateReport(wb, csm, contents);
        }

        wb.write(outputStream);
    }

    public List<NhiMetricReport> getNhiMetricReports(NhiMetricReportQueryStringVM queryStringVM) {
        return nhiMetricReportRepository.findByYearMonthAndCreatedByOrderByCreatedDateDesc(
            DateTimeUtil.transformIntYyyymmToFormatedStringYyyymm(
                queryStringVM.getYyyymm()
            ),
            queryStringVM.getCreatedBy()
        );
    }
}
