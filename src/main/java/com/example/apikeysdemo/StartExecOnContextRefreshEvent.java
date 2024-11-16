package com.example.apikeysdemo;

import com.example.apikeysdemo.workflows.MyWorkflow;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class StartExecOnContextRefreshEvent {

    @Autowired
    private WorkflowClient workflowClient;


    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        MyWorkflow workflow = workflowClient.newWorkflowStub(MyWorkflow.class,
                WorkflowOptions.newBuilder()
                        .setWorkflowId("my-workflow")
                        .setTaskQueue("mytaskqueue")
                        .build());

        // start async
        WorkflowClient.start(workflow::sayHello, "Michael Jackson");
    }
}
