package com.manmeet.moudgill.NewTodoApplication.config.security;


import com.manmeet.moudgill.NewTodoApplication.exceptions.ApplicationException;
import com.manmeet.moudgill.NewTodoApplication.persistance.entities.User;
import com.manmeet.moudgill.NewTodoApplication.persistance.respositories.UserRepo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtHelper {


    private final UserRepo userRepo;


    @Autowired
    public JwtHelper(UserRepo userRepo){
            this.userRepo=userRepo;
    };


    public String extractUsername(String token) {

        return extractClaim(token, Claims::getSubject);
    }


    public Long extractUserId(String token){
        String username=this.extractUsername(token);
        User user=this.userRepo.findByUsername(username).orElseThrow(()->new ApplicationException("User not found!!"));
        return user.getUserId();
    }


    /**
     * @param token
     * @return
     * @Description This method is to get all the claims(data) present in the token
     */
    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
    }


    /**
     * @return
     * @Description This method is to get the decoded sign key
     */
    public Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode("MASD5541ASDASD87923235ASD5AS4D4ASD465ASD4ASD65SA4D65AS4D65ASD4SAD65AS4654");
        return Keys.hmacShaKeyFor(keyBytes);
    }


    /**
     * @param token
     * @param <T>
     * @Description This method is to get the single claim from the token
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }


    /**
     * @Description This method is used to generate a token with extra claims(data)
     * @param extraClaims
     * @param userDetails
     * @return
     */
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder().
                setClaims(extraClaims).
                setSubject(userDetails.getUsername()).
                setIssuedAt(new Date(System.currentTimeMillis())).
                setExpiration(new Date(System.currentTimeMillis() + 7200000L)).
                signWith(getSignInKey(), SignatureAlgorithm.HS256).compact();
    }


    /**
     * @Description This is method is used to generate the token without any extra claims(data)
     * @param userDetails
     * @return
     */
    public String generateToken(UserDetails userDetails) {
        return this.generateToken(new HashMap<>(),userDetails);
    }


    /**
     * @Description This method is used to check if the token is valid or not
     * @param token
     * @param userDetails
     * @return
     */
    public boolean isTokenValid(String token,UserDetails userDetails){
        final String username=extractUsername(token);
        return (username.equals(userDetails.getUsername()) || !isTokenExpired(token));

    }


    /**
     * @Description This method is used to check if the token is expired or not
     * @param token
     * @return
     */
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }


    /**
     * @Description This method is used to get the expiration date of the token
     * @param token
     * @return
     */
    public Date extractExpiration(String token) {
        return extractClaim(token,Claims::getExpiration);
    }


    /**
     * @Description This method is used to check if token belongs to that particular user who are trying to  access the resource
     * @param token
     * @param userId
     */
    public void verifyUser(String token,Long userId){

        //extract the username from the token on the basis of the token
        final String username=this.extractUsername(token);

        final User user=this.userRepo.findByUsername(username).orElseThrow(()-> new ApplicationException("User not found!!"));

        if(!user.getUserId().equals(userId)) {
            throw new ApplicationException("Unauthorized to do this operation!!");
        }
    }

}
