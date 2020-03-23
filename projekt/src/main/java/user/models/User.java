package user.models;

import task.models.Task;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    private long id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private Permission permission;
    private List<Task> tasks;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(unique = true, nullable = false)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(nullable = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(nullable = false)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(nullable = false)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @ManyToOne
    public Permission getPermissions() {
        return permission;
    }

    public void setPermissions(Permission permission) {
        this.permission = permission;
    }

    @OneToMany(fetch = FetchType.EAGER)
    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public String toString() {
        return firstName + ' ' + lastName;
    }
}