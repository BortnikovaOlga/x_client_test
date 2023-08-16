package stc_23_06.model;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "employee", schema = "public", catalog = "x_clients_db_r06g")
public class EmployeeEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Column(name = "is_active", nullable = false)
    private boolean isActive;
    @Column(name = "create_timestamp", nullable = false)
    private Timestamp createDateTime;
    @Column(name = "change_timestamp", nullable = false)
    private Timestamp lastChangedDateTime;
    @Basic
    @Column(name = "first_name", nullable = false, length = 20)
    private String firstName;
    @Basic
    @Column(name = "last_name", nullable = false, length = 20)
    private String lastName;
    @Basic
    @Column(name = "middle_name", length = 20)
    private String middleName;
    @Basic
    @Column(name = "phone", nullable = false, length = 15)
    private String phone;
    @Basic
    @Column(name = "email", length = 256)
    private String email;
    @Basic
    @Column(name = "avatar_url", length = 1024)
    private String avatarUrl;

    @Basic
    @Column(name = "company_id", nullable = false)
    private int companyId;

    public EmployeeEntity() {
    }

    public EmployeeEntity(EmployeeDto emp) {
        firstName = emp.getFirstName();
        lastName = emp.getLastName();
        middleName = emp.getMiddleName();
        email = emp.getEmail();
        avatarUrl = emp.getAvatarUrl();
        phone = emp.getPhone();
        companyId = emp.getCompanyId();
        isActive = emp.isActive();
    }

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

    public Timestamp getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(Timestamp createDateTime) {
        this.createDateTime = createDateTime;
    }

    public Timestamp getLastChangedDateTime() {
        return lastChangedDateTime;
    }

    public void setLastChangedDateTime(Timestamp lastChangedDateTime) {
        this.lastChangedDateTime = lastChangedDateTime;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeEntity that = (EmployeeEntity) o;
        return id == that.id && isActive == that.isActive && companyId == that.companyId && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(middleName, that.middleName) && Objects.equals(phone, that.phone) && Objects.equals(email, that.email) && Objects.equals(avatarUrl, that.avatarUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, isActive, firstName, lastName, middleName, phone, email, avatarUrl, companyId);
    }

    public boolean isEqualDto(EmployeeDto emp) {
        return id == emp.getId()
                && isActive == emp.isActive()
                && companyId == emp.getCompanyId()
                && Objects.equals(firstName, emp.getFirstName())
                && Objects.equals(lastName, emp.getLastName())
                && Objects.equals(middleName, emp.getMiddleName())
                && Objects.equals(phone, emp.getPhone())
                && Objects.equals(email, emp.getEmail())
                && Objects.equals(avatarUrl, emp.getAvatarUrl());
    }

    @Override
    public String toString() {
        return "EmployeeEntity{" +
                "id=" + id +
                ", isActive=" + isActive +
                ", createDateTime=" + createDateTime +
                ", lastChangedDateTime=" + lastChangedDateTime +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", companyId=" + companyId +
                '}';
    }
}
