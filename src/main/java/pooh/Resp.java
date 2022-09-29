package pooh;

/**
 * Resp - ответ от сервиса.
 *
 * @author Svistunov Mikhail
 * @version 1.0
 */
public class Resp {
    /**
     * text - текст ответа.
     * status  - это HTTP response status codes.
     * И, для примера, что если запрос прошел, то статус = 200, а если нет данных, то статус = 204.
     */
    private final String text;
    private final String status;

    public Resp(String text, String status) {
        this.text = text;
        this.status = status;
    }
    public String text() {
        return text;
    }

    public String status() {
        return status;
    }
}
