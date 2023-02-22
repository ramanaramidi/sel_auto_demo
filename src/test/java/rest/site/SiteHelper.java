package rest.site;

import com.jayway.jsonpath.JsonPath;
import commons.objects.ApiResponse;
import commons.objects.Ring;
import commons.objects.Site;
import rest.ring.RingHelper;

public class SiteHelper {
    RingHelper ringHelper = new RingHelper();

    public Site createNewRingAndSite(Ring ring, Site site){
        //Ring ring = new Ring("Active","AU" + MiscHelpers.getRandomString(5, true).toUpperCase(),"Macro");
        ring = ringHelper.createAnActiveRing(ring);
        System.out.println("ACTIVE_RING_ID "+ring.ringId);
        if(ring!=null){
            site.ringId = ring.ringId;
            site = createNewSite(site);
            if(site!=null)
                return site;
            else
                return null;
        }
        return null;
    }

    public Site createActiveRingNewSite(Ring ring, Site site){
        ring = ringHelper.createAnActiveRing(ring);
        System.out.println("ACTIVE_RING_ID "+ring.ringId);
        if(ring!=null){
            site.ringId = ring.ringId;
            site = createNewSite(site);
            if(site!=null)
                return site;
            else
                return null;
        }
        return null;
    }

    public Site createActiveRingAndSite(Ring ring, Site site){
        //Ring ring = new Ring("Active","AU" + MiscHelpers.getRandomString(5, true).toUpperCase(),"Macro");
        ring = ringHelper.createAnActiveRing(ring);
        System.out.println("ACTIVE_RING_ID "+ring.ringId);
        if(ring!=null){
            site.ringId = ring.ringId;
            site = createActiveSite(site);
            if(site!=null)
                return site;
            else
                return null;
        }
        return null;
    }



    public Site createActiveRingAndPrimaryActiveSite(Ring ring, Site site){
        ring = ringHelper.createAnActiveRing(ring);
        System.out.println("ACTIVE_RING_ID "+ring.ringId);
        if(ring!=null){
            site.ringId = ring.ringId;
            site = createPrimaryActiveSite(site);
            if(site!=null)
                return site;
            else
                return null;
        }
        return null;
    }

    public ApiResponse getSiteTrackerDetailsBySiteIdAndCustomFields(String siteId , String fields){
        SiteService siteService = new SiteService();
        ApiResponse response = siteService.getSiteTrackerDetailsBySiteIdAndCustomFields(siteId,fields);
        if(response.responseCode==200){
            return response;
        }
        return null;
    }

    public Site createNewSiteWithoutGeoLocation(Site site){
        SiteService siteService = new SiteService();
        ApiResponse response = siteService.createNewSiteWithoutGeoLocation(site);
        if(!response.isError){
            return site;
        }
        return null;
    }

    public Site createActiveSite(Site site){
        SiteService siteService = new SiteService();
        ApiResponse response = siteService.createNewSite(site);
        if(!response.isError){
            response = siteService.updateRingToActivateForMacroRing(site);
            if(!response.isError)
                return site;
            return null;
        }
        return null;
    }

    public Site updateToPrimaryActiveSite(Site site){
        SiteService siteService = new SiteService();
        ApiResponse response = siteService.updateSiteToActivateWithPrimary(site);
        if(!response.isError)
            return site;
        return null;
    }
    public Site updateSiteForFops(Site site) {
        SiteService siteService = new SiteService();
        ApiResponse response = siteService.updateSiteForFops(site);
        if (!response.isError) return site;
        return null;
    }

    public Site createPrimaryActiveSite(Site site){
        SiteService siteService = new SiteService();
        ApiResponse response = siteService.createNewSite(site);
        if(!response.isError){
            response = siteService.updateSiteToActivateWithPrimary(site);
            response = siteService.updateSiteToActivateWithPrimary(site);
            if(!response.isError)
                return site;
            return null;
        }
        return null;
    }


    public Site createNewSite(Site site){
        SiteService siteService = new SiteService();
        ApiResponse response = siteService.createNewSite(site);
        if(!response.isError){
            site.trackerId = JsonPath.read(response.responseBody, "TRACKOR_ID");
            return site;
        }

        return null;
    }
}
