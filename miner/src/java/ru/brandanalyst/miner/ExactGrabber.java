package ru.brandanalyst.miner;
//

//* To change this template, choose Tools | Templates

//* and open the template in the editor.

//

import java.util.List;

/**
 *
 * @author Александр
 */
public interface ExactGrabber {
    List<String> grab(String[] brandNames);
    List<String> grab(String brandName);
}
