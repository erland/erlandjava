package erland.webapp.diary;

import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.common.EntityInterface;
import erland.webapp.common.QueryFilter;

import java.util.Map;
import java.util.HashMap;

public class DescriptionIdHelper {
    private static DescriptionIdHelper me;
    private WebAppEnvironmentInterface environment;
    private Map map = new HashMap();

    private DescriptionIdHelper() {}

    public static DescriptionIdHelper getInstance() {
        if(me==null) {
            me = new DescriptionIdHelper();
        }
        return me;
    }

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }
    public DescriptionId[] getDescriptionIdList(String entity) {
        DescriptionId[] idList = (DescriptionId[]) map.get(entity);
        if(idList==null) {
            EntityInterface[] entities = environment.getEntityStorageFactory().getStorage(entity).search(new QueryFilter("all"));
            idList = new DescriptionId[entities.length];
            for (int i = 0; i < entities.length; i++) {
                idList[i] = (DescriptionId) entities[i];
            }
            map.put(entity,idList);
        }
        return idList;
    }

    public String getDescription(String entity, Integer id) {
        DescriptionId[] idList = getDescriptionIdList(entity);
        for (int i = 0; i < idList.length; i++) {
            DescriptionId descriptionId = idList[i];
            if(descriptionId.getId().equals(id)) {
                return descriptionId.getDescription();
            }
        }
        return null;
    }
}
