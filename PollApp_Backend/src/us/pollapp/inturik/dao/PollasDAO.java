package us.pollapp.inturik.dao;

import java.util.List;
import java.util.logging.Level;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import us.pollapp.inturik.model.Pollas;

/**
 * A data access object (DAO) providing persistence and search support for
 * Pollas entities. Transaction control of the save(), update() and delete()
 * operations must be handled externally by senders of these methods or must be
 * manually added to each of these methods for data to be persisted to the JPA
 * datastore.
 * 
 * @see .Pollas
 * @author MyEclipse Persistence Tools
 */

public class PollasDAO implements IPollasDAO {
	// property constants
	public static final String NOMBRE = "nombre";

	private EntityManager getEntityManager() {
		return EntityManagerHelper.getEntityManager();
	}

	/**
	 * Perform an initial save of a previously unsaved Pollas entity. All
	 * subsequent persist actions of this entity should use the #update()
	 * method. This operation must be performed within the a database
	 * transaction context for the entity's data to be permanently saved to the
	 * persistence store, i.e., database. This method uses the
	 * {@link javax.persistence.EntityManager#persist(Object)
	 * EntityManager#persist} operation.
	 * 
	 * <pre>
	 * EntityManagerHelper.beginTransaction();
	 * PollasDAO.save(entity);
	 * EntityManagerHelper.commit();
	 * </pre>
	 * 
	 * @param entity
	 *            Pollas entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void save(Pollas entity) {
		EntityManagerHelper.log("saving Pollas instance", Level.INFO, null);
		try {
			getEntityManager().persist(entity);
			EntityManagerHelper.log("save successful", Level.INFO, null);
		} catch (RuntimeException re) {
			EntityManagerHelper.log("save failed", Level.SEVERE, re);
			throw re;
		}
	}

	/**
	 * Delete a persistent Pollas entity. This operation must be performed
	 * within the a database transaction context for the entity's data to be
	 * permanently deleted from the persistence store, i.e., database. This
	 * method uses the {@link javax.persistence.EntityManager#remove(Object)
	 * EntityManager#delete} operation.
	 * 
	 * <pre>
	 * EntityManagerHelper.beginTransaction();
	 * PollasDAO.delete(entity);
	 * EntityManagerHelper.commit();
	 * entity = null;
	 * </pre>
	 * 
	 * @param entity
	 *            Pollas entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(Pollas entity) {
		EntityManagerHelper.log("deleting Pollas instance", Level.INFO, null);
		try {
			entity = getEntityManager().getReference(Pollas.class,
					entity.getId());
			getEntityManager().remove(entity);
			EntityManagerHelper.log("delete successful", Level.INFO, null);
		} catch (RuntimeException re) {
			EntityManagerHelper.log("delete failed", Level.SEVERE, re);
			throw re;
		}
	}

	/**
	 * Persist a previously saved Pollas entity and return it or a copy of it to
	 * the sender. A copy of the Pollas entity parameter is returned when the
	 * JPA persistence mechanism has not previously been tracking the updated
	 * entity. This operation must be performed within the a database
	 * transaction context for the entity's data to be permanently saved to the
	 * persistence store, i.e., database. This method uses the
	 * {@link javax.persistence.EntityManager#merge(Object) EntityManager#merge}
	 * operation.
	 * 
	 * <pre>
	 * EntityManagerHelper.beginTransaction();
	 * entity = PollasDAO.update(entity);
	 * EntityManagerHelper.commit();
	 * </pre>
	 * 
	 * @param entity
	 *            Pollas entity to update
	 * @return Pollas the persisted Pollas entity instance, may not be the same
	 * @throws RuntimeException
	 *             if the operation fails
	 */
	public Pollas update(Pollas entity) {
		EntityManagerHelper.log("updating Pollas instance", Level.INFO, null);
		try {
			Pollas result = getEntityManager().merge(entity);
			EntityManagerHelper.log("update successful", Level.INFO, null);
			return result;
		} catch (RuntimeException re) {
			EntityManagerHelper.log("update failed", Level.SEVERE, re);
			throw re;
		}
	}

	public Pollas findById(Integer id) {
		EntityManagerHelper.log("finding Pollas instance with id: " + id,
				Level.INFO, null);
		try {
			Pollas instance = getEntityManager().find(Pollas.class, id);
			return instance;
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find failed", Level.SEVERE, re);
			throw re;
		}
	}

	/**
	 * Find all Pollas entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the Pollas property to query
	 * @param value
	 *            the property value to match
	 * @param rowStartIdxAndCount
	 *            Optional int varargs. rowStartIdxAndCount[0] specifies the the
	 *            row index in the query result-set to begin collecting the
	 *            results. rowStartIdxAndCount[1] specifies the the maximum
	 *            number of results to return.
	 * @return List<Pollas> found by query
	 */
	@SuppressWarnings("unchecked")
	public List<Pollas> findByProperty(String propertyName, final Object value,
			final int... rowStartIdxAndCount) {
		EntityManagerHelper.log("finding Pollas instance with property: "
				+ propertyName + ", value: " + value, Level.INFO, null);
		try {
			final String queryString = "select model from Pollas model where model."
					+ propertyName + "= :propertyValue";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("propertyValue", value);
			if (rowStartIdxAndCount != null && rowStartIdxAndCount.length > 0) {
				int rowStartIdx = Math.max(0, rowStartIdxAndCount[0]);
				if (rowStartIdx > 0) {
					query.setFirstResult(rowStartIdx);
				}

				if (rowStartIdxAndCount.length > 1) {
					int rowCount = Math.max(0, rowStartIdxAndCount[1]);
					if (rowCount > 0) {
						query.setMaxResults(rowCount);
					}
				}
			}
			return query.getResultList();
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find by property name failed",
					Level.SEVERE, re);
			throw re;
		}
	}

	public List<Pollas> findByNombre(Object nombre, int... rowStartIdxAndCount) {
		return findByProperty(NOMBRE, nombre, rowStartIdxAndCount);
	}

	/**
	 * Find all Pollas entities.
	 * 
	 * @param rowStartIdxAndCount
	 *            Optional int varargs. rowStartIdxAndCount[0] specifies the the
	 *            row index in the query result-set to begin collecting the
	 *            results. rowStartIdxAndCount[1] specifies the the maximum
	 *            count of results to return.
	 * @return List<Pollas> all Pollas entities
	 */
	@SuppressWarnings("unchecked")
	public List<Pollas> findAll(final int... rowStartIdxAndCount) {
		EntityManagerHelper.log("finding all Pollas instances", Level.INFO,
				null);
		try {
			final String queryString = "select model from Pollas model";
			Query query = getEntityManager().createQuery(queryString);
			if (rowStartIdxAndCount != null && rowStartIdxAndCount.length > 0) {
				int rowStartIdx = Math.max(0, rowStartIdxAndCount[0]);
				if (rowStartIdx > 0) {
					query.setFirstResult(rowStartIdx);
				}

				if (rowStartIdxAndCount.length > 1) {
					int rowCount = Math.max(0, rowStartIdxAndCount[1]);
					if (rowCount > 0) {
						query.setMaxResults(rowCount);
					}
				}
			}
			return query.getResultList();
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find all failed", Level.SEVERE, re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Pollas> findByNameByIdUser(String name, int idUser) {
		EntityManagerHelper.log("finding Pollas User with Name>> " + name
				+ " And IdUser>> " + idUser, Level.INFO, null);
		try {
			final String queryString = "select model from Pollas model where model.nombre= :propertyNamePolla And model.idUserAdmin= :propertyIdUser";

			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("propertyNamePolla", name);
			query.setParameter("propertyIdUser", idUser);

			getEntityManager().getEntityManagerFactory().getCache().evictAll();

			return query.getResultList();

		} catch (RuntimeException re) {
			EntityManagerHelper.log("findByNameByIdUser failed", Level.SEVERE,
					re);
			throw re;
		}
	}

}