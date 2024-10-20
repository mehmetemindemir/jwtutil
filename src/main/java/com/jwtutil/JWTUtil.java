package com.jwtutil;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;


public class JWTUtil {
	private static String issuer = "mehmetemindemir@outlook.com";
    private int expireTime = 15*60*1000; //Min
    private String environment = "prod";
    Date exp=null;
    private static final String secret = "A34sdsd3Ghjhgj34543SdfsdfsdfsdfsdfsdfyopR445";

    public JWTUtil(Builder builder){
        issuer              = builder.issuer;
        this.environment    = builder.environment;
        this.expireTime     = builder.expireTime;
    }
    public static Builder create(){
        return new Builder();
    }
    public static class Builder {

        private String issuer = "mehmetemindemir@outlook.com";
        private int expireTime = 15; //min
        private String environment = "prod";

        public JWTUtil build() {
            return new JWTUtil(this);
        }

        public Builder setIssuer(String issuer) {
            this.issuer = issuer;
            return this;
        }

        public Builder setExpireTime(int expireTime) {
            this.expireTime = expireTime;
            return this;
        }

        public Builder setEnvironment(String env){
            this.environment = env;
            return this;
        }



    }

    public   Date dateAddMinute(Date date, int minute) {
        Date newdate = date;
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.MINUTE, minute);
            newdate = cal.getTime();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return newdate;
    }

    public int getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(int expireTime) {
        this.expireTime = expireTime;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public String generateTokenWithJWT(Map<String, String> fields) throws UnsupportedEncodingException {
        String jwt = "";
        exp =  dateAddMinute(new Date() ,expireTime);
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTCreator.Builder jwtBuilder = JWT.create().withIssuer(issuer)
                .withExpiresAt(exp)
                .withIssuedAt(new Date());
        for (Map.Entry<String, String> entry : fields.entrySet()) {
            jwtBuilder.withClaim(entry.getKey(), entry.getValue());
        }

        jwtBuilder.withClaim("env", environment);

        jwt = jwtBuilder.sign(algorithm);

        return jwt;
    }

    public static boolean verifyToken(String token) {
        boolean isValid = false;
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm).withIssuer(issuer).build();
            DecodedJWT jwt = verifier.verify(token);
            isValid = (jwt != null);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return isValid;
    }

    public static String getDataFromKey(String token, String key) {
        return getDataFromKey(token, key, false);
    }
    public static String getDataFromKey(String token, String key,boolean checkJsonChar) {
        String data = "";
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm).withIssuer(issuer).build();
            DecodedJWT jwt = verifier.verify(token);

            data = jwt.getClaim(key).asString();
            if(checkJsonChar) {
            	data = data.replace("\"{", "{");
            	data = data.replace("}\"", "}");
            	data = data.replace("\"\"[", "[");
            	data = data.replace("]\"\"", "]");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return data;
    }
}
