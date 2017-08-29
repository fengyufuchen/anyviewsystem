package com.anyview.utils.encryption;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * 密码加密
 * 
 * @author XSmallBird(XSB)
 */
public class EncryptDBPassWd {
	public static void main(String[] args) throws Exception {
		System.out.println("输入原密�?:");
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				System.in));
		String passwd = null;
		passwd = reader.readLine();
		System.out.println(MD5Util.getEncryptedPwd(passwd));
	}
}
