package pnus.josso;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.constants.Style;
import org.apache.axis.constants.Use;
import org.apache.axis.description.FaultDesc;
import org.apache.axis.description.OperationDesc;
import org.apache.axis.description.ParameterDesc;
import org.apache.axis.soap.SOAPConstants;
import org.apache.axis.utils.JavaUtils;
import org.apache.log4j.Logger;
import org.josso.gateway.ws._1_1.protocol.FindUserInSessionRequestType;
import org.josso.gateway.ws._1_1.protocol.FindUserInSessionResponseType;
import org.josso.gateway.ws._1_1.protocol.ResolveAuthenticationAssertionRequestType;
import org.josso.gateway.ws._1_1.protocol.ResolveAuthenticationAssertionResponseType;

public class LoginFilter implements Filter
{
	private static final Logger log = Logger.getLogger(LoginFilter.class);
	private static boolean debug = true;
	private static String loginUrl = null;
	private static String logoutUrl = null;
	private static String requestUrl = null;
	private static String endPoint = null;
	private static String flag_josso = null;
	private static OperationDesc SoapGetSessionID = null;
	private static OperationDesc SoapGetUsername = null;
	private FilterConfig filterConfig = null;
	
	public void init(FilterConfig config) throws ServletException 
	{
	    filterConfig = config;
	    loginUrl   = filterConfig.getInitParameter("jossoGatewayLoginUrl");
	    logoutUrl  = filterConfig.getInitParameter("jossoGatewayLogoutUrl");
	    endPoint   = filterConfig.getInitParameter("jossoEndpoint");
	    flag_josso = filterConfig.getInitParameter("flag_josso");
//	    POST /josso/services/SSOIdentityManager HTTP/1.0
//	    User-Agent: NuSOAP/0.6.7 (543)
//	    Host: 203.231.14.163
//	    Content-Type: text/xml; charset=UTF-8
//	    SOAPAction: "http://josso.org/gateway/ws/1.1/wsdl/soapbinding/IdentityProvider/resolveAuthenticationAssertion"
//	    Content-Length: 898 
//
//	    <?xml version="1.0" encoding="UTF-8"?>
//	    <SOAP-ENV:Envelope 
//	    	SOAP-ENV:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/"
//	        xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/" 
//	        xmlns:xsd="http://www.w3.org/2001/XMLSchema" 
//	        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
//	        xmlns:SOAP-ENC="http://schemas.xmlsoap.org/soap/encoding/" 
//	        xmlns:si="http://soapinterop.org/xsd" 
//
//	        xmlns:nu="http://josso.org/gateway/ws/1.1/wsdl/soapbinding/IdentityProvider/resolveAuthenticationAssertion" 
//	        xmlns:tns1="http://josso.org/gateway/ws/1.1/protocol">
//	        <SOAP-ENV:Body>
//	            <nu:resolveAuthenticationAssertion
//	                xmlns:nu="http://josso.org/gateway/ws/1.1/wsdl/soapbinding/IdentityProvider/resolveAuthenticationAssertion">
//	                <ResolveAuthenticationAssertionRequest xsi:type="tns1:ResolveAuthenticationAssertionRequestType">
//	                    <assertionId xsi:type="xsd:string">3BE0B7FD5DC7B7DEFF6ADAD4421E34D6</assertionId>
//	                </ResolveAuthenticationAssertionRequest>
//	            </nu:resolveAuthenticationAssertion>
//	        </SOAP-ENV:Body>
//	    </SOAP-ENV:Envelope>
	    
	    //---	AssertionIDë¡? JOSSO Session ID??? ê°???? ??¨ë??.
	    //---	AssertionID??? One-Time-Password ì²???? JOSSO Session IDë¥? ê°???¸ì?¤ê¸° ?????´ì?? ??¼í????±ì?¼ë?? ??¬ì?? ?????? ID ??´ë??.
	    SoapGetSessionID = new OperationDesc();
	    System.out.println("¼¼¼Ç ");
	    SoapGetSessionID.setStyle(Style.RPC);
	    SoapGetSessionID.setUse(Use.ENCODED);
	    SoapGetSessionID.setName("resolveAuthenticationAssertion");
	    SoapGetSessionID.addParameter(new ParameterDesc(
	    		new QName("", "ResolveAuthenticationAssertionRequest"), 
	    		(byte)1, 
	    		new QName("http://josso.org/gateway/ws/1.1/protocol", "ResolveAuthenticationAssertionRequestType"), 
	    		ResolveAuthenticationAssertionRequestType.class,
	    		false,
	    		false));
	    
	    SoapGetSessionID.setReturnType(new QName("http://josso.org/gateway/ws/1.1/protocol", "ResolveAuthenticationAssertionResponseType"));
	    SoapGetSessionID.setReturnClass(ResolveAuthenticationAssertionResponseType.class);
	    SoapGetSessionID.setReturnQName(new QName("", "ResolveAuthenticationAssertionResponse"));
	    
	    SoapGetSessionID.addFault(new FaultDesc(
	    		new QName("http://josso.org/gateway/ws/1.1/wsdl/soapbinding/IdentityProvider/resolveAuthenticationAssertion", "AssertionNotValidError"), 
	    		"org.josso.gateway.ws._1_1.protocol.AssertionNotValidErrorType", 
	    		new QName("http://josso.org/gateway/ws/1.1/protocol", "AssertionNotValidErrorType"), 
	    		true));
	    SoapGetSessionID.addFault(new FaultDesc(
	    		new QName("http://josso.org/gateway/ws/1.1/wsdl/soapbinding/IdentityProvider/resolveAuthenticationAssertion", "SSOIdentityProviderError"), 
	    		"org.josso.gateway.ws._1_1.protocol.SSOIdentityProviderErrorType", 
	    		new QName("http://josso.org/gateway/ws/1.1/protocol", "SSOIdentityProviderErrorType"), 
	    		true));

	    //---	JOSSO Session IDë¡? ??¬ì?©ì?? ???ë³?(usernameê³? ??¬ì?©ì?? Properties)ë¥? ê°???? ??¨ë??.
	    SoapGetUsername = new OperationDesc();
	    SoapGetUsername.setStyle(Style.RPC);
	    SoapGetUsername.setUse(Use.ENCODED);
	    SoapGetUsername.setName("findUserInSession");
	    SoapGetUsername.addParameter(new ParameterDesc(
	    		new QName("", "FindUserInSessionRequest"), 
	    		(byte)1, 
	    		new QName("http://josso.org/gateway/ws/1.1/protocol", "FindUserInSessionRequestType"), 
	    		FindUserInSessionRequestType.class,
	    		false,
	    		false));
	    
	    SoapGetUsername.setReturnType(new QName("http://josso.org/gateway/ws/1.1/protocol", "FindUserInSessionResponseType"));
	    SoapGetUsername.setReturnClass(FindUserInSessionResponseType.class);
	    SoapGetUsername.setReturnQName(new QName("", "FindUserInSessionResponse"));
	    
	    SoapGetUsername.addFault(new FaultDesc(
	    		new QName("http://josso.org/gateway/ws/1.1/wsdl/soapbinding/IdentityManager/findUserInSession", "InvalidSessionError"), 
	    		"org.josso.gateway.ws._1_1.protocol.InvalidSessionErrorType", 
	    		new QName("http://josso.org/gateway/ws/1.1/protocol", "InvalidSessionErrorType"), 
	    		true));
	    SoapGetUsername.addFault(new FaultDesc(
	    		new QName("http://josso.org/gateway/ws/1.1/wsdl/soapbinding/IdentityManager/findUserInSession", "NoSuchUserError"), 
	    		"org.josso.gateway.ws._1_1.protocol.NoSuchUserErrorType", 
	    		new QName("http://josso.org/gateway/ws/1.1/protocol", "NoSuchUserErrorType"), 
	    		true));	
	    SoapGetUsername.addFault(new FaultDesc(
	    		new QName("http://josso.org/gateway/ws/1.1/wsdl/soapbinding/IdentityManager/findUserInSession", "SSOIdentityManagerError"), 
	    		"org.josso.gateway.ws._1_1.protocol.SSOIdentityManagerErrorType", 
	    		new QName("http://josso.org/gateway/ws/1.1/protocol", "SSOIdentityManagerErrorType"), 
	    		true));	
	}
	
	public void destroy()
	{
		filterConfig = null;
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException 
	{
		HttpServletRequest req  = null;
		HttpServletResponse res = null;

		String josso_assertion_id = null;
        String sessionID = null;
        String username = null;

		String reqUrl = null;
        String token[] = {null, null, null, null, null};
		boolean flag_username = false;

		String query = null;
		Enumeration data = null;
		String name = null, value = null;
		boolean flag_start = true;
        
		debug("Start josso login filter.");
		req = (HttpServletRequest)request;
		res = (HttpServletResponse)response;

		debug("RequestURL : [" + req.getRequestURL() + "], [" + nvl(req.getQueryString()) + "]");

		if (flag_josso.equals("false")) {
        	chain.doFilter(req, res);
        	return;
        }
		
		//---	ë¡?ê·¸ì?? ???ì²???? ê²½ì??, ë¡?ê·¸ì?? ???ë©´ì?¼ë?? ??????, ppp
//		if (-1 < req.getRequestURL().indexOf("login.action")) {
//			res.sendRedirect(endPoint + "/confluence/display/daouPms/Home");
//			return;
//		}
		
		//---	ë¡?ê·¸ì????? ???ì²???? ê²½ì??, ë¡?ê·¸ì????? ???ë©´ì?¼ë?? ??????, ppp
		
		//---	JOSSO ë¡?ê·¸ì?? ???ì²?ë¦? ???ì²???? ê²½ì??, ì¿???¤ì?? JOSSO ??¸ì?? ?????´ë?? ??????
		josso_assertion_id = (String)req.getParameter("josso_assertion_id");
		if (josso_assertion_id != null) {
			debug("request for sessionID, josso_assertion_id : [" + josso_assertion_id + "]");
			sessionID = soapGetSessionID(josso_assertion_id);
			if (sessionID != null) {
				setCookie(res, "JOSSO_SESSIONID", sessionID);
			}
		}
		
		//---	JOSSO ??¸ì?? ??????
		if (sessionID == null) {
			sessionID = getCookie(req, "JOSSO_SESSIONID");
		}
		if (flag_username == false) {
			debug("request for username, sessionID : [" + nvl(sessionID) + "]");
			username = soapGetUsername(sessionID);
			if (username != null) {
				flag_username = true;
				setCookie(res, "JOSSO_USERNAME", token[2]);
			}
		}
		
		debug("josso_assertion_id : [" + nvl(josso_assertion_id) + "]");
		debug("sessionID : [" + nvl(sessionID) + "]");
		debug("username : [" + nvl(username) + "]");
		if (flag_username) {
			//---	ë§???? JOSSO ??¸ì????? ?????¼ë©´
			
			//---		???ë¡?ê·¸ë?¨ì?? ??¬ì?©ì?? ?????´ë??(username) ??????
			req.setAttribute("JOSSO_USERNAME", username);
			debug("start called page : username - [" + username + "]");
		} else {
			//---	ë§???? JOSSO ??¸ì????? ?????¼ë©´
			
			//---		ê¸°ì¡´ ???ì²? ???ë³´ë?? ?????¥í??ê³?, ppp
			//---		JOSSO??? ë¡?ê·¸ì?? ???ë©´ì?¼ë?? ??????
			reqUrl = loginUrl;
			reqUrl = reqUrl + "?josso_back_to=" + req.getRequestURL();
			data = req.getParameterNames();
			while (data.hasMoreElements()) {
				name = (String)data.nextElement();
				value = req.getParameter(name);
				
				if (name.equals("josso_assertion_id")) {
					continue;
				}
				
				if (flag_start) {
					flag_start = false;
					query = "?" + name + "=" + value;
				} else {
					query = query + "&" + name + "=" + value;
				}
			}
			if (query != null) {
				reqUrl = reqUrl + query;
			}

			reqUrl = reqUrl + "&josso_partnerapp_host=" + request.getServerName();
			debug("sendRedirect : [" + reqUrl + "]");
			res.sendRedirect(reqUrl);
			return;
		}
	    chain.doFilter(req, res);		
	}
	
	private String soapGetUsername(String sessionID)
	{
		String rtUsername = null;
		Service service = null;
		Call call = null;
		FindUserInSessionRequestType soaRequest = null;
		FindUserInSessionResponseType soapResponse = null;

		try {
			service = new Service(); 
			call = (Call)service.createCall();
			call.setTargetEndpointAddress( new URL(endPoint + "/josso/services/SSOIdentityManager") );
		    call.setOperation(SoapGetUsername);
		    call.setUseSOAPAction(true);
		    call.setSOAPActionURI("http://josso.org/gateway/ws/1.1/wsdl/soapbinding/IdentityManager/findUserInSession");
		    call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
		    call.setOperationName(new QName("http://josso.org/gateway/ws/1.1/wsdl/soapbinding/IdentityManager/findUserInSession", "findUserInSession"));

		    soaRequest = new FindUserInSessionRequestType();
		    soaRequest.setSsoSessionId(sessionID);
			Object _resp = call.invoke(new Object[] { soaRequest });
			if (_resp instanceof RemoteException) {
				throw ((RemoteException)_resp);
			}
		
			try {
				soapResponse = ((FindUserInSessionResponseType)_resp);
			} catch (Exception _exception) {
				soapResponse = ((FindUserInSessionResponseType)JavaUtils.convert(_resp, FindUserInSessionResponseType.class));
			}
			rtUsername = soapResponse.getSSOUser().getName();
			debug("soap return username : [" + rtUsername + "]");
		} catch (Exception ex) {
			debug("Exception : " + ex.getMessage());
			ex.printStackTrace();
		}
		return rtUsername;
	}

	private String soapGetSessionID(String josso_assertion_id)
	{
		String rtSessionID = null;
		Service service = null;
		Call call = null;
		ResolveAuthenticationAssertionRequestType soaRequest = null;
		ResolveAuthenticationAssertionResponseType soapResponse = null;

		try {
			service = new Service(); 
			call = (Call)service.createCall();
			call.setTargetEndpointAddress( new URL(endPoint + "/josso/services/SSOIdentityProvider") );
		    call.setOperation(SoapGetSessionID);
		    call.setUseSOAPAction(true);
		    call.setSOAPActionURI("http://josso.org/gateway/ws/1.1/wsdl/soapbinding/IdentityProvider/resolveAuthenticationAssertion");
		    call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
		    call.setOperationName(new QName("http://josso.org/gateway/ws/1.1/wsdl/soapbinding/IdentityProvider/resolveAuthenticationAssertion", "resolveAuthenticationAssertion"));

		    soaRequest = new ResolveAuthenticationAssertionRequestType();
		    soaRequest.setAssertionId(josso_assertion_id);
			Object _resp = call.invoke(new Object[] { soaRequest });
			if (_resp instanceof RemoteException) {
				throw ((RemoteException)_resp);
			}
		
			try {
				soapResponse = ((ResolveAuthenticationAssertionResponseType)_resp);
			} catch (Exception _exception) {
				soapResponse = ((ResolveAuthenticationAssertionResponseType)JavaUtils.convert(_resp, ResolveAuthenticationAssertionResponseType.class));
			}
			rtSessionID = soapResponse.getSsoSessionId();
			debug("soap return sessionID : [" + rtSessionID + "]");
		} catch (Exception ex) {
			debug("Exception : " + ex.getMessage());
			ex.printStackTrace();
		}
		return rtSessionID;
	}
	
	private String getCookie(HttpServletRequest req, String name)
	{
		String rtStr = null;
		Cookie cookies[] = null;

		cookies = req.getCookies();
		for (int idx = 0;idx < cookies.length;idx++) {
		    if (cookies[idx].getName().equals(name)) {
		    	rtStr = cookies[idx].getValue();
		    	break;
		    }
		}
		return rtStr;
	}
	
	private void setCookie(HttpServletResponse res, String name, String value)
	{
		Cookie cookie = null;
		
		cookie = new Cookie(name, value);
		cookie.setPath("/");
		//cookie.setMaxAge(min * 60);
		res.addCookie(cookie);
	}
	
	private String nvl(String argStr)
	{
		return (argStr == null) ? "" : argStr;
	}

	private void debug(String message)
	{
		if (debug) {
			log.debug("ppp : " + message);
		}
	}
}
