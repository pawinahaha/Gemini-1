package edu.gemini;

import edu.gemini.model.AbstractObservingProgramConfigs;
import edu.gemini.model.AbstractTelePositionPair;

public class ObservingProgramConfigs extends AbstractObservingProgramConfigs {
    private String opticsPrimary;
    private double fStop;
    private double opticsSecondaryRMS;
    private double scienceFoldMirrorDegree;
    private FoldMirrorType foldMirrorType;
    private int moduleContent;
    private CalibrationUnit calibrationUnit;
    private LightType lightType;
    private AbstractTelePositionPair[] telePositionPairs;

    public ObservingProgramConfigs() {
        super();
    }

    public ObservingProgramConfigs(String opticsPrimary,
                                  double fStop,
                                  double opticsSecondaryRMS,
                                  double scienceFoldMirrorDegree,
                                  FoldMirrorType foldMirrorType,
                                  int moduleContent,
                                  CalibrationUnit calibrationUnit,
                                  LightType lightType,
                                  AbstractTelePositionPair[] telePositionPairs) {
        this.opticsPrimary = opticsPrimary;
        this.fStop = fStop;
        this.opticsSecondaryRMS = opticsSecondaryRMS;
        this.scienceFoldMirrorDegree = scienceFoldMirrorDegree;
        this.foldMirrorType = foldMirrorType;
        this.moduleContent = moduleContent;
        this.calibrationUnit = calibrationUnit;
        this.lightType = lightType;
        this.telePositionPairs = telePositionPairs;
    }

    public String getOpticsPrimary() {
        return opticsPrimary;
    }

    public double getFStop() {
        return fStop;
    }

    public double getOpticsSecondaryRMS() {
        return opticsSecondaryRMS;
    }

    public double getScienceFoldMirrorDegree() {
        return scienceFoldMirrorDegree;
    }

    public FoldMirrorType getFoldMirrorTypeValue() {
        return foldMirrorType;
    }

    public int getModuleContent() {
        return moduleContent;
    }

    public CalibrationUnit getCalibrationUnitValue() {
        return calibrationUnit;
    }

    public LightType getLightTypeValue() {
        return lightType;
    }

    public AbstractTelePositionPair[] getTelePositionPairs() {
        return telePositionPairs;
    }
}