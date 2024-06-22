package com.lsu.${module}.controller.admin;

import com.lsu.server.dto.${Domain}Dto;
import com.lsu.server.dto.PageDto;
import com.lsu.server.dto.ResponseDto;
import com.lsu.server.service.${Domain}Service;
import com.lsu.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
* 测试
*
* @Author wang suo
* @Date 2020/10/9 0009 18:21
* @Version 1.0
*/
@RestController
@RequestMapping("/admin/${domain}")
public class ${Domain}Controller {

private static final Logger LOG = LoggerFactory.getLogger(${Domain}Controller.class);
public static final String BUSINESS_NAME = "${tableNameCn}";

@Resource
private ${Domain}Service ${domain}Service;

@PostMapping("/list")
public ResponseDto
<PageDto> getAll(@RequestBody PageDto
    <${Domain}Dto> pageDto) {
        ResponseDto
        <PageDto> responseDto = new ResponseDto<>();
            ${domain}Service.getAll(pageDto);
            responseDto.setContent(pageDto);
            return responseDto;
            }

            @PostMapping("/save")
            public ResponseDto
            <${Domain}Dto> save(@RequestBody ${Domain}Dto ${domain}Dto) {

                // 保存校验
                <#list fieldList as field>
                    <#if !field.nullAble>
                        ValidatorUtil.require(${domain}Dto.get${field.nameBigHump}(), "${field.nameCn}");
                    </#if>
                    <#if (field.length > 0)>
                        ValidatorUtil.length(${domain}Dto.get${field.nameBigHump}(), "${field.nameCn}", 1, ${field.length});
                    </#if>
                </#list>

                ResponseDto
                <${Domain}Dto> responseDto = new ResponseDto<>();
                    ${domain}Service.save(${domain}Dto);
                    responseDto.setContent(${domain}Dto);
                    return responseDto;
                    }

                    @DeleteMapping("/delete/{id}")
                    public ResponseDto delete(@PathVariable String id) {
                    ResponseDto responseDto = new ResponseDto();
                    ${domain}Service.delete(id);
                    return responseDto;
                    }
                    }