package io.dentall.totoro.business.service.nhi;

import io.dentall.totoro.business.service.nhi.util.CopaymentCode;
import io.dentall.totoro.business.service.nhi.util.NhiRuleCheckUtil;
import io.dentall.totoro.business.service.nhi.util.ToothConstraint;
import io.dentall.totoro.business.service.nhi.util.ToothUtil;
import io.dentall.totoro.domain.NhiExtendDisposal;
import io.dentall.totoro.domain.NhiExtendTreatmentProcedure;
import io.dentall.totoro.domain.Patient;
import io.dentall.totoro.repository.NhiExtendDisposalRepository;
import io.dentall.totoro.repository.NhiExtendTreatmentProcedureRepository;
import io.dentall.totoro.repository.NhiMedicalRecordRepository;
import io.dentall.totoro.repository.PatientRepository;
import io.dentall.totoro.service.dto.table.NhiExtendTreatmentProcedureTable;
import io.dentall.totoro.service.mapper.NhiExtendDisposalMapper;
import io.dentall.totoro.service.mapper.NhiExtendTreatmentProcedureMapper;
import io.dentall.totoro.service.util.DateTimeUtil;
import io.dentall.totoro.util.DataGenerator;
import io.dentall.totoro.util.TableGenerator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {io.dentall.totoro.config.TimeConfig.class})
public class NhiRuleCheckUtilMockTest {

    private final Long patientId_1 = 1L;

    private final Long patientId_2 = 2L;

    private final Long treatmentProcedureId_1 = 1L;

    private final Long treatmentProcedureId_2 = 2L;

    @Mock
    private NhiExtendDisposalRepository nhiExtendDisposalRepository;

    @Mock
    private NhiExtendTreatmentProcedureRepository nhiExtendTreatmentProcedureRepository;

    @Mock
    private PatientRepository patientRepository;

    private NhiMedicalRecordRepository nhiMedicalRecordRepository;

    @Mock
    private NhiExtendDisposalMapper nhiExtendDisposalMapper;

    @Mock
    private NhiExtendTreatmentProcedureMapper nhiExtendTreatmentProcedureMapper;

    @InjectMocks
    private NhiRuleCheckUtil nhiRuleCheckUtil;

    /**
     * 根據傳入的資料回傳對應錯誤訊息 for 在時間區間內，已經有診療項目
     * @param codes limit codes
     * @param p 時間區間
     * @param netp mocking Nhi extend treatment procedure
     * @param currentTxDate 待檢驗項目時間
     * @return 錯誤訊息
     */
    private String codeBeforeDateErrorMessage(
        List<String> codes,
        Period p,
        NhiExtendTreatmentProcedure netp,
        LocalDate currentTxDate
    ) {
        LocalDate matchDate = DateTimeUtil.transformROCDateToLocalDate(netp.getA71());

        return String.format(
            "%s 不可與 %s 在 %d 天內再次申報，上次申報 %s (%s, %d 天前)",
            netp.getA73(),
            codes.toString(),
            p,
            netp.getA73(),
            matchDate,
            Duration.between(matchDate.atStartOfDay(), currentTxDate.atStartOfDay()).toDays()
        );
    }

    /**
     * mocking Parse code 回傳空值
     */
    private void mockIgnoreParseCode()  {
        Mockito
            .when(nhiRuleCheckUtil.parseNhiCode(any()))
            .thenReturn(Arrays.asList(""));
    }

    /**
     * mocking 過去病患的診療紀錄 repo 部分
     */
    private void mockFindTxRepo() {
        Mockito
            .when(nhiExtendTreatmentProcedureRepository.findAllByTreatmentProcedure_Disposal_Registration_Appointment_Patient_IdAndA73In(
                any(),
                any()
            ))
            .thenReturn(new ArrayList<NhiExtendTreatmentProcedureTable>());
    }

    private void mockMappingData(NhiExtendTreatmentProcedure netp) {
        Mockito
            .when(nhiExtendTreatmentProcedureMapper
                .nhiExtendTreatmentProcedureTableToNhiExtendTreatmentProcedureTable(any()))
            .thenReturn(netp);
    }

    /**
     * mocking 過去病患的診療紀錄 repo 部分
     */
    private void mockFindTxRepoWithReturn(NhiExtendTreatmentProcedure netp) {
        Mockito
            .when(nhiExtendTreatmentProcedureRepository.findAllByTreatmentProcedure_Disposal_Registration_Appointment_Patient_IdAndA73In(
                any(),
                any()
            ))
            .thenReturn(Arrays.asList(new TableGenerator.NhiExtendTreatmentProcedureTableGenerator(
                netp.getId(),
                netp.getA71(),
                netp.getA73(),
                netp.getA74(),
                netp.getA75()
            )));
    }

    /**
     * mocking 過去病患 無相符合 的診療紀錄
     */
    private void mockFindTxCodeReturnNull() {
        Mockito
            .when(nhiRuleCheckUtil.findPatientTreatmentProcedureAtCodesAndBeforePeriod(
                ArgumentMatchers.any(),
                ArgumentMatchers.any(),
                ArgumentMatchers.any(),
                ArgumentMatchers.any(),
                ArgumentMatchers.any()
            ))
            .thenReturn(null);
    }

    /**
     * mocking 過去病患 有相符合 的診療紀錄
     */
    private void mockFindTxCodeReturnMatch(NhiExtendTreatmentProcedure netp) {
        Mockito
            .when(nhiRuleCheckUtil.findPatientTreatmentProcedureAtCodesAndBeforePeriod(
                ArgumentMatchers.any(),
                ArgumentMatchers.any(),
                ArgumentMatchers.any(),
                ArgumentMatchers.any(),
                ArgumentMatchers.any()
            ))
            .thenReturn(netp);
    }

    /**
     * Test case for addNotification
     * 根據給定的文字，並加入預計回傳的資料中
     */
    @Test
    public void addNotification() {
        final String msg = "hello golang";
        NhiRuleCheckResultDTO rdto = nhiRuleCheckUtil.addNotification(msg);

        Assert.assertEquals(rdto.getMessage(), msg);
    }

    /**
     * Test case for findPatientTreatmentProcedureAtCodesAndBeforePeriod
     * 給定 code 且 在給定日期內不包含其他資料
     * 1. T, 過去無資料
     * 2. T, 過去有資料，且超過限制日期
     * 3. T, 未來有資料
     * 4. T, 過去有資料，但不同 code
     * 5. T, 有資料，但是與自己相同 tx
     * 6. F, 過去有資料，且未超過限制日期
     */
    @Test
    public void findTxCode_1() {
        NhiExtendTreatmentProcedure netp = nhiRuleCheckUtil.findPatientTreatmentProcedureAtCodesAndBeforePeriod(
            DataGenerator.ID_1,
            DataGenerator.ID_1,
            DataGenerator.NHI_TREATMENT_DATE_NOW,
            DataGenerator.NHI_CODE_LIST_1,
            DateTimeUtil.NHI_1_MONTH
        );

        Assert.assertNull(netp);
    }

    @Test
    public void findTxCode_2() {
        Mockito
            .when(nhiExtendTreatmentProcedureRepository.findAllByTreatmentProcedure_Disposal_Registration_Appointment_Patient_IdAndA73In(
                ArgumentMatchers.eq(DataGenerator.ID_1),
                ArgumentMatchers.eq(DataGenerator.NHI_CODE_LIST_1)
            ))
            .thenReturn(
                Arrays.asList(new TableGenerator.NhiExtendTreatmentProcedureTableGenerator(
                    DataGenerator.ID_2,
                    DataGenerator.NHI_TREATMENT_DATE_MIN_STRING,
                    DataGenerator.NHI_CODE_1,
                    DataGenerator.TOOTH_PERMANENT_1,
                    DataGenerator.SURFACE_BLANK
                ))
            );

        NhiExtendTreatmentProcedure netp = nhiRuleCheckUtil.findPatientTreatmentProcedureAtCodesAndBeforePeriod(
            DataGenerator.ID_1,
            DataGenerator.ID_1,
            DataGenerator.NHI_TREATMENT_DATE_NOW,
            DataGenerator.NHI_CODE_LIST_1,
            DateTimeUtil.NHI_1_MONTH
        );

        Assert.assertNull(netp);
    }

    @Test
    public void findTxCode_3() {
        Mockito
            .when(nhiExtendTreatmentProcedureRepository.findAllByTreatmentProcedure_Disposal_Registration_Appointment_Patient_IdAndA73In(
                ArgumentMatchers.eq(DataGenerator.ID_1),
                ArgumentMatchers.eq(DataGenerator.NHI_CODE_LIST_1)
            ))
            .thenReturn(
                Arrays.asList(new TableGenerator.NhiExtendTreatmentProcedureTableGenerator(
                    DataGenerator.ID_2,
                    DataGenerator.NHI_TREATMENT_DATE_MAX_STRING,
                    DataGenerator.NHI_CODE_1,
                    DataGenerator.TOOTH_PERMANENT_1,
                    DataGenerator.SURFACE_BLANK
                ))
            );

        NhiExtendTreatmentProcedure netp = nhiRuleCheckUtil.findPatientTreatmentProcedureAtCodesAndBeforePeriod(
            DataGenerator.ID_1,
            DataGenerator.ID_1,
            DataGenerator.NHI_TREATMENT_DATE_NOW,
            DataGenerator.NHI_CODE_LIST_1,
            DateTimeUtil.NHI_1_MONTH
        );

        Assert.assertNull(netp);
    }

    @Test
    public void findTxCode_4() {
        Mockito
            .when(nhiExtendTreatmentProcedureRepository.findAllByTreatmentProcedure_Disposal_Registration_Appointment_Patient_IdAndA73In(
                ArgumentMatchers.eq(DataGenerator.ID_1),
                ArgumentMatchers.eq(DataGenerator.NHI_CODE_LIST_1)
            ))
            .thenReturn(
                new ArrayList<>()
            );

        NhiExtendTreatmentProcedure netp = nhiRuleCheckUtil.findPatientTreatmentProcedureAtCodesAndBeforePeriod(
            DataGenerator.ID_1,
            DataGenerator.ID_1,
            DataGenerator.NHI_TREATMENT_DATE_NOW,
            DataGenerator.NHI_CODE_LIST_1,
            DateTimeUtil.NHI_1_MONTH
        );

        Assert.assertNull(netp);
    }

    @Test
    public void findTxCode_5() {
        NhiExtendTreatmentProcedure mockingResult = new NhiExtendTreatmentProcedure();
        mockingResult.setId(DataGenerator.ID_1);
        mockingResult.a71(DataGenerator.NHI_TREATMENT_DATE_NOW_STRING).a73(DataGenerator.NHI_CODE_1).a74(DataGenerator.TOOTH_PERMANENT_1).a75(DataGenerator.SURFACE_BLANK);

        Mockito
            .when(nhiExtendTreatmentProcedureRepository.findAllByTreatmentProcedure_Disposal_Registration_Appointment_Patient_IdAndA73In(
                ArgumentMatchers.eq(DataGenerator.ID_1),
                ArgumentMatchers.eq(DataGenerator.NHI_CODE_LIST_1)
            ))
            .thenReturn(
                Arrays.asList(new TableGenerator.NhiExtendTreatmentProcedureTableGenerator(
                    DataGenerator.ID_1,
                    DataGenerator.NHI_TREATMENT_DATE_NOW_STRING,
                    DataGenerator.NHI_CODE_1,
                    DataGenerator.TOOTH_PERMANENT_1,
                    DataGenerator.SURFACE_BLANK
                ))
            );

        Mockito
            .when(nhiExtendTreatmentProcedureMapper.nhiExtendTreatmentProcedureTableToNhiExtendTreatmentProcedureTable(any()))
            .thenReturn(mockingResult);

        NhiExtendTreatmentProcedure netp = nhiRuleCheckUtil.findPatientTreatmentProcedureAtCodesAndBeforePeriod(
            DataGenerator.ID_1,
            DataGenerator.ID_1,
            DataGenerator.NHI_TREATMENT_DATE_NOW,
            DataGenerator.NHI_CODE_LIST_1,
            DateTimeUtil.NHI_1_MONTH
        );

        Assert.assertNull(netp);
    }

    @Test
    public void findTxCode_6() {
        NhiExtendTreatmentProcedure netpMock = new NhiExtendTreatmentProcedure();
        netpMock.setId(DataGenerator.ID_2);
        netpMock.a71(DataGenerator.NHI_TREATMENT_DATE_NOW_STRING)
            .a73(DataGenerator.NHI_CODE_1)
            .a74(DataGenerator.TOOTH_PERMANENT_1)
            .a75(DataGenerator.SURFACE_BLANK);

        this.mockMappingData(netpMock);
        this.mockFindTxRepoWithReturn(netpMock);

        NhiExtendTreatmentProcedure netp = nhiRuleCheckUtil.findPatientTreatmentProcedureAtCodesAndBeforePeriod(
            DataGenerator.ID_1,
            DataGenerator.ID_1,
            DataGenerator.NHI_TREATMENT_DATE_NOW,
            DataGenerator.NHI_CODE_LIST_1,
            DateTimeUtil.NHI_1_WEEK
        );

        Assert.assertEquals(netpMock, netp);
    }

    /**
     * Test case for isAllLimitTooth
     * 牙位 必須符合給訂之牙位 regex 規則
     * 1. T, 複數個 恆牙牙位 ，條件為 恆牙，並無錯誤訊息
     * 2. F, 恆牙牙位 ＋ 乳牙牙位，條件為 恆牙，並出現錯誤訊息
     * 3. F, 牙位為空，並出現錯誤訊息
     */
    @Test
    public void isAllLimitTooth_1() {
        NhiRuleCheckDTO dto = new NhiRuleCheckDTO();
        NhiExtendTreatmentProcedure netp = new NhiExtendTreatmentProcedure();
        netp.setA74("1234");
        dto.setNhiExtendTreatmentProcedure(netp);

        NhiRuleCheckResultDTO rdto = nhiRuleCheckUtil.isAllLimitedTooth(dto, ToothConstraint.PERMANENT_TOOTH);
        Assert.assertEquals(true, rdto.isValidated());
        Assert.assertEquals(rdto.getMessage(), null);
    }

    @Test
    public void isAllLimitTooth_2() {
        NhiRuleCheckDTO dto = new NhiRuleCheckDTO();
        NhiExtendTreatmentProcedure netp = new NhiExtendTreatmentProcedure();
        netp.setA74("1254");
        dto.setNhiExtendTreatmentProcedure(netp);

        NhiRuleCheckResultDTO rdto = nhiRuleCheckUtil.isAllLimitedTooth(dto, ToothConstraint.PERMANENT_TOOTH);
        Assert.assertEquals(false, rdto.isValidated());
        Assert.assertEquals(rdto.getMessage(), ToothUtil.getToothConstraintsFailureMessage(ToothConstraint.PERMANENT_TOOTH));
    }

    @Test
    public void isAllLimitTooth_3() {
        NhiRuleCheckDTO dto = new NhiRuleCheckDTO();
        NhiExtendTreatmentProcedure netp = new NhiExtendTreatmentProcedure();
        netp.setA74("");
        dto.setNhiExtendTreatmentProcedure(netp);

        NhiRuleCheckResultDTO rdto = nhiRuleCheckUtil.isAllLimitedTooth(dto, ToothConstraint.PERMANENT_TOOTH);
        Assert.assertEquals(false, rdto.isValidated());
        Assert.assertEquals(rdto.getMessage(), ToothUtil.getToothConstraintsFailureMessage(ToothConstraint.PERMANENT_TOOTH));
    }

    /**
     * Test case for isPatientIdentityInclude
     * 檢驗 選定的部分負擔代碼符合選定項目
     * 1. T, 符合指定部分負擔代碼
     * 2. F, 不符合指定不分代碼
     * 3. F, 沒有指定代碼
     */
    @Test
    public void isPatientIdentityInclude_1() {
        NhiRuleCheckDTO dto = new NhiRuleCheckDTO();
        NhiExtendDisposal ned = new NhiExtendDisposal();
        ned.setPatientIdentity(DataGenerator.PATIENT_IDENTITY_1);
        dto.setNhiExtendDisposal(ned);

        NhiRuleCheckResultDTO rdto = nhiRuleCheckUtil.isPatientIdentityInclude(dto, CopaymentCode._001);
        Assert.assertEquals(true, rdto.isValidated());
        Assert.assertEquals(null, rdto.getMessage());
    }

    @Test
    public void isPatientIdentityInclude_2() {
        NhiRuleCheckDTO dto = new NhiRuleCheckDTO();
        NhiExtendDisposal ned = new NhiExtendDisposal();
        ned.setPatientIdentity(DataGenerator.PATIENT_IDENTITY_2);
        dto.setNhiExtendDisposal(ned);

        NhiRuleCheckResultDTO rdto = nhiRuleCheckUtil.isPatientIdentityInclude(dto, CopaymentCode._001);
        Assert.assertEquals(false, rdto.isValidated());
        Assert.assertEquals(CopaymentCode._001.getNotification(), rdto.getMessage());
    }

    @Test
    public void isPatientIdentityInclude_3() {
        NhiRuleCheckDTO dto = new NhiRuleCheckDTO();
        NhiExtendDisposal ned = new NhiExtendDisposal();
        ned.setPatientIdentity(null);
        dto.setNhiExtendDisposal(ned);

        NhiRuleCheckResultDTO rdto = nhiRuleCheckUtil.isPatientIdentityInclude(dto, CopaymentCode._001);
        Assert.assertEquals(false, rdto.isValidated());
        Assert.assertEquals(CopaymentCode._001.getNotification(), rdto.getMessage());
    }

    /**
     * Test case for isPatientToothAtCodesBeforePeriod
     * 1. T, 恆牙未過期
     * 2. T, 乳牙未過期
     * 3. F, 恆牙過期
     * 4. F, 乳牙過期
     * 5. T, 時間內存在相同處置代碼，但不同牙位
     * 6. T, 在未來時間的處置，同代碼，同牙位
     * 7. F, 乳牙過期，恆牙未過期
     * 8. F, 乳牙未過期，恆牙過期
     */
    @Test
    public void isPatientToothAtCodesBeforePeriod_1() {
        NhiRuleCheckDTO dto = new NhiRuleCheckDTO();
        Patient p = new Patient();
        NhiExtendDisposal ned = new NhiExtendDisposal();
        NhiExtendTreatmentProcedure netp = new NhiExtendTreatmentProcedure();
        p.setId(DataGenerator.ID_1);
        ned.setPatientIdentity(null);
        netp.setA71(DataGenerator.NHI_TREATMENT_DATE_NOW_STRING);
        netp.setA74(DataGenerator.TOOTH_PERMANENT_1);
        dto.setPatient(p);
        dto.setNhiExtendDisposal(ned);
        dto.setNhiExtendTreatmentProcedure(netp);

        NhiRuleCheckResultDTO rdto = nhiRuleCheckUtil.isPatientToothAtCodesBeforePeriod(
            dto,
            Arrays.asList(new String[]{DataGenerator.NHI_CODE_1}.clone()),
            DateTimeUtil.NHI_12_MONTH,
            DateTimeUtil.NHI_18_MONTH);

        Assert.assertEquals(true, rdto.isValidated());
        Assert.assertEquals(null, rdto.getMessage());
    }

    @Test
    public void isPatientToothAtCodesBeforePeriod_2() {
        NhiRuleCheckDTO dto = new NhiRuleCheckDTO();
        Patient p = new Patient();
        NhiExtendDisposal ned = new NhiExtendDisposal();
        NhiExtendTreatmentProcedure netp = new NhiExtendTreatmentProcedure();
        p.setId(DataGenerator.ID_1);
        ned.setPatientIdentity(null);
        netp.setA71(DataGenerator.NHI_TREATMENT_DATE_NOW_STRING);
        netp.setA74(DataGenerator.TOOTH_DECIDUOUS_2);
        dto.setPatient(p);
        dto.setNhiExtendDisposal(ned);
        dto.setNhiExtendTreatmentProcedure(netp);

        NhiRuleCheckResultDTO rdto = nhiRuleCheckUtil.isPatientToothAtCodesBeforePeriod(
            dto,
            Arrays.asList(new String[]{DataGenerator.NHI_CODE_1}.clone()),
            DateTimeUtil.NHI_12_MONTH,
            DateTimeUtil.NHI_18_MONTH);

        Assert.assertEquals(true, rdto.isValidated());
        Assert.assertEquals(null, rdto.getMessage());
    }

    @Test
    public void isPatientToothAtCodesBeforePeriod_3() {
        NhiRuleCheckDTO dto = new NhiRuleCheckDTO();
        Patient p = new Patient();
        NhiExtendDisposal ned = new NhiExtendDisposal();
        NhiExtendTreatmentProcedure netp = new NhiExtendTreatmentProcedure();
        p.setId(DataGenerator.ID_1);
        ned.setPatientIdentity(null);
        netp.setId(DataGenerator.ID_1);
        netp.setA73(DataGenerator.NHI_CODE_1);
        netp.setA71(DataGenerator.NHI_TREATMENT_DATE_NOW_STRING);
        netp.setA74(DataGenerator.TOOTH_DECIDUOUS_2);
        dto.setPatient(p);
        dto.setNhiExtendDisposal(ned);
        dto.setNhiExtendTreatmentProcedure(netp);

        List<NhiExtendTreatmentProcedureTable> l = new ArrayList<>();
        l.add(new TableGenerator.NhiExtendTreatmentProcedureTableGenerator(
            DataGenerator.ID_2,
            DataGenerator.NHI_TREATMENT_DATE_NOW_STRING,
            DataGenerator.NHI_CODE_1,
            DataGenerator.TOOTH_DECIDUOUS_1,
            DataGenerator.SURFACE_BLANK
        ));

        NhiExtendTreatmentProcedure netpr = new NhiExtendTreatmentProcedure();
        netpr.setId(DataGenerator.ID_2);
        netpr.setA71(DataGenerator.NHI_TREATMENT_DATE_NOW_STRING);
        netpr.setA73(DataGenerator.NHI_CODE_1);
        netpr.setA74(DataGenerator.TOOTH_DECIDUOUS_1);
        netpr.setA75(DataGenerator.SURFACE_BLANK);

        Mockito
            .when(
                nhiExtendTreatmentProcedureRepository.findAllByTreatmentProcedure_Disposal_Registration_Appointment_Patient_IdAndA73In(
                any(Long.class),
                anyList()
            ))
            .thenReturn(l);

        Mockito
            .when(
                nhiExtendTreatmentProcedureMapper.nhiExtendTreatmentProcedureTableToNhiExtendTreatmentProcedureTable(
                    any(NhiExtendTreatmentProcedureTable.class)
                ))
            .thenReturn(netpr);

        NhiRuleCheckResultDTO rdto = nhiRuleCheckUtil.isPatientToothAtCodesBeforePeriod(
            dto,
            Arrays.asList(DataGenerator.NHI_CODE_1),
            DateTimeUtil.NHI_12_MONTH,
            DateTimeUtil.NHI_18_MONTH);

        Assert.assertEquals(false, rdto.isValidated());
        Assert.assertEquals(
            String.format(
            "%s 不可與 %s 在 %d 天內再次申報，上次申報 %s (%s, %d 天前)",
                dto.getNhiExtendTreatmentProcedure().getA73(),
                Arrays.asList(DataGenerator.NHI_CODE_1),
                DateTimeUtil.NHI_12_MONTH.getDays(),
                DataGenerator.NHI_CODE_1,
                DateTimeUtil.transformROCDateToLocalDate(DataGenerator.NHI_TREATMENT_DATE_NOW_STRING),
                Duration.between(
                    DateTimeUtil.transformROCDateToLocalDate(DataGenerator.NHI_TREATMENT_DATE_NOW_STRING).atStartOfDay(),
                    DateTimeUtil.transformROCDateToLocalDate(DataGenerator.NHI_TREATMENT_DATE_NOW_STRING).atStartOfDay()).toDays()
            ),
            rdto.getMessage());
    }

    /**
     * Test case for parseNhiCode
     * 1. 單一個代碼
     * 2. 單一組合代碼 e.g. 91001C~91002C
     * 3. 多代碼，包含空白 e.g. "1234", "aasd8f71", "", "a19asdc"
     * 4. 多組合代碼，e.g. "91001C~91002C", "90001C~90003C", "1~5"
     * 5. 綜合且不含不被 parse 的內容 e.g. "90001C~90003C", "1234", "1234asdf", "1~4"
     * 6. 綜合且包含不被 parse 的內容 e.g. "90001C-90003C", "90001C~90003C", "81~819C", "1234", "1234asdf", "1~4"
     */
    @Test
    public void parseNhiCode_1() {
        List<String> result = nhiRuleCheckUtil.parseNhiCode(Arrays.asList("91001C"));

        Assert.assertEquals(
            Arrays.asList("91001C").toString(),
            result.toString());
    }

    @Test
    public void parseNhiCode_2() {
        List<String> result = nhiRuleCheckUtil.parseNhiCode(Arrays.asList("91001C~91002C"));

        Assert.assertEquals(
            Arrays.asList("91001C", "91002C").toString(),
            result.toString());
    }

    @Test
    public void parseNhiCode_3() {
        List<String> result = nhiRuleCheckUtil.parseNhiCode(Arrays.asList("1234", "aasd8f71", "", "a19asdc"));

        Assert.assertEquals(
            Arrays.asList("1234", "aasd8f71", "", "a19asdc").toString(),
                result.toString());
    }

    @Test
    public void parseNhiCode_4() {
        List<String> result = nhiRuleCheckUtil.parseNhiCode(Arrays.asList("91001C~91002C", "90001C~90003C", "1~5"));

        Assert.assertEquals(
            Arrays.asList("91001C", "91002C", "90001C", "90002C", "90003C", "1", "2", "3", "4", "5").toString(),
                result.toString());
    }

    @Test
    public void parseNhiCode_5() {
        List<String> result = nhiRuleCheckUtil.parseNhiCode(Arrays.asList("90001C~90003C", "1234", "1234asdf", "1~4"));

        Assert.assertEquals(
            Arrays.asList("90001C", "90002C", "90003C", "1234", "1234asdf", "1", "2", "3", "4").toString(),
            result.toString());
    }

    @Test
    public void parseNhiCode_6() {
        List<String> result = nhiRuleCheckUtil.parseNhiCode(Arrays.asList("90001C-90003C", "90001C~90003C", "81~819C", "1234", "1234asdf", "1~4"));

        Assert.assertEquals(
            Arrays.asList("90001C-90003C", "90001C", "90002C", "90003C", "81~819C", "1234", "1234asdf", "1", "2", "3", "4").toString(),
            result.toString());
    }

}
