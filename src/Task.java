import java.util.Date;
import java.util.UUID;

public class Task {
    private String id;
    private String description;
    private Status status;
    private Date createdAt;
    private Date updateAt;

    public Task(String description , Status status , Date createdAt, Date updateAt ) {
        this.id = UUID.randomUUID().toString();
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
        this.updateAt = updateAt;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }
}
