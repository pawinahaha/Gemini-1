package edu.gemini.model;

public abstract class AbstractObservingProgram {
    private String geminiLocation;
    private String opticsPrimary;
    private double fStop;
    private double opticsSecondaryRMS;
    private double scienceFoldMirrorDegree;
    private int moduleContent;

    public AbstractObservingProgram() {
    }

    public AbstractObservingProgram(String geminiLocation, String opticsPrimary, double fStop,
                                   double opticsSecondaryRMS, double scienceFoldMirrorDegree,
                                   int moduleContent) {
        this.geminiLocation = geminiLocation;
        this.opticsPrimary = opticsPrimary;
        this.fStop = fStop;
        this.opticsSecondaryRMS = opticsSecondaryRMS;
        this.scienceFoldMirrorDegree = scienceFoldMirrorDegree;
        this.moduleContent = moduleContent;
    }

    public String getGeminiLocation() {
        return geminiLocation;
    }

    public void setGeminiLocation(String geminiLocation) {
        this.geminiLocation = geminiLocation;
    }

    public String getOpticsPrimary() {
        return opticsPrimary;
    }

    public void setOpticsPrimary(String opticsPrimary) {
        this.opticsPrimary = opticsPrimary;
    }

    public double getFStop() {
        return fStop;
    }

    public void setFStop(double fStop) {
        this.fStop = fStop;
    }

    public double getOpticsSecondaryRMS() {
        return opticsSecondaryRMS;
    }

    public void setOpticsSecondaryRMS(double opticsSecondaryRMS) {
        this.opticsSecondaryRMS = opticsSecondaryRMS;
    }

    public double getScienceFoldMirrorDegree() {
        return scienceFoldMirrorDegree;
    }

    public void setScienceFoldMirrorDegree(double scienceFoldMirrorDegree) {
        this.scienceFoldMirrorDegree = scienceFoldMirrorDegree;
    }

    public int getModuleContent() {
        return moduleContent;
    }

    public void setModuleContent(int moduleContent) {
        this.moduleContent = moduleContent;
    }
}