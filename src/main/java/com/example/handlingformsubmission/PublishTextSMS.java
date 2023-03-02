
package com.example.handlingformsubmission;

import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.SnsException;
import org.springframework.stereotype.Component;

@Component("PublishTextSMS")
public class PublishTextSMS {

    public void sendMessage(Greeting greeting) {

        Region region = Region.US_EAST_2;
        SnsClient snsClient = SnsClient.builder()
                .region(region)
                .credentialsProvider(EnvironmentVariableCredentialsProvider.create())
                .build();

        String message = "A new item with ID value "+ greeting.getName() +" was added to the DynamoDB table";
        String phoneNumber="+5521971122767";

        try {
            PublishRequest request = PublishRequest.builder()
                    .message(message)
                    .phoneNumber(greeting.getNumber())
                    .build();

            snsClient.publish(request);

        } catch (SnsException e) {

            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
    }
}