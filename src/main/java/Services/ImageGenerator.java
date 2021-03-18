package Services;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Hashtable;

public class ImageGenerator {
    private static final int BLACK = 0xFF000000;
    private static final int WHITE = 0xFFFFFFFF;

    /**
     * To Generate QR Code Image from text
     *
     * @param qrCodeData   текст для QR Code image
     * @param qrCodeHeight QR Code image height
     * @param qrCodeWidth  QR Code image width
     * @return image - QR код в формате изображения
     * @throws WriterException
     * @throws IOException
     */

    public Image createQRCode(String qrCodeData, int qrCodeHeight, int qrCodeWidth) throws WriterException, IOException, BadElementException {
        String charset = "ISO-8859-5";
        Hashtable hints = new Hashtable();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        BitMatrix matrix = new QRCodeWriter().encode(new String(qrCodeData.getBytes(charset), charset),
                BarcodeFormat.QR_CODE, qrCodeWidth, qrCodeHeight, hints);
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                bi.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
            }
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bi, "png", baos);
        baos.flush();
        byte[] imageInByte = baos.toByteArray();
        baos.close();
        return Image.getInstance(imageInByte);
    }
}
