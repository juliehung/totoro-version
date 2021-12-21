package io.dentall.totoro.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dentall.totoro.TotoroApp;
import io.dentall.totoro.business.service.ImageBusinessService;
import io.dentall.totoro.domain.*;
import io.dentall.totoro.repository.*;
import io.dentall.totoro.web.rest.vm.PatientDocumentVM;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import javax.persistence.EntityManager;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

import static io.dentall.totoro.service.util.FileNameUtil.getExtension;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.maxBy;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TotoroApp.class)
@AutoConfigureMockMvc
@WithMockUser(username = "admin", password = "admin")
public class PatientDocumentResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EntityManager em;

    @Autowired
    private DisposalRepository disposalRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private RegistrationRepository registrationRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ImageRelationRepository imageRelationRepository;

    @Autowired
    private PatientDocumentRepository patientDocumentRepository;

    @Autowired
    private HashtagRepository hashtagRepository;

    @Autowired
    private Optional<ImageBusinessService> imageBusinessServiceOptional;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${gcp.gcs-base-url}")
    private String gcsBaseUrl;

    @Value("${gcp.bucket-name}")
    private String bucketName;

    private User user;

    @Before
    public void before() {
        user = TestUtil.createUser(em, userRepository);
    }

    @After
    public void after() {
        // 原本欲使用@Transactional產生自動rollback，清除測試過程產生的測試資料
        // 而因為「post /api/patient-document」是非同步作業，所以在執行時，無法查到預先產生的病人資料，所以會發生查無病人資料的錯誤
        // 只好不使用@Transactional，讓資料直接進資料庫，測試完後再手動清除資料
        patientDocumentRepository.deleteAllInBatch();
        hashtagRepository.deleteAllInBatch();
        disposalRepository.deleteAllInBatch();
        appointmentRepository.deleteAllInBatch();
        registrationRepository.deleteAllInBatch();
        imageRelationRepository.deleteAllInBatch();
        imageRepository.deleteAllInBatch();
        patientRepository.deleteAllInBatch();
        userRepository.delete(user);
    }

    @Test
    public void testCreateDocumentForPatientNotFound() throws Exception {
        long patientId = -1L;
        Long disposalId = -1L;

        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "a.txt", "text/plain", "hi".getBytes(StandardCharsets.UTF_8));
        ResultActions resultActions = uploadDocument2(patientId, disposalId, mockMultipartFile);
        resultActions.andExpect(jsonPath("$.title").value(equalTo("patient does not found")));
    }

    @Test
    public void testCreateDocumentForDisposalNotFound() throws Exception {
        Disposal disposal = TestUtil.createDisposal(user, em, registrationRepository, appointmentRepository, disposalRepository, patientRepository);
        Long patientId = disposal.getRegistration().getAppointment().getPatient().getId();
        Long disposalId = -1L;

        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "a.txt", "text/plain", "hi".getBytes(StandardCharsets.UTF_8));
        ResultActions resultActions = uploadDocument2(patientId, disposalId, mockMultipartFile);
        resultActions.andExpect(jsonPath("$.title").value(equalTo("disposal is not found or disposal is not belong to patient")));
    }

    @Test
    public void testCreateDocumentForEmptyFile() throws Exception {
        Disposal disposal = TestUtil.createDisposal(user, em, registrationRepository, appointmentRepository, disposalRepository, patientRepository);
        Long patientId = disposal.getRegistration().getAppointment().getPatient().getId();
        Long disposalId = disposal.getId();

        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "a.txt", "text/plain", "".getBytes(StandardCharsets.UTF_8));
        ResultActions resultActions = uploadDocument2(patientId, disposalId, mockMultipartFile);
        resultActions.andExpect(jsonPath("$.title").value(equalTo("Upload not include file or it is empty")));
    }

    @Test
    public void testCreateDocument() throws Exception {
        Disposal disposal = TestUtil.createDisposal(user, em, registrationRepository, appointmentRepository, disposalRepository, patientRepository);
        Long patientId = disposal.getRegistration().getAppointment().getPatient().getId();
        Long disposalId = disposal.getId();

        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "a.txt", "text/plain", "hi".getBytes(StandardCharsets.UTF_8));
        ResultActions resultActions = uploadDocument2(patientId, disposalId, mockMultipartFile);
        resultActions.andExpect(jsonPath("$.id").value(notNullValue()));
        resultActions.andExpect(jsonPath("$.patientId").value(equalTo(patientId.intValue())));
        resultActions.andExpect(jsonPath("$.disposal.id").value(equalTo(disposalId.intValue())));
        resultActions.andExpect(jsonPath("$.document.id").value(notNullValue()));
        resultActions.andExpect(jsonPath("$.document.title").value(equalTo(mockMultipartFile.getOriginalFilename())));
        resultActions.andExpect(jsonPath("$.document.filePath").value(equalTo(getFilePath(patientId))));
        resultActions.andExpect(jsonPath("$.document.fileName").value(endsWith(mockMultipartFile.getOriginalFilename())));
        resultActions.andExpect(jsonPath("$.document.fileRealName").value(equalTo(mockMultipartFile.getOriginalFilename())));
        resultActions.andExpect(jsonPath("$.document.fileExtension").value(equalTo(getExtension(mockMultipartFile.getOriginalFilename()))));
        resultActions.andExpect(jsonPath("$.document.fileSize").value(equalTo((int) mockMultipartFile.getSize())));
        resultActions.andExpect(jsonPath("$.document.url").value(equalTo(gcsBaseUrl + "/" + bucketName + "/")));
    }

    private String getFilePath(Long patientId) {
        return imageBusinessServiceOptional.map(service -> service.createImagePath(patientId)).orElse(String.valueOf(patientId));
    }

    @Test
    public void testFindDocument() throws Exception {
        Disposal disposal = TestUtil.createDisposal(user, em, registrationRepository, appointmentRepository, disposalRepository, patientRepository);
        Long patientId = disposal.getRegistration().getAppointment().getPatient().getId();
        Long disposalId = disposal.getId();
        String fileName = disposalId + ".txt";
        String fileContent = String.valueOf(disposalId);

        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", fileName, "text/plain", fileContent.getBytes(StandardCharsets.UTF_8));
        PatientDocumentVM patientDocumentVM = uploadDocument(patientId, disposalId, mockMultipartFile);
        ResultActions resultActions;

        resultActions = findDocument(patientId);
        resultActions.andExpect(jsonPath("$.length()").value(equalTo(1)));
        resultActions.andExpect(jsonPath("$[0].id").value(equalTo(patientDocumentVM.getId().intValue())));
        resultActions.andExpect(jsonPath("$[0].patientId").value(equalTo(patientDocumentVM.getPatientId().intValue())));
        resultActions.andExpect(jsonPath("$[0].disposal.id").value(equalTo(patientDocumentVM.getDisposal().getId().intValue())));
        resultActions.andExpect(jsonPath("$[0].document.id").value(equalTo(patientDocumentVM.getDocument().getId().intValue())));
        resultActions.andExpect(jsonPath("$[0].document.title").value(equalTo(patientDocumentVM.getDocument().getTitle())));
        resultActions.andExpect(jsonPath("$[0].document.filePath").value(equalTo(patientDocumentVM.getDocument().getFilePath())));
        resultActions.andExpect(jsonPath("$[0].document.fileName").value(equalTo(patientDocumentVM.getDocument().getFileName())));
        resultActions.andExpect(jsonPath("$[0].document.fileRealName").value(equalTo(patientDocumentVM.getDocument().getFileRealName())));
        resultActions.andExpect(jsonPath("$[0].document.fileExtension").value(equalTo(patientDocumentVM.getDocument().getFileExtension())));
        resultActions.andExpect(jsonPath("$[0].document.fileSize").value(equalTo(patientDocumentVM.getDocument().getFileSize().intValue())));
        resultActions.andExpect(jsonPath("$[0].document.url").value(equalTo(patientDocumentVM.getDocument().getUrl())));
    }

    @Test
    public void testFindDocumentByDisposal() throws Exception {
        Patient patient = TestUtil.createPatient2(user, em, patientRepository);
        Disposal disposal1 = TestUtil.createDisposal(user, patient, em, registrationRepository, appointmentRepository, disposalRepository);
        Disposal disposal2 = TestUtil.createDisposal(user, patient, em, registrationRepository, appointmentRepository, disposalRepository);
        Disposal disposal3 = TestUtil.createDisposal(user, patient, em, registrationRepository, appointmentRepository, disposalRepository);

        List<Disposal> disposalList = Arrays.asList(disposal1, disposal2, disposal3);

        Map<Long, Optional<PatientDocumentVM>> patientDocumentVMList = disposalList.stream()
            .map(disposal -> {
                Long patientId = disposal.getRegistration().getAppointment().getPatient().getId();
                Long disposalId = disposal.getId();
                String fileName = disposalId + ".txt";
                String fileContent = String.valueOf(disposalId);
                PatientDocumentVM patientDocumentVM = null;
                try {
                    MockMultipartFile mockMultipartFile = new MockMultipartFile("file", fileName, "text/plain", fileContent.getBytes(StandardCharsets.UTF_8));
                    patientDocumentVM = uploadDocument(patientId, disposalId, mockMultipartFile);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return patientDocumentVM;
            })
            .filter(Objects::nonNull)
            .collect(groupingBy(vm -> vm.getDisposal().getId(), maxBy(comparing(vm -> vm.getDisposal().getId()))));

        ResultActions resultActions = findDocument(patient.getId(), disposal2.getId());
        PatientDocumentVM patientDocumentVM = patientDocumentVMList.get(disposal2.getId()).orElseThrow(() -> new Exception("disposal not found"));

        resultActions.andExpect(jsonPath("$.length()").value(equalTo(1)));
        resultActions.andExpect(jsonPath("$[0].id").value(equalTo(patientDocumentVM.getId().intValue())));
        resultActions.andExpect(jsonPath("$[0].patientId").value(equalTo(patientDocumentVM.getPatientId().intValue())));
        resultActions.andExpect(jsonPath("$[0].disposal.id").value(equalTo(patientDocumentVM.getDisposal().getId().intValue())));
        resultActions.andExpect(jsonPath("$[0].document.id").value(equalTo(patientDocumentVM.getDocument().getId().intValue())));
        resultActions.andExpect(jsonPath("$[0].document.title").value(equalTo(patientDocumentVM.getDocument().getTitle())));
        resultActions.andExpect(jsonPath("$[0].document.filePath").value(equalTo(patientDocumentVM.getDocument().getFilePath())));
        resultActions.andExpect(jsonPath("$[0].document.fileName").value(equalTo(patientDocumentVM.getDocument().getFileName())));
        resultActions.andExpect(jsonPath("$[0].document.fileRealName").value(equalTo(patientDocumentVM.getDocument().getFileRealName())));
        resultActions.andExpect(jsonPath("$[0].document.fileExtension").value(equalTo(patientDocumentVM.getDocument().getFileExtension())));
        resultActions.andExpect(jsonPath("$[0].document.fileSize").value(equalTo(patientDocumentVM.getDocument().getFileSize().intValue())));
        resultActions.andExpect(jsonPath("$[0].document.url").value(equalTo(patientDocumentVM.getDocument().getUrl())));
    }

    @Test
    public void testFindDocumentSearch() throws Exception {
        Patient patient = TestUtil.createPatient2(user, em, patientRepository);
        Disposal disposal1 = TestUtil.createDisposal(user, patient, em, registrationRepository, appointmentRepository, disposalRepository);
        Disposal disposal2 = TestUtil.createDisposal(user, patient, em, registrationRepository, appointmentRepository, disposalRepository);
        Disposal disposal3 = TestUtil.createDisposal(user, patient, em, registrationRepository, appointmentRepository, disposalRepository);

        List<Disposal> disposalList = Arrays.asList(disposal1, disposal2, disposal3);

        Map<Long, Optional<PatientDocumentVM>> patientDocumentVMList = disposalList.stream()
            .map(disposal -> {
                Long patientId = disposal.getRegistration().getAppointment().getPatient().getId();
                Long disposalId = disposal.getId();
                String fileName = disposalId + ".txt";
                String fileContent = String.valueOf(disposalId);
                PatientDocumentVM patientDocumentVM = null;
                try {
                    MockMultipartFile mockMultipartFile = new MockMultipartFile("file", fileName, "text/plain", fileContent.getBytes(StandardCharsets.UTF_8));
                    patientDocumentVM = uploadDocument(patientId, disposalId, mockMultipartFile);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return patientDocumentVM;
            })
            .filter(Objects::nonNull)
            .collect(groupingBy(vm -> vm.getDisposal().getId(), maxBy(comparing(vm -> vm.getDisposal().getId()))));

        ResultActions resultActions = findDocument(patient.getId(), disposal2.getId());
        PatientDocumentVM patientDocumentVM = patientDocumentVMList.get(disposal2.getId()).orElseThrow(() -> new Exception("disposal not found"));

        resultActions.andExpect(jsonPath("$.length()").value(equalTo(1)));
        resultActions.andExpect(jsonPath("$[0].id").value(equalTo(patientDocumentVM.getId().intValue())));
        resultActions.andExpect(jsonPath("$[0].patientId").value(equalTo(patientDocumentVM.getPatientId().intValue())));
        resultActions.andExpect(jsonPath("$[0].disposal.id").value(equalTo(patientDocumentVM.getDisposal().getId().intValue())));
        resultActions.andExpect(jsonPath("$[0].document.id").value(equalTo(patientDocumentVM.getDocument().getId().intValue())));
        resultActions.andExpect(jsonPath("$[0].document.title").value(equalTo(patientDocumentVM.getDocument().getTitle())));
        resultActions.andExpect(jsonPath("$[0].document.filePath").value(equalTo(patientDocumentVM.getDocument().getFilePath())));
        resultActions.andExpect(jsonPath("$[0].document.fileName").value(equalTo(patientDocumentVM.getDocument().getFileName())));
        resultActions.andExpect(jsonPath("$[0].document.fileRealName").value(equalTo(patientDocumentVM.getDocument().getFileRealName())));
        resultActions.andExpect(jsonPath("$[0].document.fileExtension").value(equalTo(patientDocumentVM.getDocument().getFileExtension())));
        resultActions.andExpect(jsonPath("$[0].document.fileSize").value(equalTo(patientDocumentVM.getDocument().getFileSize().intValue())));
        resultActions.andExpect(jsonPath("$[0].document.url").value(equalTo(patientDocumentVM.getDocument().getUrl())));
    }

    @Test
    public void testFindDocumentSortByTitle() throws Exception {
        Patient patient = TestUtil.createPatient2(user, em, patientRepository);
        Disposal disposal1 = TestUtil.createDisposal(user, patient, em, registrationRepository, appointmentRepository, disposalRepository);
        Disposal disposal2 = TestUtil.createDisposal(user, patient, em, registrationRepository, appointmentRepository, disposalRepository);
        Disposal disposal3 = TestUtil.createDisposal(user, patient, em, registrationRepository, appointmentRepository, disposalRepository);
        Map<Long, Optional<PatientDocumentVM>> patientDocumentVMList = uploadDocument(Arrays.asList(disposal1, disposal2, disposal3));

        ResultActions resultActions = findDocument(patient.getId(), "document.title,asc");
        PatientDocumentVM patientDocumentVM = patientDocumentVMList.get(disposal1.getId()).orElseThrow(() -> new Exception("disposal not found"));
        checkSorting(resultActions, 3, patientDocumentVM);

        resultActions = findDocument(patient.getId(), "document.title,desc");
        patientDocumentVM = patientDocumentVMList.get(disposal3.getId()).orElseThrow(() -> new Exception("disposal not found"));
        checkSorting(resultActions, 3, patientDocumentVM);
    }

    @Test
    public void testFindDocumentSortByDisposalDateTime() throws Exception {
        Patient patient = TestUtil.createPatient2(user, em, patientRepository);
        Disposal disposalEntity1 = DisposalResourceIntTest.createEntity(em);
        disposalEntity1.setDateTime(LocalDateTime.now().minusDays(5).toInstant(ZoneOffset.UTC));
        Disposal disposal1 = TestUtil.createDisposal(user, patient, disposalEntity1, em, registrationRepository, appointmentRepository, disposalRepository);

        Disposal disposalEntity2 = DisposalResourceIntTest.createEntity(em);
        disposalEntity2.setDateTime(LocalDateTime.now().minusDays(3).toInstant(ZoneOffset.UTC));
        Disposal disposal2 = TestUtil.createDisposal(user, patient, disposalEntity2, em, registrationRepository, appointmentRepository, disposalRepository);

        Disposal disposalEntity3 = DisposalResourceIntTest.createEntity(em);
        disposalEntity3.setDateTime(LocalDateTime.now().minusDays(1).toInstant(ZoneOffset.UTC));
        Disposal disposal3 = TestUtil.createDisposal(user, patient, disposalEntity3, em, registrationRepository, appointmentRepository, disposalRepository);

        Map<Long, Optional<PatientDocumentVM>> patientDocumentVMList = uploadDocument(Arrays.asList(disposal1, disposal2, disposal3));

        ResultActions resultActions = findDocument(patient.getId(), "disposal.dateTime,asc");
        PatientDocumentVM patientDocumentVM = patientDocumentVMList.get(disposal1.getId()).orElseThrow(() -> new Exception("disposal not found"));
        checkSorting(resultActions, 3, patientDocumentVM);

        resultActions = findDocument(patient.getId(), "disposal.dateTime,desc");
        patientDocumentVM = patientDocumentVMList.get(disposal3.getId()).orElseThrow(() -> new Exception("disposal not found"));
        checkSorting(resultActions, 3, patientDocumentVM);
    }

    @Test
    public void testFindDocumentSortByUploadTime() throws Exception {
        Patient patient = TestUtil.createPatient2(user, em, patientRepository);
        Disposal disposal1 = TestUtil.createDisposal(user, patient, em, registrationRepository, appointmentRepository, disposalRepository);
        Disposal disposal2 = TestUtil.createDisposal(user, patient, em, registrationRepository, appointmentRepository, disposalRepository);
        Disposal disposal3 = TestUtil.createDisposal(user, patient, em, registrationRepository, appointmentRepository, disposalRepository);
        Map<Long, Optional<PatientDocumentVM>> patientDocumentVMList = uploadDocument(Arrays.asList(disposal1, disposal2, disposal3));

        ResultActions resultActions = findDocument(patient.getId(), "document.uploadTime,asc");
        PatientDocumentVM patientDocumentVM = patientDocumentVMList.get(disposal1.getId()).orElseThrow(() -> new Exception("disposal not found"));
        checkSorting(resultActions, 3, patientDocumentVM);

        resultActions = findDocument(patient.getId(), "document.uploadTime,desc");
        patientDocumentVM = patientDocumentVMList.get(disposal3.getId()).orElseThrow(() -> new Exception("disposal not found"));
        checkSorting(resultActions, 3, patientDocumentVM);
    }

    @Test
    public void testFindDocumentSortByModifiedTime() throws Exception {
        Patient patient = TestUtil.createPatient2(user, em, patientRepository);
        Disposal disposal1 = TestUtil.createDisposal(user, patient, em, registrationRepository, appointmentRepository, disposalRepository);
        Disposal disposal2 = TestUtil.createDisposal(user, patient, em, registrationRepository, appointmentRepository, disposalRepository);
        Disposal disposal3 = TestUtil.createDisposal(user, patient, em, registrationRepository, appointmentRepository, disposalRepository);
        Map<Long, Optional<PatientDocumentVM>> patientDocumentVMList = uploadDocument(Arrays.asList(disposal1, disposal2, disposal3));

        ResultActions resultActions = findDocument(patient.getId(), "document.modifiedTime,asc");
        PatientDocumentVM patientDocumentVM = patientDocumentVMList.get(disposal1.getId()).orElseThrow(() -> new Exception("disposal not found"));
        checkSorting(resultActions, 3, patientDocumentVM);

        resultActions = findDocument(patient.getId(), "document.modifiedTime,desc");
        patientDocumentVM = patientDocumentVMList.get(disposal3.getId()).orElseThrow(() -> new Exception("disposal not found"));
        checkSorting(resultActions, 3, patientDocumentVM);
    }

    private Map<Long, Optional<PatientDocumentVM>> uploadDocument(List<Disposal> disposalList) {
        return disposalList.stream()
            .map(disposal -> {
                Long patientId = disposal.getRegistration().getAppointment().getPatient().getId();
                Long disposalId = disposal.getId();
                String fileName = disposalId + ".txt";
                String fileContent = String.valueOf(disposalId);
                PatientDocumentVM patientDocumentVM = null;
                try {
                    MockMultipartFile mockMultipartFile = new MockMultipartFile("file", fileName, "text/plain", fileContent.getBytes(StandardCharsets.UTF_8));
                    patientDocumentVM = uploadDocument(patientId, disposalId, mockMultipartFile);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return patientDocumentVM;
            })
            .filter(Objects::nonNull)
            .collect(groupingBy(vm -> vm.getDisposal().getId(), maxBy(comparing(vm -> vm.getDisposal().getId()))));
    }

    private void checkSorting(ResultActions resultActions, int length, PatientDocumentVM patientDocumentVM) throws Exception {
        resultActions.andExpect(jsonPath("$.length()").value(equalTo(length)));
        resultActions.andExpect(jsonPath("$[0].id").value(equalTo(patientDocumentVM.getId().intValue())));
        resultActions.andExpect(jsonPath("$[0].patientId").value(equalTo(patientDocumentVM.getPatientId().intValue())));
        resultActions.andExpect(jsonPath("$[0].disposal.id").value(equalTo(patientDocumentVM.getDisposal().getId().intValue())));
        resultActions.andExpect(jsonPath("$[0].document.id").value(equalTo(patientDocumentVM.getDocument().getId().intValue())));
        resultActions.andExpect(jsonPath("$[0].document.title").value(equalTo(patientDocumentVM.getDocument().getTitle())));
        resultActions.andExpect(jsonPath("$[0].document.filePath").value(equalTo(patientDocumentVM.getDocument().getFilePath())));
        resultActions.andExpect(jsonPath("$[0].document.fileName").value(equalTo(patientDocumentVM.getDocument().getFileName())));
        resultActions.andExpect(jsonPath("$[0].document.fileRealName").value(equalTo(patientDocumentVM.getDocument().getFileRealName())));
        resultActions.andExpect(jsonPath("$[0].document.fileExtension").value(equalTo(patientDocumentVM.getDocument().getFileExtension())));
        resultActions.andExpect(jsonPath("$[0].document.fileSize").value(equalTo(patientDocumentVM.getDocument().getFileSize().intValue())));
        resultActions.andExpect(jsonPath("$[0].document.url").value(equalTo(patientDocumentVM.getDocument().getUrl())));
    }

    @Test
    public void testUpdateDocumentForPatientDocumentIdEmpty() throws Exception {
        long patientId = -1L;

        PatientDocument patientDocument = new PatientDocument();

        MockHttpServletRequestBuilder requestBuilder = put("/api/patient-document/" + patientId)
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsBytes(patientDocument));

        ResultActions resultActions = mockMvc.perform(requestBuilder);
        resultActions.andExpect(jsonPath("$.title").value(equalTo("patient document id is not provided")));
    }

    @Test
    public void testUpdateDocumentForDisposalEmpty() throws Exception {
        long patientId = -1L;

        PatientDocument patientDocument = new PatientDocument();
        patientDocument.setId(-1L);

        MockHttpServletRequestBuilder requestBuilder = put("/api/patient-document/" + patientId)
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsBytes(patientDocument));

        ResultActions resultActions = mockMvc.perform(requestBuilder);
        resultActions.andExpect(jsonPath("$.title").value(equalTo("disposal is not provided")));
    }

    @Test
    public void testUpdateDocumentForDisposalIdEmpty() throws Exception {
        long patientId = -1L;

        PatientDocument patientDocument = new PatientDocument();
        patientDocument.setId(-1L);
        patientDocument.setDisposal(new PatientDocumentDisposal());

        MockHttpServletRequestBuilder requestBuilder = put("/api/patient-document/" + patientId)
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsBytes(patientDocument));

        ResultActions resultActions = mockMvc.perform(requestBuilder);
        resultActions.andExpect(jsonPath("$.title").value(equalTo("disposal id is not provided")));
    }

    @Test
    public void testUpdateDocumentForPatientDocumentNotExist() throws Exception {
        long patientId = -1L;

        PatientDocumentDisposal patientDocumentDisposal = new PatientDocumentDisposal();
        patientDocumentDisposal.setId(-1L);
        PatientDocument patientDocument = new PatientDocument();
        patientDocument.setId(-1L);
        patientDocument.setDisposal(patientDocumentDisposal);

        MockHttpServletRequestBuilder requestBuilder = put("/api/patient-document/" + patientId)
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsBytes(patientDocument));

        ResultActions resultActions = mockMvc.perform(requestBuilder);
        resultActions.andExpect(jsonPath("$.title").value(equalTo("patient document is not found")));
    }

    @Test
    public void testUpdateDocumentForPatientNotMatch() throws Exception {
        Disposal disposal = TestUtil.createDisposal(user, em, registrationRepository, appointmentRepository, disposalRepository, patientRepository);
        Long patientId = disposal.getRegistration().getAppointment().getPatient().getId();
        Long disposalId = disposal.getId();

        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "a.txt", "text/plain", "hi".getBytes(StandardCharsets.UTF_8));
        PatientDocumentVM patientDocumentVM = uploadDocument(patientId, disposalId, mockMultipartFile);

        PatientDocumentDisposal patientDocumentDisposal = new PatientDocumentDisposal();
        patientDocumentDisposal.setId(-1L);

        PatientDocument patientDocument = new PatientDocument();
        patientDocument.setId(patientDocumentVM.getId());
        patientDocument.setPatientId(patientId);
        patientDocument.setDisposal(patientDocumentDisposal);

        long fakePatientId = -1L;
        MockHttpServletRequestBuilder requestBuilder = put("/api/patient-document/" + fakePatientId)
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsBytes(patientDocument));

        ResultActions resultActions = mockMvc.perform(requestBuilder);
        resultActions.andExpect(jsonPath("$.title").value(equalTo("patient id is not match")));
    }

    @Test
    public void testUpdateDocumentForDisposalNotMatch() throws Exception {
        Disposal disposal = TestUtil.createDisposal(user, em, registrationRepository, appointmentRepository, disposalRepository, patientRepository);
        Long patientId = disposal.getRegistration().getAppointment().getPatient().getId();
        Long disposalId = disposal.getId();

        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "a.txt", "text/plain", "hi".getBytes(StandardCharsets.UTF_8));
        PatientDocumentVM patientDocumentVM = uploadDocument(patientId, disposalId, mockMultipartFile);

        ResultActions resultActions = updateDocument(patientDocumentVM, -1L, new Document());
        resultActions.andExpect(jsonPath("$.title").value(equalTo("disposal is not found or disposal is not belong to patient")));
    }

    @Test
    public void testUpdateDocument() throws Exception {
        Patient patient = TestUtil.createPatient2(user, em, patientRepository);
        Disposal disposal1 = TestUtil.createDisposal(user, patient, em, registrationRepository, appointmentRepository, disposalRepository);
        Disposal disposal2 = TestUtil.createDisposal(user, patient, em, registrationRepository, appointmentRepository, disposalRepository);
        Long patientId = disposal1.getRegistration().getAppointment().getPatient().getId();
        Long disposalId = disposal1.getId();

        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", disposalId + ".txt", "text/plain", "hi".getBytes(StandardCharsets.UTF_8));
        PatientDocumentVM patientDocumentVM = uploadDocument(patientId, disposalId, mockMultipartFile);

        Document document = new Document();
        // allow modified
        document.setTitle("NewTitle");
        document.setDescription("dot dot dot #dot");
        document.setHashtags(new String[]{"#dot"});
        // not allow modified
        document.setId(-1L);
        document.setFileName("newFileName");
        document.setFileRealName("newFileRealName");
        document.setFilePath("newFilePath");
        document.setUploadUser("newUploadUser");
        document.setUploadTime(Instant.now());
        document.setFileSize(0L);
        document.setFileExtension("newFileExtension");

        ResultActions resultActions = updateDocument(patientDocumentVM, disposal2.getId(), document);
        resultActions.andExpect(jsonPath("$.id").value(equalTo(patientDocumentVM.getId().intValue())));
        resultActions.andExpect(jsonPath("$.patientId").value(equalTo(patientDocumentVM.getPatientId().intValue())));
        resultActions.andExpect(jsonPath("$.disposal.id").value(equalTo(disposal2.getId().intValue())));
        resultActions.andExpect(jsonPath("$.document.id").value(equalTo(patientDocumentVM.getDocument().getId().intValue())));
        resultActions.andExpect(jsonPath("$.document.filePath").value(equalTo(patientDocumentVM.getDocument().getFilePath())));
        resultActions.andExpect(jsonPath("$.document.fileName").value(endsWith(patientDocumentVM.getDocument().getFileName())));
        resultActions.andExpect(jsonPath("$.document.fileRealName").value(equalTo(patientDocumentVM.getDocument().getFileRealName())));
        resultActions.andExpect(jsonPath("$.document.fileExtension").value(equalTo(patientDocumentVM.getDocument().getFileExtension())));
        resultActions.andExpect(jsonPath("$.document.fileSize").value(equalTo(patientDocumentVM.getDocument().getFileSize().intValue())));
        resultActions.andExpect(jsonPath("$.document.url").value(equalTo(patientDocumentVM.getDocument().getUrl())));
        resultActions.andExpect(jsonPath("$.document.title").value(equalTo(document.getTitle())));
        resultActions.andExpect(jsonPath("$.document.description").value(equalTo(document.getDescription())));
        resultActions.andExpect(jsonPath("$.document.hashtags").value(hasItems(document.getHashtags())));
    }

    @Test
    public void testDeleteDocumentForPatientDocumentNotExist() throws Exception {
        ResultActions resultActions = deleteDocument(-1L, -1L);
        resultActions.andExpect(jsonPath("$.title").value(equalTo("patient document is not found")));
    }

    @Test
    public void testDeleteDocumentForPatientNotMatch() throws Exception {
        Disposal disposal = TestUtil.createDisposal(user, em, registrationRepository, appointmentRepository, disposalRepository, patientRepository);
        Long patientId = disposal.getRegistration().getAppointment().getPatient().getId();
        Long disposalId = disposal.getId();

        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "a.txt", "text/plain", "hi".getBytes(StandardCharsets.UTF_8));
        PatientDocumentVM patientDocumentVM = uploadDocument(patientId, disposalId, mockMultipartFile);

        ResultActions resultActions = deleteDocument(-1L, patientDocumentVM.getId());
        resultActions.andExpect(jsonPath("$.title").value(equalTo("patient id is not match")));
    }

    @Test
    public void testDeleteDocument() throws Exception {
        Disposal disposal = TestUtil.createDisposal(user, em, registrationRepository, appointmentRepository, disposalRepository, patientRepository);
        Long patientId = disposal.getRegistration().getAppointment().getPatient().getId();
        Long disposalId = disposal.getId();

        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "a.txt", "text/plain", "hi".getBytes(StandardCharsets.UTF_8));
        PatientDocumentVM patientDocumentVM = uploadDocument(patientId, disposalId, mockMultipartFile);
        MockHttpServletRequestBuilder requestBuilder;
        ResultActions resultActions;

        resultActions = findDocument(patientId, disposalId);
        resultActions.andExpect(jsonPath("$.length()").value(equalTo(1)));

        resultActions = deleteDocument(patientId, patientDocumentVM.getId());
        resultActions.andExpect(jsonPath("$.id").value(equalTo(patientDocumentVM.getId().intValue())));

        resultActions = findDocument(patientId, disposalId);
        resultActions.andExpect(jsonPath("$.length()").value(equalTo(0)));
    }

    /**
     * 測試單一hashtag查詢
     *
     * @throws Exception
     */
    @Test
    public void testSearchPatientDocumentTag1() throws Exception {
        Patient patient = TestUtil.createPatient2(user, em, patientRepository);
        Disposal disposal1 = TestUtil.createDisposal(user, patient, em, registrationRepository, appointmentRepository, disposalRepository);
        Long patientId = disposal1.getRegistration().getAppointment().getPatient().getId();
        Long disposalId = disposal1.getId();

        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", disposalId + ".txt", "text/plain", "hi".getBytes(StandardCharsets.UTF_8));
        PatientDocumentVM patientDocumentVM = uploadDocument(patientId, disposal1.getId(), mockMultipartFile);

        Document document = new Document();
        document.setHashtags(new String[]{"#dot", "#etc"});
        updateDocument(patientDocumentVM, disposal1.getId(), document);

        ResultActions resultActions;

        resultActions = findTag("#dot");
        resultActions.andExpect(jsonPath("$[*].tagName").value(hasItem("#dot")));

        resultActions = findTag("#DOT");
        resultActions.andExpect(jsonPath("$[*].tagName").value(hasItem("#dot")));

        resultActions = findTag("dot");
        resultActions.andExpect(jsonPath("$[*].tagName").value(hasItem("#dot")));

        resultActions = findTag("DOT");
        resultActions.andExpect(jsonPath("$[*].tagName").value(hasItem("#dot")));

        resultActions = findTag("DoT");
        resultActions.andExpect(jsonPath("$[*].tagName").value(hasItem("#dot")));

        resultActions = findTag("d");
        resultActions.andExpect(jsonPath("$[*].tagName").value(hasItem("#dot")));

        resultActions = findTag("o");
        resultActions.andExpect(jsonPath("$[*].tagName").value(hasItem("#dot")));

        resultActions = findTag("t");
        resultActions.andExpect(jsonPath("$[*].tagName").value(hasItem("#dot")));

        resultActions = findTag("");
        resultActions.andExpect(jsonPath("$[*].tagName").value(hasItem("#dot")));

        resultActions = findTag("Pneumonoultramicroscopicsilicovolcanoconiosis");
        resultActions.andExpect(jsonPath("$.length()").value(equalTo(0)));
    }

    /**
     * 測試多個hashtag查詢
     *
     * @throws Exception
     */
    @Test
    public void testSearchPatientDocumentTag2() throws Exception {
        Patient patient = TestUtil.createPatient2(user, em, patientRepository);
        Disposal disposal1 = TestUtil.createDisposal(user, patient, em, registrationRepository, appointmentRepository, disposalRepository);
        Long patientId = disposal1.getRegistration().getAppointment().getPatient().getId();
        Long disposalId = disposal1.getId();

        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", disposalId + ".txt", "text/plain", "hi".getBytes(StandardCharsets.UTF_8));
        PatientDocumentVM patientDocumentVM = uploadDocument(patientId, disposal1.getId(), mockMultipartFile);

        Document document = new Document();
        document.setHashtags(new String[]{"#dot", "#etc"});
        updateDocument(patientDocumentVM, disposal1.getId(), document);

        ResultActions resultActions;

        resultActions = findTag("#dot");
        resultActions.andExpect(jsonPath("$[*].tagName").value(hasItem("#dot")));
        resultActions.andExpect(jsonPath("$.length()").value(equalTo(1)));

        resultActions = findTag("#etc");
        resultActions.andExpect(jsonPath("$[*].tagName").value(hasItem("#etc")));
        resultActions.andExpect(jsonPath("$.length()").value(equalTo(1)));

        resultActions = findTag("d");
        resultActions.andExpect(jsonPath("$[*].tagName").value(hasItem("#dot")));
        resultActions.andExpect(jsonPath("$.length()").value(equalTo(1)));

        resultActions = findTag("e");
        resultActions.andExpect(jsonPath("$[*].tagName").value(hasItem("#etc")));
        resultActions.andExpect(jsonPath("$.length()").value(equalTo(1)));

        resultActions = findTag("t");
        resultActions.andExpect(jsonPath("$[*].tagName").value(allOf(hasItem("#dot"), hasItem("#etc"))));
        resultActions.andExpect(jsonPath("$.length()").value(equalTo(2)));

        resultActions = findTag("Pneumonoultramicroscopicsilicovolcanoconiosis");
        resultActions.andExpect(jsonPath("$.length()").value(equalTo(0)));
    }

    /**
     * 測試hashtag reference count (透過文件編輯)
     *
     * @throws Exception
     */
    @Test
    public void testSearchPatientDocumentTag3() throws Exception {
        Patient patient = TestUtil.createPatient2(user, em, patientRepository);
        Disposal disposal1 = TestUtil.createDisposal(user, patient, em, registrationRepository, appointmentRepository, disposalRepository);
        Disposal disposal2 = TestUtil.createDisposal(user, patient, em, registrationRepository, appointmentRepository, disposalRepository);
        Long patientId = disposal1.getRegistration().getAppointment().getPatient().getId();

        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", disposal1.getId() + ".txt", "text/plain", "hi".getBytes(StandardCharsets.UTF_8));
        PatientDocumentVM patientDocumentVM1 = uploadDocument(patientId, disposal1.getId(), mockMultipartFile);
        PatientDocumentVM patientDocumentVM2 = uploadDocument(patientId, disposal1.getId(), mockMultipartFile);

        Document document1 = new Document();
        document1.setHashtags(new String[]{"#dot"});
        updateDocument(patientDocumentVM1, disposal1.getId(), document1);

        ResultActions resultActions;

        resultActions = findTag("#dot");
        resultActions.andExpect(jsonPath("$.[?(@.tagName == '#dot')].referenceCount").value(hasItem(1)));

        Document document2 = new Document();
        document2.setHashtags(new String[]{"#dot", "#etc"});
        updateDocument(patientDocumentVM2, disposal2.getId(), document2);

        resultActions = findTag("#dot");
        resultActions.andExpect(jsonPath("$.[?(@.tagName == '#dot')].referenceCount").value(hasItem(2)));

        resultActions = findTag("#etc");
        resultActions.andExpect(jsonPath("$.[?(@.tagName == '#etc')].referenceCount").value(hasItem(1)));

        resultActions = findTag("t");
        resultActions.andExpect(jsonPath("$.[0].tagName").value(equalTo("#dot")));
        resultActions.andExpect(jsonPath("$.[0].referenceCount").value(equalTo(2)));
        resultActions.andExpect(jsonPath("$.[1].tagName").value(equalTo("#etc")));
        resultActions.andExpect(jsonPath("$.[1].referenceCount").value(equalTo(1)));

        resultActions = findTag("t", "referenceCount,asc");
        resultActions.andExpect(jsonPath("$.[0].tagName").value(equalTo("#etc")));
        resultActions.andExpect(jsonPath("$.[0].referenceCount").value(equalTo(1)));
        resultActions.andExpect(jsonPath("$.[1].tagName").value(equalTo("#dot")));
        resultActions.andExpect(jsonPath("$.[1].referenceCount").value(equalTo(2)));

        document2 = new Document();
        document2.setHashtags(new String[]{});
        updateDocument(patientDocumentVM2, disposal2.getId(), document2);

        resultActions = findTag("#dot");
        resultActions.andExpect(jsonPath("$.[?(@.tagName == '#dot')].referenceCount").value(hasItem(1)));

        resultActions = findTag("#etc");
        resultActions.andExpect(jsonPath("$.[?(@.tagName == '#etc')].referenceCount").value(hasItem(0)));
    }

    /**
     * 測試hashtag reference count (透過文件刪除)
     *
     * @throws Exception
     */
    @Test
    public void testSearchPatientDocumentTag4() throws Exception {
        Patient patient = TestUtil.createPatient2(user, em, patientRepository);
        Disposal disposal1 = TestUtil.createDisposal(user, patient, em, registrationRepository, appointmentRepository, disposalRepository);
        Long patientId = disposal1.getRegistration().getAppointment().getPatient().getId();

        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", disposal1.getId() + ".txt", "text/plain", "hi".getBytes(StandardCharsets.UTF_8));
        PatientDocumentVM patientDocumentVM1 = uploadDocument(patientId, disposal1.getId(), mockMultipartFile);

        Document document1 = new Document();
        document1.setHashtags(new String[]{"#dot"});
        updateDocument(patientDocumentVM1, disposal1.getId(), document1);

        ResultActions resultActions;

        resultActions = findTag("#dot");
        resultActions.andExpect(jsonPath("$.[?(@.tagName == '#dot')].referenceCount").value(hasItem(1)));

        deleteDocument(patientId, patientDocumentVM1.getId());

        resultActions = findTag("#dot");
        resultActions.andExpect(jsonPath("$.[?(@.tagName == '#dot')].referenceCount").value(hasItem(0)));
    }

    private PatientDocumentVM uploadDocument(Long patientId, Long disposalId, MockMultipartFile mockMultipartFile) throws Exception {
        return objectMapper.readValue(uploadDocument2(patientId, disposalId, mockMultipartFile).andReturn().getResponse().getContentAsString(), PatientDocumentVM.class);
    }

    private ResultActions uploadDocument2(Long patientId, Long disposalId, MockMultipartFile mockMultipartFile) throws Exception {
        MockHttpServletRequestBuilder requestBuilder = multipart("/api/patient-document/" + patientId)
            .file(mockMultipartFile)
            .param("disposalId", String.valueOf(disposalId));
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andExpect(request().asyncStarted()).andReturn();
        return mockMvc.perform(asyncDispatch(mvcResult));
    }

    private ResultActions findDocument(Long patientId, Long disposalId) throws Exception {
        MockHttpServletRequestBuilder requestBuilder = get("/api/patient-document/" + patientId).
            param("disposalId", String.valueOf(disposalId));
        return mockMvc.perform(requestBuilder);
    }

    private ResultActions findDocument(Long patientId, String sort) throws Exception {
        MockHttpServletRequestBuilder requestBuilder = get("/api/patient-document/" + patientId).
            param("sort", sort);
        return mockMvc.perform(requestBuilder);
    }

    private ResultActions findDocument(Long patientId) throws Exception {
        MockHttpServletRequestBuilder requestBuilder = get("/api/patient-document/" + patientId);
        return mockMvc.perform(requestBuilder);
    }

    private ResultActions updateDocument(PatientDocumentVM patientDocumentVM, Long disposalId, Document document) throws Exception {
        PatientDocumentDisposal patientDocumentDisposal = new PatientDocumentDisposal();
        patientDocumentDisposal.setId(disposalId);

        PatientDocument patientDocument = new PatientDocument();
        patientDocument.setId(patientDocumentVM.getId());
        patientDocument.setDisposal(patientDocumentDisposal);
        patientDocument.setDocument(document);

        MockHttpServletRequestBuilder requestBuilder = put("/api/patient-document/" + patientDocumentVM.getPatientId())
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsBytes(patientDocument));
        return mockMvc.perform(requestBuilder);
    }

    private ResultActions deleteDocument(Long patientId, Long patientDocumentId) throws Exception {
        MockHttpServletRequestBuilder requestBuilder = delete("/api/patient-document/" + patientId).param("patientDocumentId", String.valueOf(patientDocumentId));
        return mockMvc.perform(requestBuilder);
    }

    private ResultActions findTag(String search) throws Exception {
        MockHttpServletRequestBuilder requestBuilder = get("/api/hashtag")
            .param("search", search)
            .param("tagType", "PatientDocument")
            .contentType(APPLICATION_JSON);
        return mockMvc.perform(requestBuilder);
    }

    private ResultActions findTag(String search, String sort) throws Exception {
        MockHttpServletRequestBuilder requestBuilder = get("/api/hashtag")
            .param("search", search)
            .param("tagType", "PatientDocument")
            .param("sort", sort)
            .contentType(APPLICATION_JSON);
        return mockMvc.perform(requestBuilder);
    }
}
