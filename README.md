This repository is a test of creating shortUrl service which supports up to 10K request a second with 8 chars long string.
 
By using 73 hashing characters - "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789?#@!$&()*+=" 

we get 73^8 = ~806 trillion optional indexes, its 100K * 8 Billion.... its a lot

The total expected requests in a year is : 10000 (a second) * 606024*365 = ~315 billion requests So we are safe for a long time.


The scale and concurrency is supported by A few ENVIRONMENT VARIABLE (set in the code as variable for convenient)
* INDEX_RANGE = 8,000,000,000
* MAX_AGENTS_ID = 10,000
* MIN_AGENT_ID = 0

this is a setup of 10K handlers, each handler holds a range of ~8 Billion indexes 

It allows us to run with 10 servers with 10K agents, 20 servers with 5K agents, any combination up to 100K agents


### Agent initiation: 
Once the system starts, each agent gets it current index by querying db for the max index used for the relevant agent. 
If first time the agent starts from agentId * 8 Billion +1 => agent 2 will get 16,000,000,000,001

### The App exposes two rest services.
http://localhost:8080/getShortUrl
* generates index by one of the agents, saves a record in DB with expiration and

http://localhost:8080/fetch/{key}
* tarnslate the long url by fetching record from db by the key.
* Increase a counter on the record to mark a read operation.
