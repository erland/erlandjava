package erland.webapp.diary.appendix;

import erland.webapp.common.EntityInterface;
import erland.webapp.common.WebAppEnvironmentInterface;

public class AppendixEntry implements EntityInterface {
    private WebAppEnvironmentInterface environment;
    private Integer id;
    private String name;
    private String description;

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
