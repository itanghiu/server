package com.foryousoft.hsking.dao;

import com.foryousoft.hsking.beans.HskList;
import com.foryousoft.hsking.beans.HskWord;
import com.foryousoft.hsking.beans.User;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by i-tang on 29/08/16.
 */
@org.springframework.cache.annotation.Cacheable(value = "hskingCache")
public class UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    Logger logger = Logger.getLogger(UserDao.class);

    @org.springframework.cache.annotation.Cacheable(value = "hskingCache")
    public List<User> find() {

        Query query = entityManager.createQuery("SELECT e FROM User e");
        Collection<User> coll = (Collection<User>) query.getResultList();
        List<User> entities = new ArrayList(coll);
        return entities;
    }

    public User find(Long id) {

        User entity = entityManager.find(User.class, id);
        return entity;
    }

    public User find(String emailAddress, String firstName, String lastName) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteria = builder.createQuery(User.class);

        Root<User> wordRoot = criteria.from(User.class);
        Predicate[] predicates = {
                builder.equal(wordRoot.get("emailAddress"), emailAddress),
                builder.equal(wordRoot.get("firstName"), firstName),
                builder.equal(wordRoot.get("lastName"), lastName)};
        criteria.where(predicates);
        List<User> entities = entityManager.createQuery(criteria).getResultList();
        User entity = (entities.size() == 0) ? null : entities.get(0);
        return entity;
    }

    @Transactional
    public List<HskList> getHskLists(Long userId) {

        User entity = find(userId);
        List<HskList> results = entity.getHskLists();
        return results;
    }

    @Transactional
    public User save(User entity) {

        entityManager.persist(entity);
        return entity;
    }

    @Transactional
    public User update(User entity) {

        entityManager.merge(entity);
        return entity;
    }

    @Transactional
    public void delete(Long id) {

        HskWord entity = entityManager.find(HskWord.class, id);
        if (entity != null)
            entityManager.remove(entity);
    }
}
