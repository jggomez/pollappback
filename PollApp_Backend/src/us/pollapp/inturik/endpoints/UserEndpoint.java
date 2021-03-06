package us.pollapp.inturik.endpoints;

import java.util.logging.Logger;

import us.pollapp.inturik.DTO.ContextRequestDTO;
import us.pollapp.inturik.DTO.ContextResponseDTO;
import us.pollapp.inturik.DTO.UserDTO;
import us.pollapp.inturik.DTO.Messages.MsgCreateUserRequest;
import us.pollapp.inturik.DTO.Messages.MsgCreateUserResponse;
import us.pollapp.inturik.DTO.Messages.MsgUserByEmailByPassResponse;
import us.pollapp.inturik.DTO.Messages.MsgUserByEmailResponse;
import us.pollapp.inturik.businesslogic.LUser;
import us.pollapp.inturik.businessprocess.UserBP;
import us.pollapp.inturik.endpoints.utils.UtilContext;
import us.pollapp.inturik.model.User;
import us.pollapp.inturik.transformDTO.UserTransform;
import us.pollapp.inturik.util.Error;
import us.pollapp.inturik.util.Messages;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;

@Api(name = "userendpoint", namespace = @ApiNamespace(ownerDomain = "pollapp.us", ownerName = "pollapp.us", packagePath = "inturik.endpoints"), version = "v1")
public class UserEndpoint {

	private static final Logger log = Logger.getLogger(UserEndpoint.class
			.getName());

	@ApiMethod(name = "createUser", path = "createUser", httpMethod = HttpMethod.POST)
	public MsgCreateUserResponse createUser(MsgCreateUserRequest msgRequest)
			throws Exception {

		MsgCreateUserResponse msgResponse = new MsgCreateUserResponse();

		try {

			ContextRequestDTO ctr = msgRequest.getContextRequestDTO();

			log.info(String.format(Messages.MSG_REG_ENDPOINT,
					ctr.getIdTransaction(), ctr.getUserName(),
					ctr.getApplicationName(), "createUser"));

			ContextResponseDTO contextResponse = UtilContext
					.getFillContextResponseDTOBasic(ctr);
			msgResponse.setContextResponse(contextResponse);

			UserBP objLUserBP = new UserBP();

			UserDTO userDTO = msgRequest.getUserDTO();

			if (userDTO != null) {
				User user = objLUserBP.addUser(userDTO.getName(),
						userDTO.getLastName(), userDTO.getEmail(),
						userDTO.getPassword());

				UserTransform objUserTransform = new UserTransform();
				msgResponse.setUserDTO(objUserTransform.transformDTO(user));
			}

		} catch (Exception e) {
			msgResponse.getContextResponse().setTransactionState(false);
			if (!e.getMessage().equals(Messages.MSG_00003)) {
				Error.SendError(e.getMessage());
			}
			throw e;
		}

		return msgResponse;

	}

	@ApiMethod(name = "getUserByEmailByPass", path = "getUserByEmailByPass", httpMethod = HttpMethod.GET)
	public MsgUserByEmailByPassResponse getUserByEmailByPass(
			@Named("idTransaction") String idTransaction,
			@Named("user") String user,
			@Named("application") String application,
			@Named("email") String email, @Named("password") String password)
			throws Exception {

		MsgUserByEmailByPassResponse msgResponse = new MsgUserByEmailByPassResponse();

		try {

			log.info(String.format(Messages.MSG_REG_ENDPOINT, idTransaction,
					user, application, "getUserByEmailByPass"));

			ContextResponseDTO contextResponse = UtilContext
					.getFillContextResponseDTOBasic(idTransaction);
			msgResponse.setContextResponse(contextResponse);

			LUser objLUser = new LUser();

			User objUser = null;
			UserDTO objUserDTO = null;

			if (email != null && password != null) {
				objUser = objLUser.getUserByEmailByPassword(email, password);
			}

			if (objUser != null) {
				UserTransform userTrans = new UserTransform();
				objUserDTO = userTrans.transformDTO(objUser);
			}

			msgResponse.setUserDTO(objUserDTO);

		} catch (Exception e) {
			msgResponse.getContextResponse().setTransactionState(false);
			Error.SendError(e.getMessage());
			throw e;
		}

		return msgResponse;

	}

	@ApiMethod(name = "getUserByEmail", path = "getUserByEmail", httpMethod = HttpMethod.GET)
	public MsgUserByEmailResponse getUserByEmail(
			@Named("idTransaction") String idTransaction,
			@Named("user") String user,
			@Named("application") String application,
			@Named("email") String email) throws Exception {

		MsgUserByEmailResponse msgResponse = new MsgUserByEmailResponse();

		try {

			log.info(String.format(Messages.MSG_REG_ENDPOINT, idTransaction,
					user, application, "getUserByEmail"));

			ContextResponseDTO contextResponse = UtilContext
					.getFillContextResponseDTOBasic(idTransaction);
			msgResponse.setContextResponse(contextResponse);

			LUser objLUser = new LUser();

			User objUser = null;
			UserDTO objUserDTO = null;

			if (email != null) {
				objUser = objLUser.getUserByEmail(email);
			}

			if (objUser != null) {
				UserTransform userTrans = new UserTransform();
				objUserDTO = userTrans.transformDTO(objUser);
			}

			msgResponse.setUserDTO(objUserDTO);

		} catch (Exception e) {
			msgResponse.getContextResponse().setTransactionState(false);
			Error.SendError(e.getMessage());
			throw e;
		}

		return msgResponse;

	}

}
