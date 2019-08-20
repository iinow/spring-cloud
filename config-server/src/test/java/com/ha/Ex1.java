package com.ha;

import java.util.Scanner;

import org.junit.Test;

public class Ex1 {

	@Test
	public void dd() {
		for(int i = 1; i <= 10; i++) {
			int sum = calc(i);
			System.out.println(i+": "+sum);
		}
	}
	
	private int calc(int n) {
		if(n == 1) {
			return 1;
		}
		int res = 0;
		if(n % 2 == 0) {
			res = n / 2;
		}else {
			res = 3 * n + 1;
		}
		return 1 + calc(res);
	}
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		String str = scanner.nextLine();
		String str2 = scanner.nextLine();
		
		System.out.println(str+" "+ str2);
	}
}
