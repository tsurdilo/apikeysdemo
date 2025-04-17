package com.example.apikeysdemo;

import com.example.apikeysdemo.workflows.MyWorkflow;
import com.example.apikeysdemo.workflows2.MyWorkflowTwo;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import jakarta.annotation.Resource;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class StartExecOnContextRefreshEvent {

    @Resource
    private WorkflowClient workflowClient;

    @Resource(name = "secondWorkflowClient")
    private WorkflowClient secondWorkflowClient;


    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        MyWorkflow workflow = workflowClient.newWorkflowStub(MyWorkflow.class,
                WorkflowOptions.newBuilder()
                        .setWorkflowId("my-workflow-new")
                        .setTaskQueue("mytaskqueue")
                        .build());

        // start async
        WorkflowClient.start(workflow::sayHello, "Michael Jackson");

        MyWorkflowTwo workflowTwo = secondWorkflowClient.newWorkflowStub(MyWorkflowTwo.class,
                WorkflowOptions.newBuilder()
                        .setWorkflowId("my-workflow-new-two")
                        .setTaskQueue("mytaskqueue")
                        .build());

        // start async
        WorkflowClient.start(workflowTwo::sayHello, "Michael Jordan");
    }
}
