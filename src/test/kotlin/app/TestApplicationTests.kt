package app

import bl.UrlHandler
import org.assertj.core.api.StringAssert
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class TestApplicationTests {

    @Test
    fun minIndex() {
        var agent = UrlHandler(0)
        var string = agent.indexToString(1L)
        assert(string.equals("A"))
    }

    @Test
    fun maxIndex(){
        //73^8
        var agent = UrlHandler(10000)
        var string = agent.indexToString(806460091894081L)
        println(string)
        assert(string.equals("========"))
    }

    @Test
    fun shortUrl(){
        var agent = UrlHandler(500)
        var url = agent.getShortUrl()
        println(url)
        assert(url.contains(Regex("http:\\/\\/localhost:8080\\/fetch\\/..*")))

    }
}
