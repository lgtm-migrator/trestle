package com.nickrobison.trestle.gaulintegrator.UnitTests;

import com.nickrobison.trestle.gaulintegrator.common.GAULHelpers;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by nrobison on 5/9/16.
 */
public class DateFieldUtilsTest {

    @Test
    public void TestYearExtraction() {
        final String inputString = "g2015_2008_2";
        final String[] testHosts = {"localhost", "test-host"};
        FileSplit inputSplit = new FileSplit(new Path("test/path/" + inputString), 0L, 1000L, testHosts);
        Assertions.assertEquals(new IntWritable(2008), GAULHelpers.extractSplitYear(inputSplit), "Years should match");
    }

}
