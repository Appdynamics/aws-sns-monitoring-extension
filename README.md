# AWS SNS Monitoring Extension

## Use Case
Captures SNS statistics from Amazon CloudWatch and displays them in the AppDynamics Metric Browser.

**Note : By default, the Machine agent can only send a fixed number of metrics to the controller. This extension potentially reports thousands of metrics, so to change this limit, please follow the instructions mentioned [here](https://docs.appdynamics.com/display/PRO40/Metrics+Limits).**

## Prerequisites
1. Please give the following permissions to the account being used to with the extension.
   **cloudwatch:ListMetrics**
   **cloudwatch:GetMetricStatistics**
2. In order to use this extension, you do need a [Standalone JAVA Machine Agent](https://docs.appdynamics.com/display/PRO44/Standalone+Machine+Agents) or [SIM Agent](https://docs.appdynamics.com/display/PRO44/Server+Visibility).  For more details on downloading these products, please  visit [here](https://download.appdynamics.com/).
3. The extension needs to be able to connect to AWS Cloudwatch in order to collect and send metrics. To do this, you will have to either establish a remote connection in between the extension and the product, or have an agent on the same machine running the product in order for the extension to collect and send the metrics.


## Installation

1. Run 'mvn clean install' from aws-sns-monitoring-extension
2. Copy and unzip AWSSNSMonitor-\<version\>.zip from 'target' directory into \<machine_agent_dir\>/monitors/
3. Edit config.yaml file in AWSSNSMonitor/conf and provide the required configuration (see Configuration section)
4. Restart the Machine Agent.
Please place the extension in the "**monitors**" directory of your Machine Agent installation directory. Do not place the extension in the "**extensions**" directory of your Machine Agent installation directory.


## Configuration
In order to use the extension, you need to update the config.yml file that is present in the extension folder. The following is a step-by-step explanation of the configurable fields that are present in the config.yml file.

1. If SIM is enabled, then use the following metricPrefix
        ```
        metricPrefix: "Custom Metrics|AWS SNS"
        ```
   Else, configure the "COMPONENT_ID" under which the metrics need to be reported. This can be done by changing the value of `<COMPONENT_ID>` in
        metricPrefix: "Server|Component:<COMPONENT_ID>|Custom Metrics|AWS SNS|".

        For example,
        ```
        metricPrefix: "Server|Component:100|Custom Metrics|AWS SNS|"
        ```

2. Provide accessKey(required) and secretKey(required) of our account(s), also provide displayAccountName(any name that represents your account) and
   regions(required). If you are running this extension inside an EC2 instance which has IAM profile configured then you don't have to configure these values,
   extension will use IAM profile to authenticate.
   ~~~
   accounts:
     - awsAccessKey: "XXXXXXXX1"
       awsSecretKey: "XXXXXXXXXX1"
       displayAccountName: "TestAccount_1"
       regions: ["us-east-1","us-west-1","us-west-2"]

     - awsAccessKey: "XXXXXXXX2"
       awsSecretKey: "XXXXXXXXXX2"
       displayAccountName: "TestAccount_2"
       regions: ["eu-central-1","eu-west-1"]
   ~~~

## Metrics

   1. NumberOfMessagesPublished          -     The number of messages published to your Amazon SNS topics.
   2. NumberOfNotificationsDelivered     -     The number of messages successfully delivered from your Amazon SNS topics to subscribing endpoints.
   3. NumberOfNotificationsFailed        -     The number of messages that Amazon SNS failed to deliver.
   4. NumberOfNotificationsFilteredOut   -     The number of messages that were rejected by subscription filter policies
   5. PublishSize                        -     The size of messages published.
   6. SMSMonthToDateSpentUSD             -     The charges you have accrued since the start of the current calendar month for sending SMS messages.
   7. SMSSuccessRate                     -     The rate of successful SMS message deliveries.

Apart from the above metric, we also have a metric called "API calls", that gives out the number of cloudwatch API calls from the extension.


## Extensions Workbench

Workbench is an inbuilt feature provided with each extension in order to assist you to fine tune the extension setup before you actually deploy it on the controller. Please review the following document on [How to use the Extensions WorkBench](https://community.appdynamics.com/t5/Knowledge-Base/How-to-use-the-Extensions-WorkBench/ta-p/30130)

## Troubleshooting

Please follow the steps listed in this [troubleshooting-document](https://community.appdynamics.com/t5/Knowledge-Base/How-to-troubleshoot-missing-custom-metrics-or-extensions-metrics/ta-p/28695) in order to troubleshoot your issue. These are a set of common issues that customers might have faced during the installation of the extension. If these don't solve your issue, please follow the last step on the [troubleshooting-document](https://community.appdynamics.com/t5/Knowledge-Base/How-to-troubleshoot-missing-custom-metrics-or-extensions-metrics/ta-p/28695) to contact the support team.

## Support Tickets

If after going through the [Troubleshooting Document](https://community.appdynamics.com/t5/Knowledge-Base/How-to-troubleshoot-missing-custom-metrics-or-extensions-metrics/ta-p/28695) you have not been able to get your extension working, please file a ticket and add the following information.

Please provide the following in order for us to assist you better.

1. Stop the running machine agent.
2. Delete all existing logs under <MachineAgent>/logs.
3. Please enable debug logging by editing the file <MachineAgent>/conf/logging/log4j.xml. Change the level value of the following <logger> elements to debug.
   <logger name="com.singularity">
   <logger name="com.appdynamics">
4. Start the machine agent and please let it run for 10 mins. Then zip and upload all the logs in the directory <MachineAgent>/logs/*.
5. Attach the zipped <MachineAgent>/conf/* directory here.
6. Attach the zipped <MachineAgent>/monitors/ExtensionFolderYouAreHavingIssuesWith directory here.
   For any support related questions, you can also contact help@appdynamics.com.

## Contributing

Always feel free to fork and contribute any changes directly here on [GitHub](https://github.com/Appdynamics/aws-sns-monitoring-extension).

## Version
   |          Name            |  Version   |
   |--------------------------|------------|
   |Extension Version         |1.0.3       |
   |Controller Compatibility  |4.4 or Later|
   |Last Update               |12 May 2020 |
