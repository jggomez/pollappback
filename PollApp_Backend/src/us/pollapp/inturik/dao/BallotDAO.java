package us.pollapp.inturik.dao;

import java.util.List;
import java.util.logging.Level;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import us.pollapp.inturik.model.Ballot;

public class BallotDAO implements IBallotDAO {
	// property constants

	private EntityManager getEntityManager() {
		return EntityManagerHelper.getEntityManager();
	}

	public void save(Ballot entity) {
		EntityManagerHelper.log("saving Ballot instance", Level.INFO, null);
		try {
			getEntityManager().persist(entity);
			EntityManagerHelper.log("save successful", Level.INFO, null);
		} catch (RuntimeException re) {
			EntityManagerHelper.log("save failed", Level.SEVERE, re);
			throw re;
		}
	}

	public void delete(Ballot entity) {
		EntityManagerHelper.log("deleting Ballot instance", Level.INFO, null);
		try {
			entity = getEntityManager().getReference(Ballot.class,
					entity.getId());
			getEntityManager().remove(entity);
			EntityManagerHelper.log("delete successful", Level.INFO, null);
		} catch (RuntimeException re) {
			EntityManagerHelper.log("delete failed", Level.SEVERE, re);
			throw re;
		}
	}

	public Ballot update(Ballot entity) {
		EntityManagerHelper.log("updating Ballot instance", Level.INFO, null);
		try {
			Ballot result = getEntityManager().merge(entity);
			EntityManagerHelper.log("update successful", Level.INFO, null);
			return result;
		} catch (RuntimeException re) {
			EntityManagerHelper.log("update failed", Level.SEVERE, re);
			throw re;
		}
	}

	public Ballot findById(Integer id) {
		EntityManagerHelper.log("finding Ballot instance with id: " + id,
				Level.INFO, null);
		try {
			Ballot instance = getEntityManager().find(Ballot.class, id);
			return instance;
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find failed", Level.SEVERE, re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Ballot> findByProperty(String propertyName, final Object value,
			final int... rowStartIdxAndCount) {
		EntityManagerHelper.log("finding Ballot instance with property: "
				+ propertyName + ", value: " + value, Level.INFO, null);
		try {
			final String queryString = "select ballot from Ballot ballot where ballot."
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

	@SuppressWarnings("unchecked")
	public List<Ballot> findAll(final int... rowStartIdxAndCount) {
		EntityManagerHelper.log("finding all Ballot instances", Level.INFO,
				null);
		try {
			final String queryString = "select ballot from Ballot ballot";
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
	public List<Ballot> findByIdModelAndIdUser(int idModel, int idUser) {
		EntityManagerHelper.log("findByIdModelAndIdUser ballot", Level.INFO,
				null);
		try {
			final String queryString = "select ballot from Ballot ballot where ballot.model.id = :propertyIdModel And ballot.user.id = :propertyIdUser";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("propertyIdModel", idModel);
			query.setParameter("propertyIdUser", idUser);
			return query.getResultList();
		} catch (RuntimeException re) {
			EntityManagerHelper.log("findByIdModelAndIdUser failed",
					Level.SEVERE, re);
			throw re;
		}
	}	

	public long countBallotByModel(int idModel) {
		EntityManagerHelper.log("Count Ballot ballot", Level.INFO, null);
		try {
			final String queryString = "select count(ballot) from Ballot ballot where ballot.model.id = :propertyValue";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("propertyValue", idModel);
			return ((long) query.getSingleResult());
		} catch (RuntimeException re) {
			EntityManagerHelper.log("Count Ballot Model failed", Level.SEVERE,
					re);
			throw re;
		}
	}

}