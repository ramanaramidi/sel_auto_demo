package commons.objects;

import commons.enums.SeedingDataTypeEnum;

public class Trackers {
    private SeedingDataTypeEnum dataType;
    private String trackerId;
    private String trackerType;
    private String owner;

    public SeedingDataTypeEnum getDataType() {
        return dataType;
    }

    public String getTrackerId() {
        return trackerId;
    }

    public String getTrackerType() {
        return trackerType;
    }

    public String getOwner() {
        return owner;
    }

    public Trackers(SeedingDataTypeEnum dataType, String trackerId, String trackerType, String owner) {
        this.dataType = dataType;
        this.trackerId = trackerId;
        this.trackerType = trackerType;
        this.owner = owner;
    }


}
