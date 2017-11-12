package com.yalday.proto.domain;

import org.joda.time.DateTime;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by martin on 12/11/2017.
 */
public class AppointmentTest {

    @Test
    public void testAppointmentOverlaps(){
        DateTime appointment1StartTime = new DateTime(2017, 1, 1, 12, 15);
        DateTime appointment1EndTime = new DateTime(2017, 1, 1, 12, 30);

        Appointment appointment1 = new Appointment("test",
            appointment1StartTime.toDate(),
            appointment1EndTime.toDate());

        DateTime appointment2StartTime = new DateTime(2017, 1, 1, 12, 25);
        DateTime appointment2EndTime = new DateTime(2017, 1, 1, 12, 45);

        Appointment appointment2 = new Appointment("test",
            appointment2StartTime.toDate(),
            appointment2EndTime.toDate());

        assertThat(appointment2.overlaps(appointment1)).isTrue();
    }

    @Test
    public void testAppointmentDoesNotOverlap(){
        DateTime appointment1StartTime = new DateTime(2017, 1, 1, 12, 15);
        DateTime appointment1EndTime = new DateTime(2017, 1, 1, 12, 30);

        Appointment appointment1 = new Appointment("test",
            appointment1StartTime.toDate(),
            appointment1EndTime.toDate());

        DateTime appointment2StartTime = new DateTime(2017, 1, 1, 12, 35);
        DateTime appointment2EndTime = new DateTime(2017, 1, 1, 12, 45);

        Appointment appointment2 = new Appointment("test",
            appointment2StartTime.toDate(),
            appointment2EndTime.toDate());

        assertThat(appointment2.overlaps(appointment1)).isFalse();
    }
}
