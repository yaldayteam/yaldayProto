package com.yalday.proto.cucumber.stepdefs;

import com.yalday.proto.YaldayProtoApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = YaldayProtoApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
