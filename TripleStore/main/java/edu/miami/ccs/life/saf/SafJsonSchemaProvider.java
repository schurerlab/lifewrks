package edu.miami.ccs.life.saf;

import edu.miami.ccs.life.LifeException;
import edu.miami.ccs.life.config.ConfigurationDescription;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import static edu.miami.ccs.life.LifeConstants.*;

/**
 * @author Sam Abyruwan
 * @version 0.1
 *          <p/>
 *          This class provides one-to-one translation from model to SAF object model. This class
 *          represents just the proof-of-concept.
 *          <p/>
 *          The schema is build based on "A JSON Media Type for Describing the Structure and
 *          Meaning of JSON Documents draft-zyp-json-schema-03".
 *          <p/>
 *          Reference: http://tools.ietf.org/html/draft-zyp-json-schema-03
 */
public class SafJsonSchemaProvider {

    private File         xlsxFile;
    private File         safJsonFile;
    private Workbook     workbook;
    private ObjectMapper objectMapper;
    private Logger       log;

    private final static int safHeadersIndex             = 0;
    private final static int safJasonScalarElementsIndex = 1;
    private final static int safJsonVectorElementsIndex  = 2;

    private Map<String, SafJsonScalarObjectModel> scalarObjectModelMap =
            new HashMap<String, SafJsonScalarObjectModel>();
    private Map<String, SafJsonVectorObjectModel> vectorObjectModelMap =
            new HashMap<String, SafJsonVectorObjectModel>();

    public SafJsonSchemaProvider(ConfigurationDescription configurationDescription, Logger log) {
        this.xlsxFile = new File(configurationDescription.getXlsxFile());
        this.safJsonFile = new File(configurationDescription.getSafJsonFile());
        this.log = log;
    }

    public SafJsonSchemaProvider(File xlsxFile, File safJsonFile) {
        this.xlsxFile = xlsxFile;
        this.safJsonFile = safJsonFile;
        log = Logger.getLogger(getClass().getName());
        log.setLevel(Level.ALL);
        try {
            FileHandler handler = new FileHandler();
            handler.setFormatter(new SimpleFormatter());
            log.addHandler(handler);
        } catch (IOException e) {
            System.err.println("ERROR! when creating log file handler!");
        }
    }

    /**
     * Read the model file and load it into the Json model, then serialize it.
     */
    public boolean execute() throws LifeException, IOException {
        // Read
        workbook = new XSSFWorkbook(new FileInputStream(xlsxFile));
        objectMapper = new ObjectMapper();
        Sheet sheet = workbook.getSheetAt(safHeadersIndex);

        Map<String, Object> jsonSchema = new HashMap<String, Object>();
        for (Iterator<Row> iterator = sheet.rowIterator(); iterator.hasNext(); ) {
            Row row = iterator.next();
            int rowNum = row.getRowNum();
            log.info("sheet=" + safHeadersIndex + " rowNum=" + rowNum);
            if (rowNum == 0)
                continue;
            Cell cellA = row.getCell(CellReference.convertColStringToIndex(COL_A));
            Cell cellB = row.getCell(CellReference.convertColStringToIndex(COL_B));
            if (cellA != null && cellA.getCellType() != Cell.CELL_TYPE_BLANK && cellB != null &&
                    cellB.getCellType() == Cell.CELL_TYPE_NUMERIC)
                jsonSchema.put(cellA.getStringCellValue(), cellB.getNumericCellValue());
            if (cellA != null && cellA.getCellType() != Cell.CELL_TYPE_BLANK && cellB != null &&
                    cellB.getCellType() == Cell.CELL_TYPE_STRING)
                jsonSchema.put(cellA.getStringCellValue(), cellB.getStringCellValue());

        }

        sheet = workbook.getSheetAt(safJasonScalarElementsIndex);
        for (Iterator<Row> iterator = sheet.rowIterator(); iterator.hasNext(); ) {
            Row row = iterator.next();
            int rowNum = row.getRowNum();
            log.info("sheet=" + safJasonScalarElementsIndex + " rowNum=" + rowNum);
            if (rowNum == 0)
                continue;

            SafJsonScalarObjectModel safJsonScalarObject = new SafJsonScalarObjectModel();
            // required
            Cell cell = row.getCell(CellReference.convertColStringToIndex(COL_A));
            if (cell != null && cell.getCellType() == Cell.CELL_TYPE_BOOLEAN)
                safJsonScalarObject.setRequired(cell.getBooleanCellValue());

            // saf id
            cell = row.getCell(CellReference.convertColStringToIndex(COL_B));
            if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK)
                safJsonScalarObject.setSafId(cell.getStringCellValue().trim());

            // saf type
            cell = row.getCell(CellReference.convertColStringToIndex(COL_C));
            if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK)
                safJsonScalarObject.setSafType(cell.getStringCellValue().trim());

            // saf description
            cell = row.getCell(CellReference.convertColStringToIndex(COL_D));
            if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK)
                safJsonScalarObject.setSafDescription(cell.getStringCellValue().trim());

            // owl type
            cell = row.getCell(CellReference.convertColStringToIndex(COL_E));
            if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK)
                safJsonScalarObject.setOwlType(cell.getStringCellValue().trim());

            // bao label
            cell = row.getCell(CellReference.convertColStringToIndex(COL_F));
            if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK)
                safJsonScalarObject.setBaoLabel(cell.getStringCellValue().trim());

            // examples
            cell = row.getCell(CellReference.convertColStringToIndex(COL_F));
            if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK)
                safJsonScalarObject.setExamples(cell.getStringCellValue().trim());

            if (scalarObjectModelMap.containsKey(safJsonScalarObject.getSafId())) {
                log.warning("ERROR! the safId=" + safJsonScalarObject.getSafType() +
                        " already exists!");
                return false;
            }
            scalarObjectModelMap.put(safJsonScalarObject.getSafId(), safJsonScalarObject);

        }

        sheet = workbook.getSheetAt(safJsonVectorElementsIndex);
        String tmpGroupName = null;
        for (Iterator<Row> iterator = sheet.rowIterator(); iterator.hasNext(); ) {
            Row row = iterator.next();
            int rowNum = row.getRowNum();
            log.info("sheet=" + safJsonVectorElementsIndex + " rowNum=" + rowNum);
            if (rowNum == 0)
                continue;

            Cell cell = row.getCell(CellReference.convertColStringToIndex(COL_A));
            if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK) {
                tmpGroupName = cell.getStringCellValue().trim();
                if (vectorObjectModelMap.containsKey(tmpGroupName)) {
                    log.warning("ERROR! the vectorElement=" + tmpGroupName + " already exists!");
                    return false;
                }
                SafJsonVectorObjectModel safJsonVectorObject = new SafJsonVectorObjectModel();
                safJsonVectorObject.setGroupName(tmpGroupName);
                cell = row.getCell(CellReference.convertColStringToIndex(COL_C));
                if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK)
                    safJsonVectorObject.setDescription(cell.getStringCellValue().trim());
                vectorObjectModelMap.put(tmpGroupName, safJsonVectorObject);
                cell = row.getCell(CellReference.convertColStringToIndex(COL_D));
                if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK)
                    safJsonVectorObject.setType(cell.getStringCellValue().trim());
            }
            cell = row.getCell(CellReference.convertColStringToIndex(COL_B));
            if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK) {
                if (cell.getStringCellValue().startsWith("&")) {    // This is a reference
                    SafJsonScalarObjectModel safJsonScalarObjectModel =
                            scalarObjectModelMap.get(cell.getStringCellValue().trim().substring(1));
                    if (safJsonScalarObjectModel != null)
                        vectorObjectModelMap.get(tmpGroupName)
                                .addRefScalarElement(safJsonScalarObjectModel.getSafId());
                    else
                        throw new LifeException(
                                "The referenced element is not found=" + cell.getStringCellValue());
                } else {
                    String cellName = cell.getStringCellValue().trim();
                    // TODO: only list type for the time being
                    cell = row.getCell(CellReference.convertColStringToIndex(COL_F));
                    if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK) {
                        String values = cell.getStringCellValue().trim();
                        String[] tokens = values.split(",");
                        for (String token : tokens)
                            vectorObjectModelMap.get(tmpGroupName)
                                    .addListSubtypeElement(cellName, token);
                    }
                }

            }
        }


        // scalars
        Map<String, Object> properties = new HashMap<String, Object>();
        for (SafJsonScalarObjectModel modelDataScalar : scalarObjectModelMap.values()) {
            Map<String, String> jsonPropertiesData = new HashMap<String, String>();
            jsonPropertiesData.put(REQUIRED, Boolean.toString(modelDataScalar.isRequired()));
            jsonPropertiesData.put(TYPE, modelDataScalar.getSafType());
            if (modelDataScalar.getSafDescription() != null)
                jsonPropertiesData.put(DESCRIPTION, modelDataScalar.getSafDescription());

            properties.put(modelDataScalar.getSafId(), jsonPropertiesData);
        }

        // vector
        for (SafJsonVectorObjectModel modelDataVector : vectorObjectModelMap.values()) {
            Map<String, Object> jsonPropertiesData = new HashMap<String, Object>();
            if (modelDataVector.getDescription() != null)
                jsonPropertiesData.put(DESCRIPTION, modelDataVector.getDescription());
            jsonPropertiesData.put(TYPE, modelDataVector.getType());
            // reference elements
            List<Object> references = new ArrayList<Object>();
            /*for (String ref : modelDataVector.getRefScalarElements()) {
                Map<String, String> refs = new HashMap<String, String>();
                refs.put(REF, "{" + ref + "}");
                references.add(refs);
            }
            jsonPropertiesData.put(REFERENCES, references);
            */
            for (String ref : modelDataVector.getRefScalarElements())
                references.add("{" + ref + "}");
            jsonPropertiesData.put(REF, references);
            // list elements
            for (String key : modelDataVector.getListSubtypeElements().keySet())
                jsonPropertiesData.put(key, modelDataVector.getListSubtypeElements().get(key));
            properties.put(modelDataVector.getGroupName(), jsonPropertiesData);
        }

        jsonSchema.put(PROPERTIES, properties);

        //serialize
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(safJsonFile, jsonSchema);

        return true; // success status
    }
}
