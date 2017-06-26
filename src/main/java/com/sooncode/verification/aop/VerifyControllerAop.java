package com.sooncode.verification.aop;

 

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.util.ResourceUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.sooncode.verification.moduler.Method;
import com.sooncode.verification.moduler.VerificationResult;
import com.sooncode.verification.response.RespondHead;
import com.sooncode.verification.service.MethodParameter;
import com.sooncode.verification.service.MethodParameterManager;
import com.sooncode.verification.service.VerificationService;


/**controller验证aop
 * @author pc
 *
 */
public class VerifyControllerAop {

    public final static Log logger = LogFactory.getLog(VerifyControllerAop.class); 

    public void setConfLocation(String confLocation) throws FileNotFoundException{
        
        File file =  ResourceUtils.getFile(confLocation);
        String fileNames[];
        fileNames=file.list();

        for(int i=0;i<fileNames.length;i++)
        {
            File f =  ResourceUtils.getFile(confLocation+File.separatorChar+ fileNames[i]);
            new MethodParameter(f);
            logger.debug("[parameter_verification] 加载完成"+fileNames[i]+"参数配置文件");
        }

    } 

    
    
    public Object doAroundController(ProceedingJoinPoint joinPoint) throws Throwable{
        
        ServletRequestAttributes requestAttributes = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes());
        HttpServletRequest request = requestAttributes.getRequest();
       
        Object[] params = joinPoint.getArgs();
       
        Method i = MethodParameterManager.getMethod(request.getRequestURI());
        if (i == null) {
            logger.warn("[parameter_verification] '" + request.getRequestURL() + "'接口 ,没有参数描述");
            return joinPoint.proceed(params);
        }
        
        
        String str = new String();
        if(   params[0] instanceof String){
        	str = params[0].toString();
        }else{
        	SJson sj = new SJson(params[0]);
        	str = sj.getJsonString();
        }
        VerificationResult vr = VerificationService.verificationInterface(request.getMethod(), str, i);
        Map<String ,Object> map = new HashMap<>();
        if (vr.getIsPass()) {
            return joinPoint.proceed(params);
        } else {
            RespondHead rd = new RespondHead();
            rd.setFailureMessage(vr.getReason());
            map = rd.getRespondHeadMap();
            return map;
        }
    }
}
