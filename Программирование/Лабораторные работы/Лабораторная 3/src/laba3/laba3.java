package laba3;

import java.io.IOException;
import java.util.Scanner;

public class laba3 {

	public static void main(String[] args) throws IOException {
			Scanner in = new Scanner(System.in);
			while(in.hasNextLine()){
				String str = in.nextLine();
				removal(str);
			}
			
			in.close();
	}

	private static void removal(String temp) {
	//	String[] arr = temp.split(" ");
	//	temp = "";
	//	for (int i = 0; i < arr.length; i++){
	//		if (arr[i].length() > 1){
	//			temp += arr[i];
	//			if (i != arr.length - 1){
	//				temp += " ";
	//			}
	//		}
	//	}	
		
		StringBuilder str = new StringBuilder(" " + temp + " ");
		
		for (int i = 1; i < str.length() - 1; i++){
			if ((str.charAt(i - 1) == ' ') && (str.charAt(i + 1) == ' ')){
				str.deleteCharAt(i);
			}
		}
		
		for (int i = 1; i < str.length() - 1; i++){
			if (str.charAt(i) == ' '){
				if ((str.charAt(i - 1) != ' ') && (str.charAt(i + 1) != ' ')){
					continue;
				}
				else {
					str.deleteCharAt(i);
					i--;
				}
			}
		}
		
		if (str.charAt(0) == ' '){
			while (str.charAt(0) == ' ')
			{
				str.deleteCharAt(0);
			}
		}
		
		if (str.charAt(str.length() - 1) == ' '){
			while (str.charAt(str.length() - 1) == ' ')
			{
				str.deleteCharAt(str.length() - 1);
			}
		}
		 

		temp = str.toString();
		
		System.out.println(temp);
	}

}
