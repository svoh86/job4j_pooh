package pooh;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Отправитель посылает запрос на добавление данных с указанием очереди (weather)
 * и значением параметра (temperature=18). Сообщение помещается в конец очереди.
 * Если очереди нет в сервисе, то нужно создать новую и поместить в нее сообщение.
 * Получатель посылает запрос на получение данных с указанием очереди.
 * Сообщение забирается из начала очереди и удаляется.
 * Если в очередь приходят несколько получателей, то они поочередно получают сообщения из очереди.
 * Каждое сообщение в очереди может быть получено только одним получателем.
 *
 * @author Svistunov Mikhail
 * @version 1.0
 */
public class QueueService implements Service {
    private final Map<String, ConcurrentLinkedQueue<String>> queue = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        return "POST".equals(req.httpRequestType()) ? post(req) : get(req);
    }

    private Resp post(Req req) {
        queue.putIfAbsent(req.getSourceName(), new ConcurrentLinkedQueue<>());
        queue.get(req.getSourceName()).add(req.getParam());
        return new Resp("", "200");
    }

    private Resp get(Req req) {
        ConcurrentLinkedQueue<String> linkedQueue = queue.get(req.getSourceName());
        return linkedQueue == null || linkedQueue.isEmpty()
                ? new Resp("", "204") : new Resp(linkedQueue.poll(), "200");
    }
}
