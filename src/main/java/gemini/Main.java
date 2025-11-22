package edu.gemini;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import edu.gemini.model.AbstractSciencePlan;
import edu.gemini.model.AbstractTelePositionPair;
import edu.gemini.model.StarSystem;

public class Main {

    private static final SimpleDateFormat DATE_FMT = new SimpleDateFormat("yyyy-MM-dd");
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        OCS ocs = new OCS();

        Astronomer astronomer = new Astronomer();
        astronomer.setId(1);
        astronomer.setFirstName("John");
        astronomer.setLastName("Doe");
        astronomer.setInstitution("MIT");
        
        ScienceObserver observer = new ScienceObserver();
        observer.setId(1);
        observer.setFirstName("Jane");
        observer.setLastName("Smith");
        observer.setDepartment("Astro Department");

        while (true) {
            System.out.println("\n===== Gemini OCS CLI =====");
            System.out.println("=== Astronomer Menu ===");
            System.out.println("1. Create a Science Plan");
            System.out.println("2. Test Science Plan");
            System.out.println("3. Submit Science Plan");
            System.out.println("=== Science Observer Menu ===");
            System.out.println("4. Validate Science Plan");
            System.out.println("5. Create an Observing Program");
            System.out.println("=== General ===");
            System.out.println("6. Show All Science Plans");
            System.out.println("0. Exit");
            System.out.print("Select menu: ");

            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1":
                    createSciencePlanUI(ocs, astronomer);
                    break;
                case "2":
                    testSciencePlanUI(ocs, astronomer);
                    break;
                case "3":
                    submitSciencePlanUI(ocs, astronomer);
                    break;
                case "4":
                    validateSciencePlanUI(ocs, observer);
                    break;
                case "5":
                    createObservingProgramUI(ocs, observer);
                    break;
                case "6":
                    listSciencePlans(ocs);
                    break;
                case "0":
                    System.out.println("Bye.");
                    return;
                default:
                    System.out.println("Invalid menu.");
            }
        }
    }

    private static void createSciencePlanUI(OCS ocs, Astronomer astronomer) {
        try {
            System.out.println("\n--- Create a Science Plan ---");

            SciencePlan sp = new SciencePlan();

            System.out.print("Plan name: ");
            String planName = sc.nextLine().trim();
            sp.setName(planName);

            System.out.print("Objective (long text): ");
            String objective = sc.nextLine().trim();
            sp.setObjective(objective);

            System.out.print("Funding in USD (e.g. 12000.50): ");
            sp.setFunding(readDouble());

            System.out.print("Start date (yyyy-MM-dd): ");
            Date startDate = readDate();
            sp.setStartDate(startDate);

            System.out.print("End date (yyyy-MM-dd): ");
            Date endDate = readDate();
            sp.setEndDate(endDate);

            System.out.println("Telescope assigned: 1) Hawaii (North)  2) Chile (South)");
            sp.setTelescope(readTelescope());

            // Direct constellation name input
            System.out.println("\n--- Select Target (Star System/Constellation) ---");
            String target = readConstellationName();
            sp.setTarget(target);

            System.out.println("\n--- Data Processing Requirements ---");

            System.out.println("File type: 1) PNG  2) JPEG  3) RAW");
            sp.setFileType(readFileType());

            System.out.println("File quality: 1) LOW  2) FINE");
            sp.setFileQuality(readFileQuality());

            System.out.println("Color type: 1) COLOR  2) B&W");
            sp.setColorType(readColorType());

            System.out.print("Contrast (0.0-2.0): ");
            sp.setContrast(readDouble());

            if (sp.getColorType() == SciencePlan.ColorType.COLOR) {
                System.out.print("Brightness (-1.0 to 1.0): ");
                sp.setBrightness(readDouble());

                System.out.print("Saturation (0.0-2.0): ");
                sp.setSaturation(readDouble());

                System.out.print("Exposure (-3.0 to 3.0): ");
                sp.setExposure(readDouble());
            }

            String result = ocs.createSciencePlan(sp, astronomer);
            System.out.println("\n[Result] " + result);

        } catch (Exception e) {
            System.out.println("[Error] " + e.getMessage());
        }
    }

    private static String readConstellationName() {
        while (true) {
            System.out.print("Enter constellation/star system name: ");
            String input = sc.nextLine().trim();
            
            if (input.isEmpty()) {
                System.out.println("Constellation name cannot be empty. Please try again.");
                continue;
            }
            
            // Check if the input matches any constellation name
            boolean found = false;
            String matchedName = null;
            
            for (StarSystem.CONSTELLATIONS constellation : StarSystem.CONSTELLATIONS.values()) {
                if (constellation.name().equalsIgnoreCase(input)) {
                    found = true;
                    matchedName = constellation.name();
                    break;
                }
            }
            
            if (found) {
                System.out.println("Valid constellation: " + matchedName);
                return matchedName;
            } else {
                System.out.println("It's not a valid constellation");
                System.out.println("Please enter a valid constellation name from the star catalogue.");
                System.out.println("Examples: Andromeda, Orion, Leo, Aquarius, etc.");
            }
        }
    }

    private static void testSciencePlanUI(OCS ocs, Astronomer astronomer) {
        System.out.println("\n--- Test Science Plan ---");

        List<SciencePlan> plans = ocs.getAllSciencePlans();
        List<SciencePlan> drafts = ocs.getAllDraftSciencePlans();
        
        if (plans.isEmpty() && drafts.isEmpty()) {
            System.out.println("No science plans found. Please create one first.");
            return;
        }

        List<SciencePlan> createdPlans = plans.stream()
                .filter(sp -> sp.getStatus() == AbstractSciencePlan.STATUS.CREATED)
                .toList();

        if (createdPlans.isEmpty()) {
            System.out.println("No science plans in CREATED status available for testing.");
            System.out.println("\nAll science plans:");
            printSciencePlans(plans);
            return;
        }

        System.out.println("\nScience plans available for testing (CREATED status):");
        printSciencePlans(createdPlans);

        System.out.print("Enter Plan ID to test (0 = cancel): ");
        int planNo = readInt();
        if (planNo == 0) {
            System.out.println("Cancelled.");
            return;
        }

        SciencePlan sp = ocs.getSciencePlanByNo(planNo);
        if (sp == null) {
            System.out.println("Science plan not found.");
            return;
        }

        if (sp.getStatus() != AbstractSciencePlan.STATUS.CREATED) {
            System.out.println("This science plan is not in CREATED status.");
            System.out.println("Current status: " + sp.getStatus());
            System.out.println("Only plans in CREATED status can be tested.");
            return;
        }

        System.out.println("\nYou are going to test this plan:");
        printSciencePlanDetail(sp);

        System.out.print("Confirm test? (y/n): ");
        String confirm = sc.nextLine().trim().toLowerCase();
        if (!confirm.equals("y")) {
            System.out.println("Test cancelled.");
            return;
        }

        // NEW: Ask if user wants to mark as TESTED or keep as CREATED
        System.out.println("\nTest options:");
        System.out.println("1. Test and mark as TESTED (ready for submission)");
        System.out.println("2. Test only (keep status as CREATED)");
        System.out.print("Select option (1 or 2): ");
        String option = sc.nextLine().trim();

        boolean markAsTested = option.equals("1");
        
        String result = ocs.testSciencePlan(sp, markAsTested);
        System.out.println("[Result] " + result);
        
        if (result.contains("passed")) {
            System.out.println("Current status: " + sp.getStatus());
            if (markAsTested) {
                System.out.println("This plan can now be submitted.");
            } else {
                System.out.println("Plan tested successfully but remains in CREATED status.");
            }
        }
    }

    private static void submitSciencePlanUI(OCS ocs, Astronomer astronomer) {
        System.out.println("\n--- Submit Science Plan ---");

        List<SciencePlan> plans = ocs.getAllSciencePlans();
        if (plans.isEmpty()) {
            System.out.println("No science plans found. Please complete it before submission.");
            return;
        }

        printSciencePlans(plans);

        System.out.print("Enter Plan ID to submit (0 = cancel): ");
        int planNo = readInt();
        if (planNo == 0) {
            System.out.println("Cancelled.");
            return;
        }

        SciencePlan sp = ocs.getSciencePlanByNo(planNo);
        if (sp == null) {
            System.out.println("Science plan not found.");
            return;
        }

        System.out.println("\nCurrent status: " + sp.getStatus());

        if (sp.getStatus() == AbstractSciencePlan.STATUS.CREATED) {
            System.out.println("This science plan has not been tested yet.");
            System.out.println("You must test the plan before submission.");
            System.out.print("Would you like to test it now? (y/n): ");
            String testNow = sc.nextLine().trim().toLowerCase();
            
            if (testNow.equals("y")) {
                System.out.println("\nTesting plan #" + sp.getPlanNo() + "...");
                String testResult = ocs.testSciencePlan(sp, true);
                System.out.println("[Test Result] " + testResult);
                
                if (!testResult.contains("passed")) {
                    System.out.println("Cannot submit: Plan failed the test.");
                    return;
                }
                System.out.println("Test passed! You can now submit.");
            } else {
                System.out.println("Submission cancelled. Please test the plan first using menu option 2.");
                return;
            }
        } else if (sp.getStatus() != AbstractSciencePlan.STATUS.TESTED) {
            System.out.println("This science plan has already been submitted or executed.");
            System.out.println("Expected status: TESTED, Current status: " + sp.getStatus());
            return;
        }

        System.out.println("\nYou are going to submit this plan:");
        printSciencePlanDetail(sp);

        System.out.print("Confirm submit? (y/n): ");
        String confirm = sc.nextLine().trim().toLowerCase();
        if (!confirm.equals("y")) {
            System.out.println("Submission cancelled.");
            return;
        }

        String result = ocs.submitSciencePlan(sp, astronomer);
        System.out.println("[Result] " + result);
    }

    private static void validateSciencePlanUI(OCS ocs, ScienceObserver observer) {
        System.out.println("\n--- Validate Science Plan ---");

        List<SciencePlan> plans = ocs.getAllSciencePlans();
        if (plans.isEmpty()) {
            System.out.println("No science plans found.");
            return;
        }

        List<SciencePlan> submittedPlans = plans.stream()
                .filter(sp -> sp.getStatus() == AbstractSciencePlan.STATUS.SUBMITTED)
                .toList();

        if (submittedPlans.isEmpty()) {
            System.out.println("No submitted science plans available for validation.");
            System.out.println("\nAll science plans:");
            printSciencePlans(plans);
            return;
        }

        System.out.println("\nSubmitted science plans:");
        printSciencePlans(submittedPlans);

        System.out.print("Enter Plan ID to validate (0 = cancel): ");
        int planNo = readInt();
        if (planNo == 0) {
            System.out.println("Cancelled.");
            return;
        }

        SciencePlan sp = ocs.getSciencePlanByNo(planNo);
        if (sp == null) {
            System.out.println("Science plan not found.");
            return;
        }

        if (sp.getStatus() != AbstractSciencePlan.STATUS.SUBMITTED) {
            System.out.println("This science plan is not in SUBMITTED status.");
            System.out.println("Current status: " + sp.getStatus());
            return;
        }

        System.out.println("\nYou are going to validate this plan:");
        printSciencePlanDetail(sp);

        System.out.print("Validate this plan? (y/n): ");
        String confirm = sc.nextLine().trim().toLowerCase();
        if (!confirm.equals("y")) {
            System.out.println("Validation cancelled.");
            return;
        }

        SciencePlan validated = ocs.validateSciencePlan(sp, observer);
        if (validated != null && validated.getStatus() == AbstractSciencePlan.STATUS.VALIDATED) {
            System.out.println("[Result] Science plan #" + validated.getPlanNo() + " validated successfully!");
            System.out.println("Status updated to: " + validated.getStatus());
            System.out.println("You can now create an observing program from this plan.");
        } else {
            System.out.println("[Result] Failed to validate science plan.");
        }
    }

    private static void createObservingProgramUI(OCS ocs, ScienceObserver observer) {
        System.out.println("\n--- Create an Observing Program ---");
    
        List<SciencePlan> all = ocs.getAllSciencePlans();
    
        List<SciencePlan> validatedPlans = all.stream()
                .filter(sp -> sp.getStatus() == AbstractSciencePlan.STATUS.VALIDATED)
                .toList();
    
        if (validatedPlans.isEmpty()) {
            System.out.println("No validated science plans available.");
            System.out.println("Science plans must be VALIDATED before creating an observing program.");
            System.out.println("\nCurrent science plans:");
            printSciencePlans(all);
            return;
        }
    
        System.out.println("Validated science plans:");
        printSciencePlans(validatedPlans);
    
        System.out.print("Enter Plan ID to create an observing program from (0 = cancel): ");
        int planNo = readInt();
        if (planNo == 0) {
            System.out.println("Cancelled.");
            return;
        }
    
        SciencePlan sp = ocs.getSciencePlanByNo(planNo);
        if (sp == null) {
            System.out.println("Science plan not found.");
            return;
        }
        
        if (sp.getStatus() != AbstractSciencePlan.STATUS.VALIDATED) {
            System.out.println("Selected plan is not VALIDATED (current: " + sp.getStatus() + ")");
            System.out.println("Only VALIDATED plans can be used to create observing programs.");
            return;
        }
    
        System.out.println("\nEnter observing program parameters:");
        System.out.println("(Refer to telescope: " + sp.getTelescope() + ")");

        System.out.print("Optics primary (GNZ for North/Hawaii, GSZ for South/Chile): ");
        String opticsPrimary = sc.nextLine().trim().toUpperCase();

        System.out.print("F-stop (GNZ: 1.8-8.1, GSZ: 2.9-18.0): ");
        double fStop = readDouble();

        System.out.print("Optics secondary RMS (Hawaii: 5-17nm, Chile: 5-13nm): ");
        double opticsSecondaryRMS = readDouble();

        System.out.print("Science fold mirror degree (30-45): ");
        double degree = readDouble();

        System.out.println("Fold mirror type: 1) REFLECTIVE_CONVERGING_BEAM  2) CASSEGRAIN_FOCUS");
        ObservingProgramConfigs.FoldMirrorType foldType = readFoldMirrorType();

        System.out.print("Module content (1-4): ");
        int moduleContent = readInt();

        System.out.println("Calibration Unit: 1) Argon  2) Xenon  3) ThAr  4) CuAr");
        ObservingProgramConfigs.CalibrationUnit calUnit = readCalibrationUnit();

        System.out.println("Light Type: 1) MaunaKeaSkyEmission  2) CerroPachonSkyEmission");
        ObservingProgramConfigs.LightType lightType = readLightType();

        AbstractTelePositionPair[] telePairs = new AbstractTelePositionPair[0];

        System.out.println("\nCreating observing program...");
        
        ObservingProgram op = ocs.createObservingProgram(
                sp, opticsPrimary, fStop, opticsSecondaryRMS, degree,
                foldType, moduleContent, calUnit, lightType, telePairs, observer);

        if (op == null) {
            System.out.println("[Result] Failed to create observing program.");
            System.out.println("Please check your input parameters:");
            System.out.println("  - Optics primary must be GNZ or GSZ");
            System.out.println("  - F-stop range: GNZ (1.8-8.1), GSZ (2.9-18.0)");
            System.out.println("  - Science fold mirror degree: 30-45");
            System.out.println("  - Module content: 1-4");
        } else {
            System.out.println("[Result] Observing program created successfully!");
            System.out.println("Program ID: " + op.getId());
            System.out.println("Science Plan: #" + sp.getPlanNo() + " - " + sp.getName());
            System.out.println("Science Plan Status: " + sp.getStatus());
            System.out.println("Observer: " + observer.getFirstName() + " " + observer.getLastName());
        }
    }

    private static void listSciencePlans(OCS ocs) {
        System.out.println("\n--- All Science Plans ---");
        List<SciencePlan> plans = ocs.getAllSciencePlans();
        List<SciencePlan> drafts = ocs.getAllDraftSciencePlans();
        
        if (plans.isEmpty() && drafts.isEmpty()) {
            System.out.println("No science plans.");
            return;
        }
        
        if (!plans.isEmpty()) {
            System.out.println("\n=== Complete Science Plans ===");
            printSciencePlans(plans);
            
            System.out.println("\n--- Status Summary ---");
            long created = plans.stream().filter(sp -> sp.getStatus() == AbstractSciencePlan.STATUS.CREATED).count();
            long tested = plans.stream().filter(sp -> sp.getStatus() == AbstractSciencePlan.STATUS.TESTED).count();
            long submitted = plans.stream().filter(sp -> sp.getStatus() == AbstractSciencePlan.STATUS.SUBMITTED).count();
            long validated = plans.stream().filter(sp -> sp.getStatus() == AbstractSciencePlan.STATUS.VALIDATED).count();
            long running = plans.stream().filter(sp -> sp.getStatus() == AbstractSciencePlan.STATUS.RUNNING).count();
            long complete = plans.stream().filter(sp -> sp.getStatus() == AbstractSciencePlan.STATUS.COMPLETE).count();
            
            System.out.println("CREATED: " + created);
            System.out.println("TESTED: " + tested);
            System.out.println("SUBMITTED: " + submitted);
            System.out.println("VALIDATED: " + validated);
            System.out.println("RUNNING: " + running);
            System.out.println("COMPLETE: " + complete);
        }
        
        if (!drafts.isEmpty()) {
            System.out.println("\n=== Draft Science Plans (" + drafts.size() + " drafts) ===");
            printSciencePlans(drafts);
        }
    }

    private static void printSciencePlans(List<SciencePlan> plans) {
        System.out.println();
        System.out.printf("%-5s %-35s %-12s %-12s %-15s%n", 
                "ID", "Name", "Status", "Telescope", "Target");
        System.out.println("-".repeat(82));
        
        for (SciencePlan sp : plans) {
            System.out.printf("%-5d %-35s %-12s %-12s %-15s%n",
                    sp.getPlanNo(),
                    truncate(sp.getName() != null ? sp.getName() : "[DRAFT]", 35),
                    sp.getStatus(),
                    truncate(sp.getTelescope() != null ? sp.getTelescope() : "-", 12),
                    truncate(sp.getTarget() != null ? sp.getTarget() : "-", 15)
            );
        }
    }

    private static void printSciencePlanDetail(SciencePlan sp) {
        System.out.println("Plan #" + sp.getPlanNo());
        System.out.println("Name: " + (sp.getName() != null ? sp.getName() : "[DRAFT]"));
        System.out.println("Objective: " + (sp.getObjective() != null ? sp.getObjective() : "-"));
        System.out.println("Funding: $" + String.format("%.2f", sp.getFunding()));
        System.out.println("Start: " + (sp.getStartDate() != null ? DATE_FMT.format(sp.getStartDate()) : "N/A") + 
                          "  End: " + (sp.getEndDate() != null ? DATE_FMT.format(sp.getEndDate()) : "N/A"));
        System.out.println("Telescope: " + (sp.getTelescope() != null ? sp.getTelescope() : "-"));
        System.out.println("Target: " + (sp.getTarget() != null ? sp.getTarget() : "-"));
        System.out.println("Status: " + sp.getStatus());
    }

    private static String truncate(String str, int maxLength) {
        if (str == null) return "";
        if (str.length() <= maxLength) return str;
        return str.substring(0, maxLength - 3) + "...";
    }

    private static Date readDate() throws ParseException {
        while (true) {
            String text = sc.nextLine().trim();
            try {
                return DATE_FMT.parse(text);
            } catch (ParseException e) {
                System.out.print("Invalid date, please use yyyy-MM-dd: ");
            }
        }
    }

    private static int readInt() {
        while (true) {
            try {
                String s = sc.nextLine().trim();
                return Integer.parseInt(s);
            } catch (NumberFormatException e) {
                System.out.print("Please enter an integer: ");
            }
        }
    }

    private static double readDouble() {
        while (true) {
            try {
                String s = sc.nextLine().trim();
                return Double.parseDouble(s);
            } catch (NumberFormatException e) {
                System.out.print("Please enter a decimal number: ");
            }
        }
    }

    private static String readTelescope() {
        while (true) {
            String choice = sc.nextLine().trim();
            if ("1".equals(choice)) return "Hawaii";
            if ("2".equals(choice)) return "Chile";
            System.out.print("Please enter 1 or 2: ");
        }
    }

    private static SciencePlan.FileType readFileType() {
        while (true) {
            String c = sc.nextLine().trim();
            switch (c) {
                case "1": return SciencePlan.FileType.PNG;
                case "2": return SciencePlan.FileType.JPEG;
                case "3": return SciencePlan.FileType.RAW;
                default: System.out.print("Please enter 1, 2 or 3: ");
            }
        }
    }

    private static SciencePlan.FileQuality readFileQuality() {
        while (true) {
            String c = sc.nextLine().trim();
            switch (c) {
                case "1": return SciencePlan.FileQuality.LOW;
                case "2": return SciencePlan.FileQuality.FINE;
                default: System.out.print("Please enter 1 or 2: ");
            }
        }
    }

    private static SciencePlan.ColorType readColorType() {
        while (true) {
            String c = sc.nextLine().trim();
            switch (c) {
                case "1": return SciencePlan.ColorType.COLOR;
                case "2": return SciencePlan.ColorType.BW;
                default: System.out.print("Please enter 1 or 2: ");
            }
        }
    }

    private static ObservingProgramConfigs.FoldMirrorType readFoldMirrorType() {
        while (true) {
            String c = sc.nextLine().trim();
            switch (c) {
                case "1": return ObservingProgramConfigs.FoldMirrorType.REFLECTIVE_CONVERGING_BEAM;
                case "2": return ObservingProgramConfigs.FoldMirrorType.CASSEGRAIN_FOCUS;
                default: System.out.print("Please enter 1 or 2: ");
            }
        }
    }

    private static ObservingProgramConfigs.CalibrationUnit readCalibrationUnit() {
        while (true) {
            String c = sc.nextLine().trim();
            switch (c) {
                case "1": return ObservingProgramConfigs.CalibrationUnit.Argon;
                case "2": return ObservingProgramConfigs.CalibrationUnit.Xenon;
                case "3": return ObservingProgramConfigs.CalibrationUnit.ThAr;
                case "4": return ObservingProgramConfigs.CalibrationUnit.CuAr;
                default: System.out.print("Please enter 1, 2, 3 or 4: ");
            }
        }
    }

    private static ObservingProgramConfigs.LightType readLightType() {
        while (true) {
            String c = sc.nextLine().trim();
            switch (c) {
                case "1": return ObservingProgramConfigs.LightType.MaunaKeaSkyEmission;
                case "2": return ObservingProgramConfigs.LightType.CerroPachonSkyEmission;
                default: System.out.print("Please enter 1 or 2: ");
            }
        }
    }
}