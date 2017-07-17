package com.sunshine.chat;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class ValidationTool {

	private static final String TOKEN = "sunshine";
   
	public static boolean checkSignature(String signature,String timestamp,String nonce) {
		
		String[] params = new String[]{TOKEN,timestamp,nonce};
		
		Arrays.sort(params);
		
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < params.length; i++) {
			builder.append(params[i]);
		}
		System.out.println("拼接后的字符�?:"+builder.toString());
		
		MessageDigest md = null;
		String result = "";
		
		try {
			md = md.getInstance("SHA-1");
			byte[] digest = md.digest(builder.toString().getBytes());
			result = byteToStr(digest);	
			System.out.println("加密后的密文是："+result);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return result != null ?(result.equals(signature.toUpperCase())):false;
	}
	
	private static String byteToStr(byte[] str){
		String strDigest = "";
		for (int i = 0; i < str.length; i++) {
			strDigest += byteToHexStr(str[i]);
		}
		return strDigest;	
	}
	
	private static String byteToHexStr(byte mByte){
		char[] Digit = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
		char[] temp = new char[2];
		
		temp[0] = Digit[(mByte>>>4)& 0X0F];
		temp[1] = Digit[mByte & 0X0F];
		
		String s = new String(temp);
		return s;	
	}
	
}
		

