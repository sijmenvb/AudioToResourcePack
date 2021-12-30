import java.io.File;

import ws.schild.jave.Encoder;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;

public class Main {

	public static void main(String[] args) {
		System.out.println("running..");

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

	}
}