package erland.webapp.dirgallery;

import erland.webapp.common.EntityInterface;
import erland.webapp.common.QueryFilter;
import erland.webapp.common.WebAppEnvironmentInterface;

import java.util.HashMap;
import java.util.Map;

public class DescriptionTagHelper {
    private static DescriptionTagHelper me;
    private WebAppEnvironmentInterface environment;
    private Map map = new HashMap();

    private DescriptionTagHelper() {
    }

    public static DescriptionTagHelper getInstance() {
        if (me == null) {
            me = new DescriptionTagHelper();
        }
        return me;
    }

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public DescriptionTag[] getDescriptionTagList(String entity) {
        DescriptionTag[] tagList = (DescriptionTag[]) map.get(entity);
        if (tagList == null) {
            QueryFilter filter = new QueryFilter("allfortype");
            filter.setAttribute("type", entity);
            EntityInterface[] entities = environment.getEntityStorageFactory().getStorage(entity).search(filter);
            tagList = new DescriptionTag[entities.length];
            for (int i = 0; i < entities.length; i++) {
                tagList[i] = (DescriptionTag) entities[i];
            }
            map.put(entity, tagList);
        }
        return tagList;
    }

    public String getDescription(String entity, String tag) {
        DescriptionTag[] tagList = getDescriptionTagList(entity);
        for (int i = 0; i < tagList.length; i++) {
            DescriptionTag descriptionTag = tagList[i];
            if (descriptionTag.getTag().equals(tag)) {
                return descriptionTag.getDescription();
            }
        }
        return null;
    }
}