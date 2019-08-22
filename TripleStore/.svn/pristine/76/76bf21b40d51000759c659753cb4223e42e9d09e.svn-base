package edu.miami.ccs.life.read;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * @author Sam Abeyruwan
 * @version 0.1
 *          <p/>
 *          CSV data object
 */
public class Csv {

    private Map<String, Integer>       headerToIndex = new HashMap<String, Integer>();
    private Map<Integer, List<String>> dataStore     = new HashMap<Integer, List<String>>();


    public void read(InputStream in, char separator) throws Exception {
        CsvReader reader = new CsvReader(new InputStreamReader(in), separator);
        boolean isHeaderRead = false;
        String[] nextLine;
        while ((nextLine = reader.readNext()) != null) {

            // read the header values
            if (!isHeaderRead) {
                isHeaderRead = true;
                for (int i = 0; i < nextLine.length; i++)
                    headerToIndex.put(nextLine[i], i);
                continue;
            }

            // read all the data values
            List<String> data = new ArrayList<String>();
            Collections.addAll(data, nextLine);
            dataStore.put(dataStore.size(), data);
        }

    }

    public Map<String, Integer> getHeaderToIndex() {
        return headerToIndex;
    }

    public Map<Integer, List<String>> getDataStore() {
        return dataStore;
    }
}
