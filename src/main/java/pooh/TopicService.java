package pooh;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Отправитель посылает запрос на добавление данных с указанием топика (weather)
 * и значением параметра (temperature=18). Сообщение помещается в конец каждой
 * индивидуальной очереди получателей. Если топика нет в сервисе, то данные игнорируются.
 * Получатель посылает запрос на получение данных с указанием топика.
 * Если топик отсутствует, то создается новый. А если топик присутствует,
 * то сообщение забирается из начала индивидуальной очереди получателя и удаляется.
 * Когда получатель впервые получает данные из топика –
 * для него создается индивидуальная пустая очередь.
 * Все последующие сообщения от отправителей с данными для этого топика помещаются в эту очередь тоже.
 * Таким образом в режиме "topic" для каждого потребителя своя будет уникальная очередь с данными,
 * в отличие от режима "queue", где для все потребители получают данные из одной и той же очереди.
 *
 * @author Svistunov Mikhail
 * @version 1.0
 */
public class TopicService implements Service {
    private final Map<String, ConcurrentHashMap<String, ConcurrentLinkedQueue<String>>> topic
            = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        return "POST".equals(req.httpRequestType()) ? post(req) : get(req);
    }

    private Resp get(Req req) {
        topic.putIfAbsent(req.getSourceName(), new ConcurrentHashMap<>());
        topic.get(req.getSourceName())
                .putIfAbsent(req.getParam(), new ConcurrentLinkedQueue<>());
        String text = topic.get(req.getSourceName())
                .get(req.getParam())
                .poll();
        return text == null ? new Resp("", "204") : new Resp(text, "200");
    }

    private Resp post(Req req) {
        ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> topics = topic.get(req.getSourceName());
        if (topics != null) {
            topics.values()
                    .forEach(c -> c.add(req.getParam()));
        }
        return new Resp("", "200");
    }
}
