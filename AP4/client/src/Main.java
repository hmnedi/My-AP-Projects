import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {

        CardLaout frame = new CardLaout();
        frame.setSize(855, 447);
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);


    }
}