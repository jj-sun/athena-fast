package com.athena.modules.sys.service.impl;

import com.athena.common.exception.RRException;
import com.athena.common.utils.DateUtils;
import com.athena.modules.sys.entity.SysCaptchaEntity;
import com.athena.modules.sys.repository.SysCaptchaRepository;
import com.athena.modules.sys.service.SysCaptchaService;
import com.google.code.kaptcha.Producer;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.util.Date;
import java.util.Optional;

/**
 * 验证码
 *
 * @author Mr.sun
 */
@Service
public class SysCaptchaServiceImpl implements SysCaptchaService {
    @Autowired
    private Producer producer;

    @Autowired
    private SysCaptchaRepository captchaRepository;

    @Override
    public BufferedImage getCaptcha(String uuid) {
        if(StringUtils.isBlank(uuid)){
            throw new RRException("uuid不能为空");
        }
        //生成文字验证码
        var code = producer.createText();

        var captchaEntity = new SysCaptchaEntity();
        captchaEntity.setUuid(uuid);
        captchaEntity.setCode(code);
        //5分钟后过期
        captchaEntity.setExpireTime(DateUtils.addDateMinutes(new Date(), 5));
        captchaRepository.save(captchaEntity);

        return producer.createImage(code);
    }

    @Override
    public boolean validate(String uuid, String code) {
        Optional<SysCaptchaEntity> optional = captchaRepository.findOne((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("uuid"), uuid));
        if(optional.isEmpty()){
            return false;
        }

        //删除验证码
        captchaRepository.deleteById(uuid);
        SysCaptchaEntity captchaEntity = optional.get();

        return captchaEntity.getCode().equalsIgnoreCase(code) && captchaEntity.getExpireTime().getTime() >= System.currentTimeMillis();
    }
}
