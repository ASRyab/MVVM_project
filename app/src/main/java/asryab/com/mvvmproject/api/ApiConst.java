package asryab.com.mvvmproject.api;

public class ApiConst {

    public static final String GET_INTRO_IMAGES         = "/intro";

    public static final String LOGIN                    = "/signIn";
    public static final String SIGN_UP                  = "/signUp";
    public static final String CHECK_AUTH               = "/auth";

    public static final String GET_POLLS                = "/polls";
    public static final String GET_POLLS_BY_STATUS      = "/polls/{polls_status}";
    public static final String GET_POLLS_BY_ID          = "/polls/{id}";
    public static final String CREATE_VOTE_FOR_POLLS    = "/polls/{id}/vote";
    public static final String GET_ALL_THOUGHTS         = "/polls/{id}/comments";
    public static final String GET_PETITIONS_BY_STATUS  = "/petitions/{petitions_status}";
    public static final String POST_COMMENT_LIKE        = "/likes";

    public static final String GET_STATISTICS_FROM_ANSW = "/statistics/answers/{id}";

    public static final String KEY_SET_COOKIE           = "set-Cookie";
    public static final String KEY_COOKIE               = "cookie";
    public static final String KEY_SID_COOKIE           = "";

    public static final String GET_PINCODES             = "/pinCodes";

    public static final String SIGN_OUT                 = "/signOut";
    public static final String KEEP_UPDATE_PETITION     = "/petitions/keepUpdated";
    public static final String POST_SIGN_PETITION       = "/petitions/{id}/sign";
    public static final String GET_ALL_UPDATES          = "/petitions/{id}/updates";
    public static final String PROFILE                  = "/profile";
}
