/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cloud.spring.boot.controller;

import com.cloud.spring.boot.common.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Quy Duong
 */
public abstract class AbstracBaseController {
    @Autowired
    protected ResponseUtil responseUtil;
}
