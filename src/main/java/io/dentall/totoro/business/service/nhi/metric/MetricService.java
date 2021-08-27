package io.dentall.totoro.business.service.nhi.metric;

import com.google.api.client.util.Value;
import io.dentall.totoro.business.service.ImageGcsBusinessService;
import io.dentall.totoro.business.service.nhi.metric.dto.DisposalSummaryDto;
import io.dentall.totoro.business.service.nhi.metric.dto.DoctorSummaryDto;
import io.dentall.totoro.business.service.nhi.metric.dto.GiantMetricDto;
import io.dentall.totoro.business.service.nhi.metric.dto.MetricLDto;
import io.dentall.totoro.business.service.nhi.metric.mapper.SpecialTreatmentMapper;
import io.dentall.totoro.business.service.nhi.metric.mapper.TimeLineDataMapper;
import io.dentall.totoro.business.service.nhi.metric.source.MetricSubjectType;
import io.dentall.totoro.business.service.nhi.metric.vm.*;
import io.dentall.totoro.business.vm.nhi.NhiMetricReportBodyVM;
import io.dentall.totoro.business.vm.nhi.NhiMetricReportQueryStringVM;
import io.dentall.totoro.domain.NhiMetricReport;
import io.dentall.totoro.domain.User;
import io.dentall.totoro.domain.enumeration.BackupFileCatalog;
import io.dentall.totoro.domain.enumeration.BatchStatus;
import io.dentall.totoro.repository.NhiMetricReportRepository;
import io.dentall.totoro.service.UserService;
import io.dentall.totoro.service.mapper.NhiMetricReportMapper;
import io.dentall.totoro.service.util.DateTimeUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

import static io.dentall.totoro.business.service.nhi.metric.source.MetricSubjectType.CLINIC;
import static io.dentall.totoro.business.service.nhi.metric.source.MetricSubjectType.DOCTOR;

@Service
@Transactional
public class MetricService {

    private final MetricDashboardService metricDashboardService;

    private final NhiMetricReportRepository nhiMetricReportRepository;

    private final UserService userService;

    private final ApplicationContext applicationContext;

    @Value("${ nhi.metric.maxLock }")
    private Integer MAX_LOCK;

    public MetricService(
        MetricDashboardService metricDashboardService,
        NhiMetricReportRepository nhiMetricReportRepository,
        UserService userService,
        ApplicationContext applicationContext
    ) {
        this.metricDashboardService = metricDashboardService;
        this.nhiMetricReportRepository = nhiMetricReportRepository;
        this.userService = userService;
        this.applicationContext = applicationContext;
    }

    public List<MetricLVM> getDashboardMetric(final LocalDate baseDate, List<Long> excludeDisposalIds) {

        List<GiantMetricDto> giantMetricDtoList = metricDashboardService.metric(baseDate, excludeDisposalIds);

        return giantMetricDtoList.stream().map(giantDto -> {
            MetricSubjectType metricSubjectType = giantDto.getType();
            MetricLDto metricLDto = giantDto.getMetricLDto();
            Section5 section5 = new Section5();
            section5.setL1(new MetricData(metricLDto.getL1()));
            section5.setL2(new MetricData(metricLDto.getL2()));
            section5.setL3(new MetricData(metricLDto.getL3()));
            section5.setL4(new MetricData(metricLDto.getL4()));

            Section6 section6 = new Section6();
            section6.setL5(new MetricData(metricLDto.getL5()));
            section6.setL6(new MetricData(metricLDto.getL6()));
            section6.setTimeline(TimeLineDataMapper.INSTANCE.mapToTimeLineData(giantDto.getDailyPt1().entrySet()));

            Section7 section7 = new Section7();
            section7.setL7(new MetricData(metricLDto.getL7()));
            section7.setL8(new MetricData(metricLDto.getL8()));
            section7.setTimeline(TimeLineDataMapper.INSTANCE.mapToTimeLineData(giantDto.getDailyIc3().entrySet()));

            Section8 section8 = new Section8();
            if (metricSubjectType == CLINIC) {
                section8.setL9(metricLDto.getL9());
            } else {
                section8.setL10(metricLDto.getL10());
            }

            Section9 section9 = new Section9();
            section9.setTimeline(TimeLineDataMapper.INSTANCE.mapToTimeLineData(giantDto.getDailyPoints().entrySet()));

            Section10 section10 = new Section10();
            section10.setL11(new MetricData(metricLDto.getL11()));
            section10.setL12(new MetricData(metricLDto.getL12()));
            section10.setL13(new MetricData(metricLDto.getL13()));
            section10.setL14(new MetricData(metricLDto.getL14()));
            section10.setL15(new MetricData(metricLDto.getL15()));
            section10.setL16(new MetricData(metricLDto.getL16()));
            section10.setL17(new MetricData(metricLDto.getL17()));
            section10.setL18(new MetricData(metricLDto.getL18()));
            section10.setL19(new MetricData(metricLDto.getL19()));

            Section11 section11 = new Section11();
            section11.setP1(SpecialTreatmentMapper.INSTANCE.mapToVM(giantDto.getSpecialTreatmentAnalysisDto().getP1()));
            section11.setP2(SpecialTreatmentMapper.INSTANCE.mapToVM(giantDto.getSpecialTreatmentAnalysisDto().getP2()));
            section11.setP3(SpecialTreatmentMapper.INSTANCE.mapToVM(giantDto.getSpecialTreatmentAnalysisDto().getP3()));
            section11.setP4(SpecialTreatmentMapper.INSTANCE.mapToVM(giantDto.getSpecialTreatmentAnalysisDto().getP4()));
            section11.setP5(SpecialTreatmentMapper.INSTANCE.mapToVM(giantDto.getSpecialTreatmentAnalysisDto().getP5()));
            section11.setP6(SpecialTreatmentMapper.INSTANCE.mapToVM(giantDto.getSpecialTreatmentAnalysisDto().getP6()));
            section11.setP7(SpecialTreatmentMapper.INSTANCE.mapToVM(giantDto.getSpecialTreatmentAnalysisDto().getP7()));
            section11.setP8(SpecialTreatmentMapper.INSTANCE.mapToVM(giantDto.getSpecialTreatmentAnalysisDto().getP8()));
            section11.setOther(SpecialTreatmentMapper.INSTANCE.mapToVM(giantDto.getSpecialTreatmentAnalysisDto().getOther()));
            section11.setSummary(SpecialTreatmentMapper.INSTANCE.mapToVM(giantDto.getSpecialTreatmentAnalysisDto().getSummary()));
            section11.setP1p5(SpecialTreatmentMapper.INSTANCE.mapToVM(giantDto.getSpecialTreatmentAnalysisDto().getP1p5()));
            section11.setP2p3(SpecialTreatmentMapper.INSTANCE.mapToVM(giantDto.getSpecialTreatmentAnalysisDto().getP2p3()));
            section11.setP4p8(SpecialTreatmentMapper.INSTANCE.mapToVM(giantDto.getSpecialTreatmentAnalysisDto().getP4p8()));
            section11.setP6p7AndOther(SpecialTreatmentMapper.INSTANCE.mapToVM(giantDto.getSpecialTreatmentAnalysisDto().getP6p7AndOther()));

            Section12 section12 = new Section12();
            section12.setL20(new MetricData(metricLDto.getL20()));
            section12.setL21(new MetricData(metricLDto.getL21()));
            section12.setL22(new MetricData(metricLDto.getL22()));
            section12.setL23(new MetricData(metricLDto.getL23()));

            Section13 section13 = new Section13();
            section13.setL24(new MetricData(metricLDto.getL24()));

            Section14 section14 = new Section14();
            section14.setL25(new MetricData(metricLDto.getL25()));
            section14.setL26(new MetricData(metricLDto.getL26()));
            section14.setL27(new MetricData(metricLDto.getL27()));
            section14.setL28(new MetricData(metricLDto.getL28()));
            section14.setL29(new MetricData(metricLDto.getL29()));
            section14.setL30(new MetricData(metricLDto.getL30()));
            section14.setL31(new MetricData(metricLDto.getL31()));
            section14.setL32(new MetricData(metricLDto.getL32()));
            section14.setL33(new MetricData(metricLDto.getL33()));
            section14.setL34(new MetricData(metricLDto.getL34()));
            section14.setL35(new MetricData(metricLDto.getL35()));
            section14.setL36(new MetricData(metricLDto.getL36()));

            Section15 section15 = new Section15();
            section15.setL37(new MetricData(metricLDto.getL37()));
            section15.setL38(new MetricData(metricLDto.getL38()));
            section15.setL39(new MetricData(metricLDto.getL39()));
            section15.setL40(new MetricData(metricLDto.getL40()));
            section15.setL41(new MetricData(metricLDto.getL41()));
            section15.setL42(new MetricData(metricLDto.getL42()));
            section15.setL43(new MetricData(metricLDto.getL43()));
            section15.setL44(new MetricData(metricLDto.getL44()));
            section15.setL45(new MetricData(metricLDto.getL45()));
            section15.setL46(new MetricData(metricLDto.getL46()));
            section15.setL47(new MetricData(metricLDto.getL47()));
            section15.setL48(new MetricData(metricLDto.getL48()));

            Section16 section16 = new Section16();
            section16.setL49(new MetricData(metricLDto.getL49()));
            section16.setL50(new MetricData(metricLDto.getL50()));
            section16.setL51(new MetricData(metricLDto.getL51()));
            section16.setL52(new MetricData(metricLDto.getL52()));

            Section17 section17 = new Section17();
            section17.setL53(new MetricData(metricLDto.getL53()));
            section17.setL54(new MetricData(metricLDto.getL54()));
            section17.setL55(new MetricData(metricLDto.getL55()));
            section17.setL56(new MetricData(metricLDto.getL56()));

            Section18 section18 = new Section18();
            if (metricSubjectType == CLINIC) {
                section18.setCount(giantDto.getDoctorSummary().size());
                section18.setTotal(giantDto.getDoctorSummary().stream().mapToLong(DoctorSummaryDto::getTotal).sum());
                section18.setDoctorSummary(giantDto.getDoctorSummary());
            } else {
                section18.setCount(giantDto.getDisposalSummary().size());
                section18.setTotal(giantDto.getDisposalSummary().stream().mapToLong(DisposalSummaryDto::getTotal).sum());
                section18.setDisposalSummary(giantDto.getDisposalSummary());
            }

            MetricLVM metricLVM = new MetricLVM();
            if (metricSubjectType == DOCTOR) {
                metricLVM.setDoctor(giantDto.getDoctor());
            }
            metricLVM.setType(metricSubjectType.name().toLowerCase());
            metricLVM.setSection5(section5);
            metricLVM.setSection6(section6);
            metricLVM.setSection7(section7);
            metricLVM.setSection8(section8);
            metricLVM.setSection9(section9);
            metricLVM.setSection10(section10);
            metricLVM.setSection11(section11);
            metricLVM.setSection12(section12);
            metricLVM.setSection13(section13);
            metricLVM.setSection14(section14);
            metricLVM.setSection15(section15);
            metricLVM.setSection16(section16);
            metricLVM.setSection17(section17);
            metricLVM.setSection18(section18);

            return metricLVM;
        }).collect(Collectors.toList());
    }

    @Profile("img-gcs")
    @Transactional
    synchronized public String generateNhiMetricReport(NhiMetricReportBodyVM nhiMetricReportBodyVM) {
        NhiMetricReport r = null;
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

            r = nhiMetricReportRepository.save(
                NhiMetricReportMapper.INSTANCE.convertBodyToDomain(
                    nhiMetricReportBodyVM
                )
            );

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
//                    String gcpUrl = nhiRuleCheckUtil.generateMonthDeclarationRuleCheckReport(
//                        finalPartialACDateTime,
//                        finalExcludeDisposals,
//                        imageGcsBusinessService
//                    );


//                    r.setStatus(BatchStatus.DONE);
//                    r.getComment().setUrl(gcpUrl);
                } catch (Exception e) {
//                    log.error();
//                    r.setStatus(BatchStatus.FAILURE);
                }
            });


        } catch(Exception e) {
            if (r != null) {
                r.setStatus(BatchStatus.FAILURE);
            }

            return e.getMessage();
        }

        return "ok";
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
