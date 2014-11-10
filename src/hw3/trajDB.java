package hw3;
import java.io.*;
import java.util.Arrays;

public class trajDB {
	public static void main(String[] args){
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.println("Welcome to trajDB!");
		String query = "";
		while(!query.equals("exit;")){
			query = "";
			try {
				String currentLine = "";
				while (true){
					currentLine = br.readLine().toLowerCase();
					query += currentLine;
					if(currentLine.length() != 0 && currentLine.charAt(currentLine.length()-1)==';')
						break;
				}
				trajHandler.handleQuery(query);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
//			
//			try {
//				query = br.readLine().toLowerCase();
//				trajHandler.handleQuery(query);
//			} catch (IOException e) {
//				// TODO Auto-generated catch block        
//				System.out.println("IO error!");
//				e.printStackTrace();
//				System.exit(1);
//			}
		}
	}
}
