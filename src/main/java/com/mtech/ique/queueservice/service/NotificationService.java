package com.mtech.ique.queueservice.service;

import com.google.api.client.json.Json;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

@Service
public class NotificationService {

  private static final String APP_ID = "d4d2aaa1-6481-46fa-87c0-67c1800defef";

  public void sendPush(String playerId) {

    try {
      String jsonResponse;

      URL url = new URL("https://onesignal.com/api/v1/notifications");
      HttpURLConnection con = (HttpURLConnection) url.openConnection();
      con.setUseCaches(false);
      con.setDoOutput(true);
      con.setDoInput(true);

      con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
      con.setRequestMethod("POST");

      String strJsonBody =
          "{"
              + "\"app_id\": \""
              + APP_ID
              + "\","
              + "\"include_player_ids\": [\""
              + playerId
              + "\"],"
              + "\"data\": {\"foo\": \"bar\"},"
              + "\"contents\": {\"en\": \"English Message\"}"
              + "}";

      System.out.println("strJsonBody:\n" + strJsonBody);

      byte[] sendBytes = strJsonBody.getBytes("UTF-8");
      con.setFixedLengthStreamingMode(sendBytes.length);

      OutputStream outputStream = con.getOutputStream();
      outputStream.write(sendBytes);

      int httpResponse = con.getResponseCode();
      System.out.println("httpResponse: " + httpResponse);

      if (httpResponse >= HttpURLConnection.HTTP_OK
          && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
        Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
        jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
        scanner.close();
      } else {
        Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
        jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
        scanner.close();
      }
      System.out.println("jsonResponse:\n" + jsonResponse);

    } catch (Throwable t) {
      t.printStackTrace();
    }
  }
}
