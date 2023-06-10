import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Send {
    private static Socket socket;
    private static ObjectOutputStream outputStream;
    private static ObjectInputStream inputStream;

    public Send(Socket socket1) throws IOException {
        socket = socket1;
        outputStream = new ObjectOutputStream(socket.getOutputStream());
        inputStream = new ObjectInputStream(socket.getInputStream());
    }

    public Send() {
    }

    public void sendRequest(JSONObject jsonObject) throws IOException {
        outputStream.writeObject(jsonObject);
        outputStream.flush();
    }
    public JSONObject sendRequestgetObject(JSONObject jsonObject) throws IOException, ClassNotFoundException {
        outputStream.writeObject(jsonObject);
        outputStream.flush();
        return (JSONObject) inputStream.readObject();
    }
    public JSONArray sendRequestgetArray(JSONObject jsonObject) throws IOException, ClassNotFoundException {
        outputStream.writeObject(jsonObject);
        outputStream.flush();
        return (JSONArray) inputStream.readObject();
    }
}
