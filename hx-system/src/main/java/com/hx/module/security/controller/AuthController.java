package com.hx.module.security.controller;

import cn.hutool.core.util.IdUtil;
import com.hx.annotation.Log;
import com.hx.annotation.rest.AnonymousDeleteMapping;
import com.hx.annotation.rest.AnonymousGetMapping;
import com.hx.annotation.rest.AnonymousPostMapping;
import com.hx.config.RsaProperties;
import com.hx.module.security.config.bean.LoginCodeEnum;
import com.hx.module.security.config.bean.LoginProperties;
import com.hx.module.security.config.bean.SecurityProperties;
import com.hx.module.security.domain.dto.AuthUserDTO;
import com.hx.module.security.domain.dto.CommonResultDTO;
import com.hx.module.security.domain.dto.JwtUserDTO;
import com.hx.module.security.domain.entity.TokenProvider;
import com.hx.module.security.service.OnlineUserService;
import com.hx.module.system.domain.dto.JsonWebTokenDTO;
import com.hx.util.RedisUtils;
import com.hx.util.RsaUtils;
import com.hx.util.SecurityUtils;
import com.wf.captcha.base.Captcha;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 授权、根据token获取用户详细信息
 */
@RestController
@RequestMapping("/auth")
@Api(tags = "系统：系统授权接口")
public class AuthController {
    private final SecurityProperties properties;
    private final RedisUtils redisUtils;
    private final OnlineUserService onlineUserService;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    @Resource
    private LoginProperties loginProperties;

    public AuthController(SecurityProperties properties, RedisUtils redisUtils, OnlineUserService onlineUserService, TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.properties = properties;
        this.redisUtils = redisUtils;
        this.onlineUserService = onlineUserService;
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    @Log("用户登录")
    @ApiOperation("登录授权")
    @AnonymousPostMapping(value = "/login")
    public ResponseEntity<Object> login(@Validated @RequestBody AuthUserDTO authUser, HttpServletRequest request) throws Exception {
        // 密码解密
        String  password = RsaUtils.decryptByPrivateKey(RsaProperties.privateKey, authUser.getPassword());
        // 查询验证码
//        String code = (String) redisUtils.get(authUser.getUuid());
//        // 清除验证码
//        redisUtils.del(authUser.getUuid());
//        if (StringUtils.isBlank(code)) {
//            throw new SkException("验证码不存在或已过期");
//        }
//        if (StringUtils.isBlank(authUser.getCode()) || !authUser.getCode().equalsIgnoreCase(code)) {
//            throw new SkException("验证码错误");
//        }
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(authUser.getAccount(), password);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        redisUtils.set(authUser.getAccount(),authentication);
        // 生成令牌
        String token = tokenProvider.createToken(authentication);
        final JwtUserDTO jwtUserDto = (JwtUserDTO) authentication.getPrincipal();
        jwtUserDto.setJsonWebToken(new JsonWebTokenDTO(token,null,null,null));
        // 保存在线信息
        onlineUserService.save(jwtUserDto, token, request);
        // 返回 token 与 用户信息
        Map<String, Object> authInfo = new HashMap<String, Object>(2) {{
            put("token", properties.getTokenStartWith() + token);
            put("data", jwtUserDto);
            put("code", 0);
        }};
        if (loginProperties.isSingleLogin()) {
            //踢掉之前已经登录的token
            onlineUserService.checkLoginOnUser(authUser.getAccount(), token);
        }
        return ResponseEntity.ok(authInfo);
    }

    @ApiOperation("获取用户信息")
    @GetMapping(value = "/info")
    public CommonResultDTO<Object> getUserInfo() {
         ResponseEntity.ok(SecurityUtils.getCurrentUser());

        return new CommonResultDTO(null);
    }


    @ApiOperation("获取验证码")
    @AnonymousGetMapping(value = "/code")
    public ResponseEntity<Object> getCode() {
        // 获取运算的结果
        Captcha captcha = loginProperties.getCaptcha();
        String uuid = properties.getCodeKey() + IdUtil.simpleUUID();
        //当验证码类型为 arithmetic时且长度 >= 2 时，captcha.text()的结果有几率为浮点型
        String captchaValue = captcha.text();
        if (captcha.getCharType() - 1 == LoginCodeEnum.arithmetic.ordinal() && captchaValue.contains(".")) {
            captchaValue = captchaValue.split("\\.")[0];
        }
        // 验证码在redis 暂时先不做
        redisUtils.set(uuid, captchaValue, loginProperties.getLoginCode().getExpiration(), TimeUnit.MINUTES);
        // 验证码信息
        Map<String, Object> imgResult = new HashMap<String, Object>(3) {{
            put("img", captcha.toBase64());
            put("uuid", uuid);
            put("randomCodeFlag",true);
        }};
        return ResponseEntity.ok(imgResult);
    }

    @ApiOperation("退出登录")
    @AnonymousDeleteMapping(value = "/logout")
    public ResponseEntity<Object> logout(HttpServletRequest request) {
        onlineUserService.logout(tokenProvider.getToken(request));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation("获取公钥")
    @AnonymousGetMapping(value="rsa/publickey")
    public ResponseEntity<Object> getPublicKey(HttpServletRequest request){
        Map<String, Object> keyResult = new HashMap<String, Object>(3) {{
            put("publicKey", RsaProperties.publicKey);
            put("code", 0);
            put("data",RsaProperties.publicKey);
        }};
        return ResponseEntity.ok(keyResult);
    }

//    @ApiOperation("刷新token")
//    @AnonymousGetMapping(value="/refreshtoken")
//    public ResponseEntity<Object> refreshtoken(HttpServletRequest request){
//        Map<String, Object> keyResult = new HashMap<String, Object>(3) {{
//            put("publicKey", RsaProperties.publicKey);
//            put("code", 0);
//            put("data",RsaProperties.publicKey);
//        }};
//        return ResponseEntity.ok(keyResult);
//    }

}
