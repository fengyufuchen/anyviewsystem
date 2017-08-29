package com.anyview.utils;

public class BinaryUtils {

	public static byte[] getTCRgihtArray(int tcRight){
		byte right[] = new byte[4];
		int flag = 1;
		for(int i=0;i<4;i++){
			if((tcRight & flag) != 0)
				right[i] = 1;
			flag = flag << 1;
		}
		return right;
	}
	
	public static boolean viewStuStatus(int tcRight){
		byte right[] = new byte[4];
		int flag = 1;
		for(int i=0;i<4;i++){
			if((tcRight & flag) != 0)
				right[i] = 1;
			flag = flag << 1;
		}
		return right[0] == 1;
	}
	
	public static boolean setClaStatus(int tcRight){
		byte right[] = new byte[4];
		int flag = 1;
		for(int i=0;i<4;i++){
			if((tcRight & flag) != 0)
				right[i] = 1;
			flag = flag << 1;
		}
		return right[1] == 1;
	}
	
	public static boolean resetStuPsw(int tcRight){
		byte right[] = new byte[4];
		int flag = 1;
		for(int i=0;i<4;i++){
			if((tcRight & flag) != 0)
				right[i] = 1;
			flag = flag << 1;
		}
		return right[2] == 1;
	}
	
	public static boolean manageStu(int tcRight){
		byte right[] = new byte[4];
		int flag = 1;
		for(int i=0;i<4;i++){
			if((tcRight & flag) != 0)
				right[i] = 1;
			flag = flag << 1;
		}
		return right[3] == 1;
	}
}
