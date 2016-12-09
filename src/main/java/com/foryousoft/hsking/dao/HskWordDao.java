package com.foryousoft.hsking.dao;

import com.foryousoft.hsking.beans.HskWord;
import com.foryousoft.hsking.beans.User;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by i-tang on 10/09/16.
 */
@org.springframework.cache.annotation.Cacheable(value = "hskingCache")
public class HskWordDao {

    @PersistenceContext
    private EntityManager entityManager;
    Logger logger = Logger.getLogger(HskWordDao.class);

    public List<HskWord> getAll() {

        Query query = entityManager.createQuery("SELECT e FROM HskWord e");
        Collection<HskWord> coll = (Collection<HskWord>) query.getResultList();
        List<HskWord> entities = new ArrayList(coll);
        return entities;
    }

    public HskWord get(Long id) {

        HskWord entity = entityManager.find(HskWord.class, id);
        return entity;
    }

    @Transactional
    public HskWord get(String traditional, String simplified, String pinyin) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<HskWord> criteria = builder.createQuery(HskWord.class);

        Root<HskWord> wordRoot = criteria.from(HskWord.class);
        Predicate[] predicates = {
                builder.equal(wordRoot.get("traditional"), traditional),
                builder.equal(wordRoot.get("simplified"), simplified),
                builder.equal(wordRoot.get("pinyin"), pinyin)};
        criteria.where(predicates);
        List<HskWord> entities = entityManager.createQuery(criteria).getResultList();
        HskWord entity = (entities.size() == 0) ? null : entities.get(0);
        if (entity == null) return null;
        User user = entity.getUser(); // equivalent of Hibernate.initialize(user);
        if (user != null) user.getHskLists().size();
        return entity;
    }

    @Transactional
    public HskWord save(HskWord entity) {

        entityManager.persist(entity);
        return entity;
    }

    @Transactional
    public HskWord update(HskWord entity) {

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

