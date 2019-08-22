package edu.miami.ccs.life.saf;

import edu.miami.ccs.life.LifeException;
import junit.framework.TestCase;

import java.io.File;
import java.io.IOException;

/**
 * @author Sam Abeyruwan
 */
public class SafJsonSchemaProviderTest extends TestCase {

    public void testExecute() throws IOException {
        SafJsonSchemaProvider provider = new SafJsonSchemaProvider(
                new File("./metadata", "SAF.xlsx"),
                new File("./metadata", "safSchema.json"));
        try {
            assertEquals(provider.execute(), true);
        } catch (LifeException e) {
            throw new IOException(e);
        }
    }
}
