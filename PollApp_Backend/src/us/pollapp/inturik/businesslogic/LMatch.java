package us.pollapp.inturik.businesslogic;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.google.appengine.api.memcache.Expiration;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

import us.pollapp.inturik.dao.EntityManagerHelper;
import us.pollapp.inturik.dao.IMatchDAO;
import us.pollapp.inturik.dao.IResultMatchDAO;
import us.pollapp.inturik.dao.MatchDAO;
import us.pollapp.inturik.dao.ResultMatchDAO;
import us.pollapp.inturik.model.Match;
import us.pollapp.inturik.model.Pollas;
import us.pollapp.inturik.model.ResultMatch;
import us.pollapp.inturik.model.User;
import us.pollapp.inturik.util.Constant;

public class LMatch {

	private static final Logger log = Logger.getLogger(LMatch.class.getName());

	/**
	 * Obtiene los marcadores de un partido dado por todos los participante de
	 * la polla
	 * 
	 * @param idPolla
	 * @param idMatch
	 * @param lstIdUser
	 * @return Lista de marcadores
	 * @throws Exception
	 */
	public List<ResultMatch> getResulstMatch(int idPolla, int idMatch,
			int pageNumber, int pageSize) throws Exception {

		List<ResultMatch> lstResultMatch = new ArrayList<>();

		try {

			// TODO: Validar entrada

			EntityManagerHelper.getEntityManager().getEntityManagerFactory()
					.getCache().evictAll();

			IResultMatchDAO objResultMatchDAO = new ResultMatchDAO();

			lstResultMatch = objResultMatchDAO.findByIdPollaByMatch(idPolla,
					idMatch, pageNumber, pageSize);

			log.info("Numero de marcadores de los participantes = "
					+ lstResultMatch.size());

		} catch (Exception e) {
			throw e;
		}

		return lstResultMatch;

	}

	/**
	 * Adiciona un resultado al partido dado por un usuario
	 * 
	 * @param idUser
	 * @param idPolla
	 * @param idMatch
	 * @param scoreTeamA
	 * @param scoreTeamB
	 * @throws Exception
	 */
	public void setResultMatch(int idUser, int idPolla, int idMatch,
			int scoreTeamA, int scoreTeamB) throws Exception {

		try {

			// TODO: Validar entrada

			log.info(String
					.format("Adicionar resultado. IdUser = %s, IdPolla = %s, IdMatch = %s, ScoreTeamA = %s, ScoreTeamB = %s ",
							idUser, idPolla, idMatch, scoreTeamA, scoreTeamB));

			IResultMatchDAO objResultMatchDAO = new ResultMatchDAO();
			ResultMatch result = objResultMatchDAO.findByIdPollaByMatchByUser(
					idPolla, idMatch, idUser);

			if (result != null) {

				log.info("Se actualiza el marcador");

				result.setScoreTeamA(scoreTeamA);
				result.setScoreTeamB(scoreTeamB);

				try {

					EntityManagerHelper.beginTransaction();
					objResultMatchDAO.update(result);
					EntityManagerHelper.commit();

				} catch (Exception e) {
					log.info("Error = " + e.getMessage());
					EntityManagerHelper.rollback();
					throw e;
				}

			} else {

				log.info("Se crea el marcador");

				ResultMatch objResult = new ResultMatch();

				Pollas objPollas = new Pollas();
				objPollas.setId(idPolla);

				Match objMatch = new Match();
				objMatch.setId(idMatch);

				User user = new User();
				user.setId(idUser);

				objResult.setPollas(objPollas);
				objResult.setMatch(objMatch);
				objResult.setUser(user);
				objResult.setScoreTeamA(scoreTeamA);
				objResult.setScoreTeamB(scoreTeamB);

				try {

					EntityManagerHelper.beginTransaction();
					objResultMatchDAO.save(objResult);
					EntityManagerHelper.commit();

				} catch (Exception e) {
					log.info("Error = " + e.getMessage());
					EntityManagerHelper.rollback();
					throw e;
				}

			}

		} catch (Exception e) {
			throw e;
		}

	}

	/**
	 * Actualiza el score por partido
	 * 
	 * @param idMatch
	 * @param scoreTeamAOrg
	 * @param scoreTeamBOrg
	 * @throws Exception
	 */
	public void updateScoreMatchOriginal(int idMatch, int scoreTeamAOrg,
			int scoreTeamBOrg) throws Exception {

		try {

			IMatchDAO objMatchDAO = new MatchDAO();
			Match objMatch = objMatchDAO.findById(idMatch);

			objMatch.setResultTeamA(scoreTeamAOrg);
			objMatch.setResultTeamB(scoreTeamBOrg);

			EntityManagerHelper.beginTransaction();

			objMatchDAO.update(objMatch);

			log.info("Se actualiza el resultado del partido");

			calculatePointsByMatch(idMatch, scoreTeamAOrg, scoreTeamBOrg);

			EntityManagerHelper.commit();

		} catch (Exception e) {
			EntityManagerHelper.rollback();
			throw e;
		}

	}

	/**
	 * Registra los puntos por partido y actualiza los puntos totales
	 * 
	 * @param idMatch
	 * @param scoreTeamAOrg
	 * @param scoreTeamBOrg
	 * @throws Exception
	 */
	private void calculatePointsByMatch(int idMatch, int scoreTeamAOrg,
			int scoreTeamBOrg) throws Exception {

		try {

			IResultMatchDAO objResultMatchDAO = new ResultMatchDAO();
			List<ResultMatch> lstResult = objResultMatchDAO.findByIdMatch(
					idMatch, null);

			LPolla objLPolla = new LPolla();

			int pointsMatchNew = 0;
			int pointsMatchOld = 0;

			log.info("Se calculan puntos por partido. Resultados a calcular = "
					+ lstResult.size());

			for (ResultMatch resultMatch : lstResult) {

				pointsMatchNew = calculatePoints(
						resultMatch.getScoreTeamA() == null ? 0
								: resultMatch.getScoreTeamA(),
						resultMatch.getScoreTeamB() == null ? 0 : resultMatch
								.getScoreTeamB(), scoreTeamAOrg, scoreTeamBOrg);

				pointsMatchOld = resultMatch.getTotalScore() == null ? 0
						: resultMatch.getTotalScore();
				resultMatch.setTotalScore(pointsMatchNew);

				log.info("ResultMatch ID = " + resultMatch.getId());
				log.info("pointsMatchOld = " + pointsMatchOld);
				log.info("pointsMatchNew = " + pointsMatchNew);

				objResultMatchDAO.update(resultMatch);

				objLPolla.updateTotalScoresByUser(resultMatch.getPollas()
						.getId(), resultMatch.getUser().getId(),
						pointsMatchNew, pointsMatchOld);

			}

		} catch (Exception e) {
			throw e;
		}

	}

	/**
	 * Calcula los puntos segun los resultados
	 * 
	 * @param scoreTeamAUser
	 *            Marcador dado por el usuario para el equipo A
	 * @param scoreTeamBUser
	 *            Marcador dado por el usuario para el equipo B
	 * @param scoreTeamAOrig
	 *            Marcador original para el equipo A
	 * @param scoreTeamBOrig
	 *            Marcador original para el equipo B
	 * @return Puntos obtenidos
	 */
	private int calculatePoints(int scoreTeamAUser, int scoreTeamBUser,
			int scoreTeamAOrig, int scoreTeamBOrig) {

		int totalPoints = 0;

		if (scoreTeamAUser == scoreTeamAOrig
				&& scoreTeamBUser == scoreTeamBOrig) {
			totalPoints = 3;
		} else if (scoreTeamAOrig > scoreTeamBOrig) {
			if (scoreTeamAUser > scoreTeamBUser) {
				totalPoints = 1;
			}
		} else if (scoreTeamBOrig > scoreTeamAOrig) {
			if (scoreTeamBUser > scoreTeamAUser) {
				totalPoints = 1;
			}
		} else if (scoreTeamAOrig == scoreTeamBOrig) {
			if (scoreTeamAUser == scoreTeamBUser) {
				totalPoints = 1;
			}
		}

		return totalPoints;

	}

	/**
	 * Obtiene los resultados de todos los partidos de una polla dado por un
	 * usuario
	 * 
	 * @param idPolla
	 * @param idUser
	 * @return List<ResultMatch>
	 * @throws Exception
	 */
	public List<ResultMatch> getResultMatchByPollaByUser(int idPolla,
			int idUser, int pageNumber, int pageSize) throws Exception {

		List<ResultMatch> lstResultMatch = new ArrayList<>();

		try {

			// TODO: Validar entrada

			IResultMatchDAO objResultMatchDAO = new ResultMatchDAO();

			lstResultMatch = objResultMatchDAO.findByIdPollaByUser(idPolla,
					idUser, pageNumber, pageSize);

			return lstResultMatch;

		} catch (Exception e) {
			throw e;
		}

	}

	/**
	 * Obtiene los partidos por ronda (octavos, cuartos, semifinales, finales)
	 * 
	 * @param idRound
	 * @param pageNumber
	 * @param pageSize
	 * @return Lista de partidos
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Match> getMatchsByRound(int idRound, int pageNumber,
			int pageSize) throws Exception {

		List<Match> lstMatch = new ArrayList<>();

		try {

			// TODO: Validar entrada
	
			MemcacheService objCache = MemcacheServiceFactory
					.getMemcacheService();

			IMatchDAO objMatchDAO = new MatchDAO();

			if (idRound == 1) {

				if (!objCache.contains(Constant.KEY_CACHE_MATCH_ROUND1)) {

					log.info("Los match round 1 no se encuentran en cache. Se carga por primera vez");

					lstMatch = objMatchDAO.findByRoundOrderByDate(idRound,
							pageNumber, pageSize);

					objCache.put(
							Constant.KEY_CACHE_MATCH_ROUND1,
							lstMatch,
							Expiration
									.byDeltaSeconds(Constant.SECONDS_EXPIRATION_MATCH));
				} else {

					log.info("Se cargan los matchs round 1 desde cache");

					lstMatch = (List<Match>) objCache
							.get(Constant.KEY_CACHE_MATCH_ROUND1);

				}
				
				return lstMatch;

			}

			if (idRound == 2) {

				if (!objCache.contains(Constant.KEY_CACHE_MATCH_ROUND2)) {

					log.info("Los match round 2 no se encuentran en cache. Se carga por primera vez");

					lstMatch = objMatchDAO.findByRoundOrderByDate(idRound,
							pageNumber, pageSize);

					objCache.put(
							Constant.KEY_CACHE_MATCH_ROUND2,
							lstMatch,
							Expiration
									.byDeltaSeconds(Constant.SECONDS_EXPIRATION_MATCH));
				} else {

					log.info("Se cargan los matchs round 2 desde cache");

					lstMatch = (List<Match>) objCache
							.get(Constant.KEY_CACHE_MATCH_ROUND2);

				}
				
				return lstMatch;

			}

			if (idRound == 3) {

				if (!objCache.contains(Constant.KEY_CACHE_MATCH_ROUND3)) {

					log.info("Los match round 3 no se encuentran en cache. Se carga por primera vez");

					lstMatch = objMatchDAO.findByRoundOrderByDate(idRound,
							pageNumber, pageSize);

					objCache.put(
							Constant.KEY_CACHE_MATCH_ROUND3,
							lstMatch,
							Expiration
									.byDeltaSeconds(Constant.SECONDS_EXPIRATION_MATCH));
				} else {

					log.info("Se cargan los matchs round 3 desde cache");

					lstMatch = (List<Match>) objCache
							.get(Constant.KEY_CACHE_MATCH_ROUND3);

				}

				return lstMatch;
			}
			
			lstMatch = objMatchDAO.findByRoundOrderByDate(idRound,
					pageNumber, pageSize);

			return lstMatch;

		} catch (Exception e) {
			throw e;
		}

	}

}
