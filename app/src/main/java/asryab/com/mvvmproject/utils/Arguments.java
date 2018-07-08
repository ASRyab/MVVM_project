package asryab.com.mvvmproject.utils;

public abstract class Arguments {

    public static class Activity {
        public static final String FORGOT_TYPE                  = "forgot_type";
        public static final String CHECK_TYPE                   = "check_type";
        public static final String SEND_EMAIL_ACTION            = "send_email_action";
        public static final String DONT_RECEIVE_ACTION          = "dont_receive_action";
        public static final String START_SIGNIN_ACTION          = "start_signin_action ";

        public static final String BUNDLE_FOR_HOME_CHILD        = "activity_bundle_for_home_child";
        public static final String FRAGMENT_COPY_HOME_CHILD     = "activity_fragment_copy_home_child";
    }

    public static class Fragment {
        public static final String ARG_PAGE_INTRO_MODEL         = "fragment_page_intro_model";
        public static final String ARG_PAGE_POLL_MODEL          = "fragment_page_poll_model";
        public static final String ARG_POLLS_STATUS             = "fragment_polls_status";
        public static final String ARG_POLL                     = "fragment_poll";
        public static final String ARG_POLL_ANSWER              = "fragment_poll_answer";
        public static final String ARG_POLL_ID                  = "fragment_poll_id";
        public static final String ARG_PETITION_READ_MORE       = "fragment_petition_read_more";
        public static final String ARG_PETITION_ALL_UPDATES     = "fragment_petition_all_updates";
        public static final String ARG_LIST_STATE               = "list state";
    }

    public static class Broadcast {
        public static final String EVENT_UPDATE_POLL                = "broadcast_update_poll";
        public static final String EVENT_UPDATE_PETITION_READ_MORE  = "broadcast_update_petition_on_read_more_screen";
        public static final String INTENT_KEY_POLL                  = "broadcast_key_poll";
    }

    public static class File {
        public static final String FOLDER_INTRO                 = "intro";
    }

    public static class DataStorage {
        public static final String DATA_STORAGE                 = "data_storage";
        public static final String LANGUAGE                     = "language";
        public static final String USER                         = "user";

        public static final String PREFS_COOKIE                 = "cookies_";
        public static final String PREFS_COOKIE_KEY             = "cookies__key";
        public static final String IS_LOGIN                     = "is login";
    }

}
