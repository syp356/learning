package com.syp.user.service.provider.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Map;

/**
 * @Author: SYP
 * @Date: 2020/10/9
 * @Description:
 */
@Slf4j
public class JwtGeneratorUtil {
    private static String SECRET_KEY="syp";

    public static String generatorToken(Map<String, Object> payload){
        ObjectMapper objectMapper = new ObjectMapper();
        String token = null;
        try{
            token = Jwts.builder().setPayload(objectMapper.writeValueAsString(payload))
                .signWith(SignatureAlgorithm.HS256, getKeyInstance())
                .compact();
        }catch (JsonProcessingException e){
            log.error("generatorToken: "+ e);
        }
        return token;
    }

    private static Key getKeyInstance() {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] keySecretByte = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Key key = new SecretKeySpec(keySecretByte,signatureAlgorithm.getJcaName());
        return key;
    }

    public static Claims parseToken(String token){
        Jws<Claims> claimsJwt=Jwts.parser().setSigningKey(getKeyInstance()).parseClaimsJws(token);
        return claimsJwt.getBody();
    }

}
