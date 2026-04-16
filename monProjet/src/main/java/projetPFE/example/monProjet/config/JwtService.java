package projetPFE.example.monProjet.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import projetPFE.example.monProjet.service.Utilisateur;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    private static final String SECRET_KEY = "qjejafijaoiejfioefhiuefhheshuhuguyydrrdojfjgojrgepjgpjgjrzegjswrijgoierjhkjdjkhggyuuygyufdyytojtrytffeiha"; // Vous pouvez remplacer cette chaîne par n'importe quelle valeur de votre choix
    private static final SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    public String extractNomUtilisateur(String token) {
        return extractClaim(token,Claims::getSubject) ;
    }

    public <T> T extractClaim(String token, Function<Claims,T> claimsResolver )
    {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims) ;
    }

    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(),userDetails);
    }


     public String  generateToken(
             Map<String,Object> extraClaims,
             UserDetails userDetails
     ){
        return Jwts.builder()
                .claims(extraClaims)
                .subject(userDetails.getUsername())
              //  .subject(String.valueOf(Utilisateur.getRole))
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration( new Date(System.currentTimeMillis()+1000 *60 *24*10))
                .signWith(getSignInKey())
                //.signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
     }

     public boolean isTokenValid(String token ,UserDetails userDetails){
        final String username = extractNomUtilisateur(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
     }

    private boolean isTokenExpired(String token) {
        return  extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token,Claims::getExpiration);

    }


    private Claims extractAllClaims(String token){

    return Jwts
                .parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();   }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
