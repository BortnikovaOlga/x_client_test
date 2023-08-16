package stc_23_06.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EmployeeDto {
    private int id;
    @JsonProperty("isActive")
    private boolean isActive;
    //    createDateTime": "2023-08-11T15:32:45.575Z",
//    "lastChangedDateTime": "2023-08-11T15:32:45.575Z",
    private String firstName;
    private String lastName;
    private String middleName;
    private String phone;
    private String email;
    //  "birthdate": "2023-08-11",
    @JsonProperty("avatar_url")
    private String avatarUrl;
    private int companyId;

    public EmployeeDto() {
    }

    public EmployeeDto(boolean isActive, String firstName, String lastName, String middleName, String phone, String email, String avatarUrl, int companyId) {
        this.isActive = isActive;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.phone = phone;
        this.email = email;
        this.avatarUrl = avatarUrl;
        this.companyId = companyId;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", isActive=" + isActive +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", companyId=" + companyId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmployeeDto that)) return false;
        return getId() == that.getId() && isActive() == that.isActive() && getCompanyId() == that.getCompanyId() && Objects.equals(getFirstName(), that.getFirstName()) && Objects.equals(getLastName(), that.getLastName()) && Objects.equals(getMiddleName(), that.getMiddleName()) && Objects.equals(getPhone(), that.getPhone()) && Objects.equals(getEmail(), that.getEmail()) && Objects.equals(getAvatarUrl(), that.getAvatarUrl());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), isActive(), getFirstName(), getLastName(), getMiddleName(), getPhone(), getEmail(), getAvatarUrl(), getCompanyId());
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
}
