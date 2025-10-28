import java.util.Date;
import java.util.UUID;

public class Task {
    private String id;
    private String description;
    private String status;
    private Date createdAt;
    private Date updateAt;

    // Default constructor for Jackson
    public Task() {
    }

    public Task(String description , String status , Date createdAt, Date updateAt ) {
        this.id = UUID.randomUUID().toString();
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
        this.updateAt = updateAt;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getStatus() {
        return status;
    }
    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }
    public Date getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public Date getUpdateAt() {
        return updateAt;
    }
    public void printTask () {
        System.out.println("Task id : " + id + " description : " + description + " status : " + status +
                " createdAt : " + createdAt );
    }
}
