package com.design.monitor.imau.dao;

import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for
 * Stdinfo entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.design.monitor.imau.dao.Stdinfo
 * @author MyEclipse Persistence Tools
 */
public class StdinfoDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(StdinfoDAO.class);
	// property constants
	public static final String STD_NAME = "stdName";
	public static final String STD_SEX = "stdSex";
	public static final String STD_MAJOR = "stdMajor";
	public static final String STD_GRADE = "stdGrade";
	public static final String STD_CLASS = "stdClass";
	public static final String STD_PHONE = "stdPhone";
	public static final String STD_QQ = "stdQq";
	public static final String STD_SELF = "stdSelf";
	public static final String ISSELECT = "isselect";
	public static final String HAS_TEA = "hasTea";
	public static final String TEA_NUM = "teaNum";
	public static final String STD_HEAD = "stdHead";

	public void save(Stdinfo transientInstance) {
		log.debug("saving Stdinfo instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Stdinfo persistentInstance) {
		log.debug("deleting Stdinfo instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Stdinfo findById(java.lang.Integer id) {
		log.debug("getting Stdinfo instance with id: " + id);
		try {
			Stdinfo instance = (Stdinfo) getSession().get(
					"com.design.monitor.imau.dao.Stdinfo", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Stdinfo instance) {
		log.debug("finding Stdinfo instance by example");
		try {
			List results = getSession()
					.createCriteria("com.design.monitor.imau.dao.Stdinfo")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Stdinfo instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Stdinfo as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByStdName(Object stdName) {
		return findByProperty(STD_NAME, stdName);
	}

	public List findByStdSex(Object stdSex) {
		return findByProperty(STD_SEX, stdSex);
	}

	public List findByStdMajor(Object stdMajor) {
		return findByProperty(STD_MAJOR, stdMajor);
	}

	public List findByStdGrade(Object stdGrade) {
		return findByProperty(STD_GRADE, stdGrade);
	}

	public List findByStdClass(Object stdClass) {
		return findByProperty(STD_CLASS, stdClass);
	}

	public List findByStdPhone(Object stdPhone) {
		return findByProperty(STD_PHONE, stdPhone);
	}

	public List findByStdQq(Object stdQq) {
		return findByProperty(STD_QQ, stdQq);
	}

	public List findByStdSelf(Object stdSelf) {
		return findByProperty(STD_SELF, stdSelf);
	}

	public List findByIsselect(Object isselect) {
		return findByProperty(ISSELECT, isselect);
	}

	public List findByHasTea(Object hasTea) {
		return findByProperty(HAS_TEA, hasTea);
	}

	public List findByTeaNum(Object teaNum) {
		return findByProperty(TEA_NUM, teaNum);
	}

	public List findByStdHead(Object stdHead) {
		return findByProperty(STD_HEAD, stdHead);
	}

	public List findAll() {
		log.debug("finding all Stdinfo instances");
		try {
			String queryString = "from Stdinfo";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Stdinfo merge(Stdinfo detachedInstance) {
		log.debug("merging Stdinfo instance");
		try {
			Stdinfo result = (Stdinfo) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Stdinfo instance) {
		log.debug("attaching dirty Stdinfo instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Stdinfo instance) {
		log.debug("attaching clean Stdinfo instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}