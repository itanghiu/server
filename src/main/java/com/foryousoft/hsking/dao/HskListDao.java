package com.foryousoft.hsking.dao;

import com.foryousoft.hsking.beans.HskList;
import com.foryousoft.hsking.beans.HskWord;
import org.apache.log4j.Logger;
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
 * Created by i-tang on 04/09/16.
 */
@org.springframework.cache.annotation.Cacheable(value = "hskingCache")
public class HskListDao {

    @PersistenceContext
    private EntityManager entityManager;
    Logger logger = Logger.getLogger(HskListDao.class);

    public HskList get(Long id) {

        HskList entity = entityManager.find(HskList.class, id);
        return entity;
    }

    @Transactional
    public HskList get(String name) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<HskList> criteria = builder.createQuery(HskList.class);

        Root<HskList> wordRoot = criteria.from(HskList.class);
        Predicate[] predicates = {
                builder.equal(wordRoot.get("name"), name),
                };
        criteria.where(predicates);
        List<HskList> entities = entityManager.createQuery(criteria).getResultList();
        HskList entity = (entities.size() == 0) ? null : entities.get(0);
        if (entity == null) return null;
        List<HskWord> words = entity.getHskWords();
        if (words !=null) words.size();
        return entity;
    }

    public List<HskList> getHskLists() {

        javax.persistence.Query query = entityManager.createQuery("SELECT e FROM HskList e");
        Collection<HskList> coll = (Collection<HskList>) query.getResultList();
        return new ArrayList(coll);
    }

    @Transactional
    public HskList save(HskList entity) {

        entityManager.persist(entity);
        return entity;
    }

    @Transactional
    public HskList update(HskList entity) {

        entityManager.merge(entity);
        return entity;
    }

    @Transactional
    public void delete(Long id) {

        HskList entity = entityManager.find(HskList.class, id);
        if (entity != null)
            entityManager.remove(entity);
    }
}

