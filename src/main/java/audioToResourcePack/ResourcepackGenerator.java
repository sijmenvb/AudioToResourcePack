package audioToResourcePack;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ResourcepackGenerator {
	private static final Exception NullPointerException = null;
	private final String name;
	private ZipOutputStream zipArch;
	private FileOutputStream f;

	public ResourcepackGenerator(String name) {
		super();
		this.name = name;

		try {
			f = new FileOutputStream(name + ".zip");
			zipArch = new ZipOutputStream(new BufferedOutputStream(f));
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}
	}

	public void generate() {
		deletePreviousResourcepack();
		LoadEmptyResourcePack();
		createRun();
		File inFile = LoadInputFolder();

		try {// see if there are any files to load
			loadSoundFiles(inFile);

			FileFunctions.copydirTozip(name + "\\", zipArch);// copy to zip

			try {
				zipArch.finish();
				zipArch.close();
				f.close();

			} catch (IOException e) {
				System.err.println("ERROR SAVING ZIP FILE!");
				e.printStackTrace();
			}
		} catch (Exception e1) {
			System.err.println("Failed to load any audio files");
			e1.printStackTrace();
			try {
				zipArch.finish();
				zipArch.close();
				f.close();

			} catch (IOException e) {
				System.err.println("ERROR SAVING ZIP FILE!");
				e.printStackTrace();
			}
			new File(name + ".zip").delete();
		}
		deletePreviousResourcepack();
	}

	public void createRun() {
		// check if the file already exist
		File tmpDir = new File("./run.bat");
		if (!tmpDir.exists()) {
			try {
				InputStream source = getClass().getClassLoader().getResourceAsStream("run.bat");
				File target = new File("./run.bat");

				Files.copy(source, target.toPath(), StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private void loadSoundFiles(File inFile) throws Exception {
		generateSoundsJson(convertSoundFiles(inFile));
	}

	private void generateSoundsJson(ArrayList<String> list) throws Exception {
		// TODO Auto-generated method stub
		if (list.isEmpty()) {
			System.err.println("ERROR no files could be loaded");
			throw NullPointerException;
		}

		String s = "";

		for (String name : list) {
			s += String.format("\t\"custom.%s\":{\n\t\t\"sounds\":[\n\t\t\"%s\"\n\t\t]\n\t},\n", name, name);
		}

		s = s.substring(0, s.length() - 2);// remove last comma

		FileFunctions.SaveAsFile(String.format("{\n%s\n}", s), name + "\\assets\\minecraft\\sounds.json");

	}

	private ArrayList<String> convertSoundFiles(File inFile) {
		// TODO put the sound files in to the right folder
		File outFile = new File(name + "\\assets\\minecraft\\sounds");
		return AudioConverter.convertAlltoOgg(inFile, outFile);
	}

	private File LoadInputFolder() {
		File f = new File("Input");
		f.mkdir();
		return f;
	}

	private void deletePreviousResourcepack() {
		try {
			FileFunctions.deleteDirectory(System.getProperty("user.dir") + "\\" + name);
		} catch (IOException e) {
			System.out.println("There was no previous ResourcePack to delete.");
		}
	}

	private void LoadEmptyResourcePack() {
		InputStream source;

		source = getClass().getClassLoader().getResourceAsStream("emptyResourcePack.zip");
		// get path from resource
		ZipInputStream zipIn = new ZipInputStream(source);
		FileFunctions.unzip(zipIn, name);

	}

}
