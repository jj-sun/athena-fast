package com.athena.common.aspect;

import com.athena.common.annotation.Log;
import com.athena.common.constant.Constant;
import com.athena.common.utils.HttpContextUtils;
import com.athena.common.utils.IPUtils;
import com.athena.modules.sys.entity.SysLog;
import com.athena.modules.sys.form.LoginUser;
import com.athena.modules.sys.repository.SysLogRepository;
import com.athena.modules.sys.service.SysLogService;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Date;


/**
 * 系统日志，切面处理类
 *
 * @author Mr.sun
 */
@Aspect
@Component
public class SysLogAspect {
	@Autowired
	private SysLogRepository sysLogRepository;
	
	@Pointcut("@annotation(com.athena.common.annotation.Log)")
	public void logPointCut() { 
		
	}

	@Around("logPointCut()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		long beginTime = System.currentTimeMillis();
		//执行方法
		Object result = point.proceed();
		//执行时长(毫秒)
		long time = System.currentTimeMillis() - beginTime;

		//保存日志
		saveSysLog(point, time);

		return result;
	}

	private void saveSysLog(ProceedingJoinPoint joinPoint, long time) {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();

		SysLog sysLog = new SysLog();
		sysLog.setLogType(Constant.LogType.OPERATE.getValue());
		Log syslog = method.getAnnotation(Log.class);
		if(syslog != null){
			//注解上的描述
			sysLog.setOperation(syslog.value());
		}

		//请求的方法名
		String className = joinPoint.getTarget().getClass().getName();
		String methodName = signature.getName();
		sysLog.setMethod(className + "." + methodName + "()");

		//请求的参数
		Object[] args = joinPoint.getArgs();
		try{
			String params = new Gson().toJson(args);
			sysLog.setParams(params);
		}catch (Exception e){

		}

		//获取request
		HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
		//设置IP地址
		sysLog.setIp(IPUtils.getIpAddr(request));
		sysLog.setTime(time);

		//用户名
		String username = ((LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser().getUsername();
		sysLog.setUsername(username);

		sysLog.setTime(time);
		sysLog.setCreateDate(new Date());
		//保存系统日志
		sysLogRepository.save(sysLog);
	}
}
