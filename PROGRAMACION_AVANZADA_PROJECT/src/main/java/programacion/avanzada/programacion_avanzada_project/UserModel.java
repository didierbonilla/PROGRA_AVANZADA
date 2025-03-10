package programacion.avanzada.programacion_avanzada_project;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserModel {
    private String usuario;
    private String nombre;
    private String rol;
    private String email;
    private String telefono;
    private String password;

    public UserModel() {}

    public UserModel(String usuario, String nombre, String rol, String email, String telefono, String password) {
        this.usuario = usuario;
        this.nombre = nombre;
        this.rol = rol;
        this.email = email;
        this.telefono = telefono;
        this.password = password;
    }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    @Override
    public String toString() {
        return "UserModel{" +
                "usuario='" + usuario + '\'' +
                ", nombre='" + nombre + '\'' +
                ", rol='" + rol + '\'' +
                ", email='" + email + '\'' +
                ", telefono='" + telefono + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
