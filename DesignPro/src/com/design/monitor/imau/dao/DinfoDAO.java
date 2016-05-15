package com.design.monitor.imau.dao;

import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for Dinfo
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 * @see com.design.monitor.imau.dao.Dinfo
 * @author MyEclipse Persistence Tools
 */
public class DinfoDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(DinfoDAO.class);
	// property constants
	public static final String STD_ID = "stdId";
	public static final String TEA_ID = "teaId";
	public static final String DNAME = "dname";
	public static final String DPATH = "dpath";
	public static final String SUPCOUNT = "supcount";
	public static final String TUPCOUNT = "tupcount";
	public static final String SDOWNCOUNT = "sdowncount";
	public static final String TDOWNCOUNT = "tdowncount";

	public void save(Dinfo transientInstance) {
		log.debug("saving Dinfo instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Dinfo persistentInstance) {
		log.debug("deleting Dinfo instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Dinfo findById(java.lang.Integer id) {
		log.debug("getting Dinfo instance with id: " + id);
		try {
			Dinfo instance = (Dinfo) getSession().get(
					"com.design.monitor.imau.dao.Dinfo", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Dinfo instance) {
		log.debug("finding Dinfo instance by example");
		try {
			List results = getSession()
					.createCriteria("com.design.monitor.imau.dao.Dinfo")
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
		log.debug("finding Dinfo instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Dinfo as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByStdId(Object stdId) {
		return findByProperty(STD_ID, stdId);
	}

	public List findByTeaId(Object teaId) {
		return findByProperty(TEA_ID, teaId);
	}

	public List findByDname(Object dname) {
		return findByProperty(DNAME, dname);
	}

	public List findByDpath(Object dpath) {
		return findByProperty(DPATH, dpath);
	}

	public List findBySupcount(Object supcount) {
		return findByProperty(SUPCOUNT, supcount);
	}

	public List findByTupcount(Object tupcount) {
		return findByProperty(TUPCOUNT, tupcount);
	}

	public List findBySdowncount(Object sdowncount) {
		return findByProperty(SDOWNCOUNT, sdowncount);
	}

	public List findByTdowncount(Object tdowncount) {
		return findByProperty(TDOWNCOUNT, tdowncount);
	}

	public List findAll() {
		log.debug("finding all Dinfo instances");
		try {
			String queryString = "from Dinfo";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Dinfo merge(Dinfo detachedInstance) {
		log.debug("merging Dinfo instance");
		try {
			Dinfo result = (Dinfo) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Dinfo instance) {
		log.debug("attaching dirty Dinfo instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Dinfo instance) {
		log.debug("attaching clean Dinfo instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}