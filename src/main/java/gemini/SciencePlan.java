package edu.gemini;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.gemini.model.AbstractSciencePlan;

public class SciencePlan extends AbstractSciencePlan {
    private Astronomer creator;
    private List<AstronomicalData> astronomicalDataList;

    // Data processing requirements
    public enum FileType { PNG, JPEG, RAW }
    public enum FileQuality { LOW, FINE }
    public enum ColorType { COLOR, BW }

    private FileType fileType;
    private FileQuality fileQuality;
    private ColorType colorType;
    private Double contrast;
    private Double brightness;
    private Double saturation;
    private Double exposure;

    public SciencePlan() {
        super();
        this.astronomicalDataList = new ArrayList<>();
    }

    public SciencePlan(int planNo, String name, String creator, double funding, String objective,
                      Date startDate, Date endDate, String telescope, String target, STATUS status) {
        super(planNo, name, creator, funding, objective, startDate, endDate, telescope, target, status);
        this.astronomicalDataList = new ArrayList<>();
    }

    // --- แก้ไขส่วนนี้ ---
    // เปลี่ยนชื่อเป็น getCreatorAstronomer เพื่อไม่ให้ชนกับ getCreator() ของแม่ที่เป็น String
    public Astronomer getCreatorAstronomer() {
        return creator;
    }

    public void setCreator(Astronomer creator) {
        this.creator = creator;
        if (creator != null) {
            // ส่งชื่อที่เป็น String ไปเก็บที่แม่ด้วย เพื่อให้ getCreator() ธรรมดาทำงานได้
            super.setCreator(creator.getFirstName() + " " + creator.getLastName());
        }
    }
    // -------------------

    public List<AstronomicalData> getAstronomicalDataList() {
        return astronomicalDataList;
    }

    public void addAstronomicalData(AstronomicalData data) {
        this.astronomicalDataList.add(data);
    }

    public FileType getFileType() {
        return fileType;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }

    public FileQuality getFileQuality() {
        return fileQuality;
    }

    public void setFileQuality(FileQuality fileQuality) {
        this.fileQuality = fileQuality;
    }

    public ColorType getColorType() {
        return colorType;
    }

    public void setColorType(ColorType colorType) {
        this.colorType = colorType;
    }

    public Double getContrast() {
        return contrast;
    }

    public void setContrast(Double contrast) {
        this.contrast = contrast;
    }

    public Double getBrightness() {
        return brightness;
    }

    public void setBrightness(Double brightness) {
        this.brightness = brightness;
    }

    public Double getSaturation() {
        return saturation;
    }

    public void setSaturation(Double saturation) {
        this.saturation = saturation;
    }

    public Double getExposure() {
        return exposure;
    }

    public void setExposure(Double exposure) {
        this.exposure = exposure;
    }

    @Override
    public String toString() {
        return "#" + getPlanNo() +
                " [" + getStatus() + "] " +
                getName() + " (" + getTelescope() + ", " + getTarget() + ")";
    }
}