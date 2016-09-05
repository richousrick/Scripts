import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * @author Richousrick
 */
public class UnHide {

	/**
	 * un-hides a given file
	 *
	 * @param path to the file
	 * @throws IOException
	 */
	static void unHideFile(String path) throws IOException {
		String os = System.getProperty("os.name", "generic").toLowerCase();
		if(os.indexOf("win")>=0){
			unHideFileWin(path);
		}else if(os.indexOf("mac")>=0||os.indexOf("nux")>=0){
			unHideFileUnix(path);
		}else{
			System.out.println("Couldent determine host OS, to specify please add os (win,unix");
		}
	}
	
	/**
	 * Renames the given file so it doesn't start with '.' to un-hide it in Unix based systems
	 *
	 * @param path to the given file
	 */
	public static void unHideFileUnix(String path) {
		
		File f = new File(path);
		if(f.getName().startsWith(".")){
			String name = f.getName().substring(1);
			File newFile;
			if(f.getParent()==null){
				newFile = new File(name);
			}else{
				newFile = new File(f.getParent()+"\\"+name);
			}
			f.renameTo(newFile);
			System.out.println("File unhidden sucessfully");
		}
	}

	/**
	 * Makes the given file a non-hidden non system file to un-hide it in windows based systems
	 * 
	 * @param path to the given file
	 */
	static void unHideFileWin(String path) throws IOException {
		File f = new File(path);
		Files.setAttribute(f.toPath(), "dos:hidden", false);
		Files.setAttribute(f.toPath(), "dos:system", false);
		System.out.println("File unhidden sucessfully");
	}
	
	/**
	 * @param args an array of strings of length 1 or 2 <br>
	 * args[0] is the path to the file to hide <br>
	 * args[1] is optional and is used to specify the OS, it can be either win or unix
	 */
	public static void main(String[] args) {
		if(args.length>0){
			File f = new File(args[0]);
			if(f.exists()){
				try {
					if(args.length>1){
						switch (args[1]) {
						case "win":
							unHideFileWin(args[0]);
							break;
							
						case "unix":
							unHideFileUnix(args[0]);
							break;

						default:
							System.out.println("Error. The possible options are \"win\" and \"unix\"");
						}
					}else{
						unHideFile(args[0]);
					}
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else{
				System.out.println("Error. File \""+args[0]+"\" does not exist");
			}
		}else{
			System.out.println("Error. Requires target destination");
		}
	}
}
