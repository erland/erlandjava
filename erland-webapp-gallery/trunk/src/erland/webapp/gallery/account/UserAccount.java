package erland.webapp.gallery.account;

import erland.webapp.common.EntityInterface;
import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.common.BaseEntity;

public class UserAccount extends BaseEntity {
    private String username;
    private String description;
    private String welcomeText;
    private String logo;
    private Boolean official;
    private Integer defaultGallery;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getOfficial() {
        return official;
    }

    public void setOfficial(Boolean official) {
        this.official = official;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getWelcomeText() {
        return welcomeText;
    }

    public void setWelcomeText(String welcomeText) {
        this.welcomeText = welcomeText;
    }

    public Integer getDefaultGallery() {
        return defaultGallery;
    }

    public void setDefaultGallery(Integer defaultGallery) {
        this.defaultGallery = defaultGallery;
    }
}
