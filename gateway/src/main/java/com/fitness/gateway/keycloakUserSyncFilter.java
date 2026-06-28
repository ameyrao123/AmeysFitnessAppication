package com.fitness.gateway;


import com.fitness.gateway.user.RegisterRequest;
import com.fitness.gateway.user.UserService;
import com.nimbusds.jwt.JWTClaimNames;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@RequiredArgsConstructor
public class keycloakUserSyncFilter implements WebFilter {
    private final UserService userService;

    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain) {

        String userId = serverWebExchange.getRequest().getHeaders().getFirst("X-User-Id");
        String tokenId = serverWebExchange.getRequest().getHeaders().getFirst("Authorization");
        RegisterRequest registerRequest = getUserDetails(tokenId);
        if(userId==null){
            userId=registerRequest.getKeycloakId();
        }
        if (userId != null && tokenId != null) {

            String finalUserId = userId;
            return userService.validateUser(userId)
                    .flatMap(exists -> {

                        if (!exists) {
                            // register user

                            if(registerRequest!=null){
                                return userService.registerUser(registerRequest)
                                        .then(Mono.empty());
                            }
                            else{
                                return Mono.empty();
                            }



                        } else {

                            log.info("User Info Already Exists, Skipping Sync");

                            return Mono.empty();
                        }
                    })
                    .then(Mono.defer(() -> {

                        ServerHttpRequest mutatedRequest =
                                serverWebExchange.getRequest()
                                        .mutate()
                                        .header("X-User-Id", finalUserId)
                                        .build();

                        return webFilterChain.filter(
                                serverWebExchange.mutate()
                                        .request(mutatedRequest)
                                        .build()
                        );
                    }));
        }

        return webFilterChain.filter(serverWebExchange);
    }
    private RegisterRequest getUserDetails(String tokenId) {
        try{
            String tokenWithoutBearer=tokenId.replace("Bearer","".trim());
            SignedJWT signedJWT=SignedJWT.parse(tokenWithoutBearer);
            JWTClaimsSet jwtClaimsSet=signedJWT.getJWTClaimsSet();
            RegisterRequest registerRequest=new RegisterRequest();
            registerRequest.setEmail(jwtClaimsSet.getStringClaim("email"));
            registerRequest.setKeycloakId(jwtClaimsSet.getStringClaim("sub"));
            registerRequest.setFirstName(jwtClaimsSet.getStringClaim("given_name"));
            registerRequest.setLastName(jwtClaimsSet.getStringClaim("family_name"));
            registerRequest.setPassword("dummy@123");
            return registerRequest;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
