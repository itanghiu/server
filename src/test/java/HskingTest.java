import com.foryousoft.hsking.beans.*;
import com.foryousoft.hsking.enums.LanguageType;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by i-tang on 22/08/16.
 */
public class HskingTest {

    public static Logger logger = Logger.getLogger(HskingTest.class);
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    //@Before
    public void setUp() {

        entityManagerFactory = Persistence.createEntityManagerFactory("hsking-jpa");
        entityManager = entityManagerFactory.createEntityManager();
    }

    //@Test
    public void testDb() {
        assertEquals(entityManagerFactory.isOpen(), true);
    }

    //@Test
    public void test2() {

        // -------- Creates the HSList
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        User user = new User("usr@gmail.com", "I-Tang", "HIU");
        HskWord hsWord1 = new HskWord(user, "我", "我", "wo2", "I, self");
        hsWord1.addSentenceExample("我啤酒 . 马");
        hsWord1.addSentenceExample("啤酒 我 . 马");
        hsWord1.addSentenceExample("酒 火车 我 . 马啤");

        HskWord hsWord2 = new HskWord(user, "马", "马", "ma1", "I, self");
        HskWord hsWord3 = new HskWord(user, "火车", "火车", "huo che", "train");
        HskWord hsWord4 = new HskWord(user, "啤酒", "啤酒", "pi jiu", "beer");

        HskList hsList = new HskList(user, "list1");
        hsList.setBookName("book1");
        hsList.setLesson("lesson1");
        hsList.setLanguage(LanguageType.FRENCH);
        user.add(hsList);
       /* hsList.addHsWord(hsWord1);
        hsList.addHsWord(hsWord2);
        hsList.addHsWord(hsWord3);
        hsList.addHsWord(hsWord4);*/

        entityManager.persist(user);
        entityManager.persist(hsList);
        entityManager.persist(hsWord1);
        entityManager.persist(hsWord2);
        entityManager.persist(hsWord3);
        entityManager.persist(hsWord4);
        transaction.commit();

        // --------------------------------------------
        // second work unit
        // --------------------------------------------
        Session session = entityManager.unwrap(Session.class);
        Criteria criteria = session.createCriteria(HskList.class);
        List<HskList> hsLists = criteria.add(Restrictions.eq("name", "list1")).list();
        hsList = hsLists.get(0);
        logger.info("-------------   List: " + hsList);
     /*   assertEquals(hsList.size(), new Integer(4));
        EntityTransaction transaction2 = entityManager.getTransaction();
        transaction2.begin();
        HskWord wordToDelete = hsList.getHsWord(2);
        hsList.removeHsWord(wordToDelete);
        assertEquals(hsList.size(), new Integer(3));
        logger.info("-------------   List.size: " + hsList.size());*/
        transaction.commit();

        entityManager.close();


    }

   /* @Test
    public void test3() {

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        MyBean mBean = new MyBean("usr@gmail.com");
        //user.setCreationDate(new Date());
        entityManager.persist(mBean);
        transaction.commit();
        entityManager.close();
    }*/
}
