/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Servlet Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloWorld
*/

package com.bujok.sharelocation.backend;

import com.google.appengine.api.xmpp.Message;
import com.google.firebase.database.Transaction;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;

import java.io.IOException;

import javax.servlet.http.*;

public class MyServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        resp.setContentType("text/plain");
        resp.getWriter().println("Please use the form to POST to this url");
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String name = req.getParameter("name");
        resp.setContentType("text/plain");
        if (name == null) {
            resp.getWriter().println("Please enter a name");
        }
        resp.getWriter().println("Hello " + name);

        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost("https://fcm.googleapis.com/fcm/send");
        post.setHeader("Content-type", "application/json");
        post.setHeader("Authorization", "key=AIzaSyBWxOlG37hhBEhxAWLSF-Jx8z1Eusf70-Q");

        JSONObject message = new JSONObject();
        message.put("to", "dTxicpHHh6Y:APA91bHpPaFC_fKHqnWRWuiWJho2DxtqSvS5as5AvqFgvYZ743XEtdqLqg2SPpJe03zGhqIkTEO0M-MTL-Hr3WsaxcbmGgMStsmPBmCiNElFX2uVgkuCwwS0NG4TJlfZNT58mjyqXjkC");
        message.put("priority", "high");

        JSONObject notification = new JSONObject();
        notification.put("title", "Java");
        notification.put("body", "Notificação do Java");
        //two types of message
       // message.put("notification", notification);
        message.put("notification", notification);

        JSONObject data = new JSONObject();
        data.put("sampleAppSpecificData" , "Joe Test");
        message.put("data",data);

        post.setEntity(new StringEntity(message.toString(), "UTF-8"));
        HttpResponse response = client.execute(post);
        System.out.println(response);
        System.out.println(message);
    }
}
