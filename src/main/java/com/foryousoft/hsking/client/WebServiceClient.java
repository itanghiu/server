package com.foryousoft.hsking.client;

import com.foryousoft.hsking.EmbeddedServer;
import com.foryousoft.hsking.beans.HskList;
import com.foryousoft.hsking.beans.HskWord;
import com.foryousoft.hsking.beans.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

/**
 * Created by i-tang on 28/08/16.
 */
public class WebServiceClient {

    private RestTemplate template;

    public WebServiceClient() {
    }

    //--------- USER -------------------------
    public User getUser(Long id) {
        return template.getForObject(EmbeddedServer.getUrlRoot() + "hsking/users/{id}", User.class, id);
    }

    public User getUser(String emailAddress, String firstName, String lastName) {
        return template.getForObject(EmbeddedServer.getUrlRoot() + "hsking/users/{emailAddress}/{firstName}/{lastName}", User.class, emailAddress, firstName, lastName);
    }

    public List<User> getUsers() {

        String url = EmbeddedServer.getUrlRoot() + "hsking/users/";
        ResponseEntity<User[]> responseEntity = template.getForEntity(url, User[].class);
        User[] users = responseEntity.getBody();
        return Arrays.asList(users);
    }

    public User saveUser(User user) {
        return template.postForObject(EmbeddedServer.getUrlRoot() + "hsking/users", user, User.class);
    }

    public void updateUser(User entity) {
        template.put(EmbeddedServer.getUrlRoot() + "hsking/users", entity);
    }

    // ------------------------------------------
    //--------- HSKWORD -------------------------
    public HskWord getHskWord(Long id) {
        return template.getForObject(EmbeddedServer.getUrlRoot() + "hsking/hskwords/get/{id}",HskWord.class, id);
    }

    public HskWord loadHskWord(Long id) {
        return template.getForObject(EmbeddedServer.getUrlRoot() + "hsking/hskwords/load/{id}",HskWord.class, id);
    }

    public HskWord getHskWord(String traditional, String simplified, String pinyin) {
        return template.getForObject(EmbeddedServer.getUrlRoot() + "hsking/hskwords/{traditional}/{simplified}/{pinyin}",
                HskWord.class, traditional, simplified, pinyin);
    }

    public HskWord saveHskWord(HskWord entity) {
        return template.postForObject(EmbeddedServer.getUrlRoot() + "hsking/hskwords", entity, HskWord.class);
    }

    public void updateHskWord(HskWord entity) {
        template.put(EmbeddedServer.getUrlRoot() + "hsking/hskwords", entity);
    }

    //--------- HSKLIST -------------------------

    public HskList getHskList(String name) {
        return template.getForObject(EmbeddedServer.getUrlRoot() + "hsking/hskLists/{name}",
                HskList.class, name);
    }

    public HskList saveHSList(HskList hskList) {
        return template.postForObject(EmbeddedServer.getUrlRoot() + "hsking/hskLists", hskList, HskList.class);
    }

    public void updateHskList(User entity) {
        template.put(EmbeddedServer.getUrlRoot() + "hsking/hskLists", entity);
    }

    public void deleteHSList(Long id) {
        template.delete(EmbeddedServer.getUrlRoot() + "hsking/hskLists/" + id);
    }

    public RestTemplate getTemplate() {
        return template;
    }

    public void setTemplate(RestTemplate template) {
        this.template = template;
    }
}
