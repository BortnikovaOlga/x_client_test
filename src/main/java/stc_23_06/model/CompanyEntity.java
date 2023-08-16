package stc_23_06.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "company", schema = "public", catalog = "x_clients_db_r06g")
public class CompanyEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "is_active", nullable = false)
    private boolean isActive;
    @Basic
    @Column(name = "create_timestamp", nullable = false)
    private Timestamp createDateTime;
    @Basic
    @Column(name = "change_timestamp", nullable = false)
    private Timestamp lastChangedDateTime;
    @Basic
    @Column(name = "name", nullable = false, length = 100)
    private String name;
    @Basic
    @Column(name = "description", length = 300)
    private String description;
    @Basic
    @Column(name = "deleted_at")
    private Timestamp deletedAt;

//    @JsonIgnore
//    @OneToMany(mappedBy = "company")
//    private List<EmployeeEntity> employees;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Object getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(Timestamp createDateTime) {
        this.createDateTime = createDateTime;
    }

    public Object getLastChangedDateTime() {
        return lastChangedDateTime;
    }

    public void setLastChangedDateTime(Timestamp lastChangedDateTime) {
        this.lastChangedDateTime = lastChangedDateTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Timestamp deletedAt) {
        this.deletedAt = deletedAt;
    }

//    public List<EmployeeEntity> getEmployees() {
//        return employees;
//    }
//
//    public void setEmployees(List<EmployeeEntity> employees) {
//        this.employees = employees;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CompanyEntity company)) return false;
        return getId() == company.getId() && isActive() == company.isActive() && Objects.equals(getCreateDateTime(), company.getCreateDateTime()) && Objects.equals(getLastChangedDateTime(), company.getLastChangedDateTime()) && Objects.equals(getName(), company.getName()) && Objects.equals(getDescription(), company.getDescription()) && Objects.equals(getDeletedAt(), company.getDeletedAt());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), isActive(), getCreateDateTime(), getLastChangedDateTime(), getName(), getDescription(), getDeletedAt());
    }

    @Override
    public String toString() {
        return "CompanyEntity{" +
                "id=" + id +
                ", isActive=" + isActive +
                ", createDateTime=" + createDateTime +
                ", lastChangedDateTime=" + lastChangedDateTime +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", deletedAt=" + deletedAt +
//                ", employees.size=" + employees.size() +
                '}';
    }
}

