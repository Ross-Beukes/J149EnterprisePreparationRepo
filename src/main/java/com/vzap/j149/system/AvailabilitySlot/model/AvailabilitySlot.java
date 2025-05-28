package com.vzap.j149.system.AvailabilitySlot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class AvailabilitySlot {

    private Long slotId,staff;
    private Timestamp startTime,endTime;
    private Boolean isAvailable;
    private RecurrenceFrequency recurrenceFrequency;
    private Integer interval;
    private String daysOfWeek;
    private Date startDate,endDate;
}
