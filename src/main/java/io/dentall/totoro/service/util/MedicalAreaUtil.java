package io.dentall.totoro.service.util;

import io.dentall.totoro.domain.NhiMedicalArea;

public final class MedicalAreaUtil {

    /**
     * Update the medicalArea.
     *
     * @param medicalArea the update entity
     * @param updateMedicalArea the update entity
     */
    public static void update(NhiMedicalArea medicalArea, NhiMedicalArea updateMedicalArea) {
        if (updateMedicalArea.getA71() != null) {
            medicalArea.setA71((updateMedicalArea.getA71()));
        }

        if (updateMedicalArea.getA72() != null) {
            medicalArea.setA72((updateMedicalArea.getA72()));
        }

        if (updateMedicalArea.getA73() != null) {
            medicalArea.setA73((updateMedicalArea.getA73()));
        }

        if (updateMedicalArea.getA74() != null) {
            medicalArea.setA74((updateMedicalArea.getA74()));
        }

        if (updateMedicalArea.getA75() != null) {
            medicalArea.setA75((updateMedicalArea.getA75()));
        }

        if (updateMedicalArea.getA76() != null) {
            medicalArea.setA76((updateMedicalArea.getA76()));
        }

        if (updateMedicalArea.getA77() != null) {
            medicalArea.setA77((updateMedicalArea.getA77()));
        }

        if (updateMedicalArea.getA78() != null) {
            medicalArea.setA78((updateMedicalArea.getA78()));
        }

        if (updateMedicalArea.getA79() != null) {
            medicalArea.setA79((updateMedicalArea.getA79()));
        }
    }
}
