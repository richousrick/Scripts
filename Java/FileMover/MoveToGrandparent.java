import java.io.File;

/**
 * Moves a file or folder up a directory.<br>
 * Meaning the parent will become its sister directory.
 * @author Richousrick
 */
public class MoveToGrandparent {

	/**
	 * MoveToParent <file> [y]
	 * <> required
	 * [] optional
	 * [y] - move to parent even if file exists
	 */
	public static void main(String[] args) {
		if(args.length<1){
			System.out.println("MoveToParent <file> [y]\n[y] - optional, \n\tmove to parent even if file exists");
		}else{
			File f = new File(args[0]);
			if(f.exists()){
				File destination = new File(new File(f.getParent()).getParent()+"/"+f.getName());
				if(!destination.exists()||(args.length>1&&args[1].equals("y"))){
					f.renameTo(destination);
					System.out.println(destination);
				}else{
					System.out.println("File with that name exists in the parent\nTo move anyway add parameter y");
				}
			}else{
				System.out.println("File does not exist");
			}
		}
	}
}
