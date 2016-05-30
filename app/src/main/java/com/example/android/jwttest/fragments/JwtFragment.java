package com.example.android.jwttest.fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.jwttest.R;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
//import javax.xml.bind.DatatypeConverter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.IncorrectClaimException;
import io.jsonwebtoken.InvalidClaimException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MissingClaimException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.impl.crypto.MacProvider;

public class JwtFragment extends Fragment {

    private final String LOG_TAG_NAME = "FRAGMENT JWT";
    private Button generateJwtGitHub, generateJwtStormPath;
    private TextView result;

    public JwtFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_jwt,container,false);
        generateJwtGitHub = (Button) view.findViewById(R.id.button_generate_jwt_github);
        generateJwtStormPath =(Button) view.findViewById(R.id.button_generate_jwt_stormpath);
        result = (TextView) view.findViewById(R.id.text_response);
        view.findViewById(R.id.text_clear_response).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result.setText(R.string.response);
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        generateJwtGitHub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateJwtViaGithubCode();
            }
        });

        generateJwtStormPath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateJwtViaStormPathCode();

            }
        });
    }
    /**
     *
     * code of generateJwtViaStormPathCode() has been copied
     * from a GitRepository at https://github.com/jwtk/jjwt
     *
     **/

    private void generateJwtViaGithubCode() {
        result.setText(R.string.response);

        // We need a signing key, so we'll create one just for this example. Usually
        // the key would be read from your application configuration instead.

        Key key = MacProvider.generateKey();
        String gitToken = Jwts.builder().setSubject("JWT TEST").signWith(SignatureAlgorithm.HS512, key)
                .compact();
        result.setText("Git J-JWT : " + gitToken);
        //      Now let's verify the JWT (you should always discard JWTs that don't match an expected signature):
        assert Jwts.parser().setSigningKey(key).parseClaimsJws(gitToken).getBody().getSubject().equals("JWT TEST");
        result.setText(result.getText() + "\n\n" + "1. assert getSubject().equals(\"JWT TEST\"): success \n");
        //      Will throw `SignatureException` if signature validation fails.
        //      But what if signature validation failed? You can catch SignatureException and react accordingly:
        try {

            result.setText(result.getText() + "\n\n" + "2. parseClaimJws(s) : signingKey (Signature) OK, Verified.");

            //OK, we can trust this JWT

        } catch (SignatureException e) {
            result.setText(result.getText() + "\n\n 2. signingKey (Signature) catch " + e.getClass() +e.getMessage());

            //don't trust the JWT!
        }

//        You can now enforce that JWT claims have expected values when parsing a compact JWT string.
        try {
            Jwts.parser().requireSubject("JWT TEST ").setSigningKey(key).parseClaimsJws(gitToken);
            result.setText(result.getText() + "\n\n" + "3. requireSubject(\"JWT TEST \") OK.");

        } catch(InvalidClaimException ice) {
            // the sub field was missing or did not have a 'jsmith' value
            result.setText(result.getText()+"\n\n3. requireSubject(\"JWT TEST \") catch "+ice.getClass() +ice.getMessage());

        }

//        If it is important to react to a missing vs an incorrect value,
//        instead of catching InvalidClaimException, you can catch either MissingClaimException or IncorrectClaimException:

        try {
            Jwts.parser().requireSubject("JWT :: PUNEET ").setSigningKey(key).parseClaimsJws(gitToken);
            result.setText(result.getText()+"\n\n"+ "4. require subject OK");

        } catch(MissingClaimException mce) {
            // the parsed JWT did not have the sub field
            result.setText(result.getText()+"\n\n4.1 catch "+mce.getClass() + mce.getMessage());

        } catch(IncorrectClaimException ice) {
            // the parsed JWT had a sub field, but its value was not equal to 'jsmith'
            result.setText(result.getText()+"\n\n4.2 catch "+ ice.getClass() + ice.getMessage());
        }

//        You may also require custom fields by using the require(fieldName, requiredFieldValue) method - for example:

        try {
            Jwts.parser().require("myfield", "myRequiredValue").setSigningKey(key).parseClaimsJws(gitToken);
            result.setText(result.getText() + "\n\n" + "5. require OK field value");
        } catch(InvalidClaimException ice) {
            // the 'myfield' field was missing or did not have a 'myRequiredValue' value
            result.setText(result.getText()+"\n\n5. catch "+ ice.getClass() + ice.getMessage());
        }

        Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(gitToken).getBody();
        Log.d(LOG_TAG_NAME, "Git Token contains : " + claims);
        result.setText(result.getText() + "\n\nGit Token Details " + claims);
        result.setText(result.getText() + "\n\nDecoded ID: " + claims.getId());
        result.setText(result.getText() + "\n\nDecoded Subject: " + claims.getSubject());
        result.setText(result.getText() + "\n\nDecoded Issuer: " + claims.getIssuer());
        result.setText(result.getText() + "\n\nDecoded Expiration: " + claims.getExpiration());

        result.setText(result.getText() + "\n\nDecoded Audience: " + claims.getAudience());
        result.setText(result.getText() + "\n\nDecoded IssuedAt: " + claims.getIssuedAt());
        result.setText(result.getText() + "\n\nDecoded NotBefore: " + claims.getNotBefore());
        result.setText(result.getText() + "\n\nDecoded Class: " + claims.getClass());



    }

    /**
     *
     * code of generateJwtViaStormPathCode() has been copied
     * from https://stormpath.com/blog/jwt-java-create-verify
     *
     **/

    private void generateJwtViaStormPathCode() {

        result.setText(R.string.response);

        //The JWT signature algorithm we will be using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        //if it has been specified, let's add the expiration
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        long ttlMillis = 5000;
        long expMillis = nowMillis + ttlMillis;
        Date exp = new Date(expMillis);

        //We will sign our JWT with our ApiKey secret

        /*
         * byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(apiKey.getSecret());
         * Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
         *
         * */

//        Since Above couldn't run so I'm using following signing key
        KeyGenerator keyGen;
        SecretKey signingKey = null;
        try {
            keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(128);
            signingKey = keyGen.generateKey();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        //Let's set the JWT Claims
        JwtBuilder builder = null;
        try{
            builder = Jwts.builder()
                    .setId("27052016")
                    .setIssuedAt(now)
                    .setSubject("PUNEET plz don't take too much time")
                    .setIssuer("TRANCIS")
                    .signWith(signatureAlgorithm, signingKey)
                    .setExpiration(exp);

        } catch (Exception e){
            result.setText("JWT Not created : Exception"+e.getClass()+"\n"+e.getMessage()+"\n\n");
            e.printStackTrace();
        }


        //Builds the JWT and serializes it to a compact, URL-safe string
        String jwtToken = builder != null ? builder.compact() : null;
        result.setText(result.getText() + "\n\n TOKEN[JJWT] = " + jwtToken);

        parseJWT(jwtToken, signingKey);


    }


    //Sample method to validate and read the JWT
    private void parseJWT(String jwt, Key signingKey) {
//This line will throw an exception if it is not a signed JWS (as expected)
        try{
            Claims claims = Jwts.parser()
//                .setSigningKey(DatatypeConverter.parseBase64Binary(signingKey.getSecret()))        /* it wasn't working so added below line
                    .setSigningKey(signingKey)
                    .parseClaimsJws(jwt).getBody();
            Log.d(LOG_TAG_NAME, "StormPath Token contains : " + claims);
            result.setText(result.getText() + "\n\nStormPath Token Details " + claims);
            result.setText(result.getText() + "\n\nDecoded ID: " + claims.getId());
            result.setText(result.getText() + "\n\nDecoded Subject: " + claims.getSubject());
            result.setText(result.getText() + "\n\nDecoded Issuer: " + claims.getIssuer());
            result.setText(result.getText() + "\n\nDecoded Expiration: " + claims.getExpiration());

            result.setText(result.getText() + "\n\nDecoded Audience: " + claims.getAudience());
            result.setText(result.getText() + "\n\nDecoded IssuedAt: " + claims.getIssuedAt());
            result.setText(result.getText() + "\n\nDecoded NotBefore: " + claims.getNotBefore());
            result.setText(result.getText() + "\n\nDecoded Class: " + claims.getClass());


        } catch(Exception e){
            result.setText(result.getText()+"\n\non StormPath-Decode catch Exception : "+ e.getClass() + e.getMessage());
        }
    }

}
