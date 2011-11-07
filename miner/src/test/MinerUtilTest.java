import org.junit.Test;
import ru.brandanalyst.miner.util.DataTransformator;

import static junit.framework.Assert.assertEquals;

/**
 * Created by IntelliJ IDEA.
 * User: Dmitry Batkovich
 * Date: 11/6/11
 * Time: 8:02 PM
 */
public class MinerUtilTest {
    @Test
    public void testDataTransformator() {
        assertEquals(DataTransformator.clearString("<sdfsdf>  <jhgjh>\n\n\n jhkh"), "sdfsdf jhgjh\n hjkhk");
        assertEquals(DataTransformator.stringToQueryString("testing стринг"), "testing+стринг");
        assertEquals(DataTransformator.stringToHexQueryString("газпром"), "%e3%e0%e7%ef%f0%ee%ec");
    }

    @Test
    public void LentaDataTransformatorChecker() {
        //    assertEquals(StringChecker.isTitleHaveTerm(new ArrayList<String>().add("няшка"),"НяШкА"), "%e3%e0%e7%ef%f0%ee%ec");
    }
}
