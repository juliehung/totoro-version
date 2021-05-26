package io.dentall.totoro.dentauth;

import io.jsonwebtoken.*;

import java.security.Key;

import static io.dentall.totoro.dentauth.DentauthConstraint.DENTAUTH;

public class TokenWrapper {

    private String token;

    private boolean valid;

    private Jws<Claims> jws;

    private TokenWrapper(String token) {
        this.token = token;
    }

    public boolean isDentauth() {
        return jws != null && DENTAUTH.equals(jws.getBody().getSubject());
    }

    public String getIssuer() {
        return jws != null ? jws.getBody().getIssuer() : null;
    }

    public static TokenWrapper validateToInstance(Key key, String token) {
        TokenWrapper tokenWrapper = new TokenWrapper(token);
        try {
            tokenWrapper.jws = Jwts.parser().setSigningKey(key).parseClaimsJws(token);
            tokenWrapper.valid = true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            tokenWrapper.valid = false;
        } catch (ExpiredJwtException e) {
            tokenWrapper.valid = false;
        } catch (UnsupportedJwtException e) {
            tokenWrapper.valid = false;
        } catch (IllegalArgumentException e) {
            tokenWrapper.valid = false;
        }

        return tokenWrapper;
    }
}
