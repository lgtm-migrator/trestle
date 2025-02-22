package com.nickrobison.trestle.server.resources;

import com.nickrobison.metrician.Metrician;
import com.nickrobison.metrician.MetricianHeader;
import com.nickrobison.metrician.backends.MetricianExportedValue;
import com.nickrobison.trestle.reasoner.TrestleReasoner;
import com.nickrobison.trestle.server.annotations.PrivilegesAllowed;
import com.nickrobison.trestle.server.auth.Privilege;
import com.nickrobison.trestle.server.resources.requests.MetricsQueryRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

/**
 * Created by nrobison on 3/24/17.
 */
@Path("/metrics")
@PrivilegesAllowed({Privilege.ADMIN})
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "metrics")
public class MetricsResource {
    private static final Logger logger = LoggerFactory.getLogger(MetricsResource.class);
    public static final String CSV_SEPARATOR = ",";
    private final Metrician metrician;

    @Inject
    public MetricsResource(TrestleReasoner reasoner) {
        this.metrician = reasoner.getMetricsEngine();
    }

    @GET
    @ApiOperation(value = "Returns a summary of available system metrics",
            notes = "Returns metrics summary which includes registered metrics and time period of available data.",
            response = MetricianHeader.class)
    public Response getMetrics() {
        final MetricianHeader header = this.metrician.getMetricsHeader();
        if (header == null) {
            return Response.status(Response.Status.NOT_IMPLEMENTED).build();
        }
        return Response.ok(this.metrician.getMetricsHeader()).build();
    }

    @GET
    @Path("/metric/{metricID}")
    @ApiOperation(value = "Retrieves subset of metric values",
            notes = "Retrieves all values for the given metric over the specified temporal period. " +
                    "Returns a map of Long/Object pairs",
            response = Long.class,
            responseContainer = "Map")
    public Response getMetricValues(@NotEmpty @PathParam("metricID") String metricID, @NotNull @QueryParam("start") Long startTemporal, @QueryParam("end") Long endTemporal) {
        logger.debug("Values for {}, from {}, to {}", metricID, startTemporal, endTemporal);
        final Map<Long, Object> metricValues = this.metrician.getMetricValues(metricID, startTemporal, endTemporal);
        return Response.ok(metricValues).build();
    }

    @POST
    @Path("/export")
    @Produces(MediaType.TEXT_PLAIN)
    @ApiOperation(value = "Export values for the given metrics over the specified temporal period",
            notes = "For the given list of metrics, export all values for the given temporal period, as a CSV file. " +
                    "Returns a Streaming byte array that can be downloaded",
            response = StreamingOutput.class)
    public Response exportMetricValues(@Valid MetricsQueryRequest metrics) {
        final List<MetricianExportedValue> exportedMetrics = this.metrician.exportMetrics(metrics.getMetrics(), metrics.getStart(), metrics.getEnd());
        final StreamingOutput metricsOutput = output -> {
            final BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(output, Charset.defaultCharset()));
            for (final MetricianExportedValue metric : exportedMetrics) {
              String resultRow = metric.getMetric() +
                CSV_SEPARATOR +
                metric.getTimestamp() +
                CSV_SEPARATOR +
                metric.getValue();
              bufferedWriter.write(resultRow);
                bufferedWriter.newLine();
            }
            bufferedWriter.flush();
        };

        return Response.ok(metricsOutput).build();
    }
}
