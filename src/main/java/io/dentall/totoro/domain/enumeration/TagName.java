package io.dentall.totoro.domain.enumeration;

/**
 * The TagName enumeration.
 */
public enum TagName {
    Aspirin(1L),
    Penicillin(2L),
    青黴素(3L),
    Pyrine(4L),
    NSAID(5L),
    磺胺(6L),
    消炎藥(7L),
    骨質疏鬆藥_雙磷酸鹽類藥物(8L),

    AIDS(9L),
    Hypertension(10L),
    低血壓(11L),
    氣喘(12L),
    心臟雜音(13L),
    糖尿病(14L),
    肺炎(15L),
    肺結核(16L),
    LiverDiseases(17L),
    消化性潰瘍(18L),
    癲癇(19L),
    暈眩(20L),
    中風(21L),
    惡性腫瘤_癌症(22L),
    風濕熱(23L),
    過敏(24L),

    Pregnancy(25L),
    Smoker(26L),
    DifficultExtractionOrContinuousBleeding(27L),
    NauseaOrDizziness(28L),
    AdverseReactionsToAnestheticInjections(29L);

    private final Long value;

    TagName(Long value) {
        this.value = value;
    }

    public Long getValue() {
        return this.value;
    }
}
