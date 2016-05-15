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
 * TControl entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.design.monitor.imau.dao.TControl
 * @author MyEclipse Persistence Tools
 */
public class TControlDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(TControlDAO.class);
	// property constants
	public static final String TEA_ID = "teaId";
	public static final String CYCLE = "cycle";
	public static final String REMINDER = "reminder";

	public void save(TControl transientInstance) {
		log.debug("saving TControl instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(TControl persistentInstance) {
		log.debug("deleting TControl instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TControl findById(java.lang.Integer id) {
		log.debug("getting TControl instance with id: " + id);
		try {
			TControl instance = (TControl) getSession().get(
					"com.design.monitor.imau.dao.TControl", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(TControl instance) {
		log.debug("finding TControl instance by example");
		try {
			List results = getSession()
					.createCriteria("com.design.monitor.imau.dao.TControl")
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
		log.debug("finding TControl instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from TControl as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByTeaId(Object teaId) {
		return findByProperty(TEA_ID, teaId);
	}

	public List findByCycle(Object cycle) {
		return findByProperty(CYCLE, cycle);
	}

	public List findByReminder(Object reminder) {
		return findByProperty(REMINDER, reminder);
	}

	public List findAll() {
		log.debug("finding all TControl instances");
		try {
			String queryString = "from TControl";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public TControl merge(TControl detachedInstance) {
		log.debug("merging TControl instance");
		try {
			TControl result = (TControl) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(TControl instance) {
		log.debug("attaching dirty TControl instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(TControl instance) {
		log.debug("attaching clean TControl instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}