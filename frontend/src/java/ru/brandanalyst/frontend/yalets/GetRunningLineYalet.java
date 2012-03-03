package ru.brandanalyst.frontend.yalets;

import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;
import net.sf.xfresh.core.Yalet;
import net.sf.xfresh.core.xml.Xmler;
import org.springframework.beans.factory.annotation.Required;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * User: dima
 * Date: 2/26/12
 * Time: 1:34 PM
 *
 * reading running line from file (it's more fast then sql and more simple than cache)
 */
public class GetRunningLineYalet implements Yalet {
    private String runningLineFile;

    @Required
    public void setRunningLineFile(String runningLineFile) {
        this.runningLineFile = runningLineFile;
    }

    @Override
    public void process(InternalRequest req, InternalResponse res) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(runningLineFile));
            res.add(Xmler.tag("running-line", br.readLine()));
        } catch (IOException e) {
            res.add(Xmler.ERROR_TAG);
        }
    }
}
