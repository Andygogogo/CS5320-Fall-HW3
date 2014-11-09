package hw3;
import java.io.*;
import java.util.Arrays;

public class trajDB {
	public static void main(String[] args){
		String query = "";
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.println("Welcome to trajDB!");
		while(!query.equals("exit;")){
			try {
				query = br.readLine().toLowerCase();
				trajHandler.handleQuery(query);
			} catch (IOException e) {
				// TODO Auto-generated catch block        
				System.out.println("IO error!");
				e.printStackTrace();
				System.exit(1);
			}
		}
	}
}
