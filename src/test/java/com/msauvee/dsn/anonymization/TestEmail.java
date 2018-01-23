package com.msauvee.dsn.anonymization;

import junit.framework.Assert;
import org.junit.Test;

public class TestEmail {
    
    @Test
    public void testRemoveAccentsAndLowerCase() {
        People loic = new People("Loïc", "Tybrëz", null, null, null);
        Assert.assertEquals("check remove accent", "loic.tybrez", loic.email.substring(0, loic.email.indexOf("@")));
    }
}
