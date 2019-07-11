package io.dentall.totoro.domain.enumeration;

/**
 * The TagName enumeration.
 */
public enum TagName {
    Aspirin(1L),
    Penicillin(2L),
    Pyrine(4L),
    NSAID(5L),
    Sulfonamides(6L),
    AntiInflammatory(7L),
    Bisphosphonate(8L),

    AIDS(9L),
    Hypertension(10L),
    Hypotension(11L),
    Asthma(12L),
    CardiacMurmur(13L),
    Diabetes(14L),
    Pneumonia(15L),
    Tuberculosis(16L),
    LiverDiseases(17L),
    PepticUlcer(18L),
    Epilepsy(19L),
    Vertigo(20L),
    Stroke(21L),
    Cancer(22L),
    RheumaticFever(23L),
    Allergy(24L),

    Pregnancy(25L),
    Smoker(26L),
    DifficultExtractionOrContinuousBleeding(27L),
    NauseaOrDizziness(28L),
    AdverseReactionsToAnestheticInjections(29L),
    BeeninHospitalOrUndergoneOperation(30L),
    TakenLongTermMedicineinAYear(31L),
    UndergoneRadiationTherapyOrChemotherapy(32L),
    HepatitisB(33L);

    private final Long value;

    TagName(Long value) {
        this.value = value;
    }

    public Long getValue() {
        return this.value;
    }
}
