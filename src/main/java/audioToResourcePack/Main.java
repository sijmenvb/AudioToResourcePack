package audioToResourcePack;

import java.io.File;

public class Main {

	public static void main(String[] args) {
		System.out.println("running..");

		ResourcepackGenerator gen = new ResourcepackGenerator("Generated ResourcePack");

		gen.generate();

		System.out.println("Done! -- tool provided by sijmen_v_b");
	}

}