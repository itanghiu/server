import org.apache.log4j.Logger;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;

import static org.junit.Assert.assertEquals;

/**
 * Created by i-tang on 23/11/16.
 */
public class H2databaseTest {

    public static Logger logger = Logger.getLogger(H2databaseTest.class);
    String url = "jdbc:h2:~/test";


    @Test
    public void testDb() {

        Connection conn= null;
        try {
            Class.forName("org.h2.Driver");
            conn = DriverManager.getConnection(url, "sa", "");
            logger.info("CONNECTED" + '\n');
            if(conn.isValid(1000)){
                //DO WHATEVER FOR ALIVENESS
            }

        } catch (Exception e) {
            logger.info(e.getMessage() + '\n');
        }
    }
}
