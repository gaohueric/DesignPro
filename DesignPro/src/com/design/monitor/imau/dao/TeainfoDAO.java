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
 * Teainfo entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.design.monitor.imau.dao.Teainfo
 * @author MyEclipse Persistence Tools
 */
public class TeainfoDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(TeainfoDAO.class);
	// property constants
	public static final String TEA_NAME = "teaName";
	public static final String TEA_SEX = "teaSex";
	public static final String TEA_MAJOR = "teaMajor";
	public static final String TEA_TITLE = "teaTitle";
	public static final String TEA_PHONE = "teaPhone";
	public static final String TEA_QQ = "teaQq";
	public static final String TEA_WX = "teaWx";
	public static final String TEA_SELF = "teaSelf";
	public static final String TEA_STDCOUNT = "teaStdcount";
	public static final String TEA_HEAD = "teaHead";

	public void save(Teainfo transientInstance) {
		log.debug("saving Teainfo instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Teainfo persistentInstance) {
		log.debug("deleting Teainfo instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Teainfo findById(java.lang.Integer id) {
		log.debug("getting Teainfo instance with id: " + id);
		try {
			Teainfo instance = (Teainfo) getSession().get(
					"com.design.monitor.imau.dao.Teainfo", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Teainfo instance) {
		log.debug("finding Teainfo instance by example");
		try {
			List results = getSession()
					.createCriteria("com.design.monitor.imau.dao.Teainfo")
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
		log.debug("finding Teainfo instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Teainfo as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByTeaName(Object teaName) {
		return findByProperty(TEA_NAME, teaName);
	}

	public List findByTeaSex(Object teaSex) {
		return findByProperty(TEA_SEX, teaSex);
	}

	public List findByTeaMajor(Object teaMajor) {
		return findByProperty(TEA_MAJOR, teaMajor);
	}

	public List findByTeaTitle(Object teaTitle) {
		return findByProperty(TEA_TITLE, teaTitle);
	}

	public List findByTeaPhone(Object teaPhone) {
		return findByProperty(TEA_PHONE, teaPhone);
	}

	public List findByTeaQq(Object teaQq) {
		return findByProperty(TEA_QQ, teaQq);
	}

	public List findByTeaWx(Object teaWx) {
		return findByProperty(TEA_WX, teaWx);
	}

	public List findByTeaSelf(Object teaSelf) {
		return findByProperty(TEA_SELF, teaSelf);
	}

	public List findByTeaStdcount(Object teaStdcount) {
		return findByProperty(TEA_STDCOUNT, teaStdcount);
	}

	public List findByTeaHead(Object teaHead) {
		return findByProperty(TEA_HEAD, teaHead);
	}

	public List findAll() {
		log.debug("finding all Teainfo instances");
		try {
			String queryString = "from Teainfo";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Teainfo merge(Teainfo detachedInstance) {
		log.debug("merging Teainfo instance");
		try {
			Teainfo result = (Teainfo) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Teainfo instance) {
		log.debug("attaching dirty Teainfo instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Teainfo instance) {
		log.debug("attaching clean Teainfo instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}