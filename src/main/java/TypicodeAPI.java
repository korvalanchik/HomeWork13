import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TypicodeAPI {
    private static final String BASE_POST_URL =
            "https://jsonplaceholder.typicode.com/users";
    private static final String BASE_COMMENTS_URL =
            "https://jsonplaceholder.typicode.com/posts";
    private static final String BASE_TODO_URL =
            "https://jsonplaceholder.typicode.com/users";
    private static final Gson GSON = new Gson();
    static void getComments(int numUser) throws IOException {
        URL url = new URL(BASE_POST_URL + "/" + numUser + "/posts");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/json");
        int responseCode = connection.getResponseCode();
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
        } else {
            System.out.println("GET posts request not worked");
        }
        StringReader reader = new StringReader(String.valueOf(response));
        List<Post> posts = GSON.fromJson(reader, new TypeToken<List<Post>>(){}.getType());
        long lastPost = 0;
        for(Post current: posts){
            if(current.getId() > lastPost){
                lastPost = current.getId();
            }
        }
        URL urlComments = new URL(BASE_COMMENTS_URL + "/" + lastPost + "/" + "comments");
        HttpURLConnection connectionComments = (HttpURLConnection) urlComments.openConnection();
        connectionComments.setRequestMethod("GET");
        connectionComments.setRequestProperty("Content-Type", "application/json");
        int responseCodeComments = connectionComments.getResponseCode();
        System.out.println("GET last comments for user " + numUser);
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

        StringReader readerComments = new StringReader(String.valueOf(responseComments));
        List<Comment> comments = GSON.fromJson(readerComments, new TypeToken<List<Comment>>(){}.getType());

        String userDirectory = new File("").getAbsolutePath();
        File file = new File(userDirectory +"\\src\\main\\resources\\user-" + numUser + "-post-" + lastPost + "-comments.json");
        file.createNewFile();

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        String jsonComment = mapper.writeValueAsString(comments);

        try (FileWriter writer = new FileWriter(file)) {
            writer.write(jsonComment);
            writer.flush();
            System.out.println("and write it to file");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }
    public static void getToDo(int numUser) throws IOException {
        URL url = new URL(BASE_TODO_URL + "/" + numUser + "/todos");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/json");
        int responseCode = connection.getResponseCode();
//        System.out.println("GET posts response code: " + responseCode);
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
            System.out.println("Get todos user " + numUser + " with 'complited'=false");
        } else {
            System.out.println("GET posts request not worked");
        }
        StringReader reader = new StringReader(String.valueOf(response));
        ArrayList<Todo> todos = GSON.fromJson(reader, new TypeToken<ArrayList<Todo>>(){}.getType());
        todos.removeIf(Todo::getCompleted);

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        String jsonTodo = mapper.writeValueAsString(todos);
        System.out.println(jsonTodo);
    }

}
