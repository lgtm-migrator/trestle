package com.nickrobison.trestle.exporter;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by nrobison on 9/14/16.
 */
public interface ITrestleExporter {

    enum DataType {
        SHAPEFILE,
        GEOJSON,
        KML,
        KMZ,
        TOPOJSON
    }

    /**
     * Determines which
     *
     * @return - {@link DataType}
     */
    DataType exporterType();

    /**
     * Write a given list of properties to the output format
     *
     * @param individuals - List of Property,Value maps to write
     * @param fileName    - {@link String} of file to create
     * @return - ByteBuffer of data format
     * @throws IOException - Throws exception
     */
    File writePropertiesToByteBuffer(List<TSIndividual> individuals, String fileName) throws IOException;
}
