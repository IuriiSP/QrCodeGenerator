package Entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Класс представляет собой сущность, включающую в себя
 * основные атрибуты для генерации QR кода:
 * <p>
 * approvedText - Текст утверждения
 * setStatusDate - Дата установки статуса (утверждение ОЛ)
 * projectRequirementNumber - Номер проектной потребности
 * separatorOne, separatorTwo - разделители
 * projectRequirementVersion - версия проектной потребности
 * dmsId - идентификатор DMS
 * dmsType - вид DMS
 * questionnaireNumber - номер опросного листа
 *
 * @version 1.0
 */
@Getter
@Setter
@RequiredArgsConstructor
public class QRCodeData {

    private final String approvedText;
    private final String setStatusDate;
    private final String projectRequirementNumber;
    private final String separatorOne;
    private final String projectRequirementVersion;
    private final String dmsId;
    private final String separatorTwo;
    private final String dmsType;
    private final String questionnaireNumber;

    /**
     * @return в возвращаемую строку добавлены статические данные для подписи соответствующих полей.
     */
    @Override
    public String toString() {
        return approvedText + "\n" + setStatusDate + "\n" + "ПП: " + projectRequirementNumber
                + separatorOne + projectRequirementVersion + "\n" + "ID: " + dmsId
                + separatorTwo + dmsType + "\n" + "ОЛ: " + questionnaireNumber;
    }
}
