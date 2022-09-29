package pooh;

/**
 * Класс служит для парсинга входящего запроса.
 *
 * @author Svistunov Mikhail
 * @version 1.0
 */
public class Req {
    /**
     * httpRequestType - GET или POST. Он указывает на тип запроса.
     * poohMode - указывает на режим работы: queue или topic.
     * sourceName - имя очереди или топика.
     * param - содержимое запроса
     */
    private final String httpRequestType;
    private final String poohMode;
    private final String sourceName;
    private final String param;

    public Req(String httpRequestType, String poohMode, String sourceName, String param) {
        this.httpRequestType = httpRequestType;
        this.poohMode = poohMode;
        this.sourceName = sourceName;
        this.param = param;
    }

    public static Req of(String content) {
        String[] strings = content.split("(/|\\s)");
        String httpRequestType = strings[0];
        String poohMode = strings[2];
        String sourceName = strings[3];
        String param = strings[strings.length - 1];
        if ("GET".equals(httpRequestType)
            && "topic".equals(poohMode)) {
            param = strings[4];
        } else if ("GET".equals(httpRequestType)) {
            param = "";
        }
        return new Req(httpRequestType, poohMode, sourceName, param);
    }

    public String httpRequestType() {
        return httpRequestType;
    }

    public String getPoohMode() {
        return poohMode;
    }

    public String getSourceName() {
        return sourceName;
    }

    public String getParam() {
        return param;
    }
}
