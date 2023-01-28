package commons.objects;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Por {
    public String programName;
    public String objective;
    public String planStatus;

    public String queueStatus;
    public String porId;
    public String ringID;

    public String getRandomQueueStatus(){
        Random r = new Random();
        List<String> queueStatusList = Arrays.asList(
                "Market Future Planning",
                "Market POR Submission",
                "Region POR Submission",
                "National POR Submission",
                "Waiting For CFO Approval",
                "Exception Request",
                "POR Request Complete");
        return queueStatusList.get(r.nextInt(queueStatusList.size()));
    }

    public String getRandomObjective(){
        Random r = new Random();
        List<String> objectiveList = Arrays.asList("Capacity","Coverage","Coverage&Capacity","Other");
        return objectiveList.get(r.nextInt(objectiveList.size()));
    }

    public String getRandomPlanStatus(){
        Random r = new Random();
        List<String> planStatusList = Arrays.asList("Active");
        return planStatusList.get(r.nextInt(planStatusList.size()));
    }

    public String getRandomProgramName(){
        Random r = new Random();
        List<String> patList = Arrays.asList(
                "5GmmW_Stage 2_Site Mod"
//                "AAS_Regional Capacity_Site Mod",
//                "AAV_Non-Governance Project Creation_Site Mod",
//                "AIR32_Regional Capacity_Site Mod",
//                "AWS Shutdown_Phase 2_Site Mod",
//                "Anchor_Phase 3_Site Mod",
//                "Backhaul Upgrade_ATT 10G Upgrade_Site Mod",
//                "Backhaul Upgrade_Altice Lightpath 10G Upgrade_Site Mod",
//                "Backhaul Upgrade_Altice Suddenlink 10G Upgrade_Site Mod"
                );
        return patList.get(r.nextInt(patList.size()));
    }

    public Por(String programName, String objective, String planStatus, String porId, String queueStatus) {
        this.programName = programName;
        this.objective = objective;
        this.planStatus = planStatus;
        this.porId = porId;
        this.queueStatus = queueStatus;
        this.ringID = null;
    }

    public Por(String programName, String ringId) {
        this.programName = programName;
        this.objective = getRandomObjective();
        this.planStatus = getRandomPlanStatus();
        this.porId = "";
        this.queueStatus = "";
        this.ringID = ringId;
    }

    public Por(String ringID) {
        this.programName = getRandomProgramName();
        this.objective = getRandomObjective();
        this.planStatus = getRandomPlanStatus();
        this.porId = "";
        this.queueStatus = getRandomQueueStatus();
        this.ringID = ringID;
    }
}
