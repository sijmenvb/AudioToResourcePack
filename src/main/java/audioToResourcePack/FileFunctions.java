package audioToResourcePack;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class FileFunctions {
	

	public static int countNoFiles(File direcory) {
		File[] listOfFiles = direcory.listFiles();
		int i = 0;
		for (File file : listOfFiles) {
			if (file.isFile()) {
				i++;
			} else if (file.isDirectory()) {
				i += countNoFiles(file);
			}
		}
		return i;
	}

//---- unzip ---- source: https://www.baeldung.com/java-compress-and-uncompress

	public static void unzip(ZipInputStream zipIn, String destinationPath) {
		try {
			File destDir = new File(destinationPath);
			byte[] buffer = new byte[1024];
			ZipEntry zipEntry;

			zipEntry = zipIn.getNextEntry();

			while (zipEntry != null) {
				File newFile = newFile(destDir, zipEntry);
				if (zipEntry.isDirectory()) {
					if (!newFile.isDirectory() && !newFile.mkdirs()) {
						throw new IOException("Failed to create directory " + newFile);
					}
				} else {
					// fix for Windows-created archives
					File parent = newFile.getParentFile();
					if (!parent.isDirectory() && !parent.mkdirs()) {
						throw new IOException("Failed to create directory " + parent);
					}

					// write file content
					FileOutputStream fos = new FileOutputStream(newFile);
					int len;
					while ((len = zipIn.read(buffer)) > 0) {
						fos.write(buffer, 0, len);
					}
					fos.close();
				}
				zipEntry = zipIn.getNextEntry();
			}
			zipIn.closeEntry();
			zipIn.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
		File destFile = new File(destinationDir, zipEntry.getName());

		String destDirPath = destinationDir.getCanonicalPath();
		String destFilePath = destFile.getCanonicalPath();

		if (!destFilePath.startsWith(destDirPath + File.separator)) {
			throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
		}

		return destFile;
	}

	// ---- delete directory ---- source:
	// https://softwarecave.org/2018/03/24/delete-directory-with-contents-in-java/

	public static void deleteDirectory(String dirpath) throws IOException {

		File file = new File(dirpath);
		deleteDirectoryRecursionJava6(file);

	}

	private static void deleteDirectoryRecursionJava6(File file) throws IOException {
		if (file.isDirectory()) {
			File[] entries = file.listFiles();
			if (entries != null) {
				for (File entry : entries) {
					deleteDirectoryRecursionJava6(entry);
				}
			}
		}
		if (!file.delete()) {
			throw new IOException("Failed to delete " + file);
		}
	}

	// ---- copy directory ---- source: https://www.baeldung.com/java-copy-directory

	public static void copydirTozip(String path, ZipOutputStream zipArch) {
		copydirTozip1(path, path, zipArch);
	}

	private static void copydirTozip1(String path, String originalPath, ZipOutputStream zipArch) {
		File[] file = (new File(path).listFiles());// get all files/directory's in
													// the folder
		for (File file2 : file) { // for every file
			if (file2.isDirectory()) { // if the file is a directory
				copydirTozip1(path + file2.getName() + "/", originalPath, zipArch); // recursively copy that directory
																					// as
																					// well.
			} else { // if it is a file
				try {
					// get the relative file path
					String filePath = path + file2.getName();
					filePath = filePath.substring((originalPath).length(), filePath.length());

					// create new entry for the file
					ZipEntry e = new ZipEntry(filePath);
					zipArch.putNextEntry(e);

					// write the content's of the file to the new zip entry.
					zipArch.write(Files.readAllBytes(file2.toPath()));
					zipArch.closeEntry();

				} catch (IOException e1) {
					System.err.println("Failed saving " + path);
					e1.printStackTrace();
				}
			}
		}
	}

	// ---- copy directory ---- source: https://www.baeldung.com/java-copy-directory
	// ---- save string as file ----

	public static void SaveAsFile(String s, String path) {
		try {
			PrintWriter out = new PrintWriter(path);
			out.write(s);
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	// ---- save string as file ----

}
