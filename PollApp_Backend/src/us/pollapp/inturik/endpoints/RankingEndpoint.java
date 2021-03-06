package us.pollapp.inturik.endpoints;

import java.util.List;

import us.pollapp.inturik.DTO.ContextResponseDTO;
import us.pollapp.inturik.DTO.Messages.MsgGetRankingByUserResponse;
import us.pollapp.inturik.DTO.Messages.MsgGetRankingResponse;
import us.pollapp.inturik.businesslogic.LMatch;
import us.pollapp.inturik.businesslogic.LPolla;
import us.pollapp.inturik.endpoints.utils.UtilContext;
import us.pollapp.inturik.model.PollasUser;
import us.pollapp.inturik.model.ResultMatch;
import us.pollapp.inturik.transformDTO.RankingUserTransform;
import us.pollapp.inturik.transformDTO.RankingTransform;
import us.pollapp.inturik.util.Error;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;

@Api(name = "rankingendpoint", namespace = @ApiNamespace(ownerDomain = "pollapp.us", ownerName = "pollapp.us", packagePath = "inturik.endpoints"), version = "v1")
public class RankingEndpoint {

	@ApiMethod(name = "getRanking", path = "getRanking", httpMethod = HttpMethod.GET)
	public MsgGetRankingResponse getRanking(
			@Named("idTransaction") String idTransaction,
			@Named("user") String user,
			@Named("application") String application,
			@Named("pageNumber") Integer pageNumber,
			@Named("pageSize") Integer pageSize, @Named("idPolla") int idPolla)
			throws Exception {

		MsgGetRankingResponse msgResponse = new MsgGetRankingResponse();

		try {

			ContextResponseDTO contextResponse = UtilContext
					.getFillContextResponseDTOBasic(idTransaction);

			msgResponse.setContextResponse(contextResponse);

			LPolla objLPolla = new LPolla();

			List<PollasUser> lstPollasUser = objLPolla
					.getPollaUsersById(idPolla);

			RankingTransform objRankingTrans = new RankingTransform();

			msgResponse.setLstRankingDTO(objRankingTrans
					.transformListDTO(lstPollasUser));

		} catch (Exception e) {
			e.printStackTrace();
			msgResponse.getContextResponse().setTransactionState(false);
			Error.SendError(e.getMessage());
			throw e;
		}

		return msgResponse;

	}

	@ApiMethod(name = "getRankingByUser", path = "getRankingByUser", httpMethod = HttpMethod.GET)
	public MsgGetRankingByUserResponse getRankingByUser(
			@Named("idTransaction") String idTransaction,
			@Named("user") String user,
			@Named("application") String application,
			@Named("pageNumber") Integer pageNumber,
			@Named("pageSize") Integer pageSize, @Named("idPolla") int idPolla,
			@Named("idUser") int idUser) throws Exception {

		MsgGetRankingByUserResponse msgResponse = new MsgGetRankingByUserResponse();

		try {

			ContextResponseDTO contextResponse = UtilContext
					.getFillContextResponseDTOBasic(idTransaction);

			msgResponse.setContextResponse(contextResponse);

			LMatch objLMatch = new LMatch();

			List<ResultMatch> lstResultMatch = objLMatch
					.getResultMatchByPollaByUser(idPolla, idUser, pageNumber,
							pageSize);

			RankingUserTransform objRankingUserTrans = new RankingUserTransform();

			msgResponse.setLstRankingUserDTO(objRankingUserTrans
					.transformListDTO(lstResultMatch));

		} catch (Exception e) {
			e.printStackTrace();
			msgResponse.getContextResponse().setTransactionState(false);
			Error.SendError(e.getMessage());
			throw e;
		}

		return msgResponse;

	}
}
