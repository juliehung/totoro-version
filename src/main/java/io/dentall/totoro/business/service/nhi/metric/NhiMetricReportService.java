package io.dentall.totoro.business.service.nhi.metric;

import com.google.api.client.util.Value;
import io.dentall.totoro.business.service.ImageGcsBusinessService;
import io.dentall.totoro.business.service.nhi.metric.dto.*;
import io.dentall.totoro.business.service.nhi.metric.report.*;
import io.dentall.totoro.business.vm.nhi.NhiMetricReportBodyVM;
import io.dentall.totoro.business.vm.nhi.NhiMetricReportQueryStringVM;
import io.dentall.totoro.domain.NhiMetricReport;
import io.dentall.totoro.domain.User;
import io.dentall.totoro.domain.enumeration.BackupFileCatalog;
import io.dentall.totoro.domain.enumeration.BatchStatus;
import io.dentall.totoro.domain.enumeration.NhiMetricReportType;
import io.dentall.totoro.repository.NhiMetricReportRepository;
import io.dentall.totoro.service.UserService;
import io.dentall.totoro.service.mapper.NhiMetricReportMapper;
import io.dentall.totoro.service.util.DateTimeUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ForkJoinPool;

@Service
@Transactional
public class NhiMetricReportService {

    private final MetricDashboardService metricDashboardService;

    private final NhiMetricReportRepository nhiMetricReportRepository;

    private final UserService userService;

    private final ApplicationContext applicationContext;

    private final KaoPingDistrictRegularReport kaoPingDistrictRegularReport;

    private final KaoPingDistrictReductionReport kaoPingDistrictReductionReport;

    private final MiddleDistrictReport middleDistrictReport;

    private final NorthDistrictReport northDistrictReport;

    private final SouthDistrictReport southDistrictReport;

    private final TaipeiDistrictReport taipeiDistrictReport;

    @Value("${ nhi.metric.maxLock }")
    private Integer MAX_LOCK;

    public NhiMetricReportService(
        MetricDashboardService metricDashboardService,
        NhiMetricReportRepository nhiMetricReportRepository,
        UserService userService,
        ApplicationContext applicationContext,
        KaoPingDistrictRegularReport kaoPingDistrictRegularReport,
        KaoPingDistrictReductionReport kaoPingDistrictReductionReport,
        MiddleDistrictReport middleDistrictReport,
        NorthDistrictReport northDistrictReport,
        SouthDistrictReport southDistrictReport,
        TaipeiDistrictReport taipeiDistrictReport
    ) {
        this.metricDashboardService = metricDashboardService;
        this.nhiMetricReportRepository = nhiMetricReportRepository;
        this.userService = userService;
        this.applicationContext = applicationContext;
        this.kaoPingDistrictRegularReport = kaoPingDistrictRegularReport;
        this.kaoPingDistrictReductionReport = kaoPingDistrictReductionReport;
        this.middleDistrictReport = middleDistrictReport;
        this.northDistrictReport = northDistrictReport;
        this.southDistrictReport = southDistrictReport;
        this.taipeiDistrictReport = taipeiDistrictReport;
    }

    @Transactional
    synchronized public String generateNhiMetricReport(NhiMetricReportBodyVM nhiMetricReportBodyVM) throws IOException {
        String returnMessage = "";
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        Workbook wb = new HSSFWorkbook();
        Long reportId = 0L;

        try {
            Optional<User> currentUser = userService.getUserWithAuthorities();

            if (!currentUser.isPresent()) {
                return "Can not found current login user.";
            }

            if (
                nhiMetricReportRepository.countByStatusOrderByCreatedDateDesc(BatchStatus.LOCK) >= MAX_LOCK
            ) {
                return String.format(
                    "Excess maximum lock, wait until procedure done and download result from url",
                    this.MAX_LOCK
                );
            }

            NhiMetricReport reportRecord = nhiMetricReportRepository.save(
                NhiMetricReportMapper.INSTANCE.convertBodyToDomain(
                    nhiMetricReportBodyVM
                )
            );
            final long finalReportId = reportRecord.getId();

            // running
            ForkJoinPool.commonPool().submit(() -> {
                try {
                    ImageGcsBusinessService imageGcsBusinessService = applicationContext.getBean(ImageGcsBusinessService.class);
                    imageGcsBusinessService.uploadFile(
                        imageGcsBusinessService.getClinicName()
                            .concat("/")
                            .concat(BackupFileCatalog.NHI_METRIC_REPORT.getRemotePath())
                            .concat("/"),
                        "filename",
                        "content".getBytes(StandardCharsets.UTF_8),
                        BackupFileCatalog.NHI_METRIC_REPORT.getFileExtension()
                    );

                    if (nhiMetricReportBodyVM.getNhiMetricReportTypes().contains(NhiMetricReportType.KAO_PING_REDUCTION_DISTRICT)) {
                        List<KaoPingDistrictReductionDto> contents = new ArrayList<>();
                        kaoPingDistrictReductionReport.generateReport(wb, contents);
                    }
                    if (nhiMetricReportBodyVM.getNhiMetricReportTypes().contains(NhiMetricReportType.KAO_PING_REGULAR_DISTRICT)) {
                        List<KaoPingDistrictRegularDto> contents = new ArrayList<>();
                        kaoPingDistrictRegularReport.generateReport(wb, contents);
                    }
                    if (nhiMetricReportBodyVM.getNhiMetricReportTypes().contains(NhiMetricReportType.MIDDLE_DISTRICT)) {
                        List<MiddleDistrictDto> contents = new ArrayList<>();
                        middleDistrictReport.generateReport(wb, contents);
                    }
                    if (nhiMetricReportBodyVM.getNhiMetricReportTypes().contains(NhiMetricReportType.NORTH_DISTRICT)) {
                        List<NorthDistrictDto> contents = new ArrayList<>();
                        northDistrictReport.generateReport(wb, contents);
                    }
                    if (nhiMetricReportBodyVM.getNhiMetricReportTypes().contains(NhiMetricReportType.SOUTH_DISTRICT)) {
                        List<SouthDistrictDto> contents = new ArrayList<>();
                        southDistrictReport.generateReport(wb, contents);
                    }
                    if (nhiMetricReportBodyVM.getNhiMetricReportTypes().contains(NhiMetricReportType.TAIPEI_DISTRICT)) {
                        List<TaipeiDistrictDto> contents = new ArrayList<>();
                        taipeiDistrictReport.generateReport(wb, contents);
                    }

                    wb.write(outStream);


                    String gcsPath = imageGcsBusinessService.getClinicName()
                        .concat("/")
                        .concat(BackupFileCatalog.NHI_METRIC_REPORT.getRemotePath())
                        .concat("/");
                    String fileName = Instant.now().toString()
                        .concat(".xls");
                    String fileUrl = imageGcsBusinessService.getUrlForDownload()
                        .concat(imageGcsBusinessService.getClinicName())
                        .concat("/")
                        .concat(BackupFileCatalog.NHI_METRIC_REPORT.getRemotePath())
                        .concat("/")
                        .concat(fileName);
                    imageGcsBusinessService.uploadFile(
                        gcsPath,
                        fileName,
                        outStream.toByteArray(),
                        BackupFileCatalog.NHI_METRIC_REPORT.getFileExtension()
                    );

                    reportRecord.setStatus(BatchStatus.DONE);
                    reportRecord.getComment().setUrl(fileUrl);
                } catch (Exception e) {
                    NhiMetricReport r = nhiMetricReportRepository.findById(finalReportId)
                        .orElse(new NhiMetricReport());
                    r.setStatus(BatchStatus.FAILURE);
                    r.getComment().setUrl(e.getMessage());
                }
            });

        } catch(Exception e) {
            if (reportId != 0L) {
                NhiMetricReport r = nhiMetricReportRepository.findById(reportId)
                    .orElse(new NhiMetricReport());
                r.setStatus(BatchStatus.FAILURE);
                r.getComment().setUrl(e.getMessage());
            }

            return e.getMessage();
        } finally {
            outStream.close();
            wb.close();
        }

        return returnMessage;
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
