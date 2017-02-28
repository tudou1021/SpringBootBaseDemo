package com.demo.utils;

import com.github.pagehelper.StringUtil;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;

public class IPv4Util {

	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (StringUtil.isNotEmpty(ip)) {
			if(ip.indexOf(',') > 0)
				ip = ip.substring(0, ip.indexOf(","));
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknow".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	public static long ipToLong(String ipAddress) {

		// ipAddressInArray[0] = 192
		String[] ipAddressInArray = ipAddress.split("\\.");
		long result = 0;
		for (int i = 0; i < ipAddressInArray.length; i++) {
			int power = 3 - i;
			int ip = Integer.parseInt(ipAddressInArray[i]);
			result += ip * Math.pow(256, power);
		}
		return result;
	}

	public static String longToIp(long i) {
		return ((i >> 24) & 0xFF) + "." + ((i >> 16) & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + (i & 0xFF);
	}

	/**
	 * 把IP地址转化为字节数组
	 * 
	 * @param ipAddr
	 * @return byte[]
	 */
	public static byte[] ipToBytesByInet(String ipAddr) {
		try {
			return InetAddress.getByName(ipAddr).getAddress();
		} catch (Exception e) {
			throw new IllegalArgumentException(ipAddr + " is invalid IP");
		}
	}

	/**
	 * 把IP地址转化为int
	 * 
	 * @param ipAddr
	 * @return int
	 */
	public static byte[] ipToBytesByReg(String ipAddr) {
		byte[] ret = new byte[4];
		try {
			String[] ipArr = ipAddr.split("\\.");
			ret[0] = (byte) (Integer.parseInt(ipArr[0]) & 0xFF);
			ret[1] = (byte) (Integer.parseInt(ipArr[1]) & 0xFF);
			ret[2] = (byte) (Integer.parseInt(ipArr[2]) & 0xFF);
			ret[3] = (byte) (Integer.parseInt(ipArr[3]) & 0xFF);
			return ret;
		} catch (Exception e) {
			throw new IllegalArgumentException(ipAddr + " is invalid IP");
		}

	}

	/**
	 * 字节数组转化为IP
	 * 
	 * @param bytes
	 * @return int
	 */
	public static String bytesToIp(byte[] bytes) {
		return new StringBuffer().append(bytes[0] & 0xFF).append('.').append(bytes[1] & 0xFF).append('.')
				.append(bytes[2] & 0xFF).append('.').append(bytes[3] & 0xFF).toString();
	}

	/**
	 * 把IP地址转化为int
	 * 
	 * @param ipAddr
	 * @return int
	 */
	public static int ipToInt(String ipAddr) {
		try {
			return ByteUtil.bytes2Int(ipToBytesByInet(ipAddr));
		} catch (Exception e) {
			throw new IllegalArgumentException(ipAddr + " is invalid IP");
		}
	}

	/**
	 * 把int->ip地址
	 * 
	 * @param ipInt
	 * @return String
	 */
	public static String intToIp(int ipInt) {
		return new StringBuilder().append(((ipInt >> 24) & 0xff)).append('.').append((ipInt >> 16) & 0xff).append('.')
				.append((ipInt >> 8) & 0xff).append('.').append((ipInt & 0xff)).toString();
	}

	/**
	 * 把192.168.1.1/24 转化为int数组范围
	 * 
	 * @param ipAndMask
	 * @return int[]
	 */
	public static int[] getIPIntScope(String ipAndMask) {

		String[] ipArr = ipAndMask.split("/");
		if (ipArr.length != 2) {
			throw new IllegalArgumentException("invalid ipAndMask with: " + ipAndMask);
		}
		int netMask = Integer.valueOf(ipArr[1].trim());
		if (netMask < 0 || netMask > 31) {
			throw new IllegalArgumentException("invalid ipAndMask with: " + ipAndMask);
		}
		int ipInt = IPv4Util.ipToInt(ipArr[0]);
		int netIP = ipInt & (0xFFFFFFFF << (32 - netMask));
		int hostScope = (0xFFFFFFFF >>> netMask);
		return new int[] { netIP, netIP + hostScope };

	}

	/**
	 * 把192.168.1.1/24 转化为IP数组范围
	 * 
	 * @param ipAndMask
	 * @return String[]
	 */
	public static String[] getIPAddrScope(String ipAndMask) {
		int[] ipIntArr = IPv4Util.getIPIntScope(ipAndMask);
		return new String[] { IPv4Util.intToIp(ipIntArr[0]), IPv4Util.intToIp(ipIntArr[0]) };
	}

	/**
	 * 根据IP 子网掩码（192.168.1.1 255.255.255.0）转化为IP段
	 * 
	 * @param ipAddr
	 *            ipAddr
	 * @param mask
	 *            mask
	 * @return int[]
	 */
	public static int[] getIPIntScope(String ipAddr, String mask) {

		int ipInt;
		int netMaskInt = 0, ipcount = 0;
		try {
			ipInt = IPv4Util.ipToInt(ipAddr);
			if (null == mask || "".equals(mask)) {
				return new int[] { ipInt, ipInt };
			}
			netMaskInt = IPv4Util.ipToInt(mask);
			ipcount = IPv4Util.ipToInt("255.255.255.255") - netMaskInt;
			int netIP = ipInt & netMaskInt;
			int hostScope = netIP + ipcount;
			return new int[] { netIP, hostScope };
		} catch (Exception e) {
			throw new IllegalArgumentException("invalid ip scope express  ip:" + ipAddr + "  mask:" + mask);
		}

	}

	/**
	 * 根据IP 子网掩码（192.168.1.1 255.255.255.0）转化为IP段
	 * 
	 * @param ipAddr
	 *            ipAddr
	 * @param mask
	 *            mask
	 * @return String[]
	 */
	public static String[] getIPStrScope(String ipAddr, String mask) {
		int[] ipIntArr = IPv4Util.getIPIntScope(ipAddr, mask);
		return new String[] { IPv4Util.intToIp(ipIntArr[0]), IPv4Util.intToIp(ipIntArr[0]) };
	}

}
