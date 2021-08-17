package com.studay.resource.controller;

import com.studay.result.R;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('product:list')")
    public R list() {
        List<String> list = new ArrayList<>();
        list.add("huawei");
        list.add("vivo");
        list.add("oppo");
        return R.ok(list);
    }
}