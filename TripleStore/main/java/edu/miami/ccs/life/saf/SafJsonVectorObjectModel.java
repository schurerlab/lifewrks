package edu.miami.ccs.life.saf;

import edu.miami.ccs.life.LifeException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Sam Abeyruwan
 * @version 0.1
 */
public class SafJsonVectorObjectModel {
    private String                    groupName;
    private String                    type;
    private String                    description;
    private List<String>              refScalarElements;
    private Map<String, List<String>> listSubtypeElements;

    public SafJsonVectorObjectModel() {
        refScalarElements = new ArrayList<String>();
        listSubtypeElements = new HashMap<String, List<String>>();
        this.type = "object";
    }

    public String getGroupName() {
        return groupName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, List<String>> getListSubtypeElements() {
        return listSubtypeElements;
    }

    public void addListSubtypeElement(String key, String value) {
        if (!listSubtypeElements.containsKey(key))
            listSubtypeElements.put(key, new ArrayList<String>());
        listSubtypeElements.get(key).add(value);
    }

    public void addRefScalarElement(String refScalarElement) throws LifeException {
        if (!refScalarElements.contains(refScalarElement))
            refScalarElements.add(refScalarElement);
        else
            throw new LifeException("The reference element already exists=" + refScalarElement);
    }

    public List<String> getRefScalarElements() {
        return refScalarElements;
    }
}
