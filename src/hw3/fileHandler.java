package hw3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.ArrayList;

public class fileHandler {
	public static boolean createDirIfNotExist(String dir){
		File file = new File(dir);
		if (!file.exists() || !file.isDirectory()){
			file.mkdirs();
			return true;
		}
		return false;
	}
	
	public static boolean appendToFile(String dir,String content){
		try {
			File file = new File(dir);
			// if file doesn't exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.newLine();
			bw.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean deleteFile(String file_path) {
		// TODO Auto-generated method stub
		try{
			 
    		File file = new File(file_path);
 
    		if(file.delete()){
    			return true;
    		}else{
    			return false;
    		}
 
    	}catch(Exception e){

    		e.printStackTrace();
    		return false;
 
    	}
	}
	public static String getUniqueID(String file_folder) {
		File folder = new File(file_folder);
		File[] listOfFiles = folder.listFiles();
		if(listOfFiles.length == 0){
			return "1";
		}
		int[] id_list = new int[listOfFiles.length];
		//find max id and return max+1
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				try{
					id_list[i] = Integer.parseInt(listOfFiles[i].getName());
				}catch(Exception e){
					id_list[i] = 0;
				}
			} 
		}
		Arrays.sort(id_list);
		int u_id = id_list[id_list.length - 1] +1;
		String result = Integer.toString(u_id);
		return result;
	}

	public static String readFileContent(String file_path) {
		BufferedReader br = null;
		String result = "";
		try {
			
			File file = new File(file_path);
			if(!file.exists()) {
				System.out.println("ID: " + file_path + " not exists!");
				return null;
			}
 
			String sCurrentLine;
			br = new BufferedReader(new FileReader(file_path));
			while ((sCurrentLine = br.readLine()) != null) {
				result += sCurrentLine + "\n";
			}
 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
				return result;
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return null;
	}

	public static int countFileLineNumber(String file_path) {
		BufferedReader br = null;
		int LineNumber = 0;
		try {
			String sCurrentLine;
			br = new BufferedReader(new FileReader(file_path));
			while ((sCurrentLine = br.readLine()) != null) {
				LineNumber++;
			}
 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
				return LineNumber;
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return -1;
	}

	public static String retrieveRows(String file_folder,
			String field, String op, String value) {
		
		String result = "";
		File folder = new File(file_folder);
		File[] listOfFiles = folder.listFiles();
		if(listOfFiles.length == 0){
			return "No Result Found";
		}

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile() && !listOfFiles[i].getName().startsWith(".")) {
				BufferedReader br = null;
				try {
					String sCurrentLine;
					br = new BufferedReader(new FileReader(listOfFiles[i].toString()));
					while ((sCurrentLine = br.readLine()) != null) {
						if (satisfiedFormat( field, op,  value, sCurrentLine)){
							System.out.println(sCurrentLine);
//							result += sCurrentLine
						}
					}
		 
				} catch (IOException e) {
					e.printStackTrace();
					return "IO Exception in retrieving the files";
				} 
			} 
		}
		
		return result;

	}

	private static boolean satisfiedFormat(String field, String op, String value, String line) {
		String[] elements = line.split(",");
		double data = 0; 
		double v =  Double.parseDouble(value);
		if(field.equals("lati"))
			data = Double.parseDouble(elements[0]); 
		else if (field.equals("long"))
			data = Double.parseDouble(elements[1]);
		else if (field.equals("alti"))
			data = Double.parseDouble(elements[3]); 
		
		if (op.equals(">="))
			return data>=v;
		else if (op.equals("<="))
			return data<=v;
		else if (op.equals("="))
			return data==v;				
		else if (op.equals(">"))
			return data>v;
		else if (op.equals("<"))
			return data<v;
		
		return false;
	}

}
