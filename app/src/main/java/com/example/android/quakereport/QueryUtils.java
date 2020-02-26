package com.example.android.quakereport;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
public final class QueryUtils {


    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */

    private static final String URL_USGS = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";

    /**
     * Return a list of {@link Earthquake} objects that has been built up from
     * parsing a JSON response.
     */
    public static ArrayList<Earthquake> extractEarthquakes(String requestUrl) {

        // Create an empty ArrayList that we can start adding earthquakes to
        ArrayList<Earthquake> earthquakes;

        // Create URL object
        URL url = createUrl(requestUrl);


        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = "";
        jsonResponse = makeHttpRequest(url);

        // Extract relevant fields from the JSON response and create an {@link Event} object
        earthquakes = extractFeatureFromJson(jsonResponse);

        // Return the {@link Event}
        return earthquakes;
    }


    private static ArrayList<Earthquake> extractFeatureFromJson(String jsonResponse) {

        ArrayList<Earthquake> earthquakes = new ArrayList<Earthquake>();

        try {

            // build up a list of Earthquake objects with the corresponding data.

            JSONObject earthquake = new JSONObject(jsonResponse);

            JSONArray feat = earthquake.optJSONArray("features");

            for (int i = 0; i < feat.length(); i++) {

                JSONObject obj1 = feat.getJSONObject(i);

                JSONObject prop = obj1.getJSONObject("properties");

                Earthquake eq_obj = new Earthquake(prop.getDouble("mag"), prop.getString("place"), prop.getLong("time"), prop.getString("url"));

                earthquakes.add(eq_obj);


            }


        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes
        return earthquakes;
    }


    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException exception) {
            Log.e("Error", "Error with creating URL", exception);
            return null;
        }
        return url;
    }

    private static String makeHttpRequest(URL url) {

        String jsonResponse = "";
        HttpsURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(1000);
            urlConnection.setReadTimeout(1000);
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }


        } catch (IOException e) {
        } finally {
            if (urlConnection == null) {
                urlConnection.disconnect();
            }
            if (inputStream == null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return jsonResponse;

        }


    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }
}

