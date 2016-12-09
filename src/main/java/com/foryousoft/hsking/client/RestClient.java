package com.foryousoft.hsking.client;

import com.foryousoft.hsking.beans.HskList;
import com.foryousoft.hsking.beans.HskWord;
import com.foryousoft.hsking.beans.User;
import com.foryousoft.hsking.enums.CharacterType;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * Created by i-tang on 28/08/16.
 */
public class RestClient {

    public static Logger logger = Logger.getLogger(RestClient.class);

    // http://localhost:9999/hsking/get/user/aaaa.qqq@gmail.com/jogn/WAZ

    public static void main(String[] args) {

        ApplicationContext ctx = new ClassPathXmlApplicationContext("/clientContext.xml");
        WebServiceClient webService = (WebServiceClient) ctx.getBean("webService");

        User user = new User("bbbb@gmail.com", "BBBBBB", "BbBbBbB");
        user = webService.saveUser(user);

        HskWord hskword1 = new HskWord(user, "马", "马", "ma3", "Cheval");
        hskword1.setCharacterType(CharacterType.PICTOGRAM);
        hskword1.addSentenceExample("这个吗很高。");
        hskword1.addSentenceExample("这个吗很小。");

        HskWord hskword2 = new HskWord(user, "米", "米", "mi3", "Riz");
        hskword2.setCharacterType(CharacterType.PICTOGRAM);
        hskword2.addSentenceExample("这个米很好。");
        hskword2.addSentenceExample("这个米很小。");

        HskWord hskword3 = new HskWord(user, "大", "大", "da4", "Grand");
        hskword3.setCharacterType(CharacterType.PICTOGRAM);
        hskword3.addSentenceExample("这个房子很大。");
        hskword3.addSentenceExample("这个人很大。");

        HskWord hskword4 = new HskWord(user, "累", "累", "lei2", "Fatigué");
        hskword4.setCharacterType(CharacterType.PICTOGRAM);
        hskword4.addSentenceExample("他很累。");
        hskword4.addSentenceExample("这个人很累。");

        hskword1 = webService.saveHskWord(hskword1);
        hskword2 = webService.saveHskWord(hskword2);
        hskword3 = webService.saveHskWord(hskword3);
        hskword4 = webService.saveHskWord(hskword4);

        hskword1.setMeaning("fuck");
        webService.updateHskWord( hskword1);
        hskword1 = webService.getHskWord("马", "马", "ma3");

        HskList list1 = new HskList(user, "List1", false);
        list1.addHskWord(hskword1);
        list1.addHskWord(hskword2);
        list1.addHskWord(hskword3);
        user.add(list1);
        list1 = webService.saveHSList(list1);

        HskList list2 = new HskList(user, "List2", false);
        list2.addHskWord(hskword1);
        list2.addHskWord(hskword4);
        list2 = webService.saveHSList(list2);

        list1 = webService.getHskList("List1");
        list2 = webService.getHskList("List2");
        logger.info("LIST: " + list1);

        webService.deleteHSList(list2.getId());
        list2 = webService.getHskList("List2");
        logger.info("list2: " + list2);
         /*
        logger.info("hskword: " + hskword);
        User user1 = hskword.getUser();
        logger.info("hskword.user: " + user1);
        List<HskList> lists = (user1 == null) ? null : user1.getHskLists();*/

        Long userId = user.getId();
        //user = webService.getUser(userId);
        //logger.info("User: " + user);

    }


}
