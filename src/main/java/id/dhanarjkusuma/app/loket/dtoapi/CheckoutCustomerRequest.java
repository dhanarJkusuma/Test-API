package id.dhanarjkusuma.app.loket.dtoapi;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers;
import id.dhanarjkusuma.app.loket.domain.Gender;
import id.dhanarjkusuma.app.loket.helper.ValidateEnum;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class CheckoutCustomerRequest {

    @NotNull(message = "Customer firstName cannot be null.")
    private String firstName;

    @NotNull(message = "Customer lastName cannot be null.")
    private String lastName;

    @NotNull(message = "Customer email cannot be null.")
    private String email;

    @NotNull(message = "Customer phone cannot be null.")
    private String phone;

    @NotNull(message = "Customer dateOfBirth cannot be null.")
    @JsonDeserialize(using = DateDeserializers.DateDeserializer.class)
    private Date dateOfBirth;

    @NotNull(message = "Customer gender cannot be null.")
    @ValidateEnum(enumClass = Gender.class)
    private String gender;

    public CheckoutCustomerRequest() {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
