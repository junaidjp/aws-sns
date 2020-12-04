package com.awstraining.sns;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.CreateTopicRequest;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import com.amazonaws.services.sns.model.SetSubscriptionAttributesRequest;
import com.amazonaws.services.sns.util.Topics;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.CreateQueueRequest;

import software.amazon.sns.AmazonSNSExtendedClient;
import software.amazon.sns.SNSExtendedClientConfiguration;

//Integrate s3/sns/sqs

public class IntegrationAWSComponentsPublishing {

	static String ACCESS_KEY = "AKIAJMMYMHBAO2QUZVKQ";
	static String SECRET_KEY = "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";
	//final static String topicArn = "arn:aws:sns:us-east-2:264455084456:MyTopic";
	
	public static void main(String args[]) {

		final String BUCKET_NAME = "test-awstraining-extended";
		final String TOPIC_NAME = "extended-myTopic";
	
		
		final String QUEUE_NAME = "extended-client-queue";
		
		final int EXTENDED_STORAGE_MESSAGE_SIZE_THRESHOLD = 32;
		AWSCredentials awsCredentials = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);
		
		AmazonSNS snsClient = AmazonSNSClient.builder().withRegion(Regions.US_EAST_2)
					.withCredentials(new AWSStaticCredentialsProvider(awsCredentials)).build();
		// Subscribe an email endpoint to an Amazon SNS topic.
		
		
		 AmazonSQS sqsClient =
				AmazonSQSClientBuilder.standard().withRegion(Regions.US_EAST_2).withCredentials(new AWSStaticCredentialsProvider(awsCredentials)).build();
		
		
		  AmazonS3 s3Client =
				 AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_2).withCredentials(new AWSStaticCredentialsProvider(awsCredentials)).build();
		
		
		//Create bucket, topic, queue and subscription
		  s3Client.createBucket(BUCKET_NAME);
		  final String topicArn = snsClient.createTopic(
		  new CreateTopicRequest().withName(TOPIC_NAME)
		  ).getTopicArn();
		  final String queueUrl = sqsClient.createQueue(
		  new CreateQueueRequest().withQueueName(QUEUE_NAME)
		  ).getQueueUrl();
		  final String subscriptionArn = Topics.subscribeQueue(
		  snsClient, sqsClient, topicArn, queueUrl
		  );
		  
		  
		//To read message content stored in S3 transparently through SQS extended client,
		//set the RawMessageDelivery subscription attribute to TRUE
		final SetSubscriptionAttributesRequest subscriptionAttributesRequest = new
		SetSubscriptionAttributesRequest();
		subscriptionAttributesRequest.setSubscriptionArn(subscriptionArn);
		subscriptionAttributesRequest.setAttributeName("RawMessageDelivery");
		subscriptionAttributesRequest.setAttributeValue("TRUE");
		snsClient.setSubscriptionAttributes(subscriptionAttributesRequest);
		
		
		
		
		//Initialize SNS extended client
		//PayloadSizeThreshold triggers message content storage in S3 when the threshold is exceeded
		//To store all messages content in S3, use AlwaysThroughS3 flag
		 SNSExtendedClientConfiguration snsExtendedClientConfiguration = new
		SNSExtendedClientConfiguration()
		.withPayloadSupportEnabled(s3Client, BUCKET_NAME)
		.withPayloadSizeThreshold(EXTENDED_STORAGE_MESSAGE_SIZE_THRESHOLD);
		
		 final AmazonSNSExtendedClient snsExtendedClient = new
		AmazonSNSExtendedClient(snsClient, snsExtendedClientConfiguration);
		
		 
		//Publish message via SNS with storage in S3
		 final String message = "Any Message which is greater than 32 bytes, store it in s3";
		 final PublishResult publishResponse =   snsExtendedClient.publish(topicArn, message);
		 // Print the MessageId of the message.
		System.out.println("MessageId: " + publishResponse.getMessageId());
		
		

	}
	
	
}
