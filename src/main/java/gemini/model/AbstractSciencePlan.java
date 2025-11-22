package edu.gemini.model;

import java.util.Date;

public abstract class AbstractSciencePlan {
    /**
     * This is the enum indicating the status of a science plan
     * <ul>
     * <li>SAVED = The plan is saved</li>
     * <li>COMPLETE = The plan finishes running</li>
     * <li>TESTED = The plan finished testing</li>
     * <li>CANCELLED = The plan has been canceled</li>
     * <li>RUNNING = The plan is being run in OCS</li>
     * <li>SUBMITTED = The plan has been submitted to OCS</li>
     * <li>VALIDATED = The plan has been validated by the science observer</li>
     * <li>INVALIDATED = The plan has been rejected by the science observer</li>
     * </ul>
     */
    public enum STATUS {
        CREATED,
        COMPLETE,
        TESTED,
        CANCELLED,
        RUNNING,
        SUBMITTED,
        VALIDATED,
        INVALIDATED
    }

    private int planNo;
    private String name;
    private String creator;
    private double funding;
    private String objective;
    private Date startDate;
    private Date endDate;
    private String telescope;
    private String target;
    private STATUS status;

    public AbstractSciencePlan() {
        this.status = STATUS.CREATED;
    }

    public AbstractSciencePlan(int planNo, String name, String creator, double funding, String objective,
                              Date startDate, Date endDate, String telescope, String target, STATUS status) {
        this.planNo = planNo;
        this.name = name;
        this.creator = creator;
        this.funding = funding;
        this.objective = objective;
        this.startDate = startDate;
        this.endDate = endDate;
        this.telescope = telescope;
        this.target = target;
        this.status = status;
    }

    public int getPlanNo() {
        return planNo;
    }

    public void setPlanNo(int planNo) {
        this.planNo = planNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public double getFunding() {
        return funding;
    }

    public void setFunding(double funding) {
        this.funding = funding;
    }

    public String getObjective() {
        return objective;
    }

    public void setObjective(String objective) {
        this.objective = objective;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getTelescope() {
        return telescope;
    }

    public void setTelescope(String telescope) {
        this.telescope = telescope;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public STATUS getStatus() {
        return status;
    }

    public void setStatus(STATUS status) {
        this.status = status;
    }
}