import Services.ImageGenerator;
import Entity.QRCodeData;
import Services.WriterToPdf;
import com.google.zxing.WriterException;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import java.io.IOException;

/**
 * @version 1.0
 */
public class Application {

    public static void main(String[] args) {
        /**
         * параметры для теста
         */
        String approvedText = "Утвержено в электронном виде";
        String setStatusDate = "15.03.2021";
        String projectRequirementNumber = "100000001001744";
        String separatorOne = "/";
        String projectRequirementVersion = "2";
        String dmsId = "10001695791";
        String separatorTwo = "/";
        String dmsType = "ZOL";
        String questionnaireNumber = "Г.4.0000.2014З-ТЗС/ГТП-00. 000-ОВ.ОЛ1";

        QRCodeData qrCodeData = new QRCodeData(approvedText, setStatusDate, projectRequirementNumber, separatorOne,
                projectRequirementVersion, dmsId, separatorTwo, dmsType, questionnaireNumber);

        ImageGenerator generator = new ImageGenerator();

        try {
            Image image = generator.createQRCode(qrCodeData.toString(), 76, 76);
            WriterToPdf writerToPdf = new WriterToPdf();
            writerToPdf.writeToPdf("src/main/resources/ИсходникПример.pdf", image);
        } catch (WriterException | IOException | DocumentException e) {
            e.printStackTrace();
        }
    }
}
