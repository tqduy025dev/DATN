package com.server.datn.server.helper;

import com.server.datn.server.authority.JwtTokenProvider;
import com.server.datn.server.authority.MyUserDetail;
import com.server.datn.server.common.dto.authorize.LoginRequest;
import com.server.datn.server.common.dto.authorize.LoginResponse;
import com.server.datn.server.common.dto.base.Response;
import com.server.datn.server.common.dto.base.ResponseData;
import com.server.datn.server.common.dto.base.ResponseResult;
import com.server.datn.server.common.utils.ResponseResultUtils;
import com.server.datn.server.services.HolidaysService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import static com.server.datn.server.common.constants.AppConstants.SUCC_CODE;
import static com.server.datn.server.common.constants.AppConstants.UNAUTHORIZED_CODE;
import static com.server.datn.server.common.constants.MessageConstants.LOGIN_FAIL;
import static com.server.datn.server.common.constants.MessageConstants.LOGIN_SUCC;

@Service
public class LoginHelper {
    private final Logger logger = LoggerFactory.getLogger(LoginHelper.class);

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final HolidaysService holidaysService;

    private final Response response = new Response();

    private LoginHelper(AuthenticationManager authenticationManager, JwtTokenProvider tokenProvider, HolidaysService holidaysService) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.holidaysService = holidaysService;
    }

    private Authentication authenticate(String username, String password) throws Exception {
        try {
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    public Response authorize(LoginRequest loginRequest){
        ResponseResult result;
        ResponseData responseData =  new ResponseData();
        try {
            Authentication authentication = authenticate(loginRequest.getUsername(), loginRequest.getPassword());
            MyUserDetail myUserDetail = (MyUserDetail) authentication.getPrincipal();
            String token = tokenProvider.generateToken(myUserDetail);
            result = ResponseResultUtils.getResponseResult(LOGIN_SUCC, SUCC_CODE);
            holidaysService.refreshData();
            responseData.setData(new LoginResponse(token));
        }catch (Exception e){
            logger.error("******Login Error getDataLogin()******", e);
            result = ResponseResultUtils.getResponseResult(LOGIN_FAIL, UNAUTHORIZED_CODE);
        }
        response.setResponse(responseData);
        response.setResult(result);
        return response;
    }


}
