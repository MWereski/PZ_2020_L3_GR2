package task.models;

import user.models.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tasks")
public class Task {
    private long id;
    private String content;
    private Date dateCreated;
    private Date dateTackled;
    private Date dateFinished;
    private User doer;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(nullable = false)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateTackled() {
        return dateTackled;
    }

    public void setDateTackled(Date dateTackled) {
        this.dateTackled = dateTackled;
    }

    public Date getDateFinished() {
        return dateFinished;
    }

    public void setDateFinished(Date dateFinished) {
        this.dateFinished = dateFinished;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    public User getDoer() {
        return doer;
    }

    public void setDoer(User doer) {
        this.doer = doer;
    }

    @PrePersist
    void createdAt() {
        this.dateCreated = new Date();
    }
}
