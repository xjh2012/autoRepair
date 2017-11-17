package common;

public interface SystemConstant {

	// general
	public static final String YES = "Y";
	public static final String NO = "N";
	
	public static final Integer ACTIVE = 1;
	public static final Integer DELETED = 0;
	
	public static final Integer OPEN = 1;
	public static final Integer CLOSED = 0;
	
	public static final Integer TRUE = 1;
	public static final Integer FALSE = 0;
	
	public static final String LOCAL_PHONE = "";
	public static final String LOCAL_PHONE_IDN = "62";
	
	
	public static final String FORMAT_DATE_MMMMM_YYYY 				= "MMMMM yyyy";
	public static final String FORMAT_DATE_YYYY_MM_DD 				= "yyyy-MM-dd";
	public static final String FORMAT_DATE_YYYY_MM_DD_HH_MM_SS 		= "yyyy-MM-dd HH:mm:ss";
	public static final String FORMAT_DATE_EEE_DD_MMMM	 			= "EEE, dd MMM";
	public static final String FORMAT_DATE_YYYYMMDDHHMMSS 			= "yyyyMMddHHmmss";
	public static final String FORMAT_DATE_YYYY_MM 					= "yyyy-MM";
	public static final String FORMAT_DATE_MM 						= "MM";
	public static final String FORMAT_DATE_YYYY 					= "yyyy";

	public static final String LOG_LOGIN_STATUS_SUCCESS = "LOG_STAT_1";
	public static final String LOG_LOGIN_STATUS_FAIL = "LOG_STAT_0";
	public static final String LOG_LOGIN_STATUS_UNKNOWN = "";


	
	public static final String FORMAT_DATE_HH_MM 					= "HH.mm";
	public static final String FORMAT_DATE_HH_MM_SS 				= "HH:mm:ss";
	public static final String FORMAT_DATE_HHMMSS 					= "HHmmss";

	// date
	public static final String[] BULAN 								= {"Januari","Februari","Maret","April","Mei","Juni","Juli","Agustus","September","Oktober","November","Desember"};
	public static final Integer TAHUN 								= 2014;
	public static final String TANGGAL								= "2014-01-01";
	public static final String YEAR_DESC							= "tahun";
	// menu
	public static final String MENU 								= "MENU";
	public static final String MENU_HOME 							= "MENU0";
	public static final String MENU_PASSWORD						= "MENU0.1";
	// TODO @budi
	
	// User
	public static final String MENU_USER							= "MENU9.9";
	public static final String MENU_USER_APPOINTMENT				= "MENU10.1";
	public static final String MENU_USER_APPOINTMENT_FORM			= "MENU10.2";
	public static final String MENU_USER_APPOINTMENT_HISTORY		= "MENU10.3";
	public static final String MENU_USER_PREFERENCES				= "MENU11.0";
	public static final String MENU_USER_PREFERENCES_PROFILE		= "MENU11.1";

	// TODO @budi mail
	// Mail 
	public static final String MAIL									= "MAIL_";
	public static final String MAIL_HEADER							= "MAIL_HEADER";
	public static final String MAIL_FOOTER							= "MAIL_FOOTER";
	public static final String MAIL_CONTENT							= "MAIL_CONTENT";
	public static final String MAIL_ACCOUNT							= "MAIL_ACCOUNT_";
	public static final String MAIL_ACCOUNT_ADM						= "MAIL_ACCOUNT_ADM";
	public static final String MAIL_ACCOUNT_SYS						= "MAIL_ACCOUNT_SYS";
	public static final String MAIL_ACCOUNT_MKT						= "MAIL_ACCOUNT_MKT";
	public static final String MAIL_ACCOUNT_PWD						= "MAIl_ACCOUNT_PWD";
	public static final String MAIL_ACCOUNT_DESC					= "MAIL_ACCOUNT_DESC";
	
	public static final String DGRADER_MAIL							= "DGRADER_MAIL_";
	public static final String DGRADER_MAIL_ACCOUNT					= "DGRADER_MAIL_ACCOUNT";

	// TODO @budi notification 
	public static final String USER_NOTIFICATION_					= "USER_NOTIFICATION_";
	public static final String USER_NOTIFICATION_STATIC				= "USER_NOTIFICATON_STATIC";
	public static final String USER_NOTIFICATION_DYNAMIC			= "USER_NOTIFICATION_DYNAMIC";
	
	public static final String MENU_REFERRAL						= "MENU1.1";
	public static final String MENU_REFERRAL_VERIFICATION			= "MENU1.2";
	
	public static final String MENU_REPORT_REFERRAL_ORIGIN			= "MENU2.1";
	public static final String MENU_REPORT_REFERRAL_DESTINATION		= "MENU2.2";
	
	public static final String MENU_APPOINTMENT						= "MENU3.1";
	public static final String MENU_APPOINTMENT_CALLCENTER			= "MENU3.2";
	public static final String MENU_APPOINTMENT_VERIFICATION		= "MENU3.3";
	public static final String MENU_APPOINTMENT_CALLCENTER_NEW		= "MENU3.4";
	public static final String MENU_APPOINTMENT_VERIFICATION_NEW	= "MENU3.5";

	
	public static final String MENU_TEST_AJAX						= "MENU9.0";
	public static final String MENU_TEST_AJAX_TIME					= "MENU9.1";
	public static final String MENU_TEST_AJAX_FORM					= "MENU9.2";
	
	
	public static final String MENU_USER_REGISTRATION				= "MENU0.1";

	
	//Grading System Menu TODO @Budi 
	
	//Course Management = CM
		public static final String MENU_COURSE_MANAGEMENT		= "MENU22.0";
		public static final String MENU_CM_COURSE				= "MENU22.1";
		public static final String MENU_CM_ASSIGNMENT 			= "MENU22.2";

	//User Control Management = UCM
	public static final String MENU_USER_CONTROL_MANAGEMENT		= "MENU21.0";
	public static final String MENU_UCM_USER					= "MENU21.1";
	public static final String MENU_UCM_NEW_USER_REGISTRATION	= "MENU21.2";

	//Assignment Lang
	public static final String LANG_							= "LANG_";
	public static final String LANG_ALL							= "LANG_ALL";
	public static final String LANG_JAVA						= "LANG_JAVA";
	public static final String LANG_C							= "LANG_C";
	public static final String LANG_CPLUS						= "LANG_CPLUS";
	public static final String LANG_CCPLUS						= "LANG_CCPLUS";


	//Assignment Grading
	public static final String GRAD_							= "GRAD_";
	public static final String GRAD_STATIC						= "GRAD_STATIC";
	public static final String GRAD_DYNAMIC						= "GRAD_DYNAMIC";
	public static final String GRAD_HYBRID						= "GRAD_HYBRID";

	
	//File assignment type
	public static final String FILE_ASSIGNMENT_					= "FILE_ASSIGNMENT_";
	public static final String FILE_ASSIGNMENT_TEST_CASE		= "FILE_ASSIGNMENT_TEST_CASE";
	public static final String FILE_ASSIGNMENT_MODEL			= "FILE_ASSIGNMENT_MODEL";
	public static final String FILE_ASSIGNMENT_SOLUTION			= "FILE_ASSIGNMENT_SOLUTION";
	public static final String FILE_ASSIGNMENT_SAMPLE_SOURCE	= "FILE_ASSIGNMENT_SAMPLE_SOURCE";

	public static final String FILE_ASSIGNMENT_NAME_			= "FILE_ASSIGNMENT_";
	public static final String FILE_ASSIGNMENT_NAME_TEST_CASE	= "TEST_CASE";
	public static final String FILE_ASSIGNMENT_NAME_MODEL		= "MODEL";
	public static final String FILE_ASSIGNMENT_NAME_SOLUTION	= "SOLUTION";
	public static final String FILE_ASSIGNMENT_NAME_SAMPLE_SOURCE	= "SAMPLE_SOURCE";

	
	//TODO @Budi Assignment input type
	public static final String ASSIGNMENT_INPUT_TYPE_			= "ASSIGNMENT_INPUT_TYPE_";
	public static final String ASSIGNMENT_INPUT_TYPE_NO			= "NO";
	public static final String ASSIGNMENT_INPUT_TYPE_KEYBOARD	= "KEYBOARD";
	public static final String ASSIGNMENT_INPUT_TYPE_FILE		= "FILE";

	//TODO @Budi Assignment Level
	public static final String ASSIGNMENT_LEVEL_				= "ASSIGNMENT_LEVEL_";
	public static final String ASSIGNMENT_LEVEL_BEGINNER		= "BEGINNER";
	public static final String ASSIGNMENT_LEVEL_NOVICE			= "NOVICE";
	public static final String ASSIGNMENT_LEVEL_INTERMEDIATE	= "INTERMEDIATE";
	public static final String ASSIGNMENT_LEVEL_ADVANCE			= "ADVANCE";
	public static final String ASSIGNMENT_LEVEL_MASTER			= "MASTER";



	
	// error code
	public static final String ERR_SYSTEM 							= "E00";
	public static final String ERR_LICENSE 							= "E01";
	public static final String ERR_ACCESS 							= "E02";
	
	// error message
	public static final String ERR_MSG_INVALID_LICENSE 				= "Kode lisensi tidak benar";
	public static final String ERR_MSG_INVALID_MENU_ACCESS 			= "No access privileges for this menu";
	
	// parameter type
	public static final String PARAM_TYPE		 					= "PAR_TYPE_";
	public static final String PARAM_TYPE_SYSTEM 					= "PAR_TYPE_SYS";
	public static final String PARAM_TYPE_USER 						= "PAR_TYPE_USR";
	public static final String PARAM_UPLOAD_DIRECTORY				= "UPLOAD_DIRECTORY";
	public static final String PARAM_ASSIGNMENT_TEST_CASE_DIR		= "ASSIGNMENT_TEST_CASE_DIR";
	public static final String PARAM_ASSIGNMENT_MODEL_PROGRAM_DIR	= "ASSIGNMENT_MODEL_PROGRAM_DIR";
	public static final String PARAM_ASSIGNMENT_DYNAMIC_SOLUTION_DIR= "ASSIGNMENT_DYNAMIC_SOLUTION_DIR";
	public static final String PARAM_ASSIGNMENT_STATIC_SOLUTION_DIR	= "ASSIGNMENT_STATIC_SOLUTION_DIR";
	public static final String PARAM_DEFAULT_PASSWORD				= "DEFAULT_PASSWORD";
	public static final String PARAM_LOG_FILES_DIR					= "LOG_FILES_DIR";
	public static final String PARAM_DEFAULT_DGRADER_DIR			= "DEFAULT_DGRADER_DIR";

	// login status
	public static final String LOGIN_STATUS 						= "LOG_STAT_";
	public static final String LOGIN_STATUS_SUCCESS 				= "LOG_STAT_1";
	public static final String LOGIN_STATUS_FAIL 					= "LOG_STAT_0";
	public static final String LOGIN_STATUS_UNKNOWN 				= "";

	
	

	// menu role
	public static final String MENU_ROLE				 			= "MNU_ROLE_";
	public static final String MENU_ROLE_ADMINISTRATOR	 			= "MNU_ROLE_ADM";
	public static final String MENU_ROLE_PASIEN			 			= "MNU_ROLE_PAS";
	public static final String MENU_ROLE_PUSKESMAS		 			= "MNU_ROLE_PKM";
	public static final String MENU_ROLE_RSUD			 			= "MNU_ROLE_RS";
	public static final String MENU_DUMMY							= "MNU_ROLE_DUMMY";
	public static final String MENU_ROLE_LECTURER					= "MNU_ROLE_LECTURER";
	public static final String MENU_ROLE_STUDENT					= "MNU_ROLE_STUDENT";
	
	// user type
	public static final String USER_TYPE_PATIENT		 			= "PATIENT";
	public static final String USER_TYPE_ENTITY			 			= "ENTITY";
	
	// user level
	public static final String USER_LEVEL							= "DAT_LVEL";
	public static final String USER_LEVEL_STAFF						= "DAT_LVEL_STAFF";
	public static final String USER_LEVEL_EDU						= "DAT_LVEL_EDU";
	public static final String USER_LEVEL_STUDENT					= "DAT_LVEL_STUDENT";


	// parameter
	public static final String PARAM 								= "PARAMETER";
	public static final String PARAM_MAX_LOGIN_FAIL_ALLOWED 		= "MAX_LOGIN_FAIL_ALLOWED";

	//AJAX TIMEOUT
	public static final String AJAX_TIMEOUT = "AJAX_TIMEOUT";
	public static final String AJAX_TIMEOUT_WEB_SERVICE_V2 = "SPRO_WEB_SERVICES_V2";
	

	//SETUP LUCENE
		public static final int MinIniBlockLength = 5;
		public static final int MaxMergeLength = 2;
		public static final String CONTENTS="contents";
		public static final String FILE_NAME="filename";
		public static final String FILE_PATH="filepath";
		public static final int MAX_SEARCH = 10;
		public static final String MENU_TEST = "MNU_TEST";
		
	
	
	
}
