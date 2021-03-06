package us.pollapp.inturik.endpoints;

import java.util.ArrayList;
import java.util.List;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.config.Named;

import us.pollapp.inturik.DTO.ContextResponseDTO;
import us.pollapp.inturik.DTO.Messages.MsgGetAllModelsActiveResponse;
import us.pollapp.inturik.DTO.Messages.MsgGetModelByIdResponse;
import us.pollapp.inturik.DTO.Messages.MsgVoteByModelRequest;
import us.pollapp.inturik.DTO.Messages.MsgVoteByModelResponse;
import us.pollapp.inturik.businesslogic.LModels;
import us.pollapp.inturik.endpoints.utils.UtilContext;
import us.pollapp.inturik.model.Model;
import us.pollapp.inturik.transformDTO.ModelsTransform;
import us.pollapp.inturik.util.Error;
import us.pollapp.inturik.util.Messages;

@Api(name = "modelendpoint", namespace = @ApiNamespace(ownerDomain = "pollapp.us", ownerName = "pollapp.us", packagePath = "inturik.endpoints"), version = "v1")
public class ModelsEndpoint {

	@ApiMethod(name = "getAllModelsActivePagination", path = "getAllModelsActivePagination", httpMethod = HttpMethod.GET)
	public MsgGetAllModelsActiveResponse getAllModelsActivePagination(
			@Named("idTransaction") String idTransaction,
			@Named("user") String user,
			@Named("application") String application,
			@Named("pageNumber") Integer pageNumber,
			@Named("pageSize") Integer pageSize) throws Exception {

		MsgGetAllModelsActiveResponse msgResponse = new MsgGetAllModelsActiveResponse();

		try {

			ContextResponseDTO contextResponse = UtilContext
					.getFillContextResponseDTOBasic(idTransaction);
			msgResponse.setContextResponse(contextResponse);

			List<Model> lstModels = new ArrayList<>();

			LModels objLModels = new LModels();

			if (pageNumber == 0 && pageSize == 0) {
				lstModels = objLModels.getAllModelsActive(false);
			} else {
				lstModels = objLModels.getAllModelsActive(pageNumber, pageSize);
			}

			ModelsTransform transform = new ModelsTransform();

			msgResponse.setModels(transform.transformListDTO(lstModels));

		} catch (Exception e) {
			msgResponse.getContextResponse().setTransactionState(false);
			Error.SendError(e.getMessage());
			throw e;
		}

		return msgResponse;

	}

	@ApiMethod(name = "getModelById", path = "getModelById", httpMethod = HttpMethod.GET)
	public MsgGetModelByIdResponse getModelById(
			@Named("idTransaction") String idTransaction,
			@Named("user") String user,
			@Named("application") String application,
			@Named("idModel") Integer idModel) throws Exception {

		// TODO: Validar parametros de entrada

		MsgGetModelByIdResponse msgResponse = new MsgGetModelByIdResponse();

		ContextResponseDTO contextResponse = UtilContext
				.getFillContextResponseDTOBasic(idTransaction);
		msgResponse.setContextResponse(contextResponse);

		try {
			LModels objLModels = new LModels();

			Model objModel = objLModels.getModelById(idModel);

			ModelsTransform transform = new ModelsTransform();

			msgResponse.setModel(transform.transformDTO(objModel));

		} catch (Exception e) {
			msgResponse.getContextResponse().setTransactionState(false);
			Error.SendError(e.getMessage());
			throw e;
		}

		return msgResponse;
	}

	@ApiMethod(name = "voteByModel", path = "voteByModel", httpMethod = HttpMethod.POST)
	public MsgVoteByModelResponse voteByModel(
			MsgVoteByModelRequest msgVoteByModelRequest) throws Exception {

		// TODO: Validar parametros de entrada

		MsgVoteByModelResponse msgResponse = new MsgVoteByModelResponse();

		ContextResponseDTO contextResponse = UtilContext
				.getFillContextResponseDTOBasic(msgVoteByModelRequest
						.getContextRequest());
		msgResponse.setContextResponse(contextResponse);

		try {

			LModels objLModels = new LModels();

			int score = objLModels.updateScoreModel(
					msgVoteByModelRequest.getIdModel(),
					msgVoteByModelRequest.getScore(),
					msgVoteByModelRequest.getIdUser());

			msgResponse.setScore(score);

		} catch (Exception e) {
			e.printStackTrace();
			msgResponse.getContextResponse().setTransactionState(false);
			if (!e.getMessage().equals(Messages.MSG_00005)) {
				Error.SendError(e.getMessage());	
			}
			
			throw e;
		}

		return msgResponse;
	}
}
