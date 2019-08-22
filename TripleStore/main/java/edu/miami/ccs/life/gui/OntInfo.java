package edu.miami.ccs.life.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 */

public class OntInfo {
    private Map<String, String> infoMap = new ConcurrentHashMap<String, String>();

    private List<String> ns = new ArrayList<String>();

    public boolean addNs(String ns) {
        return this.ns.add(ns);
    }

    public List<String> getNs() {
        return ns;
    }

    public void put(String label, String concept) {
        if (label == null || label.length() == 0) {
            return;
        }
        if (!infoMap.keySet().contains(label)) {
            infoMap.put(label, concept);
        }
    }

    public List<String> displayList() {
        List<String> dl = new ArrayList<String>();
        for (Map.Entry<String, String> entry : infoMap.entrySet()) {
            dl.add(entry.getKey() + ":" + entry.getValue());
        }
        return dl;
    }
}
