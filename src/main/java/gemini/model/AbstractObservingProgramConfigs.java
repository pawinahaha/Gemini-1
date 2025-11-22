package edu.gemini.model;

public abstract class AbstractObservingProgramConfigs {
    // Reflective converging beam and Cassegrain focus
    public enum FoldMirrorType {
        REFLECTIVE_CONVERGING_BEAM, CASSEGRAIN_FOCUS
    }

    public enum CalibrationUnit {
        Argon, Xenon, ThAr, CuAr
    }

    public enum LightType {
        MaunaKeaSkyEmission, CerroPachonSkyEmission
    }

    public static FoldMirrorType[] getFoldMirrorType() {
        return FoldMirrorType.values();
    }

    public static CalibrationUnit[] getCalibrationUnit() {
        return CalibrationUnit.values();
    }

    public static LightType[] getLightType() {
        return LightType.values();
    }
}