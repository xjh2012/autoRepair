import common.SystemConstant;
import service.SystemService;
import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class BaseController {
	
	public String getRequestList(HttpServletRequest request) {
		List<String> requestList = new ArrayList<String>();
		Iterator<Map.Entry<String, String[]>> it = request.getParameterMap().entrySet().iterator();
		while (it.hasNext()) {
	        Map.Entry<String, String[]> pairs = it.next();
			requestList.add(pairs.getKey().concat(Arrays.toString(pairs.getValue())));
	    }
		String result = "";
		if (requestList.size()>0){
			List<String> sublist = requestList.subList(0, requestList.size()); 
			Collections.sort(sublist);
			result = sublist.toString();
		}
		return DigestUtils.md5Hex(result.getBytes());
	}
	
	public void checkLicense(HttpServletRequest arg0,
			HttpServletResponse arg1, SystemService ss) throws Exception {
		//SystemView systemView = ss.getLicense();
		//String systemCode = systemView.getSystemCode();
		//String licenseCode = systemView.getLicenseCode();
		//Timestamp currentDate = ss.getCurrentDate();
		/*if(!LicenseUtil.getInstance().validateLicense(systemCode, currentDate, licenseCode)){
			String licenseStatus = SystemConstant.ERR_MSG_INVALID_LICENSE;	
			throw new CustomException(SystemConstant.ERR_LICENSE, licenseStatus);
		}	*/
	}
	
	public String[] getYearList() {
		Calendar nextYear = Calendar.getInstance();
		nextYear.add(Calendar.YEAR, 1);
		Integer toYear = nextYear.get(nextYear.YEAR);
		String[] years = new String[toYear-SystemConstant.TAHUN+1];
		for(int i=SystemConstant.TAHUN; i<=toYear; i++){
			years[i-SystemConstant.TAHUN] = String.valueOf(i);
		}
		return years;
	}
	
	public String generateRandomToken(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		String userSessionId = "";
		if(session != null && session.getAttribute("userId") != null ){
			userSessionId = session.getAttribute("sessionId").toString();
		}
		Calendar calendar = Calendar.getInstance();
		DateFormat dateFormat = new SimpleDateFormat(SystemConstant.FORMAT_DATE_YYYYMMDDHHMMSS);
		String currentDate = dateFormat.format(calendar.getTime());
		String token = DigestUtils.md5Hex(currentDate).concat(DigestUtils.md5Hex(userSessionId));
		return token;
	}
	
}
