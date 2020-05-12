/*
 * Copyright 2018. AppDynamics LLC and its affiliates.
 * All Rights Reserved.
 * This is unpublished proprietary source code of AppDynamics LLC and its affiliates.
 * The copyright notice above does not evidence any actual or intended publication of such source code.
 *
 */

package com.appdynamics.extensions.aws.sns;

import com.amazonaws.services.cloudwatch.AmazonCloudWatch;
import com.amazonaws.services.cloudwatch.model.DimensionFilter;
import com.appdynamics.extensions.aws.config.Dimension;
import com.appdynamics.extensions.aws.config.IncludeMetric;
import com.appdynamics.extensions.aws.dto.AWSMetric;
import com.appdynamics.extensions.aws.metric.NamespaceMetricStatistics;
import com.appdynamics.extensions.aws.metric.StatisticType;
import com.appdynamics.extensions.aws.metric.processors.MetricsProcessor;
import com.appdynamics.extensions.aws.metric.processors.MetricsProcessorHelper;
import com.appdynamics.extensions.aws.predicate.MultiDimensionPredicate;
import com.google.common.collect.Lists;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author Satish Muddam
 */
public class SNSMetricsProcessor implements MetricsProcessor {

    private static final String NAMESPACE = "AWS/SNS";

    private List<IncludeMetric> includeMetrics;

    private List<Dimension> dimensions;

    public SNSMetricsProcessor(List<IncludeMetric> includeMetrics, List<Dimension> dimensions) {
        this.includeMetrics = includeMetrics;
        this.dimensions = dimensions;
    }

    public List<AWSMetric> getMetrics(AmazonCloudWatch awsCloudWatch, String accountName, LongAdder awsRequestsCounter) {


        List<DimensionFilter> dimensionFilters = getDimensionFilters();

        MultiDimensionPredicate predicate = new MultiDimensionPredicate(dimensions);

        return MetricsProcessorHelper.getFilteredMetrics(awsCloudWatch, awsRequestsCounter,
                NAMESPACE,
                includeMetrics,
                dimensionFilters,
                predicate);
    }

    public StatisticType getStatisticType(AWSMetric metric) {
        return MetricsProcessorHelper.getStatisticType(metric.getIncludeMetric(), includeMetrics);
    }

    public List<com.appdynamics.extensions.metrics.Metric> createMetricStatsMapForUpload(NamespaceMetricStatistics namespaceMetricStats) {
        Map<String, String> dimensionToMetricPathNameDictionary = new HashMap<String, String>();
        for (Dimension dimension : dimensions) {
            dimensionToMetricPathNameDictionary.put(dimension.getName(), dimension.getDisplayName());
        }
        return MetricsProcessorHelper.createMetricStatsMapForUpload(namespaceMetricStats,
                dimensionToMetricPathNameDictionary, false);
    }

    public String getNamespace() {
        return NAMESPACE;
    }

    private List<DimensionFilter> getDimensionFilters() {

        List<DimensionFilter> dimensionFilters = Lists.newArrayList();
        for (Dimension dimension : dimensions) {
            DimensionFilter dbDimensionFilter = new DimensionFilter();
            dbDimensionFilter.withName(dimension.getName());
            dimensionFilters.add(dbDimensionFilter);
        }
        return dimensionFilters;
    }

}
