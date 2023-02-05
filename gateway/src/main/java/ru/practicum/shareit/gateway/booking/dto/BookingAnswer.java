package ru.practicum.shareit.gateway.booking.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class BookingAnswer {
    private Long id;
    private LocalDateTime start;
    private LocalDateTime end;
    private ItemDtoBooking item;
    private UserDtoBooking booker;
    private String status;


    @Data
    public static class UserDtoBooking {
        private Long id;
        private String name;
        private String email;

    }

    @Data
    public static class ItemDtoBooking {
        private Long id;
        private String name;
        private String description;
        private Boolean available;
        private UserDtoBooking owner;

    }

}
