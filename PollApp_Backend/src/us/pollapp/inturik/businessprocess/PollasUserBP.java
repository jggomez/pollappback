package us.pollapp.inturik.businessprocess;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import us.pollapp.inturik.businesslogic.LGuest;
import us.pollapp.inturik.businesslogic.LPolla;
import us.pollapp.inturik.businesslogic.LUser;
import us.pollapp.inturik.dao.EntityManagerHelper;
import us.pollapp.inturik.model.Guest;
import us.pollapp.inturik.model.Pollas;
import us.pollapp.inturik.model.User;
import us.pollapp.inturik.util.Messages;

public class PollasUserBP {

	private static final Logger log = Logger.getLogger(PollasUserBP.class
			.getName());

	/**
	 * Permite crear una polla y adicionar el usuario administrador
	 * 
	 * @param namePolla
	 *            Nombre de la polla
	 * @param idUser
	 *            Usuario admin
	 * @return idPolla
	 * @throws Exception
	 */
	public int createPolla(String namePolla, int idUser) throws Exception {

		try {

			LPolla objLPolla = new LPolla();

			List<Pollas> lstPollas = objLPolla.getPollaByNameByIdUser(
					namePolla.trim(), idUser);

			if (lstPollas != null && lstPollas.size() > 0) {
				throw new Exception(Messages.MSG_00006);
			}
			
			log.info("El nombre de la polla no existe");

			// Se crea la polla
			int idPolla = 0;
			
			try {
				EntityManagerHelper.beginTransaction();

				idPolla = objLPolla.addPolla(namePolla.trim(), new Date(), idUser);

				if (idPolla == 0) {
					throw new Exception(Messages.MSG_00001);
				}

				// Se adiciona el usuario admin a la polla
				objLPolla.addUserPolla(idUser, idPolla, true);

				LUser objLUser = new LUser();
				User objUser = objLUser.getUserById(idUser);
				String email = objUser.getEmail();

				// Se registra como invitado
				LGuest objLGuest = new LGuest();
				objLGuest.addGuest(email, idPolla, idUser);			
				objLGuest.updateAcceptedInvitation(idPolla, idUser, true);

				EntityManagerHelper.commit();
				
			} catch (Exception e) {
				EntityManagerHelper.rollback();
			}

			return idPolla;

		} catch (Exception e) {			
			throw e;
		}

	}

	/**
	 * Adiciona invitados a la polla
	 * 
	 * @param emails
	 *            correos electronicos de los invitados
	 * @param idPolla
	 *            Polla
	 * @param idUserAdmin
	 *            Usuario administrador
	 * @throws Exception
	 */
	public void addGuestPolla(List<String> emails, int idPolla, int idUserAdmin)
			throws Exception {

		try {

			log.info("Adicionar invitado a la polla");

			if (emails.size() > 0) {

				LUser objLUser = new LUser();
				User user;

				LGuest objLGuest = new LGuest();

				try {

					EntityManagerHelper.beginTransaction();

					List<Guest> lstGuest;

					for (String email : emails) {

						log.info("Adicionar invitado. Email : " + email);

						lstGuest = objLGuest.getGuestByPollaByEmail(idPolla,
								email);

						if (lstGuest.size() == 0) {

							user = objLUser.getUserByEmail(email);

							if (user != null) {
								log.info("Usuario ya tiene cuenta. Email : "
										+ email);
								objLGuest
										.addGuest(email, idPolla, user.getId());
							} else {
								log.info("Usuario no tiene cuenta. Email : "
										+ email);
								objLGuest.addGuest(email, idPolla, 0);
							}
						}
					}

					user = objLUser.getUserById(idUserAdmin);

					log.info("Usuario admin. User : " + user.getEmail());

					LPolla objLPolla = new LPolla();
					Pollas objPolla = objLPolla.getPollaById(idPolla);

					objLGuest.sendEmailGuestUsers(
							emails,
							String.format("%s %s", user.getName(),
									user.getLastname()), objPolla.getNombre());

					EntityManagerHelper.commit();

				} catch (Exception e) {
					EntityManagerHelper.rollback();
					throw e;
				}

			}

		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Registra si el invitado acepto la invitación a la polla o no la acepta
	 * 
	 * @param idUser
	 * @param idPolla
	 * @throws Exception
	 */
	public void acceptedInvitacion(int idUser, int idPolla, boolean accepted)
			throws Exception {

		try {						

			EntityManagerHelper.beginTransaction();

			LGuest objLGuest = new LGuest();

			objLGuest.updateAcceptedInvitation(idPolla, idUser, accepted);

			log.info("Se actualiza invitación");

			if (accepted) {
				LPolla objLPolla = new LPolla();

				// Se crea la polla
				Pollas polla = objLPolla.getPollaById(idPolla);

				log.info("Polla para registrar el invitado. Polla = "
						+ polla.getId());

				if (polla != null) {
					// Se adiciona el usuario admin a la polla
					objLPolla.addUserPolla(idUser, idPolla, false);
				}
			}

			EntityManagerHelper.commit();

		} catch (Exception e) {
			EntityManagerHelper.rollback();
			throw e;
		}

	}

}
