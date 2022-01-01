import java.io.File;

import ws.schild.jave.Encoder;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;

public class Main {

	public static void main(String[] args) {
		System.out.println("running..");
		
		//
		File folder = new File("src/main/resources");
		File[] listOfFiles = folder.listFiles();
		
		for (int i = 0; i < listOfFiles.length; i++) {
			  if (listOfFiles[i].isFile()) {
			    System.out.println("File " + getFileType(listOfFiles[i]));
			  } else if (listOfFiles[i].isDirectory()) {
			    System.out.println("Directory " + listOfFiles[i].getName());
			  }
			}
		/*
		// variables
		final String inFilePath = "src/main/resources/conversation engine.mp4";
		final String outFilePath = "src/main/resources/output2.ogg";
		final String oggFormat = "ogg";
		final String oggCodec = "libvorbis";
		
		try {
			File source = new File(inFilePath);
			File target = new File(outFilePath);
			
			System.out.println("does the in file exist:" + source.exists());
			System.out.println("does the out file exist:" + target.exists());

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
			System.out.println("does the out file exist:" + target.exists());
		} catch (Exception ex) {
			ex.printStackTrace();

		}
		//*/
	}
	
	private static String getFileType(File f) {
		String s = f.getName();
		return s.substring(s.lastIndexOf('.')+1);
		
	}
}