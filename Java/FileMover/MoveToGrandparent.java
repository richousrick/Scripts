package file_mover;

import java.io.File;
import java.util.Arrays;

/**
 * Moves a file or folder up a directory.<br>
 * Meaning the parent will become its sister directory.
 * @author Richousrick
 */
public class MoveToGrandparent {

	/**
	 * MoveToGrandParent [y] <file's>
	 * <> required
	 * [] optional
	 * [y] - move to grand parent even if file exists
	 */
	public static void main(String[] args) {
		System.out.println("input: "+Arrays.toString(args));
		boolean force = false;
		
		if(args.length>0){
			int i = 0;
			if(args[0].equals("y")){
				force = true;
				if(args.length<2){
					System.err.println("Error : MoveToGrandParent [y] <file's> \n[y] - optional, \n\tmove to grand parent even if file exists");
				}
				i=1;
			}
			
			for(; i< args.length; i++){
				File f = new File(args[i]);
				if(f.exists()){
					File destination = new File(new File(f.getParent()).getParent()+"/"+f.getName());
					if(!destination.exists()||force){
						f.renameTo(destination);
						System.out.println(destination);
					}else{
						System.err.println("Error: File with the name \""+f.getName()+"\" exists in the grand parent\nTo move anyway add parameter y");
					}
				}else{
					System.err.println("Error: File does not exist");
				}
			}
		}else{	
			System.err.println("Error : MoveToGrandParent [y] <file's> \n[y] - optional, \n\tmove to grand parent even if file exists");
		}
	}
}
