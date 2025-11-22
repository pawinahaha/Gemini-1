package edu.gemini;

import edu.gemini.model.AbstractAstronomicalData;

public class AstronomicalData extends AbstractAstronomicalData {
    private String fileName;
    private String description;

    public AstronomicalData() {
        super();
    }

    public AstronomicalData(String fileName, String description) {
        super();
        this.fileName = fileName;
        this.description = description;
    }

    public AstronomicalData(String fileType, String fileQuality, String colorType,
                           double contrast, double brightness, double saturation,
                           double highlights, double exposure, double shadows,
                           double whites, double blacks, double luminance, double hue,
                           String fileName, String description) {
        super(fileType, fileQuality, colorType, contrast, brightness, saturation,
              highlights, exposure, shadows, whites, blacks, luminance, hue);
        this.fileName = fileName;
        this.description = description;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}