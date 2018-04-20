package rodrigojosuetec.garage_finder;


public class UserSingleton {


    private int id;
    private String nombre;
    private String correo;
    private String password;
    private String telefono;
    private String auth_token;
    private boolean admin = false;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setEmail(String correo) {
        this.correo= correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setPass(String password) {
        this.password= password;
    }

    public String getPass() {
        return password;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getAuth_token() {
        return auth_token;
    }

    public void setAuth_token(String auth_token) {
        this.auth_token = auth_token;
    }


    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }



}
