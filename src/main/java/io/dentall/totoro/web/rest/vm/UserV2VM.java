package io.dentall.totoro.web.rest.vm;

import io.dentall.totoro.config.Constants;
import io.dentall.totoro.service.dto.UserDTO;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.Set;

/**
 * View Model to represent the new User structure.
 */
public class UserV2VM  {

    private Long id;

    @NotBlank
    @Pattern(regexp = Constants.LOGIN_REGEX)
    @Size(min = 1, max = 50)
    private String login;

    @Size(max = 50)
    private String firstName;

    @Size(max = 50)
    private String lastName;

    @Email
    @Size(min = 5, max = 254)
    private String email;

    @Size(max = 256)
    private String imageUrl;

    private Boolean activated;

    private String createdBy;

    private Instant createdDate;

    private String lastModifiedBy;

    private Instant lastModifiedDate;

    private Set<String> authorities;

    private Boolean firstLogin;

    private String nationalId;

    public UserV2VM() {
        // Empty constructor needed for Jackson.
    }

    public UserV2VM(UserDTO userDto) {
        this.id = userDto.getId();
        this.login = userDto.getLogin();
        this.firstName = userDto.getFirstName();
        this.lastName = userDto.getLastName();
        this.email = userDto.getEmail();
        this.imageUrl = userDto.getImageUrl();
        this.activated = userDto.isActivated();
        this.createdBy = userDto.getCreatedBy();
        this.createdDate = userDto.getCreatedDate();
        this.lastModifiedBy = userDto.getLastModifiedBy();
        this.lastModifiedDate = userDto.getLastModifiedDate();
        this.authorities = userDto.getAuthorities();
        if (userDto.getExtendUser() != null) {
            this.firstLogin = userDto.getExtendUser().isFirstLogin();
            this.nationalId = userDto.getExtendUser().getNationalId();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public Boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public Set<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<String> authorities) {
        this.authorities = authorities;
    }

    public Boolean isFirstLogin() {
        return firstLogin;
    }

    public String getNationalId() {
        return nationalId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserV2VM user = (UserV2VM) o;
        return !(user.getId() == null || getId() == null) && getId().equals(user.getId());
    }
}
