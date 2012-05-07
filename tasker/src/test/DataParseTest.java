import junit.framework.TestCase;
import ru.brandanalyst.core.util.BrandAnalyticsLocale;

import java.text.SimpleDateFormat;

/**
 * User: daddy-bear
 * Date: 30.04.12
 * Time: 9:13
 */
public class DataParseTest extends TestCase {
    //private static final String DATE_FORMAT = "EEE', 'dd' 'MMM' 'yyyy' 'HH:mm:ss";
    private static final String DATE_FORMAT = "EEE, dd MMM yyyy HH:mm:ss zzz";
    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat(DATE_FORMAT, BrandAnalyticsLocale.RU);

    //private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormat.forPattern(DATE_FORMAT);

    public void testDate() throws Exception {
        //System.out.println(FORMATTER.parse("Jul"));
        //System.out.println(DATE_TIME_FORMATTER.parseLocalDateTime("Sat, 28 Apr 2012 03:21:29"));

        System.out.println(FORMATTER.parse("Mon, 30 Apr 2012 01:22:12 GMT"));
    }
}
