package us.pollapp.inturik.businesslogic;

import java.util.Hashtable;
import java.util.List;

import us.pollapp.inturik.dao.BallotDAO;
import us.pollapp.inturik.dao.IBallotDAO;
import us.pollapp.inturik.model.Ballot;
import us.pollapp.inturik.model.Model;
import us.pollapp.inturik.model.User;

public class LBallot {

	private IBallotDAO ballotDAO;

	public LBallot() {
		ballotDAO = new BallotDAO();
	}

	public boolean isUserVote(int idUser, int idModel) throws Exception {

		try {

			List<Ballot> lstBallot = ballotDAO.findByIdModelAndIdUser(idModel,
					idUser);

			if (lstBallot != null && lstBallot.size() == 1) {
				return true;
			}

			return false;

		} catch (Exception e) {
			throw e;
		}

	}

	public Hashtable<Integer, Long> countBallotByModel(List<Integer> lstModels)
			throws Exception {

		try {

			Hashtable<Integer, Long> resultsCountModels = new Hashtable<>();
			long countVoteModel;

			for (Integer idModel : lstModels) {
				countVoteModel = ballotDAO.countBallotByModel(idModel);
				resultsCountModels.put(idModel, countVoteModel);
			}

			return resultsCountModels;

		} catch (Exception e) {
			throw e;
		}

	}

	public void addScoreModel(int idModel, int idUser, int newScore)
			throws Exception {

		try {

			if (idModel > 0 && idUser > 0 && newScore > 0) {

				Ballot objBallot = new Ballot();
				objBallot
						.setScore(Double.parseDouble(String.valueOf(newScore)));

				Model objModel = new Model();
				objModel.setId(idModel);

				objBallot.setModel(objModel);

				User objUser = new User();
				objUser.setId(idUser);

				objBallot.setUser(objUser);

				ballotDAO.save(objBallot);
			}

		} catch (Exception e) {
			throw e;
		}

	}

}
