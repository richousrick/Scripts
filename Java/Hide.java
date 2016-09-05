import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * @author Richousrick
 */
public class Hide {

	/**
	 * Hides a given file
	 *
	 * @param path to the file
	 * @throws IOException
	 */
	static void hideFile(String path) throws IOException {
		String os = System.getProperty("os.name", "generic").toLowerCase();
		if(os.indexOf("win")>=0){
			hideFileWin(path);
		}else if(os.indexOf("mac")>=0||os.indexOf("nux")>=0){
			hideFileUnix(path);
		}else{
			System.out.println("Couldent determine host OS, to specify please add os (win,unix");
		}
	}
	
	/**
	 * Renames the given file so it starts with '.' to hide it in Unix based systems
	 *
	 * @param path to the given file
	 */
	public static void hideFileUnix(String path) {
		
		File f = new File(path);
		if(!f.getName().startsWith(".")){
			String name = '.'+f.getName();
			File newFile;
			if(f.getParent()==null){
				newFile = new File(name);
			}else{
				newFile = new File(f.getParent()+"\\"+name);
			}
			f.renameTo(newFile);
			System.out.println("File hidden sucessfully");
		}
	}

	/**
	 * Makes the given file a hidden system file to hide it in windows based systems
	 * 
	 * @param path to the given file
	 */
	static void hideFileWin(String path) throws IOException {
		File f = new File(path);
		Files.setAttribute(f.toPath(), "dos:hidden", true);
		Files.setAttribute(f.toPath(), "dos:system", true);
		System.out.println("File hidden sucessfully");
	}
	
	
	public static void main(String[] args) {
		if(args.length>0){
			File f = new File(args[0]);
			if(f.exists()){
				try {
					if(args.length>1){
						switch (args[1]) {
						case "win":
							hideFileWin(args[0]);
							break;
							
						case "unix":
							hideFileUnix(args[0]);
							break;

						default:
							System.out.println("Error. The possible options are \"win\" and \"unix\"");
						}
					}else{
						hideFile(args[0]);
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
