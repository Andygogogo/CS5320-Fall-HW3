package hw3;
import java.util.ArrayList;
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
			+ "CREATE <tname>;\n"
			+ "INSERT INTO <tname> VALUES <sequence>;\n"
			+ "DELETE FROM <tname> TRAJECTORY <id>;\n"
			+ "RETRIEVE FROM <tname> TRAJECTORY <id>;\n"
			+ "RETRIEVE FROM <tname> COUNT OF <id>;\n"
			+ "RETRIEVE FROM <tname> WHERE <field><op><val>;\n"
			+ "EXIT;";

	public static final String operation_list[] = {">=","<=",">","<","="};
	public static final ArrayList<String> valid_colums = new ArrayList<String>(Arrays.asList("lati", "long", "alti"));


	private static void createTable(String[] q_list) {
		if(q_list.length == 2 ){
			String t_name = q_list[1];
			//the dir to store trajectory file
			String file_folder = data_dir + t_name + "/Trajectory/";
			if (fileHandler.createDirIfNotExist(file_folder)){
				// At the end of the operation, the system should either report a success and return the trajectory id, or report a failure.
				System.out.printf("Success Creating Table:%s \n",t_name);
			}
			else{
				System.out.println("Fail to execute the creation opration");
			}
			
		}
		else{
			System.out.println("Error creation Format");
			System.out.println(correct_format);
		}
		
	}
	private static void handleInsertion(String q_list[]){
		if(q_list.length == 5 && q_list[1].equals("into") && q_list[3].equals("values")){
			String t_name = q_list[2];
			String sequence = q_list[4];
			//the dir to store trajectory file
			String file_folder = data_dir + t_name + "/Trajectory/";
			//check if the table exists
			File fd = new File(file_folder);
			if (!fd.exists() || !fd.isDirectory()){
				System.out.println("Error: Table does not exist, USE 'create <tname>;' first");
				return;
			}
			String traj_id = fileHandler.getUniqueID(file_folder);
			String file_path = file_folder + traj_id;
			if (fileHandler.appendToFile(file_path,sequence)){
				// At the end of the operation, the system should either report a success and return the trajectory id, or report a failure.
				System.out.printf("Success Insertion. The new trajectory id is:%s \n",traj_id);
			}
			else{
				System.out.println("Fail to execute the insertion opration");
			}
			
		}
		else{
			System.out.println("Error insertion Format");
			System.out.println(correct_format);
		}
	}
	
	private static void handleDeletion(String q_list[]){
		if(q_list.length == 5 && q_list[1].equals("from") && q_list[3].equals("trajectory")){
			String t_name = q_list[2];
			String traj_id = q_list[4];
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
			System.out.println("Error deletion Format");
			System.out.println(correct_format);
		}
	}
	
	private static void handleRetrieval(String q_list[]){
		String t_name, traj_id;
//		A retrieval of a trajectory from a trajectory set is by providing the name of the trajectory set and the identifier of the trajectory:
		if(q_list.length == 5 && q_list[1].equals("from") && q_list[3].equals("trajectory")){
			t_name = q_list[2];
			traj_id = q_list[4];
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
		else if (q_list.length == 6 && q_list[1].equals("from") && q_list[3].equals("count") && q_list[4].equals("of")){	
			t_name = q_list[2];
			traj_id = q_list[5];
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
		//advanced retrival implement a more advanced querying mechanism, however, implement a retrieval option of the form: RETRIEVE FROM <tname> WHERE <field><op><val>;
		else if (q_list.length == 5 && q_list[1].equals("from") && q_list[3].equals("where")){	
			handleAdvancedRetrieval(q_list);
		}
		else{
			System.out.println("Error retrival Format");
			System.out.println(correct_format);
		}

	}
	private static void handleAdvancedRetrieval(String[] q_list) {
		String t_name = q_list[2];
		String field_op_value = q_list[4];
		String params[] = getQueryParams(field_op_value);
		
		//the format is correct 
		if (params[0].equals("true")){
			String field = params[1];
			String op = params[2];
			String value = params[3];
			String file_folder = data_dir + t_name +"/Trajectory/";
			String result = fileHandler.retrieveRows(file_folder,field,op,value);
		}
		else{
			System.out.println("Error advanced retrival Format");
			System.out.println( "RETRIEVE FROM <tname> WHERE <field><op><val>;\n <field> should be 'lati','long' or 'alti'.");

		}
	}
	//return <"true"("false"), field, op, value>
	private static String[] getQueryParams(String field_op_value){
		String result[] = new String[4];
		result[0] = "false";
		for(int i = 0;i<operation_list.length;i++){
			if(field_op_value.contains(operation_list[i])){
				result[1] = field_op_value.split(operation_list[i])[0];
				result[2] = operation_list[i];
				result[3] = field_op_value.split(operation_list[i])[1];
				if(valid_colums.contains(result[1])){
					result[0] = "true";
				}
				break;
			}
		}
		return result;
	}

	public static void handleQuery(String query){	
		
		fileHandler.createDirIfNotExist(data_dir);
		query = query.trim();
		if (query.charAt(query.length()-1) == ';'){
			query = query.substring(0,query.length()-1);
		}
		String []q_list = query.split(" ");
		String s = q_list[0];
		if(query.equals("exit")){
			System.out.println("GoodBye!");
			return;
		}
		else if (s.equals("create")){
			createTable(q_list);
		}
		else if (s.equals("insert")){
			handleInsertion(q_list);
		}
		else if(s.equals("delete")){
			handleDeletion(q_list);
		}
		else if(s.equals("retrieve")){
			handleRetrieval(q_list);
		}
		else{
			System.out.println("Error Format");
			System.out.println(correct_format);
		}
	}

	
	
}