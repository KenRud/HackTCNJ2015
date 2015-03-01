package edu.tcnj.hacktcnj2015;

import android.os.AsyncTask;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AsyncUrlCall extends AsyncTask<String, Void, JSONObject> {

    private static final String SERVER_URL = "http://45.56.96.115:6969";

    private TaskListener listener;

    public AsyncUrlCall(TaskListener listener) {
        this.listener = listener;
    }

    @Override
    protected JSONObject doInBackground(String... params) {
        URL url = null;
        try {
            url = new URL(SERVER_URL + params[0]);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            List<NameValuePair> payload = new ArrayList<NameValuePair>();
            for (int i=1; i<params.length-1; i+=2) {
                payload.add(new BasicNameValuePair(params[i], params[i+1]));
            }
            OutputStream os = urlConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(getQuery(payload));
            writer.flush();
            writer.close();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        InputStream in = null;
        StringBuilder sb = new StringBuilder();
        try {
            in = new BufferedInputStream(urlConnection.getInputStream());
            Scanner scan = new Scanner(in);
            while (scan.hasNextLine()) {
                sb.append(scan.nextLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }

        String str = sb.toString();
        JSONObject result = null;

        try {
            result = new JSONObject(str);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }


    @Override
    protected void onPostExecute(JSONObject result) {
        super.onPostExecute(result);
        listener.onFinished(result);
    }

    private String getQuery(List<NameValuePair> params) throws UnsupportedEncodingException
    {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (NameValuePair pair : params)
        {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
        }

        return result.toString();
    }

    public interface TaskListener {
        public void onFinished(JSONObject result);
    }
}