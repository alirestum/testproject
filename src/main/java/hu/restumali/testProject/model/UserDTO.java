package hu.restumali.testProject.model;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class UserDTO {

    @NotEmpty(message = "Username is required!")
    private String username;

    @NotEmpty(message = "Password is required")
    @Size(min = 5, message = "Password must be 5 or more characters long!")
    private String password;

    @NotEmpty(message = "Password confirmation is required!")
    private String passwordConfirm;

    public UserDTO() {}

    public UserDTO(String username, String password){
        this.username = username;
        this.password = password;
    }
}
