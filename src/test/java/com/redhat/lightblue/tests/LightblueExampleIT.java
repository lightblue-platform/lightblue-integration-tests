package com.redhat.lightblue.tests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Example integration test for lightblue
 *
 * @author mpatercz
 *
 */
public class LightblueExampleIT {

    @BeforeClass
    public static void loadMetadata() throws UnsupportedCharsetException, IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String port = System.getProperty("jboss.port");
        System.out.println("Calling port "+port);
        HttpPut httpPut = new HttpPut("http://localhost:"+port+"/rest/metadata/country/0.1.0-SNAPSHOT");
        httpPut.setEntity(new StringEntity(loadResource("./country.json"), "UTF-8"));
        httpPut.setHeader("content-type", "application/json");
        CloseableHttpResponse response1 = httpclient.execute(httpPut);

        try {
            System.out.println("Setting up metadata "+response1.getStatusLine());
            Assert.assertEquals(200, response1.getStatusLine().getStatusCode());
            HttpEntity entity1 = response1.getEntity();
            System.out.println(EntityUtils.toString(entity1));
            EntityUtils.consume(entity1);
        } finally {
            response1.close();
        }
    }

    @Test
    public void confirmCountryMetadata() throws ClientProtocolException, IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String port = System.getProperty("jboss.port");
        System.out.println("Calling port "+port);
        HttpGet httpGet = new HttpGet("http://localhost:"+port+"/rest/metadata/country");
        httpGet.setHeader("content-type", "application/json");
        CloseableHttpResponse response1 = httpclient.execute(httpGet);
        try {
            System.out.println(response1.getStatusLine());
            HttpEntity entity1 = response1.getEntity();
            Assert.assertEquals("[{\"version\":\"0.1.0-SNAPSHOT\",\"changelog\":\"Initial version\",\"extendsVersions\":[],\"status\":\"active\",\"defaultVersion\":false}]", EntityUtils.toString(entity1));
            EntityUtils.consume(entity1);
        } finally {
            response1.close();
        }
    }

    /**
     * Load contents of resource on classpath as String.
     *
     * @param resourceName
     * @return the resource as a String
     * @throws IOException
     */
    public static final String loadResource(String resourceName) throws IOException {
        StringBuilder buff = new StringBuilder();

        try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourceName);
                InputStreamReader isr = new InputStreamReader(is, Charset.defaultCharset());
                BufferedReader reader = new BufferedReader(isr)) {
            String line;
            while ((line = reader.readLine()) != null) {
                buff.append(line).append("\n");
            }
        }

        return buff.toString();
    }

}
