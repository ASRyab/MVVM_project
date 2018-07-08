package asryab.com.mvvmproject.api.requests;

import java.util.Map;

import asryab.com.mvvmproject.api.ApiConst;
import asryab.com.mvvmproject.models.GetListResponse;
import asryab.com.mvvmproject.models.petitions.Petition;
import asryab.com.mvvmproject.models.petitions.Update;
import asryab.com.mvvmproject.models.polls.Poll;
import asryab.com.mvvmproject.models.polls.PollStatistics;
import asryab.com.mvvmproject.models.polls.PollVote;
import asryab.com.mvvmproject.models.polls.VoteComment;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import rx.Observable;

public interface ApiFeed {

    @GET(ApiConst.GET_POLLS)
    Observable<GetListResponse<Poll>> getPolls(@QueryMap final Map<String, Integer> pagination);

    @GET(ApiConst.GET_POLLS_BY_STATUS)
    Observable<GetListResponse<Poll>> getPollsByStatus(@Path("polls_status") String status, @QueryMap final Map<String, Integer> pagination);

    @GET(ApiConst.GET_POLLS_BY_ID)
    Observable<Poll> getPollByID(@Path("id") int pollID);

    @POST(ApiConst.CREATE_VOTE_FOR_POLLS)
    Observable<Poll> createVote(@Path("id") int id, @Body PollVote pollVote);

    @GET(ApiConst.GET_PETITIONS_BY_STATUS)
    Observable<GetListResponse<Petition>> getPetitionsByStatus(@Path("petitions_status") String status, @QueryMap final Map<String, Integer> pagination);

    @POST(ApiConst.POST_SIGN_PETITION)
    Observable<Petition> signPetition(@Path("id") int id);

    @GET(ApiConst.GET_ALL_UPDATES)
    Observable<GetListResponse<Update>> getAllUpdates(@Path("id") int pollId, @QueryMap final Map<String, Integer> pagination);


    @GET(ApiConst.GET_STATISTICS_FROM_ANSW)
    Observable<PollStatistics> getStatisticsFromAnswer(@Path("id") int answerId);

    @PUT(ApiConst.KEEP_UPDATE_PETITION)
    Observable<Petition> keepUpdate(@Body final Map<String, Object> mapKeepUpdate);

    @GET(ApiConst.GET_ALL_THOUGHTS)
    Observable<GetListResponse<VoteComment>> getAllThoughts(@Path("id") int pollId, @QueryMap final Map<String, Integer> pagination);

    @POST(ApiConst.POST_COMMENT_LIKE)
    Observable<VoteComment> postLikeComment(@Body final Map<String, Integer> voteIdField);

}
