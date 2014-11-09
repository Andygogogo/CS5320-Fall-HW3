package hw3;
import java.util.Arrays;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class trajHandler {
	private static String root_dir = System.getProperty("user.dir");
	private static String data_dir = root_dir + "/Data/";
	private static String correct_format = "USAGE:\n"
			+ "INSERT INTO <tname> VALUES <sequence>;\n"
			+ "DELETE FROM <tname> TRAJECTORY <id>;\n"
			+ "RETRIEVE FROM <tname> TRAJECTORY <id>;\n"
			+ "RETRIEVE FROM <tname> COUNT OF <id>;";
	
	private static void handleInsertion(String q_list[]){
		if(q_list[1].equals("into") && q_list[3].equals("values")){
			String t_name = q_list[2];
			String sequence = q_list[4];
			sequence = sequence.replaceAll(";","");
			//the dir to store trajectory file
			String file_folder = data_dir + t_name + "/Trajectory/";
			fileHandler.createDirIfNotExist(file_folder);
			String traj_id = fileHandler.getUniqueID(file_folder);
			String file_path = file_folder + traj_id;
			if (fileHandler.appendToFile(file_path,sequence)){
				// At the end of the operation, the system should either report a success and return the trajectory id, or report a failure.
				System.out.printf("Success Insertion. The new trajectory id is:%d \n",traj_id);
			}
			else{
				System.out.println("Fail to execute the insertion opration");
			}
			
		}
		else{
			System.out.println("Error Format");
			System.out.println(correct_format);
		}
	}
	
	private static void handleDeletion(String q_list[]){
		if(q_list[1].equals("from") && q_list[3].equals("trajectory")){
			String t_name = q_list[2];
			String traj_id = q_list[4];
			traj_id = traj_id.replaceAll(";","");
			String file_folder = data_dir + t_name + "/Trajectory/";
			String file_path = file_folder + traj_id;
			
			if (fileHandler.deleteFile(file_path)){
				System.out.println("Success Deletion!");
			}
			else{
				System.out.println("Fail to execute the deletion opration");
			}
		}
		else{
			System.out.println("Error Format");
			System.out.println(correct_format);
		}
	}
	
	private static void handleRetrival(String q_list[]){
		String t_name, traj_id;
//		A retrieval of a trajectory from a trajectory set is by providing the name of the trajectory set and the identifier of the trajectory:
		if(q_list[1].equals("from") && q_list[3].equals("trajectory")){
			t_name = q_list[2];
			traj_id = q_list[4];
			traj_id = traj_id.replaceAll(";","");
			String file_folder = data_dir + t_name + "/Trajectory/";
			String file_path = file_folder + traj_id;

			String content = fileHandler.readFileContent(file_path);


			if (content != null){
				// At the end of the operation, the system should either report a success and return the trajectory id, or report a failure.
				System.out.printf("Success Retrival: \n%s",content);
			}
			else{
				System.out.println("Fail to execute the retrival opration");
			}
		}
//		return the number of measures in the trajectory:
		else if (q_list[1].equals("from") && q_list[3].equals("count") && q_list[4].equals("of")){	
			t_name = q_list[2];
			traj_id = q_list[5];
			traj_id = traj_id.replaceAll(";","");
			String file_folder = data_dir + t_name + "/Trajectory/";
			String file_path = file_folder + traj_id;

			int count = fileHandler.countFileLineNumber(file_path);


			if (count != -1){
				// At the end of the operation, the system should either report a success and return the trajectory id, or report a failure.
				System.out.printf("Success Retrival, the number of measures in the trajectory is: %s \n",count);
			}
			else{
				System.out.println("Fail to execute the retrival opration");
			}
		}
		else{
			System.out.println("Error Format");
			System.out.println(correct_format);
		}
	}
	public static void handleQuery(String query){	
		
		fileHandler.createDirIfNotExist(data_dir);
		
		String []q_list = query.split(" ");
		String s = q_list[0];
		if(query.equals("exit;")){
			return;
		}
		else if (s.equals("insert")){
			handleInsertion(q_list);
		}
		else if(s.equals("delete")){
			handleDeletion(q_list);
		}
		else if(s.equals("retrieve")){
			handleRetrival(q_list);
		}
		else{
			System.out.println("Error Format");
			System.out.println(correct_format);
		}
	}
	
	
}