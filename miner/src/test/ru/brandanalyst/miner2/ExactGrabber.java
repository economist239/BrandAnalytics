package ru.brandanalyst.miner2;
//

//* To change this template, choose Tools | Templates

//* and open the template in the editor.

//

import java.util.List;

/**
 *
 * @author Александр
 */
public abstract class ExactGrabber {
    public abstract void setBrandNames(List<String> brandNames);
    public abstract void grab();
}
