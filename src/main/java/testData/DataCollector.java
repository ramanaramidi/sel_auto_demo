package testData;

import commons.enums.SeedingDataTypeEnum;
import commons.objects.Trackers;
import utility.helper.XlHelper;

import java.util.ArrayList;
import java.util.Iterator;

public class DataCollector {


//    public ArrayList<TrackerData> getDataFromExcel(String path){
//        XlHelper xlsx = new XlHelper(path);
//    }

    public void collectSeededData(Trackers data){
        ArrayList<Trackers> dataObject = TrackerData.getInstance();
        dataObject.add(data);
    }

    public static void writeTrackerDataToExcel(ArrayList<Trackers> dataObject){
        XlHelper xlsx = TrackerData.getXlsxInstance();
        setTrackerHeader(xlsx);
        System.out.print( "headder added ");
        for(int row =0; row < dataObject.size() ; row++){
            System.out.print(dataObject.get(row) + " ");
            xlsx.setCellData("Tracker",0,row+2, Integer.toString(row));
            xlsx.setCellData("Tracker",1,row+2,dataObject.get(row).getTrackerId());
            xlsx.setCellData("Tracker",2,row+2,dataObject.get(row).getDataType().toString());
            xlsx.setCellData("Tracker",3,row+2,dataObject.get(row).getTrackerType());
            xlsx.setCellData("Tracker",4,row+2,dataObject.get(row).getOwner());
        }
    }

    public static Trackers getTrackerData(SeedingDataTypeEnum dataType){
        ArrayList<Trackers> dataObject = TrackerData.getInstance();
        System.out.println("SIZE OF DATA BEFORE USE :: " + dataObject.size());
        if(!dataObject.isEmpty()){
            Iterator<Trackers> itr = dataObject.iterator();
            while (itr.hasNext()){
                Trackers tracker = itr.next();
                if(tracker.getDataType()==dataType){
                    System.out.println("FOUND THE DATA:: WILL BE DELETING SINCE ITS SINGLE USE");
                    itr.remove();
                    System.out.println("SIZE OF DATA AFTER USE :: " + dataObject.size());
                    return new Trackers(tracker.getDataType(),tracker.getTrackerId(),tracker.getTrackerType(),tracker.getOwner());
                }
            }
        }
        System.out.println("THERE IS NO TEST DATA PLEASE GENERATE DATA");
        return null;
    }

    public static void readTrackerDataFromExcel(){
        emptyTheTrackerList();
        ArrayList<Trackers> dataObject = TrackerData.getInstance();
        XlHelper xlsx = TrackerData.getXlsxInstance();
        int rows = xlsx.getRowCount("Tracker");
        if(rows>1) {
            for (int row = 0; row < rows; row++) {
                String trackerId = xlsx.getCellData("Tracker", "tracker_id", row + 2);
                String dataType = xlsx.getCellData("Tracker", "data_type", row + 2);
                String trackerType = xlsx.getCellData("Tracker", "tracker_type", row + 2);
                String owner = xlsx.getCellData("Tracker", "owner", row + 2);
                SeedingDataTypeEnum data_type = getSeedingDataType(dataType);
                if (data_type != null) {
                    Trackers tracker = new Trackers(data_type, trackerId, trackerType, owner);
                    dataObject.add(tracker);
                }
            }
        }
    }

    public static void emptyTheTrackerList(){
        ArrayList<Trackers> dataObject = TrackerData.getInstance();
        Iterator<Trackers> itr = dataObject.iterator();
        while (itr.hasNext()){
            itr.remove();
        }
        System.out.println("Tracker Data has been reset");
    }


    private static void setTrackerHeader(XlHelper xlsx) {
        xlsx.setCellData("Tracker",0,1,"pk_id");
        xlsx.setCellData("Tracker",1,1,"tracker_id");
        xlsx.setCellData("Tracker",2,1,"data_type");
        xlsx.setCellData("Tracker",3,1,"tracker_type");
        xlsx.setCellData("Tracker",4,1,"owner");
    }

    public static SeedingDataTypeEnum getSeedingDataType(String dataType){
        switch (dataType){
            case "NewRing": return SeedingDataTypeEnum.NewRing;
            case "ActiveRing": return SeedingDataTypeEnum.ActiveRing;
            case "ActiveSite": return SeedingDataTypeEnum.ActiveSite;
            case "NewSite": return SeedingDataTypeEnum.NewSite;
            default: return null;
        }
    }

    public static Boolean RecreateTheTestSheet(){
        XlHelper xlsx = TrackerData.getXlsxInstance();
        xlsx.addSheet("temp");
        xlsx.removeSheet("Tracker");
        xlsx.addSheet("Tracker");
        xlsx.removeSheet("temp");
        return xlsx.isSheetExist("Tracker");
    }

}
