package com.example.apikeysdemo.workflows;

import io.temporal.spring.boot.WorkflowImpl;
import io.temporal.workflow.Workflow;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;

import java.time.Duration;

//@Profile("one")
//@ComponentScan
@WorkflowImpl(taskQueues = "mytaskqueue")
public class MyWorkflowImpl implements MyWorkflow {
    @Override
    public String sayHello(String name) {
        Workflow.sleep(Duration.ofSeconds(5));
        return "ONE -- Hello: " + name;
    }
}
