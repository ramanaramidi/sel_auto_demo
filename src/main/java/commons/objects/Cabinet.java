package commons.objects;

public class Cabinet {
    public String cabinetId;
    public String cabinetEquipmentId;
    public String siteId;

    public String string;
    public String manufacture;
    public String model;
    public String type;

    public Cabinet(String cabinetId, String cabinetEquipmentId, String siteId, String string, String manufacture, String model, String type) {
        this.cabinetId = cabinetId;
        this.cabinetEquipmentId = cabinetEquipmentId;
        this.siteId = siteId;
        this.string = string;
        this.manufacture = manufacture;
        this.model = model;
        this.type = type;
    }

    public Cabinet(String cabinetId, String cabinetEquipmentId, String siteId, String type) {
        this.cabinetId = cabinetId;
        this.cabinetEquipmentId = cabinetEquipmentId;
        this.siteId = siteId;
        this.string = "299";
        this.manufacture = "GNB";
        this.model = "1253";
        this.type = type;
    }

    public String getCabinetId() {
        return cabinetId;
    }

    public void setCabinetId(String cabinetId) {
        this.cabinetId = cabinetId;
    }

    public String getCabinetEquipmentId() {
        return cabinetEquipmentId;
    }

    public void setCabinetEquipmentId(String cabinetEquipmentId) {
        this.cabinetEquipmentId = cabinetEquipmentId;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public String getManufacture() {
        return manufacture;
    }

    public void setManufacture(String manufacture) {
        this.manufacture = manufacture;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
