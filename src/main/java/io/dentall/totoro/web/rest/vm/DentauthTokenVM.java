package io.dentall.totoro.web.rest.vm;

public class DentauthTokenVM {

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "DentauthTokenVM{" +
            "token='" + token + '\'' +
            '}';
    }
}
