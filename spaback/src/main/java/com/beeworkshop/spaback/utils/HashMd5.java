package com.beeworkshop.spaback.utils;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * 
 * @author beeworkshop
 * @description 提供对MD5哈希的支持
 * 
 *              哈希加盐（salt）可以解决用户使用相同的密码哈希结果也相同的问题。
 * 
 *              salt=username+"a string"
 */
public class HashMd5 {
	public String hashMd5Encrypt(String passwd, String salt, int iterNO) {
		ByteSource hashSalt = ByteSource.Util.bytes(salt);
		String result = (new SimpleHash("MD5", passwd, hashSalt, iterNO)).toString();
		return result;
	}

	public String hashMd5Encrypt(String passwd) {
		String result = (new SimpleHash("MD5", passwd, "bolabola", 1024)).toString();
		return result;
	}

	public String hashMd5Encrypt(String passwd, String salt) {
		ByteSource hashSalt = ByteSource.Util.bytes(salt);
		String result = (new SimpleHash("MD5", passwd, hashSalt, 1024)).toString();
		return result;
	}
}