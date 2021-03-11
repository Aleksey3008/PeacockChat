package android.example.peacockchat;

public class User {

    private String name;
    private String email;
    private String id;
    private int avatarMocUpResource;

    public User() {
    }

    public User(String name, String email, String id, int avatarMocUpResource) {
        this.name = name;
        this.email = email;
        this.id = id;
        this.avatarMocUpResource = avatarMocUpResource;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAvatarMocUpResource() {
        return avatarMocUpResource;
    }

    public void setAvatarMocUpResource(int avatarMocUpResource) {
        this.avatarMocUpResource = avatarMocUpResource;
    }
}
