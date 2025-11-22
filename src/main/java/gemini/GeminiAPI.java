package edu.gemini;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import edu.gemini.model.AbstractAstronomer;
import edu.gemini.model.AbstractAstronomicalData;
import edu.gemini.model.AbstractObservingProgram;
import edu.gemini.model.AbstractObservingProgramConfigs;
import edu.gemini.model.AbstractScienceObserver;
import edu.gemini.model.AbstractSciencePlan;
import edu.gemini.model.AbstractTelePositionPair;

public interface GeminiAPI<
        SP extends AbstractSciencePlan,
        OP extends AbstractObservingProgram,
        OPC extends AbstractObservingProgramConfigs,
        AAD extends AbstractAstronomicalData,
        AA extends AbstractAstronomer,
        AS extends AbstractScienceObserver> {
    /**
     * Get all the science plans in the Gemini system
     *
     * @return a list of science plans
     */
    public ArrayList<SP> getAllSciencePlans();


    /**
     * Get a specific science plan by providing a plan number.
     *
     * @param planNo the science plan number
     * @return the selected science plan
     */
    public SP getSciencePlanByNo(int planNo);



    public String createSciencePlan(SP sp, AA an);

    /**
     * Submit a new science plan into the Gemini system.
     *
     * @param sp the science plan to be submitted
     * @return a string indicating the result of the submission
     */
    public String submitSciencePlan(SP sp, AA an);


    /**
     * Update the status of a science plan
     *
     * @param planno the number of the science plan to be updated
     * @param stssp  the status to update (using SciencePlan.STATUS enum).
     * @return true=successful, false=failed
     */
    public boolean updateSciencePlanStatus(int planno, AbstractSciencePlan.STATUS stssp);


    /**
     * Test a science plan before getting executed.
     *
     * @param sp the science plan to be tested
     * @return a string indicating the result of the test
     */
    public String testSciencePlan(SP sp);


    /**
     * Delete all the science plans in the Gemini system (BE CAREFUL WHEN CALLING THIS).
     */
    public void deleteAllSciencePlans();


    /**
     * Delete a specific science plan by a plan number
     *
     * @param planNo the number of the science plan to be deleted
     * @return true=successful, false=failed
     */
    boolean deleteSciencePlanByNo(int planNo);


    /***
     * Add a new unavailable date into the observational schedule
     * @param datevalue the date that Gemini is not available
     * @return A string explaining the status of the addition
     */
    String addUnavailableDate(Date datevalue);


    /***
     * Delete an existing unavailable date from the observational schedule
     * @param datevalue the date that will be removed from the observational schedule
     * @return A string explaining the status of the addition
     */
    String deleteUnavailableDate(Date datevalue);


    /***
     * Retrieve all the unavailable observation schedule of Gemini
     * @return List of dates that the telescope is unavailable.
     */
    ArrayList<Date> getAllObservationSchedule();


    /**
     * Get the collected astronomical data of a given science plan
     *
     * @param sp the science plan to get the astronomical data
     * @return astronomical data ({@link edu.gemini.model.AstronomicalData}), or null if the plan is not complete yet.
     */
    AAD getAstronomicalData(SP sp) throws IOException;


    /***
     * Remove astronomical data from a given science plan
     * @param sp the science plan to remove the astronomical data
     * @param index index of the astronomical data ({@link edu.gemini.model.AstronomicalData}) to removve
     * @return the updated astronomical data
     * @throws IOException
     */
    AAD removeAstronomicalData(SP sp, int index) throws IOException;


    /**
     * Get access to the telescope's live view
     *
     * @return the link to the live video of the telescope
     * @throws IOException IOException is thrown when errors occur
     */
    String accessTelescopeLiveView() throws IOException;


    /**
     * The following commands are “status” queries used to obtain information about the state of the subsystem.
     * <ul>
     *   <li>GetVersion The subsystem returns its version identification as a string value.</li>
     *   <li>GetStatus The subsystem reports its current status as one of:</li>
     *   <li>
     *      <ul>
     *      <li>DOWN - the subsystem is not operational</li>
     *      <li>BOOTED - the subsystem has been booted, but not yet configured</li>
     *      <li>CONFIGURING - the system is in the process of configuring.</li>
     *      <li>CONFIGURED - the subsystem is configured, but not yet initialized</li>
     *      <li>INITIALIZING - the subsystem is doing initialization actions</li>
     *      <li>RUNNING - the subsystem is running at observing level</li>
     *      <li>MAINTENANCE - the subsystem is running at maintenance level</li>
     *      <li>SIMULATION - the subsystem is running in simulation mode</li>
     *      <li>DISABLED - the subsystem is functional, but has been commanded to ignore control commands</li>
     *      <li>SHUTDOWN - the subsystem is in the process of shutting down</li>
     *      <li>LOCKED - an interlock exists on this subsystem.</li>
     *      </ul>
     *   </li>
     * </ul>
     * GetState The internal state of the subsystem is returned as one of:
     * <ul>
     * <li>READY - the subsystem is fully operational, but currently idle.</li>
     * <li>BUSY_ON command - the subsystem is working, and currently processing the indicated command.</li>
     * <li>NOT_READY - the subsystem is not responding to commands at this time.</li>
     * </ul>
     * RunTest The subsystem runs a self-test and reports the result as one of:
     * <ul>
     * <li>OK - the subsystem has detected no problems, it is running within specifications</li>
     * <li>BAD - the subsystem has detected problems that prevent its successful operation</li>
     * <li>WARNING - the subsystem has detected a problem that may prevent it from operating to
     *    full specification, but does not prevent it from functioning at this time</li>
     * </ul>
     * Control commands to control the virtual telescope are as followings
     * <ul>
     *     <li>START - starting the virtual telescope</li>
     *     <li>UP - moving the telescope up by 10 points</li>
     *     <li>DOWN - moving the telescope down by 10 points</li>
     *     <li>LEFT - moving the telescope left by 10 points</li>
     *     <li>RIGHT - moving the telescope right by 10 points</li>
     *     <li>FOCUS - instructing the telescope to adjust the focus</li>
     *     <li>TAKE_PHOTO - taking the photo at the current location</li>
     *     <li>STOP - stopping the virtual telescope</li>
     * </ul>
     *
     * @param command the command to be executed
     * @return the result of running the command
     */
    String executeCommand(String command);


    /**
     * Get all configurations
     *
     * @return A list of all configurations
     */
    String getConfigurations();


    /**
     * Add a configuration to the Gemini system
     *
     * @param confFilePath the path to the configuration file to be added
     * @return true=successful, false=failed
     */
    boolean addConfiguration(String confFilePath);


    /**
     * Remove a configuration that has already been installed in the Gemini system
     *
     * @param confNo the no of the configuration to be removed
     * @return true=successfully removed, false=failed to remove
     */
    boolean removeConfiguration(int confNo);


    /**
     * Get the list of supported fold mirror types
     * @return a list of supported fold mirror types
     */
    OPC.FoldMirrorType[] getFoldMirrorTypes();


    /**
     * Get the list of supported calibration units
     * @return a list of supported calibration units
     */
    OPC.CalibrationUnit[] getCalibrationUnits();


    /**
     * Get the list of supported light types
     * @return a list of supported light types
     */
    OPC.LightType[] getLightTypes();


    /**
     * Create an observing program
     * @param sp The science plan that the observing program is based on
     * @param opticsPrimary The primary optics that is used in the science plan.
     *                      There are two types of primary optics:
     *                      <ul>
     *                          <li>GNZ (GN Zemax Model)</li>
     *                          <li>GSZ (GS Zemax Model)</li>
     *                      </ul>
     * @param fStop The f-stop of the primary optics.
     *              <ul>
     *                  <li>The value range of f-stop for GNZ is 1.8 - 8.1</li>
     *                  <li>The value range of f-stop for GSZ is 2.9 - 18.</li>
     *              </ul>
     * @param opticsSecondaryRMS The secondary optics that is used in the science plan, which depends on the
     *                           location of the telescope.
     *                           <ul>
     *                              <li>South telescope (Cerro Pachón, Chile): 5-13 nm</li>
     *                              <li>North telescope (Mauna Kea, Hawaii): 5-17 nm</li>
     *                           </ul>
     * @param scienceFoldMirrorDegree The degree of angle of the fold mirror. The supported degree is in
     *                                the range of [30, 45].
     * @param scienceFoldMirrorType The type of the fold mirror. There are two options: Reflective converging
     *                              beam or Cassegrain focus
     * @param moduleContent There are 4 types of module content:
     *                      <ul>
     *                          <li>1: Module 1 acquisition camera/high resolution wavefront sensor.</li>
     *                          <li>2: Module 2: science fold.</li>
     *                          <li>3: Module 3: second peripheral wavefront sensor.</li>
     *                          <li>4: Module 4: (closest to the sky) first peripheral wavefront sensor.</li>
     *                      </ul>
     *
     * @param calibrationUnit Calibration Unit (GCAL) Lamp Name. There are 4 options:
     *                        <ul>
     *                        <li>Argon</li>
     *                        <li>Xenon</li>
     *                        <li>ThAr</li>
     *                        <li>CuAr</li>
     *                        </ul>
     * @param lightType Light type (for light detector). There are 2 types: Mauna Kea Sky Emission, Cerro Pachon Sky Emission
     * @param telePositionPair A list of tele position pairs that will be issued to control the movements of the telescope.
     * @return The created observing program
     */
    OP createObservingProgram(AbstractSciencePlan sp, String opticsPrimary,
                              double fStop,
                              double opticsSecondaryRMS,
                              double scienceFoldMirrorDegree,
                              OPC.FoldMirrorType scienceFoldMirrorType,
                              int moduleContent,
                              OPC.CalibrationUnit calibrationUnit,
                              OPC.LightType lightType,
                              AbstractTelePositionPair[] telePositionPair,
                              AS so);

    /**
     * Validate the given science plan
     * @param sp the science plan to validate
     * @param so the science observer who validates the science plan
     * @return the validated science plan
     */

    SP validateSciencePlan(AbstractSciencePlan sp, AS so);

    /**
     * Save the observing program to the edu.gemini.OCS system.
     * @param op the observing program to be saved.
     * @return true = success, false = failure
     */
    public boolean saveObservingProgram(AbstractObservingProgram op);

    /**
     * Get the observing program that is associated with the given science plan.
     * @param sp The science plan
     * @return The observing program
     */
    public OP getObservingProgramBySciencePlan(AbstractSciencePlan sp);

    /***
     * Get the default configuration of the Gemini system as a JSON file.
     * @throws IOException
     */
    public void getDefaultConfiguration() throws IOException;

    /***
     * Get the current configuration of the Gemini system as a JSON file
     * @throws IOException
     */
    public void getCurrentConfiguration() throws IOException;

    /***
     * Update the configuration of the Gemini system using a provided configuration file.
     * @return A string indicating the status of the update.
     * @throws FileNotFoundException
     */
    public String updateConfiguration() throws FileNotFoundException;

}
