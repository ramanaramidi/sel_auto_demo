package rest.project;

import com.jayway.jsonpath.JsonPath;
import commons.constants.Constants;
import commons.enums.SeedingDataTypeEnum;
import commons.objects.*;
import java.util.List;
import rest.por.PorHelper;
import rest.site.SiteHelper;
import testData.DataCollector;

public class ProjectHelper {

    SiteHelper siteHelper = new SiteHelper();
    PorHelper porHelper = new PorHelper();

    public String deleteProjectTracker(String projectId) {
        String[] projectCode = projectId.split("-");
        System.out.println("Site Code:: " + projectCode[0]);
        ProjectService projectService = new ProjectService();
        ApiResponse response = projectService.getProjectTrackerIdsForSite(
                projectCode[0]
        );
        if (response.responseCode == 200) {
            List<Integer> trackerKeys = JsonPath.read(
                    response.responseBody,
                    "[?(@.TRACKOR_KEY== '" + projectId + "')].TRACKOR_ID"
            );
            for (Integer trackor : trackerKeys) {
                System.out.println("DELETING TRACKER ID:: " + trackor);
                response = projectService.deleteProjectTracker(trackor.toString());
            }
            if (response.responseCode == 200) return "PASS"; else return null;
        }
        return null;
    }

    public Project getActiveProject(
            boolean isForceSeeding,
            Ring ring,
            Site site,
            Por por
    ) {
        if (isForceSeeding) {
            return createActiveProjectFromScratch(ring, site, por);
        } else {
            Trackers trackerData = DataCollector.getTrackerData(
                    SeedingDataTypeEnum.ActiveProject
            );
            if (trackerData != null) {
                String projectId = trackerData.getTrackerId();
                String[] siteCode = projectId.split("-");
                System.out.println("Site Code:: " + siteCode[0]);
                ApiResponse response = siteHelper.getSiteTrackerDetailsBySiteIdAndCustomFields(
                        siteCode[0],
                        "RingTracker.xitor_key"
                );
                if (response != null) {
                    String ringId = JsonPath.read(
                            response.responseBody,
                            "[0].['RingTracker.TRACKOR_KEY']"
                    );
                    // TODO - get details on how to find the POR data to store in POR object
                    return new Project(projectId, "", siteCode[0], ringId, null);
                }
                return null;
            } else {
                return createActiveProjectFromScratch(ring, site, por);
            }
        }
    }

    public Project getActiveProject(
            boolean isForceSeeding,
            Ring ring,
            Site site
    ) {
        if (isForceSeeding) {
            return createActiveProjectFromScratch(ring, site);
        } else {
            Trackers trackerData = DataCollector.getTrackerData(
                    SeedingDataTypeEnum.ActiveProject
            );
            if (trackerData != null) {
                String projectId = trackerData.getTrackerId();
                String[] siteCode = projectId.split("-");
                System.out.println("Site Code:: " + siteCode[0]);
                ApiResponse response = siteHelper.getSiteTrackerDetailsBySiteIdAndCustomFields(
                        siteCode[0],
                        "RingTracker.xitor_key"
                );
                if (response != null) {
                    String ringId = JsonPath.read(
                            response.responseBody,
                            "[0].['RingTracker.TRACKOR_KEY']"
                    );
                    // TODO - get details on how to find the POR data to store in POR object
                    return new Project(projectId, "", siteCode[0], ringId, null);
                }
                return null;
            } else {
                return createActiveProjectFromScratch(ring, site);
            }
        }
    }

    public Project createActiveProjectFromScratch(Ring ring, Site site) {
        ProjectService projectService = new ProjectService();
        site = siteHelper.createActiveRingAndPrimaryActiveSite(ring, site);
        Por porData = new Por(site.ringId);
        porData = porHelper.createAPor(porData);
        porData.queueStatus = "POR Request Complete";
        porHelper.updatePorWithQueueStatus(porData);

        ApiResponse response = projectService.getProjectTrackerIdsForSite(
                site.siteId
        );
        if (response.responseCode == 200) {
            String trackerKey = JsonPath.read(
                    response.responseBody,
                    "[0].TRACKOR_KEY"
            );
            Project project = new Project(
                    trackerKey,
                    porData.porId,
                    site.siteId,
                    ring.ringId,
                    porData
            );

            if (project.projectId != null) {
                response = projectService.activateProject(project.projectId);
                if (response.responseCode == 200) {
                    int trackerId = JsonPath.read(response.responseBody, "TRACKOR_ID");
                    project.trackerId = trackerId;
                    return project;
                } else {
                    project.status = "Activation ERROR";
                    return project;
                }
            }
            return null;
        }
        return null;
    }

    public Project createActiveProjectFromScratch(
            Ring ring,
            Site site,
            Por porData
    ) {
        ProjectService projectService = new ProjectService();
        site = siteHelper.createActiveRingAndPrimaryActiveSite(ring, site);
        // Por porData = new Por(site.ringId);
        porData = porHelper.createAPor(porData);
        porData.queueStatus = "POR Request Complete";
        porHelper.updatePorWithQueueStatus(porData);

        ApiResponse response = projectService.getProjectTrackerIdsForSite(
                site.siteId
        );
        if (response.responseCode == 200) {
            String trackerKey = JsonPath.read(
                    response.responseBody,
                    "[0].TRACKOR_KEY"
            );
            Project project = new Project(
                    trackerKey,
                    porData.porId,
                    site.siteId,
                    ring.ringId,
                    porData
            );
            if (project.projectId != null) {
                response = projectService.activateProject(project.projectId);
                if (response.responseCode == 200) {
                    int trackerId = JsonPath.read(response.responseBody, "TRACKOR_ID");
                    project.trackerId = trackerId;
                    return project;
                } else {
                    project.status = "Activation ERROR";
                    return project;
                }
            }
            return null;
        }
        return null;
    }

    public Project getNewProject(boolean isForceSeeding, Ring ring, Site site) {
        if (isForceSeeding) {
            return createNewProjectFromScratch(ring, site);
        } else {
            Trackers trackerData = DataCollector.getTrackerData(
                    SeedingDataTypeEnum.NewProject
            );
            if (trackerData != null) {
                String projectId = trackerData.getTrackerId();
                String[] siteCode = projectId.split("-");
                System.out.println("Site Code:: " + siteCode[0]);
                ApiResponse response = siteHelper.getSiteTrackerDetailsBySiteIdAndCustomFields(
                        siteCode[0],
                        "RingTracker.xitor_key"
                );
                if (response != null) {
                    String ringId = JsonPath.read(
                            response.responseBody,
                            "[0].['RingTracker.TRACKOR_KEY']"
                    );
                    return new Project(projectId, "", siteCode[0], ringId, null);
                }
                return null;
            } else {
                return createNewProjectFromScratch(ring, site);
            }
        }
    }

    public Project createNewProjectFromScratch(Ring ring, Site site) {
        ProjectService projectService = new ProjectService();
        site = siteHelper.createActiveRingAndPrimaryActiveSite(ring, site);
        Por porData = new Por(site.ringId);
        porData = porHelper.createAPor(porData);
        porData.queueStatus = "POR Request Complete";
        porHelper.updatePorWithQueueStatus(porData);

        ApiResponse response = projectService.getProjectTrackerIdsForSite(
                site.siteId
        );
        if (response.responseCode == 200) {
            String trackerKey = JsonPath.read(
                    response.responseBody,
                    "[0].TRACKOR_KEY"
            );
            int trackerId = JsonPath.read(response.responseBody, "[0].TRACKOR_ID");
            Project project = new Project(
                    trackerKey,
                    porData.porId,
                    site.siteId,
                    ring.ringId,
                    porData
            );
            project.trackerId = trackerId;
            if (project.projectId != null) return project; else return null;
        }
        return null;
    }

    public String getProjectTrackerIdsAndDeleteAll(String projectId) {
        String[] projectCode = projectId.split("-");
        System.out.println("Site Code:: " + projectCode[0]);
        ProjectService projectService = new ProjectService();
        ApiResponse response = projectService.getProjectTrackerIdsForSite(
                projectCode[0]
        );
        if (response.responseCode == 200) {
            List<Integer> trackerKeys = JsonPath.read(
                    response.responseBody,
                    "[*].TRACKOR_ID"
            );
            for (Integer trackor : trackerKeys) {
                System.out.println("DELETING TRACKER ID:: " + trackor);
                response = projectService.deleteProjectTracker(trackor.toString());
            }
            if (response.responseCode == 200) return "PASS"; else return null;
        }
        return null;
    }

    public boolean uploadDocument(
            String trackerId,
            String field,
            String path,
            String fileName
    ) {
        ProjectService projectService = new ProjectService();
        ApiResponse response = projectService.uploadNTPFile(
                trackerId,
                field,
                path,
                fileName
        );
        if (response.responseCode == 200) return true; else return false;
    }

    public Project createNewProjectForExistingPor(Por porData, String siteId) {
        ProjectService projectService = new ProjectService();
        porData.queueStatus = "POR Request Complete";
        porHelper.updatePorWithQueueStatus(porData);
        System.out.println("site" + siteId);
        ApiResponse response = projectService.getProjectTrackerIdsForSite(siteId);
        while (response.responseBody.equals("[]")) {
            response = projectService.getProjectTrackerIdsForSite(siteId);
        }

        if (response.responseCode == 200) {
            String trackerKey = JsonPath.read(
                    response.responseBody,
                    "[0].TRACKOR_KEY"
            );
            int trackerId = JsonPath.read(response.responseBody, "[0].TRACKOR_ID");
            Project project = new Project(
                    trackerKey,
                    porData.porId,
                    siteId,
                    porData.ringID,
                    porData
            );
            project.trackerId = trackerId;
            if (project.projectId != null) return project; else return null;
        }
        return null;
    }

    public Project createActiveProjectForExistingPor(Por porData, String siteId) {
        ProjectService projectService = new ProjectService();
        porData.queueStatus = "POR Request Complete";
        porHelper.updatePorWithQueueStatus(porData);
        System.out.println("site" + siteId);
        ApiResponse response = projectService.getProjectTrackerIdsForSite(siteId);
        while (response.responseBody.equals("[]")) {
            response = projectService.getProjectTrackerIdsForSite(siteId);
        }

        if (response.responseCode == 200) {
            String trackerKey = JsonPath.read(
                    response.responseBody,
                    "[0].TRACKOR_KEY"
            );
            int trackerId = JsonPath.read(response.responseBody, "[0].TRACKOR_ID");
            Project project = new Project(
                    trackerKey,
                    porData.porId,
                    siteId,
                    porData.ringID,
                    porData
            );
            project.trackerId = trackerId;

            if (project.projectId != null) {
                response = projectService.activateProject(project.projectId);
                if (response.responseCode == 200) {
                    trackerId = JsonPath.read(response.responseBody, "TRACKOR_ID");
                    project.trackerId = trackerId;
                    return project;
                } else {
                    project.status = "Activation ERROR";
                    return project;
                }
            }
            return null;
        }
        return null;
    }

    public Project sendToPierProject(Project project) {
        ProjectService projectService = new ProjectService();
        ApiResponse response = projectService.sendToPier(project.projectId);
        if (response.responseCode == 200) {
            return project;
        }
        return null;
    }
}
