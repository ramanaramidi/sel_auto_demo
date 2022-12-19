package testData;

import commons.constants.Constants;
import commons.objects.Trackers;
import utility.helper.XlHelper;

import java.util.ArrayList;

public class TrackerData {

    private static ArrayList<Trackers> trackerData = new ArrayList<Trackers>();
    private static XlHelper xlsx = new XlHelper(Constants.TRACKER_DATA);

    private TrackerData(){}

    public static ArrayList<Trackers> getInstance(){
        return trackerData;
    }
    public static XlHelper getXlsxInstance(){
        return xlsx;
    }
}
