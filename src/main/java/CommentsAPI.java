import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class CommentsAPI {
    private static final String POST_URL =
            "https://jsonplaceholder.typicode.com/users/1/posts";
    private static final String BASE_COMMENTS_URL =
            "https://jsonplaceholder.typicode.com/posts";
    private static final Gson GSON = new Gson();
    static void getComments() throws IOException {
        URL url = new URL(POST_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/json");
        int responseCode = connection.getResponseCode();
        System.out.println("GET posts response code: " + responseCode);
        String inputLine;
        StringBuilder response = new StringBuilder();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in =
                    new BufferedReader(
                            new InputStreamReader(connection.getInputStream()));
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            System.out.println(response.toString());
        } else {
            System.out.println("GET posts request not worked");
        }
        StringReader reader = new StringReader(response.toString());
        List<Post> post = GSON.fromJson(reader, new TypeToken<List<Post>>(){}.getType());
//        System.out.println(post);
        long lastPost = 0;
        for(Post current: post){
            if(current.getId() > lastPost){
                lastPost = current.getId();
            }
        }
        URL urlComments = new URL(BASE_COMMENTS_URL + "/" + lastPost + "/" + "comments");
        HttpURLConnection connectionComments = (HttpURLConnection) url.openConnection();
        connectionComments.setRequestMethod("GET");
        connectionComments.setRequestProperty("Content-Type", "application/json");
        int responseCodeComments = connectionComments.getResponseCode();
        System.out.println("GET posts response code: " + responseCodeComments);
        StringBuilder responseComments = new StringBuilder();
        if (responseCodeComments == HttpURLConnection.HTTP_OK) {
            BufferedReader inCom =
                    new BufferedReader(
                            new InputStreamReader(connectionComments.getInputStream()));
            while ((inputLine = inCom.readLine()) != null) {
                responseComments.append(inputLine);
            }
            inCom.close();
            System.out.println(responseComments.toString());
        } else {
            System.out.println("GET posts request not worked");
        }

    }

}
