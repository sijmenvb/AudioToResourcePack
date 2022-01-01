import java.io.File;
public class Main {

	public static void main(String[] args) {
		System.out.println("running..");
		
		//
		File folder = new File("src/main/resources");
		File[] listOfFiles = folder.listFiles();
		
		
		final String inFilePath = "src/main/resources/conversation engine.mp4";
		final String outFilePath = "src/main/resources/output2.ogg";
		
        File source = new File(inFilePath);
		File target = new File(outFilePath);
		
		AudioConverter.anyToOgg(source,target);
	}
	
	
}