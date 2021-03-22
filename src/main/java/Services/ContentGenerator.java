package Services;

import Entity.QRCodeData;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Hashtable;

public class ContentGenerator {
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

    public Image createQRCode(String qrCodeData, int qrCodeWidth, int qrCodeHeight) throws WriterException, IOException, BadElementException {
        String charset = "ISO-8859-5";
        /**
         * В hints задаются доп.параметры для генерации
         * кодировка для кириллицы, размеры Quite зоны
         */
        Hashtable hints = new Hashtable();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.MARGIN, 0);
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

    public PdfPTable createCodeDataTable(QRCodeData qrCodeData) throws IOException, DocumentException {
        // создаем шрифты для таблицы
        BaseFont bf = BaseFont.createFont("src/main/resources/FRABK.TTF", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font headerFont = new Font(bf, 11, Font.BOLD);
        Font bodyFont = new Font(bf, 8);

        // создаем таблицу
        PdfPTable table = new PdfPTable(1);
        table.setTotalWidth(200);

        // создаем ячейки, с невидимыми границами и выравниванием
        PdfPCell cellHeader = new PdfPCell();
        cellHeader.setBorder(Rectangle.NO_BORDER);
        cellHeader.setHorizontalAlignment(Element.ALIGN_LEFT);
        cellHeader.setVerticalAlignment(Element.ALIGN_CENTER);
        PdfPCell cellBody = new PdfPCell();
        cellBody.setBorder(Rectangle.NO_BORDER);
        cellBody.setHorizontalAlignment(Element.ALIGN_LEFT);
        cellBody.setVerticalAlignment(Element.ALIGN_CENTER);
        // генерация текста для таблицы
        Paragraph pHeader = new Paragraph(13, getHeaderText(qrCodeData), headerFont);
        Paragraph pBody = new Paragraph(8, getBodyText(qrCodeData), bodyFont);

        cellHeader.addElement(pHeader);
        cellBody.addElement(pBody);
        table.addCell(cellHeader);
        table.addCell(cellBody);
        return table;
    }

    /**
     * генерация текста для верхней части таблицы
     * @param qrCodeData
     * @return
     */
    public static String getHeaderText(QRCodeData qrCodeData) {
        return qrCodeData.getApprovedText() + "\n" + qrCodeData.getSetStatusDate();
    }

    /**
     * генерация текста для нижней части таблицы
     * @param qrCodeData
     * @return
     */
    public static String getBodyText(QRCodeData qrCodeData) {
        String text = "ПП: " + qrCodeData.getProjectRequirementNumber()
                + qrCodeData.getSeparatorOne() + qrCodeData.getProjectRequirementVersion() + "\nID: " + qrCodeData.getDmsId()
                + qrCodeData.getSeparatorTwo() + qrCodeData.getDmsType() + "\nОЛ: " + qrCodeData.getQuestionnaireNumber();
        return text;
    }
}
