package ru.brandanalyst.frontend.yalets;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;
import net.sf.xfresh.core.xml.Xmler;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;


/**
 * Created by IntelliJ IDEA.
 * User: dima
 * Date: 12/15/11
 * Time: 9:11 AM
 */
public class GetGraphsYalet extends AbstractDbYalet {
    public void process(InternalRequest req, InternalResponse res) {

        long graphGroupId = req.getLongParameter("group-id");
        /*     try {
            OutputStream os = res.getOutputStream();
            os.write("{\"key\": \"value\"}".getBytes());
        } catch (IOException e) {
            res.add(Xmler.tag("error"));
        }*/
        String str = "{\"key\": \"value\"}";
        res.add(Xmler.tag("mytag", str));
    }
}
