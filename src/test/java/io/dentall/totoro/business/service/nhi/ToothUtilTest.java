package io.dentall.totoro.business.service.nhi;

import io.dentall.totoro.business.service.nhi.util.ToothUtil;
import org.junit.Assert;
import org.junit.Test;

public class ToothUtilTest {

    @Test
    public void testToothToDisplayValidateClass() {
        Assert.assertEquals("11", ToothUtil.multipleToothToDisplay("11"));
    }

    @Test
    public void testToothToDisplayInvalidateClass() {
        Assert.assertEquals("", ToothUtil.multipleToothToDisplay(""));
    }

    @Test
    public void testRegularInput2Multiple() {
        Assert.assertEquals("11/12", ToothUtil.multipleToothToDisplay("1112"));
    }

    @Test
    public void testRegularInputNon2Multiple() {
        Assert.assertEquals("", ToothUtil.multipleToothToDisplay("11121"));
    }

}
