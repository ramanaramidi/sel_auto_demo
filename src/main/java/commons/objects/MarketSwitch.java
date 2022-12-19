package commons.objects;

public class MarketSwitch {

    public String market;
    public String recordType;
    public String msmSwitch;
    public String msmSelectable;

    public MarketSwitch(){
        this.market = "SAN FRANCISCO";
        this.recordType = "1_Switch";
        this.msmSelectable = "Yes";
        this.msmSwitch = null;
    }

    public MarketSwitch(String recordType, String market){
        this.market = market;
        this.recordType = recordType;
        this.msmSelectable = "Yes";
        this.msmSwitch = null;
    }

}
