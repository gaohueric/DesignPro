package com.design.monitor.imau.dao;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for
 * Notification entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.design.monitor.imau.dao.Notification
 * @author MyEclipse Persistence Tools
 */
public class NotificationDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(NotificationDAO.class);
	// property constants
	public static final String ACCOUNT = "account";
	public static final String CHANNELID = "channelid";
	public static final String USERID = "userid";

	public void save(Notification transientInstance) {
		log.debug("saving Notification instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Notification persistentInstance) {
		log.debug("deleting Notification instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Notification findById(java.lang.Integer id) {
		log.debug("getting Notification instance with id: " + id);
		try {
			Notification instance = (Notification) getSession().get(
					"com.design.monitor.imau.dao.Notification", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Notification instance) {
		log.debug("finding Notification instance by example");
		try {
			List results = getSession()
					.createCriteria("com.design.monitor.imau.dao.Notification")
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
		log.debug("finding Notification instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from Notification as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByAccount(Object account) {
		return findByProperty(ACCOUNT, account);
	}

	public List findByChannelid(Object channelid) {
		return findByProperty(CHANNELID, channelid);
	}

	public List findByUserid(Object userid) {
		return findByProperty(USERID, userid);
	}

	public List findAll() {
		log.debug("finding all Notification instances");
		try {
			String queryString = "from Notification";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Notification merge(Notification detachedInstance) {
		log.debug("merging Notification instance");
		try {
			Notification result = (Notification) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Notification instance) {
		log.debug("attaching dirty Notification instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Notification instance) {
		log.debug("attaching clean Notification instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}