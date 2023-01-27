package commons.objects;

import org.apache.xmlbeans.impl.xb.xsdschema.Public;

public class Project {

    public String projectId;
    public String porId;
    public String siteId;
    public String ringId;
    public String status;
    public String projectType;
    public Integer trackerId;
    public Por por;

    public Project(
            String projectId,
            String porId,
            String siteId,
            String ringId,
            Por por
    ) {
        this.projectId = projectId;
        this.porId = porId;
        this.siteId = siteId;
        this.ringId = ringId;
        this.status = "new";
        this.por = por;
        this.projectType = "B-MW";
    }

    public Project(
            String projectId

    ) {
        this.projectId = projectId;
        this.porId = null;
        this.siteId = null;
        this.ringId = null;
        this.status = "new";
        this.por = null;
        this.projectType = "B-MW";
    }
}
