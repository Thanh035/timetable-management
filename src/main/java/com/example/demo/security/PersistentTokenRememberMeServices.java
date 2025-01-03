package com.example.demo.security;

import com.example.demo.dao.impl.PersistentTokensDAO;
import com.example.demo.dao.impl.UserDAO;
import com.example.demo.model.PersistentTokens;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.rememberme.AbstractRememberMeServices;
import org.springframework.security.web.authentication.rememberme.CookieTheftException;
import org.springframework.security.web.authentication.rememberme.InvalidCookieException;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;


@Service
public class PersistentTokenRememberMeServices extends AbstractRememberMeServices {

    private final Logger log = LoggerFactory.getLogger(PersistentTokenRememberMeServices.class);

    // Token is valid for one month
    private static final int TOKEN_VALIDITY_DAYS = 31;

    private static final int TOKEN_VALIDITY_SECONDS = 60 * 60 * 24 * TOKEN_VALIDITY_DAYS;

    private static final long UPGRADED_TOKEN_VALIDITY_MILLIS = 5000L;
    private final PersistentTokensDAO persistentTokensDAO;

    private final UserDAO userDAO;

    public PersistentTokenRememberMeServices(
            org.springframework.security.core.userdetails.UserDetailsService userDetailsService,
            PersistentTokensDAO persistentTokensDAO,
            UserDAO userDAO
    ) {
        super("adjavsakjasndajsdhjasdjsad", userDetailsService);
        this.persistentTokensDAO = persistentTokensDAO;
        this.userDAO = userDAO;
    }

    @Override
    protected UserDetails processAutoLoginCookie(String[] cookieTokens, HttpServletRequest request, HttpServletResponse response) {
        synchronized (this) { // prevent 2 authentication requests from the same user in parallel
            String login = null;

            if (login == null) {
                PersistentTokens token = getPersistentTokens(cookieTokens);
                login = token.getUser().getLogin();

                // Token also matches, so login is valid. Update the token value, keeping the *same* series number.
                log.debug("Refreshing persistent login token for user '{}', series '{}'", login, token.getSeries());
                token.setTokenDate(LocalDate.now());
                token.setTokenValue(RandomUtil.generateRandomAlphanumericString());
                token.setIpAddress(request.getRemoteAddr());
                token.setUserAgent(request.getHeader("User-Agent"));
                try {
                    persistentTokensDAO.saveAndFlush(token);
                } catch (DataAccessException e) {
                    log.error("Failed to update token: ", e);
                    throw new RememberMeAuthenticationException("Autologin failed due to data access problem", e);
                }
                addCookie(token, request, response);
            }
            return getUserDetailsService().loadUserByUsername(login);
        }
    }

    @Override
    protected void onLoginSuccess(HttpServletRequest request, HttpServletResponse response, Authentication successfulAuthentication) {
        String login = successfulAuthentication.getName();

        log.debug("Creating new persistent login for user {}", login);
        PersistentTokens token = userDAO
                .findOneByLogin(login)
                .map(u -> {
                    PersistentTokens t = new PersistentTokens();
                    t.setSeries(RandomUtil.generateRandomAlphanumericString());
                    t.setUserId(u.getId());
                    t.setTokenValue(RandomUtil.generateRandomAlphanumericString());
                    t.setTokenDate(LocalDate.now());
                    t.setIpAddress(request.getRemoteAddr());
                    t.setUserAgent(request.getHeader("User-Agent"));
                    return t;
                })
                .orElseThrow(() -> new UsernameNotFoundException("User " + login + " was not found in the database"));
        try {
            persistentTokensDAO.saveAndFlush(token);
            addCookie(token, request, response);
        } catch (DataAccessException e) {
            log.error("Failed to save persistent token ", e);
        }
    }

    /**
     * When logout occurs, only invalidate the current token, and not all user sessions.
     * <p>
     * The standard Spring Security implementations are too basic: they invalidate all tokens for the
     * current user, so when he logs out from one browser, all his other sessions are destroyed.
     *
     * @param request        the request.
     * @param response       the response.
     * @param authentication the authentication.
     */
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
//        String rememberMeCookie = extractRememberMeCookie(request);
//        if (rememberMeCookie != null && rememberMeCookie.length() != 0) {
//            try {
//                String[] cookieTokens = decodeCookie(rememberMeCookie);
//                PersistentTokens token = getPersistentTokens(cookieTokens);
//                persistentTokensDAO.deleteById(token.getSeries());
//            } catch (InvalidCookieException ice) {
//                log.info("Invalid cookie, no persistent token could be deleted", ice);
//            } catch (RememberMeAuthenticationException rmae) {
//                log.debug("No persistent token found, so no token could be deleted", rmae);
//            }
//        }
//        super.logout(request, response, authentication);
    }

    /**
     * Validate the token and return it.
     */
    private PersistentTokens getPersistentTokens(String[] cookieTokens) {
        if (cookieTokens.length != 2) {
            throw new InvalidCookieException(
                    "Cookie token did not contain " + 2 + " tokens, but contained '" + Arrays.asList(cookieTokens) + "'"
            );
        }
        String presentedSeries = cookieTokens[0];
        String presentedToken = cookieTokens[1];
        Optional<PersistentTokens> optionalToken = persistentTokensDAO.findById(presentedSeries);
        if (!optionalToken.isPresent()) {
            // No series match, so we can't authenticate using this cookie
            throw new RememberMeAuthenticationException("No persistent token found for series id: " + presentedSeries);
        }
        PersistentTokens token = optionalToken.orElseThrow();
        // We have a match for this user/series combination
        log.info("presentedToken={} / tokenValue={}", presentedToken, token.getTokenValue());
//        if (!presentedToken.equals(token.getTokenValue())) {
//            // Token doesn't match series value. Delete this session and throw an exception.
//            persistentTokensDAO.deleteById(token.getSeries());
//            throw new CookieTheftException("Invalid remember-me token (Series/token) mismatch. Implies previous " + "cookie theft attack.");
//        }
//        if (token.getTokenDate().plusDays(TOKEN_VALIDITY_DAYS).isBefore(LocalDate.now())) {
//            persistentTokensDAO.deleteById(token.getSeries());
//            throw new RememberMeAuthenticationException("Remember-me login has expired");
//        }
        return token;
    }
    private void addCookie(PersistentTokens token, HttpServletRequest request, HttpServletResponse response) {
        setCookie(new String[]{token.getSeries(), token.getTokenValue()}, TOKEN_VALIDITY_SECONDS, request, response);
    }

}
