package com.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 *
 * 简单示例：使用 Apache HttpClient 调用 GitHub API 获取用户信息，
 * 并用 Jackson 解析返回的 JSON。
 */
public class GithubApiClient {

    public static void main(String[] args) {
        // 要查询的用户名
        String username = "octocat";
        String url = "https://api.github.com/users/" + username;

        // 1. 创建 HttpClient
        HttpClient httpClient = HttpClientBuilder.create().build();

        // 2. 创建 GET 请求
        HttpGet request = new HttpGet(url);
        request.setHeader("Accept", "application/json");

        try {
            // 3. 执行请求
            HttpResponse response = httpClient.execute(request);
            String json = EntityUtils.toString(response.getEntity());

            // 4. 使用 Jackson 解析 JSON
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(json);

            // 5. 提取并打印信息
            String name = root.path("name").asText();
            String login = root.path("login").asText();
            String location = root.path("location").asText();
            int publicRepos = root.path("public_repos").asInt();

            System.out.println("用户名: " + login);
            System.out.println("姓名: " + name);
            System.out.println("所在城市: " + location);
            System.out.println("公开仓库数: " + publicRepos);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}