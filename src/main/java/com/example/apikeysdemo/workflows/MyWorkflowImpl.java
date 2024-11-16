package com.example.apikeysdemo.workflows;

import io.temporal.spring.boot.WorkflowImpl;
import io.temporal.workflow.Workflow;

import java.time.Duration;

@WorkflowImpl(taskQueues = "mytaskqueue")
public class MyWorkflowImpl implements MyWorkflow {
    @Override
    public String sayHello(String name) {
        Workflow.sleep(Duration.ofSeconds(5));
        return "Hello: " + name;
    }
}
