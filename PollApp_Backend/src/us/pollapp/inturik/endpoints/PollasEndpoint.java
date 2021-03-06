package us.pollapp.inturik.endpoints;

import java.util.List;

import us.pollapp.inturik.DTO.ContextResponseDTO;
import us.pollapp.inturik.DTO.GuestDTO;
import us.pollapp.inturik.DTO.Messages.MsgAddGuestPollaRequest;
import us.pollapp.inturik.DTO.Messages.MsgAddGuestPollaResponse;
import us.pollapp.inturik.DTO.Messages.MsgAddPollaRequest;
import us.pollapp.inturik.DTO.Messages.MsgAddPollaResponse;
import us.pollapp.inturik.DTO.Messages.MsgGetGuestByUserResponse;
import us.pollapp.inturik.DTO.Messages.MsgGetAllGuestUsersResponse;
import us.pollapp.inturik.DTO.Messages.MsgGetPollasByUserResponse;
import us.pollapp.inturik.DTO.Messages.MsgRemoveGuestUserResponse;
import us.pollapp.inturik.DTO.Messages.MsgUpdateAcceptedInvitationRequest;
import us.pollapp.inturik.DTO.Messages.MsgUpdateAcceptedInvitationResponse;
import us.pollapp.inturik.businesslogic.LGuest;
import us.pollapp.inturik.businesslogic.LPolla;
import us.pollapp.inturik.businessprocess.PollasUserBP;
import us.pollapp.inturik.endpoints.utils.UtilContext;
import us.pollapp.inturik.model.Guest;
import us.pollapp.inturik.model.Pollas;
import us.pollapp.inturik.model.User;
import us.pollapp.inturik.transformDTO.GuestTransform;
import us.pollapp.inturik.transformDTO.PollaTransform;
import us.pollapp.inturik.transformDTO.UserTransform;
import us.pollapp.inturik.util.Error;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;

@Api(name = "pollasendpoint", namespace = @ApiNamespace(ownerDomain = "pollapp.us", ownerName = "pollapp.us", packagePath = "inturik.endpoints"), version = "v1")
public class PollasEndpoint {

	@ApiMethod(name = "addPolla", path = "addPolla", httpMethod = HttpMethod.POST)
	public MsgAddPollaResponse addPolla(MsgAddPollaRequest msgRequest)
			throws Exception {

		MsgAddPollaResponse msgResponse = new MsgAddPollaResponse();

		try {

			ContextResponseDTO contextResponse = UtilContext
					.getFillContextResponseDTOBasic(msgRequest
							.getContextRequestDTO());
			msgResponse.setContextResponse(contextResponse);

			PollasUserBP objPollasUserBP = new PollasUserBP();

			int idPolla = objPollasUserBP.createPolla(
					msgRequest.getNamePolla(), msgRequest.getIdUser());

			objPollasUserBP.addGuestPolla(msgRequest.getEmails(), idPolla,
					msgRequest.getIdUser());

			msgResponse.setIdPolla(idPolla);

		} catch (Exception e) {
			msgResponse.getContextResponse().setTransactionState(false);
			Error.SendError(e.getMessage());
			throw e;
		}

		return msgResponse;

	}

	@ApiMethod(name = "addGuestPolla", path = "addGuestPolla", httpMethod = HttpMethod.POST)
	public MsgAddGuestPollaResponse addGuestPolla(
			MsgAddGuestPollaRequest msgRequest) throws Exception {

		MsgAddGuestPollaResponse msgResponse = new MsgAddGuestPollaResponse();

		try {

			ContextResponseDTO contextResponse = UtilContext
					.getFillContextResponseDTOBasic(msgRequest
							.getContextRequestDTO());

			msgResponse.setContextResponse(contextResponse);

			PollasUserBP objPollasUserBP = new PollasUserBP();

			objPollasUserBP.addGuestPolla(msgRequest.getEmails(),
					msgRequest.getIdPolla(), msgRequest.getIdUserAdmin());

		} catch (Exception e) {
			msgResponse.getContextResponse().setTransactionState(false);
			throw e;
		}

		return msgResponse;

	}

	@ApiMethod(name = "getPollasByUser", path = "getPollasByUser", httpMethod = HttpMethod.GET)
	public MsgGetPollasByUserResponse getPollasByUser(
			@Named("idTransaction") String idTransaction,
			@Named("user") String user,
			@Named("application") String application,
			@Named("idUser") int idUser) throws Exception {

		MsgGetPollasByUserResponse msgResponse = new MsgGetPollasByUserResponse();

		try {

			ContextResponseDTO contextResponse = UtilContext
					.getFillContextResponseDTOBasic(idTransaction);

			msgResponse.setContextResponse(contextResponse);

			LPolla objLPolla = new LPolla();

			List<Pollas> lstPollas = objLPolla.getPollasByUser(idUser);

			PollaTransform objPollaTransform = new PollaTransform();

			msgResponse.setLstPollaDTO(objPollaTransform
					.transformListDTO(lstPollas));

		} catch (Exception e) {
			msgResponse.getContextResponse().setTransactionState(false);
			Error.SendError(e.getMessage());
			throw e;
		}

		return msgResponse;

	}

	@ApiMethod(name = "getGuestByUser", path = "getGuestByUser", httpMethod = HttpMethod.GET)
	public MsgGetGuestByUserResponse getGuestByUser(
			@Named("idTransaction") String idTransaction,
			@Named("user") String user,
			@Named("application") String application,
			@Named("idUser") int idUser) throws Exception {

		MsgGetGuestByUserResponse msgResponse = new MsgGetGuestByUserResponse();

		try {

			ContextResponseDTO contextResponse = UtilContext
					.getFillContextResponseDTOBasic(idTransaction);

			msgResponse.setContextResponse(contextResponse);

			LGuest objLGuest = new LGuest();

			List<Guest> lstGuest = objLGuest.getGuestByUser(idUser);

			GuestTransform objGuestTransform = new GuestTransform();

			List<GuestDTO> lstGuestDTO = objGuestTransform
					.transformListDTO(lstGuest);

			if (lstGuestDTO != null && lstGuestDTO.size() > 0) {

				int idPolla;
				LPolla objLPolla = new LPolla();
				UserTransform userTrans = new UserTransform();
				User userAdmin;

				for (GuestDTO guestDTO : lstGuestDTO) {
					idPolla = guestDTO.getPollaDTO().getId();
					userAdmin = objLPolla.getUserAdminByPolla(idPolla);
					guestDTO.setUserAdmin(userTrans.transformDTO(userAdmin));
				}
			}

			msgResponse.setLstGuestDTO(lstGuestDTO);

		} catch (Exception e) {
			msgResponse.getContextResponse().setTransactionState(false);
			Error.SendError(e.getMessage());
			throw e;
		}

		return msgResponse;

	}

	@ApiMethod(name = "getAllGuestUsers", path = "getAllGuestUsers", httpMethod = HttpMethod.GET)
	public MsgGetAllGuestUsersResponse getAllGuestUsers(
			@Named("idTransaction") String idTransaction,
			@Named("user") String user,
			@Named("application") String application,
			@Named("idPolla") int idPolla) throws Exception {

		MsgGetAllGuestUsersResponse msgResponse = new MsgGetAllGuestUsersResponse();

		try {

			ContextResponseDTO contextResponse = UtilContext
					.getFillContextResponseDTOBasic(idTransaction);

			msgResponse.setContextResponse(contextResponse);

			LGuest objLGuest = new LGuest();

			List<Guest> lstGuest = objLGuest.getGuestByPolla(idPolla);

			GuestTransform objGuestTransform = new GuestTransform();

			msgResponse.setLstGuestDTO(objGuestTransform
					.transformListDTO(lstGuest));

		} catch (Exception e) {
			msgResponse.getContextResponse().setTransactionState(false);
			Error.SendError(e.getMessage());
			throw e;
		}

		return msgResponse;

	}

	@ApiMethod(name = "updateAcceptedInvitation", path = "updateAcceptedInvitation", httpMethod = HttpMethod.PUT)
	public MsgUpdateAcceptedInvitationResponse updateAcceptedInvitation(
			MsgUpdateAcceptedInvitationRequest msgRequest) throws Exception {

		MsgUpdateAcceptedInvitationResponse msgResponse = new MsgUpdateAcceptedInvitationResponse();

		try {

			ContextResponseDTO contextResponse = UtilContext
					.getFillContextResponseDTOBasic(msgRequest
							.getContextRequestDTO());

			msgResponse.setContextResponse(contextResponse);

			PollasUserBP objPollasUserBP = new PollasUserBP();

			objPollasUserBP.acceptedInvitacion(msgRequest.getIdUser(),
					msgRequest.getIdPolla(), msgRequest.isAccepted());

		} catch (Exception e) {
			msgResponse.getContextResponse().setTransactionState(false);
			Error.SendError(e.getMessage());
			throw e;
		}

		return msgResponse;

	}

	@ApiMethod(name = "removeGuestUser", path = "removeGuestUser", httpMethod = HttpMethod.DELETE)
	public MsgRemoveGuestUserResponse removeGuestUser(
			@Named("idTransaction") String idTransaction,
			@Named("user") String user,
			@Named("application") String application,
			@Named("idGuest") int idGuest) throws Exception {

		MsgRemoveGuestUserResponse msgResponse = new MsgRemoveGuestUserResponse();

		try {

			ContextResponseDTO contextResponse = UtilContext
					.getFillContextResponseDTOBasic(idTransaction);

			msgResponse.setContextResponse(contextResponse);

			LGuest objLGuest = new LGuest();

			objLGuest.deleteGuest(idGuest);

		} catch (Exception e) {
			msgResponse.getContextResponse().setTransactionState(false);
			Error.SendError(e.getMessage());
			throw e;
		}

		return msgResponse;

	}

}
