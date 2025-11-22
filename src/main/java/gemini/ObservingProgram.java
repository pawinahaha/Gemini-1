package edu.gemini;

import edu.gemini.model.AbstractObservingProgram;

public class ObservingProgram extends AbstractObservingProgram {
    private int id;
    private SciencePlan sciencePlan;
    private ObservingProgramConfigs configs;
    private ScienceObserver observer;

    public ObservingProgram() {
        super();
    }

    public ObservingProgram(String geminiLocation, String opticsPrimary, double fStop,
                           double opticsSecondaryRMS, double scienceFoldMirrorDegree,
                           int moduleContent) {
        super(geminiLocation, opticsPrimary, fStop, opticsSecondaryRMS, scienceFoldMirrorDegree, moduleContent);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SciencePlan getSciencePlan() {
        return sciencePlan;
    }

    public void setSciencePlan(SciencePlan sciencePlan) {
        this.sciencePlan = sciencePlan;
    }

    public ObservingProgramConfigs getConfigs() {
        return configs;
    }

    public void setConfigs(ObservingProgramConfigs configs) {
        this.configs = configs;
    }

    public ScienceObserver getObserver() {
        return observer;
    }

    public void setObserver(ScienceObserver observer) {
        this.observer = observer;
    }

    @Override
    public String toString() {
        return "ObservingProgram{" +
                "id=" + id +
                ", sciencePlan=" + (sciencePlan != null ? sciencePlan.getPlanNo() : "?") +
                ", observer=" + (observer != null ? observer.getId() : "?") +
                '}';
    }
}