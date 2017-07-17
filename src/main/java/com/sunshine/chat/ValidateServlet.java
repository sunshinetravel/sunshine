package com.sunshine.chat;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 接受来自微信服务器转发过来的请求
 * 
 * 该程序部署在我们自己的服务器�?
 * @author 黄伟
 * @version 1.0
 */
@SuppressWarnings("serial")
public class ValidateServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
//		super.doGet(req, resp);
//		微信加密的签名字符串
		String signature = req.getParameter("signature"); 
//		时间�?
		String timestamp = req.getParameter("timestamp"); 
//		随机�?
		String nonce = req.getParameter("nonce"); 
//		随机字符�?
		String echostr = req.getParameter("echostr"); 
		
		System.out.println("微信加密的签名字符串" + signature);
		System.out.println("时间�?" + timestamp);
		System.out.println("随机�?" + nonce);
		System.out.println("随机字符�?" + echostr);
		
		PrintWriter out = resp.getWriter();
		if (ValidationTool.checkSignature(signature, timestamp, nonce)) {
			System.out.println("验证成功�?");
			out.print(echostr);			
		}else {
			System.out.println("验证失败�?");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
	}

}

