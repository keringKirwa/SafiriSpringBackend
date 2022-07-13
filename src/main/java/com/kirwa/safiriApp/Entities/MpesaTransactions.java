package com.kirwa.safiriApp.Entities;

import okhttp3.*;
import org.json.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import io.github.cdimascio.dotenv.Dotenv;

public class MpesaTransactions {
    private final String appKey;
    private final String appSecret;
    public MpesaTransactions(){

        Dotenv dotenv = null;
        dotenv = Dotenv.configure().load();
        this.appKey=dotenv.get("MPESA_CONSUMER_KEY");
        this.appSecret=dotenv.get("MPESA_CONSUMER_SECRET");
    }

    /*FIXME: {1} the  auth method below is working good . the result
       is a JSON  hashMap.The assert method will throw an error if there is an error if the body is
       null .
            {2} Calling response.body().string() consumes the body - therefore, you can't call it the second time.
       */
    public String authenticate() throws IOException {
        String appKeySecret = appKey + ":" + appSecret;
        byte[] bytes = appKeySecret.getBytes(StandardCharsets.ISO_8859_1);
        String encoded = Base64.getEncoder().encodeToString(bytes);

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://sandbox.safaricom.co.ke/oauth/v1/generate?grant_type=client_credentials")
                .get()
                .addHeader("authorization", "Basic "+encoded)
                .addHeader("cache-control", "no-cache")
                .build();

        Response response = client.newCall(request).execute();

        System.out.println("This is the Auth response from Dara-ja Api : " +
                response.peekBody(99999L).string());

        assert response.body() != null;
        JSONObject jsonObject=new JSONObject(response.body().string());

        /*TODO: JSONObject is actually a special Kind of an Hash Map.the token below will be added to he header.*/

        return jsonObject.getString("access_token");

    }

    public String C2BSimulation(
            String shortCode, String commandID, int amount,long MSISDN,String billRef)
            throws IOException {

        JSONArray jsonArray=new JSONArray();
        JSONObject jsonObject=new JSONObject();

        jsonObject.put("ShortCode", shortCode);
        jsonObject.put("CommandID", commandID);
        jsonObject.put("Amount", amount);
        jsonObject.put("Msisdn", MSISDN);
        jsonObject.put("BillRefNumber",billRef);


        jsonArray.put(jsonObject);

        String requestJson=jsonArray.toString().replaceAll("[\\[\\]]","");

        System.out.println("This is the requestJsonObject(JSON) from Kelvin : " +requestJson);

        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, requestJson);

        Request request = new Request.Builder()
                .url("https://sandbox.safaricom.co.ke/mpesa/c2b/v1/simulate")
                .post(body)
                .addHeader("content-type", "application/json")
                .addHeader("authorization", "Bearer "+authenticate())
                .addHeader("cache-control", "no-cache")
                .build();

        Response response = client.newCall(request).execute();
        assert response.body() != null;
        System.out.println("response to Kelvin in C2B simulation = " + response.peekBody(99999L).string());

        return response.peekBody(999999L).string();
    }

    public String STKPushSimulation(String businessShortCode, String password,
                                    String timestamp,String transactionType, String amount,
                                    String phoneNumber, String partyA, String partyB,
                                    String callBackURL, String queueTimeOutURL,String accountReference,
                                    String transactionDesc) throws IOException {

        JSONArray jsonArray=new JSONArray();
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("BusinessShortCode", businessShortCode);
        jsonObject.put("Password", password);
        jsonObject.put("Timestamp", timestamp);
        jsonObject.put("TransactionType", transactionType);
        jsonObject.put("Amount",amount);
        jsonObject.put("PhoneNumber", phoneNumber);
        jsonObject.put("PartyA", partyA);
        jsonObject.put("PartyB", partyB);
        jsonObject.put("CallBackURL", callBackURL);
        jsonObject.put("AccountReference", accountReference);
        jsonObject.put("QueueTimeOutURL", queueTimeOutURL);
        jsonObject.put("TransactionDesc", transactionDesc);



        jsonArray.put(jsonObject);

        String requestJson=jsonArray.toString().replaceAll("[\\[\\]]","");

        OkHttpClient client = new OkHttpClient();
        String url="https://sandbox.safaricom.co.ke/mpesa/stkpush/v1/processrequest";
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, requestJson);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("content-type", "application/json")
                .addHeader("authorization", "Bearer "+authenticate())
                .addHeader("cache-control", "no-cache")
                .build();


        Response response = client.newCall(request).execute();
        assert response.body() != null;
        System.out.println(response.body().string());
        return response.body().toString();
    }
}
