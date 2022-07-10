package graphicsTest;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.imageio.ImageIO;

public class JavaCreatePngWithTransparency {

	public static void main(String[] args) throws IOException {
//		URL url = JavaCreatePngWithTransparency.class.getResource(JavaCreatePngWithTransparency.class.getName());
//		Path path = null;
//		Path outPath = null;
		//path = Paths.get("");
		Path outPath = Paths.get("");//path.getParent();
		String timeNow = DateTimeFormatter
				.ofPattern("yyyy_MM_dd__HH_mm_ss_SSS")
				.format(LocalDateTime.now());
		String filename = "test_png_pic__" + timeNow + "__.png";
		File absOutFile = outPath.resolve(filename).toFile();
		int width = 300;
		int height = 300;

		BufferedImage bufferedImage = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = bufferedImage.createGraphics();
		g2d.setComposite(AlphaComposite.Clear);
		g2d.fillRect(0, 0, width, height);

		g2d.setComposite(AlphaComposite.Src);
		int alpha = 127; // 50% transparent
		g2d.setColor(new Color(255, 100, 100, alpha));
		g2d.fillRect(100, 100, 123, 123);

		g2d.setColor(new Color(0, 0, 0));
		g2d.fillRect(30, 30, 60, 60);

		g2d.dispose();

		ImageIO.write(bufferedImage, "png", absOutFile);
		System.out.println("File saved to:");
		System.out.println(absOutFile);


	}
}