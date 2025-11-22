package edu.gemini;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import edu.gemini.model.AbstractObservingProgram;
import edu.gemini.model.AbstractObservingProgramConfigs;
import edu.gemini.model.AbstractSciencePlan;
import edu.gemini.model.AbstractTelePositionPair;
import edu.gemini.model.StarSystem;

public class OCS implements GeminiAPI<SciencePlan, ObservingProgram, ObservingProgramConfigs,
        AstronomicalData, Astronomer, ScienceObserver> {

    private final ArrayList<SciencePlan> sciencePlans = new ArrayList<>();
    private final ArrayList<SciencePlan> draftSciencePlans = new ArrayList<>();
    private int nextPlanNo = 1;
    
    private final ArrayList<ObservingProgram> observingPrograms = new ArrayList<>();
    private int nextObservingProgramId = 1;
    
    private final ArrayList<Date> unavailableDates = new ArrayList<>();
    private final ArrayList<String> configurations = new ArrayList<>();
    
    private boolean simulateNetworkError = false;

    // Inner class for validation constants (Facade pattern)
    private static class Validation {
        // Optics validation
        public static final double GNZ_FSTOP_MIN = 1.8;
        public static final double GNZ_FSTOP_MAX = 8.1;
        public static final double GSZ_FSTOP_MIN = 2.9;
        public static final double GSZ_FSTOP_MAX = 18.0;
        
        // Secondary optics RMS
        public static final double HAWAII_RMS_MIN = 5.0;
        public static final double HAWAII_RMS_MAX = 17.0;
        public static final double CHILE_RMS_MIN = 5.0;
        public static final double CHILE_RMS_MAX = 13.0;
        
        // Fold mirror
        public static final double FOLD_MIRROR_DEGREE_MIN = 30.0;
        public static final double FOLD_MIRROR_DEGREE_MAX = 45.0;
        
        // Module content
        public static final int MODULE_CONTENT_MIN = 1;
        public static final int MODULE_CONTENT_MAX = 4;
        
        // Data processing ranges
        public static final double CONTRAST_MIN = 0.0;
        public static final double CONTRAST_MAX = 2.0;
        public static final double BRIGHTNESS_MIN = -1.0;
        public static final double BRIGHTNESS_MAX = 1.0;
        public static final double SATURATION_MIN = 0.0;
        public static final double SATURATION_MAX = 2.0;
        public static final double EXPOSURE_MIN = -3.0;
        public static final double EXPOSURE_MAX = 3.0;
        public static final double HIGHLIGHTS_MIN = -1.0;
        public static final double HIGHLIGHTS_MAX = 1.0;
        public static final double SHADOWS_MIN = -1.0;
        public static final double SHADOWS_MAX = 1.0;
        public static final double WHITES_MIN = -1.0;
        public static final double WHITES_MAX = 1.0;
        public static final double BLACKS_MIN = -1.0;
        public static final double BLACKS_MAX = 1.0;
        public static final double LUMINANCE_MIN = -1.0;
        public static final double LUMINANCE_MAX = 1.0;
        public static final double HUE_MIN = -180.0;
        public static final double HUE_MAX = 180.0;
        
        // Star catalogue
        public static final Set<String> VALID_TARGETS = Arrays.stream(StarSystem.CONSTELLATIONS.values())
                .map(Enum::name)
                .collect(Collectors.toSet());
        
        // Validation methods
        public static boolean isValidTarget(String target) {
            if (target == null || target.trim().isEmpty()) {
                return false;
            }
            return VALID_TARGETS.contains(target.trim());
        }
        
        public static boolean isInRange(Double value, double min, double max) {
            if (value == null) return false;
            return value >= min && value <= max;
        }
        
        public static String getRangeString(double min, double max) {
            return String.format("%.1f to %.1f", min, max);
        }
    }

    public OCS() {
    }

    @Override
    public ArrayList<SciencePlan> getAllSciencePlans() {
        return sciencePlans;
    }
    
    public ArrayList<SciencePlan> getAllDraftSciencePlans() {
        return draftSciencePlans;
    }

    @Override
    public SciencePlan getSciencePlanByNo(int planNo) {
        for (SciencePlan sp : sciencePlans) {
            if (sp.getPlanNo() == planNo) {
                return sp;
            }
        }
        for (SciencePlan sp : draftSciencePlans) {
            if (sp.getPlanNo() == planNo) {
                return sp;
            }
        }
        return null;
    }

    @Override
    public String createSciencePlan(SciencePlan sciencePlan, Astronomer an) {
        if (simulateNetworkError) {
            return "Unable to save the science plan due to network or system error. Please try again later.";
        }

        sciencePlan.setPlanNo(nextPlanNo++);
        sciencePlan.setCreator(an);

        // Check for incomplete required fields - save as draft
        if (isBlank(sciencePlan.getName()) ||
            isBlank(sciencePlan.getObjective()) ||
            sciencePlan.getStartDate() == null ||
            sciencePlan.getEndDate() == null ||
            isBlank(sciencePlan.getTelescope()) ||
            isBlank(sciencePlan.getTarget())) {
            
            draftSciencePlans.add(sciencePlan);
            sciencePlan.setStatus(AbstractSciencePlan.STATUS.CREATED);
            return "Please complete all required fields before saving the science plan. Draft saved as #" + sciencePlan.getPlanNo();
        }

        // Validation 1: Start date vs End date
        if (sciencePlan.getStartDate().after(sciencePlan.getEndDate())) {
            draftSciencePlans.add(sciencePlan);
            sciencePlan.setStatus(AbstractSciencePlan.STATUS.CREATED);
            return "Start date cannot be after the end date. Draft saved as #" + sciencePlan.getPlanNo();
        }

        // Validation 2: Funding
        if (sciencePlan.getFunding() <= 0) {
            draftSciencePlans.add(sciencePlan);
            sciencePlan.setStatus(AbstractSciencePlan.STATUS.CREATED);
            return "Funding amount must be a positive numerical value. Draft saved as #" + sciencePlan.getPlanNo();
        }

        // Validation 3: Duplicate name
        for (SciencePlan existing : sciencePlans) {
            if (existing.getName().equalsIgnoreCase(sciencePlan.getName())) {
                draftSciencePlans.add(sciencePlan);
                sciencePlan.setStatus(AbstractSciencePlan.STATUS.CREATED);
                return "A science plan with this name already exists. Please use a different plan name. Draft saved as #" + sciencePlan.getPlanNo();
            }
        }

        // Validation 4: Target in star catalogue
        if (!Validation.isValidTarget(sciencePlan.getTarget())) {
            draftSciencePlans.add(sciencePlan);
            sciencePlan.setStatus(AbstractSciencePlan.STATUS.CREATED);
            return "Selected target not found in the star catalogue. Please choose a valid target. Draft saved as #" + sciencePlan.getPlanNo();
        }

        // Validation 5: Date range conflicts
        String conflictError = checkDateConflict(sciencePlan);
        if (conflictError != null) {
            draftSciencePlans.add(sciencePlan);
            sciencePlan.setStatus(AbstractSciencePlan.STATUS.CREATED);
            return conflictError + " Draft saved as #" + sciencePlan.getPlanNo();
        }

        // Validation 6: Data processing ranges
        String rangeError = validateDataProcessingRanges(sciencePlan);
        if (rangeError != null) {
            draftSciencePlans.add(sciencePlan);
            sciencePlan.setStatus(AbstractSciencePlan.STATUS.CREATED);
            return rangeError + " Draft saved as #" + sciencePlan.getPlanNo();
        }

        // Validation 7: Color mode requirements
        if (sciencePlan.getColorType() != null) {
            if (sciencePlan.getColorType() == SciencePlan.ColorType.COLOR) {
                if (sciencePlan.getBrightness() == null || 
                    sciencePlan.getSaturation() == null || 
                    sciencePlan.getExposure() == null) {
                    draftSciencePlans.add(sciencePlan);
                    sciencePlan.setStatus(AbstractSciencePlan.STATUS.CREATED);
                    return "Please complete all required fields for the selected data processing mode. Draft saved as #" + sciencePlan.getPlanNo();
                }
            }
        }

        // Validation 8: Contrast required
        if (sciencePlan.getContrast() == null) {
            draftSciencePlans.add(sciencePlan);
            sciencePlan.setStatus(AbstractSciencePlan.STATUS.CREATED);
            return "Please complete all required fields for the selected data processing mode. Draft saved as #" + sciencePlan.getPlanNo();
        }

        // Validation 9: Legacy system compatibility
        String legacyError = checkLegacyCompatibility(sciencePlan);
        if (legacyError != null) {
            draftSciencePlans.add(sciencePlan);
            sciencePlan.setStatus(AbstractSciencePlan.STATUS.CREATED);
            return legacyError + " Draft saved as #" + sciencePlan.getPlanNo();
        }

        // All validations passed - save as complete plan
        sciencePlan.setStatus(AbstractSciencePlan.STATUS.CREATED);
        sciencePlans.add(sciencePlan);
        
        return "Science plan #" + sciencePlan.getPlanNo() + " '" + 
                sciencePlan.getName() + "' created successfully.";
    }

    private String checkDateConflict(SciencePlan newPlan) {
        for (SciencePlan existing : sciencePlans) {
            if (!existing.getTelescope().equalsIgnoreCase(newPlan.getTelescope())) {
                continue;
            }
            
            Date existingStart = existing.getStartDate();
            Date existingEnd = existing.getEndDate();
            Date newStart = newPlan.getStartDate();
            Date newEnd = newPlan.getEndDate();
            
            boolean overlap = !(newEnd.before(existingStart) || newStart.after(existingEnd));
            
            if (overlap) {
                return "Selected date range conflicts with another scheduled plan for this telescope.";
            }
        }
        return null;
    }

    private String validateDataProcessingRanges(SciencePlan sp) {
        List<String> errors = new ArrayList<>();
        
        if (sp.getContrast() != null && !Validation.isInRange(sp.getContrast(), 
                Validation.CONTRAST_MIN, Validation.CONTRAST_MAX)) {
            errors.add("Contrast must be between " + Validation.getRangeString(
                    Validation.CONTRAST_MIN, Validation.CONTRAST_MAX));
        }
        
        if (sp.getBrightness() != null && !Validation.isInRange(sp.getBrightness(), 
                Validation.BRIGHTNESS_MIN, Validation.BRIGHTNESS_MAX)) {
            errors.add("Brightness must be between " + Validation.getRangeString(
                    Validation.BRIGHTNESS_MIN, Validation.BRIGHTNESS_MAX));
        }
        
        if (sp.getSaturation() != null && !Validation.isInRange(sp.getSaturation(), 
                Validation.SATURATION_MIN, Validation.SATURATION_MAX)) {
            errors.add("Saturation must be between " + Validation.getRangeString(
                    Validation.SATURATION_MIN, Validation.SATURATION_MAX));
        }
        
        if (sp.getExposure() != null && !Validation.isInRange(sp.getExposure(), 
                Validation.EXPOSURE_MIN, Validation.EXPOSURE_MAX)) {
            errors.add("Exposure must be between " + Validation.getRangeString(
                    Validation.EXPOSURE_MIN, Validation.EXPOSURE_MAX));
        }
        
        if (!errors.isEmpty()) {
            return "Invalid input value. Please enter valid decimal numbers within the allowed range. " + 
                   String.join(". ", errors) + ".";
        }
        
        return null;
    }

    private String checkLegacyCompatibility(SciencePlan sp) {
        if (sp.getFileType() == SciencePlan.FileType.RAW && 
            sp.getFileQuality() == SciencePlan.FileQuality.FINE) {
            return "Input data is not compatible with the legacy OCS system.";
        }
        return null;
    }

    @Override
    public String submitSciencePlan(SciencePlan sciencePlan, Astronomer an) {
        SciencePlan stored = getSciencePlanByNo(sciencePlan.getPlanNo());
        if (stored == null) {
            return "Science plan not found.";
        }

        AbstractSciencePlan.STATUS st = stored.getStatus();
        
        if (st == AbstractSciencePlan.STATUS.CREATED) {
            return "This science plan is created but not tested. Please test it before submission.";
        }

        if (st == AbstractSciencePlan.STATUS.SUBMITTED ||
            st == AbstractSciencePlan.STATUS.VALIDATED ||
            st == AbstractSciencePlan.STATUS.RUNNING ||
            st == AbstractSciencePlan.STATUS.COMPLETE) {
            return "This science plan has already been submitted or executed.";
        }

        if (st == AbstractSciencePlan.STATUS.INVALIDATED ||
            st == AbstractSciencePlan.STATUS.CANCELLED) {
            return "This science plan cannot be submitted because it is " + st + ".";
        }

        if (st != AbstractSciencePlan.STATUS.TESTED) {
            return "This science plan must be tested before submission. Current status: " + st;
        }

        stored.setStatus(AbstractSciencePlan.STATUS.SUBMITTED);
        
        return "Science plan #" + stored.getPlanNo() + " submitted successfully. It now awaits validation by a Science Observer.";
    }

    @Override
    public boolean updateSciencePlanStatus(int planno, AbstractSciencePlan.STATUS stssp) {
        SciencePlan sp = getSciencePlanByNo(planno);
        if (sp == null) return false;
        sp.setStatus(stssp);
        return true;
    }

    @Override
    public String testSciencePlan(SciencePlan sciencePlan) {
        // This version always marks as TESTED (for backward compatibility)
        return testSciencePlan(sciencePlan, true);
    }

    // New overloaded method with option to control status update
    public String testSciencePlan(SciencePlan sciencePlan, boolean markAsTested) {
        SciencePlan stored = getSciencePlanByNo(sciencePlan.getPlanNo());
        if (stored == null) {
            return "Science plan not found.";
        }

        if (stored.getStatus() != AbstractSciencePlan.STATUS.CREATED) {
            return "TEST FAILED: Science plan must be in CREATED status. Current status: " + stored.getStatus();
        }

        if (stored.getFunding() <= 0) {
            return "TEST FAILED: Invalid funding amount.";
        }
        
        if (isBlank(stored.getObjective())) {
            return "TEST FAILED: Missing objective.";
        }
        
        if (isBlank(stored.getTarget())) {
            return "TEST FAILED: Missing target.";
        }

        // Only update status if markAsTested is true
        if (markAsTested) {
            stored.setStatus(AbstractSciencePlan.STATUS.TESTED);
            return "Science plan #" + stored.getPlanNo() + " passed the test. Status updated to TESTED.";
        } else {
            return "Science plan #" + stored.getPlanNo() + " passed the test. Status remains CREATED.";
        }
    }

    @Override
    public void deleteAllSciencePlans() {
        sciencePlans.clear();
        draftSciencePlans.clear();
        nextPlanNo = 1;
    }

    @Override
    public boolean deleteSciencePlanByNo(int planNo) {
        SciencePlan sp = getSciencePlanByNo(planNo);
        if (sp == null) return false;
        return sciencePlans.remove(sp) || draftSciencePlans.remove(sp);
    }

    @Override
    public SciencePlan validateSciencePlan(AbstractSciencePlan sp, ScienceObserver so) {
        SciencePlan sciencePlan = (SciencePlan) sp;
        
        if (sciencePlan == null) {
            return null;
        }

        if (sciencePlan.getStatus() == AbstractSciencePlan.STATUS.SUBMITTED) {
            sciencePlan.setStatus(AbstractSciencePlan.STATUS.VALIDATED);
            return sciencePlan;
        }

        return null;
    }

    @Override
    public ObservingProgram createObservingProgram(
            AbstractSciencePlan sp,
            String opticsPrimary,
            double fStop,
            double opticsSecondaryRMS,
            double scienceFoldMirrorDegree,
            AbstractObservingProgramConfigs.FoldMirrorType scienceFoldMirrorType,
            int moduleContent,
            AbstractObservingProgramConfigs.CalibrationUnit calibrationUnit,
            AbstractObservingProgramConfigs.LightType lightType,
            AbstractTelePositionPair[] telePositionPair,
            ScienceObserver so) {

        SciencePlan sciencePlan = (SciencePlan) sp;

        if (sciencePlan.getStatus() != AbstractSciencePlan.STATUS.VALIDATED) {
            System.err.println("Error: Science plan must be VALIDATED. Current status: " + sciencePlan.getStatus());
            return null;
        }

        if ("GNZ".equalsIgnoreCase(opticsPrimary)) {
            if (fStop < Validation.GNZ_FSTOP_MIN || fStop > Validation.GNZ_FSTOP_MAX) {
                System.err.println("Error: F-stop for GNZ must be in range " + 
                        Validation.GNZ_FSTOP_MIN + "-" + Validation.GNZ_FSTOP_MAX + ". Got: " + fStop);
                return null;
            }
        } else if ("GSZ".equalsIgnoreCase(opticsPrimary)) {
            if (fStop < Validation.GSZ_FSTOP_MIN || fStop > Validation.GSZ_FSTOP_MAX) {
                System.err.println("Error: F-stop for GSZ must be in range " + 
                        Validation.GSZ_FSTOP_MIN + "-" + Validation.GSZ_FSTOP_MAX + ". Got: " + fStop);
                return null;
            }
        } else {
            System.err.println("Error: Optics primary must be GNZ or GSZ. Got: " + opticsPrimary);
            return null;
        }

        String telescope = sciencePlan.getTelescope();
        if (telescope != null) {
            if (telescope.equalsIgnoreCase("Hawaii")) {
                if (opticsSecondaryRMS < Validation.HAWAII_RMS_MIN || 
                    opticsSecondaryRMS > Validation.HAWAII_RMS_MAX) {
                    System.err.println("Error: Optics secondary RMS for Hawaii must be " + 
                            Validation.HAWAII_RMS_MIN + "-" + Validation.HAWAII_RMS_MAX + 
                            "nm. Got: " + opticsSecondaryRMS);
                    return null;
                }
            } else if (telescope.equalsIgnoreCase("Chile")) {
                if (opticsSecondaryRMS < Validation.CHILE_RMS_MIN || 
                    opticsSecondaryRMS > Validation.CHILE_RMS_MAX) {
                    System.err.println("Error: Optics secondary RMS for Chile must be " + 
                            Validation.CHILE_RMS_MIN + "-" + Validation.CHILE_RMS_MAX + 
                            "nm. Got: " + opticsSecondaryRMS);
                    return null;
                }
            }
        }

        if (scienceFoldMirrorDegree < Validation.FOLD_MIRROR_DEGREE_MIN || 
            scienceFoldMirrorDegree > Validation.FOLD_MIRROR_DEGREE_MAX) {
            System.err.println("Error: Science fold mirror degree must be in range " + 
                    Validation.FOLD_MIRROR_DEGREE_MIN + "-" + Validation.FOLD_MIRROR_DEGREE_MAX + 
                    ". Got: " + scienceFoldMirrorDegree);
            return null;
        }

        if (moduleContent < Validation.MODULE_CONTENT_MIN || 
            moduleContent > Validation.MODULE_CONTENT_MAX) {
            System.err.println("Error: Module content must be " + 
                    Validation.MODULE_CONTENT_MIN + "-" + Validation.MODULE_CONTENT_MAX + 
                    ". Got: " + moduleContent);
            return null;
        }

        ObservingProgramConfigs conf = new ObservingProgramConfigs(
                opticsPrimary,
                fStop,
                opticsSecondaryRMS,
                scienceFoldMirrorDegree,
                scienceFoldMirrorType,
                moduleContent,
                calibrationUnit,
                lightType,
                telePositionPair
        );

        ObservingProgram op = new ObservingProgram();
        op.setId(nextObservingProgramId++);
        op.setSciencePlan(sciencePlan);
        op.setConfigs(conf);
        op.setObserver(so);

        if (telescope != null) {
            op.setGeminiLocation(telescope);
        }
        op.setOpticsPrimary(opticsPrimary);
        op.setFStop(fStop);
        op.setOpticsSecondaryRMS(opticsSecondaryRMS);
        op.setScienceFoldMirrorDegree(scienceFoldMirrorDegree);
        op.setModuleContent(moduleContent);

        observingPrograms.add(op);
        sciencePlan.setStatus(AbstractSciencePlan.STATUS.RUNNING);

        System.out.println("ObservingProgram created successfully with ID: " + op.getId());
        
        return op;
    }

    @Override
    public boolean saveObservingProgram(AbstractObservingProgram op) {
        ObservingProgram observingProgram = (ObservingProgram) op;
        if (observingProgram != null) {
            if (!observingPrograms.contains(observingProgram)) {
                observingPrograms.add(observingProgram);
            }
            return true;
        }
        return false;
    }

    @Override
    public ObservingProgram getObservingProgramBySciencePlan(AbstractSciencePlan sp) {
        SciencePlan sciencePlan = (SciencePlan) sp;
        for (ObservingProgram op : observingPrograms) {
            if (op.getSciencePlan() != null && op.getSciencePlan().getPlanNo() == sciencePlan.getPlanNo()) {
                return op;
            }
        }
        return null;
    }

    public ArrayList<ObservingProgram> getAllObservingPrograms() {
        return observingPrograms;
    }

    @Override
    public String addUnavailableDate(Date datevalue) {
        if (!unavailableDates.contains(datevalue)) {
            unavailableDates.add(datevalue);
            return "Added unavailable date: " + datevalue;
        }
        return "Date already exists.";
    }

    @Override
    public String deleteUnavailableDate(Date datevalue) {
        if (unavailableDates.remove(datevalue)) {
            return "Removed unavailable date: " + datevalue;
        }
        return "Date not found.";
    }

    @Override
    public ArrayList<Date> getAllObservationSchedule() {
        return unavailableDates;
    }

    @Override
    public String getConfigurations() {
        if (configurations.isEmpty()) {
            return "No configurations installed.";
        }
        return String.join(",", configurations);
    }

    @Override
    public boolean addConfiguration(String confFilePath) {
        if (!configurations.contains(confFilePath)) {
            configurations.add(confFilePath);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeConfiguration(int confNo) {
        if (confNo < 0 || confNo >= configurations.size()) return false;
        configurations.remove(confNo);
        return true;
    }

    @Override
    public AbstractObservingProgramConfigs.FoldMirrorType[] getFoldMirrorTypes() {
        return AbstractObservingProgramConfigs.FoldMirrorType.values();
    }

    @Override
    public AbstractObservingProgramConfigs.CalibrationUnit[] getCalibrationUnits() {
        return AbstractObservingProgramConfigs.CalibrationUnit.values();
    }

    @Override
    public AbstractObservingProgramConfigs.LightType[] getLightTypes() {
        return AbstractObservingProgramConfigs.LightType.values();
    }

    @Override
    public void getDefaultConfiguration() throws IOException {
        System.out.println("{ \"config\": \"default\" }");
    }

    @Override
    public void getCurrentConfiguration() throws IOException {
        System.out.println("{ \"config\": \"current\" }");
    }

    @Override
    public String updateConfiguration() throws FileNotFoundException {
        return "Configuration updated successfully.";
    }

    @Override
    public String accessTelescopeLiveView() throws IOException {
        return "http://localhost:8080/telescope-live";
    }

    @Override
    public String executeCommand(String command) {
        return "Executed command: " + command.toUpperCase();
    }

    @Override
    public AstronomicalData getAstronomicalData(SciencePlan sciencePlan) throws IOException {
        return null;
    }

    @Override
    public AstronomicalData removeAstronomicalData(SciencePlan sciencePlan, int index) throws IOException {
        return null;
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
    
    public void setSimulateNetworkError(boolean simulate) {
        this.simulateNetworkError = simulate;
    }
}