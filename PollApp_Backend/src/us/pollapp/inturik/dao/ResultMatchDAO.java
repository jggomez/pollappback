package us.pollapp.inturik.dao;

import java.util.List;
import java.util.logging.Level;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import us.pollapp.inturik.model.ResultMatch;

/**
 * A data access object (DAO) providing persistence and search support for
 * ResultMatch entities. Transaction control of the save(), update() and
 * delete() operations must be handled externally by senders of these methods or
 * must be manually added to each of these methods for data to be persisted to
 * the JPA datastore.
 * 
 * @see .ResultMatch
 * @author MyEclipse Persistence Tools
 */

public class ResultMatchDAO implements IResultMatchDAO {
	// property constants
	// property constants
	public static final String SCORE_TEAM_A = "scoreTeamA";
	public static final String SCORE_TEAM_B = "scoreTeamB";
	public static final String TOTAL_SCORE = "totalScore";

	private EntityManager getEntityManager() {
		return EntityManagerHelper.getEntityManager();
	}

	/**
	 * Perform an initial save of a previously unsaved ResultMatch entity. All
	 * subsequent persist actions of this entity should use the #update()
	 * method. This operation must be performed within the a database
	 * transaction context for the entity's data to be permanently saved to the
	 * persistence store, i.e., database. This method uses the
	 * {@link javax.persistence.EntityManager#persist(Object)
	 * EntityManager#persist} operation.
	 * 
	 * <pre>
	 * EntityManagerHelper.beginTransaction();
	 * ResultMatchDAO.save(entity);
	 * EntityManagerHelper.commit();
	 * </pre>
	 * 
	 * @param entity
	 *            ResultMatch entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void save(ResultMatch entity) {
		EntityManagerHelper
				.log("saving ResultMatch instance", Level.INFO, null);
		try {
			getEntityManager().persist(entity);
			EntityManagerHelper.log("save successful", Level.INFO, null);
		} catch (RuntimeException re) {
			EntityManagerHelper.log("save failed", Level.SEVERE, re);
			throw re;
		}
	}

	/**
	 * Delete a persistent ResultMatch entity. This operation must be performed
	 * within the a database transaction context for the entity's data to be
	 * permanently deleted from the persistence store, i.e., database. This
	 * method uses the {@link javax.persistence.EntityManager#remove(Object)
	 * EntityManager#delete} operation.
	 * 
	 * <pre>
	 * EntityManagerHelper.beginTransaction();
	 * ResultMatchDAO.delete(entity);
	 * EntityManagerHelper.commit();
	 * entity = null;
	 * </pre>
	 * 
	 * @param entity
	 *            ResultMatch entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(ResultMatch entity) {
		EntityManagerHelper.log("deleting ResultMatch instance", Level.INFO,
				null);
		try {
			entity = getEntityManager().getReference(ResultMatch.class,
					entity.getId());
			getEntityManager().remove(entity);
			EntityManagerHelper.log("delete successful", Level.INFO, null);
		} catch (RuntimeException re) {
			EntityManagerHelper.log("delete failed", Level.SEVERE, re);
			throw re;
		}
	}

	/**
	 * Persist a previously saved ResultMatch entity and return it or a copy of
	 * it to the sender. A copy of the ResultMatch entity parameter is returned
	 * when the JPA persistence mechanism has not previously been tracking the
	 * updated entity. This operation must be performed within the a database
	 * transaction context for the entity's data to be permanently saved to the
	 * persistence store, i.e., database. This method uses the
	 * {@link javax.persistence.EntityManager#merge(Object) EntityManager#merge}
	 * operation.
	 * 
	 * <pre>
	 * EntityManagerHelper.beginTransaction();
	 * entity = ResultMatchDAO.update(entity);
	 * EntityManagerHelper.commit();
	 * </pre>
	 * 
	 * @param entity
	 *            ResultMatch entity to update
	 * @return ResultMatch the persisted ResultMatch entity instance, may not be
	 *         the same
	 * @throws RuntimeException
	 *             if the operation fails
	 */
	public ResultMatch update(ResultMatch entity) {
		EntityManagerHelper.log("updating ResultMatch instance", Level.INFO,
				null);
		try {
			ResultMatch result = getEntityManager().merge(entity);
			EntityManagerHelper.log("update successful", Level.INFO, null);
			return result;
		} catch (RuntimeException re) {
			EntityManagerHelper.log("update failed", Level.SEVERE, re);
			throw re;
		}
	}

	public ResultMatch findById(Integer id) {
		EntityManagerHelper.log("finding ResultMatch instance with id: " + id,
				Level.INFO, null);
		try {
			ResultMatch instance = getEntityManager().find(ResultMatch.class,
					id);
			return instance;
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find failed", Level.SEVERE, re);
			throw re;
		}
	}

	/**
	 * Find all ResultMatch entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the ResultMatch property to query
	 * @param value
	 *            the property value to match
	 * @param rowStartIdxAndCount
	 *            Optional int varargs. rowStartIdxAndCount[0] specifies the the
	 *            row index in the query result-set to begin collecting the
	 *            results. rowStartIdxAndCount[1] specifies the the maximum
	 *            number of results to return.
	 * @return List<ResultMatch> found by query
	 */
	@SuppressWarnings("unchecked")
	public List<ResultMatch> findByProperty(String propertyName,
			final Object value, final int... rowStartIdxAndCount) {
		EntityManagerHelper.log("finding ResultMatch instance with property: "
				+ propertyName + ", value: " + value, Level.INFO, null);
		try {
			final String queryString = "select model from ResultMatch model where model."
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

	public List<ResultMatch> findByScoreTeamA(Object scoreTeamA,
			int... rowStartIdxAndCount) {
		return findByProperty(SCORE_TEAM_A, scoreTeamA, rowStartIdxAndCount);
	}

	public List<ResultMatch> findByScoreTeamB(Object scoreTeamB,
			int... rowStartIdxAndCount) {
		return findByProperty(SCORE_TEAM_B, scoreTeamB, rowStartIdxAndCount);
	}

	public List<ResultMatch> findByTotalScore(Object totalScore,
			int... rowStartIdxAndCount) {
		return findByProperty(TOTAL_SCORE, totalScore, rowStartIdxAndCount);
	}

	/**
	 * Find all ResultMatch entities.
	 * 
	 * @param rowStartIdxAndCount
	 *            Optional int varargs. rowStartIdxAndCount[0] specifies the the
	 *            row index in the query result-set to begin collecting the
	 *            results. rowStartIdxAndCount[1] specifies the the maximum
	 *            count of results to return.
	 * @return List<ResultMatch> all ResultMatch entities
	 */
	@SuppressWarnings("unchecked")
	public List<ResultMatch> findAll(final int... rowStartIdxAndCount) {
		EntityManagerHelper.log("finding all ResultMatch instances",
				Level.INFO, null);
		try {
			final String queryString = "select model from ResultMatch model";
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
	public List<ResultMatch> findByIdPollaMatchByUsers(final int idPollaMatch,
			final String idUsers, final int... rowStartIdxAndCount) {

		EntityManagerHelper.log(
				"finding ResultMatch instance with IdPollaMatch: "
						+ idPollaMatch + ", IdUser: " + idUsers, Level.INFO,
				null);

		try {
			final String queryString = "select model from ResultMatch model where model.pollaMatch.id"
					+ "= :idPollaMatchValue And model.user.id in (:idUsersValue) Order By model.user.name asc";

			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("idPollaMatchValue", idPollaMatch);
			query.setParameter("idUsersValue", idUsers);

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

			getEntityManager().getEntityManagerFactory().getCache().evictAll();
			
			return query.getResultList();

		} catch (RuntimeException re) {
			EntityManagerHelper.log("findByIdPollaMatchByUsers failed",
					Level.SEVERE, re);
			throw re;
		}
	}

	public ResultMatch findByIdPollaByMatchByUser(final int idPolla,
			final int idMatch, final int idUser) {

		EntityManagerHelper.log("finding ResultMatch instance with IdPolla: "
				+ idPolla + ", IdMatch: " + idMatch + ", IdUser: " + idUser,
				Level.INFO, null);

		try {
			final String queryString = "select model from ResultMatch model where model.pollas.id"
					+ "= :idPollaValue And model.match.id= :idMatchValue And model.user.id= :idUserValue";

			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("idPollaValue", idPolla);
			query.setParameter("idMatchValue", idMatch);
			query.setParameter("idUserValue", idUser);

			getEntityManager().getEntityManagerFactory().getCache().evictAll();
			
			return (ResultMatch) query.getSingleResult();

		} catch (NoResultException re) {
			return null;
		} catch (RuntimeException re) {
			EntityManagerHelper.log("findByIdPollaMatchByUser failed",
					Level.SEVERE, re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<ResultMatch> findByIdPollaByMatch(final int idPolla,
			final int idMatch, final int... rowStartIdxAndCount) {

		EntityManagerHelper.log("finding ResultMatch instance with IdPolla: "
				+ idPolla + " And IdMatch = " + idMatch, Level.INFO, null);

		try {

			final String queryString = "select model from ResultMatch model where model.pollas.id= :idPollaValue"
					+ " And model.match.id= :idMatchValue order by model.user.name asc";

			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("idPollaValue", idPolla);
			query.setParameter("idMatchValue", idMatch);

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

			getEntityManager().getEntityManagerFactory().getCache().evictAll();
			
			return query.getResultList();

		} catch (RuntimeException re) {
			EntityManagerHelper.log("findByIdPollaByMatch failed",
					Level.SEVERE, re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<ResultMatch> findByIdPolla(final int idPolla,
			final int... rowStartIdxAndCount) {

		EntityManagerHelper.log("finding ResultMatch instance with IdPolla: "
				+ idPolla, Level.INFO, null);

		try {

			final String queryString = "select model from ResultMatch model where model.pollas.id= :idPollaValue";

			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("idPollaValue", idPolla);

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

			getEntityManager().getEntityManagerFactory().getCache().evictAll();
			
			return query.getResultList();

		} catch (RuntimeException re) {
			EntityManagerHelper.log("findByIdPollaByMatch failed",
					Level.SEVERE, re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<ResultMatch> findByIdPollaByUser(final int idPolla,
			final int idUser, final int... rowStartIdxAndCount) {

		EntityManagerHelper.log("finding ResultMatch instance with idPolla: "
				+ idPolla + ", idUser: " + idUser, Level.INFO, null);

		try {
			final String queryString = "select model from ResultMatch model where model.pollas.id= :idPollaValue"
					+ " And model.user.id= :idUserValue Order By model.match.date asc";

			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("idPollaValue", idPolla);
			query.setParameter("idUserValue", idUser);

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

			getEntityManager().getEntityManagerFactory().getCache().evictAll();
			
			return query.getResultList();

		} catch (RuntimeException re) {
			EntityManagerHelper.log("findByIdPollaByUser failed",
					Level.SEVERE, re);
			throw re;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<ResultMatch> findByIdMatch(final int idMatch, final int... rowStartIdxAndCount) {

		EntityManagerHelper.log("finding ResultMatch instance with idMatch: "
				+ idMatch, Level.INFO, null);

		try {
			final String queryString = "select model from ResultMatch model where model.match.id= :idMatchValue";

			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("idMatchValue", idMatch);			

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

			getEntityManager().getEntityManagerFactory().getCache().evictAll();
			
			return query.getResultList();

		} catch (RuntimeException re) {
			EntityManagerHelper.log("findByIdPollaByUser failed",
					Level.SEVERE, re);
			throw re;
		}
	}

}