package com.mtech.ique.queueservice.service.impl;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import com.mtech.ique.queueservice.model.DirectNotification;
import com.mtech.ique.queueservice.model.entity.UserToken;
import com.mtech.ique.queueservice.service.NotificationService;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

@Service
public class NotificationServiceImpl implements NotificationService {

  public static final String APP_ID = "d4d2aaa1-6481-46fa-87c0-67c1800defef";

  @Override
  public String sendNotificationToTarget(DirectNotification notification) {

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
              + notification.getTarget()
              + "\"],"
              + "\"data\": {\"foo\": \"bar\"},"
              + "\"contents\": {\"en\": \""
              + notification.getMessage()
              + "\"}"
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
      return jsonResponse;

    } catch (Throwable t) {
      t.printStackTrace();
      return "ERROR";
    }
  }

  public String getTokenByUserId(Long userId) {
    Firestore dbFirestore = FirestoreClient.getFirestore();
    DocumentReference documentReference =
        dbFirestore.collection("userTokens").document(userId.toString());
    ApiFuture<DocumentSnapshot> future = documentReference.get();

    DocumentSnapshot document = null;
    try {
      document = future.get();
    } catch (Exception e) {
      System.out.println("ERROR: " + e.getMessage());
    }

    UserToken userToken = null;
    if (document.exists()) {
      userToken = document.toObject(UserToken.class);
      return userToken.getToken();
    } else {
      return null;
    }
  }

  @Override
  public void registerToken(Long userId, String token) {
    Firestore dbFirestore = FirestoreClient.getFirestore();
    UserToken userToken = UserToken.builder().token(token).userId(userId).build();
    dbFirestore.collection("userTokens").document(userId.toString()).set(userToken);
  }
}
