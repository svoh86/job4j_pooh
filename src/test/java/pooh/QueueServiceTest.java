package pooh;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class QueueServiceTest {

    @Test
    public void whenPostThenGetQueue() {
        QueueService queueService = new QueueService();
        String paramForPostMethod = "temperature=18";
        queueService.process(
                new Req("POST", "queue", "weather", paramForPostMethod)
        );
        Resp result = queueService.process(
                new Req("GET", "queue", "weather", null)
        );
        assertThat(result.text()).isEqualTo("temperature=18");
    }

    @Test
    public void whenGetQueueWithoutPost() {
        QueueService queueService = new QueueService();
        Resp result = queueService.process(
                new Req("GET", "queue", "weather", null)
        );
        assertThat(result.status()).isEqualTo("204");
    }

    @Test
    public void whenTwicePostThenTwiceGetQueue() {
        QueueService queueService = new QueueService();
        queueService.process(
                new Req("POST", "queue", "weather", "temperature=18")
        );
        queueService.process(
                new Req("POST", "queue", "weather", "temperature=20")
        );
        queueService.process(
                new Req("GET", "queue", "weather", null)
        );
        Resp result = queueService.process(
                new Req("GET", "queue", "weather", null)
        );
        assertThat(result.text()).isEqualTo("temperature=20");
    }

    @Test
    public void whenPostThenTwiceGetQueue() {
        QueueService queueService = new QueueService();
        queueService.process(
                new Req("POST", "queue", "weather", "temperature=18")
        );
        Resp result = queueService.process(
                new Req("GET", "queue", "weather", null)
        );
        Resp result2 = queueService.process(
                new Req("GET", "queue", "weather", null)
        );
        assertThat(result.text()).isEqualTo("temperature=18");
        assertThat(result2.text()).isEqualTo("");
    }
}