package ru.brandanalyst;

import ru.brandanalyst.core.util.ArticleCleaner;
import org.junit.Test;

/**
 * Alexandra Mikhaylova mikhaylova@yandex-team.ru
 */
public class ArticleCleanerTest {
    @Test
    public void simpleArticleCleanerTest() {
        String[] badArticleList = { "Исследовательская группа <a href=\"http://research.microsoft.com/en-us/events/techfest2012/video.aspx\">Applied Sciences Group</a> в Microsoft Research разрабатывает новую технологию сенсорных экранов, которая позволит сократить задержки (лаги) со стандартных сегодня 100 мс до 1 мс. Для чего это может потребоваться и что это даст в будущем демонстрируется на видео.<br/><br/><iframe width=\"560\" height=\"349\" src=\"http://www.youtube.com/embed/vOvQCPLkPt4\" frameborder=\"0\" allowfullscreen></iframe>",
            "<p>Неизвестные похитили в ночь на субботу, 10 марта, 19 миллионов рублей из отделения Сбербанка на западе Москвы. Об этом <a href=\"http://www.petrovka-38.org/news/8329\" target=\"_blank\">сообщает</a> столичное полицейское управление.&nbsp;</p><p>По данным полицейских, деньги были похищены из отделения на улице 1812-го года, дом 2. Злоумышленники вскрыли сейф банка, отключив сигнализацию. По факту возбуждено уголовное дело по статье &laquo;кража, совершенная в особо крупном размере&raquo; (ч. 4 ст. 158 УК).</p><p>По данным <a href=\"http://lifenews.ru/news/85067\" target=\"_blank\">Life News</a>, дверь в банк и в сейфовое хранилище не взламывали, а аккуратно открыли.  Следствие полагает, что у грабителей был ключ и они знали код от сейфа. По этой причине полицейские подозревают, что к ограблению причастны сотрудники отделения банка, их отвезли на допрос.&nbsp;</p>",
            "МОСКВА, 6 марта. /ТАСС-Телеком/. 16 марта текущего года макрорегиональный филиал &quot;Сибирь&quot; компании &quot;Ростелеком&quot; снижает стоимость безлимитного тарифного плана на внутризоновые..."};
        String[] articleCleanedList = new String[3];
        for (int i = 0; i < 3; i++) {
            articleCleanedList[i] = ArticleCleaner.cleanArticle(badArticleList[i]);
            System.out.println(articleCleanedList[i]);
        }
    }
}