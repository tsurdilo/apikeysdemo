package com.example.apikeysdemo.workflows;

import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface MyWorkflow {
    @WorkflowMethod
    String sayHello(String name);
}
