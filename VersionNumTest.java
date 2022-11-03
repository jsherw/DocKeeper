import org.junit.Test;

import static org.junit.Assert.*;

public class VersionNumTest {

    VersionNum testVN;

    /**
     * @author James Sherwood
     */
    @Test
    public void getVersionNum() {
        testVN = new VersionNum();
        assertEquals(0.1, testVN.getVersionNum(), 0.0);
        testVN = new VersionNum("Sally Maclennane", "pogues@music.com");
        assertEquals(0.1, testVN.getVersionNum(), 0.0);
    }

    /**
     * @author James Sherwood
     */
    @Test
    public void setUserName() {
        testVN = new VersionNum();
        assertNull(testVN.getUserName());
        testVN.setUserName("Eddie Veder");
        assertEquals("Eddie Veder", testVN.getUserName());
    }

    /**
     * @author James Sherwood
     */
    @Test
    public void getUserName() {
        testVN = new VersionNum();
        assertNull(testVN.getUserName());
        testVN = new VersionNum("Kurt Cobain", "kc@nirvana.com");
        assertEquals("Kurt Cobain", testVN.getUserName());
    }

    /**
     * @author James Sherwood
     */
    @Test
    public void setUserEmail() {
        testVN = new VersionNum();
        assertNull(testVN.getUserEmail());
        testVN.setUserEmail("kc@nirvana.com");
        assertEquals("kc@nirvana.com", testVN.getUserEmail());
    }

    /**
     * @author James Sherwood
     */
    @Test
    public void getUserEmail() {
        testVN = new VersionNum();
        assertNull(testVN.getUserEmail());
        testVN = new VersionNum("Kurt Cobain", "kc@nirvana.com");
        assertEquals("kc@nirvana.com", testVN.getUserEmail());
    }

    /**
     * @author James Sherwood
     */
    @Test
    public void getGroup() {
        testVN = new VersionNum();
        assertEquals("Team Gamma", testVN.getGroup());
        testVN = new VersionNum("Sally Maclennane", "pogues@music.com");
        assertEquals("Team Gamma", testVN.getGroup());
    }
}