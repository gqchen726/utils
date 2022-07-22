package ga.tianyuge.common;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Date;

/**
 * @author: guoqing.chen01@hand-china.com 2021-09-18 10:30
 **/

//@Entity
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
//@Table(name = "base_entity")
public class BaseEntity {
    @Id
    Long id;
    @Column(name = "creation_date")
    @JsonProperty("creation_date")
    Date creationDate;
    @Column(name = "created_by")
    @JsonProperty("created_by")
    String createdBy;
    @Column(name = "last_update_date")
    @JsonProperty("last_update_date")
    Date lastUpdateDate;
    @Column(name = "last_update_by")
    @JsonProperty("last_update_by")
    String lastUpdatedBy;
    @Column(name = "object_version_number")
    @JsonProperty("object_version_number")
    String objectVersionNumber;
    @Column(name = "domain_id")
    @JsonProperty("domain_id")
    String domainId;
    @Column(name = "domain_path")
    @JsonProperty("domain_path")
    String domainPath;
    @Column(name = "`_token`")
    String _token;

    public BaseEntity() {
    }



    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public String getObjectVersionNumber() {
        return objectVersionNumber;
    }

    public void setObjectVersionNumber(String objectVersionNumber) {
        this.objectVersionNumber = objectVersionNumber;
    }


    public String getDomainId() {
        return domainId;
    }

    public void setDomainId(String domainId) {
        this.domainId = domainId;
    }

    public String getDomainPath() {
        return domainPath;
    }

    public void setDomainPath(String domainPath) {
        this.domainPath = domainPath;
    }



}
