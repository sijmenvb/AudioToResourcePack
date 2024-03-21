package audioToResourcePack;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import ws.schild.jave.Encoder;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;

public class AudioConverter {
	// variables

	/**
	 * converts any sound file to a .ogg file
	 * 
	 * @param source the source sound/video file.
	 * @param target the new .ogg sound file.
	 * @return true if successful.
	 */
	public static Boolean anyToOgg(File source, File target) {
		//TODO text to speech
		final String oggFormat = "ogg";
		final String oggCodec = "libvorbis";
		if (!source.isFile()) {
			System.err.println("input " + source.getName() + " is not a file!");
			return false;
		}
		/*
		 * if (!target.isFile()) { System.err.println("output " +target.getName() +
		 * " is not a file!"); return false; }//
		 */

		try {

			// set the audio attributes (see https://github.com/a-schild/jave2 and
			// https://codereview.stackexchange.com/questions/20084/converting-audio-to-different-file-formats)
			AudioAttributes audioAttr = new AudioAttributes();
			audioAttr.setBitRate(128000);
			audioAttr.setChannels(2);
			audioAttr.setSamplingRate(44100);
			audioAttr.setCodec(oggCodec);

			// set the encoder
			EncodingAttributes encoAttrs = new EncodingAttributes();
			encoAttrs.setOutputFormat(oggFormat);
			encoAttrs.setAudioAttributes(audioAttr);

			// Encode
			Encoder encoder = new Encoder();
			encoder.encode(new MultimediaObject(source), target, encoAttrs);
			return true;
		} catch (Exception ex) {
			System.err.println("file " + source.getName() + " could not be converted to audio file (.ogg)!");
			return false;
		}
	}

	public static ArrayList<String> convertAlltoOgg(File sourceFolder, File targetFolder) {
		ArrayList<String> list = new ArrayList<String>();
		// System.out.println(String.format("%-50s", ""));// print clear line for
		// progress to override
		return convertAlltoOgg1(sourceFolder, targetFolder, list, 0, FileFunctions.countNoFiles(sourceFolder));
	}

	private static ArrayList<String> convertAlltoOgg1(File sourceFolder, File targetFolder, ArrayList<String> list,
			int count, int total) {
		File[] listOfFiles = sourceFolder.listFiles();// get all subfiles/folders
		for (File file : listOfFiles) {// go over all files
			if (file.isFile()) {// if it is a file convert it
				count++;
				System.out.println(String.format("%-50s",
						String.format("progress %d out of %d, converting %s", count, total, file.getName())));

				String name = getValidName(toOggFileName(file.getName()), list); // get a name that is not yet used for
																					// output
				File outFile = new File(targetFolder.getPath() + "/" + name + ".ogg");// create the destination file
				boolean sucsess = false;
				if (getFileType(file) == "ogg") {
					// copy the file
					sucsess = copyFile(file, outFile);
				} else {
					// convert the file
					sucsess = anyToOgg(file, outFile);
				}

				if (sucsess) {// if the conversion was successful
					list.add(name);// add the file name to the list
				} else {
					outFile.delete();// delete the file
				}

			} else if (file.isDirectory()) {// if it is a directory convert those files as well
				list = convertAlltoOgg1(file, targetFolder, list, count, total);
			}
		}

		return list; // return the list of all the file names.
	}

	private static boolean copyFile(File source, File target) {
		try {
			FileUtils.copyFile(source, target);
			return true;
		} catch (IOException e) {
			System.err.println(source.getName() + " could not be copied!");
			return false;
		}

	}

	/**
	 * takes a file name and replaces the type with .ogg
	 * 
	 * @param s
	 * @return the file as a [name].ogg file
	 */
	private static String toOggFileName(String s) {
		return s.substring(0, s.lastIndexOf('.')) + ".ogg";
	}

	private static String getValidName(String s, ArrayList<String> list) {
		String name = s.substring(0, s.lastIndexOf('.')).replace(" ", "_").replace("'", "").toLowerCase();
		String out = name;
		int i = 1;
		while (list.contains(out)) {
			out = name + i;
			i++;
		}

		return out;
	}

	private static String getFileType(File f) {
		String s = f.getName();
		return s.substring(s.lastIndexOf('.') + 1);

	}

}
