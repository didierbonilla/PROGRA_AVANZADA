package programacion.avanzada.programacion_avanzada_project;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private static final String FILE_PATH = "src/main/resources/users.json";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static List<UserModel> readUsers() {
        try {
            File file = new File(FILE_PATH);
            if (!file.exists()) return new ArrayList<>();
            return objectMapper.readValue(file, new TypeReference<List<UserModel>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private static void saveUsers(List<UserModel> users) {
        try {
            objectMapper.writeValue(new File(FILE_PATH), users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean createUser(UserModel user) {
        List<UserModel> users = readUsers();

        // Verificar si el usuario ya existe
        if (users.stream().anyMatch(u -> u.getUsuario().equals(user.getUsuario()))) {
            return false;
        }

        users.add(user);
        saveUsers(users);
        return true;
    }

    public static UserModel getUserByUsername(String usuario) {
        return readUsers().stream().filter(user -> user.getUsuario().equals(usuario)).findFirst().orElse(null);
    }

    public static boolean updateUser(String usuario, UserModel data) {
        List<UserModel> users = readUsers();

        for (UserModel user : users) {
            if (user.getUsuario().equals(usuario)) {
                user.setNombre(data.getNombre());
                user.setPassword(data.getPassword());
                user.setRol(data.getRol());
                user.setEmail(data.getEmail());
                user.setTelefono(data.getTelefono());
                saveUsers(users);
                return true;
            }
        }
        return false;
    }

    public static boolean deleteUser(String usuario) {
        List<UserModel> users = readUsers();
        boolean removed = users.removeIf(user -> user.getUsuario().equals(usuario));
        if (removed) saveUsers(users);
        return removed;
    }

    public static UserModel findUserByUsername(String usuario) {
        List<UserModel> users = readUsers();
        for (UserModel user : users) {
            if (user.getUsuario().equalsIgnoreCase(usuario)) {
                return user;
            }
        }
        return null;
    }
}

