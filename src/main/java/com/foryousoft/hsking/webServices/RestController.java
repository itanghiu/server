package com.foryousoft.hsking.webServices;

import com.foryousoft.hsking.beans.*;
import com.foryousoft.hsking.dao.HskListDao;
import com.foryousoft.hsking.dao.HskWordDao;
import com.foryousoft.hsking.dao.UserDao;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by i-tang on 28/08/16.
 */
@Controller
public class RestController {

    Logger logger = Logger.getLogger(RestController.class);
    @Autowired
    private UserDao userDao;
    @Autowired
    private HskListDao listDao;
    @Autowired
    private HskWordDao hskwordDao;

    public RestController() {
    }

    // ---------------------------------------------
    // --- USER
    // ---------------------------------------------
    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public
    @ResponseBody
    User getUser(@PathVariable("id") Long id) {

        User entity = userDao.find(id);
        return entity;
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public
    @ResponseBody
    List<User> getUser() {

        logger.info("----- in getUser()");
        List<User> users = userDao.find();
        return users;
    }

    @RequestMapping(value = "/users/{email}/{firstName}/{lastName}", method = RequestMethod.GET)
    @ResponseBody
    public User getUser(@PathVariable("email") String email,
                        @PathVariable("firstName") String firstName,
                        @PathVariable("lastName") String lastName) {

        Assert.notNull(email);
        Assert.notNull(firstName);
        Assert.notNull(lastName);
        User user = userDao.find(email, firstName, lastName);
        return user;
    }

    @RequestMapping(value = "/hskLists", method = RequestMethod.PUT, produces = "application/json")
    @ResponseBody
    public HskList updateUser(@RequestBody HskList entity) {

        logger.info("Updating list");
        Assert.notNull(entity);
        Assert.notNull(entity.getName());
        return listDao.update(entity);
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public User saveUser(@RequestBody User entity) {

        Assert.notNull(entity);
        Assert.notNull(entity.getEmail());
        Assert.notNull(entity.getLastName());
        Assert.notNull(entity.getFirstName());
        return userDao.save(entity);
    }

    @RequestMapping(value = "/users", method = RequestMethod.PUT, produces = "application/json")
    @ResponseBody
    public User updateUser( @RequestBody User entity) {

        logger.info("Updating user");
        Assert.notNull(entity);
        Assert.notNull(entity.getEmail());
        Assert.notNull(entity.getLastName());
        Assert.notNull(entity.getFirstName());
        return userDao.update(entity);
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity deleteUser(@PathVariable Long id) {

        logger.info("Deleting user");
        User user = userDao.find(id);
        if (user == null)
            return new ResponseEntity(new UnexpectedError("Invalid user id (" + id + ")."), HttpStatus.BAD_REQUEST);
        userDao.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    // ---------------------------------------------
    // --- HSKWORD
    // ---------------------------------------------
    @RequestMapping(value = "/hskwords/get/{id}", method = RequestMethod.GET)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public HskWord getHskWord(@PathVariable("id") Long id) {

        Assert.notNull(id);
        HskWord entity = hskwordDao.get(id);
        return entity;
    }

    @RequestMapping(value = "/hskwords/{traditional}/{simplified}/{pinyin}", method = RequestMethod.GET)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public HskWord getHskWord(@PathVariable("traditional") String traditional,
                              @PathVariable("simplified") String simplified,
                              @PathVariable("pinyin") String pinyin) {

        logger.info("in getHskWord");
        Assert.notNull(traditional);
        Assert.notNull(simplified);
        Assert.notNull(pinyin);
        HskWord entity = hskwordDao.get(traditional, simplified, pinyin);
        return entity;
    }

    @RequestMapping(value = "/hskwords", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public HskWord saveHskWord(@RequestBody HskWord entity) {

        Assert.notNull(entity);
        Assert.notNull(entity.getUser());
        Assert.notNull(entity.getTraditional());
        Assert.notNull(entity.getSimplified());
        Assert.notNull(entity.getCedictMeaning());
        return hskwordDao.save(entity);
    }

    @RequestMapping(value = "/hskwords", method = RequestMethod.PUT)
    @ResponseBody
    public void updateHskWord(@RequestBody HskWord entity) {

        logger.info("Updating user");
        Assert.notNull(entity);
        Assert.notNull(entity.getTraditional());
        Assert.notNull(entity.getSimplified());
        Assert.notNull(entity.getPinyin());
        hskwordDao.update(entity);
    }

    // ---------------------------------------------
    // --- HSKLIST
    // ---------------------------------------------
    @RequestMapping(value = "/hskLists/{name}", method = RequestMethod.GET)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public HskList getHskWord(@PathVariable("name") String name) {

        Assert.notNull(name);
        HskList entity = listDao.get(name);
        return entity;
    }

    @RequestMapping(value = "/hskLists", method = RequestMethod.GET)
    public
    @ResponseBody
    List<HskList> getHskLists() {

        logger.info("----- in getHskLists()");
        List<HskList> hskLists = listDao.getHskLists();
        return hskLists;
    }

    @RequestMapping(value = "/hskLists", method = RequestMethod.POST)
    @ResponseBody
    public HskList saveList(@RequestBody HskList entity) {

        Assert.notNull(entity);
        Assert.notNull(entity.getName());
        return listDao.save(entity);
    }

    @RequestMapping(value = "/hskLists/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteHsklist(@PathVariable Long id) {

        logger.info("Deleting user");
        HskList entity = listDao.get(id);
        if (entity == null)
            return new ResponseEntity(new UnexpectedError("Invalid HskList id (" + id + ")."), HttpStatus.BAD_REQUEST);
        listDao.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}

