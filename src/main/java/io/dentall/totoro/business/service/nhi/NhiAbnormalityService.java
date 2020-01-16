package io.dentall.totoro.business.service.nhi;

import io.dentall.totoro.business.vm.nhi.NhiAbnormality;
import io.dentall.totoro.business.vm.nhi.NhiAbnormalityDoctor;
import io.dentall.totoro.business.vm.nhi.NhiAbnormalityPatient;
import io.dentall.totoro.domain.*;
import io.dentall.totoro.repository.NhiExtendDisposalRepository;
import io.dentall.totoro.repository.NhiProcedureRepository;
import io.dentall.totoro.repository.PatientRepository;
import io.dentall.totoro.repository.UserRepository;
import io.dentall.totoro.service.util.ProblemUtil;
import io.dentall.totoro.service.util.StreamUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static io.dentall.totoro.business.service.nhi.util.NhiStatisticUtil.*;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingInt;

@Service
public class NhiAbnormalityService {

    private final NhiExtendDisposalRepository nhiExtendDisposalRepository;

    private final PatientRepository patientRepository;

    private final UserRepository userRepository;

    private final NhiProcedureRepository nhiProcedureRepository;

    private final List<String> composeToothPosition = Arrays.asList("99", "FM", "UB", "LB", "UR", "UL", "LR", "LL", "UA", "LA");

    private final Predicate<NhiExtendTreatmentDrug> checkNhiExtendTreatmentDrug = nhiExtendTreatmentDrug ->
        // 01: 自行調劑, 02: 交付調劑
        nhiExtendTreatmentDrug.getA78().equals("01") || nhiExtendTreatmentDrug.getA78().equals("02");

    private final Function<NhiExtendTreatmentDrug, Double> nhiExtendTreatmentDrugPoint = nhiExtendTreatmentDrug ->
        nhiExtendTreatmentDrug.getTreatmentDrug().getDrug().getPrice() * Double.parseDouble(nhiExtendTreatmentDrug.getA77());

    private final Function<NhiExtendDisposal, LocalDate> nhiExtendDisposalDate = nhiExtendDisposal ->
        nhiExtendDisposal.getReplenishmentDate() == null ? nhiExtendDisposal.getDate() : nhiExtendDisposal.getReplenishmentDate();

    private final BiPredicate<String, List<String>> notInPosition = (a74, composToothPosition) ->
        !composToothPosition.contains(a74);

    private Map<String, String> nhiProcedureSpecificCode;

    public NhiAbnormalityService(
        NhiExtendDisposalRepository nhiExtendDisposalRepository,
        PatientRepository patientRepository,
        UserRepository userRepository,
        NhiProcedureRepository nhiProcedureRepository
    ) {
        this.nhiExtendDisposalRepository = nhiExtendDisposalRepository;
        this.patientRepository = patientRepository;
        this.userRepository = userRepository;
        this.nhiProcedureRepository = nhiProcedureRepository;
    }

    @Transactional(readOnly = true)
    public NhiAbnormality getNhiAbnormality(int yyyymm) {
        YearMonth ym = YearMonth.of(yyyymm / 100, yyyymm % 100);
        List<NhiExtendDisposal> monthlyNhiExtendDisposals = nhiExtendDisposalRepository
            .findByDateBetween(
                ym.atDay(1),
                ym.atEndOfMonth()
            );

        NhiAbnormality nhiAbnormality = new NhiAbnormality();

        nhiAbnormality.setFrequentDoctors(getDoctorsByFrequency(monthlyNhiExtendDisposals, nhiAbnormality.getFrequentLimit()));
        nhiAbnormality.setCode92013cAvgPointDoctors(getDoctorsByCode92013cAvgPoint(monthlyNhiExtendDisposals));
        nhiAbnormality.setRatioOf90004cTo90015cDoctors(getDoctorsByRatioOf90004cTo90015c(monthlyNhiExtendDisposals));
        get048(monthlyNhiExtendDisposals, nhiAbnormality, ym);
        nhiAbnormality.setCode92003cDoctors(getDoctorsByCode(monthlyNhiExtendDisposals, "92003C", x -> true));
        nhiAbnormality.setCode92071cDoctors(getDoctorsByCode(monthlyNhiExtendDisposals, "92071C", x -> true));
        nhiAbnormality.setCode91013cDoctors(getDoctorsByCode(monthlyNhiExtendDisposals, "91013C", checkCategory14.or(checkCategory16).negate()));

        return nhiAbnormality;
    }

    @Transactional(readOnly = true)
    public List<NhiAbnormalityDoctor> getDoctorsByFrequency(List<NhiExtendDisposal> monthlyNhiExtendDisposals, int limit) {
        List<NhiExtendDisposal> nhiExtendDisposals = filterFrequency(monthlyNhiExtendDisposals);

        Map<Long, List<NhiExtendDisposal>> map = nhiExtendDisposals
            .stream()
            .collect(groupingBy(NhiExtendDisposal::getPatientId));
        List<NhiAbnormalityDoctor> doctors = new ArrayList<>();
        map.forEach((id, nhiExtDisposals) -> {
            int count = nhiExtDisposals.size();
            if (count > limit) {
                Patient patient = getPatient(id);
                nhiExtDisposals.forEach(nhiExtendDisposal -> {
                    String login = nhiExtendDisposal.getDisposal().getCreatedBy();
                    NhiAbnormalityDoctor nhiAbnormalityDoctor = getNhiAbnormalityDoctor(doctors, login);
                    NhiAbnormalityPatient nhiAbnormalityPatient = new NhiAbnormalityPatient(patient)
                        .date(nhiExtendDisposalDate.apply(nhiExtendDisposal))
                        .count(count);
                    nhiAbnormalityDoctor.getPatients().add(nhiAbnormalityPatient);
                });
            }
        });

        return doctors;
    }

    @Transactional(readOnly = true)
    public List<NhiAbnormalityDoctor> getDoctorsByCode92013cAvgPoint(List<NhiExtendDisposal> monthlyNhiExtendDisposals) {
        List<NhiExtendDisposal> nhiExtendDisposals = filterCode92013cAvgPoint(monthlyNhiExtendDisposals);

        Map<String, List<NhiExtendTreatmentDrug>> map = getDoctorNhiExtendTreatmentDrugsByCode(nhiExtendDisposals);
        List<NhiAbnormalityDoctor> doctors = new ArrayList<>();
        map.forEach((login, nhiExtendTreatmentDrugs) -> {
            double avg = nhiExtendTreatmentDrugs
                .stream()
                .mapToDouble(nhiExtendTreatmentDrugPoint::apply)
                .sum()
                /
                nhiExtendTreatmentDrugs
                    .stream()
                    .map(NhiAbstractMedicalArea::getNhiExtendDisposal)
                    .distinct()
                    .flatMap(nhiExtendTreatmentDisposal -> StreamUtil.asStream(nhiExtendTreatmentDisposal.getNhiExtendTreatmentProcedures()))
                    .filter(nhiExtendTreatmentProcedure -> nhiExtendTreatmentProcedure.getA73().equals("92013C"))
                    .count();

            User user = getDoctor(login);
            NhiAbnormalityDoctor doctor = new NhiAbnormalityDoctor().id(user.getId()).login(login).point(avg);
            doctors.add(doctor);

            Map<AbstractMap.SimpleEntry<Long, LocalDate>, List<NhiExtendDisposal>> patientDateNhiExtendDisposals =
                nhiExtendTreatmentDrugs
                    .stream()
                    .map(NhiExtendTreatmentDrug::getNhiExtendDisposal)
                    .distinct()
                    .collect(groupingBy(
                        nhiExtendDisposal ->
                            new AbstractMap.SimpleEntry<>(nhiExtendDisposal.getPatientId(), nhiExtendDisposalDate.apply(nhiExtendDisposal))
                        )
                    );

            if (patientDateNhiExtendDisposals.size() > 0) {
                List<NhiAbnormalityPatient> patients = new ArrayList<>();
                patientDateNhiExtendDisposals.forEach((patientDate, nhiExtDisposals) -> {
                    Patient patient = getPatient(patientDate.getKey());
                    Double sum = nhiExtDisposals
                        .stream()
                        .flatMap(nhiExtendDisposal -> StreamUtil.asStream(nhiExtendDisposal.getNhiExtendTreatmentDrugs()))
                        .filter(checkNhiExtendTreatmentDrug)
                        .mapToDouble(nhiExtendTreatmentDrugPoint::apply)
                        .sum();

                    patients.add(new NhiAbnormalityPatient(patient).date(patientDate.getValue()).code92013cPoint(sum));
                });

                doctor.setPatients(patients);
            }
        });

        return doctors;
    }

    @Transactional(readOnly = true)
    public List<NhiAbnormalityDoctor> getDoctorsByRatioOf90004cTo90015c(List<NhiExtendDisposal> monthlyNhiExtendDisposals) {
        List<NhiExtendDisposal> nhiExtendDisposals = filterRatioOf90004cTo90015c(monthlyNhiExtendDisposals);

        Map<Long, Integer> patientTeethCount = getPatientTeethCountByCode(nhiExtendDisposals, "90015C");
        Map<String, Map<AbstractMap.SimpleEntry<Long, LocalDate>, List<NhiExtendTreatmentProcedure>>> map =
            getDoctorPatientDateNhiExtendTreatmentProceduresByCode(nhiExtendDisposals, "90015C");
        List<NhiAbnormalityDoctor> doctors = new ArrayList<>();
        map.forEach((login, nhiExtTxPsByPatientDate) ->
            nhiExtTxPsByPatientDate.forEach((entry, nhiExtendTreatmentProcedures) -> {
                List<NhiExtendDisposal> nhiExtDisposals = nhiExtendDisposalRepository
                    .findByDateGreaterThanEqualAndPatientId(entry.getValue().minusDays(30), entry.getKey());

                Set<String> positions = nhiExtendTreatmentProcedures
                    .stream()
                    .flatMap(nhiExtendTreatmentProcedure -> Arrays.stream(nhiExtendTreatmentProcedure.getA74().split("(?<=\\G.{2})")))
                    .collect(Collectors.toSet());

                positions.forEach(position -> {
                    long count = getCountByCodeAndPosition(nhiExtDisposals, "90004C", position);
                    Integer teeth = patientTeethCount.get(entry.getKey());
                    if (teeth != null && teeth > 0) {
                        Patient patient = getPatient(entry.getKey());
                        NhiAbnormalityDoctor nhiAbnormalityDoctor = getNhiAbnormalityDoctor(doctors, login);
                        NhiAbnormalityPatient nhiAbnormalityPatient = nhiAbnormalityDoctor
                            .getPatients()
                            .stream()
                            .filter(p -> p.getId().equals(patient.getId()) && p.getDate() == entry.getValue())
                            .findFirst()
                            .orElseGet(() -> {
                                NhiAbnormalityPatient p = new NhiAbnormalityPatient(patient).date(entry.getValue()).ratioOf90004cTo90015c(new HashMap<>());
                                nhiAbnormalityDoctor.getPatients().add(p);

                                return p;
                            });

                        nhiAbnormalityPatient.getRatioOf90004cTo90015c().put(position, count / Double.valueOf(teeth));
                    }
                });
            })
        );

        return doctors;
    }

    @Transactional(readOnly = true)
    public void get048(List<NhiExtendDisposal> nhiExtendDisposals, NhiAbnormality nhiAbnormality, YearMonth ym) {
        List<NhiAbnormalityDoctor> doctors = get048Doctors(filter048(nhiExtendDisposals), ym);
        nhiAbnormality.setStatisticNo048Doctors(doctors);
        double total = 0;

        for (NhiAbnormalityDoctor doc : doctors) {
            total += doc.getPoint();
        }
        nhiAbnormality.setStatisticNo048(total);
    }

    @Transactional(readOnly = true)
    public List<NhiAbnormalityDoctor> getDoctorsByCode(List<NhiExtendDisposal> monthlyNhiExtendDisposals, String code, Predicate<NhiExtendDisposal> filter) {
        List<NhiExtendDisposal> nhiExtendDisposals = monthlyNhiExtendDisposals.stream().filter(filter).collect(Collectors.toList());

        Map<String, List<NhiExtendTreatmentProcedure>> map = getDoctorNhiExtendTreatmentProceduresByCode(nhiExtendDisposals, code);
        List<NhiAbnormalityDoctor> doctors = new ArrayList<>();
        map.forEach((login, nhiExtendTreatmentProcedures) -> {
            User user = getDoctor(login);
            NhiAbnormalityDoctor doctor = new NhiAbnormalityDoctor().id(user.getId()).login(login).count(nhiExtendTreatmentProcedures.size());
            doctors.add(doctor);

            List<NhiAbnormalityPatient> patients = nhiExtendTreatmentProcedures
                .stream()
                .map(NhiExtendTreatmentProcedure::getNhiExtendDisposal)
                .distinct()
                .map(nhiExtendDisposal -> {
                    Patient patient = getPatient(nhiExtendDisposal.getPatientId());

                    return new NhiAbnormalityPatient(patient).date(nhiExtendDisposalDate.apply(nhiExtendDisposal));
                })
                .collect(Collectors.toList());

            doctor.setPatients(patients);
        });

        return doctors;
    }

    private List<NhiExtendDisposal> filterFrequency(List<NhiExtendDisposal> nhiExtendDisposals) {
        Set<String> frequentExcludeSpecificCode = new HashSet<>(Arrays.asList("F5", "F7", "F8", "F9"));

        return nhiExtendDisposals
            .stream()
            .filter(checkExaminationPointGreaterThan0)
            .filter(nhiExtendDisposal ->
                StreamUtil.asStream(nhiExtendDisposal.getNhiExtendTreatmentProcedures())
                    .noneMatch(nhiExtendTreatmentProcedure -> {
                        String specificCode = getNhiProcedureSpecificCode().get(nhiExtendTreatmentProcedure.getA73());

                        return specificCode != null && frequentExcludeSpecificCode.contains(specificCode);
                    })
            )
            .collect(Collectors.toList());
    }

    private List<NhiExtendDisposal> filterCode92013cAvgPoint(List<NhiExtendDisposal> nhiExtendDisposals) {
        Predicate<NhiExtendDisposal> code92013cAvgPointExcludeCategories =
            checkCategory14.or(checkCategory16).or(checkCategoryA3).or(checkCategoryB6).negate();

        Predicate<NhiExtendDisposal> code92013cAvgPointExcludeCodes = nhiExtendDisposal -> {
            List<String> codes = StreamUtil.asStream(nhiExtendDisposal.getNhiExtendTreatmentProcedures())
                .map(NhiExtendTreatmentProcedure::getA73)
                .distinct()
                .collect(Collectors.toList());

            if (codes.contains("92013C")) {
                return !(codes.contains("92014C") || codes.contains("92015C") || codes.contains("92016C") ||
                    codes.contains("92059C") || codes.contains("92063C") || codes.contains("92064C"));
            }

            return false;
        };

        return nhiExtendDisposals
            .stream()
            .filter(code92013cAvgPointExcludeCategories.and(code92013cAvgPointExcludeCodes))
            .collect(Collectors.toList());
    }

    private List<NhiExtendDisposal> filterRatioOf90004cTo90015c(List<NhiExtendDisposal> nhiExtendDisposals) {
        Map<String, Integer> doctorTeethCount = getDoctorTeethCountByCode(nhiExtendDisposals, "90015C");
        List<String> ratioOf90004cTo90015cIncludeDoctors = doctorTeethCount
            .entrySet()
            .stream()
            .filter(entry -> entry.getValue() > 2)
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());

        return nhiExtendDisposals
            .stream()
            .filter(checkCategory14.or(checkCategory16).negate())
            .filter(nhiExtendDisposal -> ratioOf90004cTo90015cIncludeDoctors.contains(nhiExtendDisposal.getDisposal().getCreatedBy()))
            .collect(Collectors.toList());
    }

    private Map<String, List<NhiExtendTreatmentDrug>> getDoctorNhiExtendTreatmentDrugsByCode(List<NhiExtendDisposal> nhiExtendDisposals) {
        return nhiExtendDisposals
            .stream()
            .flatMap(nhiExtendDisposal -> StreamUtil.asStream(nhiExtendDisposal.getNhiExtendTreatmentDrugs()))
            .filter(checkNhiExtendTreatmentDrug)
            .collect(groupingBy(nhiExtendTreatmentDrug -> nhiExtendTreatmentDrug.getNhiExtendDisposal().getDisposal().getCreatedBy()));
    }

    private Map<String, Map<AbstractMap.SimpleEntry<Long, LocalDate>, List<NhiExtendTreatmentProcedure>>> getDoctorPatientDateNhiExtendTreatmentProceduresByCode(
        List<NhiExtendDisposal> nhiExtendDisposals, String code
    ) {
        return nhiExtendDisposals
            .stream()
            .flatMap(nhiExtendDisposal -> StreamUtil.asStream(nhiExtendDisposal.getNhiExtendTreatmentProcedures()))
            .filter(nhiExtendTreatmentProcedure -> nhiExtendTreatmentProcedure.getA73().equals(code))
            .collect(
                groupingBy(
                    nhiExtendTreatmentProcedure -> nhiExtendTreatmentProcedure.getTreatmentProcedure().getDisposal().getCreatedBy(),
                    groupingBy(nhiExtendTreatmentProcedure ->
                        new AbstractMap.SimpleEntry<>(
                            nhiExtendTreatmentProcedure.getTreatmentProcedure().getTreatmentTask().getTreatmentPlan().getTreatment().getPatient().getId(),
                            nhiExtendDisposalDate.apply(nhiExtendTreatmentProcedure.getTreatmentProcedure().getDisposal().getNhiExtendDisposals().iterator().next())
                        )
                    )
                )
            );
    }

    private long getCountByCodeAndPosition(List<NhiExtendDisposal> nhiExtendDisposals, String code, String position) {
        return nhiExtendDisposals
            .stream()
            .flatMap(nhiExtendDisposal -> StreamUtil.asStream(nhiExtendDisposal.getNhiExtendTreatmentProcedures()))
            .filter(nhiExtendTreatmentProcedure ->
                nhiExtendTreatmentProcedure.getA73().equals(code) &&
                    Arrays.asList(nhiExtendTreatmentProcedure.getA74().split("(?<=\\G.{2})")).contains(position)
            )
            .count();
    }

    private Map<String, List<NhiExtendTreatmentProcedure>> getDoctorNhiExtendTreatmentProceduresByCode(List<NhiExtendDisposal> nhiExtendDisposals, String code) {
        return nhiExtendDisposals
            .stream()
            .flatMap(nhiExtendDisposal -> StreamUtil.asStream(nhiExtendDisposal.getNhiExtendTreatmentProcedures()))
            .filter(nhiExtendTreatmentProcedure -> nhiExtendTreatmentProcedure.getA73().equals(code))
            .collect(groupingBy(nhiExtendTreatmentProcedure -> nhiExtendTreatmentProcedure.getTreatmentProcedure().getDisposal().getCreatedBy()));
    }

    private List<NhiAbnormalityDoctor> get048Doctors(List<NhiExtendDisposal> currentMonthNhiDis, YearMonth ym) {
        final String code = "89013C";
        List<Long> patientList = new ArrayList<>();
        Map<Long, Long> patDocMap = new HashMap<>();
        Set<PatTooth> patToothCountSet = new HashSet<>();
        Integer currentMonthTotalCounting = 0;

        // Process current monty nhi disposal
        currentMonthNhiDis.forEach(nhiDis -> {
            // Counting pat tooth
            nhiDis.getNhiExtendTreatmentProcedures().stream()
                .filter(nhiTx -> nhiTx.getA73().equals(code))
                .forEach(nhiTx -> {

                    if (!patientList.contains(nhiDis.getPatientId())) {
                        patientList.add(nhiDis.getPatientId());
                    }

                    if (!patDocMap.containsKey(nhiDis.getPatientId())) {
                        User user = getDoctor(nhiDis.getDisposal().getCreatedBy());
                        patDocMap.put(nhiDis.getPatientId(), user.getId());
                    }

                    parseA74ToToothList(nhiTx.getA74()).forEach(toothPos -> {
                        PatTooth patTooth = new PatTooth(nhiDis.getPatientId(), toothPos);
                        if (!patToothCountSet.contains(patTooth)) {
                            patToothCountSet.add(patTooth);
                        } else {
                            patToothCountSet.stream()
                                .filter(e -> e.equals(patTooth))
                                .forEach(PatTooth::increment);
                        }
                    });
                });
        });

        // Process necessary past 2 year data
        patientList.forEach(pat ->
            nhiExtendDisposalRepository.findByDateBetweenAndPatientId(
                ym.atDay(1).minusDays(730),
                ym.atDay(1).minusDays(1),
                pat).stream()
                .flatMap(nhiExtendDisposal -> StreamUtil.asStream(nhiExtendDisposal.getNhiExtendTreatmentProcedures()))
                .filter(nhiExtendTreatmentProcedure -> nhiExtendTreatmentProcedure.getA73().equals(code))
                .forEach(nhiTx ->
                    parseA74ToToothList(nhiTx.getA74()).forEach(toothPos -> {
                        PatTooth patTooth = new PatTooth(pat, toothPos);
                        patToothCountSet.stream()
                            .filter(e -> e.equals(patTooth))
                            .forEach(PatTooth::increment);
                    })
                )
        );

        // Count current month total
        for (PatTooth patTooth : patToothCountSet) {
            currentMonthTotalCounting += patTooth.getCount();
        }
        final Integer currentMonthTotal = currentMonthTotalCounting;
        if (currentMonthTotal <= 3) {
            return new ArrayList<>();
        }

        // Process patToothCount for abnormal only result
        Map<Long, NhiAbnormalityDoctor> abDocIdMap = new HashMap<>();
        Map<Long, Integer> patCountMap = patToothCountSet.stream()
            .filter(e -> e.getCount() > 1)
            .map(PatTooth::decrement)
            .collect(groupingBy(PatTooth::getPatientId, summingInt(PatTooth::getCount)));

        patCountMap.forEach((pat, count) -> {
            NhiAbnormalityPatient abPat = new NhiAbnormalityPatient(getPatient(pat)).count(count);
            Long docId = patDocMap.get(abPat.getId());
            if (!abDocIdMap.containsKey(docId)) {
                NhiAbnormalityDoctor doc =
                    new NhiAbnormalityDoctor()
                        .point(Double.valueOf(count)/Double.valueOf(currentMonthTotal))
                        .id(docId)
                        .count(count);
                abDocIdMap.put(docId, doc);
                List<NhiAbnormalityPatient> abPatList = new ArrayList<>();
                abPatList.add(abPat);
                doc.setPatients(abPatList);
            } else {
                NhiAbnormalityDoctor abDoc = abDocIdMap.get(docId);
                abDoc.increment(count)
                    .point(Double.valueOf(abDoc.getCount())/Double.valueOf(currentMonthTotal))
                    .getPatients().add(abPat);
            }
        });

        return new ArrayList<>(abDocIdMap.values());
    }

    private List<String> parseA74ToToothList(String a74) {
        List<String> toothList = new ArrayList<>();
        int length = a74.length();

        if (
            length > 0 &&
                length <7 &&
                length % 2 == 0 &&
                notInPosition.test(a74, composeToothPosition)
        ) {
            int toothPositions = length / 2;
            for(int idx = 0; idx < toothPositions; idx++) {
                toothList.add(a74.substring(idx * 2, idx * 2 + 2));
            }
        }

        return toothList;
    }

    private Patient getPatient(Long id) {
        return patientRepository.findById(id).orElseThrow(() -> ProblemUtil.notFoundException("patient"));
    }

    private User getDoctor(String login) {
        return userRepository.findOneByLogin(login).orElseThrow(() -> ProblemUtil.notFoundException("user"));
    }

    private NhiAbnormalityDoctor getNhiAbnormalityDoctor(List<NhiAbnormalityDoctor> doctors, String login) {
        return doctors
            .stream()
            .filter(doctor -> doctor.getLogin().equals(login))
            .findFirst()
            .orElseGet(() -> {
                User user = getDoctor(login);
                NhiAbnormalityDoctor doctor = new NhiAbnormalityDoctor().id(user.getId()).login(login);
                doctor.setPatients(new ArrayList<>());
                doctors.add(doctor);

                return doctor;
            });
    }

    private Map<String, String> getNhiProcedureSpecificCode() {
        if (nhiProcedureSpecificCode == null) {
            nhiProcedureSpecificCode = nhiProcedureRepository
                .findAll()
                .stream()
                .filter(nhiProcedure -> nhiProcedure.getSpecificCode() != null)
                .collect(Collectors.toMap(NhiProcedure::getCode, NhiProcedure::getSpecificCode));
        }

        return nhiProcedureSpecificCode;
    }

    private class PatTooth {
        private Long patientId;

        private String toothPos;

        private Integer count;

        public PatTooth(Long patientId, String toothPos) {
            this.patientId = patientId;
            this.toothPos = toothPos;
            this.count = 1;
        }

        public PatTooth increment() {
            ++this.count;
            return this;
        }

        public PatTooth decrement() {
            --this.count;
            return this;
        }

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }

        public Long getPatientId() {
            return patientId;
        }

        public void setPatientId(Long patientId) {
            this.patientId = patientId;
        }

        public String getToothPos() {
            return toothPos;
        }

        public void setToothPos(String toothPos) {
            this.toothPos = toothPos;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            PatTooth that = (PatTooth) o;
            return Objects.equals(patientId, that.patientId) &&
                Objects.equals(toothPos, that.toothPos);
        }

        @Override
        public int hashCode() {
            return Objects.hash(patientId, toothPos);
        }
    }

    // 048 排除邏輯
    private List<NhiExtendDisposal> filter048(List<NhiExtendDisposal> nhiExtendDisposals) {
        Predicate<NhiExtendDisposal> exclude =
            checkCategory14.or(checkCategory16).negate();


        return nhiExtendDisposals
            .stream()
            .filter(exclude)
            .collect(Collectors.toList());
    }
}
