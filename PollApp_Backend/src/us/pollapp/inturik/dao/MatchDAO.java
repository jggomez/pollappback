package us.pollapp.inturik.dao;

import java.util.List;
import java.util.logging.Level;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import us.pollapp.inturik.model.Match;

/**
 * A data access object (DAO) providing persistence and search support for Match
 * entities. Transaction control of the save(), update() and delete() operations
 * must be handled externally by senders of these methods or must be manually
 * added to each of these methods for data to be persisted to the JPA datastore.
 * 
 * @see .Match
 * @author MyEclipse Persistence Tools
 */

public class MatchDAO implements IMatchDAO {
	// property constants
	public static final String TEAM_A = "teamA";
	public static final String TEAM_B = "teamB";
	public static final String HOUR = "hour";
	public static final String NAME_GROUP = "nameGroup";
	public static final String FLAG_TEAM_A = "flagTeamA";
	public static final String FLAG_TEAM_B = "flagTeamB";
	public static final String STADIUM = "stadium";
	public static final String RESULT_TEAM_A = "resultTeamA";
	public static final String RESULT_TEAM_B = "resultTeamB";
	public static final String ROUND = "round";

	private EntityManager getEntityManager() {
		return EntityManagerHelper.getEntityManager();
	}

	/**
	 * Perform an initial save of a previously unsaved Match entity. All
	 * subsequent persist actions of this entity should use the #update()
	 * method. This operation must be performed within the a database
	 * transaction context for the entity's data to be permanently saved to the
	 * persistence store, i.e., database. This method uses the
	 * {@link javax.persistence.EntityManager#persist(Object)
	 * EntityManager#persist} operation.
	 * 
	 * <pre>
	 * EntityManagerHelper.beginTransaction();
	 * MatchDAO.save(entity);
	 * EntityManagerHelper.commit();
	 * </pre>
	 * 
	 * @param entity
	 *            Match entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void save(Match entity) {
		EntityManagerHelper.log("saving Match instance", Level.INFO, null);
		try {
			getEntityManager().persist(entity);
			EntityManagerHelper.log("save successful", Level.INFO, null);
		} catch (RuntimeException re) {
			EntityManagerHelper.log("save failed", Level.SEVERE, re);
			throw re;
		}
	}

	/**
	 * Delete a persistent Match entity. This operation must be performed within
	 * the a database transaction context for the entity's data to be
	 * permanently deleted from the persistence store, i.e., database. This
	 * method uses the {@link javax.persistence.EntityManager#remove(Object)
	 * EntityManager#delete} operation.
	 * 
	 * <pre>
	 * EntityManagerHelper.beginTransaction();
	 * MatchDAO.delete(entity);
	 * EntityManagerHelper.commit();
	 * entity = null;
	 * </pre>
	 * 
	 * @param entity
	 *            Match entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(Match entity) {
		EntityManagerHelper.log("deleting Match instance", Level.INFO, null);
		try {
			entity = getEntityManager().getReference(Match.class,
					entity.getId());
			getEntityManager().remove(entity);
			EntityManagerHelper.log("delete successful", Level.INFO, null);
		} catch (RuntimeException re) {
			EntityManagerHelper.log("delete failed", Level.SEVERE, re);
			throw re;
		}
	}

	/**
	 * Persist a previously saved Match entity and return it or a copy of it to
	 * the sender. A copy of the Match entity parameter is returned when the JPA
	 * persistence mechanism has not previously been tracking the updated
	 * entity. This operation must be performed within the a database
	 * transaction context for the entity's data to be permanently saved to the
	 * persistence store, i.e., database. This method uses the
	 * {@link javax.persistence.EntityManager#merge(Object) EntityManager#merge}
	 * operation.
	 * 
	 * <pre>
	 * EntityManagerHelper.beginTransaction();
	 * entity = MatchDAO.update(entity);
	 * EntityManagerHelper.commit();
	 * </pre>
	 * 
	 * @param entity
	 *            Match entity to update
	 * @return Match the persisted Match entity instance, may not be the same
	 * @throws RuntimeException
	 *             if the operation fails
	 */
	public Match update(Match entity) {
		EntityManagerHelper.log("updating Match instance", Level.INFO, null);
		try {
			Match result = getEntityManager().merge(entity);
			EntityManagerHelper.log("update successful", Level.INFO, null);
			return result;
		} catch (RuntimeException re) {
			EntityManagerHelper.log("update failed", Level.SEVERE, re);
			throw re;
		}
	}

	public Match findById(Integer id) {
		EntityManagerHelper.log("finding Match instance with id: " + id,
				Level.INFO, null);
		try {
			Match instance = getEntityManager().find(Match.class, id);
			return instance;
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find failed", Level.SEVERE, re);
			throw re;
		}
	}

	/**
	 * Find all Match entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the Match property to query
	 * @param value
	 *            the property value to match
	 * @param rowStartIdxAndCount
	 *            Optional int varargs. rowStartIdxAndCount[0] specifies the the
	 *            row index in the query result-set to begin collecting the
	 *            results. rowStartIdxAndCount[1] specifies the the maximum
	 *            number of results to return.
	 * @return List<Match> found by query
	 */
	@SuppressWarnings("unchecked")
	public List<Match> findByProperty(String propertyName, final Object value,
			final int... rowStartIdxAndCount) {
		EntityManagerHelper.log("finding Match instance with property: "
				+ propertyName + ", value: " + value, Level.INFO, null);
		try {
			final String queryString = "select model from Match model where model."
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

	public List<Match> findByTeamA(Object teamA, int... rowStartIdxAndCount) {
		return findByProperty(TEAM_A, teamA, rowStartIdxAndCount);
	}

	public List<Match> findByTeamB(Object teamB, int... rowStartIdxAndCount) {
		return findByProperty(TEAM_B, teamB, rowStartIdxAndCount);
	}

	public List<Match> findByHour(Object hour, int... rowStartIdxAndCount) {
		return findByProperty(HOUR, hour, rowStartIdxAndCount);
	}

	public List<Match> findByNameGroup(Object nameGroup,
			int... rowStartIdxAndCount) {
		return findByProperty(NAME_GROUP, nameGroup, rowStartIdxAndCount);
	}

	public List<Match> findByFlagTeamA(Object flagTeamA,
			int... rowStartIdxAndCount) {
		return findByProperty(FLAG_TEAM_A, flagTeamA, rowStartIdxAndCount);
	}

	public List<Match> findByFlagTeamB(Object flagTeamB,
			int... rowStartIdxAndCount) {
		return findByProperty(FLAG_TEAM_B, flagTeamB, rowStartIdxAndCount);
	}

	public List<Match> findByStadium(Object stadium, int... rowStartIdxAndCount) {
		return findByProperty(STADIUM, stadium, rowStartIdxAndCount);
	}

	public List<Match> findByResultTeamA(Object resultTeamA,
			int... rowStartIdxAndCount) {
		return findByProperty(RESULT_TEAM_A, resultTeamA, rowStartIdxAndCount);
	}

	public List<Match> findByResultTeamB(Object resultTeamB,
			int... rowStartIdxAndCount) {
		return findByProperty(RESULT_TEAM_B, resultTeamB, rowStartIdxAndCount);
	}

	public List<Match> findByRound(Object round, int... rowStartIdxAndCount) {
		return findByProperty(ROUND, round, rowStartIdxAndCount);
	}

	/**
	 * Find all Match entities.
	 * 
	 * @param rowStartIdxAndCount
	 *            Optional int varargs. rowStartIdxAndCount[0] specifies the the
	 *            row index in the query result-set to begin collecting the
	 *            results. rowStartIdxAndCount[1] specifies the the maximum
	 *            count of results to return.
	 * @return List<Match> all Match entities
	 */
	@SuppressWarnings("unchecked")
	public List<Match> findAll(final int... rowStartIdxAndCount) {
		EntityManagerHelper
				.log("finding all Match instances", Level.INFO, null);
		try {
			final String queryString = "select model from Match model";
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
	public List<Match> findByRoundOrderByDate(final int idRound,
			final int... rowStartIdxAndCount) {
		EntityManagerHelper.log(
				"findByRoundOrderByDate by IdRound =" + idRound, Level.INFO,
				null);
		try {
			final String queryString = "select model from Match model Where model.round= :idRoundValue order by model.date asc";

			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("idRoundValue", idRound);

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
			EntityManagerHelper.log("findByRoundOrderByDate failed", Level.SEVERE, re);
			throw re;
		}
	}

}