package io.dentall.totoro.service;

import io.dentall.totoro.domain.*;
import io.dentall.totoro.domain.enumeration.TagName;
import io.dentall.totoro.repository.TagRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Supplier;

/**
 * Service class for managing patients.
 */
@Service
public class PatientService {

    private final Logger log = LoggerFactory.getLogger(PatientService.class);

    private final TagRepository tagRepository;

    public PatientService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public void setTagsByQuestionnaire(Set<Tag> tags, Questionnaire questionnaire) {
        if (questionnaire != null) {
            setTag(tags, TagName.Hypertension, () -> questionnaire.getHypertension() != null);
            setTag(tags, TagName.LiverDiseases, () -> questionnaire.getLiverDiseases() != null || questionnaire.getHepatitisType() != null);
            setTag(tags, TagName.Smoker, () -> questionnaire.getSmokeNumberADay() != null);
            setTag(tags, TagName.Pregnancy, () -> questionnaire.getProductionYear() != null && questionnaire.getProductionMonth() != null);
            setTag(tags, TagName.DifficultExtractionOrContinuousBleeding,
                () -> questionnaire.isDifficultExtractionOrContinuousBleeding() != null && questionnaire.isDifficultExtractionOrContinuousBleeding());
            setTag(tags, TagName.NauseaOrDizziness,
                () -> questionnaire.isNauseaOrDizziness() != null && questionnaire.isNauseaOrDizziness());
            setTag(tags, TagName.AdverseReactionsToAnestheticInjections,
                () -> questionnaire.isAdverseReactionsToAnestheticInjections() != null && questionnaire.isAdverseReactionsToAnestheticInjections());
        }
    }

    private void setTag(Set<Tag> tags, TagName tagName, Supplier<Boolean> condition) {
        Optional<Tag> tag = tagRepository.findById(tagName.getValue());
        if (condition.get()) {
            tag.ifPresent(tags::add);
        } else {
            tag.ifPresent(tags::remove);
        }
    }
}
