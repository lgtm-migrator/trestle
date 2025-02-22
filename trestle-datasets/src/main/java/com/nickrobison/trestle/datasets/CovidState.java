package com.nickrobison.trestle.datasets;

import com.nickrobison.trestle.reasoner.annotations.DatasetClass;
import com.nickrobison.trestle.reasoner.annotations.IndividualIdentifier;
import com.nickrobison.trestle.reasoner.annotations.temporal.StartTemporal;

import java.time.LocalDate;

/**
 * Created by nickrobison on 3/23/20
 */
@DatasetClass(name = "CensusState")
public class CovidState {

    private final String geoid;
    private final int confirmed;
    private final LocalDate start_date;

    public CovidState(String geoid, int confirmed, LocalDate start_date) {
        this.geoid = geoid;
        this.confirmed = confirmed;
        this.start_date = start_date;
    }

    @IndividualIdentifier
    public String getGeoid() {
        return geoid;
    }

    public int getConfirmed() {
        return confirmed;
    }

    @StartTemporal(name = "start_date")
    public LocalDate getStartDate() {
        return start_date;
    }
}
