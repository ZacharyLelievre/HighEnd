package com.example.highenddetailing.appointmentsubdomain;

import com.example.highenddetailing.appointmentssubdomain.domainclientlayer.RescheduleRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class RescheduleRequestTest {
    @Test
    void testAllArgsConstructorAndGetters() {
        RescheduleRequest req = new RescheduleRequest("2024-12-26", "14:00", "15:00");
        assertEquals("2024-12-26", req.getNewDate());
        assertEquals("14:00", req.getNewStartTime());
        assertEquals("15:00", req.getNewEndTime());
    }

    @Test
    void testNoArgsConstructorAndSetters() {
        RescheduleRequest req = new RescheduleRequest();
        req.setNewDate("2024-12-31");
        req.setNewStartTime("09:00");
        req.setNewEndTime("10:00");

        assertEquals("2024-12-31", req.getNewDate());
        assertEquals("09:00", req.getNewStartTime());
        assertEquals("10:00", req.getNewEndTime());
    }

    @Test
    void testBuilder() {
        RescheduleRequest req = RescheduleRequest.builder()
                .newDate("2024-12-26")
                .newStartTime("14:00")
                .newEndTime("15:00")
                .build();

        assertEquals("2024-12-26", req.getNewDate());
        assertEquals("14:00", req.getNewStartTime());
        assertEquals("15:00", req.getNewEndTime());
    }

    @Test
    void testEqualsAndHashCode() {
        RescheduleRequest req1 = new RescheduleRequest("2024-12-26", "14:00", "15:00");
        RescheduleRequest req2 = new RescheduleRequest("2024-12-26", "14:00", "15:00");
        RescheduleRequest req3 = new RescheduleRequest("2024-12-27", "14:00", "15:00");

        assertEquals(req1, req2);
        assertEquals(req1.hashCode(), req2.hashCode());
        assertNotEquals(req1, req3);
        assertNotEquals(req1.hashCode(), req3.hashCode());
    }

    @Test
    void testToString() {
        RescheduleRequest req = new RescheduleRequest("2024-12-26", "14:00", "15:00");
        String str = req.toString();

        // Basic sanity checks
        assertTrue(str.contains("RescheduleRequest"));
        assertTrue(str.contains("2024-12-26"));
        assertTrue(str.contains("14:00"));
        assertTrue(str.contains("15:00"));
    }
    @Test
    void testEqualsAndHashCodeCoverage() {
        RescheduleRequest req1 = new RescheduleRequest("2024-12-26", "14:00", "15:00");
        // Same reference
        assertTrue(req1.equals(req1));

        // Compare to null
        assertFalse(req1.equals(null));

        // Compare to different class
        Object differentClass = new Object();
        assertFalse(req1.equals(differentClass));

        // Same fields
        RescheduleRequest req2 = new RescheduleRequest("2024-12-26", "14:00", "15:00");
        assertTrue(req1.equals(req2));
        assertEquals(req1.hashCode(), req2.hashCode());

        // Different field
        RescheduleRequest req3 = new RescheduleRequest("2024-12-27", "14:00", "15:00");
        assertFalse(req1.equals(req3));
        assertNotEquals(req1.hashCode(), req3.hashCode());
    }
}
