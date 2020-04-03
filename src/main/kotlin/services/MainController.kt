package services

import bl.UrlHandler
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.view.RedirectView
import java.util.*
import kotlin.collections.HashMap



@RestController
class MainController {
    /* + Those two ENV VARIABLE sets the number of handlers (agents) that will be in the pool to answer requests.
       + For load support and scale, we want to split to many agents to allow
       + concurrency in each server and ability to run many servers.
       + Each handler gets a agentId, each agent gets range of indexes 73^8 / 100,000 agents = ~8 billion values.
       + (73 encryption key chats and 8 characters long for short link phrase).
       + the total expected request in a year is : 10000 (a second) * 60*60*24*365 = ~315 billion requests.
       + so ~40 agents will cover a year, since we have 100K agents - we are safe for many years
       + we will set pool of 10K - it will leave the option to grow to 10 more servers
       + or 20 servers with pool of 5000 agents. */

    // ***should be ENV VARIABLE****
    val MAX_AGENTS_ID = 10000
    val MIN_AGENT_ID = 0

    private var agentsMap = HashMap<Int,UrlHandler>()

    // creating the agents pool
    init {
        println ("Running with ${MAX_AGENTS_ID - MIN_AGENT_ID} agents")
        for (i in MIN_AGENT_ID..MAX_AGENTS_ID){
            agentsMap.put(i,UrlHandler(i))
        }
    }


    @RequestMapping("/getShortUrl")
    fun getShortUrl() : String? {
        // get random agent
        var selector = Random().nextInt(MAX_AGENTS_ID - MIN_AGENT_ID)+MIN_AGENT_ID
        return agentsMap[selector]?.getShortUrl()
    }


    @RequestMapping("/fetch/{shortString}")
    @GetMapping("/redirectWithRedirectView")
    fun fetch(@PathVariable("shortString") shortString: String) : RedirectView {
        var selector = Random().nextInt(MAX_AGENTS_ID - MIN_AGENT_ID)+MIN_AGENT_ID
        var longUrl = (agentsMap[selector])?.fetch(shortString).toString()
        return  RedirectView(longUrl);
    }
}
