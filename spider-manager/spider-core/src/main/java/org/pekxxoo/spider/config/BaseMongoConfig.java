package org.pekxxoo.spider.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chong on 2017/8/9.
 */
public class BaseMongoConfig {
    private String hosts;
    private String username;
    private String password;
    private String database;

    public String getHosts() {
        return hosts;
    }

    public void setHosts(String hosts) {
        this.hosts = hosts;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongoDbFactory(), null);
    }

    private ServerAddress parseServerAddress(String host)
            throws UnknownHostException {
        ServerAddress addr = null;
        int mh = host.indexOf(':');
        if (mh < 0) {
            addr = new ServerAddress(host);
        } else {
            String serv = host.substring(0, mh).trim();
            int port = Integer.parseInt(host.substring(mh + 1).trim());
            addr = new ServerAddress(serv, port);
        }
        return addr;
    }

    public MongoClient mongo() throws Exception {
        // List<MongoCredential> authList = new ArrayList<>();
        // 【mongodb 3.x】
        // MongoCredential auth = null;
        // if(StringUtils.isNoneEmpty(username,password)) {
        //     auth = MongoCredential.createScramSha1Credential(
        //             username, database, password.toCharArray());
        // }
        // 【mongodb 2.x】
        //    MongoCredential auth = MongoCredential.createMongoCRCredential(
        //            username, database, password.toCharArray());
        String[] hostArray = hosts.split(",");
        // int countMongoServers = hostArray.length;
        // while (countMongoServers-- > 0) {
        //     authList.add(auth);
        // }
        MongoClient mongo = null;
        if (hostArray.length == 1) {
            ServerAddress addr = parseServerAddress(hosts);
            mongo = new MongoClient(addr);
        } else {
            List<ServerAddress> reps = new ArrayList<>();
            for (String host : hostArray) {
                ServerAddress addr = parseServerAddress(host);
                reps.add(addr);
            }
            mongo = new MongoClient(reps);
        }
        return mongo;
    }

    public MongoDbFactory mongoDbFactory() throws Exception {
        return new SimpleMongoDbFactory(mongo(), database);
    }
}
